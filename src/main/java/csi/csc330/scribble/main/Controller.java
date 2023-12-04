package csi.csc330.scribble.main;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Controller {

    @FXML
    private Canvas canvas;
    @FXML
    private BorderPane borderPane; // Reference to the BorderPane
    @FXML
    private MenuItem clearCanvasMenuItem;
    private GraphicsContext gc;
    private final Color[] strokeColors = new Color[]{Color.WHITE, Color.GREEN, Color.RED, Color.YELLOW, Color.BLUE, Color.ORANGE, Color.PURPLE, Color.GRAY, Color.MEDIUMPURPLE, Color.PINK, Color.BROWN};
    private final double[] strokeThicknessOptions = {1.0, 3.0, 6.0}; // Add desired stroke thickness values
    // STYLE PARAMETERS
    private double selectedStrokeThickness = 1.0; // Default stroke thickness
    private Color selectedColor = Color.WHITE; // Default Stroke color

    @FXML
    public void initialize() {
        gc = canvas.getGraphicsContext2D();
        setupDrawing();
        populateColorMenu();  // Populate the color menu
        populateThicknessMenu(); // Populate the thickness menu
        // Bind canvas size to border pane size
        canvas.widthProperty().bind(borderPane.widthProperty());
        canvas.heightProperty().bind(borderPane.heightProperty());

        clearCanvasMenuItem.setOnAction(e -> clearCanvas(gc));
    }

    private void clearCanvas(GraphicsContext gc) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        canvas.getParent().requestLayout(); // Force a layout pass
    }

    private void setupDrawing() {
        GaussianBlur blur = new GaussianBlur();
        blur.setRadius(2);
        gc.setEffect(blur);

        double circleDiameter = 6;
        final double[] lastX = {0};
        final double[] lastY = {0};

        canvas.setOnMousePressed(e -> {
            lastX[0] = e.getX();
            lastY[0] = e.getY();
            drawCircle(lastX[0], lastY[0], circleDiameter / 2, selectedColor);
        });

        canvas.setOnMouseDragged(e -> {
            interpolateAndDrawCircles(lastX[0], lastY[0], e.getX(), e.getY(), circleDiameter, selectedColor);
            lastX[0] = e.getX();
            lastY[0] = e.getY();
        });
    }

    private void interpolateAndDrawCircles(double x1, double y1, double x2, double y2, double diameter, Color color) {
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

            drawCircle(interpolatedX, interpolatedY, diameter / 2, color);
        }
    }

    private void drawCircle(double x, double y, double radius, Color color) {
        gc.setFill(color);
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);
        gc.setLineWidth(selectedStrokeThickness);
        gc.setStroke(color);
        gc.strokeOval(x - radius, y - radius, radius * 2, radius * 2);
    }

    private Rectangle createColorRectangle(Color color) {
        Rectangle rectangle = new Rectangle(20, 20);
        rectangle.setFill(color);
        return rectangle;
    }

    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu colorMenu;
    @FXML
    private Menu thicknessMenu;

    private void populateColorMenu() {
        for (Color color : strokeColors) {
            MenuItem colorItem = new MenuItem();
            colorItem.setGraphic(createColorRectangle(color));

            // Set an action handler for each color item
            colorItem.setOnAction(e -> {
                selectedColor = color;
                System.out.println("Selected color: " + color); // Just for debugging, you can remove this later
            });

            colorMenu.getItems().add(colorItem);
        }
    }

    private void populateThicknessMenu() {
        for (double thickness : strokeThicknessOptions) {
            MenuItem thicknessItem = new MenuItem(String.valueOf(thickness));
            thicknessItem.setOnAction(e -> {
                selectedStrokeThickness = thickness;
                // Additional action logic
            });
            thicknessMenu.getItems().add(thicknessItem);
        }
    }

}
