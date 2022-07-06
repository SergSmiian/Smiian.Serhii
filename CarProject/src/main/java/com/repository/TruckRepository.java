package com.repository;

import com.model.Truck;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TruckRepository implements CrudRepository<Truck> {
    private final List<Truck> trucks;

    public TruckRepository() {
        trucks = new LinkedList<>();
    }

    @Override
    public Truck getById(String id) {
        for (Truck truck : trucks) {
            if (truck.getId().equals(id)) {
                return truck;
            }
        }
        return null;
    }

    @Override
    public List<Truck> getAll() {
        return trucks;
    }

    @Override
    public boolean create(Truck truck) {
        trucks.add(truck);
        return true;
    }

    @Override
    public boolean create(List<Truck> truck) {
        return trucks.addAll(truck);
    }

    @Override
    public boolean update(Truck truck) {
        final Truck founded = getById(truck.getId());
        if (founded != null) {
            TruckRepository.TruckCopy.copy(truck, founded);
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
            to.setCarryingСapacity(from.getCarryingСapacity());
            to.setPrice(from.getPrice());
        }
    }
}
