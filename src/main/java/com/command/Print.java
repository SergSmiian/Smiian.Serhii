package com.command;

import com.model.vehicle.Vehicle;
import com.model.vehicle.VehicleType;
import com.service.*;
import com.util.UserInputUtil;

import java.util.List;

public class Print implements Command{
    private static final VehicleServiceFactory VEHICLE_SERVICE_FACTORY = VehicleServiceFactory.getInstance();
    @Override
    public void execute() {
        final List<String> names = VehicleType.getNames();
        final int userInput = UserInputUtil.getUserInput("What vehicle type you want to print:", names);
        final VehicleType value = VehicleType.valueOf(names.get(userInput));

        VehicleService<?> service = VEHICLE_SERVICE_FACTORY.build(value);
        final List<? extends Vehicle> vehicles = service.getAllVehicle();
        System.out.println(vehicles);

    }
}
