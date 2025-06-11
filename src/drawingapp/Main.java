package drawingapp;

import drawingapp.commands.CommandManager;
import drawingapp.controller.ShapeController;
import drawingapp.shapes.ShapeManager;
import drawingapp.ui.DrawingFrame;
import drawingapp.ui.DrawingPanel;
import drawingapp.ui.DrawingToolBar;
import drawingapp.ui.PropertyPanel;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            ShapeManager model = new ShapeManager();
            CommandManager commandManager = new CommandManager();
            ShapeController controller = new ShapeController(model, commandManager);
            DrawingPanel drawingPanel = new DrawingPanel(model, controller);
            PropertyPanel propertyPanel = new PropertyPanel(model, controller);
            DrawingToolBar toolBar = new DrawingToolBar(controller);
            new DrawingFrame(drawingPanel, propertyPanel, toolBar);
        });
    }
}
