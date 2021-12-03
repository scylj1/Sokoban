module bestSokobanEverV6 {
    requires java.base;
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.media;
    requires java.desktop;
    requires java.logging;
    opens gameStart;
    opens gameSettings;
    opens gameProcessor;
    opens gameController;
    opens gameRole;
    opens gameView;
}