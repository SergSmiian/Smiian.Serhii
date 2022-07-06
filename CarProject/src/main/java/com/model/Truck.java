package com.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Truck extends Vehicle {
    private Double carryingСapacity;

    public Truck(String model, Manufacturer manufacturer, BigDecimal price, Double carryingСapacity) {
        super(model, manufacturer, price);
        this.carryingСapacity = carryingСapacity;
    }

    @Override
    public String toString() {
        return "Truck{" +
                "СarryingСapacity='" + carryingСapacity + '\'' +
                ", id='" + id + '\'' +
                ", model='" + model + '\'' +
                ", price=" + price +
                ", manufacturer=" + manufacturer +
                '}';
    }
}
