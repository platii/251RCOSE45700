package drawingapp.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import drawingapp.DrawObserver;
import drawingapp.controller.ShapeController;
import drawingapp.shapes.*;


public class DrawingPanel extends JPanel implements DrawObserver {
    ShapeManager model;
    ShapeController controller;

    public DrawingPanel(ShapeManager model, ShapeController controller) {
        this.model = model;
        this.controller = controller;
        model.registerObserver(this);
        initPanel();
        initKeyListener();
        initMouseListener();
    }

    private void initPanel() {
        setBackground(Color.WHITE); // 흰색 배경
        setFocusable(true);
        // 삭제 키 이벤트 수신을 위해 포커스 요청 추가
        addHierarchyListener(e -> {
            if (isShowing()) {
                requestFocusInWindow();
            }
        });
    }

    private void initKeyListener() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                controller.pressDelete(e);
            }
        });
    }

    private void initMouseListener() {
        MouseAdapter mouseHandler = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                requestFocusInWindow();
                boolean shiftPressed = (e.getModifiersEx() & InputEvent.SHIFT_DOWN_MASK) != 0;
                controller.downClick(e.getX(), e.getY(), shiftPressed);

            }

            public void mouseDragged(MouseEvent e) {
                controller.drag(e.getX(), e.getY());

            }

            public void mouseReleased(MouseEvent e) {
                controller.upClick(e.getX(), e.getY());
            }
        };

        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
    }

    @Override
    public void updateCanvas() {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (DrawableShape shape : model.getShapes()) {
            shape.draw(g);
        }
    }
}
