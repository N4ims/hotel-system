package com.n4ims.hotelsystem.controllers;

import com.n4ims.hotelsystem.controllers.loaders.ComponentContentLoader;
import com.n4ims.hotelsystem.controllers.loaders.ComponentContentLoaderImpl;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.ResourceBundle;

public class BasicController extends Application {
    public static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @FXML
    private Parent navigationBar;
    @FXML
    protected NavigationBarController navigationBarController;

    protected ComponentContentLoader componentContentLoader;

    public BasicController(){
        componentContentLoader = new ComponentContentLoaderImpl();
    }

    @Override
    public void start(Stage stage) throws Exception {
    }

    private void initialize() {

    }

    /***
     * Navigates by loading other views
     * @param sourceScene Scene of the object the event occurred on
     * @param resourcePath Resource path of to the view to navigate to
     */
    protected void navigate(Scene sourceScene, String resourcePath){
        try {
            ResourceBundle resources = ResourceBundle.getBundle("com.n4ims.hotelsystem.i18n.lang");
            URL url = getClass().getClassLoader().getResource(resourcePath);
            assert url != null;
            Parent view = FXMLLoader.load(url, resources);

            Scene scene = new Scene(view);
            Stage stage = (Stage) sourceScene.getWindow();
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();
        } catch (IOException | NullPointerException e) {
            log.error(Arrays.toString(e.getStackTrace()));
            // TODO show user error field
        }
    }

    protected Callback<DatePicker, DateCell> getDayCellFactory(LocalDate minDate, LocalDate maxDate){
        return new Callback<>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);

                        // Disable unallowed dates
                        if (item.isBefore(minDate) || item.isAfter(maxDate)) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;"); // Set a different background color for disabled cells
                        }
                    }
                };
            }
        };
    }

    public NavigationBarController getNavigationBarController() {
        return navigationBarController;
    }
}
