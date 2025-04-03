package drawingapp.shapes;

import java.awt.Color;
import java.awt.Graphics;

public interface DrawableShape {
    void draw(Graphics g);
    void moveTo(int x, int y);
    void moveBy(int dx, int dy);
    boolean contains(int x, int y);

    int getWidth();
    int getHeight();
    void setWidth(int width);
    void setHeight(int height);

    Color getColor();
    void setColor(Color color);

    void setSelected(boolean selected);
    boolean isSelected();

    DrawableShape cloneShape();
    int getX();
    int getY();

}
