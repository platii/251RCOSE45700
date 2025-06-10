package drawingapp.commands;

import drawingapp.shapes.ShapeManager;

public class CreateShapeCommand implements Command{
    ShapeManager model;
    int x, y;

    public CreateShapeCommand(ShapeManager model, int x, int y) {
        this.model = model;
        this. x = x;
        this. y = y;
    }


    @Override
    public void execute() {
        
        model.upClick(x, y);
    }

    @Override
    public void undo() {

    }
}
