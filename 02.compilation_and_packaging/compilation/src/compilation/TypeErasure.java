package compilation;

import java.util.List;

public class TypeErasure<T> {

    T[] data;


    public static void differentLists() {
        List<Integer> ints = List.of(1, 2, 3);
        System.out.println(ints);
        List<String> strings = List.of("1", "2", "3");
        System.out.println(strings);
    }
}
