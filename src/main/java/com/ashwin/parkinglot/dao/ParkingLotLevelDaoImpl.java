package com.ashwin.parkinglot.dao;

import com.ashwin.parkinglot.model.Vehicle;

import java.util.List;
import java.util.Map;

public class ParkingLotLevelDaoImpl <T extends Vehicle> implements ParkingLotLevelDao<T> {

    private Map<Integer,ParkingLotDao<T>> floorParkingLotMap;



    @Override
    public int parkCar(int floor, T vehicle) {
        floorParkingLotMap.get(floor).parkCar(vehicle);
        return 0;
    }

    @Override
    public boolean leaveCar(int floor, int slotNumber) {
        return false;
    }

    @Override
    public List<String> getStatus(int flor) {
        return null;
    }

    @Override
    public int getAvailableSlotsCount(int floor) {
        return 0;
    }

    @Override
    public List<String> getRegistrationForColour(int floor, String colour) {
        return null;
    }

    @Override
    public List<Integer> getSlotNumbersFromColour(int floor, String colour) {
        return null;
    }

    @Override
    public int getSlotNoFromRegistrationNo(int floor, String registrationNo) {
        return 0;
    }

    @Override
    public void clear() {

    }
}
