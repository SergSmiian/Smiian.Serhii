package com.service;

import com.model.vehicle.Bus;
import com.model.vehicle.Manufacturer;
import com.repository.BusRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

class BusServiceTest {

    private BusService target;
    private BusRepository busRepository;

    @BeforeEach
    void setUp() {
        busRepository = Mockito.mock(BusRepository.class);
        target = new BusService(busRepository);
    }
    @Test
    void createBuses_negativeCount() {
        final List<Bus> actual = target.createAndSave(-1);
        Assertions.assertEquals(0, actual.size());
    }

    @Test
    void createBuses_zeroCount() {
        final List<Bus> actual = target.createAndSave(0);
        Assertions.assertEquals(0, actual.size());
    }
    @Test
    void createBuses() {
        final List<Bus> actual = target.createAndSave(5);
        Assertions.assertEquals(5, actual.size());
        Mockito.verify(busRepository, Mockito.times(5))
                .save(Mockito.any());
    }
    @Test
    void saveBuses() {
        List<Bus> buses = List.of(createSimpleBus(), createSimpleBus());
        target.saveVehicle(buses);
        Mockito.verify(busRepository).saveAll(Mockito.any());
    }

    @Test
    void printAll() {
        List<Bus> buses = List.of(createSimpleBus(), createSimpleBus());
        Mockito.when(busRepository.getAll()).thenReturn(buses);
        target.printAllVehicle();
    }
    private Bus createSimpleBus() {
        return new Bus("Model", Manufacturer.VOLVO, BigDecimal.ZERO, 50);
    }
    @Test
    void findOneById_null1() {
        final Bus expected = createSimpleBus();
        Mockito.when(busRepository.findById("")).thenReturn(Optional.of(expected));
        final Optional<Bus> actual = target.findOneById(null);
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(expected.getId(), actual.get().getId());
    }

    @Test
    void findOneById_null2() {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        target.findOneById(null);
        Mockito.verify(busRepository).findById(captor.capture());
        Assertions.assertEquals("", captor.getValue());
    }

    @Test
    void updateBus(){
        final Bus expected = createSimpleBus();
        target.updateVehicle(expected);

        Mockito.verify(busRepository).update(Mockito.argThat(actual -> actual.getId().equals(expected.getId()) &&
                actual.getManufacturer().equals(expected.getManufacturer()) &&
                actual.getModel().equals(expected.getModel()) &&
                actual.getPrice().equals(expected.getPrice()) &&
                actual.getPassengers() == expected.getPassengers()));

    }
    @Test
    void deleteBus(){
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        final Bus expected = createSimpleBus();
        target.deleteVehicle(expected);

        Mockito.verify(busRepository).delete(captor.capture());

        Assertions.assertEquals(expected.getId(), captor.getValue());
    }

    @Test
    void deleteBus_fail(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.deleteVehicle(null));
    }
}
