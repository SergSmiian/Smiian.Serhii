package com.service;

import com.model.vehicle.Manufacturer;
import com.model.vehicle.Vehicle;
import com.repository.CrudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public abstract class VehicleService<T extends Vehicle> {
    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleService.class);
    private static final Random RANDOM = new Random();
    abstract CrudRepository<T> getRepository();
    abstract T createVehicle();

    protected Random getRandom (){
        return RANDOM;
    }
    public List<T> createAndSave(int count) {
        List<T> result = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            final T vehicle = createVehicle();
            result.add(vehicle);
            getRepository().save(vehicle);
            //LOGGER.debug("Created vehicle {}", vehicle.getId());
        }
        return result;
    }
    public void updateVehicle(T vehicle) {
        Optional.ofNullable(vehicle).orElseThrow(() ->
                new IllegalArgumentException("vehicle = NULL"));
        LOGGER.info("updated vehicle {}", vehicle.getId());
        getRepository().update(vehicle);
    }

    protected Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }
    public void deleteVehicle(T vehicle) {
        Optional.ofNullable(vehicle).orElseThrow(() ->
                new IllegalArgumentException("vehicle = NULL"));
        LOGGER.info("deleted vehicle: {}", vehicle.getId());
        getRepository().delete(vehicle.getId());
    }
    public void saveVehicle(List <T> vehicles) {
        Optional.ofNullable(vehicles).orElseThrow(() ->
                new IllegalArgumentException("vehicle = NULL"));
        LOGGER.info("Save {} vehicles", vehicles.size());
        getRepository().saveAll(vehicles);
    }
    public void printAllVehicle() {
        for (T vehicle : getRepository().getAll()) {
            LOGGER.info(String.valueOf(vehicle));
        }
        LOGGER.info("-".repeat(13));
    }
    public Optional<T> findOneById(String id) {
        id = Optional.ofNullable(id).orElse("");
        return getRepository().findById(id);
    }
}
