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
  private static final String REQUEST_CHANNEL_BOOST_MESSAGE_TEMPLATE =
      "Please, support our channel by boosting it via link: t.me/" + Environment.CHANNEL_ID.getValue() + "?boost.\n" +
      "It will help us to provide more personalized experience for our subscribers. Thank you for your support!";

  @Override
  public boolean isApplicable(Update update) {
    return isTextMessage(update) && update.getMessage().getText().startsWith(START_PREFIX);
  }

  @Override
  public void handle(Update update) {
    var message = update.getMessage();
    var userFrom = message.getFrom();
    var fromChatId = String.valueOf(userFrom.getId());
    var username = userFrom.getUserName();

    sendMessage(buildMessage(fromChatId, START_MESSAGE_TEMPLATE));
    if (userFrom.getIsPremium()) {
      sendMessage(buildMessage(fromChatId, REQUEST_CHANNEL_BOOST_MESSAGE_TEMPLATE));
      log.info("Sent channel boosting request to user with id: {} and username: {}", fromChatId, username);
    }
    log.info("Sent welcome message to user with id: {} and username: {}", fromChatId, username);
  }
}
