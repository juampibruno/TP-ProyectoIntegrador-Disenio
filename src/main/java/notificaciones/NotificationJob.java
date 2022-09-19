package notificaciones;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;

public class NotificationJob implements Job {

  public void execute(JobExecutionContext context) {
    JobDataMap dataMap = context.getJobDetail().getJobDataMap();
    EnvioRecomendaciones envioRec = (EnvioRecomendaciones) dataMap.get("envioRec");
    envioRec.envioMasivo();
  }
}
