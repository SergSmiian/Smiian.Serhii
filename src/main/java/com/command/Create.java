package com.command;

import com.model.vehicle.Vehicle;
import com.model.vehicle.VehicleType;
import com.service.*;
import com.util.UserInputUtil;

import java.util.*;

public class Create implements Command{
    private static final VehicleServiceFactory VEHICLE_SERVICE_FACTORY = VehicleServiceFactory.getInstance();

    @Override
    public void execute() {

        final List<String> names = VehicleType.getNames();
        int userInput = UserInputUtil.getUserInput("What vehicle type do you want to create? :", names);
        final VehicleType value = VehicleType.valueOf(names.get(userInput));
        Vehicle vehicle = VehicleFactory.getInstance().build(value);
        VehicleService service = VEHICLE_SERVICE_FACTORY.build(value);
        service.save(vehicle);

    }
}
