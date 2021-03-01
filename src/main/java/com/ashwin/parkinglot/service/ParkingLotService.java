package com.ashwin.parkinglot.service;

import com.ashwin.parkinglot.exception.ParkingLotException;
import com.ashwin.parkinglot.model.Vehicle;

import java.util.Optional;

public interface ParkingLotService extends AbstractService {

    public void createParkingLot(int level, int capacity) throws ParkingLotException;

    public Optional<Integer> park(int level, Vehicle vehicle) throws ParkingLotException;

    public void leaveParking(int level, int slotNumber) throws ParkingLotException;

    public void getStatus(int level) throws ParkingLotException;

    public Optional<Integer> getAvailableSlotsCount(int level) throws ParkingLotException;

    public void getRegistrationForColor(int level, String color) throws ParkingLotException;

    public void getSlotNumbersFromColor(int level, String colour) throws ParkingLotException;

    public int getSlotNoFromRegistration(int level, String registrationNum) throws ParkingLotException;

    public void clear();
}
