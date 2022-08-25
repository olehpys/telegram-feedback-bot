package com.pysarenko.feedbackbot.handler;

import static com.pysarenko.feedbackbot.utils.TelegramUtils.buildMessage;

import com.pysarenko.feedbackbot.model.Environment;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
public class WelcomeMessageHandler extends MessageHandler {

  private static final String START_PREFIX = "/start";
  private static final String START_MESSAGE_TEMPLATE = "Hi there!\n" +
      "This bot will help you to stay in touch with the " + Environment.CHANNEL_ID.getValue() + " channel administration. " +
      "Just drop a message next and we will text you back shortly!";

  @Override
  public boolean isApplicable(Update update) {
    return isTextMessage(update) && update.getMessage().getText().startsWith(START_PREFIX);
  }

  @Override
  public void handle(Update update) {
    var message = update.getMessage();
    var fromChatId = String.valueOf(message.getFrom().getId());
    var username = message.getFrom().getUserName();

    sendMessage(buildMessage(fromChatId, START_MESSAGE_TEMPLATE));
    log.info("Sent welcome message to user with id: {} and username: {}", fromChatId, username);
  }
}
