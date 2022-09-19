package trayecto;

import excepciones.InvalidIdenticalStop;
import excepciones.InvalidStopNotRegistred;
import java.util.ArrayList;
import java.util.function.ToDoubleFunction;

public class ListaConDistancias<T> extends ArrayList<T> {
  private void validarPuntos(T origen, T destino) {
    if (!this.contains(origen)) {
      throw new InvalidStopNotRegistred("Origen no registrado");
    } else if (!this.contains(destino)) {
      throw new InvalidStopNotRegistred("Destino no registrado");
    } else if (this.indexOf(origen) == this.indexOf(destino)) {
      throw new InvalidIdenticalStop("Origen y destino son iguales");
    }
  }

  public double calcularDistanciaEntre(T origen, T destino, ToDoubleFunction<T> mapper) {
    validarPuntos(origen, destino);
    return this.subList(this.indexOf(origen), this.indexOf(destino))
        .stream()
        .mapToDouble(mapper)
        .sum();
  }
}
