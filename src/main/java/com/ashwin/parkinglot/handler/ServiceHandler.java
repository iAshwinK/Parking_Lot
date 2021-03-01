package com.ashwin.parkinglot.handler;

import com.ashwin.parkinglot.exception.ParkingError;
import com.ashwin.parkinglot.exception.ParkingLotException;
import com.ashwin.parkinglot.model.Car;
import com.ashwin.parkinglot.service.ParkingLotService;
import com.ashwin.parkinglot.util.CommandConstants;
import com.ashwin.parkinglot.util.CommandMap;

public class ServiceHandler {
    private ParkingLotService parkingLotService;

    public void setParkingLotService(ParkingLotService parkingLotService) {
        this.parkingLotService = parkingLotService;
    }

    public boolean validateInput(String inputString) {
        boolean valid = true;
        try {
            String[] argArr = inputString.split(" ");
            int arg = CommandMap.getCommandConstantIdMap().get(argArr[0]);

            switch (argArr.length) {
                case 1:
                    if (arg != 0) {
                        valid = false;
                    }
                    break;
                case 2:
                    if (arg != 1) {
                        valid = false;
                    }
                    break;
                case 3:
                    if (arg != 2) {
                        valid = false;
                    }
                    break;
                default:
                    valid = false;
            }
        } catch (Exception e) {
            valid = false;
        }
        return valid;
    }

    public void process(String input) throws ParkingLotException {
        String[] inputs = input.split(" ");
        String key = inputs[0];
        switch (key) {
            case CommandConstants.CREATE_PARKING_LOT:
                try {
                    int capacity = Integer.parseInt(inputs[1]);
                    parkingLotService.createParkingLot(capacity);
                } catch (NumberFormatException e) {
                    throw new ParkingLotException(ParkingError.INVALID_VALUE.getMessage().replace("{variable}", "capacity"));
                }
                break;
            case CommandConstants.PARK:
                parkingLotService.park(new Car(inputs[1], inputs[2]));
                break;
            case CommandConstants.LEAVE:
                try {
                    int slotNumber = Integer.parseInt(inputs[1]);
                    parkingLotService.leaveParking(slotNumber);
                } catch (NumberFormatException e) {
                    throw new ParkingLotException(
                            ParkingError.INVALID_VALUE.getMessage().replace("{variable}", "slot_number"));
                }
                break;
            case CommandConstants.STATUS:
                parkingLotService.getStatus();
                break;
            case CommandConstants.REG_NUMBER_FOR_CARS_WITH_COLOUR:
                parkingLotService.getRegistrationFromColour(inputs[1]);
                break;
            case CommandConstants.SLOT_NUMBERS_FOR_CARS_WITH_COLOUR:
                parkingLotService.getSlotNumbersFromColour(inputs[1]);
                break;
            case CommandConstants.SLOT_NUMBER_FOR_REG_NUMBER:
                parkingLotService.getSlotNoFromRegistration(inputs[1]);
                break;
            default:
                break;
        }
    }



}
