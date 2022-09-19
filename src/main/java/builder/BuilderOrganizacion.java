package builder;

import domain.Clasificacion;
import domain.Organizacion;
import domain.TipoOrganizacion;
import domain.TipoSociedad;
import excepciones.NullAtributeException;
import java.util.Arrays;
import mediotransporte.Ubicacion;


public class BuilderOrganizacion {
  private String razonSocial;
  private TipoOrganizacion tipoOrganizacion;
  private Ubicacion ubicacion;
  private Clasificacion clasificacion;
  private String email;
  private String numTel;

  public void setRazonSocial(String nombreRazon, TipoSociedad tipo) {
    this.razonSocial = nombreRazon + " " + tipo.toString();
  }

  public void setTipoOrganizacion(TipoOrganizacion tipoOrganizacion) {
    this.tipoOrganizacion = tipoOrganizacion;
  }

  public void setUbicacion(Ubicacion ubicacion) {
    this.ubicacion = ubicacion;
  }

  public void setClasificacion(Clasificacion clasificacion) {
    this.clasificacion = clasificacion;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setNumTel(String numTel) {
    this.numTel = numTel;
  }

  public String getRazonSocial() {
    return razonSocial;
  }

  public TipoOrganizacion getTipoOrganizacion() {
    return tipoOrganizacion;
  }

  public Ubicacion getUbicacion() {
    return ubicacion;
  }

  public Clasificacion getClasificacion() {
    return clasificacion;
  }

  public String getEmail() {
    return email;
  }

  public String getNumTel() {
    return numTel;
  }

  public Organizacion build() {
    if ((Arrays.asList(razonSocial, tipoOrganizacion, ubicacion, clasificacion, email, numTel))
        .contains(null)) {
      throw new NullAtributeException("Atributos null hallado - Complete todos los campos");
    }
    return new Organizacion(razonSocial, tipoOrganizacion, ubicacion, clasificacion, email, numTel);
  }
}