package com.csc330.scribble;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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
        double size = 10; // Square size
        gc.setFill(Color.WHITE); // Use setFill for the color of the squares and circles
        gc.setStroke(Color.WHITE); // Set the same color for the stroke
        gc.setLineWidth(size); // Set the line width to match the square's size

        final double[] lastX = {0}; // Array to hold the last position
        final double[] lastY = {0}; // Using array to allow modification in lambda

        canvas.setOnMousePressed(e -> {
            lastX[0] = e.getX();
            lastY[0] = e.getY();
            drawCircle(gc, lastX[0], lastY[0], size / 2); // Draw circle at the click position
        });

        canvas.setOnMouseDragged(e -> {
            interpolateAndDrawLine(gc, lastX[0], lastY[0], e.getX(), e.getY(), size);

            lastX[0] = e.getX();
            lastY[0] = e.getY();
        });

        // THIS IS NOT WORKING.....
        canvas.setOnMouseReleased(e -> {
            drawCircle(gc, e.getX(), e.getY(), size / 2); // Draw circle when the mouse is released
        });
    }

    private void interpolateAndDrawLine(GraphicsContext gc, double lastX, double lastY, double currentX, double currentY, double radius) {
        double distance = Math.sqrt(Math.pow(currentX - lastX, 2) + Math.pow(currentY - lastY, 2));
        int steps = (int) distance;

        for (int i = 0; i <= steps; i++) {
            double t = i / (double) steps;
            double interpolatedX = lastX + t * (currentX - lastX);
            double interpolatedY = lastY + t * (currentY - lastY);

            // Draw a circle at each interpolated position
            drawCircle(gc, interpolatedX, interpolatedY, radius/2);
        }
    }

    private void drawSquare(GraphicsContext gc, double x, double y, double size) {
        gc.fillRect(x - size / 2, y - size / 2, size, size);
    }

    private void drawCircle(GraphicsContext gc, double x, double y, double radius) {
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
