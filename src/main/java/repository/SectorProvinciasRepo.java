package repository;

import java.util.ArrayList;
import sistema.Provincia;

public class SectorProvinciasRepo extends SectorRepo<Provincia> {
  static SectorProvinciasRepo instancia = new SectorProvinciasRepo();

  public static SectorProvinciasRepo getInstance() {
    return instancia;
  }

  public SectorProvinciasRepo() {
    storage = new ArrayList<>();
  }
}
