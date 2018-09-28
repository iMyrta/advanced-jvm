package com.epam.advancedjvm.compilation;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

public class SyntacticSugar {

    public void foreach() {
        List<String> strings = Arrays.asList("a", "b", "c");
        for (String string : strings) {
            System.out.println(string);
        }
    }

    public void foreachArray() {
        String[] strings = {"a", "b", "c"};
        for (String string : strings) {
            System.out.println(string);
        }
    }

    public void tryWithResources() {
        try (BufferedReader reader = new BufferedReader(new StringReader("a\nb\nc"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
