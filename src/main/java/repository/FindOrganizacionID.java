package repository;

import domain.Area;
import domain.Organizacion;

public class FindOrganizacionID implements Specification<Organizacion> {
  private int id;

  public FindOrganizacionID(int id) {
    this.id = id;
  }

  public boolean exists(Organizacion area) {
    return area.getId() == this.id;
  }

}
