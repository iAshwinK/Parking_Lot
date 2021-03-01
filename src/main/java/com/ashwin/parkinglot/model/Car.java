package com.ashwin.parkinglot.model;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Car extends Vehicle {
    public Car(String registrationNo, String colour) {
        super(registrationNo, colour);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        super.writeExternal(out);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        super.readExternal(in);
    }
}
