package org.pioupiou.view;

import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import org.pioupiou.core.ViewHandler;
import org.pioupiou.model.Sprite;

import java.io.File;

public class ViewController {

    public Canvas screen;
    private ViewHandler viewHandler;
    private ViewModel viewModel;

    public void init(ViewHandler viewHandler, ViewModel viewModel){
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
    }
    public void onKeyPressed(KeyEvent keyPressed){
        System.out.println("pressed");
        viewModel.sendKeyPressedToCPU(keyPressed.getText());
    }

    public void onKeyReleased(KeyEvent keyReleased){
        System.out.println("released");
        viewModel.sendKeyReleasedToCPU(keyReleased.getText());
    }

    public void onLoadROM(ActionEvent loadROMclick) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Chip 8 ROMs", "*.ch8"));
        File file = fileChooser.showOpenDialog(viewHandler.getBb8Stage());
        viewModel.sendFileToCPU(file);
        while(viewModel.isDrawFlag()){
            this.drawPixel();
        }
    }

    public void drawPixel(){
        Sprite sprite = viewModel.getSpriteToDraw();
        screen = (Canvas) viewHandler.getBb8Stage().getScene().lookup("#screen");
        for(int row = 0; row < sprite.getHeight(); row++){
            viewHandler.draw(this, sprite);
        }
    }


}
