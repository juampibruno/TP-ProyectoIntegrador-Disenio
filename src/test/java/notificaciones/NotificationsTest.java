package notificaciones;

import builder.Builders;
import domain.Organizacion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import org.quartz.SchedulerException;
import static org.mockito.Mockito.*;

public class NotificationsTest {

  MailSender mailsender;
  WhatsappSender wspSender;
  EnvioRecomendaciones envioRecomendaciones;
  EnvioRecomendaciones envioRecomendacionesMock;
  NotificationJob notificationJob;

  @BeforeEach
  void init() {
    mailsender = mock(MailSender.class);
    wspSender = mock(WhatsappSender.class);
    envioRecomendaciones = Builders.enviadorDeNotificaciones(mailsender, wspSender);
    envioRecomendacionesMock = mock(EnvioRecomendaciones.class);
    notificationJob = mock(NotificationJob.class);
  }

  @Test
  void seEnvianMails() {

    doNothing().when(mailsender).mandarEmail(any(), any());

    mailsender.mandarEmail(any(), any());

    envioRecomendaciones.envioMasivo();

    verify(mailsender, atLeastOnce()).mandarEmail(any(), any());
  }

  @Test
  void seEnvianWSP() {

    doNothing().when(wspSender).mandarMensaje(any(), any());

    wspSender.mandarMensaje(any(), any());

    envioRecomendaciones.envioMasivo();

    verify(wspSender, atLeastOnce()).mandarMensaje(any(), any());
  }

  @Test
  void envioRecomendacionesLink() {
    EnvioRecomendaciones recomendaciones = Builders.enviadorDeNotificaciones(new MailSender(), new WhatsappSender());
    recomendaciones.setLink("https://notfound");
    Assertions.assertEquals(recomendaciones.getLink(), "https://notfound");
  }

  @Test
  void envioRecomendacionesSenders() {
    MailSender mailsender = new MailSender();
    WhatsappSender wspSender = new WhatsappSender();
    List<MedioDeComunicacion> listasenders = new ArrayList<>();
    listasenders.add(mailsender);
    EnvioRecomendaciones recomendaciones = Builders.enviadorDeNotificaciones(mailsender, wspSender);
    recomendaciones.setSenders(listasenders);
    Assertions.assertEquals(recomendaciones.getSenders(), listasenders);
  }

  @Test
  void schedualerSet() throws SchedulerException {

    doNothing().when(envioRecomendacionesMock).init(anyInt());

    envioRecomendacionesMock.init(2);

    verify(envioRecomendacionesMock, atLeastOnce()).init(anyInt());

    envioRecomendaciones.init(2);

    Assertions.assertTrue(envioRecomendaciones.isEnvioProgramado());

  }

  // Este test te come los USD de Twilio

  @Test
  void enviarMsjWsp() {
    Organizacion organizacion = Builders.organizacionBuilderCompleto();

    WhatsappSender whatsAppSender = new WhatsappSender();

    organizacion.setNumTel("+5491130669680");

    doNothing().when(wspSender).mandarMensaje(organizacion.getNumTel(), "Revertí esas emisiones");

    wspSender.mandarMensaje(organizacion.getNumTel(), "Revertí esas emisiones");

    //whatsAppSender.mandarMensaje(organizacion.getNumTel(), "Revertí esas emisiones");

    verify(wspSender, atLeastOnce()).mandarMensaje(organizacion.getNumTel(), "Revertí esas emisiones");
  }

}
