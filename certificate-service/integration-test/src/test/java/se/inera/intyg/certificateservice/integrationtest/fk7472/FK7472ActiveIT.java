package se.inera.intyg.certificateservice.integrationtest.fk7472;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO.SIGNED;
import static se.inera.intyg.certificateservice.application.testdata.TestDataIncomingMessage.incomingComplementDTOBuilder;
import static se.inera.intyg.certificateservice.application.testdata.TestDataIncomingMessage.incomingComplementMessageBuilder;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472.CertificateModelFactoryFK7472.QUESTION_PERIOD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472.CertificateModelFactoryFK7472.QUESTION_SYMPTOM_ID;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customUpdateCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultGetCertificateMessageRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultRenewCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultSendCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultSignCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultTestablilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.certificate;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.certificateId;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.questions;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.updateDateRangeListValue;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDateRange;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7472.CodeSystemKvFkmu0008;
import se.inera.intyg.certificateservice.integrationtest.AccessLevelsDeepIntegrationIT;
import se.inera.intyg.certificateservice.integrationtest.AccessLevelsSVODIT;
import se.inera.intyg.certificateservice.integrationtest.AnswerComplementIT;
import se.inera.intyg.certificateservice.integrationtest.CertificateFromMessageIT;
import se.inera.intyg.certificateservice.integrationtest.CertificatesWithQAForCareIT;
import se.inera.intyg.certificateservice.integrationtest.ComplementIT;
import se.inera.intyg.certificateservice.integrationtest.CreateCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.DeleteCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.ExistsCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.ExistsCertificateTypeInfoIT;
import se.inera.intyg.certificateservice.integrationtest.ForwardCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.ForwardCertificateMessageIT;
import se.inera.intyg.certificateservice.integrationtest.GetCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.GetCertificatePdfIT;
import se.inera.intyg.certificateservice.integrationtest.GetCertificateTypeInfoIT;
import se.inera.intyg.certificateservice.integrationtest.GetCertificateXmlIT;
import se.inera.intyg.certificateservice.integrationtest.GetPatientCertificatesIT;
import se.inera.intyg.certificateservice.integrationtest.GetUnitCertificatesIT;
import se.inera.intyg.certificateservice.integrationtest.GetUnitCertificatesInfoIT;
import se.inera.intyg.certificateservice.integrationtest.GetUnitCertificatesWhenSignedIT;
import se.inera.intyg.certificateservice.integrationtest.InternalApiIT;
import se.inera.intyg.certificateservice.integrationtest.InternalApiMessagesIT;
import se.inera.intyg.certificateservice.integrationtest.MessageExistsIT;
import se.inera.intyg.certificateservice.integrationtest.MessagesIT;
import se.inera.intyg.certificateservice.integrationtest.RenewCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.ReplaceCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.ResponsibleIssuerIT;
import se.inera.intyg.certificateservice.integrationtest.RevokeCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.SendCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.SignCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.SignCertificateNurseMidwifeIT;
import se.inera.intyg.certificateservice.integrationtest.UpdateCertificateFK7472IT;
import se.inera.intyg.certificateservice.integrationtest.UpdateCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.ValidateCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.ValidateFK7472IT;

public class FK7472ActiveIT {

  private static final String CERTIFICATE_TYPE = FK7472Constants.FK7472;
  private static final String ACTIVE_VERSION = FK7472Constants.VERSION;
  private static final String WRONG_VERSION = FK7472Constants.WRONG_VERSION;
  private static final String TYPE = FK7472Constants.TYPE;
  private static final String VALUE = "Svarstext för symptom.";
  private static final ElementId ELEMENT_ID = QUESTION_SYMPTOM_ID;


  @DynamicPropertySource
  static void testProperties(DynamicPropertyRegistry registry) {
    registry.add("certificate.model.fk7472.v1_0.active.from", () -> "2024-01-01T00:00:00");
  }

  @Nested
  @DisplayName(TYPE + "Utökad behörighet vid djupintegration utan SVOD")
  class AccessLevelsDeepIntegration extends AccessLevelsDeepIntegrationIT {

    @Override
    protected String type() {
      return CERTIFICATE_TYPE;
    }

    @Override
    protected String typeVersion() {
      return ACTIVE_VERSION;
    }
  }

  @Nested
  @DisplayName(TYPE + "Utökad behörighet vid djupintegration och SVOD (sjf=true)")
  class AccessLevelsSVOD extends AccessLevelsSVODIT {

    @Override
    protected String type() {
      return CERTIFICATE_TYPE;
    }

