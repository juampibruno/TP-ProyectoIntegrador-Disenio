package consumo;

public enum TipoDeConsumo {
  GAS_NATURAL("Gas Natural", Unidad.M3),
  DIESEL_GASOIL("Diesel/Gasoil", Unidad.LT),
  NAFTA("Nafta", Unidad.LT),
  CARBON("Carbón", Unidad.KG),
  COMBUSTIBLE_CONSUMIDO_GASOIL("Combustible consumido - Gasoil", Unidad.LTS),
  COMBUSTIBLE_CONSUMIDO_NAFTA("Combustible consumido - Nafta", Unidad.LTS),
  ELECTRICIDAD("Electricidad", Unidad.KWH),
  CAMION_DE_CARGA("Camion de carga", null),
  UTILITARIO_LIVIANO("Utilitario liviano", null),
  DISTANCIA_MEDIA_RECORRIDA("Distancia media recorrida", Unidad.KM);

  private String text;
  private Unidad unidad;

  TipoDeConsumo(String text, Unidad unidad) {
    this.text = text;
    this.unidad = unidad;
  }

  public Unidad getUnidad() {
    return unidad;
  }

  @Override
  public String toString() {
    return text;
  }
}
