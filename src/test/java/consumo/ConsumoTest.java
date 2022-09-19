package consumo;

import domain.Clasificacion;
import domain.Organizacion;
import domain.TipoOrganizacion;
import excepciones.InvalidUnitsException;
import mediotransporte.Ubicacion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.FErepo;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;

class ConsumoTest {

  private Medicion medicionMock;
  private FErepo feRepo;

  @BeforeEach
  void init() {
    medicionMock = mock(Medicion.class);
    feRepo = FErepo.getInstance();
    feRepo.removeAll();
  }

  @Test
  void tipoDeConsumoFromString() {
    Medicion medicion = new Medicion();
    Assertions.assertEquals(TipoDeConsumo.class, medicion.tipoDeConsumoFromString("Gas Natural").getClass());
  }

  @Test
  void periodicidadFromString() {
    Medicion medicion = new Medicion();
    Assertions.assertEquals(Periodicidad.class, medicion.periodicidadFromString("Anual").getClass());
  }

  @Test
  void siAgregoUnFactorDeEmisionConUnidadInconsistenteLanzaError() {
    Assertions.assertThrows(InvalidUnitsException.class, () -> new FactorDeEmision(TipoDeConsumo.GAS_NATURAL, Unidad.LT, 32.3));
  }

  @Test
  void subidaCorrectaDeFactorDeEmision() {
    new FactorDeEmision(TipoDeConsumo.GAS_NATURAL, Unidad.M3, 32.2);
    Assertions.assertEquals(32.2, feRepo.findByTipoDeConsumo(TipoDeConsumo.GAS_NATURAL));
  }

  @Test
  void cargaDeArchivoDeMedicionesCorrecta() {
    Organizacion o = new Organizacion("Hola", TipoOrganizacion.EMPRESA, new Ubicacion("", 3, -1, -1, -1), Clasificacion.EMPRESA_SECTOR_PRIMARIO, "", "");

    Assertions.assertDoesNotThrow(() -> o.cargarDatosDeActividades("src/test/resources/medicionesCorrectas.csv"));
    Assertions.assertEquals(TipoDeConsumo.ELECTRICIDAD, o.getMediciones().get(2).getTipoDeConsumo());
    Assertions.assertEquals(31, o.getMediciones().get(3).getValor());
    Assertions.assertEquals(Periodicidad.ANUAL, o.getMediciones().get(0).getPeriodicidad());
  }

  @Test
  void cargaIncorrectaDeArchivoDeMediciones() {
    Organizacion org = new Organizacion("Hola", TipoOrganizacion.EMPRESA, new Ubicacion("", 3, -1, -1, -1), Clasificacion.EMPRESA_SECTOR_PRIMARIO, "", ""); // TODO agregar al otro archivo
    Assertions.assertThrows(FileNotFoundException.class, () -> org.cargarDatosDeActividades("sdaafsdafs"));
    Assertions.assertThrows(RuntimeException.class, () -> org.cargarDatosDeActividades("src/test/resources/medicionesErroneas.csv"));
  }

  //TODO
  @Test
  void testPeriodoSimple() {
    Periodo periodo = new Periodo("1234"); // TODO agregar al otro archivo
    Assertions.assertEquals("1234", periodo.getYear());
    Assertions.assertThrows(RuntimeException.class, () -> periodo.getMonth());
  }

  @Test
  void testPeriodoCompleto() {
    Periodo periodo = new Periodo("12/1234"); // TODO agregar al otro archivo
    Assertions.assertEquals("1234", periodo.getYear());
    Assertions.assertEquals("12", periodo.getMonth());
  }

  @Test
  void gettearHCSoloYear() {
    FErepo.getInstance().add(new FactorDeEmision(TipoDeConsumo.GAS_NATURAL, TipoDeConsumo.GAS_NATURAL.getUnidad(), (double) 365));
    List<Medicion> medicionList = new ArrayList<>();
    medicionList.add(new Medicion(TipoDeConsumo.GAS_NATURAL, 2, Periodicidad.ANUAL, "1111")); // TODO agregar al otro archivo
    Assertions.assertEquals((2 * (double) (2 / (double) 365) * 365), Medicion.getHc(medicionList, new Periodo("1111")));
  }

  @Test
  void gettearHCFechaComp() {
    FErepo.getInstance().add(new FactorDeEmision(TipoDeConsumo.GAS_NATURAL, TipoDeConsumo.GAS_NATURAL.getUnidad(), (double) 365));
    List<Medicion> medicionList = new ArrayList<>();
    medicionList.add(new Medicion(TipoDeConsumo.GAS_NATURAL, 2, Periodicidad.ANUAL, "11/1111")); // TODO agregar al otro archivo
    Assertions.assertEquals((2 * (double) (2 / (double) 365) * 365), Medicion.getHc(medicionList, new Periodo("11/1111")));
  }

  @Test
  void gettearHCNoCoincideMes() { // TODO
    FErepo.getInstance().add(new FactorDeEmision(TipoDeConsumo.GAS_NATURAL, TipoDeConsumo.GAS_NATURAL.getUnidad(), (double) 365));
    List<Medicion> medicionList = new ArrayList<>();
    medicionList.add(new Medicion(TipoDeConsumo.GAS_NATURAL, 1, Periodicidad.ANUAL, "12/1111")); // TODO agregar al otro archivo
    Assertions.assertThrows(RuntimeException.class, () -> Medicion.getHc(medicionList, new Periodo("11/1111")));
  }

  @Test
  void gettearHCNoCoincideYear() { // TODO
    FErepo.getInstance().add(new FactorDeEmision(TipoDeConsumo.GAS_NATURAL, TipoDeConsumo.GAS_NATURAL.getUnidad(), (double) 365));
    List<Medicion> medicionList = new ArrayList<>();
    medicionList.add(new Medicion(TipoDeConsumo.GAS_NATURAL, 1, Periodicidad.ANUAL, "11/1111")); // TODO agregar al otro archivo
    Assertions.assertThrows(RuntimeException.class, () -> Medicion.getHc(medicionList, new Periodo("11/1112")));
  }

}
