package sistema;

import lombok.Getter;

@Getter
public class Provincia {
  public int id;
  public String nombre;
  private int paisId;

  public Provincia(int id, String nombre) {
    this.id = id;
    this.nombre = nombre;
  }

  void setPaisdId(int id) {
    this.paisId = id;
  }

  public int getIdProv() {
    return id;
  }
}
