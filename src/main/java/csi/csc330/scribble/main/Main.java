package csi.csc330.scribble.main;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class Main extends Application {

    private Canvas canvas;
    private Color selectedColor = Color.BLACK; // Default color
    private double selectedStrokeThickness = 1.0; // Default stroke thickness
    @Override
    public void start(Stage stage) {

        Pane root = new Pane();
        Scene scene = new Scene(root, 800, 600, Color.rgb(20, 20, 20));
        canvas = new Canvas(800, 600);
        canvas.widthProperty().bind(root.widthProperty());
        canvas.heightProperty().bind(root.heightProperty());

        // Load the cursor image using the correct path
        Image cursorImage = new Image(getClass().getResourceAsStream("/chalk.png"));
        Cursor cursor = new ImageCursor(cursorImage);

        stage.setScene(scene);
        stage.setTitle("Scribble");
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
