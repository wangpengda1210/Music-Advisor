import java.util.List;

class QualityControl {

    public static boolean check(List<Box<? extends Bakery>> boxes) {
        // Add implementation here
        try {
            if (boxes == null) {
                return false;
            } else if (boxes.isEmpty()) {
                return true;
            } else {
                for (Box box : boxes) {
                    if (!box.getClass().equals(Box.class) || box.get() == null) {
                        return false;
                    } else {
                        Class clazz = box.get().getClass();
                        if (!(clazz.equals(Bakery.class) || clazz.getSuperclass().equals(Bakery.class))) {
                            return false;
                        }
                    }
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}

// Don't change the code below
class Bakery { }

class Cake extends Bakery { }

class Pie extends Bakery { }

class Tart extends Bakery { }

class Paper { }

class Box<T> {

    private T item;

    public void put(T item) {
        this.item = item;
    }

    public T get() {
        return this.item;
    }
}