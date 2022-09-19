package repository;

import java.util.ArrayList;
import sistema.Municipio;

public class SectorMunicipiosRepo extends SectorRepo<Municipio> {
  static SectorMunicipiosRepo instancia = new SectorMunicipiosRepo();

  public static SectorMunicipiosRepo getInstance() {
    return instancia;
  }

  public SectorMunicipiosRepo() {
    storage = new ArrayList<>();
  }
}
