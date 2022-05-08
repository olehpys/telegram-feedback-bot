package com.pysarenko.feedbackbot.telegram;

import static com.pysarenko.feedbackbot.model.Environment.ADMIN_ID;
import static com.pysarenko.feedbackbot.utils.TelegramUtils.START_MESSAGE_TEMPLATE;
import static com.pysarenko.feedbackbot.utils.TelegramUtils.START_PREFIX;
import static com.pysarenko.feedbackbot.utils.TelegramUtils.buildForwardMessage;
import static com.pysarenko.feedbackbot.utils.TelegramUtils.buildMessage;
import static java.util.Objects.nonNull;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
public class TelegramMessageHandler {

  private static final AbsSender SENDER = new TelegramSender();

  public void handleMessage(Update update) throws TelegramApiException {
    var message = update.getMessage();
    if (message == null) {
      return;
    }

    sendWelcomeMessage(message);

    sendReplyMessageByUserId(message);
    var replyToMessage = message.getReplyToMessage();
    if (nonNull(replyToMessage)) {
      sendReplyMessageByUserId(update, replyToMessage);
    } else {
      forwardMessageToAdmin(update);
    }
  }

  private void forwardMessageToAdmin(Update update) throws TelegramApiException {
    var fromChatId = String.valueOf(update.getMessage().getFrom().getId());
    var username = String.valueOf(update.getMessage().getFrom().getUserName());
    var messageId = update.getMessage().getMessageId();
    if(!Objects.equals(fromChatId, ADMIN_ID.getValue())){
      var forwardMessage = buildForwardMessage(ADMIN_ID.getValue(), fromChatId, messageId);
      sendMessage(forwardMessage);
      sendMessage(buildMessage(ADMIN_ID.getValue(), "Forwarded message from userId #" + fromChatId));
      log.info("Forwarded message to admin from user with id=[{}] and username [{}]", fromChatId, username);
    }
  }

  private void sendReplyMessageByUserId(Update update, Message replyToMessage) throws TelegramApiException {
    var userId = Optional.ofNullable(replyToMessage.getForwardFrom())
        .map(User::getId)
        .orElseThrow(TelegramApiException::new);
    var userName = replyToMessage.getForwardFrom().getUserName();
    var text = update.getMessage().getText();
    var message = buildMessage(String.valueOf(userId), text);

    var execute = sendMessage(message);
    log.info("Sent message with id [{}] to userId [{}] with username [{}]", execute.getMessageId(), userId, userName);
  }

  private void sendWelcomeMessage(Message message) throws TelegramApiException {
    var messageText = Optional.ofNullable(message.getText())
        .orElse("");
    if (START_PREFIX.contains(messageText)) {
      var fromChatId = String.valueOf(message.getFrom().getId());
      var username = String.valueOf(message.getFrom().getUserName());
      sendMessage(buildMessage(fromChatId, START_MESSAGE_TEMPLATE));
      log.info("Sent welcome message to user with id [{}] and username [{}]", fromChatId, username);
    }
  }

  private void sendReplyMessageByUserId(Message message) throws TelegramApiException {
    var messageText = Optional.ofNullable(message.getText())
        .orElse("");
    if (messageText.startsWith("#")) {
      var userId = messageText.substring(1, messageText.indexOf(" "));
      var replyMessageText = messageText.substring(messageText.indexOf(" ") + 1);
      var execute = sendMessage(buildMessage(userId, replyMessageText));
      log.info("Sent message with id [{}] to userId [{}]", execute.getMessageId(), userId);
    }
  }

  private <T extends Serializable> T sendMessage(BotApiMethod<T> message) throws TelegramApiException {
      return SENDER.execute(message);
  }
}
