package com.n4ims.hotelsystem.controllers;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import utils.ResourcePaths;

public class NavigationBarController extends BasicController{

    @FXML
    private Label navBarItem0;

    @FXML
    private Label navBarItem1;

    @FXML
    private Label navBarItem2;

    @FXML
    private Label navBarItem3;

    @FXML
    private Label navBarItem4;

    public void initialize(){
        navBarItem0.setOnMouseClicked(this::handleNavigation);
        System.out.println("Navigation Controller initialized");
    }

    public void disableNavigationItem(int itemIndex) throws IllegalArgumentException{
        Label l = switch(itemIndex){
            case 0 -> navBarItem0;
            case 1 -> navBarItem1;
            case 2 -> navBarItem2;
            case 3 -> navBarItem3;
            case 4 -> navBarItem4;
            default -> throw new IllegalStateException("No navigation element with index " + itemIndex);
        };

        l.setDisable(true);
    }

    @FXML
    private void handleNavigation(MouseEvent event){
        if (event.getSource().equals(navBarItem0)){
            Scene scene = navBarItem0.getScene();
            super.navigate(scene, ResourcePaths.OVERVIEW_CALENDER_VIEW);
        }

    }

}
