package com.ashwin.parkinglot.dao;

import com.ashwin.parkinglot.model.Vehicle;

import java.util.List;

public interface ParkingLotDao<T extends Vehicle>
{
    public int parkCar(T vehicle);

    public boolean leaveCar(int slotNumber);

    public List<String> getStatus();

    public int getAvailableSlotsCount();

    public List<String> getRegistrationForColour(String colour);

    public List<Integer> getSlotNumbersFromColour(String colour);

    public int getSlotNoFromRegistrationNo(String registrationNo);

    public void clear();
}