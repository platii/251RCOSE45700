// Z-order 조절 기능을 위한 DrawingPanel.java 수정 예시

package drawingapp.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import drawingapp.ShapeType;
import drawingapp.ShapeSelectedListener;
import drawingapp.shapes.*;

public class DrawingPanel extends JPanel {
    private ArrayList<DrawableShape> shapes = new ArrayList<>();
    private ArrayList<DrawableShape> selectedShapes = new ArrayList<>();
    private ShapeType currentShapeType = ShapeType.RECTANGLE;
    private int startX, startY, prevMouseX, prevMouseY;
    private boolean dragging = false;
    private ShapeSelectedListener shapeSelectedListener;

    public DrawingPanel() {
        setBackground(Color.WHITE); // 흰색 배경
        setFocusable(true);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                char key = e.getKeyChar();
                if (key == 'r') currentShapeType = ShapeType.RECTANGLE;
                else if (key == 'l') currentShapeType = ShapeType.LINE;
                else if (key == 'e') currentShapeType = ShapeType.ELLIPSE;

                if (e.isControlDown() && key == 'a') {
                    selectAll();
                }

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
                boolean clickedAny = false;

                for (int i = shapes.size() - 1; i >= 0; i--) {
                    DrawableShape shape = shapes.get(i);
                    if (shape.contains(startX, startY)) {
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
                            for (DrawableShape s : selectedShapes) s.setSelected(false);
                            selectedShapes.clear();
                            selectedShapes.add(shape);
                            shape.setSelected(true);
                        }
                        if (shapeSelectedListener != null)
                            shapeSelectedListener.onShapeSelected(shape);
                        break;
                    }
                }

                if (!clickedAny && !shiftPressed) {
                    for (DrawableShape s : selectedShapes) s.setSelected(false);
                    selectedShapes.clear();
                    if (shapeSelectedListener != null)
                        shapeSelectedListener.onShapeSelected(null);
                }

                prevMouseX = startX;
                prevMouseY = startY;

                repaint();
            }

            public void mouseDragged(MouseEvent e) {
                if (!selectedShapes.isEmpty()) {
                    int dx = e.getX() - prevMouseX;
                    int dy = e.getY() - prevMouseY;
                    for (DrawableShape shape : selectedShapes) {
                        shape.moveBy(dx, dy);
                    }
                    prevMouseX = e.getX();
                    prevMouseY = e.getY();
                    repaint();
                }
            }

            public void mouseReleased(MouseEvent e) {
                dragging = false;
                if (selectedShapes.isEmpty()) {
                    DrawableShape shape = null;
                    switch (currentShapeType) {
                        case RECTANGLE:
                            shape = new RectangleShape(startX, startY, e.getX(), e.getY());
                            break;
                        case LINE:
                            shape = new LineShape(startX, startY, e.getX(), e.getY());
                            break;
                        case ELLIPSE:
                            shape = new EllipseShape(startX, startY, e.getX(), e.getY());
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

    public void setSelectedColor(Color color) {
        for (DrawableShape shape : selectedShapes) {
            shape.setColor(color);
        }
        repaint();
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
        for (DrawableShape shape : selectedShapes) {
            shape.setColor(color);
        }
        repaint();
    }

    public void bringToFront() {
        for (DrawableShape shape : selectedShapes) {
            shapes.remove(shape);
            shapes.add(shape);
        }
        repaint();
    }

    public void sendToBack() {
        for (DrawableShape shape : selectedShapes) {
            shapes.remove(shape);
            shapes.add(0, shape);
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
