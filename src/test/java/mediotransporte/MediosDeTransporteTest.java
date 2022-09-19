package mediotransporte;

import builder.Builders;
import excepciones.InvalidIdenticalStop;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import repository.LineaRepo;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MediosDeTransporteTest {

  @Test
  void paradasAsignadasCorrectamenteALineaFerreaSarmiento() {
    List<Parada> paradas = Builders.paradasPrueba();
    Assertions.assertArrayEquals(paradas.toArray(), Builders.lineaFerreaSarmiento(paradas).getParadas().toArray());
  }

  @Test
  void paradasAsignadasCorrectamenteALineaColectivo() {
    List<Parada> paradas = Builders.paradasPrueba();
    Assertions.assertArrayEquals(paradas.toArray(), Builders.lineaColectivo169(paradas).getParadas().toArray());
  }

  @Test
  void lineaColectivoAgregadasCorrectamente() {
    LineaRepo lineaRepo = new LineaRepo();
    Linea lineaColectivo169 = Builders.lineaColectivo169(Builders.paradasPrueba());
    Linea lineaFerreaSarmiento = Builders.lineaFerreaSarmiento(Builders.paradasPrueba());
    lineaRepo.add(lineaColectivo169);
    lineaRepo.add(lineaFerreaSarmiento);
    List<Linea> lineas = new ArrayList<>();
    lineas.add(lineaColectivo169);
    lineas.add(lineaFerreaSarmiento);
    Assertions.assertArrayEquals(lineas.toArray(), lineaRepo.getAll().toArray());
  }

  private void distanciaEntreMismasParadas(){
    List<Parada> paradas = Builders.paradasPrueba();
    Parada primeraParada = paradas.get(0);
    Linea linea = Builders.lineaColectivo169(paradas);
    linea.calcularDistanciaEntre(primeraParada, primeraParada);
  }

  @Test
  void LaDistanciaEntreDosParadasCorrecta(){
    List<Parada> paradas = Builders.paradasPrueba();
    Parada primeraParada = paradas.get(0);
    Parada segundaParada = paradas.get(1);
    Linea linea = Builders.lineaColectivo169(paradas);
    assertEquals(linea.calcularDistanciaEntre(primeraParada, segundaParada), primeraParada.getDistanciaASiguiente());
  }


  @Test
  void DistanciaEntreParadasIguales(){
    InvalidIdenticalStop excepcion = assertThrows(InvalidIdenticalStop.class,this::distanciaEntreMismasParadas);
    assertEquals("Origen y destino son iguales", excepcion.getMessage());
  }

  @Test
  void TransporteNoEmisivoCreadoCorrectamente(){
    NoEmisivos transporteNoEmisivo = new NoEmisivos("Caminando");
    assertEquals("Caminando",transporteNoEmisivo.getNombreMedioDeTransporte());
  }

  @Test
  void ServicioContratadoCreadoCorrectamente(){
    ServicioContratado servicioContratado = new ServicioContratado("Uber");
    assertEquals("Uber",servicioContratado.getNombre());
  }
}


