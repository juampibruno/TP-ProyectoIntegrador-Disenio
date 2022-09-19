package excepciones;

public class InvalidUnitsException extends RuntimeException {
  public InvalidUnitsException(String errorMessage) {
    super(errorMessage);
  }
}