package dev.ericrybarczyk.ridelog;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class RideLogTest {

    private ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void reviewJsonSerialization() throws Exception {
        RideLog rideLog = new RideLog();
        rideLog.setRideTitle("Fun Ride");
        rideLog.setRideLocation("Best Trail");

        rideLog.setStartDateTime(LocalDateTime.now().minusHours(8));
        rideLog.setEndDateTime(LocalDateTime.now().minusHours(6));

        // start 36.477759, -94.236827
        rideLog.setStartLatitude(36.477759);
        rideLog.setStartLongitude(-94.236827);

        // end 36.494700, -94.206545
        rideLog.setEndLatitude(36.494700);
        rideLog.setEndLongitude(-94.206545);

        rideLog.setDistance(18.7);

        final String jsonRideLog = objectMapper.writeValueAsString(rideLog);

        assertThat(jsonRideLog).isNotNull();

        /*  it worked - JSON result:
        {
            "startDateTime": "2022-01-25T12:09:42.032367",
            "endDateTime": "2022-01-25T14:09:42.03818",
            "startLatitude": 36.477759,
            "startLongitude": -94.236827,
            "endLatitude": 36.4947,
            "endLongitude": -94.206545,
            "distance": 18.7,
            "rideTitle": "Fun Ride",
            "rideLocation": "Best Trail"
        }
         */

    }

}
