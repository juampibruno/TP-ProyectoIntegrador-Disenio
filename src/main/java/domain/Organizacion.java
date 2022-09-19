package domain;

import consumo.Medicion;
import consumo.Periodo;
import excepciones.NotPendingApprovalException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import mediotransporte.Ubicacion;
import repository.AreaRepo;
import repository.MiembroRepo;
import repository.OrgRepo;
import repository.PersistentEntity;
import trayecto.Trayecto;

@Entity
@Getter
@Table
public class Organizacion extends PersistentEntity {

 @Column
  private String razonSocial;
  @Enumerated(EnumType.STRING)
  private TipoOrganizacion tipoOrganizacion;
  @ManyToOne
  private Ubicacion ubicacion;
  @Enumerated(EnumType.STRING)
  private Clasificacion clasificacion;

  @OneToMany
  private List<Medicion> mediciones;
  @Setter
  private String email;
  @Setter
  private String numTel;

  public Organizacion(String razSoc, TipoOrganizacion tipoOrg,
                      Ubicacion ubi, Clasificacion clas, String email, String numTel) {
    this.razonSocial = razSoc;
    this.tipoOrganizacion = tipoOrg;
    this.ubicacion = ubi;
    this.clasificacion = clas;
    this.id = OrgRepo.getInstance().generateID(9);
    this.mediciones = new ArrayList<>();
  }

  public Organizacion() {

  }

  public void setId(int id) {
    this.id = id;
  }

  public void cargarDatosDeActividades(String path) throws FileNotFoundException {
    File archivo = new File(path);
    try (Scanner scanner = new Scanner(archivo, "utf-8")) {
      while (scanner.hasNextLine()) {
        Medicion medicion = new Medicion(scanner.nextLine());
        mediciones.add(medicion);
      }
    }
  }

  public void aceptarVinculacion(Area area, Miembro miembro) {
    if (area.getPendientesDeVinculacion().contains(miembro)) {
      area.aceptarMiembro(miembro);
    } else {
      throw new NotPendingApprovalException("Error - Vinculación No Solicitada");
    }
  }

  double calcularHuellaDeCarbono(Periodo periodoImputacion) {
    // Este calculo se hace aca para que no se repitan trayectos compartidos
    HashSet<Trayecto> trayectosUnicos = new HashSet<>();
    MiembroRepo.getInstance()
        .findByOrgID(this.id)
        .forEach(m -> trayectosUnicos.addAll(m.getTrayectosOrg(this)));
    double hcTrayectos = trayectosUnicos.stream().mapToDouble(Trayecto::getHc).sum();

    return Medicion.getHc(mediciones, periodoImputacion) + hcTrayectos;
  }

  public List<Area> getAreas() {
    return AreaRepo.getInstance().findByOrgID(id);
  }

}

