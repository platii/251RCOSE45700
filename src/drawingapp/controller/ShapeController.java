package drawingapp.controller;

import drawingapp.ShapeType;
import drawingapp.commands.ChangeTypeCommand;
import drawingapp.commands.Command;
import drawingapp.commands.CommandManager;
import drawingapp.shapes.ShapeManager;

import java.awt.*;
import java.awt.event.KeyEvent;

public class ShapeController {
    ShapeManager model;
    CommandManager commandManager;

    public ShapeController(ShapeManager model, CommandManager commandManager) {
        this.model = model;
        this.commandManager = commandManager;
    }

    public void changeType(ShapeType shapeType) {
        Command command = new ChangeTypeCommand(model, shapeType);
        commandManager.executeCommand(command);
    }

    public void updateWidth(int width) {
        model.updateWidth(width);
    }

    public void updateHeight(int height) {
        model.updateHeight(height);
    }

    public void updateColor(Color color) {
        model.updateColor(color);
    }

    public void selectAll() {
        model.selectAll();
    }

    public void deleteShape() {
        model.deleteShape();
    }

    public void bringToFront() {
        model.bringToFront();
    }

    public void sendToBack() {
        model.sendToBack();
    }

    public void downClick(int startX, int startY, boolean shiftPressed) {
        model.downClick(startX, startY, shiftPressed);
    }

    public void drag(int x, int y) {
        model.drag(x, y);
    }

    public void upClick(int x, int y) {
        model.upClick(x, y);
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
