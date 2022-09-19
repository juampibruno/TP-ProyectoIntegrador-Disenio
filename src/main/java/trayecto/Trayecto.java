package trayecto;

import domain.Organizacion;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import lombok.Getter;
import org.uqbarproject.jpa.java8.extras.convert.LocalDateConverter;
import repository.FErepo;
import repository.PersistentEntity;

@Entity
@Getter
public class Trayecto extends PersistentEntity {

  @Convert(converter = LocalDateConverter.class)
  private LocalDate fecha;
  @Transient
  private ListaConDistancias<Tramo> tramos;

  public Trayecto(LocalDate fecha, List<Tramo> tramos) {
    this.fecha = fecha;
    this.tramos = new ListaConDistancias<>();
    this.tramos.addAll(tramos);
  }

  public Trayecto() {

  }

  public double calcularDistanciaTotal() {
    return tramos.stream().mapToDouble(Tramo::calcularDistancia).sum();
  }

  public double calcularDistanciasIntermedias(Tramo origen, Tramo destino) {
    return tramos.calcularDistanciaEntre(origen, destino, Tramo::calcularDistancia);
  }

  public boolean perteneceAOrg(Organizacion org) {
    return this.tramos.get(tramos.size() - 1).getDestino().equals(org.getUbicacion());
  }

  public double getHc() {
    return this.tramos.stream()
        .mapToDouble(tramo ->
            tramo.calcularDistancia() * FErepo.getInstance()
                .findByTipoDeConsumo(tramo.getMedioDeTransporte().getTipoDeConsumo())
        ).sum();
  }
}
