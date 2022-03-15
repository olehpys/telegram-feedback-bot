package com.pysarenko.feedbackbot.model.telegram;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Message {

  private Long messageId;
  private From from;
  private Chat chat;
  private Instant date;
  private String text;

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  public static class From {

    private Long id;
    private Boolean isBot;
    private String firstName;
    private String lastName;
    private String username;
    private String languageCode;
  }

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  public static class Chat {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String type;
  }
}
