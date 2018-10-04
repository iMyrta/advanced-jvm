package com.epam.advancedjvm.annotationsprocessing.processor;

import java.io.PrintWriter;
import java.io.StringWriter;

class ExceptionUtils {

    private ExceptionUtils() {

    }

    static String exceptionToString(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
}
