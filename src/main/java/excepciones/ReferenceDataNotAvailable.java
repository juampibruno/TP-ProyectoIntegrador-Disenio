package excepciones;

public class ReferenceDataNotAvailable extends RuntimeException {
  public ReferenceDataNotAvailable(String errorMessage) {
    super(errorMessage);
  }
}
