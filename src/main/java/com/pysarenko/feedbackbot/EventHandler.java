package com.pysarenko.feedbackbot;

import static com.pysarenko.feedbackbot.factory.Factory.HANDLERS;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.pysarenko.feedbackbot.model.aws.LambdaEvent;
import com.pysarenko.feedbackbot.utils.JsonMapper;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
public class EventHandler implements RequestStreamHandler {

  @Override
  public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
    String inputJson = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
    var lambdaEvent = JsonMapper.readValue(inputJson, LambdaEvent.class);
    var update = JsonMapper.readValue(lambdaEvent.getBody(), Update.class);
    log.info("Received telegram update event: {}", JsonMapper.toJson(update));

    HANDLERS.stream()
        .filter(handler -> handler.isApplicable(update))
        .findFirst()
        .ifPresent(handler -> handler.handle(update));
  }
}
