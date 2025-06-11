package drawingapp.controller;

import drawingapp.ShapeType;
import drawingapp.commands.*;
import drawingapp.shapes.ShapeManager;
import drawingapp.states.DefaultState;

import java.awt.*;
import java.awt.event.KeyEvent;

public class ShapeController {
    ShapeManager model;
    CommandManager commandManager;
    //Command currentCommand;

    public ShapeController(ShapeManager model, CommandManager commandManager) {
        this.model = model;
        this.commandManager = commandManager;
    }

    public void changeType(ShapeType shapeType) {
        model.setShapeType(shapeType);
    }

    public void updateWidth(int width) {
        Command command = new UpdateWidthCommand(model, width);
        commandManager.executeCommand(command);
    }

    public void updateHeight(int height) {
        Command command = new UpdateHeightCommand(model, height);
        commandManager.executeCommand(command);
    }

    public void updateColor(Color color) {
        if (model.getSelectedShapes().isEmpty()){
            model.updateColor(color);
            return;
        }
        Command command = new UpdateColorCommand(model, color);
        commandManager.executeCommand(command);
    }

    public void selectAll() {
        model.selectAll();
    }

    public void deleteShape() {
        if (model.getSelectedShapes().isEmpty()) {
            return;
        }
        Command command = new DeleteShapeCommand(model);
        commandManager.executeCommand(command);
    }

    public void bringToFront() {
        model.bringToFront();
    }

    public void sendToBack() {
        model.sendToBack();
    }

    public void downClick(int startX, int startY, boolean shiftPressed) {
        model.downClick(startX, startY, shiftPressed);
//        currentCommand = new MouseClickCommand(model);
//        MouseClickCommand command = (MouseClickCommand) currentCommand;
//        command.executeDownClick(startX, startY, shiftPressed);
    }

    public void drag(int x, int y) {
        model.drag(x, y);
//        MouseClickCommand command = (MouseClickCommand) currentCommand;
//        command.executeDrag(x, y);
    }

    public void upClick(int x, int y) {
        model.upClick(x, y);
//        MouseClickCommand command = (MouseClickCommand) currentCommand;
//        command.setXY(x, y);
//        if (model.getState() instanceof DefaultState) {
//            command.executeUpClick();
//        } else {
//            commandManager.executeCommand(currentCommand);
//        }
    }

    public void pressDelete(KeyEvent e) {
        model.pressDelete(e);
    }

    public void undo() {
        commandManager.undo();
    }

    public void redo() {
        commandManager.redo();
    }

}
