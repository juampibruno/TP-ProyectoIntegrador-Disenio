package repository;

import java.util.ArrayList;
import mediotransporte.ServicioContratado;

public class ServicioContratadoRepo extends GenericRepo<ServicioContratado> {

  static ServicioContratadoRepo instancia = new ServicioContratadoRepo();

  public ServicioContratadoRepo() {
    storage = new ArrayList<>();
  }

  public static ServicioContratadoRepo getInstance() {
    return instancia;
  }

  public void removeID(int id) {
  }

}