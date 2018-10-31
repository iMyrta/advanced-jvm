package com.epam.advancedjvm.profiling;

import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Repository
public class BookRepository {

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    private final List<String> contents;

    public BookRepository() {
        contents = Collections.unmodifiableList(generateContents());
    }

    public List<Book> findBooks() {
        return execute(this::generateBooks, 80, 140);
    }

    public String findContent(Book book) {
        return execute(() -> getContent(book), 5, 9);
    }

    public List<Book> findBooksWithContent() {
        return execute(Collections::emptyList, 100, 200);
    }

    private String getContent(Book book) {
        return contents.get(ThreadLocalRandom.current().nextInt(0, contents.size()));
    }

    private <T> T execute(Supplier<T> supplier, long minWait, long maxWait) {
        long wait = ThreadLocalRandom.current().nextLong(minWait, maxWait);
        Future<T> future = executorService.submit(() -> {
            try {
                Thread.sleep(wait);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return supplier.get();
        });
        try {
            return future.get();
        } catch (InterruptedException| ExecutionException e) {
            throw new IllegalStateException(e);
        }
    }

    private List<Book> generateBooks() {
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            Book book = new Book();
            book.setAuthor("Author " + i);
            book.setTitle("Title " + i);
            book.setYear(1500 + i);
            books.add(book);
        }
        Collections.shuffle(books);
        return books;
    }

    private List<String> generateContents() {
        List<String> lines;
        try {
            URI uri = getClass().getClassLoader().getResource("text.txt").toURI();
            lines = Files.readAllLines(Path.of(uri));
        } catch (IOException | URISyntaxException e) {
            throw new IllegalStateException(e);
        }
        return lines.stream()
                .filter(line -> !line.trim().isEmpty())
                .collect(Collectors.toList());
    }
}
