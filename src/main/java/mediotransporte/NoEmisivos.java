package mediotransporte;

import consumo.TipoDeConsumo;

public class NoEmisivos extends MedioDeTransporte {
  private String nombreMedioDeTransporte;

  public NoEmisivos(String nombreMedioDeTransporte) {
    this.nombreMedioDeTransporte = nombreMedioDeTransporte;
  }

  public String getNombreMedioDeTransporte() {
    return nombreMedioDeTransporte;
  }

  // Pa q no rompa en verify
  public TipoDeConsumo getTipoDeConsumo() {
    return null;
  }
}
