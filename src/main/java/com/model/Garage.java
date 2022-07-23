package com.model;

import com.model.vehicle.Vehicle;

import java.time.LocalDate;
import java.util.*;

public class Garage<T extends Vehicle> implements Iterable<T> {
    private VehicleNode<T> first;
    private VehicleNode<T> last;
    private int size = 0;

    public void addToHead(T vehicle, int restyling, LocalDate date) {
        if (vehicle == null || date == null) {
            throw new IllegalArgumentException();
        }
        if (first != null) {
            VehicleNode<T> elem = new VehicleNode<T>(null, vehicle, first, restyling, date);
            first.prev = elem;
            first = elem;
        } else {
            VehicleNode<T> elem = new VehicleNode<T>(null, vehicle, null, restyling, date);
            first = elem;
            last = elem;
        }
        size++;
    }

    public T get(int restyling) {
        VehicleNode<T> result = getNode(restyling);
        if (result == null) {
            return null;
        }
        return result.vehicle;
    }

    public T remove(int restyling) {
        VehicleNode<T> result = getNode(restyling);
        if (result == null) {
            return null;
        }
        if (result.prev != null) {
            result.prev.next = result.next;
        } else {
            first = result.next;
        }
        if (result.next != null) {
            result.next.prev = result.prev;
        } else {
            last = result.prev;
        }
        size--;

        return result.vehicle;
    }

    public T set(int restyling, T element) {
        if (element == null) {
            throw new IllegalArgumentException();
        }
        VehicleNode<T> result = getNode(restyling);
        if (result == null) {
            return null;
        }
        T oldObject = result.vehicle;
        result.vehicle = element;

        return oldObject;
    }

    public int restylingCount(int restyling) {
        if (first == null) {
            return 0;
        }
        VehicleNode<T> elem = first;
        int count = 0;
        for (int i = 0; i < size; i++) {
            if (elem.restyling == restyling) {
                count++;
            }
            elem = elem.next;
        }
        return count;
    }

    public LocalDate getFirstDate() {
        if (first == null) {
            return null;
        }
        LocalDate result = first.createdDate;
        VehicleNode<T> elem = first;
        for (int i = 1; i < size; i++) {
            if (elem.next.createdDate.isBefore(result)) {
                result = elem.next.createdDate;
            }

            elem = elem.next;
        }
        return result;
    }

    public LocalDate getLastDate() {
        if (last == null) {
            return null;
        }
        LocalDate result = last.createdDate;
        VehicleNode<T> elem = last;
        for (int i = 1; i < size; i++) {
            if (elem.prev.createdDate.isAfter(result)) {
                result = elem.prev.createdDate;
            }

            elem = elem.prev;
        }
        return result;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private VehicleNode<T> nextElem = first;

            @Override
            public boolean hasNext() {
                return nextElem != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                T elem = nextElem.vehicle;
                nextElem = nextElem.next;
                return elem;
            }
        };
    }

    private VehicleNode<T> getNode(int restyling) {
        VehicleNode<T> elem = first;
        if (first == null) {
            return null;
        }
        for (int i = 0; i < size; i++) {
            if (elem.restyling == restyling) {
                return elem;
            }
            elem = elem.next;
        }

        return null;
    }

    private static class VehicleNode<T> {
        T vehicle;
        Garage.VehicleNode<T> next;
        Garage.VehicleNode<T> prev;

        private int restyling;
        private LocalDate createdDate;

        VehicleNode(Garage.VehicleNode<T> prev, T vehicle, Garage.VehicleNode<T> next, int restyling, LocalDate createdDate) {
            this.vehicle = vehicle;
            this.next = next;
            this.prev = prev;
            this.restyling = restyling;
            this.createdDate = createdDate;

        }

        @Override
        public String toString() {
            return "VehicleNode{" + "vehicle=" + vehicle + ", restyling=" + restyling + ", createdDate=" + createdDate + '}' + '\n';
        }
    }

    @Override
    public String toString() {
        VehicleNode<T> elem = first;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            builder.append(elem).append("\n");
            elem = elem.next;
        }
        return builder.toString();
    }
}
