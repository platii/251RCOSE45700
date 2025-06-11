package drawingapp.commands;

import drawingapp.shapes.DrawableShape;
import drawingapp.shapes.ShapeManager;
import drawingapp.states.CreateState;
import drawingapp.states.MoveState;
import drawingapp.states.ResizeState;
import drawingapp.states.State;

import java.util.ArrayList;
import java.util.List;

public class MouseClickCommand implements Command{
    ShapeManager model;
    int x1, y1, x2, y2, x3, y3;
    boolean shiftPressed;
    State state;
    int state_num = 0;
    DrawableShape addedShape;
    List<DrawableShape> selectedShapes;
    List<DrawableShape> shapes;

    public MouseClickCommand(ShapeManager model) {
        this.model = model;
        shapes = model.getShapes();
    }

    public void executeDownClick(int x, int y, boolean shiftPressed) {
        this.x1 = x;
        this.y1 = y;
        this.shiftPressed = shiftPressed;
        model.downClick(x1, y1, shiftPressed);
        state = model.getState();
        if (state instanceof CreateState){
            state_num = 1;
        } else if (state instanceof MoveState) {
            state_num = 2;
            selectedShapes = new ArrayList<>(model.getSelectedShapes());
        } else if (state instanceof ResizeState) {
            state_num = 3;
            selectedShapes = new ArrayList<>(model.getSelectedShapes());
        }
    }

    public void executeDrag(int x, int y) {
        this.x2 = x;
        this.y2 = y;
        model.drag(x2, y2);
    }

    public void executeUpClick() {
        model.upClick(x3, y3);
        if (!shapes.isEmpty())
            addedShape = shapes.get(shapes.size() - 1);
    }

    @Override
    public void execute() { // upClick 실행
        model.upClick(x3, y3);
    }

    @Override
    public void undo() {
        if (state_num == 1) {   //CreateState
            shapes.remove(addedShape);
        } else if (state_num > 1) {  //MoveState, ResizeState
            for (DrawableShape selectedShape : selectedShapes) {
                for (DrawableShape shape : shapes) {
                    if (shape.getId() == selectedShape.getId()) {
                        shapes.remove(shape);
                        shapes.add(selectedShape);
                    }
                }
            }
        }
    }

    @Override
    public void redo() {
        executeDownClick(x1, y1, shiftPressed);
        executeDrag(x2, y2);
        executeUpClick();
    }

    public void setXY(int x, int y) {
        this.x3 = x;
        this.y3 = y;
    }
}
