package com.container;

import com.model.vehicle.Vehicle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Random;

import java.math.BigDecimal;

public class VehicleContainer<T extends Vehicle> {
    private static final Logger LOGGER = LoggerFactory.getLogger(VehicleContainer.class);
    private T vehicle;

    public VehicleContainer(T vehicle) {
        this.vehicle = vehicle;
    }

    public T get(){
       return vehicle;
    }

    public T  discount (){
        BigDecimal d = new BigDecimal (new Random().nextInt(10,30));
        BigDecimal newPrice = vehicle.getPrice().subtract(vehicle.getPrice().multiply(d).divide(new BigDecimal(100)));
        vehicle.setPrice(newPrice);
        return vehicle;
    }

    public <E extends Number> T increase (E number){
        if (number == null){
            throw new IllegalArgumentException("number == null");
        }
        BigDecimal d = new BigDecimal(number.toString());
        BigDecimal newPrice = vehicle.getPrice().add(d);
        vehicle.setPrice(newPrice);
        return vehicle;
    }
}
