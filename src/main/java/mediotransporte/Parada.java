package mediotransporte;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Parada extends Ubicacion {
  private String nombre; //para cada línea
  private double distASiguiente;

  public Parada(String nom, String calle, Integer alt,
                int localId, int muniId, int provId, double dist) {
    super(calle, alt, localId, muniId, provId);
    this.nombre = nom;
    this.distASiguiente = dist;
  }

  public Parada() {

  }

  public double getDistanciaASiguiente() {
    return this.distASiguiente;
  }

  public String getNombre() {
    return this.nombre;
  }

  @Override
  public String toString() {
    return this.nombre + " (" + super.toString() + ")";
  }
}
