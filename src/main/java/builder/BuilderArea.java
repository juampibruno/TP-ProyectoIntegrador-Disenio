package builder;

import domain.Area;
import domain.Organizacion;
import excepciones.NullAtributeException;
import java.util.Arrays;
import lombok.Getter;

@Getter
public class BuilderArea {
  private String nombre;
  private Organizacion organizacion;

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public void setOrganizacion(Organizacion organizacion) {
    this.organizacion = organizacion;
  }

  public Area build() {
    if ((Arrays.asList(nombre, organizacion)).contains(null)) {
      throw new NullAtributeException("Atributos null hallado - Complete todos los campos");
    }
    return new Area(nombre, organizacion);
  }

}