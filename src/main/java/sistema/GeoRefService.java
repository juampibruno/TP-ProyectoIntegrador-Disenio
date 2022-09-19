package sistema;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface GeoRefService {
  String TOKEN = "DTvLE9+idWDlOotFPYHj8643xJiBOi0knRNcHWTuNjs=";
  String HEADERS = "Authorization: Bearer " + TOKEN;

  @Headers(HEADERS)
  @GET("provincias")
  Call<List<Provincia>> provincias(
      @Query("offset") int offset,
      @Query("paisId") int pais);

  @Headers(HEADERS)
  @GET("municipios")
  Call<List<Municipio>> municipios(
      @Query("offset") int offset,
      @Query("provinciaId") int provincia);

  @Headers(HEADERS)
  @GET("localidades")
  Call<List<Localidad>> localidades(
      @Query("offset") int offset,
      @Query("municipioId") int municipio);

  @Headers(HEADERS)
  @GET("distancia")
  Call<Distancia> distancia(
      @Query("localidadOrigenId") int localidadOrigenId,
      @Query("localidadDestinoId") int localidadDestinoId,
      @Query("calleOrigen") String calleOrigen, @Query("calleDestino") String calleDestino,
      @Query("alturaOrigen") int alturaOrigen, @Query("alturaDestino") int alturaDestino
  );
}

