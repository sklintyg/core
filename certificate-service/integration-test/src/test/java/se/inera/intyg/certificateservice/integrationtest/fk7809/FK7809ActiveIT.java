package se.inera.intyg.certificateservice.integrationtest.fk7809;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO.SIGNED;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ALVA_VARDADMINISTRATOR_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ANNA_SJUKSKOTERSKA_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.BERTIL_BARNMORSKA_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataIncomingMessage.incomingComplementDTOBuilder;
import static se.inera.intyg.certificateservice.application.testdata.TestDataIncomingMessage.incomingComplementMessageBuilder;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionPrognos.QUESTION_PROGNOS_ID;
import static se.inera.intyg.certificateservice.integrationtest.fk7809.FK7809Constants.CODE;
import static se.inera.intyg.certificateservice.integrationtest.fk7809.FK7809Constants.CODE_SYSTEM;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultGetCertificateMessageRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultRenewCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultSendCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultSignCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultTestablilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.certificate;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.certificateId;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.questions;

import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.integrationtest.AccessLevelsDeepIntegrationIT;
import se.inera.intyg.certificateservice.integrationtest.AccessLevelsSVODIT;
import se.inera.intyg.certificateservice.integrationtest.AdministrativeMessagesIT;
import se.inera.intyg.certificateservice.integrationtest.AnswerComplementIT;
import se.inera.intyg.certificateservice.integrationtest.CertificateFromMessageIT;
import se.inera.intyg.certificateservice.integrationtest.CertificateReadyForSignIT;
import se.inera.intyg.certificateservice.integrationtest.CertificatesWithQAForCareIT;
import se.inera.intyg.certificateservice.integrationtest.ComplementIT;
import se.inera.intyg.certificateservice.integrationtest.ComplementMessagesIT;
import se.inera.intyg.certificateservice.integrationtest.CreateCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.DeleteCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.ExistsCertificateExternalTypeInfoIT;
import se.inera.intyg.certificateservice.integrationtest.ExistsCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.ExistsCertificateTypeInfoIT;
import se.inera.intyg.certificateservice.integrationtest.ForwardCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.ForwardCertificateMessageIT;
import se.inera.intyg.certificateservice.integrationtest.GetCertificateEventsIT;
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
import se.inera.intyg.certificateservice.integrationtest.RenewCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.ReplaceCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.RevokeCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.SendCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.SendCitizenCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.SignCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.UnitStatisticsIT;
import se.inera.intyg.certificateservice.integrationtest.UpdateCertificateFK7809IT;
import se.inera.intyg.certificateservice.integrationtest.UpdateCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.ValidateCertificateIT;

public class FK7809ActiveIT {

  private static final String CERTIFICATE_TYPE = FK7809Constants.FK7809;
  private static final String ACTIVE_VERSION = FK7809Constants.VERSION;
  private static final String WRONG_VERSION = FK7809Constants.WRONG_VERSION;
  private static final String TYPE = FK7809Constants.TYPE;
  private static final ElementId ELEMENT_ID = QUESTION_PROGNOS_ID;
  private static final String VALUE = "Svarstext för prognos.";
  private static final String RECIPIENT = "FKASSA";

