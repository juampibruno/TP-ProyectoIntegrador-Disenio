package notificaciones;

import java.util.List;

public abstract class MedioDeComunicacion {

  abstract void mandarMensaje(String numTel, String pagina);

  abstract void mandarEmail(String email, String pagina);

  void enviarMensajeA(List<String> emails, List<String> numtels, String pagina) {
    numtels.forEach(numTel -> this.mandarMensaje(numTel, pagina));
    emails.forEach(email -> this.mandarEmail(email, pagina));
  }
}
