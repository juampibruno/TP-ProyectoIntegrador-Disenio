package consumo;

import excepciones.InputException;
import excepciones.InsufficientData;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import lombok.Getter;
import repository.FErepo;

@Entity
@Getter
public class Medicion {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false)
  private int id;

  @Transient // TODO creo q necesitamos converter
  private TipoDeConsumo tipoDeConsumo;
  private double valor;
  @Enumerated(EnumType.STRING)
  private Periodicidad periodicidad;
  @OneToOne
  private Periodo periodoImputacion;

  public Medicion() {

  }

  public Medicion(String line) {
    List<String> values = new ArrayList<>();
    try (Scanner rowScanner = new Scanner(line)) {
      rowScanner.useDelimiter(",");
      while (rowScanner.hasNext()) {
        values.add(rowScanner.next());
      }
    }
    if (values.size() != 4) {
      throw new InputException("Cantidad de Valores Insuficiente");
    }

    settearValores(tipoDeConsumoFromString(values.get(0)), Integer.parseInt(values.get(1)),
        periodicidadFromString(values.get(2)), values.get(3));

  }

  // para tests
  public Medicion(TipoDeConsumo tipo, double valor,
                  Periodicidad periodicidad, String periodoImputacion) {

    settearValores(tipo, valor, periodicidad, periodoImputacion);
  }

  public static double getHc(List<Medicion> mediciones, Periodo periodoImputacion) {
    List<Medicion> medicionesAux = mediciones.stream()
        .filter(m -> m.periodoImputacion.igual(periodoImputacion))
        .collect(Collectors.toList());
    if (medicionesAux.isEmpty()) {
      throw new InsufficientData("No se encuentran mediciones correspondientes al período");
    }

    return Medicion.getHcTotal(medicionesAux);
  }

  public static double getHcTotal(List<Medicion> mediciones) {
    return mediciones.stream()
        .mapToDouble(m ->
            m.getValor()
                * m.promedioConsumo()
                * FErepo.getInstance()
                .findByTipoDeConsumo(m.getTipoDeConsumo())
        ).sum();
  }

  public void setId(int id) {
    this.id = id;
  }

  public void settearValores(TipoDeConsumo tipo, double valor,
                             Periodicidad periodicidad, String periodoImputacion) {
    this.tipoDeConsumo = tipo; // TODO excps
    this.valor = valor;
    this.periodicidad = periodicidad;
    this.periodoImputacion = new Periodo(periodoImputacion);
  }

  public TipoDeConsumo tipoDeConsumoFromString(String tipoDeConsumo) {
    return Arrays.stream(TipoDeConsumo.values())
        .filter(value -> value.toString().equals(tipoDeConsumo)).findFirst().orElse(null);
  }

  public Periodicidad periodicidadFromString(String periodicidad) {
    return Arrays.stream(Periodicidad.values())
        .filter(value -> value.toString().equals(periodicidad)).findFirst().orElse(null);
  }

  public double promedioConsumo() {
    if (this.periodicidad == Periodicidad.ANUAL) {
      return this.valor / 365;
    }
    return this.valor / 30;
  }

}
