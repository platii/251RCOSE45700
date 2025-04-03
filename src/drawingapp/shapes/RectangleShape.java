package drawingapp.shapes;

import java.awt.Color;
import java.awt.Graphics;

public class RectangleShape implements DrawableShape {
    private int x, y, width, height;
    private boolean selected = false;
    private Color color = Color.BLUE;

    public RectangleShape(int x1, int y1, int x2, int y2) {
        this.x = Math.min(x1, x2);
        this.y = Math.min(y1, y2);
        this.width = Math.abs(x2 - x1);
        this.height = Math.abs(y2 - y1);
    }

    public static RectangleShape fromXYWH(int x, int y, int width, int height) {
        RectangleShape r = new RectangleShape(0, 0, 0, 0);
        r.x = x;
        r.y = y;
        r.width = width;
        r.height = height;
        return r;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(selected ? Color.RED : color);
        g.drawRect(x, y, width, height);
    }

    @Override
    public boolean contains(int px, int py) {
        return px >= x && px <= x + width && py >= y && py <= y + height;
    }

    @Override
    public void moveBy(int dx, int dy) {
        x += dx;
        y += dy;
    }

    @Override
    public void moveTo(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public boolean isSelected() {
        return selected;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public DrawableShape cloneShape() {
        RectangleShape copy = RectangleShape.fromXYWH(x + 10, y + 10, width, height);
        copy.setColor(this.color);
        return copy;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setWidth(int width) {
        this.width = Math.max(1, width);
    }

    @Override
    public void setHeight(int height) {
        this.height = Math.max(1, height);
    }
    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }
}
