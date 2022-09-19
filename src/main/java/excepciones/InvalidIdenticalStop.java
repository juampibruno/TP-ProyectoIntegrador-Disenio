package excepciones;

public class InvalidIdenticalStop extends RuntimeException {
  public InvalidIdenticalStop(String errorMessage) {
    super(errorMessage);
  }
}