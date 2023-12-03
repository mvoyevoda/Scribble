module com.csc330.scribble {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires com.almasb.fxgl.all;

    opens com.csc330.scribble to javafx.fxml;
    exports com.csc330.scribble;
}