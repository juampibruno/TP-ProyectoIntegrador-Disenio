package sistema;

import lombok.Getter;

@Getter
public class Municipio {
  public int id;
  public String nombre;
  public int provincia;

  public Municipio(int id, String nombre) {
    this.id = id;
    this.nombre = nombre;
  }

  public int getIdMun() {
    return id;
  }

  public void setProvinciaId(int provincia) {
    this.id = provincia;
  }
}
