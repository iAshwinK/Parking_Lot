package com.ashwin.parkinglot.model.strategy;

public interface ParkingLotStrategy {
    public void add(int i);

    public int getSlot();

    public void removeSlot(int slot);
}
