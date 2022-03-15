package com.pysarenko.feedbackbot.model.aws;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pysarenko.feedbackbot.model.telegram.Update;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder(toBuilder = true)
public class ApiGatewayRequest {

  private Update body;
}
