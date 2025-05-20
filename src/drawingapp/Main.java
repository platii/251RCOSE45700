package drawingapp;

import drawingapp.ui.DrawingFrame;
import drawingapp.ui.DrawingPanel;
import drawingapp.ui.DrawingToolBar;
import drawingapp.ui.PropertyPanel;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            DrawingPanel drawingPanel = new DrawingPanel();
            PropertyPanel propertyPanel = new PropertyPanel(drawingPanel);
            DrawingToolBar toolBar = new DrawingToolBar(drawingPanel);
            new DrawingFrame(drawingPanel, propertyPanel, toolBar);
        });
    }
}
