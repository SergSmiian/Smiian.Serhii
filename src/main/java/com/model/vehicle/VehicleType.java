package com.model.vehicle;

import java.util.ArrayList;
import java.util.List;

public enum VehicleType {
    AUTO, BUS, TRUCK;

    public static List<String> getNames() {
        VehicleType[] values = values();
        final List<String> names = new ArrayList<>(values.length);
        for (VehicleType type : values) {
            names.add(type.name());
        }
        return names;
    }

}
