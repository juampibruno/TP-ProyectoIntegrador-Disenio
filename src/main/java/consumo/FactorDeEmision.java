package consumo;

import excepciones.InvalidUnitsException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import repository.FErepo;

@Entity
public class FactorDeEmision {
  @Enumerated
  TipoDeConsumo tipoDeConsumo;
  Double valor;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false)
  private Long id;

  public FactorDeEmision(TipoDeConsumo tipoConsumo, Unidad unidad, Double valor) {
    validarCargadeFE(tipoConsumo, unidad);
    this.tipoDeConsumo = tipoConsumo;
    this.valor = valor;
    cargarFE();
  }

  public FactorDeEmision() {

  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  private void validarCargadeFE(TipoDeConsumo tipoConsumo, Unidad unidad) {
    if (tipoConsumo.getUnidad() != unidad) {
      throw new InvalidUnitsException("Invalid Units");
    }
  }

  public void cargarFE() {
    FErepo.getInstance().add(this);
  }

  public Double getValor() {
    return this.valor;
  }

  public TipoDeConsumo getTipoDeConsumo() {
    return this.tipoDeConsumo;
  }
}
