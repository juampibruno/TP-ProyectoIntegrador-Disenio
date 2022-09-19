package repository;

import domain.Organizacion;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrgRepo extends GenericRepo<Organizacion> {
  static OrgRepo instancia = new OrgRepo();

  public OrgRepo() {
    storage = new ArrayList<>();
  }

  public static OrgRepo getInstance() {
    return instancia;
  }

  public void removeID(int id) {
    Organizacion org = storage.stream().filter(unaOrg -> unaOrg.getId() == id)
        .collect(Collectors.toList()).get(0);
    storage.remove(org);
  }

  public List<Organizacion> findByID(int id) {
    return this.findBy(new FindOrganizacionID(id));
  }

}
