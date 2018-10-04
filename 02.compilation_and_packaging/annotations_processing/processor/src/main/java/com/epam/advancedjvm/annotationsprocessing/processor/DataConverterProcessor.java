package com.epam.advancedjvm.annotationsprocessing.processor;

import com.epam.advancedjvm.annotationsprocessing.serialize.DataConverter;
import com.google.auto.service.AutoService;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@AutoService(Processor.class)
public class DataConverterProcessor extends AbstractProcessor {

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(ProtobufDataConverter.class.getName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Entering processing of converters");

        try {
            Map<String, String> convertersByType = getConvertersByType(annotations, roundEnv);
            if (!convertersByType.isEmpty()) {
                DataConverterRegistrySourceBuilder builder = new DataConverterRegistrySourceBuilder();
                builder.buildConverterClass(convertersByType, processingEnv.getFiler());
            }
        } catch (Exception e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "Unable to generate classes " + ExceptionUtils.exceptionToString(e));
        }

        return false;
    }

    private Map<String, String> getConvertersByType(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Map<String, String> convertersByType = new HashMap<>();
        for (TypeElement annotation : annotations) {
            for (Element el : roundEnv.getElementsAnnotatedWith(annotation)) {
                if (el.getKind() == ElementKind.CLASS) {
                    TypeElement element = (TypeElement) el;
                    convertersByType.put(extractGenericDataClass(element), element.getQualifiedName().toString());
                }
            }
        }
        return convertersByType;
    }

    private String extractGenericDataClass(TypeElement element) {
        DeclaredType dataType = null;
        for (TypeMirror interfaceElement : element.getInterfaces()) {
            DeclaredType declaredType = (DeclaredType) interfaceElement;
            if (declaredType.asElement().getSimpleName().contentEquals(DataConverter.class.getSimpleName())) {
                dataType = declaredType;
                break;
            }
        }
        return dataType == null ? null : dataType.getTypeArguments().get(0).toString();
    }
}
