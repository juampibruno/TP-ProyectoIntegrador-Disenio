package domain;

import consumo.FactorDeEmision;
import consumo.TipoDeConsumo;
import consumo.Unidad;
import excepciones.NotLoggedException;
import java.io.IOException;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import notificaciones.EnvioRecomendaciones;
import notificaciones.MedioDeComunicacion;
import org.quartz.SchedulerException;

@Entity
@Getter
public class Admin {
  @Id
  @Column(nullable = false)
  private String user;

  @Setter
  private String password;

  @Transient
  private boolean logged;

  @Transient //TODO Transient
  private ControladorDeCuentas controladorDeCuentas;

  public Admin(String user, String password) {
    this.user = user;
    this.password = password;
    this.controladorDeCuentas = new ControladorDeCuentas();
  }

  public Admin() {
  }


  public void cargarFE(TipoDeConsumo tipoCons, Unidad uni, double cons) {
    if (isLogged()) {
      new FactorDeEmision(tipoCons, uni, cons);
    } else {
      throw new NotLoggedException("Admin Not Logged in");
    }
  }

  public void login(String user, String password) {
    controladorDeCuentas.logAdmin(user, password);
  }

  public void changePassword(String password) throws IOException {
    controladorDeCuentas.changeAdminPassword(this, password);
  }

  public void setLogged() {
    this.logged = true;
  }

  public boolean isLogged() {
    return this.logged;
  }

  void configurarTareaCalendarizada(int cantidadHoras, String link, List<MedioDeComunicacion> medio)
      throws SchedulerException {
    new EnvioRecomendaciones(medio, link).init(cantidadHoras);
  }

}
