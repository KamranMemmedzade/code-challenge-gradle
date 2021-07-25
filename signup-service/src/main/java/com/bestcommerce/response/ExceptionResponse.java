package com.bestcommerce.response;

public class ExceptionResponse {
  private final String message;
  private final String path;

  public ExceptionResponse(String message, String path) {
    this.message = message;
    this.path = path;
  }

  public String getMessage() {
    return message;
  }

  public String getPath() {
    return path;
  }
}
