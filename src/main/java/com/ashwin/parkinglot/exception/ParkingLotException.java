package com.ashwin.parkinglot.exception;

public class ParkingLotException extends Exception{

    private String		errorCode		= null;	// this will hold system defined error code
    private Object[]	errorParameters	= null;	// this will hold parameters for error code/message


    public ParkingLotException(String message, Throwable throwable)
    {
        super(message, throwable);
    }

    public ParkingLotException(String message)
    {
        super(message);
    }


    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Object[] getErrorParameters() {
        return errorParameters;
    }

    public void setErrorParameters(Object[] errorParameters) {
        this.errorParameters = errorParameters;
    }
}
