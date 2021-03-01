package com.ashwin.parkinglot.model;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public abstract class Vehicle implements Externalizable {

    private String registrationNo = null;
    private String colour = null;

    public Vehicle(String registrationNo, String colour) {
        this.registrationNo = registrationNo;
        this.colour = colour;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "registrationNo='" + registrationNo + '\'' +
                ", colour='" + colour + '\'' +
                '}';
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(getRegistrationNo());
        out.writeObject(getColour());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        setRegistrationNo((String) in.readObject());
        setColour((String) in.readObject());
    }
}
