package dev.ericrybarczyk.ridelog;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import java.util.Map;


public abstract class BaseRideLogHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.create();
    protected final DynamoDbTable<RideLog> rideLogsTable = enhancedClient.table("RideLogs", TableSchema.fromBean(RideLog.class));
    protected final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public abstract APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context);

    protected APIGatewayProxyResponseEvent createResponse(int statusCode, String body) {
        return new APIGatewayProxyResponseEvent()
                .withStatusCode(statusCode)
                .withBody(body);
    }

    protected APIGatewayProxyResponseEvent createResponse(int statusCode, String body, Map<String, String> headers) {
        return new APIGatewayProxyResponseEvent()
                .withStatusCode(statusCode)
                .withHeaders(headers)
                .withBody(body);
    }

    protected APIGatewayProxyResponseEvent createResponse(int statusCode, Map<String, String> headers) {
        return new APIGatewayProxyResponseEvent()
                .withStatusCode(statusCode)
                .withHeaders(headers);
    }

}
