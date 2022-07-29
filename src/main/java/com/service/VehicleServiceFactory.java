package com.service;

import com.model.vehicle.*;

public class VehicleServiceFactory {
    private static final BusService BUS_SERVICE = BusService.getInstance();
    private static final AutoService AUTO_SERVICE = AutoService.getInstance();
    private static final TruckService TRUCK_SERVICE = TruckService.getInstance();
    private static VehicleServiceFactory instance;
    private VehicleServiceFactory() {
    }

    public static VehicleServiceFactory getInstance() {
        if (instance == null) {
            instance = new VehicleServiceFactory();
        }
        return instance;
    }
    public  VehicleService build(VehicleType type){
        return switch (type){
            case BUS -> BUS_SERVICE;
            case AUTO -> AUTO_SERVICE;
            case TRUCK -> TRUCK_SERVICE;
            default -> throw new IllegalArgumentException("Cannot build" + type);
        };
    }
}
