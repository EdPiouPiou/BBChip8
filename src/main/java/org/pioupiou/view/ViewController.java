package org.pioupiou.view;

import javafx.event.ActionEvent;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import org.pioupiou.core.ViewHandler;
import org.pioupiou.core.ViewModelFactory;

import java.io.File;

public class ViewController {

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
        System.out.println(file.getName());
        viewModel.sendFileToCPU(file);
    }
}
