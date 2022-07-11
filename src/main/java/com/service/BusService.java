package com.service;

import com.model.Bus;
import com.model.Manufacturer;
import com.repository.BusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class BusService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BusService.class);
    private static final Random RANDOM = new Random();
    private final BusRepository busRepository;
    public BusService(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    public List<Bus> createAndSaveBuses(int count) {
        List<Bus> result = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            final Bus bus = new Bus(
                    "Model-" + RANDOM.nextInt(1000),
                    getRandomManufacturer(),
                    BigDecimal.valueOf(RANDOM.nextDouble(1000.0)),
                    RANDOM.nextInt(350)
            );
            result.add(bus);
            busRepository.save(bus);
            LOGGER.debug("Created bus {}", bus.getId());
        }
        return result;
    }
    public void updateBus(Bus bus){
        LOGGER.info("updated bus: {}", bus.getId());
        busRepository.update(bus);
    }

    public void deleteBus(Bus bus){
        if(bus == null){
            throw new IllegalArgumentException("bus = NULL");
        }
        LOGGER.info("deleted bus: {}", bus.getId());
        busRepository.delete(bus.getId());
    }
    private Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }

    public void saveBuses(List<Bus> buses) {
        LOGGER.info("create {} buses", buses.size());
        busRepository.saveAll(buses);
    }

    public void printAll() {
        List<Bus> buses = busRepository.getAll();
        if (buses == null) {
            System.out.println("empty");
            return;
        }
        for (Bus bus : buses) {
            System.out.println(bus);
        }
        System.out.println("- - - -");
    }
    public Bus findOneById(String id) {
        if (id == null) {
            return busRepository.getById("");
        } else {
            return busRepository.getById(id);
        }
    }
}
