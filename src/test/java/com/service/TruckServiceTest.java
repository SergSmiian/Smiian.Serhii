package com.service;

import com.model.Manufacturer;
import com.model.Truck;
import com.repository.TruckRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

class TruckServiceTest {

    private TruckService target;
    private TruckRepository truckRepository;

    @BeforeEach
    void setUp() {
        truckRepository = Mockito.mock(TruckRepository.class);
        target = new TruckService(truckRepository);
    }

    @Test
    void createTrucks_negativeCount() {
        final List<Truck> actual = target.createAndSaveTrucks(-1);
        Assertions.assertEquals(0, actual.size());
    }

    @Test
    void createTrucks_zeroCount() {
        final List<Truck> actual = target.createAndSaveTrucks(0);
        Assertions.assertEquals(0, actual.size());
    }

    @Test
    void createTrucks() {
        final List<Truck> actual = target.createAndSaveTrucks(5);
        Assertions.assertEquals(5, actual.size());
        Mockito.verify(truckRepository, Mockito.times(5))
                .save(Mockito.any());
    }

    @Test
    void saveTrucks() {
        List<Truck> truck = List.of(createSimpleTruck(), createSimpleTruck());
        target.saveTruck(truck);
        Mockito.verify(truckRepository).saveAll(Mockito.any());
    }

    @Test
    void printAll() {
        List<Truck> autos = List.of(createSimpleTruck(), createSimpleTruck());
        Mockito.when(truckRepository.getAll()).thenReturn(autos);
        target.printAll();
    }

    private Truck createSimpleTruck() {
        return new Truck("Model", Manufacturer.RENAULT, BigDecimal.ZERO, 15000.0);
    }

    @Test
    void findOneById_null1() {
        final Truck expected = createSimpleTruck();
        Mockito.when(truckRepository.getById("")).thenReturn(expected);
        final Truck actual = target.findOneById(null);
        Assertions.assertEquals(expected.getId(), actual.getId());
    }

    @Test
    void findOneById_null2() {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        target.findOneById(null);
        Mockito.verify(truckRepository).getById(captor.capture());
        Assertions.assertEquals("", captor.getValue());
    }
}
