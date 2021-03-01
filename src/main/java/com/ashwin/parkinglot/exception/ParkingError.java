package com.ashwin.parkinglot.exception;

public enum ParkingError {

    PARKING_ALREADY_EXIST("Sorry, Parking Already Created, can't be recreated."),
    PARKING_NOT_EXIST_ERROR("Sorry, parking lot is full"),
    INVALID_VALUE("{variable} value is incorrect"),
    INVALID_FILE("Invalid File"),
    PROCESSING_ERROR("Internal Processing Error "),
    INVALID_REQUEST("Invalid Request");

    private String message = "";

    private ParkingError(String s) {
        this.message= s;
    }

    public String getMessage() {
        return message;
    }
}
