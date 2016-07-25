package eu.span.dev.osijek.di.compdepend.model;

import javax.inject.Inject;

public class Motor {

    private int rpm;

    @Inject
    public Motor(){
        this.rpm = 0;
    }

    public int getRpm(){
        return rpm;
    }

    public void accelerate(int value){
        rpm = rpm + value;
    }

    public void brake(){
        rpm = 0;
    }
}