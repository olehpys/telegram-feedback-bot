package com.pysarenko.feedbackbot.model;

import java.util.NoSuchElementException;
import java.util.Optional;

public enum Environment {

  BOT_TOKEN,
  BOT_USERNAME,
  ADMIN_ID,
  CHANNEL_ID;

  public String getValue() {
    return Optional.ofNullable(System.getenv(this.name()))
        .orElseThrow(() -> new NoSuchElementException(this.name()));
  }
}
