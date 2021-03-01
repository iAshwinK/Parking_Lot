package com.ashwin.parkinglot.util;

import java.util.HashMap;
import java.util.Map;

/**
 * This command Map is created so that it is easy for understanding how many parameters are passed with specific key
 * As PARK,2 --> This command will have two arguments as: park KA-01-HH-1234 White
 * LEAVE,1 --> This command will have one argument as: leave 4
 * STATUS, 0 --> status
 */
public class CommandMap {

    private static Map<String, Integer> commandConstantIdMap = new HashMap<String, Integer>();

    static {
        commandConstantIdMap.put(CommandConstants.CREATE_PARKING_LOT, 1);
        commandConstantIdMap.put(CommandConstants.PARK, 2);
        commandConstantIdMap.put(CommandConstants.LEAVE, 1);
        commandConstantIdMap.put(CommandConstants.STATUS, 0);
        commandConstantIdMap.put(CommandConstants.REG_NUMBER_FOR_CARS_WITH_COLOUR, 1);
        commandConstantIdMap.put(CommandConstants.SLOT_NUMBER_FOR_REG_NUMBER, 1);
        commandConstantIdMap.put(CommandConstants.SLOT_NUMBERS_FOR_CARS_WITH_COLOUR, 1);
    }

    public static Map<String, Integer> getCommandConstantIdMap() {
        return commandConstantIdMap;
    }

    public static void addCommand(String commandConstant, int id) {
        commandConstantIdMap.put(commandConstant, id);
    }
}
