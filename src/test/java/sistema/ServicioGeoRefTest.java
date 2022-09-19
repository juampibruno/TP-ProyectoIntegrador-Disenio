package sistema;

import builder.Builders;
import excepciones.LocalidadNotFound;
import mediotransporte.TipoCombustible;
import mediotransporte.Ubicacion;
import mediotransporte.VehiculoParticular;
import okhttp3.Request;
import okio.Timeout;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ServicioGeoRefTest {
  ServicioGeoRef servicioGeoRef;
  GeoRefService geoRefService;


  @BeforeEach
  void init() {
    geoRefService = mock(GeoRefService.class);
    servicioGeoRef = new ServicioGeoRef(geoRefService);
  }

  @Test
  void getLocalidadException() {
    Assertions.assertThrows(LocalidadNotFound.class, () -> ServicioGeoRef.getInstancia().getLocalidad(19000,0, 190000000));
  }

  @Test
  void asignacionDeIDPaisAProvincia() {
    Assertions.assertEquals(9, ServicioGeoRef.getInstancia().listadoDeProvinciasPorPais(9).get(0).getPaisId());
  }
  @Test
  void asignacionDeIDMunicipioALocalidad() {
    Assertions.assertEquals(9, ServicioGeoRef.getInstancia().listadoDeLocalidadesPorMunicipio(9).get(0).getMunicipioId());
  }

  @Test
  void distancia() {

    when(geoRefService.distancia(anyInt(), anyInt(), anyString(), anyString(), anyInt(), anyInt()))
        .thenReturn(new Call<Distancia>() {
          @Override
          public Response<Distancia> execute() throws IOException {
            return null;
          }

          @Override
          public void enqueue(Callback<Distancia> callback) {

          }

          @Override
          public boolean isExecuted() {
            return false;
          }

          @Override
          public void cancel() {

          }

          @Override
          public boolean isCanceled() {
            return false;
          }

          @Override
          public Call<Distancia> clone() {
            return null;
          }

          @Override
          public Request request() {
            return null;
          }

          @Override
          public Timeout timeout() {
            return null;
          }
        });

    geoRefService.distancia(1, 1, "a", "a", 1, 1);

    verify(geoRefService).distancia(anyInt(), anyInt(), anyString(), anyString(), anyInt(), anyInt());
  }

  @Test
  void distanciaCreation() {
    Assertions.assertEquals("1", new Distancia("1").getValor());
  }

  @Test
  void asignacionDeIDPaisAProvinciaError() {
    Assertions.assertThrows(IndexOutOfBoundsException.class,
        () -> ServicioGeoRef.getInstancia().listadoDeProvinciasPorPais(1900).get(0));
  }

  @Test
  void asignacionDeIDMunicipioALocalidadError() {
    Assertions.assertThrows(IndexOutOfBoundsException.class,
        () -> ServicioGeoRef.getInstancia().listadoDeLocalidadesPorMunicipio(19000).get(0));
  }

  /*@Test
  void distanciaEntreDosUbicaciones() {
    Ubicacion casa = casaMiembro();
    Ubicacion organizacion = organizacion();

    when(geoRefService.distancia(any(), any(), any(), any(), any(), any()));

    when(servicioGeoRef.distanciaEntre(any(), any())).thenReturn(1.0);


    Assertions.assertEquals(1, servicioGeoRef.distanciaEntre(casa, organizacion));
  }*/

}


