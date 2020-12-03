import java.util.ArrayList;
import java.util.List;

/**
 * Class to work with
 */
class Violator {

    public static List<Box<? extends Bakery>> defraud() {
        // Add implementation here
        ArrayList<Box<? extends Bakery>> list = new ArrayList<>();
        Box paperBox = new Box();
        paperBox.put(new Paper());
        list.add(paperBox);
        return list;
    }

}