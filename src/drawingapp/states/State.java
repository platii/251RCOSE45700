package drawingapp.states;

public interface State {
    void downClick(int x, int y, boolean shiftPressed);
    void drag(int x, int y);
    void upClick(int x, int y);
}
