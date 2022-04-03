package dev.ericrybarczyk.ridelog;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import java.net.HttpURLConnection;
import java.util.Map;


public class GetRideLogHandler extends BaseRideLogHandler {

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {

        final Map<String, String> pathParameters = input.getPathParameters();
        final String userId = pathParameters.get("userId");
        final String startDateTime = pathParameters.get("startDateTime");

        if (userId == null || startDateTime == null) {
            return createResponse(HttpURLConnection.HTTP_BAD_REQUEST, "Required Parameters Missing");
        }

        Key key = Key.builder()
                .partitionValue(userId)
                .sortValue(startDateTime)
                .build();

        try {
            final RideLog item = rideLogsTable.getItem(key);
            if (item == null) {
                return createResponse(HttpURLConnection.HTTP_NOT_FOUND, "Requested RideLog Not Found");
            }

            final String jsonRideLog = objectMapper.writeValueAsString(item);
            return createResponse(HttpURLConnection.HTTP_OK, jsonRideLog);

        } catch (JsonProcessingException | DynamoDbException e) {
            return createResponse(HttpURLConnection.HTTP_INTERNAL_ERROR, e.getMessage());
        }
    }

}
