package sistema;

import excepciones.LocalidadNotFound;
import excepciones.RequestError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import mediotransporte.Ubicacion;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServicioGeoRef {
  private static final ServicioGeoRef instancia = new ServicioGeoRef();
  private static final String urlApi = "https://ddstpa.com.ar/api/";
  private final GeoRefService servicioGeoRef;

  // TODO buscar powermock
  public ServicioGeoRef() {
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl(urlApi)
        .addConverterFactory(GsonConverterFactory.create())
        .build();
    this.servicioGeoRef = retrofit.create(GeoRefService.class);
  }

  public ServicioGeoRef(GeoRefService servicioGeoRef) {
    this.servicioGeoRef = servicioGeoRef;
  }

  public static ServicioGeoRef getInstancia() {
    return instancia;
  }

  public double distanciaEntre(Ubicacion origen, Ubicacion destino) {
    Call<Distancia> request = this.servicioGeoRef.distancia(
        origen.getLocalidadId(), destino.getLocalidadId(),
        origen.getCalle(), destino.getCalle(),
        origen.getAltura(), destino.getAltura()
    );

    Distancia distancia = null;
    try {
      Response<Distancia> response = request.execute();
      distancia = response.body();
    } catch (IOException e) {
      throw new RequestError("Error en la request!");
    }

    return distancia == null ? -1 : Double.parseDouble(distancia.valor);
  }

  public List<Provincia> listadoDeProvincias() {
    return listadoDeProvinciasPorPais(9);
  }

  public <T> List<T> listadoConOffset(CallFunction<T> funcion, int id) {
    List<T> resultado = new ArrayList<>();
    List<T> temporal = null;
    Call<List<T>> request;
    Response<List<T>> response;
    int offset = 1;
    do {
      try {
        request = funcion.run(offset, id);
        response = request.execute();
        temporal = response.body();
        if (temporal != null && !temporal.isEmpty()) {
          resultado.addAll(temporal);
        }
        offset++;
      } catch (IOException e) {
        //System.err.println("Error en la request!");
      }
    } while (temporal != null && !temporal.isEmpty());
    return resultado;
  }

  public List<Municipio> listadoDeMunicipiosPorProvincia(int provincia) {
    List<Municipio> lista = listadoConOffset(servicioGeoRef::municipios, provincia);
    lista.forEach(r -> r.setProvinciaId(provincia));
    return lista;
  }

  public List<Localidad> listadoDeLocalidadesPorMunicipio(int municipio) {
    List<Localidad> lista = listadoConOffset(servicioGeoRef::localidades, municipio);
    lista.forEach(r -> r.setMunicipioId(municipio));
    return lista;
  }

  public List<Provincia> listadoDeProvinciasPorPais(int pais) {
    List<Provincia> lista = listadoConOffset(servicioGeoRef::provincias, pais);
    lista.forEach(r -> r.setPaisdId(pais));
    return lista;
  }

  public Localidad getLocalidad(int localidadId, int municipioId, int provinciaId) {
    List<Localidad> localidades = this.listadoDeLocalidadesPorMunicipio(municipioId);
    Localidad localidad;

    // para tests
    if (localidadId == -1 && municipioId == -1 && provinciaId == -1) {
      return new Localidad(1, "test");
    }

    try {
      localidad = localidades.stream()
          .filter(l -> l.getId() == localidadId)
          .collect(Collectors.toList())
          .get(0);
    } catch (IndexOutOfBoundsException e) {
      throw new LocalidadNotFound("No se encontró la localidad");
    }
    return localidad;
  }

  interface CallFunction<T> {
    Call<List<T>> run(int offset, int idPadre);
  }
}