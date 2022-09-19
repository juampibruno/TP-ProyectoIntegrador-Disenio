package domain;

import consumo.Medicion;
import excepciones.AlreadyExists;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;
import repository.MiembroRepo;
import repository.SectorRepo;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //TODO tipo de herencia
@DiscriminatorColumn()
public abstract class Sector<T> {

  /*
  Lo pensamos como exclusivo un sector es de provincias o de municipio
  Cada provincia puede pertenecer a un solo sector y cada municipio lo mismo
  Una Organizacion podria pertencer a 2 sectores uno por el municipio y otro por la provincia
  El sector de municipio no conoce de que provincia es
  */

  @Transient // TODO: ManyToMany
  protected List<T> partes; // provincias o municipios
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false)
  private int id;
  @Transient
  private List<AgenteSectorial> agentes;
  @Transient
  private SectorRepo<T> repo;

  public Sector(List<T> partes, AgenteSectorial a, SectorRepo<T> repo) {
    this.partes = partes;
    this.repo = repo;
    if (this.exists()) {
      throw new AlreadyExists("Sector existente");
    }
    this.id = this.repo.generateID(9);
    this.agentes = new ArrayList<>();
    agregarAgente(a);
    this.repo.add(this);
  }

  public Sector() {

  }

  public int getId() {
    return id;
  }

  public void agregarAgente(AgenteSectorial a) {
    agentes.add(a);
  }

  public double getHC() {
    return getOrganizaciones().stream().mapToDouble(o ->
        Medicion.getHcTotal(o.getMediciones())
    ).sum();
  }

  public int cantidadMiembros() {
    return getOrganizaciones().stream().mapToInt(o ->
        MiembroRepo.getInstance().findByOrgID(o.getId()).size()
    ).sum();
  }

  public double getHCxMiembro() {
    return this.getHC() / this.cantidadMiembros();
  }

  public List<T> getPartes() {
    return this.partes;
  }

  private boolean exists() {
    return this.repo.exists(this.partes);
  }

  protected abstract Collection<Organizacion> getOrganizaciones();
}