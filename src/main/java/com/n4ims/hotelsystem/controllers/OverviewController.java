package com.n4ims.hotelsystem.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;

public class OverviewController extends BasicController{
    @FXML
    private ChoiceBox occupationStatusPicker;

    public void initialize(){
        occupationStatusPicker.setOnMouseClicked(this::handleOnMouseClicked);
        System.out.println("OverviewController initialized");
    }

    @FXML
    private void handleOnMouseClicked(MouseEvent event){
        if (event.getSource().equals(occupationStatusPicker)) {
            Scene scene = occupationStatusPicker.getScene();
            super.navigate(scene);
        }
    }
}
