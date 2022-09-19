package repository;

import domain.Area;

public class FindSuOrganizacionID implements Specification<Area> {
  private int id;

  public FindSuOrganizacionID(int orgID) {
    this.id = orgID;
  }

  public boolean exists(Area area) {
    return area.getOrganizacionID() == this.id;
  }

}
