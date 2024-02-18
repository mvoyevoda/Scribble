package cuny.csi.csc330.scribble;

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

import java.util.List;

public class Controller {

    private final Model model = new Model(); // Instantiate the Model

    // UI Components
    @FXML
    private Canvas canvas;
    @FXML
    private BorderPane borderPane;
    @FXML
    private MenuItem clearCanvasMenuItem;
    @FXML
    private Menu colorMenu;
    @FXML
    private Menu thicknessMenu;
    @FXML
    private CheckMenuItem eraserToggle;

    // Drawing Parameters
    private GraphicsContext gc;
    private final Color[] strokeColors = new Color[]{Color.WHITE, Color.GREEN, Color.RED, Color.YELLOW, Color.BLUE, Color.ORANGE, Color.PURPLE, Color.GRAY, Color.MEDIUMPURPLE, Color.PINK, Color.BROWN};
    private final int[] strokeThicknessOptions = {3, 6, 8};
    private int selectedStrokeThickness = strokeThicknessOptions[0]; // Default stroke thickness
    private Color selectedColor = Color.WHITE; // Default stroke color
    Color backgroundColor = Color.web("#141414"); // Default background color
    private boolean eraserMode = false;

    @FXML
    public void initialize() {
        gc = canvas.getGraphicsContext2D();
        setupDrawing();
        populateColorMenu();
        populateThicknessMenu();
        // Bind canvas size to border pane size
        canvas.widthProperty().bind(borderPane.widthProperty());
        canvas.heightProperty().bind(borderPane.heightProperty());
        clearCanvasMenuItem.setOnAction(e -> clearCanvas(gc));
    }

    private void clearCanvas(GraphicsContext gc) {
        gc.setFill(backgroundColor);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void setupDrawing() {
        // Blur line for smoother appearance
        GaussianBlur blur = new GaussianBlur();
        blur.setRadius(2);
        gc.setEffect(blur);
        // Obtain circle diameter from stroke thickness
        double circleDiameter = selectedStrokeThickness * 2;

        canvas.setOnMousePressed(e -> {
            model.setLastX(e.getX());
            model.setLastY(e.getY());
            if (eraserMode){
                clearArea(e.getX(), e.getY());
            } else {
                drawCircle(model.getLastX(), model.getLastY(), circleDiameter / 2, selectedColor);
            }
        });

        canvas.setOnMouseDragged(e -> {
            double currentX = e.getX();
            double currentY = e.getY();
            if (eraserMode) {
                clearArea(e.getX(), e.getY());
            } else {
                drawInterpolatedLine(currentX, currentY, circleDiameter, selectedColor);
            }
            model.setLastX(currentX);
            model.setLastY(currentY);
        });

        eraserToggle.setOnAction(e -> eraserMode = eraserToggle.isSelected());

    }

    private void drawInterpolatedLine(double newX, double newY, double circleDiameter, Color selectedColor){
        model.setInterpolationPoints(newX, newY); // Calculate points
        List<double[]> interpolationPoints = model.getInterpolationPoints(); // Retrieve points
        for (double[] coord : interpolationPoints){
            drawCircle(coord[0], coord[1], circleDiameter / 2, selectedColor);
        }
    }

    private void drawCircle(double x, double y, double radius, Color color) {
        gc.setFill(color);
        gc.setStroke(color);
        gc.setLineWidth(selectedStrokeThickness);
        gc.fillOval(x - radius, y - radius, radius * 2, radius * 2);
        gc.strokeOval(x - radius, y - radius, radius * 2, radius * 2);
    }

    private void clearArea(double x, double y) {
        double eraserDiameter = 50;
        double radius = eraserDiameter / 2;
        gc.setFill(backgroundColor);
        gc.fillOval(x - radius, y - radius, eraserDiameter, eraserDiameter);
    }

    // Dynamic UI loading functions
    private void populateColorMenu() {
        for (Color color : strokeColors) {

            MenuItem colorItem = new MenuItem();
            Rectangle rectangle = new Rectangle(20, 20);
            rectangle.setFill(color);
            colorItem.setGraphic(rectangle);
            colorMenu.getItems().add(colorItem);

            colorItem.setOnAction(e -> {
                selectedColor = color;
                System.out.println("Selected color: " + color);
            });

        }
    }
    private void populateThicknessMenu() {
        for (int thickness : strokeThicknessOptions) {

            MenuItem thicknessItem = new MenuItem(String.valueOf(thickness));
            thicknessMenu.getItems().add(thicknessItem);

            thicknessItem.setOnAction(e -> selectedStrokeThickness = thickness);

        }
    }

}
