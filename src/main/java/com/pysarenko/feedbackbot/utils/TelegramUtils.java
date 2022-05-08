package com.pysarenko.feedbackbot.utils;

import com.pysarenko.feedbackbot.model.Environment;
import lombok.experimental.UtilityClass;
import org.telegram.telegrambots.meta.api.methods.ForwardMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@UtilityClass
public class TelegramUtils {

  public static final String START_PREFIX = "/start";
  public static final String START_MESSAGE_TEMPLATE = "Hi there!\n" +
      "This bot will help you to stay in touch with the " + Environment.CHANNEL_ID.getValue() + " channel administration. " +
      "Just drop a message next and we will text you back shortly!";

  public static SendMessage buildMessage(String chatId, String message) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(chatId);
    sendMessage.setText(message);
    return sendMessage;
  }

  public static ForwardMessage buildForwardMessage(String targetChatId, String fromChatId, Integer messageId) {
    return new ForwardMessage(targetChatId, fromChatId, messageId);
  }
}
