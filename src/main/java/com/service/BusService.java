package com.service;

import com.model.Bus;
import com.repository.BusRepository;
import com.repository.CrudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;

public class BusService extends VehicleService<Bus> {
    private static final Logger LOGGER = LoggerFactory.getLogger(BusService.class);
    private final BusRepository busRepository;
    public BusService(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    @Override
    CrudRepository<Bus> getRepository() {
        return busRepository;
    }

    @Override
    Bus createVehicle() {
        return new Bus(
                "Model-" + getRandom().nextInt(1000),
                getRandomManufacturer(),
                BigDecimal.valueOf(getRandom().nextDouble(1000.0)),
                getRandom().nextInt(350));
    }

}
