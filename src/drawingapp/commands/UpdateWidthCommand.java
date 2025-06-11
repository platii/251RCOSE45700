package drawingapp.commands;

import java.util.*;
import drawingapp.shapes.DrawableShape;
import drawingapp.shapes.ShapeManager;
// 속성창에서 width 변경하는 명령
public class UpdateWidthCommand implements Command{
    ShapeManager model;
    List<DrawableShape> prevShapes;
    int[] prevWidth;
    int width;

    public UpdateWidthCommand(ShapeManager model, int width) {
        this.model = model;
        this.width = width;
        prevShapes = new ArrayList<>(model.getSelectedShapes());
        prevWidth = new int[prevShapes.size()];
    }

    @Override
    public void execute() {
        for (int i = 0; i < prevShapes.size(); i++) {
            prevWidth[i] = prevShapes.get(i).getWidth();
            prevShapes.get(i).setWidth(width);
        }
        model.makeNotify();
    }

    @Override
    public void undo() {
        for (int i = 0; i < prevShapes.size(); i++) {
            prevShapes.get(i).setWidth(prevWidth[i]);
        }
        model.makeNotify();
    }

    @Override
    public void redo() {
        execute();
    }
}
