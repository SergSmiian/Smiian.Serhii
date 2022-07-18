package com.repository;

import com.model.Bus;
import com.model.Manufacturer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

class BusRepositoryTest {
    private BusRepository target;

    private Bus bus;

    @BeforeEach
    void setUp() {
        target = new BusRepository();
        bus = createSimpleBus();
        target.save(bus);
    }

    private Bus createSimpleBus() {
        return new Bus("Model", Manufacturer.VOLKSWAGEN, BigDecimal.TEN, 50);
    }

    @Test
    void getById_findOne() {
        final Bus actual = target.getById(bus.getId());
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(bus.getId(), actual.getId());
    }

    @Test
    void getById_notFind() {
        final Bus actual = target.getById("1232");
        Assertions.assertNull(actual);
    }

    @Test
    void getById_findOne_manyBuses() {
        final Bus otherBus = createSimpleBus();
        target.save(otherBus);
        final Bus actual = target.getById(bus.getId());
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(bus.getId(), actual.getId());
        Assertions.assertNotEquals(otherBus.getId(), actual.getId());
    }

    @Test
    void getAll() {
        final List<Bus> actual = target.getAll();
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(1, actual.size());
    }

    @Test
    void save_success_notChangePrice() {
        bus.setPrice(BigDecimal.ONE);
        final boolean actual = target.save(bus);
        Assertions.assertTrue(actual);
        final Bus actualBus = target.getById(bus.getId());
        Assertions.assertEquals(BigDecimal.ONE, actualBus.getPrice());
    }

    @Test
    void save_fail() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.save(null));
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
        final boolean actual = target.saveAll(List.of(createSimpleBus()));
        Assertions.assertTrue(actual);
    }

    @Test
    void update_notFound() {
        final Bus otherBus = createSimpleBus();
        final boolean actual = target.update(otherBus);
        Assertions.assertFalse(actual);
    }

    @Test
    void update() {
        bus.setPrice(BigDecimal.TEN);
        final boolean actual = target.update(bus);
        Assertions.assertTrue(actual);
        final Bus actualBus = target.getById(bus.getId());
        Assertions.assertEquals(BigDecimal.TEN, actualBus.getPrice());
    }

    @Test
    void updateByPassengers() {
        final Bus otherBus = createSimpleBus();
        otherBus.setManufacturer(Manufacturer.MERCEDES);
        otherBus.setPrice(BigDecimal.TEN);

        final boolean actual = target.updateByPassengers(bus.getPassengers(), otherBus);
        Assertions.assertTrue(actual);
        final Bus actualBus = target.getById(bus.getId());
        Assertions.assertEquals(Manufacturer.MERCEDES, actualBus.getManufacturer());
        Assertions.assertEquals(BigDecimal.TEN, actualBus.getPrice());
    }
}
