package repository;

import domain.Miembro;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MiembroRepo extends GenericRepo<Miembro> {
  static MiembroRepo instancia = new MiembroRepo();

  public MiembroRepo() {
    storage = new ArrayList<>();
  }

  public static MiembroRepo getInstance() {
    return instancia;
  }

  public void removeID(int id) {
    Miembro miembro = this.storage.stream().filter(unMiembro -> unMiembro.getId() == id)
        .collect(Collectors.toList()).get(0);
    this.storage.remove(miembro);
  }

  public List<Miembro> findByID(int id) {
    return this.findBy(new FindMiembroID(id));
  }

  public List<Miembro> findByOrgID(int id) {
    return this.findBy(new FindPorOrganizacionID(id));
  }

  public List<Miembro> findByAreaID(int id) {
    return this.findBy(new FindSuAreaID(id));
  }
}