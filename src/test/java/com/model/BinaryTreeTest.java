package com.model;

import com.model.vehicle.Bus;
import com.model.vehicle.Manufacturer;
import com.model.vehicle.Vehicle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

class BinaryTreeTest {
    private BinaryTree<Bus> tree = new BinaryTree<>(Comparator.comparing(Vehicle::getPrice));
    private static final Random RANDOM = new Random();

    private Bus createSimpleBus() {
        return new Bus("Model", Manufacturer.VOLVO, BigDecimal.ZERO, 50);
    }

    @Test
    void addTest() {
        Bus bus = createSimpleBus();
        tree.add(bus);
        int actualSize = tree.getSize();
        Assertions.assertEquals(1, actualSize);
    }

    @Test
    void addNullTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> tree.add(null));
    }

    @Test
    void sumPriceLeftRightTest() {
        List<Bus> busses = create(6);
        busses.get(0).setPrice(BigDecimal.valueOf(100));
        busses.get(1).setPrice(BigDecimal.valueOf(90));
        busses.get(2).setPrice(BigDecimal.valueOf(110));
        busses.get(3).setPrice(BigDecimal.valueOf(80));
        busses.get(4).setPrice(BigDecimal.valueOf(85));
        busses.get(5).setPrice(BigDecimal.valueOf(120));
        for (int i = 0; i < busses.size(); i++) {
            tree.add(busses.get(i));
        }
        BigDecimal actualLeftSumPrice = tree.sumPrice(true);
        Assertions.assertEquals(BigDecimal.valueOf(255), actualLeftSumPrice);
        BigDecimal actualRightSumPrice = tree.sumPrice(false);
        Assertions.assertEquals(BigDecimal.valueOf(230), actualRightSumPrice);
    }

    @Test
    void sumPriceRootOnlyTest() {
        Bus bus = createSimpleBus();
        tree.add(bus);
        BigDecimal actualLeftSumPrice = tree.sumPrice(true);
        Assertions.assertEquals(BigDecimal.ZERO, actualLeftSumPrice);
        BigDecimal actualRightSumPrice = tree.sumPrice(false);
        Assertions.assertEquals(BigDecimal.ZERO, actualRightSumPrice);
    }

    @Test
    void sumPriceRootNullTest() {
        BigDecimal actualLeftSumPrice = tree.sumPrice(true);
        Assertions.assertEquals(BigDecimal.ZERO, actualLeftSumPrice);
    }


    public List<Bus> create(int count) {
        List<Bus> result = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            final Bus bus = new Bus("Model-" + RANDOM.nextInt(1000), getRandomManufacturer(), BigDecimal.valueOf(RANDOM.nextDouble(1000.0)).setScale(2, RoundingMode.HALF_UP), RANDOM.nextInt(350));
            result.add(bus);
        }
        return result;
    }

    protected Manufacturer getRandomManufacturer() {
        final Manufacturer[] values = Manufacturer.values();
        final int index = RANDOM.nextInt(values.length);
        return values[index];
    }
}
