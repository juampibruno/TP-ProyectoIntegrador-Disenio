package domain;

import excepciones.NotPendingApprovalException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import lombok.Getter;
import repository.AreaRepo;
import repository.MiembroRepo;
import repository.OrgRepo;
import repository.PersistentEntity;

@Entity
@Getter
public class Area extends PersistentEntity {

  private String nombre;
  private int organizacionID;
  @Transient //TODO Transient
  private Set<Miembro> pendientesDeVinculacion;

  public Area(String nombre, Organizacion organizacion) {
    this.nombre = nombre;
    this.organizacionID = organizacion.getId();
    this.pendientesDeVinculacion = new HashSet<>();
    this.id = AreaRepo.getInstance().generateID(9);
  }

  public Area() {

  }

  public void aceptarMiembro(Miembro miembro) {
    if (!pendientesDeVinculacion.contains(miembro)) {
      throw new NotPendingApprovalException("Error - Vinculación No Solicitada");
    }
    this.pendientesDeVinculacion.remove(miembro);
    miembro.agregarAreaID(this.id);
    MiembroRepo.getInstance().add(miembro);
  }

  double calcularHuellaDeCarbono() {
    List<Miembro> miembro = MiembroRepo.getInstance().findByAreaID(this.id);
    return miembro.stream()
        .mapToDouble(t ->
            t.calcularHuellaDeCarbonoOrg(OrgRepo.getInstance()
                .findByID(this.organizacionID)
                .get(0)))
        .sum();
  }

  public Organizacion getOrganizacion() {
    return OrgRepo.getInstance().findByID(this.organizacionID).get(0);
  }

  public List<Miembro> getMiembros() {
    return MiembroRepo.getInstance().findByAreaID(id);
  }

  public void setId(int id) {
    this.id = id;
  }

  public void pedirVinculacion(Miembro miembro) {
    this.pendientesDeVinculacion.add(miembro);
  }

}
