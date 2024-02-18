module cuny.csi.csc330.scribble {
    requires javafx.controls;
    requires javafx.fxml;


    opens cuny.csi.csc330.scribble to javafx.fxml;
    exports cuny.csi.csc330.scribble;
}