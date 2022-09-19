package repository;

import consumo.FactorDeEmision;
import consumo.TipoDeConsumo;
import java.util.ArrayList;

public class FErepo extends GenericRepo<FactorDeEmision> {

  static FErepo instancia = new FErepo();

  public FErepo() {
    storage = new ArrayList<>();
  }

  public static FErepo getInstance() {
    return instancia;
  }

  @Override
  public void removeID(int id) {

  }

  public Double findByTipoDeConsumo(TipoDeConsumo c) {
    return this.findBy(fe -> fe.getTipoDeConsumo() == c).get(0).getValor();
  }
}
