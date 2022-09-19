package builder;

import domain.*;
import excepciones.NullAtributeException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BuilderAreaTest {

  @Test
  void builderExitoso() {
    assertEquals(Builders.builderCompletoArea("a").getClass(), Area.class);
  }

  @Test
  void builderConFaltaDeInfo() {
    NullAtributeException excepcion = assertThrows(
        NullAtributeException.class,
        Builders::builderIncompletoArea);
    assertEquals("Atributos null hallado - Complete todos los campos", excepcion.getMessage());
  }
}
