package com.epam.advancedjvm.annotationsprocessing.processor;

import com.epam.advancedjvm.annotationsprocessing.serialize.DataConverter;
import com.epam.advancedjvm.annotationsprocessing.serialize.DataConverterRegistry;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.*;
import com.squareup.javapoet.MethodSpec.Builder;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import java.util.HashMap;
import java.util.Map;

class DataConverterRegistrySourceBuilder {

    private static final String TYPE_VARIABLE = "D";
    private static final String ENTRIES_FIELD = "entries";
    private static final String CLASS_NAME = "ProtobufConverterRegistry";

    void buildConverterClass(Map<String, String> convertersByDataType, Filer filer) throws Exception {

        ParameterizedTypeName classType = ParameterizedTypeName.get(ClassName.get(Class.class),
                WildcardTypeName.subtypeOf(TypeName.OBJECT)
        );
        TypeName entriesType = ParameterizedTypeName.get(ClassName.get(HashMap.class), classType, classType);

        FieldSpec entries = FieldSpec.builder(entriesType, ENTRIES_FIELD, Modifier.PRIVATE, Modifier.FINAL).build();

        TypeSpec type = TypeSpec.classBuilder(CLASS_NAME)
                .addAnnotation(AnnotationSpec.builder(AutoService.class)
                        .addMember("value", "$T.class", DataConverterRegistry.class)
                        .build())
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(DataConverterRegistry.class)
                .addField(entries)
                .addMethod(getConstructor(convertersByDataType, entriesType))
                .addMethod(getConverterMethod())
                .build();

        String registryPackage = getClass().getPackage().getName();
        JavaFile javaFile = JavaFile.builder(registryPackage, type)
                .build();

        javaFile.writeTo(filer);
    }

    private MethodSpec getConstructor(Map<String, String> convertersByDataType, TypeName entriesType) {
        Builder constructorBuilder = MethodSpec.constructorBuilder()
                .addStatement(ENTRIES_FIELD + " = new $T()", entriesType);
        for (Map.Entry<String, String> entry : convertersByDataType.entrySet()) {
            constructorBuilder.addStatement(ENTRIES_FIELD + ".put($T.class, $T.class)",
                    ClassName.bestGuess(entry.getKey()), ClassName.bestGuess(entry.getValue())
            );
        }
        return constructorBuilder.build();
    }

    private MethodSpec getConverterMethod() {
        return MethodSpec.methodBuilder("getConverter")
                .addModifiers(Modifier.PUBLIC)
                .addTypeVariable(TypeVariableName.get(TYPE_VARIABLE))
                .addParameter(ParameterSpec.builder(
                        ParameterizedTypeName.get(ClassName.get(Class.class), TypeVariableName.get(TYPE_VARIABLE)), "dataClass")
                        .build())
                .beginControlFlow("try")
                .addStatement("return (DataConverter<" + TYPE_VARIABLE + ">) " + ENTRIES_FIELD + ".get(dataClass).newInstance()")
                .nextControlFlow("catch (Exception e)")
                .addStatement("throw new IllegalArgumentException(e)")
                .endControlFlow()
                .returns(ParameterizedTypeName.get(ClassName.get(DataConverter.class), TypeVariableName.get(TYPE_VARIABLE)))
                .build();
    }
}
