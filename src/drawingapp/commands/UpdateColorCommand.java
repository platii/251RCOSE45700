package drawingapp.commands;

import drawingapp.shapes.DrawableShape;
import drawingapp.shapes.ShapeManager;

import java.awt.*;
import java.util.*;
import java.util.List;

public class UpdateColorCommand implements Command{
    ShapeManager model;
    Color color;
    List<DrawableShape> prevShapes;
    List<Color> prevColors = new ArrayList<>();


    public UpdateColorCommand(ShapeManager model, Color color) {
        this.model = model;
        this.color = color;
        prevShapes = new ArrayList<>(model.getSelectedShapes());
    }

    @Override
    public void execute() {
        for (int i = 0; i < prevShapes.size(); i++) {
            prevColors.add(prevShapes.get(i).getColor());
            prevShapes.get(i).setColor(color);
        }
        model.makeNotify();
    }

    @Override
    public void undo() {
        for (int i = 0; i < prevShapes.size(); i++) {
            prevShapes.get(i).setColor(prevColors.get(i));
        }
        model.makeNotify();
    }

    @Override
    public void redo() {
        execute();
    }
}
