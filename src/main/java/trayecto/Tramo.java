package trayecto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import lombok.Getter;
import mediotransporte.MedioDeTransporte;
import mediotransporte.Ubicacion;

@Entity
@Getter
public class Tramo {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false)
  private Long id;

  @Transient // TODO
  private Ubicacion origen;
  @Transient // TODO
  private Ubicacion destino;
  @ManyToOne
  private MedioDeTransporte medioDeTransporte;

  public Tramo(Ubicacion origen, Ubicacion destino, MedioDeTransporte medioDeTransporte) {
    this.origen = origen;
    this.destino = destino;
    this.medioDeTransporte = medioDeTransporte;
  }

  public Tramo() {

  }

  public void setOrigen(Ubicacion origen) {
    this.origen = origen;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public double calcularDistancia() {
    return this.medioDeTransporte.calcularDistanciaEntre(this.origen, this.destino);
  }
}
/*

Ejemplos:

Tramo Viaje107 = new Tramo(
linea107.paradas[3]
linea107.paradas[300],
linea107))

Tramo AutoAGas = new Tramo(
miembro.getUbicacion(0),
miembro.getOrganizacion().getUbicacion(),
new VehiculoParticular(TipoCombustible.GAS))

*/