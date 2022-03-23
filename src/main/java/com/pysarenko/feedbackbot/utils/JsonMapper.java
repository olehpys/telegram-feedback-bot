package com.pysarenko.feedbackbot.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JsonMapper {

  private static final ObjectMapper MAPPER = new ObjectMapper()
      .registerModule(new JavaTimeModule());

  @SneakyThrows
  public <T> T readValue(String json, Class<T> requiredClass) {
    return MAPPER.readValue(json, requiredClass);
  }
}
