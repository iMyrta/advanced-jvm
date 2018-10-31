import java.util.HashMap;
import java.util.Map;

public class GarbageFactory {

    private static final Map<String, Garbage> garbageMap = new HashMap<>();

    public static Garbage getPlasticGarbage(String shape) {
        Garbage garbage = garbageMap.get(shape);
        if (garbage == null) {
            garbage = new PlasticGarbage(shape);
            garbageMap.put(shape, garbage);
            System.out.println("Creating plastic garbage with shape: " + shape);
        }

        return garbage;
    }
}
