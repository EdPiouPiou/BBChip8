package org.pioupiou.view;

import javafx.scene.input.KeyEvent;
import org.pioupiou.core.ViewHandler;
import org.pioupiou.core.ViewModelFactory;

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
}
