package repository;

import domain.Sector;

public class FindSectorID implements Specification<Sector> {
  private int id;

  public FindSectorID(int id) {
    this.id = id;
  }

  public boolean exists(Sector sector) {
    return sector.getId() == this.id;
  }

}
