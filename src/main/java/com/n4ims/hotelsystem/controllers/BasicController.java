package com.n4ims.hotelsystem.controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ResourcePaths;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class BasicController extends Application {
    public static final Logger log = LoggerFactory.getLogger( MethodHandles.lookup().lookupClass() );
    @Override
    public void start(Stage stage) throws Exception {
    }

    private void initialize() {

    }

    /***
     * Navigates by loading other views
     * @param sourceScene Scene of the object the event occurred on
     */
    public void navigate(Scene sourceScene){
        try {
            ResourceBundle resources = ResourceBundle.getBundle("com.n4ims.hotelsystem.i18n.lang");
            URL url = getClass().getClassLoader().getResource(ResourcePaths.BOOKING_CREATION_VIEW);
            Parent view = FXMLLoader.load(url, resources);

            Scene scene = new Scene(view);
            Stage stage = (Stage) sourceScene.getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            log.error(Arrays.toString(e.getStackTrace()));
            // TODO show user error field
        }
    }
}
