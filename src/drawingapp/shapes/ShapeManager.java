package drawingapp.shapes;

import drawingapp.*;
import drawingapp.Observable;
import drawingapp.states.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;

import static drawingapp.ResizeHandle.*;

public class ShapeManager implements Observable {
    List<PropertyObserver> propertyObservers = new ArrayList<>();
    List<DrawObserver> drawObservers = new ArrayList<>();
    List<DrawableShape> shapes = new ArrayList<>();
    List<DrawableShape> selectedShapes = new ArrayList<>();
    ShapeType shapeType = ShapeType.SELECT;
    Color selectedColor = Color.BLACK;
    boolean clickedAny = false;
    int startX, startY, prevMouseX, prevMouseY;
    ResizeHandle selectedResizeHandler = NONE;
    ShapeFactory shapeFactory;
    State defaultState;
    State createState;
    State moveState;
    State resizeState;

    State state;

    public ShapeManager() {
        shapeFactory = new ShapeFactory();

        defaultState = new DefaultState(this);
        createState = new CreateState(this);
        moveState = new MoveState(this);
        resizeState = new ResizeState(this);
        state = defaultState;
    }

    public void setShapeType(ShapeType shapeType) {
        this.shapeType = shapeType;
    }

    public void setShapes(List<DrawableShape> shapes) {
        this.shapes = shapes;
        notifyDrawObservers();
        notifyPropertyObservers();
    }

    public void setSelectedShapes(List<DrawableShape> shapes) {
        this.selectedShapes = shapes;
    }

    public void addShapes(List<DrawableShape> shapes) {
        this.shapes.addAll(shapes);
    }

    public void addSelectedShapes(List<DrawableShape> shapes) {
        selectedShapes.addAll(shapes);
    }

    public List<DrawableShape> getShapes() {
        return shapes;
    }

    public void makeNotify() {
        notifyPropertyObservers();
        notifyDrawObservers();
    }

    public List<DrawableShape> getSelectedShapes() {
        return selectedShapes;
    }

    public void pressDelete(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DELETE) {
            shapes.removeAll(selectedShapes);
            selectedShapes.clear();
            //if (shapeSelectedListener != null) shapeSelectedListener.onShapeSelected(null);
            notifyPropertyObservers();
            notifyDrawObservers();
        }
    }

    public void downClick(int x, int y, boolean shiftPressed) {
        startX = x;
        startY = y;
        if (clickedAny && !selectedShapes.isEmpty()) {      // resize handler 눌렀는지 확인
            for (DrawableShape shape : selectedShapes) {
                selectedResizeHandler = shape.resizeContains(startX, startY);
                if (selectedResizeHandler != NONE) break;
            }
        }

        if (selectedResizeHandler == NONE) {                // resize handler 안 눌렀을 때
            for (int i = shapes.size() - 1; i >= 0; i--) {
                DrawableShape shape = shapes.get(i);
                clickedAny = false;
                if (shape.contains(startX, startY)) { // 도형 클릭
                    clickedAny = true;
                    if (shiftPressed) {
                        if (selectedShapes.contains(shape)) {
                            selectedShapes.remove(shape);
                            shape.setSelected(false);
                        } else {
                            selectedShapes.add(shape);
                            shape.setSelected(true);
                        }
                    } else {
                        for (DrawableShape s : selectedShapes)
                            s.setSelected(false); // 기존 선택된 개체들 선택 여부 false로 바꾸기
                        selectedShapes.clear();  // shift 없이 다른 객체 클릭 시 selectedShapes 비우기
                        selectedShapes.add(shape);
                        shape.setSelected(true);
                    }
                    notifyPropertyObservers();
                    break;
                }
            }
        }

        if (!clickedAny && !shiftPressed) {     //마우스 눌렀는데 도형 선택도 안하고 shift도 안 누르면
            for (DrawableShape s : selectedShapes) s.setSelected(false);
            selectedShapes.clear();
            selectedResizeHandler = NONE;
            notifyPropertyObservers();
        }

        prevMouseX = startX;
        prevMouseY = startY;

        notifyDrawObservers();
    }

    public void drag(int x, int y) {
        if (!selectedShapes.isEmpty() && selectedResizeHandler == NONE) {
            int dx = x - prevMouseX;
            int dy = y - prevMouseY;
            for (DrawableShape shape : selectedShapes) {
                shape.moveBy(dx, dy);
                notifyPropertyObservers();
            }
            prevMouseX = x;
            prevMouseY = y;
            notifyDrawObservers();
        }


        if (!selectedShapes.isEmpty() && selectedResizeHandler != NONE) {
            int dx = x - prevMouseX;
            int dy = y - prevMouseY;
            for (DrawableShape shape : selectedShapes) {
                shape.resize(dx, dy, selectedResizeHandler);
                notifyPropertyObservers();
            }
            prevMouseX = x;
            prevMouseY = y;
            notifyDrawObservers();
        }
    }

    public void upClick(int x, int y) {
        if (selectedShapes.isEmpty()) {  //선택된 도형 없으면
            DrawableShape shape = shapeFactory.createShape(shapeType, startX, startY, x, y, selectedColor);
            if (shape != null) {
                shapes.add(shape);
                notifyPropertyObservers();
                notifyDrawObservers();
            }
        }
        notifyDrawObservers();
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

    public void selectAll() {
        selectedShapes.clear();
        for (DrawableShape shape : shapes) {
            shape.setSelected(true);
            selectedShapes.add(shape);
        }
        notifyPropertyObservers();
        notifyDrawObservers();
    }

    public void deleteShape() {
        for (DrawableShape shape : selectedShapes) {
            shapes.remove(shape);
        }
        selectedShapes.clear();

        notifyPropertyObservers();
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
