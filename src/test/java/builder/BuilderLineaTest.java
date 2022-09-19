package builder;

import excepciones.NullAtributeException;
import mediotransporte.Linea;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BuilderLineaTest {

  @Test
  void builderExitoso() {
    assertEquals(Builders.builderCompletoLinea().getClass(), Linea.class);
  }

  @Test
  void builderConFaltaDeInfo() {
    NullAtributeException excepcion = assertThrows(
        NullAtributeException.class,
        Builders::builderIncompletoLinea);
    assertEquals("Atributos null hallado - Complete todos los campos", excepcion.getMessage());
  }
}