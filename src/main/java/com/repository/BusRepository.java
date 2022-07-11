package com.repository;

import com.model.Bus;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class BusRepository implements CrudRepository<Bus> {
    private final List<Bus> buses;

    public BusRepository() {
        buses = new LinkedList<>();
    }

    @Override
    public Bus getById(String id) {
        for (Bus bus : buses) {
            if (bus.getId().equals(id)) {
                return bus;
            }
        }
        return null;
    }

    @Override
    public List<Bus> getAll() {
        return buses;
    }

    @Override
    public boolean save(Bus bus) {
        buses.add(bus);
        return true;
    }

    @Override
    public boolean saveAll(List<Bus> bus) {
        return buses.addAll(bus);
    }

    @Override
    public boolean update(Bus bus) {
        final Bus founded = getById(bus.getId());
        if (founded != null) {
            BusCopy.copy(bus, founded);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        final Iterator<Bus> iterator = buses.iterator();
        while (iterator.hasNext()) {
            final Bus bus = iterator.next();
            if (bus.getId().equals(id)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    private static class BusCopy {
        static void copy(final Bus from, final Bus to) {
            to.setManufacturer(from.getManufacturer());
            to.setModel(from.getModel());
            to.setPassengers(from.getPassengers());
            to.setPrice(from.getPrice());
        }
    }

    public boolean updateByPassengers(int passengers, Bus copyFrom) {
        for (Bus bus : buses) {
            if (bus.getPassengers() == passengers) {
                BusRepository.BusCopy.copy(copyFrom, bus);
            }
        }
        return true;
    }

}
