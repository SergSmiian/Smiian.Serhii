package com.service;

import com.model.vehicle.Bus;
import com.repository.BusRepository;
import com.repository.CrudRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class BusService extends VehicleService<Bus> {
    private final BusRepository busRepository = BusRepository.getInstance();
    private static BusService instance;

    public static BusService getInstance() {
        if (instance == null) {
            instance = new BusService();
        }
        return instance;
    }

    @Override
    CrudRepository<Bus> getRepository() {
        return busRepository;
    }

    @Override
    public Bus createVehicle() {
        return new Bus("Model-" + getRandom().nextInt(1000), getRandomManufacturer(),
                BigDecimal.valueOf(getRandom().nextDouble(1000.0))
                        .setScale(2, RoundingMode.HALF_UP), getRandom().nextInt(350));
    }

}
