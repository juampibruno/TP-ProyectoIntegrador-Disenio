package mediotransporte;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import lombok.Getter;
import sistema.Localidad;
import sistema.ServicioGeoRef;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //TODO tipo de herencia
@DiscriminatorColumn()
@Getter
public class Ubicacion {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false)
  private int id;

  private String calle;
  private Integer altura;

  private int localidadId;
  private int municipioId;
  private int provinciaId;

  public Ubicacion() {

  }

  public Ubicacion(String calle, Integer altura,
                   int localidadId, int municipioId, int provinviaId) {
    this.calle = calle;
    this.altura = altura;
    this.municipioId = municipioId;
    this.provinciaId = provinviaId;
    //    this.localidadId = ServicioGeoRef.getInstancia()
    //.getLocalidad(localidadId, municipioId, provinviaId);
    this.localidadId = localidadId;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return calle + " " + altura.toString();
  }

}
