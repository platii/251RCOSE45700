package drawingapp.states;

import drawingapp.shapes.ShapeManager;

public class DefaultState implements State{
    ShapeManager model;

    public DefaultState(ShapeManager model) {
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
