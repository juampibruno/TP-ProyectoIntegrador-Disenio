package domain;

import excepciones.InvalidPasswordException;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import org.passay.DictionaryRule;
import org.passay.LengthRule;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.RepeatCharacterRegexRule;
import org.passay.Rule;
import org.passay.RuleResult;
import org.passay.dictionary.ArrayWordList;
import org.passay.dictionary.WordListDictionary;
import org.passay.dictionary.WordLists;
import org.passay.dictionary.sort.ArraysSort;

public class ControladorDeCuentas {
  private static int hashSize = 128;
  private static int hashIterations = 65536;
  private static String hashAlgorithm = "PBKDF2WithHmacSHA1";
  private List<Admin> admins;

  public ControladorDeCuentas() {
    this.admins = new ArrayList<>();
  }

  public List<Admin> getAdmins() {
    return admins;
  }

  //Registro, logueo y Cambio de Contraseñas
  public Admin logAdmin(String user, String password) {
    Admin adminRegistrado = this.admins.stream()
        .filter(a -> a.getUser().equals(user))
        .findFirst()
        .orElse(null);
    validarLogueo(adminRegistrado, password);
    adminRegistrado.setLogged();
    return adminRegistrado;
  }

  public void validarLogueo(Admin adminRegistrado, String password) {
    if (adminRegistrado == null) {
      throw new InvalidPasswordException("Error - Admin No Registrado");
    }
    if (!authenticatePassword(password, adminRegistrado.getPassword())) {
      throw new InvalidPasswordException("Error - Contraseña Incorrecta");
    }
  }

  public void changeAdminPassword(Admin admin, String password) throws IOException {
    if (admin.isLogged()) {
      validarPassword(password);
      admin.setPassword(hashPassword(password));
    } else {
      throw new RuntimeException("Error - El admin no está logueado");
    }
  }

  public void validarPassword(String password) throws IOException {
    if (notValidPassword(password)) {
      throw new InvalidPasswordException("Error - Contraseña Inválida");
    }
  }

  public Admin registerAdmin(Admin admin) throws IOException {
    if (notValidPassword(admin.getPassword())) {
      throw new InvalidPasswordException("Error - Contraseña Inválida");
    }
    admin.setPassword(hashPassword(admin.getPassword()));
    this.admins.add(admin);
    return admin;
  }

  private byte[] generateHash(char[] password, byte[] salt) {
    KeySpec spec = new PBEKeySpec(password, salt, hashIterations, hashSize);
    try {
      SecretKeyFactory f = SecretKeyFactory.getInstance(hashAlgorithm);
      return f.generateSecret(spec).getEncoded();
    } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
      throw new RuntimeException("Error - No se pudo generar el hash");
    }
  }

  public boolean authenticatePassword(String password, String token) {
    String[] parts = token.split("\\$");
    Base64.Decoder decoder = Base64.getDecoder();
    byte[] salt = decoder.decode(parts[0]);
    byte[] hash = decoder.decode(parts[1]);
    byte[] testHash = generateHash(password.toCharArray(), salt);
    return Arrays.equals(hash, testHash);
  }

  public String hashPassword(String password) {
    byte[] salt = new byte[hashSize / 8];
    SecureRandom random = new SecureRandom();
    random.nextBytes(salt);
    byte[] hash = generateHash(password.toCharArray(), salt);
    Base64.Encoder enc = Base64.getEncoder();
    return enc.encodeToString(salt) + "$" + enc.encodeToString(hash);
  }

  public boolean notValidPassword(String password) throws IOException {
    //creo lista de reglas - Reglas: aquellas que tiene que cumplir una contraseña para ser válida
    List<Rule> ruleList = new ArrayList<>();
    //creo regla - Contraseñas entre 8 y 64 caracteres
    LengthRule lengthRule = new LengthRule(8, 64);
    RepeatCharacterRegexRule characterRepetition = new RepeatCharacterRegexRule(3);
    //añado regla a la lista
    ruleList.add(lengthRule);
    ruleList.add(characterRepetition);

    // cro una lista case sensitive a partir de un file
    ArrayWordList passwordList;
    try (
        InputStreamReader fileReader = new InputStreamReader(
            Files.newInputStream(
                Paths.get("src/main/resources/Top Worst Passwords.txt".replace("/", File.separator))
            ),
            StandardCharsets.UTF_8)) {
      passwordList = WordLists.createFromReader(
          new InputStreamReader[]{fileReader},
          true,
          new ArraysSort());
    } catch (NoSuchFileException e) {
      throw new RuntimeException("No se encontró ningún archivo", e);
    } catch (IOException e) {
      throw new RuntimeException("No se encontró ningún archivo", e);
    }

    //transformo la lista de reglas en un diccionario.
    WordListDictionary dictionary = new WordListDictionary(passwordList);
    //creo regla - Los strings hallados en el file
    // anteriormente no pueden coincidir con la contraseña
    DictionaryRule dictionaryRule = new DictionaryRule(dictionary);
    //añado regla a la lista
    ruleList.add(dictionaryRule);

    ArrayWordList spanishWordList;

    try (InputStreamReader fileReader = new InputStreamReader(
        Files.newInputStream(
            Paths.get("src/main/resources/Spanish Dictionary.dic".replace("/", File.separator))
        ),
        StandardCharsets.UTF_8)) {
      spanishWordList = WordLists.createFromReader(
          new InputStreamReader[]{fileReader},
          true,
          new ArraysSort());
    } catch (NoSuchFileException e) {
      throw new RuntimeException("No se encontró ningún archivo", e);
    } catch (IOException e) {
      throw new RuntimeException("No se encontró ningún archivo", e);
    }

    //transformo la lista de reglas en un diccionario
    dictionary = new WordListDictionary(spanishWordList);
    //creo regla - Los strings hallados en el
    //file anteriormente no pueden coincidir con la contraseña
    dictionaryRule = new DictionaryRule(dictionary);
    //añado regla a la lista
    ruleList.add(dictionaryRule);

    //creo validador de contraseñas con la lista de reglas anteriormente setteada
    PasswordValidator passwordValidator = new PasswordValidator(ruleList);
    //válido la contraseña almacenada en passwordData contra las reglas almacenadas
    //en passwordValidator, y los resulados se guardan en result
    PasswordData passwordData = new PasswordData(password);
    RuleResult result = passwordValidator.validate(passwordData);

    return !result.isValid();
  }
}
