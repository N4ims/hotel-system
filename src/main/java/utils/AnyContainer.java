package utils;

public class AnyContainer<T> {

    private T value;

    public AnyContainer() {
    }

    public AnyContainer(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

}
