package com.epam.advancedjvm.processor;

import com.google.protobuf.InvalidProtocolBufferException;
import com.squareup.javapoet.*;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.PrintWriter;

class ProtobufSourceBuilder {

    private final ProtobufDataClass dataClass;
    private final String outerClassName;
    private final String converterClassName;
    private final String messageClassName;

    ProtobufSourceBuilder(ProtobufDataClass dataClass) {
        this.dataClass = dataClass;
        outerClassName = dataClass.getName() + "Protos";
        converterClassName = dataClass.getName() + "Converter";
        messageClassName = dataClass.getName();
    }

    String getOuterClassName() {
        return outerClassName;
    }

    FileObject buildSchema(Filer filer) throws Exception {
        String fileName = outerClassName + ".proto";

        String packageName = dataClass.getPackageName();
        FileObject file = filer.createResource(StandardLocation.SOURCE_OUTPUT, packageName, fileName);
        try (PrintWriter out = new PrintWriter(file.openWriter())) {
            out.println("syntax = \"proto3\";");
            out.println();
            out.println("package " + packageName + ";");
            out.println();
            out.println("option java_package = \"" + packageName + "\";");
            out.println("option java_outer_classname = \"" + outerClassName + "\";");
            out.println();

            out.println("message " + messageClassName + " {");
            out.println();
            for (int i = 0; i < dataClass.getFields().size(); i++) {
                ProtobufDataField field = dataClass.getFields().get(i);
                String protobufType;
                switch (field.getType()) {
                    case "java.lang.String":
                        protobufType = "string";
                        break;
                    case "int":
                        protobufType = "int32";
                        break;
                    default:
                        throw new IllegalArgumentException("Unsupported type " + field);
                }
                out.println("  " + protobufType + " " + field.getName() + " = " + (i + 1) + ";");
            }
            out.println();
            out.println("}");
        }

        return file;
    }

    void buildConverterClass(Filer filer) throws Exception {
        String innerClass = outerClassName + "." + messageClassName;
        String dataClassName = dataClass.getName();
        String packageName = dataClass.getPackageName();

        TypeSpec type = TypeSpec.classBuilder(converterClassName)
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ParameterizedTypeName.get(ClassName.get(ProtobufConverter.class), TypeVariableName.get(dataClassName)))
                .addMethod(fromBytes(dataClassName, innerClass))
                .addMethod(toBytes(dataClassName, innerClass))
                .build();

        JavaFile javaFile = JavaFile.builder(packageName, type)
                .build();

        javaFile.writeTo(filer);
    }

    private MethodSpec fromBytes(String dataClassName, String innerClass) {

        MethodSpec.Builder builder = MethodSpec.methodBuilder("fromBytes")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(ClassName.get(dataClass.getPackageName(), dataClassName))
                .addParameter(byte[].class, "bytes")
                .beginControlFlow("try")

                .addStatement(innerClass + " proto = " + innerClass + ".parseFrom(bytes)")
                .addStatement(dataClassName + " data = new " + dataClassName + "()");

        for (ProtobufDataField field : dataClass.getFields()) {
            String capitalize = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
            String getter = "get" + capitalize;
            String setter = "set" + capitalize;
            builder = builder.addStatement("data." + setter + "(proto." + getter + "())");
        }

        return builder
                .addStatement("return data")
                .nextControlFlow("catch ($T e)", InvalidProtocolBufferException.class)
                .addStatement("throw new $T(e)", IllegalArgumentException.class)
                .endControlFlow()
                .build();
    }

    private MethodSpec toBytes(String dataClassName, String innerClass) {

        MethodSpec.Builder builder = MethodSpec.methodBuilder("toBytes")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .returns(byte[].class)
                .addParameter(ClassName.get(dataClass.getPackageName(), dataClassName), "data")
                .addStatement(innerClass + ".Builder builder = " + innerClass + ".newBuilder()");

        for (ProtobufDataField field : dataClass.getFields()) {
            boolean nullCheck = field.getType().equals("java.lang.String");
            String capitalize = field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
            String getter = "get" + capitalize;
            String setter = "set" + capitalize;
            if (nullCheck) {
                builder = builder.beginControlFlow("if (data." + getter + "() != null)");
            }
            builder = builder.addStatement("builder." + setter + "(data." + getter + "())");
            if (nullCheck) {
                builder = builder.endControlFlow();
            }
        }

        return builder.addStatement("return builder.build().toByteArray()")
                .build();

    }
}
