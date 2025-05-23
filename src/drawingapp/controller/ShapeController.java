package drawingapp.controller;

import drawingapp.ShapeType;
import drawingapp.shapes.DrawableShape;
import drawingapp.shapes.ShapeManager;
import drawingapp.ui.DrawingPanel;
import drawingapp.ui.DrawingToolBar;
import drawingapp.ui.PropertyPanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Collections;

public class ShapeController {
    ShapeManager model;
    DrawingPanel drawingPanel;
    DrawingToolBar drawingToolBar;
    PropertyPanel propertyPanel;

    public ShapeController(ShapeManager model) {
        this.model = model;
    }

    public void changeType(ShapeType shapeType) {
        model.setShapeType(shapeType);
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

}
