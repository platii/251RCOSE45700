package drawingapp.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import drawingapp.ShapeType;

public class DrawingToolBar extends JPanel {
    public DrawingPanel drawingPanel;

    public DrawingToolBar(DrawingPanel drawingPanel) {
        this.drawingPanel = drawingPanel;
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        initBtn();
    }

    private void initBtn() {
        JButton selectBtn = new JButton("선택");
        JButton rectBtn = new JButton("사각형");
        JButton lineBtn = new JButton("선");
        JButton ellipseBtn = new JButton("타원");
        JButton selectAllBtn = new JButton("전체 선택");
        JButton deleteBtn = new JButton("삭제");
        JButton bringToFrontBtn = new JButton("위로");
        JButton sendToBackBtn = new JButton("아래로");

        // 도형 유형 변경
        selectBtn.addActionListener(e -> drawingPanel.setShapeType(ShapeType.SELECT));
        rectBtn.addActionListener(e -> drawingPanel.setShapeType(ShapeType.RECTANGLE));
        lineBtn.addActionListener(e -> drawingPanel.setShapeType(ShapeType.LINE));
        ellipseBtn.addActionListener(e -> drawingPanel.setShapeType(ShapeType.ELLIPSE));

        // 전체 선택
        selectAllBtn.addActionListener((ActionEvent e) -> drawingPanel.selectAll());
        selectAllBtn.addActionListener(e -> drawingPanel.setShapeType(ShapeType.SELECT));

        // 삭제
        deleteBtn.addActionListener((ActionEvent e) -> drawingPanel.deleteShape());
        deleteBtn.addActionListener(e -> drawingPanel.setShapeType(ShapeType.SELECT));

        //z-index 변경
        bringToFrontBtn.addActionListener(e -> drawingPanel.bringToFront());
        bringToFrontBtn.addActionListener(e -> drawingPanel.setShapeType(ShapeType.SELECT));
        sendToBackBtn.addActionListener(e -> drawingPanel.sendToBack());
        sendToBackBtn.addActionListener(e -> drawingPanel.setShapeType(ShapeType.SELECT));

        this.add(selectBtn);
        this.add(rectBtn);
        this.add(lineBtn);
        this.add(ellipseBtn);
        this.add(selectAllBtn);
        this.add(deleteBtn);
        this.add(bringToFrontBtn);
        this.add(sendToBackBtn);
    }
}
