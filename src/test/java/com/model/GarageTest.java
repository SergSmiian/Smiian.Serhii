package com.model;

import com.model.vehicle.Auto;
import com.model.vehicle.Manufacturer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

class GarageTest {
    private Garage<Auto> garage = new Garage<>();

    private Auto createSimpleAuto() {
        return new Auto("Model", Manufacturer.BMW, BigDecimal.ZERO, "Type");
    }

    @Test
    void addToHeadTest() {
        Auto auto = createSimpleAuto();
        garage.addToHead(auto, 2, LocalDate.of(1985, 1, 2));
        Auto actual = garage.get(2);
        Assertions.assertEquals(auto, actual);
    }

    @Test
    void addToHeadAutoNullTest() {
        LocalDate localDate = LocalDate.of(1985,1,2);
        Assertions.assertThrows(IllegalArgumentException.class, () -> garage.addToHead(null, 2, localDate));
    }

    @Test
    void addToHeadDateNullTest() {
        Auto auto = createSimpleAuto();
        Assertions.assertThrows(IllegalArgumentException.class, () -> garage.addToHead(auto, 2, null));
    }

    @Test
    void getNonexistingRestylingTest() {
        Auto auto = createSimpleAuto();
        garage.addToHead(auto, 2, LocalDate.of(1985, 1, 2));
        Auto actual = garage.get(5);
        Assertions.assertNull(actual);
    }

    @Test
    void getEmptyGarageTest() {
        Auto actual = garage.get(2);
        Assertions.assertNull(actual);
    }

    @Test
    void removeTest() {
        Auto auto = createSimpleAuto();
        garage.addToHead(auto, 2, LocalDate.of(1985, 1, 2));
        Auto actual = garage.remove(2);
        Assertions.assertEquals(auto, actual);
        Assertions.assertNull(garage.get(2));
    }

    @Test
    void removeNonexistingRestylingTest() {
        Auto auto = createSimpleAuto();
        garage.addToHead(auto, 2, LocalDate.of(1985, 1, 2));

        Auto actual = garage.remove(3);
        Assertions.assertNull(actual);
    }

    @Test
    void removeEmptyGarageTest() {

        Auto actual = garage.remove(2);
        Assertions.assertNull(actual);
    }

    @Test
    void setTest() {
        Auto auto1 = createSimpleAuto();
        Auto auto2 = createSimpleAuto();
        garage.addToHead(auto1, 2, LocalDate.of(1985, 1, 2));

        Auto actual = garage.set(2, auto2);
        Assertions.assertEquals(auto1, actual);

        Auto updatedAuto = garage.get(2);
        Assertions.assertEquals(auto2, updatedAuto);
    }

    @Test
    void setNonexistingRestylingTest() {
        Auto auto = createSimpleAuto();
        garage.addToHead(auto, 2, LocalDate.of(1985, 1, 2));

        Auto actual = garage.set(15, auto);
        Assertions.assertNull(actual);
    }

    @Test
    void setEmptyGarageTest() {
        Auto auto = createSimpleAuto();
        Auto actual = garage.set(15, auto);
        Assertions.assertNull(actual);
    }

    @Test
    void setAutoNullTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> garage.set(2, null));
    }

    @Test
    void restylingCountTest() {
        Auto auto1 = createSimpleAuto();
        Auto auto2 = createSimpleAuto();
        Auto auto3 = createSimpleAuto();
        garage.addToHead(auto1, 2, LocalDate.of(1985, 1, 2));
        garage.addToHead(auto2, 4, LocalDate.of(1995, 1, 2));
        garage.addToHead(auto3, 4, LocalDate.of(1995, 1, 2));
        int actual1 = garage.restylingCount(4);
        Assertions.assertEquals(2, actual1);
        int actual2 = garage.restylingCount(2);
        Assertions.assertEquals(1, actual2);
        int actual3 = garage.restylingCount(5);
        Assertions.assertEquals(0, actual3);
    }

    @Test
    void restylingCountEmptyGarageTest() {
        int actual = garage.restylingCount(5);
        Assertions.assertEquals(0, actual);
    }

    @Test
    void getFirstDateTest() {
        Auto auto1 = createSimpleAuto();
        Auto auto2 = createSimpleAuto();
        Auto auto3 = createSimpleAuto();
        garage.addToHead(auto1, 2, LocalDate.of(1985, 1, 2));
        garage.addToHead(auto2, 4, LocalDate.of(1995, 1, 2));
        garage.addToHead(auto3, 4, LocalDate.of(1995, 1, 2));
        LocalDate actual = garage.getFirstDate();
        Assertions.assertEquals(LocalDate.of(1985, 1, 2), actual);
    }

    @Test
    void getFirstDateEmptyGarageTest() {
        Assertions.assertNull(garage.getFirstDate());
    }

    @Test
    void getLastDateTest() {
        Auto auto1 = createSimpleAuto();
        Auto auto2 = createSimpleAuto();
        Auto auto3 = createSimpleAuto();
        garage.addToHead(auto1, 2, LocalDate.of(1985, 1, 2));
        garage.addToHead(auto2, 4, LocalDate.of(1995, 1, 2));
        garage.addToHead(auto3, 4, LocalDate.of(1997, 1, 2));
        LocalDate actual = garage.getLastDate();
        Assertions.assertEquals(LocalDate.of(1997, 1, 2), actual);
    }

    @Test
    void getLastDateEmptyGarageTest() {
        Assertions.assertNull(garage.getLastDate());
    }

}
