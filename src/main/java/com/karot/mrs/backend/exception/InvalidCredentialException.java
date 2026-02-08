package com.karot.mrs.backend.exception;

public class InvalidCredentialException extends RuntimeException {
  public InvalidCredentialException(String message) {
    super(message);
  }
}
