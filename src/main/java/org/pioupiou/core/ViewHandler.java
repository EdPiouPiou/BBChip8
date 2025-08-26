package org.pioupiou.core;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelFormat;
import javafx.scene.image.PixelWriter;
import javafx.stage.Stage;
import org.pioupiou.model.Sprite;
import org.pioupiou.view.ViewController;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class ViewHandler {

    private Scene bb8Scene;
    private Stage bb8Stage;
    private ViewModelFactory viewModelFactory;
    private Canvas screen; //bind canvas defined in fxml file with the handler

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

    public void draw(ViewController viewController, Sprite sprite){
        GraphicsContext gc = viewController.screen.getGraphicsContext2D();
        PixelWriter pixelWriter = gc.getPixelWriter();
        final int WIDTH = 8;
        final int LINE_HEIGHT = 1;
        int offset = 0;
        int scanlineStride = 0;
        for(int spriteHeight = 0; spriteHeight < sprite.getHeight(); spriteHeight++){
            pixelWriter.setPixels(sprite.getXcoord(), sprite.getYcoord(), WIDTH, LINE_HEIGHT, PixelFormat.getIntArgbInstance(), sprite.getPixels()[spriteHeight], offset, scanlineStride);
        }

    }

    public Stage getBb8Stage() {
        return bb8Stage;
    }
}
