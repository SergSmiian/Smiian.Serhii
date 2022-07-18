package com;

import com.model.Auto;
import com.model.Bus;
import com.model.Truck;
import com.repository.AutoRepository;
import com.repository.BusRepository;
import com.repository.TruckRepository;
import com.service.AutoService;
import com.service.BusService;
import com.service.TruckService;

import java.util.List;

public class Main {
    private static final AutoService AUTO_SERVICE = new AutoService(new AutoRepository());
    private static final BusService BUS_SERVICE = new BusService(new BusRepository());
    private static final TruckService TRUCK_SERVICE = new TruckService(new TruckRepository());

    public static void main(String[] args) {
        final List<Auto> autos = AUTO_SERVICE.createAndSaveAutos(3);
        AUTO_SERVICE.printAll();
        // TODO: 03/07/22 add test
        Auto auto = autos.get(1);
        auto.setModel("UUUU0000");
        AUTO_SERVICE.updateAuto(auto);
        AUTO_SERVICE.printAll();

        final List<Bus> buses = BUS_SERVICE.createAndSaveBuses(3);
        BUS_SERVICE.printAll();
        BUS_SERVICE.deleteBus(buses.get(2));
        BUS_SERVICE.printAll();

        final List<Truck> truck = TRUCK_SERVICE.createAndSaveTrucks(3);
        TRUCK_SERVICE.printAll();
    }
}