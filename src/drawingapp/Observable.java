package drawingapp;

public interface Observable {
    void registerObserver(PropertyObserver o);
    void removeObserver(PropertyObserver o);
}
