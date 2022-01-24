package com.essential.app.validation.functional;

// this class captures the reason why something has failed
// it has a written errorMessage and a specific ErrorEnum error to indicate the failure
// For example, for i18n the errorEnum might be needed to identify the right message
public class Violation {

  private final String errorMessage;
  private final ErrorEnum error;

  private Violation(String errorMessage, ErrorEnum error){
    this.errorMessage = errorMessage;
    this.error = error;
  }

  public static Violation violation(String errorMessage, ErrorEnum error){
    return new Violation(errorMessage, error);
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public ErrorEnum getError() {
    return error;
  }
}