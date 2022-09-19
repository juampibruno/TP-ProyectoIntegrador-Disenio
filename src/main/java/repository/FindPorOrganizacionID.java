package repository;

import domain.Miembro;

public class FindPorOrganizacionID implements Specification<Miembro> {
  private int id;

  public FindPorOrganizacionID(int orgID) {
    this.id = orgID;
  }

  public boolean exists(Miembro miembro) {
    return AreaRepo.getInstance().findByOrgID(this.id)
        .stream().anyMatch(a -> a.getMiembros().contains(miembro));
  }

}
