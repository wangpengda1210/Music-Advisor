//write code of the Shelf class here
class Shelf<T extends Book> {

    T book;

    public T getElement() {
        return book;
    }

    public void setElement(T t) {
        book = t;
    }

}