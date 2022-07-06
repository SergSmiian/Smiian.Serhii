package com;

import com.model.Auto;
import com.model.Bus;
import com.model.Truck;
import com.service.AutoService;
import com.service.BusService;
import com.service.TruckService;

import java.util.List;

public class Main {
    private static final AutoService AUTO_SERVICE = new AutoService();
    private static final BusService BUS_SERVICE = new BusService();
    private static final TruckService TRUCK_SERVICE = new TruckService();

    public static void main(String[] args) {
        final List<Auto> autos = AUTO_SERVICE.createAutos(3);
        AUTO_SERVICE.saveAutos(autos);
        AUTO_SERVICE.printAll();
        // TODO: 03/07/22 add test
        Auto auto = autos.get(1);
        auto.setModel("UUUU0000");
        AUTO_SERVICE.updateAuto(auto);
        AUTO_SERVICE.printAll();

        final List<Bus> buses = BUS_SERVICE.createBuses(3);
        BUS_SERVICE.saveBuses(buses);
        BUS_SERVICE.printAll();
        BUS_SERVICE.deleteBus(buses.get(2));
        BUS_SERVICE.printAll();

        final List<Truck> truck = TRUCK_SERVICE.createTrucks(3);
        TRUCK_SERVICE.saveTruck(truck);
        TRUCK_SERVICE.printAll();
    }
}