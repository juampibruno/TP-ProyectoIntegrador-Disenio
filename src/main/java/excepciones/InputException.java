package excepciones;

public class InputException extends RuntimeException {
  public InputException(String errorMessage) {
    super(errorMessage);
  }
}
