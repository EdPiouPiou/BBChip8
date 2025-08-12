package org.pioupiou.core;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.pioupiou.view.ViewController;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

public class ViewHandler {

    private Scene bb8Scene;
    private Stage bb8Stage;
    private ViewModelFactory viewModelFactory;

    public ViewHandler(ViewModelFactory viewModelFactory){
        this.viewModelFactory = viewModelFactory;
        bb8Stage = new Stage();
    }

    public void start() {
        openBB8View();
    }

    private void openBB8View() {
        try {
            URL urlresource = URI.create("file:/C:/Dev/BBChip8/src/main/resources/BBChip8.fxml").toURL();
            FXMLLoader loader = new FXMLLoader(urlresource);
            loader.setLocation(urlresource);
            Parent root = loader.load();

            ViewController viewController = loader.getController();
            viewController.init(this, viewModelFactory.getViewModel());

            bb8Scene = new Scene(root);
            bb8Stage.setScene(bb8Scene);
            bb8Stage.setTitle("BBChip8");
            bb8Stage.show();
        }
        catch (IOException exception){
          exception.printStackTrace();
        }

    }
}
