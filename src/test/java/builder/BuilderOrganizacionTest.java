package builder;

import domain.Organizacion;
import excepciones.NullAtributeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BuilderOrganizacionTest {

  @Test
  void builderExitoso() {
    assertEquals(Builders.builderCompletoOrg().getClass(), Organizacion.class);
  }

  @Test
  void builderConFaltaDeInfo() {
    NullAtributeException excepcion = assertThrows(
        NullAtributeException.class,
        Builders::builderIncompletoOrg);
    assertEquals("Atributos null hallado - Complete todos los campos", excepcion.getMessage());
  }
}