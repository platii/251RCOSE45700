package drawingapp.commands;

import drawingapp.shapes.DrawableShape;
import drawingapp.shapes.ShapeManager;

import java.util.ArrayList;
import java.util.List;

public class UpdateHeightCommand implements Command{
    ShapeManager model;
    List<DrawableShape> prevShapes;
    int[] prevHeight;
    int height;

    public UpdateHeightCommand(ShapeManager model, int height) {
        this.model = model;
        this.height = height;
        prevShapes = new ArrayList<>(model.getSelectedShapes());
        prevHeight = new int[prevShapes.size()];
    }

    @Override
    public void execute() {

        for (int i = 0; i < prevShapes.size(); i++) {
            prevHeight[i] = prevShapes.get(i).getHeight();
            prevShapes.get(i).setHeight(height);
        }
        model.makeNotify();
    }

    @Override
    public void undo() {
        for (int i = 0; i < prevShapes.size(); i++) {
            prevShapes.get(i).setHeight(prevHeight[i]);
        }
        model.makeNotify();
    }
}
