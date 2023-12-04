package csi.csc330.scribble.main;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RenderLogic {

    private double selectedStrokeThickness;

    public RenderLogic() {
        this.selectedStrokeThickness = 1.0; // Default stroke thickness
    }

    public void setSelectedStrokeThickness(double thickness) {
        this.selectedStrokeThickness = thickness;
    }

    public void interpolateAndDrawCircles(GraphicsContext gc, double x1, double y1, double x2, double y2, double diameter, Color color) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        double distance = Math.sqrt(dx * dx + dy * dy);

        int steps = Math.max((int) (distance * 4), 40);

        gc.setFill(color);
        gc.setStroke(color);
        gc.setLineWidth(selectedStrokeThickness);

        for (int i = 0; i <= steps; i++) {
            double t = i / (double) steps;
            double interpolatedX = x1 + t * dx;
            double interpolatedY = y1 + t * dy;

            drawCircle(gc, interpolatedX, interpolatedY, diameter / 2, color);
        }
    }

    private void drawCircle(GraphicsContext gc, double x, double y, double radius, Color color) {
        gc.setFill(color);
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);
        gc.setStroke(color);
        gc.strokeOval(x - radius, y - radius, radius * 2, radius * 2);
    }
}