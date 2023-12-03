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
        double radius = 5; // Circle radius
        gc.setFill(Color.WHITE); // Use setFill for the color of the circles
        gc.setStroke(Color.WHITE); // Set the same color for the stroke
        gc.setLineWidth(radius * 2); // Set the line width to match the circle's diameter

        final double[] lastX = {0}; // Array to hold the last position
        final double[] lastY = {0}; // Using array to allow modification in lambda

        canvas.setOnMousePressed(e -> {
            lastX[0] = e.getX();
            lastY[0] = e.getY();
            drawCircle(gc, lastX[0], lastY[0], radius);
        });

        canvas.setOnMouseDragged(e -> {
            drawCircle(gc, e.getX(), e.getY(), radius);

            // Draw a line from the last position to the current position
            gc.strokeLine(lastX[0], lastY[0], e.getX(), e.getY());

            lastX[0] = e.getX();
            lastY[0] = e.getY();
        });
    }

    private void drawCircle(GraphicsContext gc, double x, double y, double radius) {
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);
    }





    public static void main(String[] args) {
        launch(args);
    }
}
