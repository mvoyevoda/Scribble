package csi.csc330.scribble.main;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckMenuItem;
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
    private final int[] strokeThicknessOptions = {3, 6, 8}; // Add desired stroke thickness values
    // STYLE PARAMETERS
    private int selectedStrokeThickness = strokeThicknessOptions[0]; // Default stroke thickness
    private Color selectedColor = Color.WHITE; // Default stroke color
    Color backgroundColor = Color.web("#141414"); // Default background color
    private boolean eraserMode = false;

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
        eraserToggle.setOnAction(e -> {
            eraserMode = eraserToggle.isSelected(); // Toggle eraser mode
        });
    }

    private void clearCanvas(GraphicsContext gc) {
        gc.setFill(backgroundColor);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void setupDrawing() {
        GaussianBlur blur = new GaussianBlur();
        blur.setRadius(2);
        gc.setEffect(blur);

        double circleDiameter = selectedStrokeThickness*2;
        final double[] lastX = {0};
        final double[] lastY = {0};

        canvas.setOnMousePressed(e -> {
            lastX[0] = e.getX();
            lastY[0] = e.getY();
            if (eraserMode){
                clearArea(e.getX(), e.getY());
            } else {
                drawCircle(lastX[0], lastY[0], circleDiameter / 2, selectedColor);
            }
        });

        canvas.setOnMouseDragged(e -> {
            if (eraserMode) {
                // Erase the area under the cursor
                clearArea(e.getX(), e.getY());
            } else {
                // Existing drawing code
                interpolateAndDrawCircles(lastX[0], lastY[0], e.getX(), e.getY(), circleDiameter, selectedColor);
            }
            lastX[0] = e.getX();
            lastY[0] = e.getY();
        });
    }

    private void clearArea(double x, double y) {
        double eraserDiameter = 50; // Diameter of the eraser
        double radius = eraserDiameter / 2;

        // Set the fill color to the background color of the canvas
        gc.setFill(backgroundColor);
        // Fill a circle with the background color to "erase"
        gc.fillOval(x - radius, y - radius, eraserDiameter, eraserDiameter);
    }

    private void interpolateAndDrawCircles(double x1, double y1, double x2, double y2, double diameter, Color color) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        double distance = Math.sqrt(dx * dx + dy * dy);

        int steps = Math.max((int) (distance * 4), 40);

//        gc.setFill(color);
//        gc.setStroke(color);
//        gc.setLineWidth(selectedStrokeThickness);

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
    @FXML
    private CheckMenuItem eraserToggle;


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
        for (int thickness : strokeThicknessOptions) {
            MenuItem thicknessItem = new MenuItem(String.valueOf(thickness));
            thicknessItem.setOnAction(e -> {
                selectedStrokeThickness = thickness;
                // Additional action logic
            });
            thicknessMenu.getItems().add(thicknessItem);
        }
    }

}
