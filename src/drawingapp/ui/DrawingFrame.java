package drawingapp.ui;

import javax.swing.*;
import java.awt.*;
import drawingapp.ShapeSelectedListener;
import drawingapp.shapes.DrawableShape;

public class DrawingFrame extends JFrame {
    public DrawingFrame() {
        setTitle("벡터 그래픽 에디터");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 중앙 정렬

        // 전체 레이아웃 패널
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(45, 45, 45));

        // 드로잉 패널
        DrawingPanel drawingPanel = new DrawingPanel();
        drawingPanel.setPreferredSize(new Dimension(750, 700));
        drawingPanel.setBackground(Color.WHITE);

        // 속성 패널 (생성자에 drawingPanel 전달)
        PropertyPanel propertyPanel = new PropertyPanel(drawingPanel);

        // 도형 선택 시 PropertyPanel 업데이트 연결
        drawingPanel.addShapeSelectedListener(new ShapeSelectedListener() {
            @Override
            public void onShapeSelected(DrawableShape shape) {
                propertyPanel.updateProperties(shape);
            }
        });

        // 툴바
        DrawingToolBar toolBar = new DrawingToolBar(drawingPanel);
        toolBar.setBackground(new Color(60, 60, 60));
        toolBar.setPreferredSize(new Dimension(1000, 50));
        int a = 7;

        // 배치
        mainPanel.add(toolBar, BorderLayout.NORTH);
        mainPanel.add(drawingPanel, BorderLayout.CENTER);
        mainPanel.add(propertyPanel, BorderLayout.EAST);

        add(mainPanel);
        setVisible(true);

        // 키 이벤트를 위해 포커스 설정
        drawingPanel.requestFocusInWindow();
    }
}
