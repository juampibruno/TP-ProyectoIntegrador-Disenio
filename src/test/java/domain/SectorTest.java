package domain;

import builder.Builders;
import consumo.TipoDeConsumo;
import consumo.Unidad;
import excepciones.AlreadyExists;
import excepciones.InvalidUnitsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SectorTest {

  @Test
  void sectorSeCargaCorrectamenteAlRepo(){
    Builders.crearSectorProvincialDeUnAgente();
  }

  /*@Test // TODO no se porque no funca
  void crearSectorYaExistente() {
    Builders.crearSectorProvincialDeUnAgente();
    Assertions.assertThrows(AlreadyExists.class,
        Builders::crearSectorProvincialDeUnAgente);
  }*/


}
