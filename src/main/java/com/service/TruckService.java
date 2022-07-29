package com.service;

import com.model.vehicle.Truck;
import com.repository.CrudRepository;
import com.repository.TruckRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class TruckService extends VehicleService<Truck> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TruckService.class);
    private final TruckRepository truckRepository = TruckRepository.getInstance();
    private static TruckService instance;

    public static TruckService getInstance() {
        if (instance == null) {
            instance = new TruckService();
        }
        return instance;
    }
    @Override
    CrudRepository<Truck> getRepository() {
        return truckRepository;
    }
    @Override
    public Truck createVehicle() {
        return new Truck(
                "Model-" + getRandom().nextInt(1000),
                getRandomManufacturer(),
                BigDecimal.valueOf(getRandom().nextDouble(1000.0)).setScale(2, RoundingMode.HALF_UP),
                getRandom().nextDouble(1000));
    }
    private List<Truck> getEmptyTruckList(){
        LOGGER.info("Empty list was born");
        return List.of();
    }
    public void saveTrucks(List<Truck> trucks) {
        trucks = Optional.ofNullable(trucks).orElseGet(this::getEmptyTruckList);
        LOGGER.info("create {} trucks", trucks.size());
        truckRepository.saveAll(trucks);
    }

    public void updateTrucks(List<Truck> trucks) {
        trucks = Optional.ofNullable(trucks).orElse(getEmptyTruckList());
        LOGGER.info("update {} trucks", trucks.size());
        for (Truck truck : trucks){
            truckRepository.update(truck);
        }
    }
}
