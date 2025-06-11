package drawingapp.shapes;

import drawingapp.ResizeHandle;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static drawingapp.ResizeHandle.*;

public class RectangleShape implements DrawableShape {
    private int x, y, width, height;
    private boolean selected = false;
    private Color color;
    private final float strokeWidth = 3.0F;
    private final int handleSize = 8;
    private final int id;

    public RectangleShape(int x1, int y1, int x2, int y2, Color color) {
        this.x = Math.min(x1, x2);
        this.y = Math.min(y1, y2);
        this.width = Math.abs(x2 - x1);
        this.height = Math.abs(y2 - y1);
        this.color = color;
        this.id = IdGenerator.getNextId();
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(strokeWidth));
        //g2d.draw(new Rectangle2D.Float(x, y, width, height));
        g2d.drawRect(x, y, width, height);

        if (selected) {
            g2d.setColor(new Color(0, 120, 215)); // selection box 표시
            g2d.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                    1f, new float[]{5f, 5f}, 0)); // 점선
            g2d.drawRect(x - handleSize / 2, y - handleSize / 2, width + handleSize, height + handleSize); // 선택 상자 그리기 (약간 크게)

            g2d.setColor(Color.DARK_GRAY);
            g2d.fillRect(x + width / 2 - handleSize / 2 , y - handleSize / 2, handleSize, handleSize); //  (TOP);
            g2d.fillRect(x + width / 2 - handleSize / 2 , y + height - handleSize / 2 , handleSize, handleSize); //  (BOTTOM);
            g2d.fillRect(x - handleSize / 2, y + height / 2 - handleSize / 2, handleSize, handleSize); //  (LEFT);
            g2d.fillRect(x + width - handleSize / 2, y + height / 2 - handleSize / 2, handleSize, handleSize); //  (RIGHT);
            g2d.fillRect(x - handleSize / 2, y - handleSize / 2, handleSize, handleSize); // 좌상단 (TOP_LEFT);
            g2d.fillRect(x + width - handleSize / 2, y - handleSize / 2, handleSize, handleSize); // 우상단 (TOP_RIGHT)
            g2d.fillRect(x - handleSize / 2, y + height - handleSize / 2, handleSize, handleSize); // 좌하단 (BOTTOM_LEFT)
            g2d.fillRect(x + width - handleSize / 2, y + height - handleSize / 2, handleSize, handleSize); // 우하단 (BOTTOM_RIGHT)
        }
    }

    @Override
    public Map<ResizeHandle, Rectangle> getHandleBounds() {
        int halfSize = handleSize / 2;
        Map<ResizeHandle, Rectangle> map = new HashMap<>();
        map.put(TOP, new Rectangle(x + width / 2 - halfSize , y - halfSize, handleSize, handleSize));
        map.put(BOTTOM, new Rectangle(x + width / 2 - halfSize , y + height - halfSize , handleSize, handleSize));
        map.put(LEFT, new Rectangle(x - halfSize, y + height / 2 - halfSize, handleSize, handleSize));
        map.put(RIGHT, new Rectangle(x + width - halfSize, y + height / 2 - halfSize, handleSize, handleSize));
        map.put(TOP_LEFT, new Rectangle(x - halfSize, y - halfSize, handleSize, handleSize));
        map.put(TOP_RIGHT, new Rectangle(x + width - halfSize, y - halfSize, handleSize, handleSize));
        map.put(BOTTOM_LEFT, new Rectangle(x - halfSize, y + height - halfSize, handleSize, handleSize));
        map.put(BOTTOM_RIGHT, new Rectangle(x + width - halfSize, y + height - halfSize, handleSize, handleSize));
        return map;
    }

    @Override
    public boolean contains(int px, int py) {
        return px >= x && px <= x + width && py >= y && py <= y + height;
    }

    @Override
    public ResizeHandle resizeContains(int px, int py) {
        for (Map.Entry<ResizeHandle, Rectangle> entry : getHandleBounds().entrySet()) {
            if (entry.getValue().contains(px, py)) {
                return entry.getKey();
            }
        }
        return ResizeHandle.NONE;
    }

    @Override
    public void resize(int dx, int dy, ResizeHandle selectedResizeHandler) {
        switch (selectedResizeHandler) {
            case TOP -> {
                this.y = y + dy; this.height = height - dy;
            }
            case BOTTOM -> {
                this.height = height + dy;
            }
            case LEFT -> {
                this.x = x + dx; this.width = width - dx;
            }
            case RIGHT -> {
                this.width = width + dx;
            }
            case TOP_LEFT -> {
                this.x = x + dx; this.width = width - dx;
                this.y = y + dy; this.height = height - dy;
            }
            case TOP_RIGHT -> {
                this.width = width + dx;
                this.y = y + dy; this.height = height - dy;
            }
            case BOTTOM_LEFT -> {
                this.x = x + dx; this.width = width - dx;
                this.height = height + dy;
            }
            case BOTTOM_RIGHT -> {
                this.width = width + dx;
                this.height = height + dy;
            }
        }
    }

    @Override
    public int getId() {
        return id;
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
