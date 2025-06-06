package drawingapp.commands;

import drawingapp.ShapeType;
import drawingapp.shapes.ShapeManager;

public class ChangeTypeCommand implements Command{
    ShapeManager model;
    ShapeType shapeType, prevShapeType;

    public ChangeTypeCommand(ShapeManager model, ShapeType shapeType) {
        this.model = model;
        this.shapeType = shapeType;
    }
    @Override
    public void execute() {
        prevShapeType = model.getShapeType();
        model.setShapeType(shapeType);
    }

    @Override
    public void undo() {
        model.setShapeType(prevShapeType);
    }
}
