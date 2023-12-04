package csi.csc330.scribble.main;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class UserInterface {

    private Canvas canvas;
    private Scene scene;
    private RenderLogic renderLogic;

    public UserInterface() {
        renderLogic = new RenderLogic();
        BorderPane root = new BorderPane();
        setupCanvas();
        setupMenus(root);
        this.scene = new Scene(root, 800, 600, Color.rgb(20, 20, 20));
    }

    private void setupCanvas() {
        canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        // Include logic for drawing on the canvas
    }

    private void setupMenus(BorderPane root) {
        MenuBar menuBar = new MenuBar();
        // Setup menus and add them to menuBar
        root.setTop(menuBar);
        root.setCenter(canvas);
    }

    private Rectangle createColorRectangle(Color color) {
        Rectangle rectangle = new Rectangle(20, 20);
        rectangle.setFill(color);
        return rectangle;
    }

    public Scene getScene() {
        return this.scene;
    }
}
