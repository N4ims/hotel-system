package com.n4ims.hotelsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.n4ims.hotelsystem.utils.ResourcePaths;

import java.net.URL;
import java.util.ResourceBundle;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        ResourceBundle langBundle = ResourceBundle.getBundle(ResourcePaths.LANGUAGE_BUNDLE);
        URL url = MainApplication.class.getClassLoader().getResource("com/n4ims/hotelsystem/views/overviewCalenderView.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(url, langBundle);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Techlodge");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }
}
