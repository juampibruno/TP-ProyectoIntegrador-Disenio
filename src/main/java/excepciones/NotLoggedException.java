package excepciones;

public class NotLoggedException extends RuntimeException {
  public NotLoggedException(String errorMessage) {
    super(errorMessage);
  }
}
