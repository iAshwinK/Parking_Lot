package com.ashwin.parkinglot.dao;

import com.ashwin.parkinglot.model.Vehicle;
import com.ashwin.parkinglot.model.strategy.LevelDistributionStrategy;
import com.ashwin.parkinglot.model.strategy.NearestToEntryStrategy;
import com.ashwin.parkinglot.model.strategy.ParkingLotStrategy;
import com.ashwin.parkinglot.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class ParkingLotDaoImpl<T extends Vehicle> implements ParkingLotDao<T> {

    private AtomicInteger floor = new AtomicInteger(0);
    private AtomicInteger capacity = new AtomicInteger();
    private AtomicInteger slotAvLeailability = new AtomicInteger();
    private Map<Integer, Optional<T>> slotVehicleMap;

    private ParkingLotStrategy parkingLotStrategy;
    private LevelDistributionStrategy levelDistributionStrategy;

    private static ParkingLotDaoImpl instance = null;

    public static <T extends Vehicle> ParkingLotDaoImpl<T> getInstance(int capacity, ParkingLotStrategy parkingLotStrategy,
                                                                       LevelDistributionStrategy levelDistributionStrategy) {
        if (instance == null) {
            synchronized (ParkingLotDaoImpl.class) {
                if (instance == null) {
                    instance = new ParkingLotDaoImpl<T>(capacity, parkingLotStrategy);
                }
            }
        }
        return instance;
    }

    private ParkingLotDaoImpl(int capacity, ParkingLotStrategy parkingLotStrategy) {
        this.capacity.set(capacity);
        this.slotAvailability.set(capacity);
        this.parkingLotStrategy = parkingLotStrategy;

        if (parkingLotStrategy == null) {
            parkingLotStrategy = new NearestToEntryStrategy();
        }

        slotVehicleMap = new ConcurrentHashMap<>();
        for (int i = 1; i <= capacity; i++) {
            slotVehicleMap.put(i, Optional.empty());
            parkingLotStrategy.add(i);
        }

    }

    @Override
    public int parkCar(T vehicle) {
        int availableSlot;
        if (slotAvailability.get() == 0) {
            return Constants.NOT_AVAILABLE;
        } else {
            levelDistributionStrategy.getSlot()

            availableSlot = parkingLotStrategy.getSlot();
            if (slotVehicleMap.containsValue(Optional.of(vehicle)))
                return Constants.VEHICLE_ALREADY_EXIST;

            slotVehicleMap.put(availableSlot, Optional.of(vehicle));
            slotAvailability.decrementAndGet();
            parkingLotStrategy.removeSlot(availableSlot);
        }
        return availableSlot;
    }

    //Floor available  parked
    //1     30         25
    //2     30         0
    //2     30         1
    //..

    //2     30         25
    //1     30         26
    //2     30         26

    @Override
    public boolean leaveCar(int slotNumber) {
        if (!slotVehicleMap.get(slotNumber).isPresent()) {
            return false;
        }
        slotAvailability.incrementAndGet();
        parkingLotStrategy.add(slotNumber);
        slotVehicleMap.put(slotNumber, Optional.empty());
        return true;
    }

    @Override
    public List<String> getStatus() {
        List<String> statusList = new ArrayList<>();

        for (int i = 1; i <= capacity.get(); i++) {
            Optional<T> vehicle = slotVehicleMap.get(i);
            if (vehicle.isPresent()) {
                statusList.add(i + "\t" + vehicle.get().getRegistrationNo() + "\t" + vehicle.get().getColour());
            }
        }
        return statusList;
    }

    @Override
    public int getAvailableSlotsCount() {
        return slotAvailability.get();
    }

    @Override
    public List<String> getRegistrationForColour(String colour) {
        List<String> registrationForColourList = new ArrayList<>();
        for (int i = 1; i <= capacity.get(); i++)
        {
            Optional<T> vehicle = slotVehicleMap.get(i);
            if (vehicle.isPresent() && vehicle.get().getColour().equalsIgnoreCase(colour))
            {
                registrationForColourList.add(vehicle.get().getRegistrationNo());
            }
        }
        return registrationForColourList;
    }

    @Override
    public List<Integer> getSlotNumbersFromColour(String colour) {
        List<Integer> slotFromColourList = new ArrayList<>();
        for (int i = 1; i <= capacity.get(); i++)
        {
            Optional<T> vehicle = slotVehicleMap.get(i);
            if (vehicle.isPresent() && colour.equalsIgnoreCase(vehicle.get().getColour()))
            {
                slotFromColourList.add(i);
            }
        }
        return slotFromColourList;
    }

    @Override
    public int getSlotNoFromRegistrationNo(String registrationNo) {
        int slotNo = Constants.NOT_FOUND;
        for (int i = 1; i <= capacity.get(); i++)
        {
            Optional<T> vehicle = slotVehicleMap.get(i);
            if (vehicle.isPresent() && registrationNo.equalsIgnoreCase(vehicle.get().getRegistrationNo()))
            {
                slotNo = i;
            }
        }
        return slotNo;
    }

    @Override
    public void clear() {
        this.capacity = new AtomicInteger();
        this.slotAvailability = new AtomicInteger();
        this.parkingLotStrategy = null;
        slotVehicleMap = null;
        instance = null;
    }

    public Object clone() throws CloneNotSupportedException
    {
        throw new CloneNotSupportedException();
    }
}
