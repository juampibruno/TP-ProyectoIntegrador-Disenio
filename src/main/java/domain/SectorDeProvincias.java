package domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import javax.persistence.Entity;
import repository.OrgRepo;
import repository.SectorProvinciasRepo;
import sistema.Provincia;

@Entity
class SectorDeProvincias extends Sector<Provincia> {
  public SectorDeProvincias(List<Provincia> provincias, AgenteSectorial agente) {
    super(provincias, agente, SectorProvinciasRepo.getInstance());
  }

  public SectorDeProvincias() {

  }

  @Override
  protected Collection<Organizacion> getOrganizaciones() {
    return new HashSet<>(
        OrgRepo.getInstance().findBy(o ->
            partes.stream().mapToLong(Provincia::getIdProv)
                .anyMatch(id -> id == o.getUbicacion().getProvinciaId())
        )
    );
  }
}
