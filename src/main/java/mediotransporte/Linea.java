package mediotransporte;

import consumo.TipoDeConsumo;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import lombok.Getter;
import repository.LineaRepo;
import trayecto.ListaConDistancias;

@Entity
@Getter
public class Linea extends MedioDeTransporte {

  private String nombre;
  @Enumerated(EnumType.STRING)
  private TipoLinea tipoLinea;

  @Transient // TODO
  private ListaConDistancias<Parada> paradas;

  public Linea(String nombre, TipoLinea tipoLinea, List<Parada> paradas) {
    this.nombre = nombre;
    this.tipoLinea = tipoLinea;
    this.paradas = new ListaConDistancias<>();
    this.paradas.addAll(paradas);
    this.id = LineaRepo.getInstance().generateID(9);
  }


  public Linea() {

  }

  public void addParada(Parada parada) {
    this.paradas.add(parada);
  }

  @Override
  public double calcularDistanciaEntre(Ubicacion origen, Ubicacion destino) {
    if (origen instanceof Parada && destino instanceof Parada) {
      return paradas.calcularDistanciaEntre(
          (Parada) origen,
          (Parada) destino,
          Parada::getDistanciaASiguiente);
    } else {
      throw new RuntimeException("Las ubicaciones tienen que ser paradas!");
    }
  }

  // Pa q no rompa en verify
  public TipoDeConsumo getTipoDeConsumo() {
    return null;
  }

}


