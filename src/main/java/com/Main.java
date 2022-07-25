package com;

import com.container.VehicleContainer;
import com.model.Garage;
import com.model.vehicle.Auto;
import com.model.vehicle.Bus;
import com.model.vehicle.Manufacturer;
import com.model.vehicle.Truck;
import com.repository.AutoRepository;
import com.repository.BusRepository;
import com.repository.TruckRepository;
import com.service.AutoService;
import com.service.BusService;
import com.service.TruckService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class Main {
    private static final AutoService AUTO_SERVICE = new AutoService(new AutoRepository());
    private static final BusService BUS_SERVICE = new BusService(new BusRepository());
    private static final TruckService TRUCK_SERVICE = new TruckService(new TruckRepository());
    private static final Random RANDOM = new Random();


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




        Garage garage = new Garage<Auto>();
        List<Auto> autos1 = AUTO_SERVICE.createAndSave(5);
        for (int i=0;i< autos1.size();i++) {
           garage.addToHead(autos1.get(i), RANDOM.nextInt(5),
                   LocalDate.of(RANDOM.nextInt(1940,2022),2,1));
        }
        System.out.println(garage);

        //System.out.println(garage.remove(3));
        System.out.println(garage.getFirstDate());
        System.out.println(garage.getLastDate());

        garage.set(3, new Auto("Model", Manufacturer.BMW, BigDecimal.ZERO, "Type"));
        System.out.println(garage);

        //компаратор
        List<Bus> buses1 = BUS_SERVICE.createAndSave(5);
        System.out.println(buses1);
        //Comparator <Bus> busComparator = new Bus.BusManufacturerComparator().thenComparing(new Bus.BusPriceComparator().thenComparing(new Bus.BusPassengersComparator()));
        Comparator <Bus> busComparator = new Bus.BusManufacturerComparator().thenComparing(new Bus.BusPassengersComparator().thenComparing(new Bus.BusManufacturerComparator()));
        TreeSet<Bus> busList1 = new TreeSet<>(busComparator);

        busList1.addAll(buses1);
        System.out.println(busList1);

    }
}