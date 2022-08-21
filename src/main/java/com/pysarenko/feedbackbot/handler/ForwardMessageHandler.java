package com.pysarenko.feedbackbot.handler;

import static com.pysarenko.feedbackbot.model.Environment.ADMIN_ID;
import static com.pysarenko.feedbackbot.model.Environment.BOT_USERNAME;
import static com.pysarenko.feedbackbot.utils.TelegramUtils.buildForwardMessage;
import static com.pysarenko.feedbackbot.utils.TelegramUtils.buildMessage;
import static java.util.Objects.isNull;

import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
public class ForwardMessageHandler extends MessageHandler {

  @Override
  public boolean isApplicable(Update update) {
    var fromChatId = String.valueOf(update.getMessage().getFrom().getId());
    var replyToMessage = update.getMessage().getReplyToMessage();
    return update.hasMessage() && (isNull(replyToMessage)
        || replyToMessage.getFrom().getUserName().equals(BOT_USERNAME.getValue()))
        && !Objects.equals(fromChatId, ADMIN_ID.getValue());
  }

  @Override
  public void handle(Update update) {
    var fromChatId = String.valueOf(update.getMessage().getFrom().getId());
    var username = String.valueOf(update.getMessage().getFrom().getUserName());
    var messageId = update.getMessage().getMessageId();

    sendMessage(buildForwardMessage(ADMIN_ID.getValue(), fromChatId, messageId));
    sendMessage(buildMessage(ADMIN_ID.getValue(), "Forwarded message from userId: #id" + fromChatId));
    log.info("Forwarded message to admin from userId: {} and username: {}", fromChatId, username);
  }
}
