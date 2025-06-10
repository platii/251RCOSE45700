package drawingapp.states;

import drawingapp.shapes.DrawableShape;
import drawingapp.shapes.ShapeManager;

import java.util.List;

public class ResizeState implements State{
    ShapeManager model;
    List<DrawableShape> shapes;
    List<DrawableShape> selectedShapes;

    public ResizeState(ShapeManager model) {
        this.model = model;
        shapes = model.getShapes();
        selectedShapes = model.getSelectedShapes();
    }

    @Override
    public void downClick(int x, int y, boolean shiftPressed) {}

    @Override
    public void drag(int x, int y) {
        int dx = x - model.getPrevMouseX();
        int dy = y - model.getPrevMouseY();
        for (DrawableShape shape : selectedShapes) {
            shape.resize(dx, dy, model.getSelectedResizeHandler());
            model.notifyPropertyObservers();
        }
        model.setPrevMouseX(x);
        model.setPrevMouseY(y);
        model.notifyDrawObservers();
    }

    @Override
    public void upClick(int x, int y) {}
}
