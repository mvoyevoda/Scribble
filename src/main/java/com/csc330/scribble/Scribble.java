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
        blur.setRadius(2); // You can adjust the radius as needed
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
        double dx = x2 - x1;
        double dy = y2 - y1;
        double distance = Math.sqrt(dx * dx + dy * dy);

        // Significantly increase the number of steps
        // The multiplier is set to a higher value to achieve higher smoothness
        int steps = Math.max((int) (distance * 4), 40);  // Increase the multiplier as needed

        gc.setFill(color); // Set the color for the circles

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
    }

    public static void main(String[] args) {
        launch(args);
    }
}