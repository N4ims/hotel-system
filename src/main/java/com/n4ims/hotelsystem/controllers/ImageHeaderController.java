package com.n4ims.hotelsystem.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import java.util.Locale;

/**

 This class represents the controller for the image header of the Hotel System application. It extends the BasicController class.
 It includes methods to handle the click events of the German and English language buttons.
 */
public class ImageHeaderController extends BasicController{

    @FXML
    private Button germanButton;
    @FXML
    private Button englishButton;

    private String sourceViewPath;

    /**
     * Initializes the controller by setting the on click event listeners for the German and English language buttons.
     */
    public void initialize(){
        germanButton.setOnAction(this::handleOnGermanButtonClicked);
        englishButton.setOnAction(this::handleOnEnglishButtonClicked);
    }

    private void handleOnGermanButtonClicked(ActionEvent e){
        setLocale(Locale.GERMAN);

        Button button = (Button) e.getSource();
        Scene scene = button.getScene();

        // to reload the resources
        navigate(scene, sourceViewPath);
    }


    private void handleOnEnglishButtonClicked(ActionEvent e){
        setLocale(Locale.ENGLISH);

        Button button = (Button) e.getSource();
        Scene scene = button.getScene();

        // to reload the resources
        navigate(scene, sourceViewPath);
    }

    /**
     * Sets the source view path for the controller.
     * @param sourceViewPath The source view path to set.
     */
    public void setSourceViewPath(String sourceViewPath){
        this.sourceViewPath = sourceViewPath;
    }
}
