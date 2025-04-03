package drawingapp.shapes;

import java.awt.Color;
import java.awt.Graphics;

public class EllipseShape implements DrawableShape {
    private int x, y, width, height;
    private boolean selected = false;
    private Color color = Color.GREEN;

    public EllipseShape(int x1, int y1, int x2, int y2) {
        this.x = Math.min(x1, x2);
        this.y = Math.min(y1, y2);
        this.width = Math.abs(x2 - x1);
        this.height = Math.abs(y2 - y1);
    }

    public static EllipseShape fromXYWH(int x, int y, int width, int height) {
        EllipseShape e = new EllipseShape(0, 0, 0, 0);
        e.x = x;
        e.y = y;
        e.width = width;
        e.height = height;
        return e;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(selected ? Color.RED : color);
        g.drawOval(x, y, width, height);
    }

    @Override
    public boolean contains(int px, int py) {
        double dx = (px - (x + width / 2.0)) / (width / 2.0);
        double dy = (py - (y + height / 2.0)) / (height / 2.0);
        return dx * dx + dy * dy <= 1.0;
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
        EllipseShape copy = EllipseShape.fromXYWH(x + 10, y + 10, width, height);
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
