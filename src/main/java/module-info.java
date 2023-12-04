module com.csc330.scribble {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires com.almasb.fxgl.all;

    opens csi.csc330.scribble.main to javafx.fxml;
    exports csi.csc330.scribble.main;
}