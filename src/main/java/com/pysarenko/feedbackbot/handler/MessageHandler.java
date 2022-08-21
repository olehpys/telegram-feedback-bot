package com.pysarenko.feedbackbot.handler;

import com.pysarenko.feedbackbot.telegram.TelegramSender;
import java.io.Serializable;
import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

public abstract class MessageHandler {

  private static final AbsSender SENDER = new TelegramSender();

  public abstract boolean isApplicable(Update update);

  public abstract void handle(Update update);

  public boolean isTextMessage(Update update) {
    return update.hasMessage() && update.getMessage().hasText();
  }

  @SneakyThrows
  public <T extends Serializable> T sendMessage(BotApiMethod<T> message) {
    return SENDER.execute(message);
  }
}
