package com.epam.advancedjvm.permissions;

import java.lang.reflect.Field;

public class StringHolderHack {

    public static void hack(StringHolder instance) {

        try {
            Field field = StringHolder.class.getDeclaredField("value");
            field.setAccessible(true);
            Field type = Field.class.getDeclaredField("type");
            type.setAccessible(true);
            type.set(field, Integer.class);
            field.set(instance, 5);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }


    }
}
