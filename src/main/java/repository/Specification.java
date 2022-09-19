package repository;

public interface Specification<T> {
  boolean exists(T t);
}
