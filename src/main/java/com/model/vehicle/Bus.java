package com.model.vehicle;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Comparator;

@Getter
@Setter
public class Bus extends Vehicle {
    private int passengers;

    public Bus(String model, Manufacturer manufacturer, BigDecimal price, int passengers) {
        super(model, manufacturer, price);
        this.passengers = passengers;
    }
    @Override
    public String toString() {
        return "Bus{" +
                "Passengers='" + passengers + '\'' +
                ", id='" + id + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", manufacturer=" + manufacturer +
                '}'+ '\n';
    }

    public static class BusPriceComparator implements Comparator<Bus> {
        @Override
        public int compare(Bus o1, Bus o2) {
            return o1.getPrice().compareTo(o2.getPrice());
        }
    }
    public static class BusManufacturerComparator implements Comparator<Bus> {
        @Override
        public int compare(Bus o1, Bus o2) {
            return o1.getManufacturer().compareTo(o2.getManufacturer());
        }
    }
    public static class BusPassengersComparator implements Comparator<Bus> {
        @Override
        public int compare(Bus o1, Bus o2) {
          return o1.getPassengers() - o2.getPassengers();
        }
    }
}
