package domain;

import consumo.Periodo;
import excepciones.MiembroNoEsCompa;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import lombok.Getter;
import mediotransporte.Ubicacion;
import repository.AreaRepo;
import repository.MiembroRepo;
import trayecto.Trayecto;

@Entity
@Getter
public class Miembro {
  @Transient
  List<Integer> areaIDs;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false)
  private int id;
  private String nombreCompleto;
  @Transient
  private TipoDoc tipoDoc;
  private int numeroDoc;
  @ManyToMany
  private List<Trayecto> trayectos;
  @Transient
  private List<Ubicacion> ubicaciones;

  public Miembro(String nombre, String apellido, TipoDoc tipoDoc, int numeroDoc) {
    this.nombreCompleto = nombre + " " + apellido;
    this.tipoDoc = tipoDoc;
    this.numeroDoc = numeroDoc;
    this.trayectos = new ArrayList<>();
    this.ubicaciones = new ArrayList<>();
    this.areaIDs = new ArrayList<>();
    this.id = MiembroRepo.getInstance().generateID(9);
  }


  public Miembro() {

  }

  public void setId(int id) {
    this.id = id;
  }

  public List<Trayecto> getTrayectosOrg(Organizacion org) {
    return this.trayectos.stream().filter(i -> i.perteneceAOrg(org)).collect(Collectors.toList());
  }

  public void agregarUbicacion(Ubicacion ubicacion) {
    this.ubicaciones.add(ubicacion);
  }

  double calcularHuellaDeCarbonoOrg(Organizacion org) {
    Set<Trayecto> trayectosFiltradosPorOrg = new HashSet<>(this.getTrayectosOrg(org));
    return this.calcularHuellaDeCarbono(trayectosFiltradosPorOrg);
  }

  double impactoHcSobreOrg(Organizacion org, Periodo periodoImputacion) {
    return org.calcularHuellaDeCarbono(periodoImputacion)
        / this.calcularHuellaDeCarbonoOrg(org) * 100;
  }

  double impactoHcSobreOrg(Area area, Periodo periodoImputacion) {
    // TODO chequear que pertenezca al area
    return area.calcularHuellaDeCarbono()
        / this.calcularHuellaDeCarbonoOrg(area.getOrganizacion()) * 100;
  }

  double calcularHuellaDeCarbono(Set<Trayecto> trayectos) {
    return trayectos.stream().mapToDouble(Trayecto::getHc).sum();
  }

  double impactoPersonalEnOrganizacion(Organizacion organizacion, Periodo periodoImputacion) {
    return this.calcularHuellaDeCarbonoOrg(organizacion)
        * 100 / organizacion.calcularHuellaDeCarbono(periodoImputacion);
  }

  public void vincularConArea(Area area) {
    area.pedirVinculacion(this);
  }

  public void agregarTrayecto(Trayecto trayecto) {
    this.trayectos.add(trayecto);
  }

  public void agregarTrayectoCompartido(Trayecto trayecto, List<Miembro> miembros) {
    miembros.add(this);
    validarPertenenciaAOrg(this, miembros);
    miembros.forEach(miembro -> miembro.agregarTrayecto(trayecto));
  }

  /* TODO
  No se si esta bien o no
  se supone que tiene que chequear que el miembro pertenezca a las mismas orgs que los
  que están en la lista
  */

  public void validarPertenenciaAOrg(Miembro miembro, List<Miembro> miembros) {
    Set<Integer> aux2 = miembros.stream()
        .map(m -> m.getAreaIDs())
        .flatMap(Collection::stream)
        .map(i -> AreaRepo.getInstance().findByID(i))
        .flatMap(Collection::stream)
        .map(a -> a.getOrganizacionID())
        .collect(Collectors.toSet());

    boolean aux = !new HashSet<>(miembro.getAreaIDs().stream()
        .map(i -> AreaRepo.getInstance().findByID(i))
        .flatMap(Collection::stream)
        .map(a -> a.getOrganizacionID())
        .collect(Collectors.toList()))
        .containsAll(aux2);

    if (aux) {
      throw new MiembroNoEsCompa("MiembroNoEsCompa");
    }
  }

  public void agregarAreaID(int id) {
    this.areaIDs.add(id);
  }

}
