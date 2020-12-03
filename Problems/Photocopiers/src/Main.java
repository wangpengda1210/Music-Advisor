/**
 * Class to work with
 */
class Multiplicator {

	public static <T extends Copy<T>> Folder<T>[] multiply(Folder<T> folder, int arraySize) {
		// Method to implement
        Folder<T>[] folders = new Folder[arraySize];
        for (int i = 0; i < arraySize; i++) {
            Folder<T> f = new Folder<>();
            f.put(folder.get().copy());
            folders[i] = f;
        }
        return folders;
	}

}

// Don't change the code below
interface Copy<T> {
	T copy();
}

class Folder<T> {

    private T item;

    public void put(T item) {
    	this.item = item;
    }

    public T get() {
        return this.item;
    }
}