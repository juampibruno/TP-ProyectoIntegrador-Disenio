package excepciones;

public class AlreadyExists extends RuntimeException {
  public AlreadyExists(String errorMessage) {
    super(errorMessage);
  }
}
