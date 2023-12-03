package com.csc330.scribble;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.effect.GaussianBlur;

public class Scribble extends Application {

    // Declare canvas as an instance variable
    private Canvas canvas;

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();
        canvas = new Canvas(800, 600); // Initialize the canvas with initial size

        // Bind the width and height of the canvas to the width and height of the pane
        canvas.widthProperty().bind(root.widthProperty());
        canvas.heightProperty().bind(root.heightProperty());

        GraphicsContext gc = canvas.getGraphicsContext2D();
        setupDrawing(gc);

        root.getChildren().add(canvas);
        Scene scene = new Scene(root, 800, 600, Color.rgb(20,20,20)); // Initial size of the scene

        primaryStage.setTitle("Scribble");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setupDrawing(GraphicsContext gc) {

        GaussianBlur blur = new GaussianBlur();
        blur.setRadius(1.5); // You can adjust the radius as needed
        gc.setEffect(blur);

        double circleDiameter = 6; // Diameter of the circles
        Color lineColor = Color.WHITE; // Color of the line

        final double[] lastX = {0};
        final double[] lastY = {0};

        canvas.setOnMousePressed(e -> {
            lastX[0] = e.getX();
            lastY[0] = e.getY();
            drawCircle(gc, lastX[0], lastY[0], circleDiameter / 2, lineColor); // Draw an initial circle at the press point
        });

        canvas.setOnMouseDragged(e -> {
            interpolateAndDrawCircles(gc, lastX[0], lastY[0], e.getX(), e.getY(), circleDiameter, lineColor);
            lastX[0] = e.getX();
            lastY[0] = e.getY();
        });

        // Optional: Handle mouse released if needed
    }

    private void interpolateAndDrawCircles(GraphicsContext gc, double x1, double y1, double x2, double y2, double diameter, Color color) {
        double cx1 = x1 + (x2 - x1) / 3.0; // Control point 1 x-coordinate
        double cy1 = y1 + (y2 - y1) / 3.0; // Control point 1 y-coordinate
        double cx2 = x1 + 2 * (x2 - x1) / 3.0; // Control point 2 x-coordinate
        double cy2 = y1 + 2 * (y2 - y1) / 3.0; // Control point 2 y-coordinate

        int steps = 40; // Number of steps for interpolation

        gc.setFill(color); // Set the color for the circles

        for (int i = 0; i <= steps; i++) {
            double t = i / (double) steps;
            double tInv = 1 - t;
            double x = x1 * Math.pow(tInv, 3) + 3 * cx1 * t * Math.pow(tInv, 2) + 3 * cx2 * Math.pow(t, 2) * tInv + x2 * Math.pow(t, 3);
            double y = y1 * Math.pow(tInv, 3) + 3 * cy1 * t * Math.pow(tInv, 2) + 3 * cy2 * Math.pow(t, 2) * tInv + y2 * Math.pow(t, 3);

            drawCircle(gc, x, y, diameter / 2, color);
        }
    }

    private void drawCircle(GraphicsContext gc, double x, double y, double radius, Color color) {
        gc.setFill(color);
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);
    }

    public static void main(String[] args) {
        launch(args);
    }
}