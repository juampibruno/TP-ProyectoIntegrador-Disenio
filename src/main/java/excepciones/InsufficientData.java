package excepciones;

public class InsufficientData extends RuntimeException {
  public InsufficientData(String errorMessage) {
    super(errorMessage);
  }
}
