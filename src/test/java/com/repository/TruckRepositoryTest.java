package com.repository;

import com.model.Manufacturer;
import com.model.Truck;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

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
    void getById_findOne() {
        final Truck actual = target.getById(truck.getId());
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(truck.getId(), actual.getId());
    }

    @Test
    void getById_notFind() {
        final Truck actual = target.getById("1111");
        Assertions.assertNull(actual);
    }

    @Test
    void getById_findOne_manyTrucks() {
        final Truck otherTruck = createSimpleTruck();
        target.save(otherTruck);
        final Truck actual = target.getById(truck.getId());
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(truck.getId(), actual.getId());
        Assertions.assertNotEquals(otherTruck.getId(), actual.getId());
    }

    @Test
    void getAll() {
        final List<Truck> actual = target.getAll();
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(1, actual.size());
    }

    @Test
    void save_success_notChangePrice() {
        truck.setPrice(BigDecimal.ONE);
        final boolean actual = target.save(truck);
        Assertions.assertTrue(actual);
        final Truck actualTruck = target.getById(truck.getId());
        Assertions.assertEquals(BigDecimal.ONE, actualTruck.getPrice());
    }

    @Test
    void save_fail() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.save(null));
    }

    @Test
    void save_success_changePrice() {
        target.save(truck);
        final Truck actual = target.getById(truck.getId());
        Assertions.assertEquals(BigDecimal.valueOf(-1), actual.getPrice());
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
    void update() {
        truck.setPrice(BigDecimal.TEN);
        final boolean actual = target.update(truck);
        Assertions.assertTrue(actual);
        final Truck actualTruck = target.getById(truck.getId());
        Assertions.assertEquals(BigDecimal.TEN, actualTruck.getPrice());
    }

    @Test
    void updateByCarryingCapacity() {
        final Truck otherTruck = createSimpleTruck();
        otherTruck.setManufacturer(Manufacturer.KIA);
        otherTruck.setPrice(BigDecimal.TEN);
        final boolean actual = target.updateByCarryingCapacity(truck.getCarryingCapacity(), otherTruck);
        Assertions.assertTrue(actual);
        final Truck actualTruck = target.getById(truck.getId());
        Assertions.assertEquals(Manufacturer.RENAULT, actualTruck.getManufacturer());
        Assertions.assertEquals(BigDecimal.TEN, actualTruck.getPrice());
    }

 }
