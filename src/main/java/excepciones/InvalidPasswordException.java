package excepciones;

public class InvalidPasswordException extends RuntimeException {
  public InvalidPasswordException(String errorMessage) {
    super(errorMessage);
  }
}