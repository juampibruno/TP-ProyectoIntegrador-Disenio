package repository;

import domain.Miembro;

public class FindSuAreaID implements Specification<Miembro> {
  private int id;

  public FindSuAreaID(int areaID) {
    this.id = areaID;
  }

  public boolean exists(Miembro miembro) {
    return miembro.getAreaIDs().contains(this.id);
  }

}
