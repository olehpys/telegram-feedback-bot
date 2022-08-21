package com.pysarenko.feedbackbot.handler;

import static com.pysarenko.feedbackbot.utils.TelegramUtils.buildMessage;
import static java.util.Objects.nonNull;

import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
public class ReplyMessageHandler extends MessageHandler {

  private static final String ID_PREFIX = "#id";

  @Override
  public boolean isApplicable(Update update) {
    var replyToMessage = update.getMessage().getReplyToMessage();
    return isTextMessage(update) && nonNull(replyToMessage) && nonNull(replyToMessage.getText());
  }

  @Override
  public void handle(Update update) {
    var replyToMessage = update.getMessage().getReplyToMessage();
    var text = replyToMessage.getText();
    if (text.contains(ID_PREFIX)) {
      sendReplyMessageByUserId(update, replyToMessage);
    } else {
      sendReplyMessageByUserName(update, replyToMessage);
    }
  }

  private void sendReplyMessageByUserName(Update update, Message replyToMessage) {
    var userId = replyToMessage.getForwardFrom().getId();
    var userName = replyToMessage.getForwardFrom().getUserName();
    var text = update.getMessage().getText();
    var message = buildMessage(String.valueOf(userId), text);

    var execute = sendMessage(message);
    log.info("Sent message with id: {} to userId: {} with username: {}", execute.getMessageId(), userId, userName);
  }

  private void sendReplyMessageByUserId(Update update, Message replyToMessage) {
    var text = update.getMessage().getText();
    var replyToMessageText = replyToMessage.getText();
    var userId = replyToMessageText.substring(replyToMessageText.lastIndexOf(ID_PREFIX) + ID_PREFIX.length());

    var execute = sendMessage(buildMessage(userId, text));
    log.info("Sent message with id: {} to userId: {}", execute.getMessageId(), userId);
  }
}
