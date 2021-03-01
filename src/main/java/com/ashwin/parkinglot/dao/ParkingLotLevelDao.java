package com.ashwin.parkinglot.dao;

import com.ashwin.parkinglot.model.Vehicle;

import java.util.List;

public interface ParkingLotLevelDao<T extends Vehicle> {
    public int parkCar(int floor, T vehicle);

    public boolean leaveCar(int floor, int slotNumber);

    public List<String> getStatus(int floor);

    public int getAvailableSlotsCount(int floor);

    public List<String> getRegistrationForColour(int floor, String colour);

    public List<Integer> getSlotNumbersFromColour(int floor, String colour);

    public int getSlotNoFromRegistrationNo(int floor, String registrationNo);

    public void clear();
}
