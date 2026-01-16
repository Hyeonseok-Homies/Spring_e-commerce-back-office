package com.backoffice.Error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@Getter
@AllArgsConstructor
public class ErrorResponse {
  private final int status;
  private final String message;
  private final List<Errors> errors;

  @Getter
  @AllArgsConstructor
  public static class Errors {
    private final String field;
    private final String value;
    private final String reason;
  }
}
