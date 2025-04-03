package drawingapp;

import drawingapp.shapes.DrawableShape;

@FunctionalInterface
public interface ShapeSelectedListener {
    void onShapeSelected(DrawableShape shape);
}
