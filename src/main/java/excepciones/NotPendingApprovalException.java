package excepciones;

public class NotPendingApprovalException extends RuntimeException {
  public NotPendingApprovalException(String errorMessage) {
    super(errorMessage);
  }
}