package se.inera.intyg.cts.integrationtest;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.cts.integrationtest.TestData.EMAIL_ADDRESS;
import static se.inera.intyg.cts.integrationtest.TestData.PHONE_NUMBER;

import io.restassured.RestAssured;
import java.time.LocalDateTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class NotificationIT {

  private TestData testData;

  @BeforeEach
  void setUp() {
    RestAssured.baseURI = System.getProperty("integration.tests.baseUrl",
        "http://cts.localtest.me");
    testData = TestData.create();
  }

  @AfterEach
  void tearDown() {
    testData.cleanUp();
    RestAssured.reset();
  }

  @Test
  void shouldSendSmsNotificationToOrganizationRepresentativeWhenUploadedPackage() {
    testData
        .defaultTermination()
        .certificates(50)
        .collectCertificates()
        .certificateTexts(10)
        .collectCertificateTexts()
        .uploadPackage("password")
        .setup();

    given()
        .when()
        .post("/api/v1/exports/sendNotification")
        .then()
        .statusCode(HttpStatus.OK.value());

    final var notificationSentBySMS = getNotificationSentBySMS(PHONE_NUMBER);

    assertTrue(notificationSentBySMS.startsWith("Hej, du har namngivits som ansvarig för att hämta "
        + "ett exportfilspaket"));
  }

  @Test
  void shouldEmailSendNotificationToOrganizationRepresentativeWhenUploadedPackage() {
    testData
        .defaultTermination()
        .certificates(50)
        .collectCertificates()
        .certificateTexts(10)
        .collectCertificateTexts()
        .uploadPackage("password")
        .setup();

    given()
        .when()
        .post("/api/v1/exports/sendNotification")
        .then()
        .statusCode(HttpStatus.OK.value());

    final var notificationSentByEmail = getNotificationSentByEmail(EMAIL_ADDRESS);

    assertTrue(notificationSentByEmail.startsWith("<p>Hej, du har namngivits som ansvarig för att "
        + "hämta ett exportfilspaket"));
  }

  @Test
  void shouldSendSmsReminderToOrganizationRepresentativeAfter14Days() {
    testData
        .terminationWithCreatedTime(LocalDateTime.now().minusDays(15L))
        .certificates(50)
        .collectCertificates()
        .certificateTexts(10)
        .collectCertificateTexts()
        .uploadPackage("password")
        .notificationSent()
        .setup();

    given()
        .when()
        .post("/api/v1/exports/sendReminder")
        .then()
        .statusCode(HttpStatus.OK.value());

    final var reminderSentBySMS = getNotificationSentBySMS(PHONE_NUMBER);

    assertTrue(reminderSentBySMS.startsWith("PÅMINNELSE Du har namngivits som ansvarig för att "
        + "hämta ett exportfilspaket "));
  }

  @Test
  void shouldSendEmailReminderToOrganizationRepresentativeAfter14Days() {
    testData
        .terminationWithCreatedTime(LocalDateTime.now().minusDays(15L))
        .certificates(50)
        .collectCertificates()
        .certificateTexts(10)
        .collectCertificateTexts()
        .uploadPackage("password")
        .notificationSent()
        .setup();

    given()
        .when()
        .post("/api/v1/exports/sendReminder")
        .then()
        .statusCode(HttpStatus.OK.value());

    final var reminderSentByEmail = getNotificationSentByEmail(EMAIL_ADDRESS);

    assertTrue(reminderSentByEmail.startsWith("<p>PÅMINNELSE<br>Du har namngivits som ansvarig för "
        + "att hämta ett exportfilspaket"));
  }

  @Test
  void shouldNotSendEmailReminderToOrganizationRepresentativeBefore14Days() {
    testData
        .terminationWithCreatedTime(LocalDateTime.now().minusDays(13L))
        .certificates(50)
        .collectCertificates()
        .certificateTexts(10)
        .collectCertificateTexts()
        .uploadPackage("password")
        .notificationSent()
        .setup();

    given()
        .when()
        .post("/api/v1/exports/sendReminder")
        .then()
        .statusCode(HttpStatus.OK.value());

    final var reminderSentByEmail = getNotificationSentByEmail(EMAIL_ADDRESS);

    assertEquals("", reminderSentByEmail);
  }

  private String getNotificationSentBySMS(String phoneNumber) {
    return given()
        .baseUri("http://localhost:18000")
        .pathParam("phoneNumber", phoneNumber)
        .when()
        .get("/testability-tellustalk/v1/smsNotifications/{phoneNumber}")
        .then()
        .statusCode(HttpStatus.OK.value())
        .extract()
        .asString();
  }

  private String getNotificationSentByEmail(String emailAddress) {
    return given()
        .baseUri("http://localhost:18000")
        .pathParam("emailAddress", emailAddress)
        .when()
        .get("/testability-tellustalk/v1/emailNotifications/{emailAddress}")
        .then()
        .statusCode(HttpStatus.OK.value())
        .extract()
        .asString();
  }

}
