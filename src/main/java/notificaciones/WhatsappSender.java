package notificaciones;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

public class WhatsappSender extends MedioDeComunicacion {
  // Find your Account Sid and Token at twilio.com/console
  public static final String ACCOUNT_SID = "AC9932a64932625f687ca3d879ecf50b53";
  public static final String AUTH_TOKEN = "2a4e468c4e0929234ff721a3d80e5904";

  public static void sendMessage(String fromNumber, String toNumber, String message)
      throws RuntimeException {
    Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    Message.creator(
        new com.twilio.type.PhoneNumber("whatsapp:" + toNumber),
        new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
        message).create();
  }

  @Override
  public void mandarEmail(String email, String mensaje) {

  }

  @Override
  public void mandarMensaje(String numTel, String mensaje) {
    try {
      sendMessage("whatsapp:+14155238886", numTel, mensaje);
    } catch (RuntimeException e) {
      throw new RuntimeException("No se encontró el número de la organización", e);
    }
  }
}
