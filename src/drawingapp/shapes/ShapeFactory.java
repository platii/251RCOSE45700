package drawingapp.shapes;

import drawingapp.ShapeType;

import java.awt.*;

public class ShapeFactory {

    public DrawableShape createShape(ShapeType shapeType, int x1, int y1, int x2, int y2, Color color) {

        switch (shapeType) {
            case RECTANGLE:
                return new RectangleShape(x1, y1, x2, y2, color);
            case LINE:
                return new LineShape(x1, y1, x2, y2, color);
            case ELLIPSE:
                return new EllipseShape(x1, y1, x2, y2, color);
        }
        return null;
    }
}
