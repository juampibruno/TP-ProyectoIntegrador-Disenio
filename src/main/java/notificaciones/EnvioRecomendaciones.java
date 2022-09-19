package notificaciones;

import domain.Organizacion;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import repository.OrgRepo;

@Getter
public class EnvioRecomendaciones {
  List<MedioDeComunicacion> senders;
  String linkARecomendaciones;
  boolean envioProgramado;

  public EnvioRecomendaciones(List<MedioDeComunicacion> senders, String linkReco) {
    this.senders = senders;
    this.linkARecomendaciones = linkReco;
  }

  public void envioMasivo() {
    List<String> emails = new ArrayList<>();
    OrgRepo.getInstance().getAll().stream()
        .map(Organizacion::getEmail)
        .forEach(emails::add);

    List<String> numTels = new ArrayList<>();
    OrgRepo.getInstance().getAll().stream()
        .map(Organizacion::getNumTel)
        .forEach(numTels::add);

    senders.forEach(s -> s.enviarMensajeA(emails, numTels, linkARecomendaciones));
  }

  public String getLink() {
    return this.linkARecomendaciones;
  }

  public void setLink(String linkDireccionamiento) {
    this.linkARecomendaciones  = linkDireccionamiento;
  }

  public void setSenders(List<MedioDeComunicacion> senders) {
    this.senders  = senders;
  }

  public void init(int dias) throws SchedulerException {

    Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

    JobDetail job = JobBuilder.newJob(NotificationJob.class)
        .withIdentity("Notification")
        .build();

    job.getJobDataMap().put("envioRec", this);

    Trigger trigger = TriggerBuilder.newTrigger()
        .startNow()
        .withIdentity("Trigger", "group1")
        .withSchedule(SimpleScheduleBuilder.simpleSchedule()
            .withIntervalInHours(dias * 24)
            .repeatForever())
        .build();

    scheduler.scheduleJob(job, trigger);

    scheduler.start();

    this.envioProgramado = true;
  }
}











