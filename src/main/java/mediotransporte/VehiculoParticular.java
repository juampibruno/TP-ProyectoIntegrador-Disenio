package mediotransporte;

import consumo.TipoDeConsumo;
import javax.persistence.Entity;
import javax.persistence.Enumerated;

@Entity
public class VehiculoParticular extends MedioDeTransporte {
  @Enumerated
  private TipoCombustible tipoCombustible;

  public VehiculoParticular(TipoCombustible tipoCombustible) {
    this.tipoCombustible = tipoCombustible;
  }

  public VehiculoParticular() {

  }

  // TODO PORQUE ESTÁ ESTO???
  public TipoDeConsumo getTipoDeConsumo() {
    return null;
  }
}
