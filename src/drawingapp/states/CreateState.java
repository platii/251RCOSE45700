package drawingapp.states;

import drawingapp.shapes.ShapeManager;

public class CreateState implements State{
    ShapeManager model;

    public CreateState(ShapeManager model) {
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
