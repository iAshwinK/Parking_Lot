package com.ashwin.parkinglot.service;

import com.ashwin.parkinglot.exception.ParkingLotException;
import com.ashwin.parkinglot.model.Vehicle;

import java.util.Optional;

public interface ParkingLotService extends AbstractService {

    public void createParkingLot(int capacity) throws ParkingLotException;

    public Optional<Integer> park(Vehicle vehicle) throws ParkingLotException;

    public void leaveParking(int slotNumber) throws ParkingLotException;

    public void getStatus() throws ParkingLotException;

    public Optional<Integer> getAvailableSlotsCount() throws ParkingLotException;

    public void getRegistrationFromColour(String colour) throws ParkingLotException;

    public void getSlotNumbersFromColour(String colour) throws ParkingLotException;

    public int getSlotNoFromRegistration(String registrationNum) throws ParkingLotException;

    public void clear();
}
