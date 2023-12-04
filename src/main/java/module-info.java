module csi.csc330.scribble {
    requires javafx.controls;
    requires javafx.fxml;

    opens csi.csc330.scribble to javafx.fxml;
    exports csi.csc330.scribble;
}
