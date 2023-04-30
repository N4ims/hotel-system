package com.n4ims.hotelsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        ResourceBundle resources = ResourceBundle.getBundle("com.n4ims.hotelsystem.i18n.lang");

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("views/overviewCalenderView.fxml"), resources);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Techlodge");
        stage.setScene(scene);
        stage.show();
    }
}
