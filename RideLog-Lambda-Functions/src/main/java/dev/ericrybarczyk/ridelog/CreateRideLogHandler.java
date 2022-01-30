package dev.ericrybarczyk.ridelog;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class CreateRideLogHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {

        final String requestBody = input.getBody();
        final ObjectMapper objectMapper = new ObjectMapper();

        try {
            final RideLog rideLog = objectMapper.readValue(requestBody, RideLog.class);
        } catch (JsonProcessingException e) {
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                    .withBody(e.getMessage());
        }

        // TODO: persistence of the RideLog instance
        final UUID rideLogId = UUID.randomUUID();

        final Map<String, String> headers = new HashMap<>();
        headers.put("Location", "/ridelogs/" + rideLogId.toString());

        return new APIGatewayProxyResponseEvent()
                .withStatusCode(HttpURLConnection.HTTP_CREATED)
                .withHeaders(headers);
    }
}
