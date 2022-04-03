package dev.ericrybarczyk.ridelog;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import java.net.HttpURLConnection;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;


public class CreateRideLogHandler extends BaseRideLogHandler {

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {

        final String requestBody = input.getBody();

        RideLog rideLog;
        try {
            rideLog = objectMapper.readValue(requestBody, RideLog.class);
            rideLogsTable.putItem(rideLog);

        } catch (JsonProcessingException | DynamoDbException e) {
            return createResponse(HttpURLConnection.HTTP_INTERNAL_ERROR, e.getMessage());
        }

        final Map<String, String> headers = new HashMap<>();
        headers.put("Location", String.format("/ridelogs/%s/%s", rideLog.getUserId(),
                rideLog.getStartDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));

        return createResponse(HttpURLConnection.HTTP_CREATED, headers);
    }
}
