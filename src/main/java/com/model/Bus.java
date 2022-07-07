package com.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

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
                '}';
    }
}
