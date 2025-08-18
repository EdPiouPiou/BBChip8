package org.pioupiou;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.pioupiou.core.ModelFactory;
import org.pioupiou.core.ViewHandler;
import org.pioupiou.core.ViewModelFactory;

public class BB8App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        ModelFactory modelFactory = new ModelFactory();
        ViewModelFactory viewModelFactory = new ViewModelFactory(modelFactory);
        ViewHandler viewHandler = new ViewHandler(viewModelFactory);
        viewHandler.start();
    }
}
