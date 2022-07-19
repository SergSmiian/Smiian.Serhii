package com;

import com.container.VehicleContainer;
import com.model.Auto;
import com.model.Bus;
import com.model.Truck;
import com.model.Vehicle;
import com.repository.AutoRepository;
import com.repository.BusRepository;
import com.repository.TruckRepository;
import com.service.AutoService;
import com.service.BusService;
import com.service.TruckService;
import com.service.VehicleService;

import java.util.List;

public class Main {
    private static final AutoService AUTO_SERVICE = new AutoService(new AutoRepository());
    private static final BusService BUS_SERVICE = new BusService(new BusRepository());
    private static final TruckService TRUCK_SERVICE = new TruckService(new TruckRepository());



    public static void main(String[] args) {

        final List<Auto> autos = AUTO_SERVICE.createAndSave(3);
        AUTO_SERVICE.printAllVehicle();
        Auto auto = autos.get(1);
        auto.setModel("UUUU0000");
        AUTO_SERVICE.updateVehicle(auto);
        AUTO_SERVICE.printAllVehicle();

        AUTO_SERVICE.optionalExamples();

        final List<Bus> buses = BUS_SERVICE.createAndSave(3);
        BUS_SERVICE.printAllVehicle();
        BUS_SERVICE.deleteVehicle(buses.get(2));
        BUS_SERVICE.printAllVehicle();

        final List<Truck> truck = TRUCK_SERVICE.createAndSave(3);
        TRUCK_SERVICE.printAllVehicle();

        VehicleContainer<Bus> busContainer = new VehicleContainer<>(buses.get(1));
        Bus bus = busContainer.discount();
        System.out.println(bus.getPrice());
    }
}