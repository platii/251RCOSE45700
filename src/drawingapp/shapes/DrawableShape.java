package drawingapp.shapes;

import drawingapp.ResizeHandle;

import java.awt.*;
import java.util.List;
import java.util.Map;

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

    int getX();
    int getY();

    Map<ResizeHandle, Rectangle> getHandleBounds();
    ResizeHandle resizeContains(int px, int py);
    void resize(int dx, int dy, ResizeHandle selectedResizeHandler);

}
