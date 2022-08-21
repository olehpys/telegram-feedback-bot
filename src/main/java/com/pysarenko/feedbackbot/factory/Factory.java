package com.pysarenko.feedbackbot.factory;

import com.pysarenko.feedbackbot.handler.ForwardMessageHandler;
import com.pysarenko.feedbackbot.handler.MessageHandler;
import com.pysarenko.feedbackbot.handler.ReplyMessageHandler;
import com.pysarenko.feedbackbot.handler.WelcomeMessageHandler;
import java.util.List;

public class Factory {

  private static final MessageHandler WELCOME_MESSAGE_HANDLER = new WelcomeMessageHandler();
  private static final MessageHandler FORWARD_MESSAGE_HANDLER = new ForwardMessageHandler();
  private static final MessageHandler REPLY_MESSAGE_HANDLER = new ReplyMessageHandler();

  public static final List<MessageHandler> HANDLERS = List.of(WELCOME_MESSAGE_HANDLER, FORWARD_MESSAGE_HANDLER,
      REPLY_MESSAGE_HANDLER);
}
