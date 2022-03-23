package com.pysarenko.feedbackbot.model.aws;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiGatewayResponse {

  private int statusCode;
  private Map<String, String> headers;
  private String body;
  private boolean isBase64Encoded;
}
