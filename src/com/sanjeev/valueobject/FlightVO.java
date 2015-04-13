package com.sanjeev.valueobject;

/**
 * Created by shemnani on 12/4/15.
 */

/*
    Contains below information about each flight;
        1.  Name
        2.  Departure Gate
        3.  Destination Airport Code
        4.  Departure time
 */

public class FlightVO {
    private String flightName;
    private String gate;
    private String destinationAirportCode;
    private String departureTime;

    public FlightVO(String flightName, String gate, String destinationAirportCode, String departureTime) {
        this.flightName = flightName;
        this.gate = gate;
        this.destinationAirportCode = destinationAirportCode;
        this.departureTime = departureTime;
    }

    public String getFlightName() {
        return flightName;
    }

    public void setFlightName(String flightName) {
        this.flightName = flightName;
    }

    public String getGate() {
        return gate;
    }

    public void setGate(String gate) {
        this.gate = gate;
    }

    public String getDestinationAirportCode() {
        return destinationAirportCode;
    }

    public void setDestinationAirportCode(String destinationAirportCode) {
        this.destinationAirportCode = destinationAirportCode;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }
}