  @DynamicPropertySource
  static void testProperties(DynamicPropertyRegistry registry) {
    registry.add("certificate.model.fk7809.v1_0.active.from", () -> "2024-01-01T00:00:00");
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
  @DisplayName(TYPE + "Administrativ ärendekommunikation")
  class AdministrativeMessages extends AdministrativeMessagesIT {

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

      api.signCertificate(
          defaultSignCertificateRequest(),
          certificateId(renewResponse.getBody()),
          Objects.requireNonNull(renewingCertificate).getMetadata().getVersion()
      );

      final var messagesForCertificate = api.getMessagesForCertificate(
          defaultGetCertificateMessageRequest(),
          certificateId(testCertificates)
      );

      assertTrue(questions(messagesForCertificate.getBody()).getFirst().isHandled(),
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

    protected static Stream<Arguments> rolesAccessToProtectedPerson() {
      return Stream.of(
          Arguments.of(AJLA_DOCTOR_DTO)
      );
    }
  }

  @Nested
  @DisplayName(TYPE + " Ta bort utkast")
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

    protected static Stream<Arguments> rolesNoAccessToProtectedPerson() {
      return Stream.of(
          Arguments.of(ALVA_VARDADMINISTRATOR_DTO),
          Arguments.of(BERTIL_BARNMORSKA_DTO),
          Arguments.of(ANNA_SJUKSKOTERSKA_DTO)
      );
    }

    protected static Stream<Arguments> rolesAccessToProtectedPerson() {
      return Stream.of(
          Arguments.of(AJLA_DOCTOR_DTO)
      );
    }
  }

  @Nested
  @DisplayName(TYPE + "Hämta intygets händelser")
  class GetCertificateEvents extends GetCertificateEventsIT {

    @Override
    protected boolean isAvailableForPatient() {
      return true;
    }

    @Override
    protected String type() {
      return CERTIFICATE_TYPE;
    }

    @Override
    protected String typeVersion() {
      return ACTIVE_VERSION;
    }

    protected static Stream<Arguments> rolesNoAccessToProtectedPerson() {
      return Stream.of(
          Arguments.of(ALVA_VARDADMINISTRATOR_DTO),
          Arguments.of(BERTIL_BARNMORSKA_DTO),
          Arguments.of(ANNA_SJUKSKOTERSKA_DTO)
      );
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

    protected static Stream<Arguments> rolesNoAccessToProtectedPerson() {
      return Stream.of(
          Arguments.of(ALVA_VARDADMINISTRATOR_DTO),
          Arguments.of(BERTIL_BARNMORSKA_DTO),
          Arguments.of(ANNA_SJUKSKOTERSKA_DTO)
      );
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

    protected static Stream<Arguments> rolesNoAccessToProtectedPerson() {
      return Stream.of(
          Arguments.of(ALVA_VARDADMINISTRATOR_DTO),
          Arguments.of(BERTIL_BARNMORSKA_DTO),
          Arguments.of(ANNA_SJUKSKOTERSKA_DTO)
      );
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

    protected static Stream<Arguments> rolesNoAccessToProtectedPerson() {
      return Stream.of(
          Arguments.of(ALVA_VARDADMINISTRATOR_DTO),
          Arguments.of(BERTIL_BARNMORSKA_DTO),
          Arguments.of(ANNA_SJUKSKOTERSKA_DTO)
      );
    }

    protected static Stream<Arguments> rolesAccessToProtectedPerson() {
      return Stream.of(
          Arguments.of(AJLA_DOCTOR_DTO)
      );
    }
  }

  @Nested
  @DisplayName(TYPE + " Hämta ej signerade utkast sökkriterier")
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
  class MessageExists extends MessageExistsIT {

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
  class ComplementMessages extends ComplementMessagesIT {

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

    protected static Stream<Arguments> rolesNoAccessToProtectedPerson() {
      return Stream.of(
          Arguments.of(ALVA_VARDADMINISTRATOR_DTO),
          Arguments.of(BERTIL_BARNMORSKA_DTO),
          Arguments.of(ANNA_SJUKSKOTERSKA_DTO)
      );
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
    protected String recipient() {
      return RECIPIENT;
    }

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
  @DisplayName(TYPE + "Markera klar för signering")
  class ReadyForSignCertificate extends CertificateReadyForSignIT {

    @Override
    protected String type() {
      return CERTIFICATE_TYPE;
    }

    @Override
    protected String typeVersion() {
      return ACTIVE_VERSION;
    }

    @Override
    protected boolean nurseCanMarkReadyForSignCertificate() {
      return true;
    }

    @Override
    protected boolean midwifeCanMarkReadyForSignCertificate() {
      return true;
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
    class UpdateCertificateFK7809 extends UpdateCertificateFK7809IT {

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

    @Override
    protected boolean nurseCanForwardCertificate() {
      return true;
    }

    @Override
    protected boolean midwifeCanForwardCertificate() {
      return true;
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
  }

  @Nested
  @DisplayName(TYPE + "Hämta statistik")
  class IncludeUnitStatistics extends UnitStatisticsIT {

    @Override
    protected String type() {
      return CERTIFICATE_TYPE;
    }

    @Override
    protected String typeVersion() {
      return ACTIVE_VERSION;
    }

    @Override
    protected Boolean canRecieveQuestions() {
      return true;
    }

    protected static Stream<Arguments> rolesNoAccessToProtectedPerson() {
      return Stream.of(
          Arguments.of(ALVA_VARDADMINISTRATOR_DTO),
          Arguments.of(BERTIL_BARNMORSKA_DTO),
          Arguments.of(ANNA_SJUKSKOTERSKA_DTO)
      );
    }

    protected static Stream<Arguments> rolesAccessToProtectedPerson() {
      return Stream.of(
          Arguments.of(AJLA_DOCTOR_DTO)
      );
    }
  }


  @Nested
  @DisplayName(TYPE + "Aktiva versioner utifrån intygstyp och kodsystem")
  class IncludeExistsCertificateExternalTypeInfo extends ExistsCertificateExternalTypeInfoIT {


    @Override
    protected String type() {
      return CERTIFICATE_TYPE;
    }

    @Override
    protected String typeVersion() {
      return ACTIVE_VERSION;
    }

    @Override
    protected String codeSystem() {
      return CODE_SYSTEM;
    }

    @Override
    protected String code() {
      return CODE;
    }
  }

  @Nested
  @DisplayName(TYPE + "Skicka intyg som invånare från 1177 Intyg")
  class IncludeSendCitizenCertificateIT extends SendCitizenCertificateIT {


    @Override
    protected String type() {
      return CERTIFICATE_TYPE;
    }

    @Override
    protected String typeVersion() {
      return ACTIVE_VERSION;
    }

    @Override
    protected boolean availableForCitizen() {
      return true;
    }
  }
}