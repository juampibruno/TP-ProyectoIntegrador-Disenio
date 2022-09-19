package trayecto;

import builder.Builders;
import excepciones.InvalidStopNotRegistred;
import mediotransporte.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sistema.ServicioGeoRef;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;

public class TramoTrayectoTest {

  List<Tramo> listaTramos;
  ServicioGeoRef servicioGeoRef;
  Tramo autoElectrico;
  Tramo viajeSarmiento;

  @BeforeEach
  void init() {
    listaTramos = new ArrayList<>();
    servicioGeoRef = mock(ServicioGeoRef.class);
    viajeSarmiento = Builders.viajeSarmiento();
    autoElectrico = Builders.autoElectrico();
  }

  @Test
  void seAgreganCorrectamenteLosTramosAlTrayecto(){
    Tramo autoAGas = Builders.autoAGas(Builders.casaMiembro(), Builders.organizacion());
    Tramo viaje169 = Builders.viaje169(Builders.organizacion(), Builders.casaMiembro());
    listaTramos.add(autoAGas);
    listaTramos.add(viaje169);
    Trayecto trayectoCasaOrganizacion = Builders.trayectoCasaOrganizacion(listaTramos);
    Assertions.assertArrayEquals(listaTramos.toArray(),trayectoCasaOrganizacion.getTramos().toArray());
  }

  @Test
  void seAgreganCorrectamenteLosTramosAlTrayecto1(){
    listaTramos.add(Builders.viajeSarmiento());
    listaTramos.add(Builders.autoElectrico());
    Trayecto trayectoSanatorioColegio = Builders.trayectoSanatorioColegio(listaTramos);
    Assertions.assertArrayEquals(listaTramos.toArray(), trayectoSanatorioColegio.getTramos().toArray());
  }

  @Test
  void origenCorrecto(){
    Assertions.assertEquals(Builders.colegioMarianista().toString(), Builders.autoElectrico().getOrigen().toString());
  }

  @Test
  void destinoCorrecto(){
    Assertions.assertEquals(Builders.colegioMarianista().toString(), Builders.viajeSarmiento().getDestino().toString());
  }

  @Test
  void distanciaTotalTest(){
    Parada parada1 = new Parada("Parada 1", "Sarasa", 123, -1, -1, -1, 1);
    Parada parada2 = new Parada("Parada 2", "Sarasa", 666, -1, -1, -1, 2);
    List<Parada> paradas = new ArrayList<>();
    paradas.add(parada1);
    paradas.add(parada2);

    Linea linea = new Linea("Papichulo", TipoLinea.COLECTIVO, paradas);
    Tramo tramo1 = new Tramo(parada1, parada2, linea);

    listaTramos.add(tramo1);
    Trayecto trayectoTest = new Trayecto(LocalDate.now(), listaTramos);
    Assertions.assertEquals(1,trayectoTest.calcularDistanciaTotal());
  }

  @Test
  void tramoInexistente(){
    listaTramos.add(autoElectrico);
    listaTramos.add(viajeSarmiento);
    Tramo autoAGas = Builders.autoAGas(Builders.casaMiembro(), Builders.organizacion());
    Trayecto trayectoTest = new Trayecto(LocalDate.now(), listaTramos);
    Assertions.assertThrows(InvalidStopNotRegistred.class,() -> trayectoTest.calcularDistanciasIntermedias(autoElectrico, autoAGas));
  }



  /*@Test
  void distanciaIntermediaTest(){
    Tramo autoAGas = autoAGas(casaMiembro(),organizacion());
    listaTramos.add(autoAGas);
    listaTramos.add(autoElectrico);
    listaTramos.add(viajeSarmiento);
    Trayecto trayectoTest = new Trayecto(LocalDate.now(), listaTramos);

    when(servicioGeoRef.distanciaEntre(any(), any())).thenReturn(1.0);
    Assertions.assertEquals(2,trayectoTest.calcularDistanciasIntermedias(autoElectrico, viajeSarmiento));
  }*/
}

