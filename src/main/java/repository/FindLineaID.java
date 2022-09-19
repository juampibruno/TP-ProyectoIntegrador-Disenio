package repository;

import mediotransporte.Linea;

public class FindLineaID implements Specification<Linea> {
  private int id;

  public FindLineaID(int id) {
    this.id = id;
  }

  public boolean exists(Linea area) {
    return area.getId() == this.id;
  }

}
