package com.sanjeev.valueobject;

/**
 * Created by shemnani on 12/4/15.
 */

/*
    Contains below information about bag node;
        1.  Bag Unique ID
        2.  Source Node
        3.  Destination Node
 */

public class BagVO {

    private String bagId;
    private String arrivalGate;
    private String destination;

    public BagVO(String bagId, String arrivalGate, String destination) {
        this.bagId = bagId;
        this.arrivalGate = arrivalGate;
        this.destination = destination;
    }

    public String getBagId() {
        return bagId;
    }

    public void setBagId(String bagId) {
        this.bagId = bagId;
    }

    public String getArrivalGate() {
        return arrivalGate;
    }

    public void setArrivalGate(String arrivalGate) {
        this.arrivalGate = arrivalGate;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
