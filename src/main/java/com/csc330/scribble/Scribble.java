package com.csc330.scribble;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.transform.Rotate;

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
        double lineWidth = 0.5; // Width of the line
        double lineLength = 10; // Length of the line, also the diameter of the circles
        Color lineColor = Color.WHITE; // Color of the line

        final double[] lastX = {0};
        final double[] lastY = {0};

        canvas.setOnMousePressed(e -> {
            lastX[0] = e.getX();
            lastY[0] = e.getY();
            drawCircle(gc, lastX[0], lastY[0], lineLength / 2, lineColor); // Draw an initial circle at the press point
        });

        canvas.setOnMouseDragged(e -> {
            interpolateAndDrawRectangles(gc, lastX[0], lastY[0], e.getX(), e.getY(), lineWidth, lineLength, lineColor);
            lastX[0] = e.getX();
            lastY[0] = e.getY();
        });

        // Optional: Handle mouse released if needed
    }

    private void interpolateAndDrawRectangles(GraphicsContext gc, double x1, double y1, double x2, double y2, double width, double length, Color color) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        double distance = Math.sqrt(dx * dx + dy * dy);
        double angle = Math.atan2(dy, dx);

        // Significantly increase the number of steps for smoother lines
        // You can adjust the multiplier (e.g., 4, 5, etc.) to see different levels of smoothness
        int steps = Math.max((int) (distance * 20), 200);

        gc.setFill(color); // Set the color for the rectangles

        for (int i = 0; i <= steps; i++) {
            double t = i / (double) steps;
            double interpolatedX = x1 + t * dx;
            double interpolatedY = y1 + t * dy;

            drawRotatedRectangle(gc, interpolatedX, interpolatedY, angle, width, length, color);
        }
    }

    private void drawRotatedRectangle(GraphicsContext gc, double x, double y, double angle, double width, double length, Color color) {
        gc.save(); // Save the current state of the graphics context

        // Translate and rotate the canvas context
        gc.translate(x, y);
        gc.rotate(Math.toDegrees(angle));

        // Set the fill color for the rectangle
        gc.setFill(color);

        // Draw the rectangle centered at the current position
        gc.fillRect(-width / 2, -length / 2, width, length);

        gc.restore(); // Restore the graphics context to its original state
    }

    private void drawCircle(GraphicsContext gc, double x, double y, double radius, Color color) {
        gc.setFill(color);
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
