package sistema;

import lombok.Getter;

@Getter
public class Distancia {
  public String valor;

  public Distancia(String valor) {
    this.valor = valor;
  }

  public String getDistancia() {
    return valor;
  }
}
