package com.service;

import com.model.vehicle.Manufacturer;
import com.model.vehicle.Truck;
import com.repository.AutoRepository;
import com.repository.TruckRepository;
import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

class TruckServiceTest {

    static private TruckService target;
    static private TruckRepository truckRepository;
    private static MockedStatic<TruckRepository> mockedSettings;

    @BeforeAll
    static void setUp() {
        truckRepository = Mockito.mock(TruckRepository.class);
        mockedSettings =
                Mockito.mockStatic(TruckRepository.class);
        mockedSettings.when(() -> TruckRepository.getInstance()).thenReturn(truckRepository);
        target = TruckService.getInstance();
    }
    @AfterAll
    static void close() {
        mockedSettings.close();
    }
    @AfterEach
    void reset(){
        Mockito.reset(truckRepository);
    }
    @Test
    void createTrucks_negativeCount() {
        final List<Truck> actual = target.createAndSave(-1);
        Assertions.assertEquals(0, actual.size());
    }

    @Test
    void createTrucks_zeroCount() {
        final List<Truck> actual = target.createAndSave(0);
        Assertions.assertEquals(0, actual.size());
    }

    @Test
    void createTrucks() {
        final List<Truck> actual = target.createAndSave(5);
        Assertions.assertEquals(5, actual.size());
        Mockito.verify(truckRepository, Mockito.times(5))
                .save(Mockito.any());
    }

    @Test
    void saveTrucks() {
        List<Truck> truck = List.of(createSimpleTruck(), createSimpleTruck());
        target.saveVehicles(truck);
        Mockito.verify(truckRepository).saveAll(Mockito.any());
    }
    @Test
    void saveTrucksNull() {
        target.saveTrucks(null);
        Mockito.verify(truckRepository).saveAll(Mockito.any());
    }
    @Test
    void updateTrucks() {
        List<Truck> truck = List.of(createSimpleTruck(), createSimpleTruck(), createSimpleTruck());
        target.updateTrucks(truck);
        Mockito.verify(truckRepository, Mockito.times(3)).update(Mockito.any());
    }
    @Test
    void updateTrucksNull() {
        target.updateTrucks(null);
        Mockito.verify(truckRepository,Mockito.times(0)).update(Mockito.any());
    }
    @Test
    void printAll() {
        List<Truck> autos = List.of(createSimpleTruck(), createSimpleTruck());
        Mockito.when(truckRepository.getAll()).thenReturn(autos);
        target.printAllVehicle();
    }

    private Truck createSimpleTruck() {
        return new Truck("Model", Manufacturer.RENAULT, BigDecimal.ZERO, 15000.0);
    }

    @Test
    void findOneById_null1() {
        final Truck expected = createSimpleTruck();
        Mockito.when(truckRepository.findById("")).thenReturn(Optional.of(expected));
        final Optional<Truck> actual = target.findOneById(null);
        Assertions.assertTrue(actual.isPresent());
        Assertions.assertEquals(expected.getId(), actual.get().getId());
    }

    @Test
    void findOneById_null2() {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        target.findOneById(null);
        Mockito.verify(truckRepository).findById(captor.capture());
        Assertions.assertEquals("", captor.getValue());
    }
    @Test
    void findOneById() {
        String expectedId = "555";
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        target.findOneById(expectedId);
        Mockito.verify(truckRepository).findById(captor.capture());
        Assertions.assertEquals(expectedId, captor.getValue());
    }
}
