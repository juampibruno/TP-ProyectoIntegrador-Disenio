package notificaciones;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.apache.commons.codec.binary.Base64;

public class MailSender extends MedioDeComunicacion {
  private static String APPLICATION_NAME = "Gmail API Java Quickstart";
  private static JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
  private static String TOKENS_DIRECTORY_PATH = "./tokens";
  private static List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_COMPOSE);
  private static String CREDENTIALS_FILE_PATH = "./credentials.json";

  private static Gmail serviceInstance = null;

  private static Gmail getGmailInstance() throws IOException, GeneralSecurityException {
    if (serviceInstance != null) {
      return serviceInstance;
    }
    final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
    return new Gmail.Builder(httpTransport, JSON_FACTORY, getCredentials(httpTransport))
        .setApplicationName(APPLICATION_NAME)
        .build();
  }

  private static Credential getCredentials(final NetHttpTransport httpTransport)
      throws IOException {
    Path path = Paths
        .get((TOKENS_DIRECTORY_PATH + CREDENTIALS_FILE_PATH)
            .replace("/", File.separator));
    InputStreamReader fileReader;
    try {
      fileReader = new InputStreamReader(Files.newInputStream(path), StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new RuntimeException("No se encontró el archivo de credenciales (MailSender)", e);
    }
    GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, fileReader);
    // Build flow and trigger user authorization request.
    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
        httpTransport, JSON_FACTORY, clientSecrets, SCOPES)
        .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
        .setAccessType("offline")
        .build();
    LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
    //returns an authorized Credential object.
    return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
  }

  public static MimeMessage createEmail(
      String toEmailAddr, String fromEmailAddr, String subj, String bodyTxt
  ) throws MessagingException {
    Properties props = new Properties();
    Session session = Session.getDefaultInstance(props, null);
    MimeMessage email = new MimeMessage(session);
    email.setFrom(new InternetAddress(fromEmailAddr));
    email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(toEmailAddr));
    email.setSubject(subj);
    email.setText(bodyTxt);
    return email;
  }

  public static Message createMessageWithEmail(MimeMessage emailContent)
      throws MessagingException, IOException {
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    emailContent.writeTo(buffer);
    byte[] bytes = buffer.toByteArray();
    String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
    Message message = new Message();
    message.setRaw(encodedEmail);
    return message;
  }

  public static void sendEmail(
      String fromEmailAddr, String toEmailAddr, String msgSubj, String bodyText
  ) throws MessagingException, IOException, GeneralSecurityException {
    // Get service instance
    Gmail service = getGmailInstance();
    // Create the email content
    MimeMessage email = createEmail(toEmailAddr, fromEmailAddr, msgSubj, bodyText);
    // Encode and wrap the MIME message into a gmail message
    Message message = createMessageWithEmail(email);
    try {
      // Create send message
      Message sentMessage = service.users().messages().send("me", message).execute();
      System.out.println("Message id: " + sentMessage.getId());
      System.out.println(sentMessage.toPrettyString());
    } catch (GoogleJsonResponseException e) {
      GoogleJsonError error = e.getDetails();
      if (error.getCode() == 403) {
        //System.err.println("Unable to send message: " + e.getDetails());
      } else {
        throw e;
      }
    }
  }

  @Override
  public void mandarMensaje(String numTel, String pagina) {

  }

  @Override
  public void mandarEmail(String email, String pagina) {
    try {
      sendEmail("faustofusse@gmail.com", email, "Titulazo", pagina);
    } catch (MessagingException | IOException | GeneralSecurityException e) {
      e.printStackTrace();
    }
  }
}

