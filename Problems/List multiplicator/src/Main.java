import java.util.List;

/**
    Class to modify
*/
class ListMultiplicator {

    /**
        Repeats original list content provided number of times   
        @param list list to repeat
        @param n times to repeat, should be zero or greater
    */
	public static void multiply(List<?> list, int n) {
		// Add implementation here
		multiplyCaptured(list, n);
	}

	private static <T> void multiplyCaptured(List<T> list, int n) {
		if (n == 0) {
			list.clear();
		} else {
			int size = list.size();
			for (int time = 0; time < n - 1; time++) {
				for (int i = 0; i < size; i++) {
					list.add(list.get(i));
				}
			}
		}
	}
}