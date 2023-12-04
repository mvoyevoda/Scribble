package csi.csc330.scribble.main;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.effect.GaussianBlur;

public class Scribble extends Application {

    private Canvas canvas;
    private final Color[] strokeColors = new Color[]{Color.GREEN, Color.BLACK, Color.RED, Color.YELLOW, Color.BLUE, Color.ORANGE, Color.PURPLE, Color.GRAY, Color.MEDIUMPURPLE, Color.PINK, Color.BROWN};
    private Color selectedColor = Color.BLACK; // Default color
    private final double[] strokeThicknessOptions = {1.0, 3.0, 6.0}; // Add desired stroke thickness values
    private double selectedStrokeThickness = 1.0; // Default stroke thickness

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        canvas = new Canvas(800, 600);
        canvas.widthProperty().bind(root.widthProperty());
        canvas.heightProperty().bind(root.heightProperty());

        GraphicsContext gc = canvas.getGraphicsContext2D();
        setupDrawing(gc);

        MenuBar menuBar = new MenuBar();
        Menu colorMenu = new Menu("Color");

        for (Color color : strokeColors) {
            MenuItem colorItem = new MenuItem();
            colorItem.setOnAction(e -> {
                selectedColor = color;
                System.out.println("Selected color: " + selectedColor);
            });
            colorItem.setGraphic(createColorRectangle(color));
            colorMenu.getItems().add(colorItem);
        }
        Menu thicknessMenu = new Menu("Thickness");

        for (double thickness : strokeThicknessOptions) {
            MenuItem thicknessItem = new MenuItem(String.valueOf(thickness));
            thicknessItem.setOnAction(e -> {
                selectedStrokeThickness = thickness;
                System.out.println("Selected thickness: " + selectedStrokeThickness); // Output selected thickness to console
            });
            thicknessMenu.getItems().add(thicknessItem);
        }

        menuBar.getMenus().addAll(colorMenu,thicknessMenu);
        root.setTop(menuBar);
        root.setCenter(canvas);

        Scene scene = new Scene(root, 800, 600, Color.rgb(20, 20, 20));

        primaryStage.setTitle("Scribble");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void setupDrawing(GraphicsContext gc) {
        GaussianBlur blur = new GaussianBlur();
        blur.setRadius(2);
        gc.setEffect(blur);

        double circleDiameter = 6;
        final double[] lastX = {0};
        final double[] lastY = {0};

        canvas.setOnMousePressed(e -> {
            lastX[0] = e.getX();
            lastY[0] = e.getY();
            drawCircle(gc, lastX[0], lastY[0], circleDiameter / 2, selectedColor);
        });

        canvas.setOnMouseDragged(e -> {
            interpolateAndDrawCircles(gc, lastX[0], lastY[0], e.getX(), e.getY(), circleDiameter, selectedColor);
            lastX[0] = e.getX();
            lastY[0] = e.getY();
        });
    }

    private void interpolateAndDrawCircles(GraphicsContext gc, double x1, double y1, double x2, double y2, double diameter, Color color) {
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
        gc.setLineWidth(selectedStrokeThickness);//set the thichness
        gc.setStroke(color);//set the color
        gc.strokeOval(x - radius, y - radius, radius * 2, radius * 2);
    }

    private javafx.scene.shape.Rectangle createColorRectangle(Color color) {
        javafx.scene.shape.Rectangle rectangle = new javafx.scene.shape.Rectangle(20, 20);
        rectangle.setFill(color);
        return rectangle;
    }

    public static void main(String[] args) {
        launch(args);
    }
}

