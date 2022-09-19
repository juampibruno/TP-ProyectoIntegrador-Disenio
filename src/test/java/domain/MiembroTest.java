package domain;

import builder.Builders;
import builder.BuilderLinea;
import consumo.TipoDeConsumo;
import consumo.Unidad;
import excepciones.InvalidUnitsException;
import excepciones.MiembroNoEsCompa;
import excepciones.NotPendingApprovalException;
import mediotransporte.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import repository.MiembroRepo;
import trayecto.Tramo;
import trayecto.Trayecto;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class MiembroTest {

  MiembroRepo miembroRepo = new MiembroRepo();

  Organizacion organizacion = Builders.organizacionBuilderCompleto();

  private Ubicacion casaMiembro(){
    return new Ubicacion("Casa miembro" , 123, -1, -1, -1);
  }

  private Ubicacion organizacion(){
    return new Ubicacion("organizacion" , 456, -1, -1, -1);
  }

  @Test
  void solicitudDeVinculacionConAreaExitosa() {
    Area areaAVincular = Builders.areaBuilderCompleto(organizacion);
    Miembro miembroQueSeQuiereVincular = Builders.primerMiembro();
    miembroQueSeQuiereVincular.vincularConArea(areaAVincular);
    assertTrue(areaAVincular.getPendientesDeVinculacion().contains(miembroQueSeQuiereVincular));
  }

  @Test
  void vinculacionAreaExitosa() {
    Area areaAVincular = Builders.areaBuilderCompleto(organizacion);
    Miembro miembroQueSeQuiereVincular = Builders.primerMiembro();
    miembroQueSeQuiereVincular.vincularConArea(areaAVincular);
    organizacion.aceptarVinculacion(areaAVincular, miembroQueSeQuiereVincular);
    assertTrue(areaAVincular.getMiembros().contains(miembroQueSeQuiereVincular));
  }

  private void vinculacionFallida() {
    Area areaAVincular = Builders.areaBuilderCompleto(organizacion);
    Miembro miembroQueSeQuiereVincular = Builders.primerMiembro();
    organizacion.aceptarVinculacion(areaAVincular, miembroQueSeQuiereVincular);
  }

  @Test
  void vinculacionNoPedida() {
    NotPendingApprovalException excepcion = assertThrows(NotPendingApprovalException.class, this::vinculacionFallida);
    assertEquals("Error - Vinculación No Solicitada", excepcion.getMessage());
  }

  /*@Test // TODO chequear para excepción MiembroNoEsCompa
  void miembrosNoCompas() {
    Area a = Builders.builderCompletoArea("a");
    Area b = Builders.builderCompletoArea("b");

    Miembro aa = Builders.unMiembro();
    Miembro bb = Builders.unMiembro();

    aa.vincularConArea(a);
    bb.vincularConArea(b);

    a.aceptarMiembro(aa);
    b.aceptarMiembro(bb);

    List<Tramo> aaa = new ArrayList<>();
    aaa.add(Builders.viajeSarmiento());

    Trayecto aaaa = Builders.trayectoCasaOrganizacion(aaa);

    List<Miembro> aaaaa = new ArrayList<>();
    aaaaa.add(aa);
    aaaaa.add(bb);

    Assertions.assertThrows(MiembroNoEsCompa.class, () -> aa.agregarTrayectoCompartido(aaaa, aaaaa));
  }*/

  @Test
  void agregaUbicaciones(){
    Miembro unMiembro = Builders.primerMiembro();
    Ubicacion unaUbicacion = organizacion();
    unMiembro.agregarUbicacion(casaMiembro());
    unMiembro.agregarUbicacion(unaUbicacion);
    assertTrue(unMiembro.getUbicaciones().contains(unaUbicacion));
  }

  private Trayecto trayectoCasaOrganizacion(List<Tramo> unosTramos){
    return new Trayecto(LocalDate.now(),unosTramos );
  }

  private Tramo autoAGas(Ubicacion puntoPartida, Ubicacion puntoFinal){
    return new Tramo(puntoPartida, puntoFinal, new VehiculoParticular(TipoCombustible.GNC));
  }
  private Tramo viaje169(Ubicacion puntoPartida, Ubicacion puntoFinal){
    return new Tramo(puntoPartida,puntoFinal,lineaColectivo169(paradasPrueba()));
  }

  public List<Parada> paradasPrueba() {
    List<Parada> unasParadasDePrueba = new ArrayList<>();
    Parada parada1 = new Parada("Parada 1", "Sarasa",333, -1, -1, -1, 123);
    Parada parada2 = new Parada("Parada 2", "Medrano", 323, -1, -1, -1, 456);
    unasParadasDePrueba.add(parada1);
    unasParadasDePrueba.add(parada2);
    return unasParadasDePrueba;
  }

  private Linea lineaFerreaSarmiento(List<Parada> paradas) {
    BuilderLinea builder = new BuilderLinea();
    builder.setNombre("Sarmiento");
    builder.setTipoLinea(TipoLinea.FERREA);
    builder.setParadas(paradas);
    return builder.build();
  }

  private Linea lineaColectivo169(List<Parada> paradas) {
    BuilderLinea builder = new BuilderLinea();
    builder.setNombre("169");
    builder.setTipoLinea(TipoLinea.COLECTIVO);
    builder.setParadas(paradas);
    return builder.build();
  }

  @Test
  void seAgregaUnTrayectoAMiembro(){
    List<Tramo> unosTramos = new ArrayList<>();
    Tramo autoAGas = autoAGas(casaMiembro(),organizacion());
    Tramo viaje169 = viaje169(organizacion(),casaMiembro());
    unosTramos.add(autoAGas);
    unosTramos.add(viaje169);
    Trayecto trayectoCasaOrganizacion = trayectoCasaOrganizacion(unosTramos);
    Miembro unMiembro = Builders.primerMiembro();
    unMiembro.agregarTrayecto(trayectoCasaOrganizacion);
    assertTrue(unMiembro.getTrayectos().contains(trayectoCasaOrganizacion));
  }


}