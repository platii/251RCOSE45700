// PropertyPanel.java - 도형 속성 표시 기능 포함

package drawingapp.ui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import drawingapp.PropertyObserver;
import drawingapp.controller.ShapeController;
import drawingapp.shapes.DrawableShape;
import drawingapp.shapes.ShapeManager;

public class PropertyPanel extends JPanel implements PropertyObserver {
    private JLabel typeLabel;
    private JLabel positionLabel;
    private JTextField widthField, heightField;
    private JColorChooser colorChooser;
    ShapeController controller;
    ShapeManager model;

    public PropertyPanel(ShapeManager model, ShapeController controller) {
        this.controller = controller;
        this.model = model;
        model.registerObserver(this);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setPreferredSize(new Dimension(250, 600));
        this.setBackground(new Color(240, 240, 240));

        initLabel();
    }

    private void initLabel() {
        // 도형 타입
        typeLabel = new JLabel(" ");
        typeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.add(typeLabel);

        // 위치
        positionLabel = new JLabel(" ");
        positionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.add(positionLabel);

        // 크기
        this.add(new JLabel("Width:"));
        widthField = new JTextField();
        widthField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        widthField.addActionListener(e -> updateWidth());
        this.add(widthField);

        this.add(new JLabel("Height:"));
        heightField = new JTextField();
        heightField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        heightField.addActionListener(e -> updateHeight());
        this.add(heightField);

        // 색상 선택기
        this.add(new JLabel("Color:"));
        colorChooser = new JColorChooser();
        colorChooser.getSelectionModel().addChangeListener(e -> updateColor());
        colorChooser.setPreviewPanel(new JPanel()); // 간단화
        colorChooser.setMaximumSize(new Dimension(240, 300));
        this.add(colorChooser);
    }

    private void updateWidth() {
        try {
            int width = Integer.parseInt(widthField.getText());
            controller.updateWidth(width);
        } catch (NumberFormatException ignored) {}
    }

    private void updateHeight() {
        try {
            int height = Integer.parseInt(heightField.getText());
            controller.updateHeight(height);
        } catch (NumberFormatException ignored) {}
    }

    private void updateColor() {
        controller.updateColor(colorChooser.getColor());
    }

    public void updateProperties() {
        List<DrawableShape> shapes = model.getSelectedShapes();
        if (shapes.isEmpty()) {
            typeLabel.setText(" ");
            positionLabel.setText(" ");
            widthField.setText("");
            heightField.setText("");
            return;
        }

        DrawableShape shape = shapes.get(0);
        // 도형 타입명
        typeLabel.setText("Type: " + shape.getClass().getSimpleName());

        // 위치 (x, y)
        int x = 0, y = 0;
        if (shape instanceof drawingapp.shapes.RectangleShape) {
            drawingapp.shapes.RectangleShape r = (drawingapp.shapes.RectangleShape) shape;
            x = r.getX(); y = r.getY();
        } else if (shape instanceof drawingapp.shapes.EllipseShape) {
            drawingapp.shapes.EllipseShape e = (drawingapp.shapes.EllipseShape) shape;
            x = e.getX(); y = e.getY();
        } // Line은 위치 제공 안 함

        positionLabel.setText("Position: (" + x + ", " + y + ")");

        widthField.setText(String.valueOf(shape.getWidth()));
        heightField.setText(String.valueOf(shape.getHeight()));
    }

}