    @Override
    protected String typeVersion() {
      return ACTIVE_VERSION;
    }
  }

  @Nested
  @DisplayName(TYPE + "Besvara kompletteringsbegäran med meddelande")
  class AnswerComplement extends AnswerComplementIT {

    @Override
    protected String type() {
      return CERTIFICATE_TYPE;
    }

    @Override
    protected String typeVersion() {
      return ACTIVE_VERSION;
    }

    @Override
    protected String questionId() {
      return ELEMENT_ID.id();
    }
  }

  @Nested
  @DisplayName(TYPE + "Hämta intyg utifrån id från meddelande")
  class CertificateFromMessage extends CertificateFromMessageIT {

    @Override
    protected String type() {
      return CERTIFICATE_TYPE;
    }

    @Override
    protected String typeVersion() {
      return ACTIVE_VERSION;
    }

    @Override
    protected String questionId() {
      return ELEMENT_ID.id();
    }
  }

  @Nested
  @DisplayName(TYPE + "Kompletteringsbegäran")
  class Complement extends ComplementIT {

    @Override
    protected String type() {
      return CERTIFICATE_TYPE;
    }

    @Override
    protected String typeVersion() {
      return ACTIVE_VERSION;
    }

    @Override
    protected String questionId() {
      return ELEMENT_ID.id();
    }

    @Test
    @DisplayName("Kompletteringsbegäran skall sättas som hanterad när förnyade intyget signeras")
    void shallSetComplementAsHandledWhenRenewingCertificateIsSigned() {
      final var testCertificates = testabilityApi.addCertificates(
          defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
      );

      api.sendCertificate(
          defaultSendCertificateRequest(),
          certificateId(testCertificates)
      );

      api.receiveMessage(
          incomingComplementMessageBuilder()
              .certificateId(certificateId(testCertificates))
              .complements(List.of(incomingComplementDTOBuilder()
                  .questionId(questionId())
                  .build()))
              .build()
      );

      final var renewResponse = api.renewCertificate(
          defaultRenewCertificateRequest(),
          certificateId(testCertificates)
      );

      final var renewingCertificate = certificate(renewResponse.getBody());

      Objects.requireNonNull(renewingCertificate).getData().put(
          QUESTION_PERIOD_ID.id(),
          updateDateRangeListValue(renewingCertificate, QUESTION_PERIOD_ID.id(),
              List.of(
                  CertificateDataValueDateRange.builder()
                      .id(CodeSystemKvFkmu0008.HALVA.code())
                      .to(LocalDate.now())
                      .from(LocalDate.now().minusDays(10))
                      .build()
              )
          )
      );

      final var updateResponse = api.updateCertificate(
          customUpdateCertificateRequest()
              .certificate(renewingCertificate)
              .build(),
          certificateId(renewResponse.getBody())
      );

      api.signCertificate(
          defaultSignCertificateRequest(),
          certificateId(renewResponse.getBody()),
          Objects.requireNonNull(certificate(updateResponse.getBody())).getMetadata().getVersion()
      );

      final var messagesForCertificate = api.getMessagesForCertificate(
          defaultGetCertificateMessageRequest(),
          certificateId(testCertificates)
      );

      assertTrue(questions(messagesForCertificate.getBody()).get(0).isHandled(),
          "Expected that complement message was handled, but it was not!"
      );
    }
  }

  @Nested
  @DisplayName(TYPE + "Skapa utkast")
  class CreateCertificate extends CreateCertificateIT {

    @Override
    protected String type() {
      return CERTIFICATE_TYPE;
    }

    @Override
    protected String typeVersion() {
      return ACTIVE_VERSION;
    }

    @Override
    protected String wrongVersion() {
      return WRONG_VERSION;
    }
  }

  @Nested
  @DisplayName(TYPE + "Ta bort utkast")
  class DeleteCertificate extends DeleteCertificateIT {

    @Override
    protected String type() {
      return CERTIFICATE_TYPE;
    }

    @Override
    protected String typeVersion() {
      return ACTIVE_VERSION;
    }
  }

  @Nested
  @DisplayName(TYPE + "Finns intyget i tjänsten")
  class ExistsCertificate extends ExistsCertificateIT {

    @Override
    protected String type() {
      return CERTIFICATE_TYPE;
    }

    @Override
    protected String typeVersion() {
      return ACTIVE_VERSION;
    }
  }

  @Nested
  @DisplayName(TYPE + "Aktiva versioner")
  class ExistsCertificateTypeInfo extends ExistsCertificateTypeInfoIT {

