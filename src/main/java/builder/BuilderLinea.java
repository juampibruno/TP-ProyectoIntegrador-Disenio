package builder;

import excepciones.NullAtributeException;
import java.util.Arrays;
import java.util.List;
import mediotransporte.Linea;
import mediotransporte.Parada;
import mediotransporte.TipoLinea;



public class BuilderLinea {
  private String nombre;
  private TipoLinea tipoLinea;
  private List<Parada> paradas;

  public Linea build() {
    if ((Arrays.asList(nombre, tipoLinea, paradas)).contains(null)) {
      throw new NullAtributeException("Atributos null hallado - Complete todos los campos");
    }
    return new Linea(nombre, tipoLinea, paradas);
  }

  public String getNombre() {
    return nombre;
  }

  public TipoLinea getTipoLinea() {
    return tipoLinea;
  }

  public List<Parada> getParadas() {
    return paradas;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public void setTipoLinea(TipoLinea tipoLinea) {
    this.tipoLinea = tipoLinea;
  }

  public void setParadas(List<Parada> paradas) {
    this.paradas = paradas;
  }
}
