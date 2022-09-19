package mediotransporte;

import consumo.TipoDeConsumo;
import javax.persistence.Entity;
import repository.ServicioContratadoRepo;

@Entity
public class ServicioContratado extends MedioDeTransporte {
  private String nombreServicio;


  public ServicioContratado(String nombreServicio) {
    this.nombreServicio = nombreServicio;
    ServicioContratadoRepo.getInstance().add(this);
  }

  public ServicioContratado() {

  }

  public String getNombre() {
    return nombreServicio;
  }


  public TipoDeConsumo getTipoDeConsumo() {
    return null;
  }


}
