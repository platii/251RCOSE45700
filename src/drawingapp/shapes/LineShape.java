package drawingapp.shapes;

import java.awt.Color;
import java.awt.Graphics;

public class LineShape implements DrawableShape {
    private int x1, y1, x2, y2;
    private boolean selected = false;
    private Color color = Color.BLACK;

    public LineShape(int x1, int y1, int x2, int y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(selected ? Color.RED : color);
        g.drawLine(x1, y1, x2, y2);
    }

    @Override
    public boolean contains(int px, int py) {
        final double TOLERANCE = 3.0;
        double dx = x2 - x1, dy = y2 - y1;
        if (dx == 0 && dy == 0) return Math.hypot(px - x1, py - y1) <= TOLERANCE;

        double len = Math.hypot(dx, dy);
        double dot = ((px - x1) * dx + (py - y1) * dy) / (len * len);
        if (dot < 0 || dot > 1) return false;

        double closestX = x1 + dot * dx;
        double closestY = y1 + dot * dy;
        return Math.hypot(px - closestX, py - closestY) <= TOLERANCE;
    }

    @Override
    public void moveBy(int dx, int dy) {
        x1 += dx; y1 += dy;
        x2 += dx; y2 += dy;
    }

    @Override
    public void moveTo(int x, int y) {
        int dx = x - x1;
        int dy = y - y1;
        moveBy(dx, dy);
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
        LineShape copy = new LineShape(x1 + 10, y1 + 10, x2 + 10, y2 + 10);
        copy.setColor(this.color);
        return copy;
    }

    @Override
    public int getWidth() {
        return Math.abs(x2 - x1);
    }

    @Override
    public int getHeight() {
        return Math.abs(y2 - y1);
    }

    @Override
    public void setWidth(int width) {
        // 너비 조정은 선의 방향에 따라 의미 없음 → 생략
    }

    @Override
    public void setHeight(int height) {
        // 높이 조정도 생략
    }

    @Override
    public int getX() {
        return Math.min(x1, x2);  // 시작점 중 작은 x를 기준으로
    }

    @Override
    public int getY() {
        return Math.min(y1, y2);  // 시작점 중 작은 y를 기준으로
    }

}