    @Override
    protected String type() {
      return CERTIFICATE_TYPE;
    }

    @Override
    protected String typeVersion() {
      return ACTIVE_VERSION;
    }
  }

  @Nested
  @DisplayName(TYPE + "Hämta intyg")
  class GetCertificate extends GetCertificateIT {

    @Override
    protected String type() {
      return CERTIFICATE_TYPE;
    }

    @Override
    protected String typeVersion() {
      return ACTIVE_VERSION;
    }
  }

  @Nested
  @DisplayName(TYPE + "Hämta intygspdf")
  class GetCertificatePdf extends GetCertificatePdfIT {

    @Override
    protected String type() {
      return CERTIFICATE_TYPE;
    }

    @Override
    protected String typeVersion() {
      return ACTIVE_VERSION;
    }
  }

  @Nested
  @DisplayName(TYPE + "Hämta intygstyp när den är aktiv")
  class GetCertificateTypeInfo extends GetCertificateTypeInfoIT {

    @Override
    protected String type() {
      return CERTIFICATE_TYPE;
    }
  }

  @Nested
  @DisplayName(TYPE + "Hämta intygsxml")
  class GetCertificateXml extends GetCertificateXmlIT {

    @Override
    protected String type() {
      return CERTIFICATE_TYPE;
    }

    @Override
    protected String typeVersion() {
      return ACTIVE_VERSION;
    }
  }

  @Nested
  @DisplayName(TYPE + "Hämta tidigare intyg för patient")
  class GetPatientCertificates extends GetPatientCertificatesIT {

    @Override
    protected String type() {
      return CERTIFICATE_TYPE;
    }

    @Override
    protected String typeVersion() {
      return ACTIVE_VERSION;
    }
  }

  @Nested
  @DisplayName(TYPE + "Hämta ej signerade utkast sökkriterier")
  class GetUnitCertificatesInfo extends GetUnitCertificatesInfoIT {

    @Override
    protected String type() {
      return CERTIFICATE_TYPE;
    }

    @Override
    protected String typeVersion() {
      return ACTIVE_VERSION;
    }
  }

  @Nested
  @DisplayName(TYPE + "Hämta ej signerade utkast")
  class GetUnitCertificates extends GetUnitCertificatesIT {

    @Override
    protected String type() {
      return CERTIFICATE_TYPE;
    }

    @Override
    protected String typeVersion() {
      return ACTIVE_VERSION;
    }

    @Override
    protected ElementId element() {
      return ELEMENT_ID;
    }

    @Override
    protected Object value() {
      return VALUE;
    }
  }

  @Nested
  @DisplayName(TYPE + "Hämta signerade intyg")
  class GetUnitCertificatesWhenSigned extends GetUnitCertificatesWhenSignedIT {

    @Override
    protected String type() {
      return CERTIFICATE_TYPE;
    }

    @Override
    protected String typeVersion() {
      return ACTIVE_VERSION;
    }
  }

  @Nested
  @DisplayName(TYPE + "Intern api")
  class InternalApi extends InternalApiIT {

    @Override
    protected String type() {
      return CERTIFICATE_TYPE;
    }

    @Override
    protected String typeVersion() {
      return ACTIVE_VERSION;
    }
  }

  @Nested
  @DisplayName(TYPE + "Intern api ärendekommunikation")
  class InternalApiMessages extends InternalApiMessagesIT {

    @Override
    protected String type() {
      return CERTIFICATE_TYPE;
    }

    @Override
    protected String typeVersion() {
      return ACTIVE_VERSION;
    }

    @Override
    protected String questionId() {
      return ELEMENT_ID.id();
    }
  }

  @Nested
  @DisplayName(TYPE + "Finns meddelandet i tjänsten")
  class MessagesExists extends MessageExistsIT {

    @Override
    protected String type() {
      return CERTIFICATE_TYPE;
    }

    @Override
    protected String typeVersion() {
      return ACTIVE_VERSION;
    }

    @Override
    protected String questionId() {
      return ELEMENT_ID.id();
    }
  }

  @Nested
  @DisplayName(TYPE + "Hantering av frågor för intyg")
  class Messages extends MessagesIT {

    @Override
    protected String type() {
      return CERTIFICATE_TYPE;
    }

    @Override
    protected String typeVersion() {
      return ACTIVE_VERSION;
    }

    @Override
    protected String questionId() {
      return ELEMENT_ID.id();
    }
  }

  @Nested
  @DisplayName(TYPE + "Förnya")
  class RenewCertificate extends RenewCertificateIT {

