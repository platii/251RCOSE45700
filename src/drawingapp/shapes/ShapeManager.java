package drawingapp.shapes;

import drawingapp.DrawObserver;
import drawingapp.Observable;
import drawingapp.PropertyObserver;
import drawingapp.ShapeType;

import java.awt.*;
import java.util.*;
import java.util.List;

public class ShapeManager implements Observable {
    List<PropertyObserver> propertyObservers = new ArrayList<>();
    List<DrawObserver> drawObservers = new ArrayList<>();
    List<DrawableShape> shapes = new ArrayList<>();
    List<DrawableShape> selectedShapes = new ArrayList<>();
    ShapeType shapeType = ShapeType.SELECT;
    Color selectedColor = Color.BLACK;

    public void setShapeType(ShapeType shapeType) {
        this.shapeType = shapeType;
    }

    public List<DrawableShape> getShapes() {
        return shapes;
    }

    public void updateWidth(int width) {
        for (DrawableShape shape : selectedShapes) {
            shape.setWidth(width);
        }
        notifyDrawObservers();
        notifyPropertyObservers();
    }

    public void updateHeight(int height) {
        for (DrawableShape shape : selectedShapes) {
            shape.setHeight(height);
        }
        notifyDrawObservers();
        notifyPropertyObservers();
    }

    public void updateColor(Color color) {
        if (!selectedShapes.isEmpty()) {    // 선택한 도형의 색 변경
            selectedColor = color;
            for (DrawableShape shape : selectedShapes) {
                shape.setColor(selectedColor);
            }
        } else {
            selectedColor = color;      // 앞으로 생성할 도형의 색 변경
        }
        notifyDrawObservers();
    }

    public void bringToFront() {
        if (selectedShapes.isEmpty()) return;

        for (DrawableShape shape : selectedShapes) {
            int index = shapes.indexOf(shape);
            if (index < shapes.size() - 1){
                Collections.swap(shapes, index, index+1);
            }
        }
        notifyDrawObservers();
    }

    public void sendToBack() {
        if (selectedShapes.isEmpty()) return;

        for (DrawableShape shape : selectedShapes) {
            int index = shapes.indexOf(shape);
            if (index > 0) {
                Collections.swap(shapes, index, index - 1);
            }
        }
        notifyDrawObservers();
    }

    // PropertyObserver 관리
    public void registerObserver(PropertyObserver o) {
        propertyObservers.add(o);
    }

    public void removeObserver(PropertyObserver o) {
        int i = propertyObservers.indexOf(o);
        if (i >= 0) {
            propertyObservers.remove(i);
        }
    }

    public void notifyPropertyObservers() {
        for (int i = 0; i < propertyObservers.size(); i++) {
            PropertyObserver observer = propertyObservers.get(i);
            observer.updateProperties();
        }
    }

    // DrawObserver 관리
    public void registerObserver(DrawObserver o) {
        drawObservers.add(o);
    }

    public void removeObserver(DrawObserver o) {
        int i = drawObservers.indexOf(o);
        if (i >= 0) {
            drawObservers.remove(i);
        }
    }

    public void notifyDrawObservers() {
        for (int i = 0; i < drawObservers.size(); i++) {
            DrawObserver observer = drawObservers.get(i);
            observer.updateCanvas();
        }
    }
}
