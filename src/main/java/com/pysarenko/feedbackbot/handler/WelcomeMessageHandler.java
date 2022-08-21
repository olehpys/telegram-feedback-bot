package com.pysarenko.feedbackbot.handler;

import static com.pysarenko.feedbackbot.utils.TelegramUtils.START_MESSAGE_TEMPLATE;
import static com.pysarenko.feedbackbot.utils.TelegramUtils.START_PREFIX;
import static com.pysarenko.feedbackbot.utils.TelegramUtils.buildMessage;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
public class WelcomeMessageHandler extends MessageHandler {

  @Override
  public boolean isApplicable(Update update) {
    return isTextMessage(update) && update.getMessage().getText().startsWith(START_PREFIX);
  }

  @Override
  public void handle(Update update) {
    var message = update.getMessage();
    var fromChatId = String.valueOf(message.getFrom().getId());
    var username = String.valueOf(message.getFrom().getUserName());

    sendMessage(buildMessage(fromChatId, START_MESSAGE_TEMPLATE));
    log.info("Sent welcome message to user with id: {} and username: {}", fromChatId, username);
  }
}
