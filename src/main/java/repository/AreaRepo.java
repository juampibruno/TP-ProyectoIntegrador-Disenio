package repository;

import domain.Area;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AreaRepo extends GenericRepo<Area> {
  static AreaRepo instancia = new AreaRepo();

  public AreaRepo() {
    storage = new ArrayList<>();
  }

  public static AreaRepo getInstance() {
    return instancia;
  }

  public void removeID(int id) {
    Area area = this.storage.stream().filter(unArea -> unArea.getId() == id)
        .collect(Collectors.toList()).get(0);
    this.storage.remove(area);
  }

  public List<Area> findByID(int id) {
    return this.findBy(new FindAreaID(id));
  }

  public List<Area> findByOrgID(int id) {
    return this.findBy(new FindSuOrganizacionID(id));
  }
}
