package com.pysarenko.feedbackbot.telegram;

import static com.pysarenko.feedbackbot.model.Environment.BOT_TOKEN;
import static com.pysarenko.feedbackbot.model.Environment.BOT_USERNAME;

import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TelegramSender extends TelegramWebhookBot {

  @Override
  public BotApiMethod<?> onWebhookUpdateReceived(Update update) {
    return null;
  }

  @Override
  public String getBotUsername() {
    return BOT_USERNAME.getValue();
  }

  @Override
  public String getBotToken() {
    return BOT_TOKEN.getValue();
  }

  @Override
  public String getBotPath() {
    return BOT_USERNAME.getValue();
  }
}
