package com.n4ims.hotelsystem.controllers.converters;

import com.n4ims.hotelsystem.entities.CateringTypeEntity;
import javafx.util.StringConverter;

public class CateringTypeEntityConverter extends StringConverter<CateringTypeEntity> {

    @Override
    public String toString(CateringTypeEntity cateringTypeEntity) {
        return cateringTypeEntity == null ? "" : cateringTypeEntity.getType();
    }

    @Override
    public CateringTypeEntity fromString(String s) {
        return null;
    }
}
