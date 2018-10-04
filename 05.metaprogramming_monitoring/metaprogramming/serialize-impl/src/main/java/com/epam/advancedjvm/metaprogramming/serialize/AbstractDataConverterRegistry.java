package com.epam.advancedjvm.metaprogramming.serialize;

import com.github.os72.protobuf.dynamic.DynamicSchema;
import com.github.os72.protobuf.dynamic.MessageDefinition;
import com.github.os72.protobuf.dynamic.MessageDefinition.Builder;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.DescriptorValidationException;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.DynamicMessage;

import java.lang.reflect.Field;
import java.util.*;

public class AbstractDataConverterRegistry<F> implements DataConverterRegistry {

    private final Map<Class<?>, DataConverter<?>> entries = new HashMap<>();

    private final InvocationStrategy<F> invocationStrategy;

    public AbstractDataConverterRegistry(InvocationStrategy<F> invocationStrategy) {
        this.invocationStrategy = invocationStrategy;
    }

    @SuppressWarnings("unchecked")
    public <T> DataConverter<T> getConverter(Class<T> dataClass) {
        return (DataConverter<T>) entries.computeIfAbsent(dataClass, key -> new ReflectionDataConverter<>(dataClass, invocationStrategy));
    }

    private static class ReflectionDataConverter<T, F> implements DataConverter<T> {

        private final String name;
        private final Class<T> dataClass;
        private final DynamicSchema schema;
        private final InvocationStrategy<F> invocationStrategy;
        private final List<ReflectionField<F>> fieldsMetadata;

        ReflectionDataConverter(Class<T> dataClass, InvocationStrategy<F> invocationStrategy) {
            this.dataClass = dataClass;
            name = dataClass.getSimpleName();
            this.invocationStrategy = invocationStrategy;
            List<Field> fields = getAllFields(dataClass);
            schema = generateSchema(dataClass, fields);
            fieldsMetadata = generateFieldsMetadata(fields);
        }

        public byte[] toBytes(T data) {
            try {
                DynamicMessage.Builder msgBuilder = schema.newMessageBuilder(name);
                for (ReflectionField<F> field : fieldsMetadata) {
                    Object value = invocationStrategy.invokeGetter(field.field, data);
                    if (value != null) {
                        msgBuilder.setField(field.fieldDescriptor, value);
                    }
                }
                return msgBuilder.build().toByteArray();
            } catch (Throwable e) {
                throw new IllegalArgumentException("Unsupported data " + data, e);
            }
        }

        public T fromBytes(byte[] bytes) {
            try {
                T instance = dataClass.newInstance();
                DynamicMessage msg = DynamicMessage.parseFrom(schema.getMessageDescriptor(name), bytes);
                for (ReflectionField<F> field : fieldsMetadata) {
                    Object value = msg.getField(field.fieldDescriptor);
                    invocationStrategy.invokeSetter(field.field, instance, value);
                }
                return instance;
            } catch (Throwable e) {
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

        private List<ReflectionField<F>> generateFieldsMetadata(List<Field> fields) {

            Descriptor msgDesc = schema.getMessageDescriptor(name);

            List<ReflectionField<F>> fieldsMetadata = new ArrayList<>();

            for (Field field : fields) {
                String fieldName = field.getName();
                try {
                    fieldsMetadata.add(new ReflectionField<>(
                            invocationStrategy.createFieldAccessor(
                                    field,
                                    extractGetter(field),
                                    extractSetter(field)
                            ),
                            msgDesc.findFieldByName(fieldName)
                    ));
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
            return fieldsMetadata;
        }

        private static String extractGetter(Field field) {
            return "get" + toUpperCamelCase(field.getName());
        }

        private static String extractSetter(Field field) {
            return "set" + toUpperCamelCase(field.getName());
        }

        private static String toUpperCamelCase(String fieldName) {
            String name = fieldName.substring(0, 1).toUpperCase();
            if (fieldName.length() > 1) {
                name += fieldName.substring(1);
            }
            return name;
        }
    }

    private static class ReflectionField<F> {

        F field;
        FieldDescriptor fieldDescriptor;

        ReflectionField(F field, FieldDescriptor fieldDescriptor) {
            this.field = field;
            this.fieldDescriptor = fieldDescriptor;
        }
    }

    public interface InvocationStrategy<F> {

        F createFieldAccessor(Field reflectionField, String getterName, String setterName) throws Throwable;

        Object invokeGetter(F field, Object instance) throws Throwable;

        void invokeSetter(F field, Object instance, Object value) throws Throwable;
    }
}
