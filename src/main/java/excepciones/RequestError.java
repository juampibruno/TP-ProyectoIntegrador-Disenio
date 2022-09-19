package excepciones;

public class RequestError extends RuntimeException {
  public RequestError(String errorMessage) {
    super(errorMessage);
  }
}