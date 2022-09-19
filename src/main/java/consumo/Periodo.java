package consumo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Periodo {
  String year;
  String month = null;
  String separador = "/"; // TODO ver persistencia de esto
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(nullable = false)
  private int id;

  public Periodo(String string) {
    if (string.contains(separador)) {
      year = this.getPeriod(string, 1);
      month = this.getPeriod(string, 0);
    } else {
      year = this.getPeriod(string, 0);
    }
  }

  public Periodo() {

  }

  public void setId(int id) {
    this.id = id;
  }

  String getPeriod(String string, int number) {
    String[] str = string.split(separador);
    return str[number];
  }

  public String getMonth() {
    if (this.month == null) {
      // TODO throw new EXCEPCION que el periodo no tiene mes
      throw new RuntimeException("change");
    }
    return this.month;
  }

  public boolean igual(Periodo per) {
    if (this.month != null) {
      if (per.month == null) {
        // TODO throw new EXCEPCION que el periodo no tiene mes
      } else {
        return (this.month.equals(per.month) && this.year.equals(per.year));
      }
    } else {
      return this.year.equals(per.year);
    }
    return false;
  }

}

