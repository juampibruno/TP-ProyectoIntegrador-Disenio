package excepciones;

public class MiembroNoEsCompa extends RuntimeException {
  public MiembroNoEsCompa(String errorMessage) {
    super(errorMessage);
  }
}
