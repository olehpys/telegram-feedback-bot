package com.pysarenko.feedbackbot;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pysarenko.feedbackbot.model.aws.ApiGatewayRequest;
import com.pysarenko.feedbackbot.model.aws.ApiGatewayResponse;
import com.pysarenko.feedbackbot.telegram.TelegramMessageHandler;
import com.pysarenko.feedbackbot.utils.JsonMapper;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public class EventHandler implements RequestStreamHandler {

  private static final ObjectMapper MAPPER = new ObjectMapper();
  private static final TelegramMessageHandler MESSAGE_HANDLER = new TelegramMessageHandler();

  @Override
  public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
    String inputJson = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
    log.info("Received telegram update event in JSON: {}", inputJson);

    var apiGatewayRequest = JsonMapper.readValue(inputJson, ApiGatewayRequest.class);
    var update = JsonMapper.readValue(apiGatewayRequest.getBody(), Update.class);
    log.info("Received telegram update event: {}", update);

    try {
      MESSAGE_HANDLER.handleMessage(update);
    } catch (TelegramApiException e) {
      log.error("Telegram exception", e);
    } finally {
      buildApiGatewayResponse(outputStream);
    }
  }

  private void buildApiGatewayResponse(OutputStream outputStream) throws IOException {
    var response = ApiGatewayResponse.builder()
        .statusCode(HttpStatus.SC_OK)
        .isBase64Encoded(false)
        .build();
    var writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
    writer.write(MAPPER.writeValueAsString(response));
    writer.close();
  }
}
