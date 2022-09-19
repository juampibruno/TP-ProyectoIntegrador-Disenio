package repository;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class GenericRepo<T> {

  protected List<T> storage;

  public static int generateID(int n) {
    int m = (int) Math.pow(10, n - 1);
    return m + new Random().nextInt(9 * m);
  }

  public void add(T item) {
    storage.add(item);
  }

  public void addMultiple(List<T> item) {
    this.storage = Stream.concat(this.storage.stream(), item.stream())
        .collect(Collectors.toList());
  }

  public List<T> getAll() {
    return storage;
  }

  public List<T> findBy(Specification<T> especificacion) {
    return storage.stream().filter(especificacion::exists).collect(Collectors.toList());
  }

  public void remove(T item) {
    storage.remove(item);
  }

  public abstract void removeID(int id);

  public void removeAll() {
    storage.clear();
  }
}
