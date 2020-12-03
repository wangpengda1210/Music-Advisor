// do not remove imports
import java.util.*;
import java.util.function.Function;

class ArrayUtils {
    // define invert method here
    public static <T> T[] invert(T[] array) {
        Collections.reverse(Arrays.asList(array));
        return array;
    }
}