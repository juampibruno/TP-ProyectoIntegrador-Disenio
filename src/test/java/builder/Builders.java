package builder;

import domain.AgenteSectorial;
import domain.*;
import mediotransporte.*;
import notificaciones.EnvioRecomendaciones;
import notificaciones.MailSender;
import notificaciones.MedioDeComunicacion;
import notificaciones.WhatsappSender;
import sistema.Municipio;
import sistema.Provincia;
import trayecto.Tramo;
import trayecto.Trayecto;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Builders {

  public static AgenteSectorial unAgente() {
    return new AgenteSectorial("Juan Rodriguez", TipoDoc.DNI, 43829143);
  }

  public static AgenteSectorial otroAgente() {
    return new AgenteSectorial("Facundo Lopez", TipoDoc.DNI, 38745893);
  }

  public static Sector<Provincia> crearSectorProvincialDeUnAgente() {
    List<Provincia> provs = new ArrayList<>();
    provs.add(new Provincia(1, "a"));
    return Builders.unAgente().crearSectorDeProvincias(provs);
  }

  public static Sector<Municipio> crearSectorMunicipalDeUnAgente() {
    List<Municipio> muns = new ArrayList<>();
    muns.add(new Municipio(1, "a"));
    return Builders.otroAgente().crearSectorDeMunicipios(muns);
  }

  public static EnvioRecomendaciones enviadorDeNotificaciones(MailSender mailsender, WhatsappSender wspSender) { // TODO poner orgs
    List<MedioDeComunicacion> senders = new ArrayList<>();
    senders.add(mailsender);
    senders.add(wspSender);
    return new EnvioRecomendaciones(senders, "https://www.ejemplo.com.ar");
  }

  public static Organizacion organizacionBuilderCompleto() {
    BuilderOrganizacion builder = new BuilderOrganizacion();
    builder.setClasificacion(Clasificacion.MINISTERIO);
    builder.setTipoOrganizacion(TipoOrganizacion.EMPRESA);
    builder.setRazonSocial("McRonald's", TipoSociedad.SA);
    builder.setUbicacion(new Ubicacion("Alem", 224, -1, -1, -1));
    builder.setEmail("pepita.vola@gmail.com");
    builder.setNumTel("whatsapp:+5491122334455");
    return builder.build();
  }

  public static Linea builderIncompletoLinea() {
    BuilderLinea builder = new BuilderLinea();
    builder.setTipoLinea(TipoLinea.COLECTIVO);
    builder.setParadas(Builders.paradasPrueba());
    return builder.build();
  }

  public static Linea builderCompletoLinea() {
    BuilderLinea builder = new BuilderLinea();
    builder.setTipoLinea(TipoLinea.COLECTIVO);
    builder.setNombre("Heisenberg");
    builder.setParadas(Builders.paradasPrueba());
    return builder.build();
  }

  public static List<Parada> paradasPrueba() {
    List<Parada> unasParadasDePrueba = new ArrayList<>();
    Parada parada1 = new Parada("Parada 1", "Sarasa", 123, -1, -1, -1, 3443);
    Parada parada2 = new Parada("Parada 2", "Medrano", 456, -1, -1, -1, 3434);
    unasParadasDePrueba.add(parada1);
    unasParadasDePrueba.add(parada2);
    return unasParadasDePrueba;
  }

  public static Area areaMarketing() {
    BuilderArea builderArea = new BuilderArea();
    builderArea.setNombre("Marketing");
    Organizacion org = Builders.unaOrganizacion();
    builderArea.setOrganizacion(org);
    return builderArea.build();
  }

  public static Miembro unMiembro() {
    return new Miembro("Juan", "Suarez", TipoDoc.DNI, 339084903);
  }

  public static Organizacion unaOrganizacion() {
    BuilderOrganizacion builder = new BuilderOrganizacion();
    builder.setClasificacion(Clasificacion.MINISTERIO);
    builder.setTipoOrganizacion(TipoOrganizacion.EMPRESA);
    builder.setRazonSocial("McRonald's", TipoSociedad.SA);
    builder.setUbicacion(new Ubicacion("Alem", 224, -1, -1, -1));
    builder.setNumTel("12345678");
    builder.setEmail("hola@gmail");
    return builder.build();
  }

  public static Organizacion otraOrganizacion() {
    BuilderOrganizacion builder = new BuilderOrganizacion();
    builder.setClasificacion(Clasificacion.MINISTERIO);
    builder.setTipoOrganizacion(TipoOrganizacion.EMPRESA);
    builder.setRazonSocial("Burger King's", TipoSociedad.SA);
    builder.setUbicacion(new Ubicacion("Avenida 9 de Julio", 3402, -1, -1, -1));
    return builder.build();
  }

  public static Miembro primerMiembro() {
    return new Miembro("Juan", "Suarez", TipoDoc.DNI, 339084903);
  }

  public static Area areaBuilderCompleto(Organizacion organizacion) {
    BuilderArea builderArea = new BuilderArea();
    builderArea.setNombre("Marketing");
    builderArea.setOrganizacion(organizacion);
    return builderArea.build();
  }

  public static Linea lineaFerreaSarmiento(List<Parada> paradas) {
    BuilderLinea builder = new BuilderLinea();
    builder.setNombre("Sarmiento");
    builder.setTipoLinea(TipoLinea.FERREA);
    builder.setParadas(paradas);
    return builder.build();
  }

  public static Linea lineaColectivo169(List<Parada> paradas) {
    BuilderLinea builder = new BuilderLinea();
    builder.setNombre("169");
    builder.setTipoLinea(TipoLinea.COLECTIVO);
    builder.setParadas(paradas);
    return builder.build();
  }

  public static Organizacion builderCompletoOrg() {
    BuilderOrganizacion builder = new BuilderOrganizacion();
    builder.setClasificacion(Clasificacion.MINISTERIO);
    builder.setTipoOrganizacion(TipoOrganizacion.EMPRESA);
    builder.setRazonSocial("McRonald's", TipoSociedad.SA);
    builder.setUbicacion(new Ubicacion("Alem", 224, -1, -1, -1));
    builder.setEmail("");
    builder.setNumTel("");
    return builder.build();
  }

  public static Organizacion builderCompletoOrg2() {
    BuilderOrganizacion builderOrganizacion = new BuilderOrganizacion();
    builderOrganizacion.setUbicacion(new Ubicacion("Almagro", 666, -1, -1, -1));
    builderOrganizacion.setTipoOrganizacion(TipoOrganizacion.EMPRESA);
    builderOrganizacion.setRazonSocial("Chirimbolos", TipoSociedad.SA);
    builderOrganizacion.setClasificacion(Clasificacion.EMPRESA_SECTOR_PRIMARIO);
    builderOrganizacion.setEmail("");
    builderOrganizacion.setNumTel("");
    return builderOrganizacion.build();
  }

  public static Area builderCompletoArea(String nombre) {
    BuilderArea builderArea = new BuilderArea();
    builderArea.setNombre(nombre);
    builderArea.setOrganizacion(builderCompletoOrg2());
    return builderArea.build();
  }

  public static Ubicacion casaMiembro() {
    return new Ubicacion("Avenida Rivadavia", 3500, -1, -1, -1);
  }

  public static Ubicacion organizacion() {
    return new Ubicacion("Avenida Rivadavia", 4900, -1, -1, -1);
  }

  public static Miembro builderCompletoMiembro() {
    return new Miembro("Vladimir", "Macao", TipoDoc.DNI, 45123679);
  }

  public static Admin admin(String password) {
    return new Admin(usuario(), password);
  }

  public static Admin adminBuenaContra() {
    return admin(buenaContra());
  }

  public static Admin adminBuenaContra2() {
    return admin(buenaContra2());
  }

  public static Admin adminMalaContra() {
    return admin(malaContra());
  }

  public static String buenaContra() {
    return "paraBailarLaB4amb4SeNeces1taUnaP0caDeGracia";
  }

  public static String buenaContra2() {
    return "unaP0cADeGrAc1APAT1PAM1YHAc1AArr1bAYArr1bA";
  }

  public static String buenaContra3() {
    return "holaBebe";
  }

  public static String malaContra() {
    return "aaa";
  }

  public static String usuario() {
    return "usuario";
  }

  public static Trayecto trayectoCasaOrganizacion(List<Tramo> unosTramos) {
    return new Trayecto(LocalDate.now(), unosTramos);
  }

  public static Trayecto trayectoSanatorioColegio(List<Tramo> unosTramos) {
    return new Trayecto(LocalDate.now(), unosTramos);
  }

  public static Tramo autoAGas(Ubicacion origen, Ubicacion destino) {
    return new Tramo(origen, destino, new VehiculoParticular(TipoCombustible.GNC));
  }

  public static Tramo viaje169(Ubicacion origen, Ubicacion destino) {
    return new Tramo(origen, destino, lineaColectivo169(paradasPrueba()));
  }

  public static Tramo autoElectrico() {
    return new Tramo(colegioMarianista(), sanatorioLaTrinidad(), new VehiculoParticular(TipoCombustible.ELECTRICO));
  }

  public static Tramo viajeSarmiento() {
    return new Tramo(sanatorioLaTrinidad(), colegioMarianista(), lineaFerreaSarmiento(estacionesPrueba()));
  }

  public static Ubicacion sanatorioLaTrinidad() {
    return new Ubicacion("Bartolome Mitre", 2553, -1, -1, -1);
  }

  public static Ubicacion colegioMarianista() {
    return new Ubicacion("Av. Rivadavia", 5652, -1, -1, -1);
  }

  public static List<Parada> estacionesPrueba() {
    List<Parada> unasEstacionesDePrueba = new ArrayList<>();
    Parada estacion1 = new Parada("Once", "Av. Pueyrredon", 200, -1, -1, -1, 2553);
    Parada estacion2 = new Parada("Caballito", "Rojas", 288, -1, -1, -1, 123231);
    unasEstacionesDePrueba.add(estacion1);
    unasEstacionesDePrueba.add(estacion2);
    return unasEstacionesDePrueba;
  }

  public static Area builderIncompletoArea() {
    BuilderArea builder = new BuilderArea();
    builder.setNombre("Marketing");
    return builder.build();
  }

  public static Organizacion builderIncompletoOrg() {
    BuilderOrganizacion builder = new BuilderOrganizacion();
    builder.setClasificacion(Clasificacion.MINISTERIO);
    builder.setTipoOrganizacion(TipoOrganizacion.EMPRESA);
    builder.setUbicacion(new Ubicacion("Alem", 224, -1, -1, -1));
    return builder.build();
  }

}