    @Override
    protected String type() {
      return CERTIFICATE_TYPE;
    }

    @Override
    protected String typeVersion() {
      return ACTIVE_VERSION;
    }
  }

  @Nested
  @DisplayName(TYPE + "Ersätta")
  class ReplaceCertificate extends ReplaceCertificateIT {

    @Override
    protected String type() {
      return CERTIFICATE_TYPE;
    }

    @Override
    protected String typeVersion() {
      return ACTIVE_VERSION;
    }
  }

  @Nested
  @DisplayName(TYPE + "Makulera")
  class RevokeCertificate extends RevokeCertificateIT {

    @Override
    protected String type() {
      return CERTIFICATE_TYPE;
    }

    @Override
    protected String typeVersion() {
      return ACTIVE_VERSION;
    }
  }

  @Nested
  @DisplayName(TYPE + "Skicka")
  class SendCertificate extends SendCertificateIT {

    @Override
    protected String type() {
      return CERTIFICATE_TYPE;
    }

    @Override
    protected String typeVersion() {
      return ACTIVE_VERSION;
    }
  }

  @Nested
  @DisplayName(TYPE + "Signera")
  class SignCertificate extends SignCertificateIT {

    @Override
    protected String type() {
      return CERTIFICATE_TYPE;
    }

    @Override
    protected String typeVersion() {
      return ACTIVE_VERSION;
    }
  }

  @Nested
  @DisplayName(TYPE + "Signera som sjuksköterska och barnmorska")
  class SignCertificateNurseMidwife extends SignCertificateNurseMidwifeIT {

    @Override
    protected String type() {
      return CERTIFICATE_TYPE;
    }

    @Override
    protected String typeVersion() {
      return ACTIVE_VERSION;
    }
  }

  @Nested
  @DisplayName(TYPE + "Uppdatera svarsalternativ")
  class UpdateCertificate extends UpdateCertificateIT {

    @Override
    protected String type() {
      return CERTIFICATE_TYPE;
    }

    @Override
    protected String typeVersion() {
      return ACTIVE_VERSION;
    }

    @Override
    protected ElementId element() {
      return ELEMENT_ID;
    }

    @Override
    protected Object value() {
      return VALUE;
    }

    @Nested
    @DisplayName(TYPE + "Uppdatera svar")
    class UpdateCertificateFK7472 extends UpdateCertificateFK7472IT {

    }
  }

  @Nested
  @DisplayName(TYPE + "Validera utkast")
  class ValidateCertificate extends ValidateCertificateIT {

    @Override
    protected String type() {
      return CERTIFICATE_TYPE;
    }

    @Override
    protected String typeVersion() {
      return ACTIVE_VERSION;
    }

    @Override
    protected ElementId element() {
      return ELEMENT_ID;
    }

    @Override
    protected Object value() {
      return VALUE;
    }

    @Nested
    @DisplayName(TYPE + "Validera")
    class ValidateFK7472 extends ValidateFK7472IT {

    }
  }

  @Nested
  @DisplayName(TYPE + "Vidarebefodra utkast")
  class ForwardCertificate extends ForwardCertificateIT {

    @Override
    protected String type() {
      return CERTIFICATE_TYPE;
    }

    @Override
    protected String typeVersion() {
      return ACTIVE_VERSION;
    }
  }

  @Nested
  @DisplayName(TYPE + "Vidarebefodra utkast med ärendekommunikation")
  class ForwardCertificateMessage extends ForwardCertificateMessageIT {

    @Override
    protected String type() {
      return CERTIFICATE_TYPE;
    }

    @Override
    protected String typeVersion() {
      return ACTIVE_VERSION;
    }

    @Override
    protected String questionId() {
      return ELEMENT_ID.id();
    }
  }

  @Nested
  @DisplayName(TYPE + "Ansvarig intygsutfärdare")
  class ResponsibleIssuer extends ResponsibleIssuerIT {

    @Override
    protected String type() {
      return CERTIFICATE_TYPE;
    }

    @Override
    protected String typeVersion() {
      return ACTIVE_VERSION;
    }
  }

  @Nested
  @DisplayName(TYPE + "ListCertificatesForCareWithQA")
  class IncludeCerificatesWithQA extends CertificatesWithQAForCareIT {

    @Override
    protected String type() {
      return CERTIFICATE_TYPE;
    }

    @Override
    protected String typeVersion() {
      return ACTIVE_VERSION;
    }

    @Override
    protected boolean canRecieveQuestions() {
      return false;
    }
  }
}
