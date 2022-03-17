package com.pysarenko.feedbackbot;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pysarenko.feedbackbot.model.aws.ApiGatewayRequest;
import com.pysarenko.feedbackbot.model.aws.ApiGatewayResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
public class EventHandler implements RequestStreamHandler {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
    String inputJson = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

    var apiGatewayRequest = JsonMapper.readValue(inputJson, ApiGatewayRequest.class);
    var update = JsonMapper.readValue(apiGatewayRequest.getBody(), Update.class);
    log.info("Received telegram update event: {}", update);

    buildApiGatewayResponse(outputStream);
  }

  private void buildApiGatewayResponse(OutputStream outputStream) throws IOException {
    var response = ApiGatewayResponse.builder()
        .statusCode(HttpStatus.SC_OK)
        .body(objectMapper.writeValueAsString("ok"))
        .isBase64Encoded(false)
        .build();
    OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
    writer.write(objectMapper.writeValueAsString(response));
    writer.close();
  }
}
