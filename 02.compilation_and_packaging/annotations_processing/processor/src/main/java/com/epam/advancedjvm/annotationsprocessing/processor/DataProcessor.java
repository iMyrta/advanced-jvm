package com.epam.advancedjvm.annotationsprocessing.processor;

import com.epam.advancedjvm.annotationsprocessing.serialize.Data;
import com.google.auto.service.AutoService;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.JavaFileObject;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@AutoService(Processor.class)
public class DataProcessor extends AbstractProcessor {

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(Data.class.getName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Entering processing");

        for (TypeElement annotation : annotations) {
            for (Element el : roundEnv.getElementsAnnotatedWith(annotation)) {
                processProtobufDataClass(el);
            }
        }

        return false;
    }

    private void processProtobufDataClass(Element element) {

        DataClass dataClass = toClass(element);
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Visited class " + dataClass, element);

        DataConverterSourceBuilder builder = new DataConverterSourceBuilder(dataClass);

        try {
            FileObject schema = builder.buildSchema(processingEnv.getFiler());
            String schemaPath = Paths.get(schema.toUri()).toString();
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Generated proto file = " + schemaPath, element);

            generateMessageClass(schemaPath, builder.getOuterClassName(), dataClass);

            builder.buildConverterClass(processingEnv.getFiler());
        } catch (Exception e) {
            processingEnv.getMessager()
                    .printMessage(Diagnostic.Kind.ERROR, "Unable to generate classes " + ExceptionUtils.exceptionToString(e), element);
        }
    }

    private DataClass toClass(Element element) {
        TypeElement rawClass = (TypeElement) element;
        String fullName = rawClass.getQualifiedName().toString();
        Element enclosing;
        do {
            enclosing = element.getEnclosingElement();
        } while (enclosing.getKind() != ElementKind.PACKAGE);

        String packageName = enclosing.toString();
        String className = fullName.substring(packageName.length() + 1);

        return new DataClass(packageName, className,
                element.getEnclosedElements().stream()
                        .map(item -> {
                            if (item.getKind() == ElementKind.FIELD) {
                                return new DataField(item.getSimpleName().toString(), item.asType().toString());
                            }
                            return null;
                        })
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList())
        );
    }

    private void generateMessageClass(String schemaLocation, String messageClass, DataClass dataClass) throws Exception {

        String sourceLocation = Paths.get(schemaLocation).getParent().toString();

        String packagePath = String.join(File.separator, dataClass.getPackageName().split("\\."));
        String exeLocation = Paths.get(processingEnv.getOptions().get("protobufLocation"), "protoc").toString();

        Path tempDir = Paths.get(".", "temp");
        if (!Files.exists(tempDir)) {
            Files.createDirectory(tempDir);
        }

        executeCommand(exeLocation + " -I=" + sourceLocation + " --java_out=" + tempDir + " " + schemaLocation + "");

        JavaFileObject messageFile = processingEnv.getFiler().createSourceFile(
                dataClass.getPackageName() + "." + messageClass);

        Path generatedSource = Paths.get(tempDir.toString(), packagePath, messageClass + ".java").toAbsolutePath().normalize();
        try (BufferedReader in = new BufferedReader(new FileReader(generatedSource.toFile()));
             PrintWriter out = new PrintWriter(messageFile.openWriter())) {
            String line;
            while ((line = in.readLine()) != null) {
                out.println(line);
            }
        }
    }

    private void executeCommand(String command) throws Exception {

        Process process = Runtime.getRuntime().exec(command);
        process.waitFor();
        BufferedReader reader =
                new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line;
        StringBuilder output = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Executed command " + command + "\n" + output.toString());
    }
}