package dev.ericrybarczyk.ridelog;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDateTime;

public class RideLog {
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private double startLatitude;
    private double startLongitude;
    private double endLatitude;
    private double endLongitude;
    private double distance;
    private String rideTitle;
    private String rideLocation;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public double getStartLatitude() {
        return startLatitude;
    }

    public void setStartLatitude(double startLatitude) {
        this.startLatitude = startLatitude;
    }

    public double getStartLongitude() {
        return startLongitude;
    }

    public void setStartLongitude(double startLongitude) {
        this.startLongitude = startLongitude;
    }

    public double getEndLatitude() {
        return endLatitude;
    }

    public void setEndLatitude(double endLatitude) {
        this.endLatitude = endLatitude;
    }

    public double getEndLongitude() {
        return endLongitude;
    }

    public void setEndLongitude(double endLongitude) {
        this.endLongitude = endLongitude;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getRideTitle() {
        return rideTitle;
    }

    public void setRideTitle(String rideTitle) {
        this.rideTitle = rideTitle;
    }

    public String getRideLocation() {
        return rideLocation;
    }

    public void setRideLocation(String rideLocation) {
        this.rideLocation = rideLocation;
    }

}
