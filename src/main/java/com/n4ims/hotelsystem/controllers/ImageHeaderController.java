package com.n4ims.hotelsystem.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import java.util.Locale;

public class ImageHeaderController extends BasicController{

    @FXML
    private Button germanButton;
    @FXML
    private Button englishButton;

    private String sourceViewPath;

    public void initialize(){
        germanButton.setOnAction(this::handleOnGermanButtonClicked);
        englishButton.setOnAction(this::handleOnEnglishButtonClicked);
    }

    public void handleOnGermanButtonClicked(ActionEvent e){
        setLocale(Locale.GERMAN);

        Button button = (Button) e.getSource();
        Scene scene = button.getScene();

        // to reload the resources
        navigate(scene, sourceViewPath);
    }
    public void handleOnEnglishButtonClicked(ActionEvent e){
        setLocale(Locale.ENGLISH);

        Button button = (Button) e.getSource();
        Scene scene = button.getScene();

        // to reload the resources
        navigate(scene, sourceViewPath);
    }

    public void setSourceViewPath(String sourceViewPath){
        this.sourceViewPath = sourceViewPath;
    }
}
