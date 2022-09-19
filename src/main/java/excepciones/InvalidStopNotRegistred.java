package excepciones;

public class InvalidStopNotRegistred extends RuntimeException {
  public InvalidStopNotRegistred(String errorMessage) {
    super(errorMessage);
  }
}