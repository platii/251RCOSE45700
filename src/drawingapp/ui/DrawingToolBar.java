package drawingapp.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import drawingapp.ShapeType;

public class DrawingToolBar extends JPanel {
    public DrawingToolBar(DrawingPanel drawingPanel) {
        this.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton selectBtn = new JButton("선택");
        JButton rectBtn = new JButton("사각형");
        JButton lineBtn = new JButton("선");
        JButton ellipseBtn = new JButton("타원");
        JButton selectAllBtn = new JButton("전체 선택");
        JButton deleteBtn = new JButton("삭제");

        // 도형 유형 변경
        selectBtn.addActionListener(e -> drawingPanel.setShapeType(ShapeType.SELECT));
        rectBtn.addActionListener(e -> drawingPanel.setShapeType(ShapeType.RECTANGLE));
        lineBtn.addActionListener(e -> drawingPanel.setShapeType(ShapeType.LINE));
        ellipseBtn.addActionListener(e -> drawingPanel.setShapeType(ShapeType.ELLIPSE));

        // 전체 선택
        selectAllBtn.addActionListener((ActionEvent e) -> drawingPanel.selectAll());

        // 삭제 키 이벤트 전송
        deleteBtn.addActionListener((ActionEvent e) -> drawingPanel.deleteShape());

        this.add(selectBtn);
        this.add(rectBtn);
        this.add(lineBtn);
        this.add(ellipseBtn);
        this.add(selectAllBtn);
        this.add(deleteBtn);

        JButton bringToFrontBtn = new JButton("위로");
        JButton sendToBackBtn = new JButton("아래로");

        bringToFrontBtn.addActionListener(e -> drawingPanel.bringToFront());
        sendToBackBtn.addActionListener(e -> drawingPanel.sendToBack());

        this.add(bringToFrontBtn);
        this.add(sendToBackBtn);
    }
}
