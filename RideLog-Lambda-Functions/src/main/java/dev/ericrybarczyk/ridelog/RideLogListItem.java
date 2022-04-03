package dev.ericrybarczyk.ridelog;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.time.LocalDateTime;


public class RideLogListItem {

    private String userId;
    private LocalDateTime startDateTime;
    private String rideTitle;
    private String rideLocation;

    public RideLogListItem() {
    }

    public RideLogListItem(String userId, LocalDateTime startDateTime, String rideTitle, String rideLocation) {
        this.userId = userId;
        this.startDateTime = startDateTime;
        this.rideTitle = rideTitle;
        this.rideLocation = rideLocation;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
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
