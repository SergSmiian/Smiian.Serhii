package com.service;

import com.model.Manufacturer;
import com.model.Truck;
import com.repository.TruckRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;

public class TruckService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TruckService.class);
    private static final Random RANDOM = new Random();
    private final TruckRepository truckRepository;

    public TruckService(TruckRepository truckRepository) {
        this.truckRepository = truckRepository;
    }

    public List<Truck> createAndSaveTrucks(Integer count) {
        Optional<Integer> size
                = Optional.ofNullable(count).or(() -> Optional.of(5));
        List<Truck> result = new LinkedList<>();
        for (int i = 0; i < size.get(); i++) {
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
        Optional.ofNullable(truck).orElseThrow(() ->
                new IllegalArgumentException("truck = NULL"));
        LOGGER.info("updated truck: {}", truck.getId());
        truckRepository.update(truck);
    }

    public void deleteTruck(Truck truck) {
        Optional.ofNullable(truck).orElseThrow(() ->
                new IllegalArgumentException("truck = NULL"));
        LOGGER.info("deleted truck: {}", truck.getId());
        truckRepository.delete(truck.getId());
    }

    private Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
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
    private List<Truck> getEmptyTruckList(){
        LOGGER.info("Empty list was born");
        return List.of();
    }
    public void printAll() {
        for (Truck truck : truckRepository.getAll()) {
            System.out.println(truck);
        }
        System.out.println("-".repeat(5));
    }

    public Optional<Truck> findOneById(String id) {
        id = Optional.ofNullable(id).orElse("");
        return truckRepository.findById(id);
    }
}
