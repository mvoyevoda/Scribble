package csi.csc330.scribble;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.image.Image;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/Scribble.fxml"));
        primaryStage.setTitle("Scribble");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
//        Image chalkCursorImage = new Image(getClass().getResourceAsStream("/chalk.png"));
//        Cursor chalkCursor = new ImageCursor(chalkCursorImage);
//        primaryStage.getScene().setCursor(chalkCursor);
//        Image icon = new Image(getClass().getResourceAsStream("/icon.png"));
//        primaryStage.getIcons().add(icon);
    }

    public static void main(String[] args) {
        launch(args);
    }
}