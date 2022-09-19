package sistema;

import builder.Builders;
import consumo.TipoDeConsumo;
import consumo.Unidad;
import domain.Admin;
import excepciones.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import domain.ControladorDeCuentas;
import repository.FErepo;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SistemaYAdminTest {
  ControladorDeCuentas controladorDeCuentas;
  FErepo feRepo;

  @BeforeEach
  void init() {
    feRepo = FErepo.getInstance();
    controladorDeCuentas = new ControladorDeCuentas();
  }

  void contraseniaInvalida(String password) throws IOException {
    assertTrue(controladorDeCuentas.notValidPassword(password));
  }

  void contraseniaValida(String password) throws IOException {
    assertFalse(controladorDeCuentas.notValidPassword(password));
  }

  @Test
  void contraseniaInvalidaErrorEnRegisto() {
    InvalidPasswordException excepcion = assertThrows(
        InvalidPasswordException.class,
        () -> controladorDeCuentas.registerAdmin(Builders.adminMalaContra()));
    assertEquals("Error - Contraseña Inválida", excepcion.getMessage());
  }

  @Test
  void registoExitoso() throws IOException {
    Admin admin = Builders.adminBuenaContra();
    controladorDeCuentas.registerAdmin(admin);
    assertTrue(controladorDeCuentas.getAdmins().contains(admin));
  }

  @Test
  void falloLogueoAdminNoRegistrado() {
    InvalidPasswordException excepcion = assertThrows(
        InvalidPasswordException.class,
        () -> controladorDeCuentas.logAdmin(Builders.usuario(), Builders.malaContra()));
    assertEquals("Error - Admin No Registrado", excepcion.getMessage());
  }

  @Test
  void falloLogueoContraseniaErronea() throws IOException {
    Admin admin = Builders.adminBuenaContra();
    controladorDeCuentas.registerAdmin(admin);
    InvalidPasswordException excepcion = assertThrows(
        InvalidPasswordException.class,
        () -> controladorDeCuentas.logAdmin(Builders.usuario(), Builders.buenaContra2()));
    assertEquals("Error - Contraseña Incorrecta", excepcion.getMessage());
    assertFalse(admin.isLogged());
  }

  @Test
  void logueoExitoso() throws IOException {
    Admin admin = Builders.adminBuenaContra2();
    controladorDeCuentas.registerAdmin(admin);
    Admin logueado = controladorDeCuentas.logAdmin(Builders.usuario(), Builders.buenaContra2());
    assertTrue(admin.isLogged());
    assertEquals(admin, logueado);
  }

  @Test
  void falloCambiodeContraseniaPorContraseniaErronea() throws IOException {
    Admin admin = Builders.adminBuenaContra();
    controladorDeCuentas.registerAdmin(admin);
    InvalidPasswordException excepcion = assertThrows(
        InvalidPasswordException.class,
        () -> controladorDeCuentas.logAdmin(Builders.usuario(), Builders.buenaContra3()));
    assertEquals("Error - Contraseña Incorrecta", excepcion.getMessage());
    assertFalse(admin.isLogged());
    RuntimeException excepcion2 = assertThrows(
        RuntimeException.class,
        () -> controladorDeCuentas.changeAdminPassword(admin, Builders.buenaContra3()));
    assertEquals("Error - El admin no está logueado", excepcion2.getMessage());
  }

  @Test
  void cambiodeContraseniaExitoso() throws IOException {
    Admin admin = Builders.adminBuenaContra();
    controladorDeCuentas.registerAdmin(admin);
    controladorDeCuentas.logAdmin(Builders.usuario(), Builders.buenaContra());
    controladorDeCuentas.changeAdminPassword(admin, "unaP0cADeGrAc1APAT1PAM1YHAc1AArr1bAYArr1bA");
    assertTrue(controladorDeCuentas.authenticatePassword(
        "unaP0cADeGrAc1APAT1PAM1YHAc1AArr1bAYArr1bA",
        admin.getPassword()
    ));

  }

  @Test
  void getDistanciaCorrecta() {
    assertEquals(new Distancia("54").getDistancia(), "54");
  }

  @Test
  void getIdCorrectoProvincia() {
    assertEquals(new Provincia(54, "bs as").getIdProv(), 54);
  }

  @Test
  void getIdCorrectoMunicipio() {
    assertEquals(new Municipio(54, "bs as").getIdMun(), 54);
  }

  @Test
  void siTieneMasDe3CaracteresConsecutivosFalla() throws IOException {
    contraseniaInvalida("holaaaa1234");
    contraseniaInvalida("aaaaaaaaaaa");
  }

  @Test
  void siEstaEntreLasPeores10000Falla() throws IOException {
    contraseniaInvalida("snowball");
    contraseniaInvalida("password1");
    contraseniaInvalida("swimming");
  }

  @Test
  void siTieneMenosDe8CaracteresFalla() throws IOException {
    contraseniaInvalida("1234567");
    contraseniaInvalida("a");
  }

  @Test
  void siTieneMasDe64CaracteresFalla() throws IOException {
    contraseniaInvalida("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"); //tiene 65
    contraseniaInvalida("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb"); // tiene 100
  }

  @Test
  void siTienePalabrasDelDiccionaioEspaniolFalla() throws IOException {
    contraseniaInvalida("Alejandra");
    contraseniaInvalida("Borrachos");
    contraseniaInvalida("Patagones");
  }

  @Test
  void siCumpleTodasLasCondicionesNoFalla() throws IOException {
    contraseniaValida("jdafkjasdfja433");
    contraseniaValida("ertflssaau3");
    contraseniaValida("asdlfjlasdkfj");
  }

  @Test
  void unidadesDeFEsNoCoinciden() {
    Admin admin = new Admin("a", "asdasdad");
    admin.setLogged();
    Assertions.assertThrows(InvalidUnitsException.class, () -> admin.cargarFE(TipoDeConsumo.GAS_NATURAL, Unidad.LT, 12));
  }

  @Test
  void adminNoLogueadoParaCarga() {
    Admin admin = new Admin("a", "asdasdad");
    Assertions.assertThrows(NotLoggedException.class, () -> admin.cargarFE(TipoDeConsumo.GAS_NATURAL, Unidad.LT, 12));
  }

  @Test
  void localidadCorrecta() {
    Localidad localidad = new Localidad(1, "nombre");
    assertEquals(1, localidad.getId());
  }

}