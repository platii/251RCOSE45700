package drawingapp.states;

import drawingapp.shapes.ShapeManager;

public class MoveState implements State{
    ShapeManager model;

    public MoveState(ShapeManager model) {
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
