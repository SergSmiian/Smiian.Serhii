package com.repository;

import com.model.Bus;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class BusRepository implements CrudRepository<Bus> {
    private final List<Bus> buses;

    public BusRepository() {
        buses = new LinkedList<>();
    }

    @Override
    public Optional<Bus> findById(String id) {
        for (Bus bus : buses) {
            if (bus.getId().equals(id)) {
                return Optional.of(bus);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Bus> getAll() {
        return buses;
    }

    @Override
    public boolean save(Bus bus) {
        bus = Optional.ofNullable(bus).orElseThrow(() ->
                new IllegalArgumentException("bus = NULL"));
        buses.add(bus);
        return true;
    }

    @Override
    public boolean saveAll(List<Bus> bus) {
        if (bus == null || bus.isEmpty()) {
            return false;
        }
        return buses.addAll(bus);
    }

    @Override
    public boolean update(Bus bus) {
        Optional.ofNullable(bus).orElseThrow(() ->
                new IllegalArgumentException("bus = NULL"));
        final Optional<Bus> optionalBus = findById(bus.getId());
        if (optionalBus.isPresent()) {
            optionalBus.ifPresent(founded -> BusRepository.BusCopy.copy(bus, founded));
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
        Optional.ofNullable(copyFrom).orElseThrow(() ->
                new IllegalArgumentException("bus = NULL"));
        for (Bus bus : buses) {
            if (bus.getPassengers() == passengers) {
                BusRepository.BusCopy.copy(copyFrom, bus);
            }
        }
        return true;
    }

}
