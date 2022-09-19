package consumo;

public enum Periodicidad {
  ANUAL("Anual"),
  MENSUAL("Mensual");

  private final String text;

  Periodicidad(final String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return text;
  }
}
