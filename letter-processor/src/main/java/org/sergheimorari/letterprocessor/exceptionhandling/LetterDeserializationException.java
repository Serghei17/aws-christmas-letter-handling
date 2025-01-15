package org.sergheimorari.letterprocessor.exceptionhandling;

public class LetterDeserializationException extends RuntimeException {
  public LetterDeserializationException(String message) {
    super(message);
  }
}
