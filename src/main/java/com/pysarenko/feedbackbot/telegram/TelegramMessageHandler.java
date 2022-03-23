package com.pysarenko.feedbackbot.telegram;

import static com.pysarenko.feedbackbot.model.Environment.ADMIN_ID;
import static com.pysarenko.feedbackbot.telegram.TelegramUtils.START_MESSAGE_TEMPLATE;
import static com.pysarenko.feedbackbot.telegram.TelegramUtils.START_PREFIX;
import static com.pysarenko.feedbackbot.telegram.TelegramUtils.forwardMessage;
import static com.pysarenko.feedbackbot.telegram.TelegramUtils.sendMessage;
import static java.util.Objects.nonNull;

import java.io.Serializable;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public class TelegramMessageHandler {

  private static final AbsSender SENDER = new TelegramSender();

  public void handleMessage(Update update) throws TelegramApiException {
    sendWelcomeMessage(update);

    var replyToMessage = update.getMessage().getReplyToMessage();
    if (nonNull(replyToMessage)) {
      sendReplyMessage(update, replyToMessage);
    } else {
      forwardMessageToAdmin(update);
    }
  }

  private void forwardMessageToAdmin(Update update) throws TelegramApiException {
    var fromChatId = String.valueOf(update.getMessage().getFrom().getId());
    var username = String.valueOf(update.getMessage().getFrom().getUserName());
    var messageId = update.getMessage().getMessageId();
    var forwardMessage = forwardMessage(ADMIN_ID.getValue(), fromChatId, messageId);

    send(forwardMessage);
    log.info("Forwarded message to admin from user with id=[{}] and username [{}]", fromChatId, username);
  }

  private void sendReplyMessage(Update update, Message replyToMessage) throws TelegramApiException{
    var userId = replyToMessage.getForwardFrom().getId();
    var userName = replyToMessage.getForwardFrom().getUserName();
    var text = update.getMessage().getText();
    var message = sendMessage(String.valueOf(userId), text);

    var execute = send(message);
    log.info("Sent message with id [{}] to userId [{}] with username [{}]", execute.getMessageId(), userId, userName);
  }

  private void sendWelcomeMessage(Update update) throws TelegramApiException {
    if (update.getMessage().getText() == null) {
      return;
    }
    if (START_PREFIX.contains(update.getMessage().getText())) {
      var fromChatId = String.valueOf(update.getMessage().getFrom().getId());
      var username = String.valueOf(update.getMessage().getFrom().getUserName());
      send(sendMessage(fromChatId, START_MESSAGE_TEMPLATE));
      log.info("Sent welcome message to user with id [{}] and username [{}]", fromChatId, username);
    }
  }

  private <T extends Serializable> T send(BotApiMethod<T> message) throws TelegramApiException {
      return SENDER.execute(message);
  }
}
