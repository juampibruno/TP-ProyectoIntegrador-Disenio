package repository;

import domain.Area;

public class FindAreaID implements Specification<Area> {
  private int id;

  public FindAreaID(int id) {
    this.id = id;
  }

  public boolean exists(Area area) {
    return area.getId() == this.id;
  }

}
