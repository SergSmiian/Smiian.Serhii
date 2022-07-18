package com.service;

import com.BusMatcher;
import com.model.Auto;
import com.model.Bus;
import com.model.Manufacturer;
import com.repository.BusRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

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
        final List<Bus> actual = target.createAndSaveBuses(-1);
        Assertions.assertEquals(0, actual.size());
    }

    @Test
    void createBuses_zeroCount() {
        final List<Bus> actual = target.createAndSaveBuses(0);
        Assertions.assertEquals(0, actual.size());
    }
    @Test
    void createBuses() {
        final List<Bus> actual = target.createAndSaveBuses(5);
        Assertions.assertEquals(5, actual.size());
        Mockito.verify(busRepository, Mockito.times(5))
                .save(Mockito.any());
    }
    @Test
    void printAll_real_object() {
        Mockito.when(busRepository.getAll()).thenCallRealMethod();

        target.printAll();
    }

    @Test
    void saveBuses() {
        List<Bus> buses = List.of(createSimpleBus(), createSimpleBus());
        target.saveBuses(buses);
        Mockito.verify(busRepository).saveAll(Mockito.any());
    }

    @Test
    void printAll() {
        List<Bus> buses = List.of(createSimpleBus(), createSimpleBus());
        Mockito.when(busRepository.getAll()).thenReturn(buses);
        target.printAll();
    }
    private Bus createSimpleBus() {
        return new Bus("Model", Manufacturer.VOLVO, BigDecimal.ZERO, 50);
    }
    @Test
    void findOneById_null1() {
        final Bus expected = createSimpleBus();
        Mockito.when(busRepository.getById("")).thenReturn(expected);
        final Bus actual = target.findOneById(null);
        Assertions.assertEquals(expected.getId(), actual.getId());
    }

    @Test
    void findOneById_null2() {
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        target.findOneById(null);
        Mockito.verify(busRepository).getById(captor.capture());
        Assertions.assertEquals("", captor.getValue());
    }

    @Test
    void updateBus(){
        final Bus expected = createSimpleBus();
        target.updateBus(expected);

        Mockito.verify(busRepository).update(Mockito.argThat(new BusMatcher(expected)));

    }
    @Test
    void deleteBus(){
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        final Bus expected = createSimpleBus();
        target.deleteBus(expected);

        Mockito.verify(busRepository).delete(captor.capture());

        Assertions.assertEquals(expected.getId(), captor.getValue());
    }

    @Test
    void deleteBus_fail(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> target.deleteBus(null));
    }
}
