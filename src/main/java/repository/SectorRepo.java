package repository;

import domain.Sector;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public abstract class SectorRepo<T> extends GenericRepo<Sector<T>> {
  public void removeID(int id) {
    Sector<T> sector = this.storage.stream().filter(s -> s.getId() == id)
        .collect(Collectors.toList()).get(0);
    this.storage.remove(sector);
  }

  public boolean exists(List<T> partes) {
    // Wrappearlo en un hashset es una recomendacion de IntelliJ por performance
    return !this.findBy(sector ->
        new HashSet<>(sector.getPartes()).containsAll(partes)
            && sector.getPartes().size() == partes.size()
    ).isEmpty();
  }
}

