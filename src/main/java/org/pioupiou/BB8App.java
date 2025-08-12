package org.pioupiou;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class BB8App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Pane rootPane = new BorderPane();
        Scene scene = new Scene(rootPane);
        stage.setScene(scene);
        stage.setTitle("BBChip8 - JavaFX Chip8 Emulator");
        stage.show();
    }
}
