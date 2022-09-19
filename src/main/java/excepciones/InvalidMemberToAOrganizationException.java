package excepciones;

public class InvalidMemberToAOrganizationException extends RuntimeException {
  public InvalidMemberToAOrganizationException(String errorMessage) {
    super(errorMessage);
  }
}
