package drawingapp;

import drawingapp.ui.DrawingFrame;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new DrawingFrame();
        });
    }
}
