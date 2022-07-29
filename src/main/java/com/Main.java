package com;

import com.command.Actions;
import com.command.Command;
import com.command.Create;
import com.container.VehicleContainer;
import com.model.BinaryTree;
import com.model.Garage;
import com.model.vehicle.*;
import com.repository.AutoRepository;
import com.repository.BusRepository;
import com.repository.TruckRepository;
import com.service.AutoService;
import com.service.BusService;
import com.service.TruckService;
import com.util.UserInputUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

public class Main {
    private static final AutoService AUTO_SERVICE = AutoService.getInstance();
    private static final BusService BUS_SERVICE = BusService.getInstance();
    private static final TruckService TRUCK_SERVICE = TruckService.getInstance();
    private static final Random RANDOM = new Random();

    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
/*
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

*/

        /*
        //Реализовать свою коллекцию простого бинарного дерево.

        BinaryTree<Bus> busses = new BinaryTree<>(new PriceComparator());
        List<Bus> elements = BUS_SERVICE.createAndSave(5);
        for (Bus bus: elements) {
            busses.add(bus);
        }
        System.out.println(busses.getTreePresentation());
        System.out.println(busses.sumPrice(true));
        System.out.println(busses.sumPrice(false));
        */

        //Реализовать консольное меню по аналогии с тем
        final Create create = new Create();
        final Actions[] actions = Actions.values();
        final List<String> names = getNames(actions);

        Command command;
        do {
            command = executeCommand(actions, names);
        } while (command != null);
    }

    private static Command executeCommand(Actions[] actions, List<String> names) {
        int userInput = UserInputUtil.getUserInput("What you want:", names);
        final Actions action = actions[userInput];
        return action.execute();
    }

    private static List<String> getNames(Actions[] actions) {
        final List<String> names = new ArrayList<>(actions.length);
        for (Actions action : actions) {
            names.add(action.getName());
        }
        return names;
    }

    static class PriceComparator implements Comparator<Vehicle> {
        @Override
        public int compare(Vehicle o1, Vehicle o2) {
            return o1.getPrice().compareTo(o2.getPrice());
        }
    }
}