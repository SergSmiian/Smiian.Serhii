package com.repository;

import com.model.Truck;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


public class TruckRepository implements CrudRepository<Truck> {
    private final List<Truck> trucks;
    private static final Logger LOGGER = LoggerFactory.getLogger(TruckRepository.class);

    public TruckRepository() {
        trucks = new LinkedList<>();
    }

    @Override
    public Optional<Truck> findById(String id) {
        for (Truck truck : trucks) {
            if (truck.getId().equals(id)) {
                return Optional.of(truck);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Truck> getAll() {
        return trucks;
    }

    @Override
    public boolean save(Truck truck) {
        Optional<Truck> value = Optional.ofNullable(truck);
        value.ifPresentOrElse(trucks::add, () -> LOGGER.info("Attempt to save unexisting truck"));

        return value.isPresent();
    }

    @Override
    public boolean saveAll(List<Truck> trucksToSave) {
        final Optional<List<Truck>> value = Optional.ofNullable(trucksToSave);
        value.ifPresent(trucks::addAll);
        if (value.isPresent()) {
            return !trucksToSave.isEmpty();
        }
        return false;
    }

    @Override
    public boolean update(Truck truck) {
        Optional.ofNullable(truck).orElseThrow(() ->
                new IllegalArgumentException("truck = NULL"));

        final Optional<Truck> optionalTruck = findById(truck.getId());
        if (optionalTruck.isPresent()) {
            optionalTruck.ifPresent(founded -> TruckRepository.TruckCopy.copy(truck, founded));
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        final Iterator<Truck> iterator = trucks.iterator();
        while (iterator.hasNext()) {
            final Truck truck = iterator.next();
            if (truck.getId().equals(id)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    private static class TruckCopy {
        static void copy(final Truck from, final Truck to) {
            to.setManufacturer(from.getManufacturer());
            to.setModel(from.getModel());
            to.setCarryingCapacity(from.getCarryingCapacity());
            to.setPrice(from.getPrice());
        }
    }
    public boolean updateByCarryingCapacity (double carryingCapacity, Truck copyFrom){
        if (copyFrom == null){
            throw new IllegalArgumentException("copyFrom == null");
        }
        for (Truck truck : trucks) {
            Optional <Truck> truckOptional = Optional.of(truck);
            truckOptional.map(Truck::getCarryingCapacity)
                    .filter(capacity -> capacity == carryingCapacity)
                    .ifPresent(capacity->TruckRepository.TruckCopy.copy(copyFrom, truck));
        }
        return true;
    }
}
