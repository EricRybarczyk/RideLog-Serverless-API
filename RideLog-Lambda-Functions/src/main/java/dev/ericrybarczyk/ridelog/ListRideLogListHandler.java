package dev.ericrybarczyk.ridelog;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class ListRideLogListHandler extends BaseRideLogHandler {

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {

        final Map<String, String> pathParameters = input.getPathParameters();
        final String userId = pathParameters.get("userId");

        if (userId == null) {
            return createResponse(HttpURLConnection.HTTP_BAD_REQUEST, "Required Parameter Missing");
        }

        try {

            QueryConditional queryConditional = QueryConditional
                    .keyEqualTo(Key.builder().partitionValue(userId)
                            .build());

            List<RideLogListItem> rideLogs = rideLogsTable.query(r -> r.queryConditional(queryConditional)
                            .addAttributeToProject("userId")
                            .addAttributeToProject("startDateTime")
                            .addAttributeToProject("rideTitle")
                            .addAttributeToProject("rideLocation")
                            .scanIndexForward(false)
                    ).items()
                    .stream()
                    .map(r -> new RideLogListItem(
                            r.getUserId(),
                            r.getStartDateTime(),
                            r.getRideTitle(),
                            r.getRideLocation()
                    ))
                    .collect(Collectors.toList());

            final String jsonRideLogList = objectMapper.writeValueAsString(rideLogs);
            return createResponse(HttpURLConnection.HTTP_OK, jsonRideLogList);

        } catch (JsonProcessingException | DynamoDbException e) {
            return createResponse(HttpURLConnection.HTTP_INTERNAL_ERROR, e.getMessage());
        }

    }

}
