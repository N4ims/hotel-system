package com.n4ims.hotelsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.n4ims.hotelsystem.utils.ResourcePaths;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.lang.invoke.MethodHandles;
import java.net.URL;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MainApplication extends Application {
    public static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    @Override
    public void start(Stage stage) throws Exception {
        try{
            ResourceBundle langBundle = ResourceBundle.getBundle(ResourcePaths.LANGUAGE_BUNDLE);
            URL url = MainApplication.class.getClassLoader().getResource(ResourcePaths.OVERVIEW_CALENDER_VIEW);
            FXMLLoader fxmlLoader = new FXMLLoader(url, langBundle);
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Techlodge");
            stage.setScene(scene);
            stage.show();
        } catch (MissingResourceException e){
            log.error("Cannot find resource and thus not run app.", e);
            throw e;
        } catch (NullPointerException e){
            log.error("Path for resource is null", e);
            throw e;
        }
    }
}
