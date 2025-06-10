package drawingapp.states;

import drawingapp.shapes.DrawableShape;
import drawingapp.shapes.ShapeManager;

import java.util.List;

public class CreateState implements State{
    ShapeManager model;
    List<DrawableShape> shapes;

    public CreateState(ShapeManager model) {
        this.model = model;
        shapes = model.getShapes();
    }
    @Override
    public void downClick(int x, int y, boolean shiftPressed) {}

    @Override
    public void drag(int x, int y) {}

    @Override
    public void upClick(int x, int y) {
        DrawableShape shape = model.addShape(x, y);
        if (shape != null) {
            shapes.add(shape);
            model.notifyPropertyObservers();
            model.notifyDrawObservers();
        }
        model.notifyDrawObservers();
    }
}
