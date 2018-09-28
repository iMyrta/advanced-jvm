package com.epam.advancedjvm.bytecodemanipulation.reflection;

import com.epam.advancedjvm.annotationsprocessing.serialize.DataConverter;
import com.epam.advancedjvm.annotationsprocessing.serialize.DataConverterRegistry;
import com.github.os72.protobuf.dynamic.DynamicSchema;
import com.github.os72.protobuf.dynamic.MessageDefinition;
import com.github.os72.protobuf.dynamic.MessageDefinition.Builder;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.DescriptorValidationException;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.DynamicMessage;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class ReflectionDataConverterRegistry implements DataConverterRegistry {

    private static final Map<Class<?>, DataConverter<?>> entries = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T> DataConverter<T> getConverter(Class<T> dataClass) {
        return (DataConverter<T>) entries.computeIfAbsent(dataClass, key -> new ReflectionDataConverter<>((Class<T>) key));
    }

    private static class ReflectionDataConverter<T> implements DataConverter<T> {

        private final String name;
        private final Class<T> dataClass;
        private final DynamicSchema schema;
        private final List<ReflectionField> fieldsMetadata;

        ReflectionDataConverter(Class<T> dataClass) {
            this.dataClass = dataClass;
            name = dataClass.getSimpleName();
            List<Field> fields = getAllFields(dataClass);
            schema = generateSchema(dataClass, fields);
            fieldsMetadata = generateFieldsMetadata(dataClass, fields);
        }

        public byte[] toBytes(T data) {
            try {
                DynamicMessage.Builder msgBuilder = schema.newMessageBuilder(name);
                for (ReflectionField field : fieldsMetadata) {
                    Object value = field.getter.invoke(data);
                    if (value != null) {
                        msgBuilder.setField(field.fieldDescriptor, value);
                    }
                }
                return msgBuilder.build().toByteArray();
            } catch (Exception e) {
                throw new IllegalArgumentException("Unsupported data " + data, e);
            }
        }

        public T fromBytes(byte[] bytes) {
            try {
                T instance = dataClass.newInstance();
                DynamicMessage msg = DynamicMessage.parseFrom(schema.getMessageDescriptor(name), bytes);
                for (ReflectionField field : fieldsMetadata) {
                    Object value = msg.getField(field.fieldDescriptor);
                    field.setter.invoke(instance, value);
                }
                return instance;
            } catch (Exception e) {
                throw new IllegalArgumentException("Cannot parse message ", e);
            }
        }

        private DynamicSchema generateSchema(Class<T> dataClass, List<Field> fields) {
            DynamicSchema.Builder schemaBuilder = DynamicSchema.newBuilder();
            schemaBuilder.setName(name + ".proto");

            MessageDefinition msgDef = getMessageDefinition(name, fields);

            schemaBuilder.addMessageDefinition(msgDef);

            try {
                return schemaBuilder.build();
            } catch (DescriptorValidationException e) {
                throw new IllegalArgumentException("Invalid schema for " + dataClass, e);
            }
        }

        private static MessageDefinition getMessageDefinition(String name, List<Field> fields) {
            Builder messageDefinitionBuilder = MessageDefinition.newBuilder(name);
            for (int i = 0; i < fields.size(); i++) {
                Field field = fields.get(i);
                String fieldName = field.getName();
                Class<?> fieldType = field.getType();
                String protobufType;
                if (fieldType == String.class) {
                    protobufType = "string";
                } else if (fieldType == int.class) {
                    protobufType = "int32";
                } else {
                    throw new IllegalArgumentException("Unsupported type " + field);
                }

                messageDefinitionBuilder.addField("optional", protobufType, fieldName, i + 1);
            }

            return messageDefinitionBuilder.build();
        }

        private static List<Field> getAllFields(Class<?> type) {
            List<Field> fields = new ArrayList<>(Arrays.asList(type.getDeclaredFields()));
            if (type.getSuperclass() != null) {
                fields.addAll(getAllFields(type.getSuperclass()));
            }
            return fields;
        }

        private List<ReflectionField> generateFieldsMetadata(Class<T> dataClass, List<Field> fields) {

            Descriptor msgDesc = schema.getMessageDescriptor(name);

            List<ReflectionField> fieldsMetadata = new ArrayList<>();

            for (Field field : fields) {
                String fieldName = field.getName();
                try {
                    fieldsMetadata.add(new ReflectionField(
                            field,
                            extractGetter(dataClass, field),
                            extractSetter(dataClass, field),
                            msgDesc.findFieldByName(fieldName)
                    ));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return fieldsMetadata;
        }

        private static Method extractGetter(Class<?> dataClass, Field field) throws NoSuchMethodException {
            return dataClass.getMethod("get" + toUpperCamelCase(field.getName()));
        }

        private static Method extractSetter(Class<?> dataClass, Field field) throws NoSuchMethodException {
            return dataClass.getMethod("set" + toUpperCamelCase(field.getName()), field.getType());
        }

        private static String toUpperCamelCase(String fieldName) {
            String name = fieldName.substring(0, 1).toUpperCase();
            if (fieldName.length() > 1) {
                name += fieldName.substring(1);
            }
            return name;
        }
    }

    private static class ReflectionField {

        Field field;
        Method getter;
        Method setter;
        FieldDescriptor fieldDescriptor;

        ReflectionField(Field field, Method getter, Method setter, FieldDescriptor fieldDescriptor) {
            this.field = field;
            this.getter = getter;
            this.setter = setter;
            this.fieldDescriptor = fieldDescriptor;
        }
    }
}
