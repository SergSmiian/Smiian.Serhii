package com.command;

import com.model.vehicle.Vehicle;
import com.model.vehicle.VehicleType;
import com.service.*;
import com.util.UserInputUtil;
import java.util.ArrayList;
import java.util.List;

public class Remove implements Command{
    private static final VehicleServiceFactory VEHICLE_SERVICE_FACTORY = VehicleServiceFactory.getInstance();
    @Override
    public void execute() {
        final List<String> names = VehicleType.getNames();
        int userInput = UserInputUtil.getUserInput("What do you want to remove? ", names);
        final VehicleType value = VehicleType.valueOf(names.get(userInput));

        VehicleService service = VEHICLE_SERVICE_FACTORY.build(value);

        final List<? extends Vehicle> vehicles = service.getAllVehicle();
        List<String> vehiclesString = new ArrayList<>(vehicles.size());
        for (int i = 0; i < vehicles.size(); i++) {
            vehiclesString.add(String.valueOf(vehicles.get(i)));
        }
        userInput = UserInputUtil.getUserInput("Make your choice ", vehiclesString);
        Vehicle vehicleToUpdate = vehicles.get(userInput);
        service.deleteVehicle(vehicleToUpdate);
    }

}
