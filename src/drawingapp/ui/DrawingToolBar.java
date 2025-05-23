package drawingapp.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import drawingapp.ShapeType;
import drawingapp.controller.ShapeController;

public class DrawingToolBar extends JPanel {
    ShapeController controller;

    public DrawingToolBar(ShapeController controller) {
        this.controller = controller;
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
        selectBtn.addActionListener(e -> controller.changeType(ShapeType.SELECT));
        rectBtn.addActionListener(e -> controller.changeType(ShapeType.RECTANGLE));
        lineBtn.addActionListener(e -> controller.changeType(ShapeType.LINE));
        ellipseBtn.addActionListener(e -> controller.changeType(ShapeType.ELLIPSE));

        // 전체 선택
        selectAllBtn.addActionListener((ActionEvent e) -> controller.selectAll());
        selectAllBtn.addActionListener(e -> controller.changeType(ShapeType.SELECT));

        // 삭제
        deleteBtn.addActionListener((ActionEvent e) -> controller.deleteShape());
        deleteBtn.addActionListener(e -> controller.changeType(ShapeType.SELECT));

        //z-index 변경
        bringToFrontBtn.addActionListener(e -> controller.bringToFront());
        bringToFrontBtn.addActionListener(e -> controller.changeType(ShapeType.SELECT));
        sendToBackBtn.addActionListener(e -> controller.sendToBack());
        sendToBackBtn.addActionListener(e -> controller.changeType(ShapeType.SELECT));

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
