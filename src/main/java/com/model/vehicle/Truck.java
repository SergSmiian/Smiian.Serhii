package com.model.vehicle;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Truck extends Vehicle {
    private Double carryingCapacity;

    public Truck(String model, Manufacturer manufacturer, BigDecimal price, Double carryingCapacity) {
        super(model, manufacturer, price, VehicleType.TRUCK);
        this.carryingCapacity = carryingCapacity;
    }

    @Override
    public String toString() {
        return "Truck{" +
                "CarryingCapacity='" + carryingCapacity + '\'' +
                ", id='" + id + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", manufacturer=" + manufacturer +
                '}';
    }
}
