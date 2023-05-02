package com.n4ims.hotelsystem.controllers.loaders;

import javafx.scene.control.ChoiceBox;

import java.time.LocalDate;

public interface ComponentContentLoader {
    void loadFreeRooms(ChoiceBox choiceBox, LocalDate fromLocalDate, LocalDate toLocalDate);
    void loadCateringTypes(ChoiceBox choiceBox);
}
