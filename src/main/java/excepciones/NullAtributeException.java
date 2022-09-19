package excepciones;

public class NullAtributeException extends RuntimeException {
  public NullAtributeException(String errorMessage) {
    super(errorMessage);
  }
}
