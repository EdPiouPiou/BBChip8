package org.pioupiou.core;

import org.pioupiou.view.ViewModel;

public class ViewModelFactory {
    private ModelFactory modelFactory;

    public ViewModelFactory(ModelFactory modelFactory) {
        this.modelFactory = modelFactory;
    }

    public ViewModel getViewModel(){
        //pass model to viewModel
        return new ViewModel(modelFactory.getChip8CPU());
    }
}
