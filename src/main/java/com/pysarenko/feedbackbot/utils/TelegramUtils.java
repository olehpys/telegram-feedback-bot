package com.pysarenko.feedbackbot.utils;

import lombok.experimental.UtilityClass;
import org.telegram.telegrambots.meta.api.methods.ForwardMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@UtilityClass
public class TelegramUtils {

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
