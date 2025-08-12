package org.pioupiou.view;

import org.pioupiou.core.ViewHandler;
import org.pioupiou.core.ViewModelFactory;

public class ViewController {

    private ViewHandler viewHandler;
    private ViewModel viewModel;

    public void init(ViewHandler viewHandler, ViewModel viewModel){
        this.viewHandler = viewHandler;
        this.viewModel = viewModel;
    }
    public void onKeyPressed(){
        System.out.println("pressed");
        viewModel.sendKeyPressedToCPU();
    }

    public void onKeyReleased(){
        System.out.println("released");
        viewModel.sendKeyReleasedToCPU();
    }
}
