package domain;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import lombok.Getter;
import repository.PersistentEntity;
import sistema.Municipio;
import sistema.Provincia;

@Entity
@Getter
public class AgenteSectorial extends PersistentEntity {

  private String nombreCompleto;

  @Enumerated
  private TipoDoc tipoDoc;
  private int numeroDoc;
  private int sectorId;

  public AgenteSectorial(String nomComp, TipoDoc tipoDoc, int numDoc) {
    this.nombreCompleto = nomComp;
    this.tipoDoc = tipoDoc;
    this.numeroDoc = numDoc;
  }

  public AgenteSectorial() {

  }

  public Sector<Provincia> crearSectorDeProvincias(List<Provincia> provincias) {
    return new SectorDeProvincias(provincias, this);
  }

  public Sector<Municipio> crearSectorDeMunicipios(List<Municipio> municipios) {
    return new SectorDeMunicipios(municipios, this);
  }

  /*
  public Sector crearSector(int idMuni, int idProv, TipoSector tipo) {
    return new Sector(idProv, idMuni, this, tipo);
  }
  */

  public void setSectorId(int id) {
    this.sectorId = id;
  }
}
