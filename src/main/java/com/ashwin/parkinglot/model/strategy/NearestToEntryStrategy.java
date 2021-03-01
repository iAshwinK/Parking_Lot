package com.ashwin.parkinglot.model.strategy;

import java.util.TreeSet;

public class NearestToEntryStrategy implements ParkingLotStrategy {

    private TreeSet<Integer> parkingSlots;

    public NearestToEntryStrategy() {
        parkingSlots = new TreeSet<Integer>();
    }

    @Override
    public void add(int i) {
        parkingSlots.add(i);
    }

    @Override
    public int getSlot() {
        return parkingSlots.first();
    }

    @Override
    public void removeSlot(int slot) {
        parkingSlots.remove(slot);
    }
}
