package com.repository;

import com.model.vehicle.Manufacturer;
import com.model.vehicle.Truck;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TruckRepositoryTest {

    private TruckRepository target;

    private Truck truck;

    @BeforeEach
    void setUp() {
        target = new TruckRepository();
        truck = createSimpleTruck();
        target.save(truck);
    }

    private Truck createSimpleTruck() {
        return new Truck("Model", Manufacturer.VOLVO, BigDecimal.ZERO, 20000.0);
    }

    @Test
    void findById_findOne() {
        final Optional<Truck> actual = target.findById(truck.getId());
        Assertions.assertNotNull(actual);
        assertEquals(truck.getId(), actual.get().getId());
    }

    @Test
    void findById_notFind() {
        final Optional<Truck> actual = target.findById("1111");
        Assertions.assertTrue(actual.isEmpty());
    }

    @Test
    void findById_findOne_manyTrucks() {
        final Truck otherTruck = createSimpleTruck();
        target.save(otherTruck);
        final Optional<Truck> actual = target.findById(truck.getId());
        Assertions.assertNotNull(actual);
        assertEquals(truck.getId(), actual.get().getId());
        Assertions.assertNotEquals(otherTruck.getId(), actual.get().getId());
    }

    @Test
    void getAll() {
        final List<Truck> actual = target.getAll();
        Assertions.assertNotNull(actual);
        assertEquals(1, actual.size());
    }

    @Test
    void save_success_notChangePrice() {
        truck.setPrice(BigDecimal.ONE);
        final boolean actual = target.save(truck);
        Assertions.assertTrue(actual);
        final Optional<Truck> actualTruck = target.findById(truck.getId());
        assertEquals(BigDecimal.ONE, actualTruck.get().getPrice());
    }

    @Test
    void save_success() {
        boolean result = target.save(truck);
        Assertions.assertTrue(result);
        final Optional<Truck> actual = target.findById(truck.getId());
        assertEquals(truck.getId(), actual.get().getId());
        assertEquals(truck.getPrice(), actual.get().getPrice());
        assertEquals(truck.getManufacturer(), actual.get().getManufacturer());
        assertEquals(truck.getModel(), actual.get().getModel());
        assertEquals(truck.getCarryingCapacity(), actual.get().getCarryingCapacity());
    }

    @Test
    void save_fail() {
        boolean result = target.save(null);
        Assertions.assertFalse(result);
    }

    @Test
    void saveAll_null() {
        final boolean actual = target.saveAll(null);
        Assertions.assertFalse(actual);
    }

    @Test
    void saveAll_emptyList() {
        final boolean actual = target.saveAll(Collections.emptyList());
        Assertions.assertFalse(actual);
    }

    @Test
    void saveAll() {
        final boolean actual = target.saveAll(List.of(createSimpleTruck()));
        Assertions.assertTrue(actual);
    }

    @Test
    void update_notFound() {
        final Truck otherTruck = createSimpleTruck();
        final boolean actual = target.update(otherTruck);
        Assertions.assertFalse(actual);
    }

    @Test
    void update_truck_null() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.update(null));
    }

    @Test
    void update() {
        truck.setPrice(BigDecimal.TEN);
        final boolean actual = target.update(truck);
        Assertions.assertTrue(actual);
        final Optional<Truck> actualTruck = target.findById(truck.getId());
        assertEquals(BigDecimal.TEN, actualTruck.get().getPrice());
    }

    @Test
    void updateByCarryingCapacity() {
        final Truck otherTruck = createSimpleTruck();
        otherTruck.setManufacturer(Manufacturer.KIA);
        otherTruck.setPrice(BigDecimal.TEN);
        final boolean actual = target.updateByCarryingCapacity(truck.getCarryingCapacity(), otherTruck);
        Assertions.assertTrue(actual);
        final Optional<Truck> actualTruck = target.findById(truck.getId());
        assertEquals(Manufacturer.KIA, actualTruck.get().getManufacturer());
        assertEquals(BigDecimal.TEN, actualTruck.get().getPrice());
    }

    @Test
    void updateByCarryingCapacityNothingToUpdate() {
        final Truck otherTruck = createSimpleTruck();
        otherTruck.setManufacturer(Manufacturer.RENAULT);
        otherTruck.setPrice(BigDecimal.TEN);
        Manufacturer manufacturer = truck.getManufacturer();
        BigDecimal price = truck.getPrice();
        final boolean actual = target.updateByCarryingCapacity(truck.getCarryingCapacity() + 1, otherTruck);
        Assertions.assertTrue(actual);
        final Optional<Truck> actualTruck = target.findById(truck.getId());
        assertEquals(manufacturer, actualTruck.get().getManufacturer());
        assertEquals(price, actualTruck.get().getPrice());
    }
}
