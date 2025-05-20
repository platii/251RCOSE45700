package drawingapp.ui;

import javax.swing.*;
import java.awt.*;
import drawingapp.ShapeSelectedListener;
import drawingapp.shapes.DrawableShape;

public class DrawingFrame extends JFrame {
    public DrawingPanel drawingPanel;
    public PropertyPanel propertyPanel;
    public DrawingToolBar toolBar;
    public JPanel mainPanel;

    public DrawingFrame(DrawingPanel drawingPanel, PropertyPanel propertyPanel, DrawingToolBar toolBar) {
        this.drawingPanel = drawingPanel;
        this.propertyPanel = propertyPanel;
        this.toolBar = toolBar;
        // 프레임 설정
        initFrame();

        // 전체 레이아웃 패널
        initMainPanel();

        // 드로잉 패널
        initDrawingPanel();

        // 도형 선택 시 PropertyPanel 업데이트 연결
        initListener();

        // 툴바
        initToolBar();

        // 배치
        mainPanel.add(toolBar, BorderLayout.NORTH);
        mainPanel.add(drawingPanel, BorderLayout.CENTER);
        mainPanel.add(propertyPanel, BorderLayout.EAST);

        add(mainPanel);
        setVisible(true);

        // 키 이벤트를 위해 포커스 설정
        drawingPanel.requestFocusInWindow();
    }

    private void initFrame() {
        setTitle("벡터 그래픽 에디터");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // 중앙 정렬
    }

    private void initMainPanel() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(45, 45, 45));
    }

    private void initDrawingPanel() {
        drawingPanel.setPreferredSize(new Dimension(750, 700));
        drawingPanel.setBackground(Color.WHITE);
    }

    private void initToolBar() {
        toolBar.setBackground(new Color(60, 60, 60));
        toolBar.setPreferredSize(new Dimension(1000, 50));
    }

    private void initListener() {
        drawingPanel.addShapeSelectedListener(new ShapeSelectedListener() {
            @Override
            public void onShapeSelected(DrawableShape shape) {
                propertyPanel.updateProperties(shape);
            }
        });
    }
}
