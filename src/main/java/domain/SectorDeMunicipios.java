package domain;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import javax.persistence.Entity;
import repository.OrgRepo;
import repository.SectorMunicipiosRepo;
import sistema.Municipio;

@Entity
class SectorDeMunicipios extends Sector<Municipio> {
  public SectorDeMunicipios(List<Municipio> municipios, AgenteSectorial agente) {
    super(municipios, agente, SectorMunicipiosRepo.getInstance());
  }

  public SectorDeMunicipios() {

  }

  @Override
  protected Collection<Organizacion> getOrganizaciones() {
    return new HashSet<>(
        OrgRepo.getInstance().findBy(o ->
            partes.stream().mapToLong(Municipio::getIdMun)
                .anyMatch(id -> id == o.getUbicacion().getMunicipioId())
        )
    );
  }
}
