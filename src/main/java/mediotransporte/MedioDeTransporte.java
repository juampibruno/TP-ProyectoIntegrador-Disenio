package mediotransporte;

import consumo.TipoDeConsumo;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import lombok.Getter;
import repository.PersistentEntity;
import sistema.ServicioGeoRef;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) //TODO tipo de herencia
//@DiscriminatorColumn()
public abstract class MedioDeTransporte extends PersistentEntity {

  public double calcularDistanciaEntre(Ubicacion origen, Ubicacion destino) {
    ServicioGeoRef servicio = ServicioGeoRef.getInstancia();
    return servicio.distanciaEntre(origen, destino);
  }

  public abstract TipoDeConsumo getTipoDeConsumo();

}