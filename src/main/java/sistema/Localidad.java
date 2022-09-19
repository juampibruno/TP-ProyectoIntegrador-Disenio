package sistema;

public class Localidad {
  public int id;
  public String nombre;
  public int municipioId;

  public Localidad(int id, String nombre) {
    this.id = id;
    this.nombre = nombre;
  }

  public int getId() {
    return id;
  }

  public String getNombre() {
    return nombre;
  }

  public int getMunicipioId() {
    return this.municipioId;
  }

  public void setMunicipioId(int municipio) {
    this.municipioId = municipio;
  }
}
