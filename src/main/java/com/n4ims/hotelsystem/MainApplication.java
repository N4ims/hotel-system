package com.n4ims.hotelsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.ResourcePaths;
import java.util.ResourceBundle;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        ResourceBundle resources = ResourceBundle.getBundle("com.n4ims.hotelsystem.i18n.lang");
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(ResourcePaths.OVERVIEW_CALENDER_VIEW), resources);

        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Techlodge");
        stage.setScene(scene);
        stage.show();
    }
}
