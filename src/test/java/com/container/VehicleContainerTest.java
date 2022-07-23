package com.container;


import com.model.vehicle.Auto;
import com.model.vehicle.Bus;
import com.model.vehicle.Manufacturer;
import com.model.vehicle.Truck;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class VehicleContainerTest {
    private VehicleContainer<Bus> busContainer;
    private VehicleContainer<Auto> autoContainer;
    private VehicleContainer<Truck> truckContainer;
    private Bus bus;
    private Truck truck;
    private Auto auto;
    @BeforeEach
    void setUp() {
        bus = createSimpleBus();
        auto = createSimpleAuto();
        truck = createSimpleTruck();
        busContainer = new VehicleContainer(bus);
        autoContainer = new VehicleContainer(auto);
        truckContainer = new VehicleContainer(truck);
    }
    private Bus createSimpleBus() {
        return new Bus("Model", Manufacturer.VOLKSWAGEN, BigDecimal.valueOf(150000.0), 50);
    }
    private Truck createSimpleTruck() {
        return new Truck("Model", Manufacturer.VOLVO, BigDecimal.valueOf(250000.0), 20000.0);
    }
    private Auto createSimpleAuto() {
        return new Auto("Model", Manufacturer.BMW, BigDecimal.valueOf(30000.0), "Type");
    }

    @Test
    void busDiscountTest(){
        BigDecimal currentPrice = bus.getPrice();
        Bus actual = busContainer.discount();
        Assertions.assertTrue(currentPrice.compareTo(actual.getPrice())>0);
    }
    @Test
    void autoDiscountTest(){
        BigDecimal currentPrice = auto.getPrice();
        Auto actual = autoContainer.discount();
        Assertions.assertTrue(currentPrice.compareTo(actual.getPrice())>0);
    }
    @Test
    void truckDiscountTest(){
        BigDecimal currentPrice = truck.getPrice();
        Truck actual = truckContainer.discount();
        Assertions.assertTrue(currentPrice.compareTo(actual.getPrice())>0);
    }
    @Test
    void busGetTest() {
        Bus actual = busContainer.discount();
        Assertions.assertEquals(bus, actual);
    }
    @Test
    void autoGetTest() {
        Auto actual = autoContainer.discount();
        Assertions.assertEquals(auto, actual);
    }
    @Test
    void truckGetTest() {
        Truck actual = truckContainer.discount();
        Assertions.assertEquals(truck, actual);
    }

    @Test
    void increaseBusTest() {
        BigDecimal currentPrice = bus.getPrice();
        Bus actual = busContainer.increase(Integer.valueOf(10));
        Assertions.assertEquals(currentPrice.add(BigDecimal.TEN),actual.getPrice());
    }
    @Test
    void increaseAutoTest() {
        BigDecimal currentPrice = auto.getPrice();
        Auto actual = autoContainer.increase(Integer.valueOf(10));
        Assertions.assertEquals(currentPrice.add(BigDecimal.TEN),actual.getPrice());
    }
    @Test
    void increaseTruckTest() {
        BigDecimal currentPrice = truck.getPrice();
        Truck actual = truckContainer.increase(Integer.valueOf(10));
        Assertions.assertEquals(currentPrice.add(BigDecimal.TEN),actual.getPrice());
    }
}
