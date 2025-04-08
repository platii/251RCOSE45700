package drawingapp.shapes;

import drawingapp.ResizeHandle;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.HashMap;
import java.util.Map;

import static drawingapp.ResizeHandle.*;

public class EllipseShape implements DrawableShape {
    private int x, y, width, height;
    private boolean selected = false;
    private Color color= Color.BLACK;
    private float strokeWidth = 3.0F;
    private final int handleSize = 8;


    public EllipseShape(int x1, int y1, int x2, int y2, Color color) {
        this.x = Math.min(x1, x2);
        this.y = Math.min(y1, y2);
        this.width = Math.abs(x2 - x1);
        this.height = Math.abs(y2 - y1);
        this.color = color;
    }

    public static EllipseShape fromXYWH(int x, int y, int width, int height, Color color) {
        EllipseShape e = new EllipseShape(0, 0, 0, 0, color);
        e.x = x;
        e.y = y;
        e.width = width;
        e.height = height;
        e.color = color;
        return e;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(color);
        g2d.setStroke(new BasicStroke(strokeWidth));
        //g2d.draw(new Ellipse2D.Float(x, y, width, height));
        g2d.drawOval(x, y, width, height);

        if (selected) {
            g2d.setColor(new Color(0, 120, 215));
            g2d.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
                    1f, new float[]{5f, 5f}, 0));
            g2d.drawRect(x - handleSize / 2, y - handleSize / 2, width + handleSize, height + handleSize);

            int handleSize = 6;
            g2d.setColor(Color.DARK_GRAY);
            g2d.fillRect(x + width / 2 - handleSize / 2 , y - handleSize / 2, handleSize, handleSize); //  (TOP);
            g2d.fillRect(x + width / 2 - handleSize / 2 , y + height - handleSize / 2 , handleSize, handleSize); //  (BOTTOM);
            g2d.fillRect(x - handleSize / 2, y + height / 2 - handleSize / 2, handleSize, handleSize); //  (LEFT);
            g2d.fillRect(x + width - handleSize / 2, y + height / 2 - handleSize / 2, handleSize, handleSize); //  (RIGHT);
            g2d.fillRect(x - handleSize / 2, y - handleSize / 2, handleSize, handleSize); // 좌상단
            g2d.fillRect(x + width - handleSize / 2, y - handleSize / 2, handleSize, handleSize); // 우상단
            g2d.fillRect(x - handleSize / 2, y + height - handleSize / 2, handleSize, handleSize); // 좌하단
            g2d.fillRect(x + width - handleSize / 2, y + height - handleSize / 2, handleSize, handleSize); // 우하단
        }
    }

    @Override
    public Map<ResizeHandle, Rectangle> getHandleBounds() {
        int halfSize = handleSize / 2;
        Map<ResizeHandle, Rectangle> map = new HashMap<>();
        map.put(ResizeHandle.TOP, new Rectangle(x + width / 2 - halfSize , y - halfSize, handleSize, handleSize));
        map.put(ResizeHandle.BOTTOM, new Rectangle(x + width / 2 - halfSize , y + height - halfSize , handleSize, handleSize));
        map.put(ResizeHandle.LEFT, new Rectangle(x - halfSize, y + height / 2 - halfSize, handleSize, handleSize));
        map.put(ResizeHandle.RIGHT, new Rectangle(x + width - halfSize, y + height / 2 - halfSize, handleSize, handleSize));
        map.put(ResizeHandle.TOP_LEFT, new Rectangle(x - halfSize, y - halfSize, handleSize, handleSize));
        map.put(ResizeHandle.TOP_RIGHT, new Rectangle(x + width - halfSize, y - halfSize, handleSize, handleSize));
        map.put(ResizeHandle.BOTTOM_LEFT, new Rectangle(x - halfSize, y + height - halfSize, handleSize, handleSize));
        map.put(ResizeHandle.BOTTOM_RIGHT, new Rectangle(x + width - halfSize, y + height - halfSize, handleSize, handleSize));
        return map;
    }



    @Override
    public boolean contains(int px, int py) {
        double dx = (px - (x + width / 2.0)) / (width / 2.0);
        double dy = (py - (y + height / 2.0)) / (height / 2.0);
        return dx * dx + dy * dy <= 1.0;
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
