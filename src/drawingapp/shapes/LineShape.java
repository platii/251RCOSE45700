package drawingapp.shapes;

import drawingapp.ResizeHandle;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.HashMap;
import java.util.Map;

import static drawingapp.ResizeHandle.*;

public class LineShape implements DrawableShape {
    private int x1, y1, x2, y2;
    private boolean selected = false;
    private Color color = Color.BLACK;
    private final float strokeWidth = 3.0F;
    private final int handleSize = 8;

    public LineShape(int x1, int y1, int x2, int y2, Color color) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.color = color;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(strokeWidth));
        //g2d.draw(new Line2D.Float(x1, y1, x2, y2));
        g2d.drawLine(x1, y1, x2, y2);

        if (selected) {
            int width = Math.abs(x2 - x1);
            int height = Math.abs(y2 - y1);
            g2d.setColor(new Color(0, 120, 215)); // 파란 테두리
            g2d.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                    1f, new float[]{5f, 5f}, 0)); // 점선
            g2d.drawRect(Math.min(x1,x2) - handleSize / 2, Math.min(y1,y2) - handleSize / 2, width + handleSize, height + handleSize); // 약간 크게

            g2d.setColor(Color.DARK_GRAY);
            g2d.fillRect(Math.min(x1, x2) + width / 2 - handleSize / 2 , Math.min(y1, y2) - handleSize / 2, handleSize, handleSize); //  (TOP);
            g2d.fillRect(Math.min(x1, x2) + width / 2 - handleSize / 2 , Math.min(y1, y2) + height - handleSize / 2 , handleSize, handleSize); //  (BOTTOM);
            g2d.fillRect(Math.min(x1, x2) - handleSize / 2, Math.min(y1, y2) + height / 2 - handleSize / 2, handleSize, handleSize); //  (LEFT);
            g2d.fillRect(Math.min(x1, x2) + width - handleSize / 2, Math.min(y1, y2) + height / 2 - handleSize / 2, handleSize, handleSize); //  (RIGHT);
            g2d.fillRect(Math.min(x1,x2) - handleSize / 2, Math.min(y1,y2) - handleSize / 2, handleSize, handleSize); // 좌상단
            g2d.fillRect(Math.max(x1,x2) - handleSize / 2, Math.min(y1,y2) - handleSize / 2, handleSize, handleSize); // 우상단
            g2d.fillRect(Math.min(x1,x2) - handleSize / 2, Math.max(y1,y2) - handleSize / 2, handleSize, handleSize); // 좌하단
            g2d.fillRect(Math.max(x1,x2) - handleSize / 2, Math.max(y1,y2) - handleSize / 2, handleSize, handleSize); // 우하단
        }
    }

    @Override
    public Map<ResizeHandle, Rectangle> getHandleBounds() {     // 사용x
        int halfSize = handleSize / 2;
        Map<ResizeHandle, Rectangle> map = new HashMap<>();
        map.put(ResizeHandle.TOP_LEFT, new Rectangle(x1 - halfSize, y1 - halfSize, handleSize, handleSize));
        map.put(ResizeHandle.TOP_RIGHT, new Rectangle(x1 + x2 - halfSize, y1 - halfSize, handleSize, handleSize));
        map.put(ResizeHandle.BOTTOM_LEFT, new Rectangle(x1 - halfSize, y1 + y2 - halfSize, handleSize, handleSize));
        map.put(ResizeHandle.BOTTOM_RIGHT, new Rectangle(x1 + x2 - halfSize, y1 + y2 - halfSize, handleSize, handleSize));
        return map;
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
    public ResizeHandle resizeContains(int px, int py) {    //생략
        return NONE;
    }

    @Override
    public void resize(int dx, int dy, ResizeHandle selectedResizeHandler){
        //생략
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
