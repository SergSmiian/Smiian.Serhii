package com.service;

import com.model.Manufacturer;
import com.model.Truck;
import com.repository.TruckRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class TruckService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TruckService.class);
    private static final Random RANDOM = new Random();
    private final TruckRepository truckRepository;

    public TruckService(TruckRepository truckRepository) {
        this.truckRepository = truckRepository;
    }

    public List<Truck> createAndSaveTrucks(int count) {
        List<Truck> result = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            final Truck truck = new Truck(
                    "Model-" + RANDOM.nextInt(1000),
                    getRandomManufacturer(),
                    BigDecimal.valueOf(RANDOM.nextDouble(1000.0)),
                    RANDOM.nextDouble(1000)
            );
            result.add(truck);
            truckRepository.save(truck);
            LOGGER.debug("Created truck {}", truck.getId());
        }
        return result;
    }

    public void updateTruck(Truck truck) {
        LOGGER.info("updated truck: {}", truck.getId());
        truckRepository.update(truck);
    }

    public void deleteTruck(Truck truck) {
        LOGGER.info("deleted truck: {}", truck.getId());
        truckRepository.delete(truck.getId());
    }

    private Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public void saveTruck(List<Truck> trucks) {
        LOGGER.info("create {} trucks", trucks.size());
        truckRepository.saveAll(trucks);
    }

    public void printAll() {
        for (Truck truck : truckRepository.getAll()) {
            System.out.println(truck);
        }
        System.out.println("- - - -");
    }

    public Truck findOneById(String id) {
        if (id == null) {
            return truckRepository.getById("");
        } else {
            return truckRepository.getById(id);
        }
    }
}
