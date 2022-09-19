package excepciones;

public class LocalidadNotFound extends RuntimeException {
  public LocalidadNotFound(String errorMessage) {
    super(errorMessage);
  }
}