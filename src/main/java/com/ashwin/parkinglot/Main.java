package com.ashwin.parkinglot;

import com.ashwin.parkinglot.exception.ParkingError;
import com.ashwin.parkinglot.exception.ParkingLotException;
import com.ashwin.parkinglot.handler.ServiceHandler;
import com.ashwin.parkinglot.service.ParkingLotServiceImpl;
import com.ashwin.parkinglot.util.CommandConstants;

import java.io.*;

public class Main {

    public static void main(String[] args) {
        ServiceHandler serviceHandler = new ServiceHandler();
        serviceHandler.setParkingLotService(new ParkingLotServiceImpl());

        BufferedReader bufferReader = null;
        String inputStr = null;
        try {
            System.out.println("*******************************************************************");
            System.out.println("Parking Lot Game started");
            System.out.println("******************************************************************");
            printHelpCommands();
            //Currently only implementing "Interactive command prompt based shell"
            if (args.length == 0) {
                System.out.println("Please Enter 'exit' to end Execution");
                System.out.println("Input:");
                while (true) {
                    try {
                        bufferReader = new BufferedReader(new InputStreamReader(System.in));
                        inputStr = bufferReader.readLine().trim();
                        if (inputStr.equalsIgnoreCase("exit")) {
                            break;
                        } else {
                            if (serviceHandler.validateInput(inputStr)) {
                                try {
                                    serviceHandler.process(inputStr.trim());
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            } else {
                                printHelpCommands();
                            }
                        }
                    } catch (Exception e) {
                        throw new ParkingLotException(ParkingError.INVALID_REQUEST.getMessage(), e);
                    }
                }

            } //TODO: file approach
            else {
                System.out.println("Invalid input string. Please enter: java -jar parking_lot.jar");
            }
        } catch (ParkingLotException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } finally {
            try {
                if (bufferReader != null)
                    bufferReader.close();
            } catch (IOException ignored) {
            }
        }
    }

    private static void printHelpCommands() {
        StringBuilder stringBuilder = new StringBuilder("");
        stringBuilder
                .append("Enter one of the follow. {var-arg} to be replaced with actual value")
                .append("\n")
                .append("1. Create Parking lot of capacity::" + CommandConstants.CREATE_PARKING_LOT + "{capacity}")
                .append("\n")
                .append("2. Park a car:: park <<car_number>> {colour}")
                .append("\n")
                .append("3. Remove car::" + CommandConstants.LEAVE + " {slot_number}")
                .append("\n")
                .append("4. Print Status::" + CommandConstants.STATUS)
                .append("\n")
                .append("5. Get cars registration no for the given car colour::" + CommandConstants.REG_NUMBER_FOR_CARS_WITH_COLOUR + "{colour}")
                .append("\n")
                .append("6. Get slot numbers for the given car colour::" + CommandConstants.SLOT_NUMBERS_FOR_CARS_WITH_COLOUR + "{colour}")
                .append("\n")
                .append("7. Get slot number for the given car number::" + CommandConstants.SLOT_NUMBER_FOR_REG_NUMBER + "{car_number}")
                .append("\n");

        System.out.println(stringBuilder.toString());
    }
}