
// _____         _     _           _
//|_   _|       | |   | |         | |
//  | | ___  ___| |__ | | ___   __| | __ _  ___
//  | |/ _ \/ __| '_ \| |/ _ \ / _` |/ _` |/ _ \
//  | |  __/ (__| | | | | (_) | (_| | (_| |  __/
//  \_/\___|\___|_| |_|_|\___/ \__,_|\__, |\___|
//                                    __/ |
//                                   |___/

package com.n4ims.hotelsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.ResourcePaths;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;



public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {



        //To be removed








        //----------------------------


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
