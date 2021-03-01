package com.ashwin.parkinglot.service;

import com.ashwin.parkinglot.dao.ParkingLotDao;
import com.ashwin.parkinglot.dao.ParkingLotDaoImpl;
import com.ashwin.parkinglot.exception.ParkingError;
import com.ashwin.parkinglot.exception.ParkingLotException;
import com.ashwin.parkinglot.model.Vehicle;
import com.ashwin.parkinglot.model.strategy.NearestToEntryStrategy;
import com.ashwin.parkinglot.model.strategy.ParkingLotStrategy;
import com.ashwin.parkinglot.util.Constants;

import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ParkingLotServiceImpl implements ParkingLotService {
    private ParkingLotDao<Vehicle> parkingLotDao = null;

    private final ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    @Override
    public void createParkingLot(int capacity) throws ParkingLotException {
        if (parkingLotDao != null) {
            throw new ParkingLotException(ParkingError.PARKING_ALREADY_EXIST.getMessage());
        }
        ParkingLotStrategy parkingLotStrategy = new NearestToEntryStrategy();
        this.parkingLotDao = ParkingLotDaoImpl.getInstance(capacity, parkingLotStrategy);
        System.out.println("Created parking lot with " + capacity + " slots");
    }

    @Override
    public Optional<Integer> park(Vehicle vehicle) throws ParkingLotException {
        Optional<Integer> parkValue;
        readWriteLock.writeLock().lock();
        validateParkingLot();
        try {
            parkValue = Optional.of(parkingLotDao.parkCar(vehicle));
            if (parkValue.get() == Constants.NOT_AVAILABLE) {
                System.out.println(ParkingError.PARKING_NOT_EXIST_ERROR.getMessage());
            } else if (parkValue.get() == Constants.VEHICLE_ALREADY_EXIST) {
                System.out.println("Sorry, vehicle is already parked.");
                System.out.println(ParkingError.PARKING_ALREADY_EXIST.getMessage());
            } else {
                System.out.println("Allocated slot number: " + parkValue.get());
            }
        } catch (Exception e) {
            throw new ParkingLotException(ParkingError.PROCESSING_ERROR.getMessage(), e);
        } finally {
            readWriteLock.writeLock().unlock();
        }
        return parkValue;
    }

    @Override
    public void leaveParking(int slotNumber) throws ParkingLotException {
        readWriteLock.writeLock().lock();
        validateParkingLot();

        try {
            if (parkingLotDao.leaveCar(slotNumber)) {
                System.out.println("Slot number " + slotNumber + " is free");
            } else {
                System.out.println("Slot number is Empty Already.");
            }
        } catch (Exception e) {
            throw new ParkingLotException(ParkingError.INVALID_VALUE.getMessage()
                    .replace("{variable}", "slot_number"), e);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public void getStatus() throws ParkingLotException {
        readWriteLock.readLock().lock();
        validateParkingLot();

        try {
            System.out.println("Slot No.\tRegistration No.\tColour");
            List<String> parkingList = parkingLotDao.getStatus();
            if (parkingList.size() == 0) {
                System.out.println("Sorry, parking lot is empty.");
            } else {
                for (String str : parkingList) {
                    System.out.println(str);
                }
            }
        } catch (Exception e) {
            throw new ParkingLotException(ParkingError.PROCESSING_ERROR.getMessage(), e);
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    @Override
    public Optional<Integer> getAvailableSlotsCount() throws ParkingLotException {
        readWriteLock.readLock().lock();
        Optional<Integer> value;
        validateParkingLot();

        try {
            value = Optional.of(parkingLotDao.getAvailableSlotsCount());
        } catch (Exception e) {
            throw new ParkingLotException(ParkingError.PROCESSING_ERROR.getMessage(), e);
        } finally {
            readWriteLock.readLock().unlock();
        }
        return value;
    }

    @Override
    public void getRegistrationFromColour(String colour) throws ParkingLotException {
        readWriteLock.readLock().lock();
        validateParkingLot();

        try {
            List<String> registrationList = parkingLotDao.getRegistrationForColour(colour);
            if (registrationList.size() == 0) {
                System.out.println("Not Found");
            } else {
                System.out.println(String.join(",", registrationList));
            }
        } catch (Exception e) {
            throw new ParkingLotException(ParkingError.PROCESSING_ERROR.getMessage(), e);
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    @Override
    public void getSlotNumbersFromColour(String colour) throws ParkingLotException {
        readWriteLock.readLock().lock();
        validateParkingLot();

        try {
            List<Integer> list = parkingLotDao.getSlotNumbersFromColour(colour);
            if (list.size() == 0)
                System.out.println("Not Found");
            StringJoiner joiner = new StringJoiner(",");
            for (Integer item : list) {
                joiner.add(item + "");
            }
            System.out.println(joiner.toString());
        } catch (Exception e) {
            throw new ParkingLotException(ParkingError.PROCESSING_ERROR.getMessage(), e);
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    @Override
    public int getSlotNoFromRegistration(String registrationNo) throws ParkingLotException {
        int value = -1;
        readWriteLock.readLock().lock();
        validateParkingLot();

        try {
            value = parkingLotDao.getSlotNoFromRegistrationNo(registrationNo);
            System.out.println(value != -1 ? value : "Not Found");
        } catch (Exception e) {
            throw new ParkingLotException(ParkingError.PROCESSING_ERROR.getMessage(), e);
        } finally {
            readWriteLock.readLock().unlock();
        }
        return value;
    }

    @Override
    public void clear() {
        if (parkingLotDao != null)
            parkingLotDao.clear();
    }

    private void validateParkingLot() throws ParkingLotException {
        if (parkingLotDao == null) {
            throw new ParkingLotException(ParkingError.PARKING_NOT_EXIST_ERROR.getMessage());
        }
    }
}
