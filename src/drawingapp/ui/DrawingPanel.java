package drawingapp.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;

import drawingapp.ResizeHandle;
import drawingapp.ShapeType;
import drawingapp.ShapeSelectedListener;
import drawingapp.shapes.*;

import static drawingapp.ResizeHandle.NONE;

public class DrawingPanel extends JPanel {
    private final ArrayList<DrawableShape> shapes = new ArrayList<>();
    private final ArrayList<DrawableShape> selectedShapes = new ArrayList<>();
    private ShapeType currentShapeType = ShapeType.RECTANGLE;
    private int startX, startY, prevMouseX, prevMouseY;
    private boolean dragging = false;
    private ShapeSelectedListener shapeSelectedListener;
    private Color selectedColor = Color.BLACK;
    private ResizeHandle selectedResizeHandler = NONE;
    private boolean clickedAny = false;


    public DrawingPanel() {
        setBackground(Color.WHITE); // 흰색 배경
        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                if (e.getKeyCode() == KeyEvent.VK_DELETE) {
                    shapes.removeAll(selectedShapes);
                    selectedShapes.clear();
                    if (shapeSelectedListener != null) shapeSelectedListener.onShapeSelected(null);
                    repaint();
                }
            }
        });

        // 삭제 키 이벤트 수신을 위해 포커스 요청 추가
        addHierarchyListener(e -> {
            if (isShowing()) {
                requestFocusInWindow();
            }
        });

        MouseAdapter mouseHandler = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                requestFocusInWindow();
                startX = e.getX();
                startY = e.getY();
                dragging = true;

                boolean shiftPressed = (e.getModifiersEx() & InputEvent.SHIFT_DOWN_MASK) != 0;

                if (clickedAny && !selectedShapes.isEmpty()) {      // resize handler 눌렀는지 확인
                    for (DrawableShape shape : selectedShapes) {
                        selectedResizeHandler = shape.resizeContains(startX, startY);
                        if (selectedResizeHandler != NONE) break;
                    }
                }

                if (selectedResizeHandler == NONE) {
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

                            if (shapeSelectedListener != null)
                                shapeSelectedListener.onShapeSelected(shape);
                            break;
                        }
                    }
                }

                if (!clickedAny && !shiftPressed) {     //마우스 눌렀는데 도형 선택도 안하고 shift도 안 누르면
                    for (DrawableShape s : selectedShapes) s.setSelected(false);
                    selectedShapes.clear();
                    selectedResizeHandler = NONE;
                    if (shapeSelectedListener != null)
                        shapeSelectedListener.onShapeSelected(null);
                }

                prevMouseX = startX;
                prevMouseY = startY;

                repaint();
            }

            public void mouseDragged(MouseEvent e) {
                if (!selectedShapes.isEmpty() && selectedResizeHandler == NONE) {
                    int dx = e.getX() - prevMouseX;
                    int dy = e.getY() - prevMouseY;
                    for (DrawableShape shape : selectedShapes) {
                        shape.moveBy(dx, dy);
                        if (shapeSelectedListener != null) {        //속성창 실시간 업데이트
                            shapeSelectedListener.onShapeSelected(shape);
                        }
                    }
                    prevMouseX = e.getX();
                    prevMouseY = e.getY();
                    repaint();
                }

                if (!selectedShapes.isEmpty() && selectedResizeHandler != NONE) {
                    int dx = e.getX() - prevMouseX;
                    int dy = e.getY() - prevMouseY;
                    for (DrawableShape shape : selectedShapes) {
                        shape.resize(dx, dy, selectedResizeHandler);
                        if (shapeSelectedListener != null) {        //속성창 실시간 업데이트
                            shapeSelectedListener.onShapeSelected(shape);
                        }
                    }
                    prevMouseX = e.getX();
                    prevMouseY = e.getY();
                    repaint();
                }
            }

            public void mouseReleased(MouseEvent e) {
                dragging = false;

                if (selectedShapes.isEmpty()) {  //선택된 도형 없으면
                    DrawableShape shape = null;
                    switch (currentShapeType) {
                        case RECTANGLE:
                            shape = new RectangleShape(startX, startY, e.getX(), e.getY(), selectedColor);
                            break;
                        case LINE:
                            shape = new LineShape(startX, startY, e.getX(), e.getY(), selectedColor);
                            break;
                        case ELLIPSE:
                            shape = new EllipseShape(startX, startY, e.getX(), e.getY(), selectedColor);
                            break;
                    }
                    if (shape != null) {
                        shapes.add(shape);
                        if (shapeSelectedListener != null)
                            shapeSelectedListener.onShapeSelected(shape);
                    }
                }
                repaint();
            }
        };

        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
    }

    public void setShapeType(ShapeType type) {
        currentShapeType = type;
    }

    public void addShapeSelectedListener(ShapeSelectedListener listener) {
        this.shapeSelectedListener = listener;
    }


    public void selectAll() {
        selectedShapes.clear();
        for (DrawableShape shape : shapes) {
            shape.setSelected(true);
            selectedShapes.add(shape);
        }
        if (shapeSelectedListener != null && !selectedShapes.isEmpty()) {
            shapeSelectedListener.onShapeSelected(selectedShapes.get(0));
        }
        repaint();
    }

    public void updateWidth(int width) {
        for (DrawableShape shape : selectedShapes) {
            shape.setWidth(width);
        }
        repaint();
    }

    public void updateHeight(int height) {
        for (DrawableShape shape : selectedShapes) {
            shape.setHeight(height);
        }
        repaint();
    }

    public void updateColor(Color color) {
        if (!selectedShapes.isEmpty()) {
            selectedColor = color;
            for (DrawableShape shape : selectedShapes) {
                shape.setColor(selectedColor);
            }
        } else {
            selectedColor = color;
        }
        repaint();
    }

    public void bringToFront() {
        if (selectedShapes.isEmpty()) return;

        for (DrawableShape shape : selectedShapes) {
            int index = shapes.indexOf(shape);
            if (index < shapes.size() - 1){
                Collections.swap(shapes, index, index+1);
            }
        }
        repaint();
    }

    public void sendToBack() {
        if (selectedShapes.isEmpty()) return;

        for (DrawableShape shape : selectedShapes) {
            int index = shapes.indexOf(shape);
            if (index > 0) {
                Collections.swap(shapes, index, index - 1);
            }
        }
        repaint();
    }

    public void deleteShape() {
        for (DrawableShape shape : selectedShapes) {
            shapes.remove(shape);
        }
        selectedShapes.clear();

        if (shapeSelectedListener != null) {
            shapeSelectedListener.onShapeSelected(null); // 속성창 초기화
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (DrawableShape shape : shapes) {
            shape.draw(g);
        }
    }
}
