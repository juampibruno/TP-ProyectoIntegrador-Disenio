package repository;

import domain.Miembro;

public class FindMiembroID implements Specification<Miembro> {
  private int id;

  public FindMiembroID(int id) {
    this.id = id;
  }

  public boolean exists(Miembro miembro) {
    return miembro.getId() == this.id;
  }

}
