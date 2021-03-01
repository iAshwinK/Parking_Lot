package com.ashwin.parkinglot.model.strategy;

public interface LevelDistributionStrategy {

    public void add(int floor,int i);
    public int getSlot(int floor);
    public void removeSlot(int floor,int slot);

}
