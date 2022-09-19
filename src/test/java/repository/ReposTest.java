package repository;

import builder.Builders;
import domain.*;
import org.junit.jupiter.api.*;
import sistema.Municipio;
import sistema.Provincia;

@TestMethodOrder(MethodOrderer.MethodName.class)
public class ReposTest {

  OrgRepo orgRepo;
  AreaRepo areaRepo;
  MiembroRepo miembroRepo;
  SectorProvinciasRepo sectorRepoProvs;
  SectorMunicipiosRepo sectorRepoMunis;
  Organizacion org;
  Area area;
  Miembro miembro;
  Sector<Provincia> sectorProvincial;
  Sector<Municipio> sectorMunicipal;

  @BeforeEach
  void init() {
    orgRepo          = OrgRepo.getInstance();
    areaRepo         = AreaRepo.getInstance();
    miembroRepo      = MiembroRepo.getInstance();
    sectorRepoProvs  = SectorProvinciasRepo.getInstance();
    sectorRepoMunis  = SectorMunicipiosRepo.getInstance();
    org              = Builders.builderCompletoOrg();
    area             = Builders.builderCompletoArea("a");
    miembro          = Builders.builderCompletoMiembro();
    sectorProvincial = Builders.crearSectorProvincialDeUnAgente();
    sectorMunicipal  = Builders.crearSectorMunicipalDeUnAgente();
  }

  @AfterEach
  void destroy() {
    orgRepo.removeAll();
    areaRepo.removeAll();
    miembroRepo.removeAll();
    sectorRepoProvs.removeAll();
    sectorRepoMunis.removeAll();

  }

  @Test
  void A_adds() {
    Organizacion org1 = org;
    Area area1 = area;
    Miembro miembro1 = miembro;
    Sector<Provincia> sectorPro = sectorProvincial;
    Sector<Municipio> sectorMun = sectorMunicipal;

    orgRepo.add(org1);
    Assertions.assertEquals(org1, orgRepo.findByID(org1.getId()).get(0));

    areaRepo.add(area1);
    Assertions.assertEquals(area1, areaRepo.findByID(area1.getId()).get(0));

    miembroRepo.add(miembro1);
    Assertions.assertEquals(miembro1, miembroRepo.findByID(miembro1.getId()).get(0));

    /* TODO
    sectorRepoProvs.add(sectorPro);
    Assertions.assertEquals(sectorPro, sectorRepoProvs.findByTerritorioID(sectorPro.getIdMuni(), sectorPro.getIdProv()).get(0));

    sectorRepoMunis.add(sectorMun);
    Assertions.assertEquals(sectorMun, sectorRepoMunis.findByTerritorioID(sectorMun.getIdMuni(), sectorMun.getIdProv()).get(0));*/
  }

  @Test
  void B_getMiembros() {
    Organizacion org1 = org;
    orgRepo.add(org1);

    Area area1 = new Area("Contaduria", org1);
    areaRepo.add(area1);

    Miembro miembroTest = miembro;
    miembroTest.vincularConArea(area1);
    area1.aceptarMiembro(miembroTest);
    Assertions.assertEquals(miembroTest, area1.getMiembros().get(0));
  }

  @Test
  void C_getAlls() {
    orgRepo.add(org);
    areaRepo.add(area);
    miembroRepo.add(miembro);
    Assertions.assertEquals(1, orgRepo.getAll().size());
    Assertions.assertEquals(1, areaRepo.getAll().size());
    Assertions.assertEquals(1, miembroRepo.getAll().size());
  }
}
