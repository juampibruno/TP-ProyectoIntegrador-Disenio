package domain;

import builder.Builders;
import excepciones.NotPendingApprovalException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AreaTest {

  private void vinculacionFallida() {
    Area areaAVincular = Builders.areaMarketing();
    Miembro miembroQueSeQuiereVincular = Builders.unMiembro();
    areaAVincular.aceptarMiembro(miembroQueSeQuiereVincular);
  }

  @Test
  void seLanzaUnaExcepcionSiSeQuiereAceptarUnMiembroSinPedirVinculacion() {
    Area areaAVincular = Builders.areaMarketing();
    Miembro miembroQueSeQuiereVincular = Builders.unMiembro();
    Assertions.assertThrows(NotPendingApprovalException.class, () -> areaAVincular.aceptarMiembro(miembroQueSeQuiereVincular));
  }

}

