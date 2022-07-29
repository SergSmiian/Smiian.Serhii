package com.service;

import com.model.vehicle.*;

import java.math.BigDecimal;
import java.util.Random;

public class VehicleFactory {
    private static final Random RANDOM = new Random();
    private static VehicleFactory instance;
    private VehicleFactory() {
    }
    public static VehicleFactory getInstance(){
        if (instance==null){
            instance = new VehicleFactory();
        }
        return instance;
}

    protected Random getRandom (){
        return RANDOM;
    }
    public Vehicle build(VehicleType type){
        return switch (type){
            case BUS -> new Bus(
                "Model-" + getRandom().nextInt(1000),
                getRandomManufacturer(),
                BigDecimal.valueOf(getRandom().nextDouble(1000.0)),
                getRandom().nextInt(350));

            case AUTO -> new Auto(
                    "Model-" + getRandom().nextInt(1000),
                    getRandomManufacturer(),
                    BigDecimal.valueOf(getRandom().nextDouble(1000.0)),
                    "Model-" + getRandom().nextInt(1000));

            case TRUCK -> new Truck(
                    "Model-" + getRandom().nextInt(1000),
                    getRandomManufacturer(),
                    BigDecimal.valueOf(getRandom().nextDouble(1000.0)),
                    getRandom().nextDouble(1000));

            default -> throw new IllegalArgumentException("Cannot build" + type);
        };
    }
    public Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }
}
