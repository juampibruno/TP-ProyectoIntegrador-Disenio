package repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import mediotransporte.Linea;

public class LineaRepo extends GenericRepo<Linea> {
  static LineaRepo instancia = new LineaRepo();

  public LineaRepo() {
    storage = new ArrayList<>();
  }

  public static LineaRepo getInstance() {
    return instancia;
  }

  public void removeID(int id) {
    Linea linea = this.storage.stream().filter(l -> l.getId() == id)
        .collect(Collectors.toList()).get(0);
    this.storage.remove(linea);
  }

  public List<Linea> findByID(int id) {
    return this.findBy(new FindLineaID(id));
  }


}