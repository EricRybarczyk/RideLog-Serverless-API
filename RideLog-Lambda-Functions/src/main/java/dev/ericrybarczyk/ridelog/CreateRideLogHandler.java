package dev.ericrybarczyk.ridelog;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import java.net.HttpURLConnection;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;


public class CreateRideLogHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {

        final String requestBody = input.getBody();
        final ObjectMapper objectMapper = new ObjectMapper();

        RideLog rideLog;
        try {
            rideLog = objectMapper.readValue(requestBody, RideLog.class);

            DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.create();

            DynamoDbTable<RideLog> rideLogsTable = enhancedClient.table("RideLogs", TableSchema.fromBean(RideLog.class));

            rideLogsTable.putItem(rideLog);

        } catch (JsonProcessingException e) {
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(HttpURLConnection.HTTP_BAD_REQUEST)
                    .withBody(e.getMessage());
        } catch (DynamoDbException e) {
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(HttpURLConnection.HTTP_INTERNAL_ERROR)
                    .withBody(e.getMessage());
        }

        final Map<String, String> headers = new HashMap<>();
        headers.put("Location", String.format("/ridelogs/%s/%s", rideLog.getUserId(),
                rideLog.getStartDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)));

        return new APIGatewayProxyResponseEvent()
                .withStatusCode(HttpURLConnection.HTTP_CREATED)
                .withHeaders(headers);
    }
}
