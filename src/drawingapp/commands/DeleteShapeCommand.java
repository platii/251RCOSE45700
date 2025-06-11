package drawingapp.commands;

import drawingapp.shapes.DrawableShape;
import drawingapp.shapes.ShapeManager;

import java.util.ArrayList;
import java.util.List;

public class DeleteShapeCommand implements Command{
    ShapeManager model;
    List<DrawableShape> deleteShapes;

    public DeleteShapeCommand(ShapeManager model) {
        this.model = model;
        deleteShapes = new ArrayList<>(model.getSelectedShapes());
        for (DrawableShape prevShape : deleteShapes) {
            prevShape.setSelected(false);
        }
    }

    @Override
    public void execute() {
        if (model.getSelectedShapes().isEmpty()) {
            model.addSelectedShapes(deleteShapes);
        }
        model.deleteShape();
    }

    @Override
    public void undo() {
        model.addShapes(deleteShapes);
        model.makeNotify();
    }

    @Override
    public void redo() {
        execute();
    }
}
