package drawingapp.states;

import drawingapp.shapes.ShapeManager;

public class ResizeState implements State{
    ShapeManager model;

    public ResizeState(ShapeManager model) {
        this.model = model;
    }
    @Override
    public void downClick() {

    }

    @Override
    public void drag() {

    }

    @Override
    public void upClick() {

    }
}
