package com;

import com.model.Bus;
import org.mockito.ArgumentMatcher;

public class BusMatcher implements ArgumentMatcher<Bus> {
    private Bus left;

    public BusMatcher(Bus left) {
        this.left = left;
    }

    @Override
    public boolean matches(Bus right) {
        return left.getId().equals(right.getId()) &&
                left.getManufacturer().equals(right.getManufacturer()) &&
                left.getModel().equals(right.getModel()) &&
                left.getPrice().equals(right.getPrice()) &&
                left.getPassengers() == right.getPassengers();

    }
}

