package com.epam.advancedjvm.jmx;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@ManagedResource(objectName = "KeywordsApplication:type=JMX,name=KeywordsServiceImpl")
public class KeywordsServiceImpl implements KeywordsService {

    private final Map<String, List<String>> cache = new ConcurrentHashMap<>();

    @Cacheable("keywords")
    @Override
    public List<String> getAllKeyWords(String text) {
        return cache.computeIfAbsent(text, this::findMostCommonWords);
    }

    @ManagedAttribute(description = "Records count estimate")
    public int getCacheSize() {
        return cache.size();
    }

    @ManagedOperation(description = "Dump the entire cache")
    public Object dumpCache() {
        return cache;
    }

    @ManagedOperation(description="Test extracting keywords")
    public List<String> findMostCommonWords(String text) {
        return Stream.of(text.split("\\s+|[.!?\\\\-]"))
                .filter(Predicate.not(String::isBlank))
                .collect(Collectors.groupingBy(String::toLowerCase, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(10)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

}
