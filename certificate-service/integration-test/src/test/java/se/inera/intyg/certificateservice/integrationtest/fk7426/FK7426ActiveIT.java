package se.inera.intyg.certificateservice.integrationtest.fk7426;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO.SIGNED;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ALVA_VARDADMINISTRATOR_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ANNA_SJUKSKOTERSKA_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.BERTIL_BARNMORSKA_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataIncomingMessage.incomingComplementDTOBuilder;
import static se.inera.intyg.certificateservice.application.testdata.TestDataIncomingMessage.incomingComplementMessageBuilder;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionPeriodSjukdom.QUESTION_PERIOD_SJUKDOM_FIELD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionPeriodSjukdom.QUESTION_PERIOD_SJUKDOM_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionPeriodSjukdomMotivering.QUESTION_PERIOD_SJUKDOM_MOTIVERING_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionVardasBarnetInneliggandePaSjukhus.QUESTION_VARDAS_BARNET_INNELIGGANDE_PA_SJUKHUS_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7426.elements.QuestionVardasBarnetInskrivetMedHemsjukvard.QUESTION_VARDAS_BARN_INSKRIVET_MED_HEMSJUKVARD_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionGrundForMedicinsktUnderlag.QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7809.elements.QuestionGrundForMedicinsktUnderlag.UTLATANDE_BASERAT_PA_JOURNALUPPGIFTER_FIELD_ID;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.customUpdateCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultGetCertificateMessageRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultRenewCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultSendCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultSignCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.ApiRequestUtil.defaultTestablilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificate;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.certificateId;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.questions;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.updateBooleanValue;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.updateDateListValue;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.updateDateRangeValue;
import static se.inera.intyg.certificateservice.integrationtest.common.util.CertificateUtil.updateTextValue;
import static se.inera.intyg.certificateservice.integrationtest.fk7426.FK7426TestSetup.fk7426TestSetup;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDate;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDateRange;
import se.inera.intyg.certificateservice.integrationtest.common.setup.ActiveCertificatesIT;
import se.inera.intyg.certificateservice.integrationtest.common.setup.BaseTestabilityUtilities;
import se.inera.intyg.certificateservice.integrationtest.common.setup.TestabilityUtilities;
import se.inera.intyg.certificateservice.integrationtest.common.tests.AccessLevelsDeepIntegrationIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.AccessLevelsSVODIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.AdministrativeMessagesIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.AnswerComplementIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.CertificateReadyForSignIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.CertificatesWithQAForCareIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.ComplementIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.ComplementMessagesIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.CreateCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.DeleteCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.ExistsCertificateExternalTypeInfoIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.ExistsCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.ExistsCertificateTypeInfoIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.ForwardCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.GetCertificateEventsIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.GetCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.GetCertificatePdfIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.GetCertificateTypeInfoIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.GetCertificateXmlIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.GetPatientCertificatesIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.GetUnitCertificatesIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.GetUnitCertificatesInfoIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.GetUnitCertificatesWhenSignedIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.InternalApiIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.InternalApiMessagesIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.MessageExistsIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.ObsoleteDraftsIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.RenewCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.RenewExternalCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.ReplaceCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.RevokeCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.SendCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.SendCitizenCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.SignCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.UnitStatisticsIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.UpdateCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.ValidateCertificateIT;

public class FK7426ActiveIT extends ActiveCertificatesIT {

  public static final String TYPE = FK7426TestSetup.TYPE;

  @BeforeEach
  void setUp() {
    super.setUpBaseIT();

    baseTestabilityUtilities = fk7426TestSetup()
        .testabilityUtilities(
            TestabilityUtilities.builder()
                .api(api)
                .internalApi(internalApi)
                .testabilityApi(testabilityApi)
                .build()
        )
        .build();
  }

  @AfterEach
  void tearDown() {
    super.tearDownBaseIT();
  }

  @Nested
  @DisplayName(TYPE + "Utökad behörighet vid djupintegration utan SVOD")
  class AccessLevelsDeepIntegration extends AccessLevelsDeepIntegrationIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(TYPE + "Utökad behörighet vid djupintegration och SVOD (sjf=true)")
  class AccessLevelsSVOD extends AccessLevelsSVODIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(TYPE + "Besvara kompletteringsbegäran med meddelande")
  class AnswerComplement extends AnswerComplementIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(TYPE + "Kompletteringsbegäran")
  class Complement extends ComplementIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }

    @Test
    @DisplayName("Kompletteringsbegäran skall sättas som hanterad när förnyade intyget signeras")
    void shallSetComplementAsHandledWhenRenewingCertificateIsSigned() {
      final var testCertificates = testabilityApi().addCertificates(
          defaultTestablilityCertificateRequest(type(), typeVersion(), SIGNED)
      );

      api().sendCertificate(
          defaultSendCertificateRequest(),
          certificateId(testCertificates)
      );

      api().receiveMessage(
          incomingComplementMessageBuilder()
              .certificateId(certificateId(testCertificates))
              .complements(List.of(incomingComplementDTOBuilder()
                  .questionId(questionId())
                  .build()))
              .build()
      );

      final var renewResponse = api().renewCertificate(
          defaultRenewCertificateRequest(),
          certificateId(testCertificates)
      );

      final var renewingCertificate = certificate(renewResponse.getBody());

      Objects.requireNonNull(renewingCertificate).getData().put(
          QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID.id(),
          updateDateListValue(renewingCertificate, QUESTION_GRUND_FOR_MEDICINSKT_UNDERLAG_ID.id(),
              List.of(
                  CertificateDataValueDate.builder()
                      .id(UTLATANDE_BASERAT_PA_JOURNALUPPGIFTER_FIELD_ID)
                      .date(LocalDate.now())
                      .build()
              )
          )
      );

      Objects.requireNonNull(renewingCertificate).getData().put(
          QUESTION_PERIOD_SJUKDOM_ID.id(),
          updateDateRangeValue(renewingCertificate, QUESTION_PERIOD_SJUKDOM_ID.id(),
              CertificateDataValueDateRange.builder()
                  .id(QUESTION_PERIOD_SJUKDOM_FIELD_ID.value())
                  .to(LocalDate.now().plusDays(7))
                  .from(LocalDate.now())
                  .build()
          )

      );

      Objects.requireNonNull(renewingCertificate).getData().put(
          QUESTION_PERIOD_SJUKDOM_MOTIVERING_ID.id(),
          updateTextValue(renewingCertificate, QUESTION_PERIOD_SJUKDOM_MOTIVERING_ID.id(),
              "text"
          )
      );

      Objects.requireNonNull(renewingCertificate).getData().put(
          QUESTION_VARDAS_BARNET_INNELIGGANDE_PA_SJUKHUS_ID.id(),
          updateBooleanValue(renewingCertificate,
              QUESTION_VARDAS_BARNET_INNELIGGANDE_PA_SJUKHUS_ID.id(),
              false
          )
      );

      Objects.requireNonNull(renewingCertificate).getData().put(
          QUESTION_VARDAS_BARN_INSKRIVET_MED_HEMSJUKVARD_ID.id(),
          updateBooleanValue(renewingCertificate,
              QUESTION_VARDAS_BARN_INSKRIVET_MED_HEMSJUKVARD_ID.id(),
              false
          )
      );

      final var updateResponse = api().updateCertificate(
          customUpdateCertificateRequest()
              .certificate(renewingCertificate)
              .build(),
          certificateId(renewResponse.getBody())
      );

      api().signCertificate(
          defaultSignCertificateRequest(),
          certificateId(renewResponse.getBody()),
          Objects.requireNonNull(certificate(updateResponse.getBody())).getMetadata().getVersion()
      );

      final var messagesForCertificate = api().getMessagesForCertificate(
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
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }

    protected static Stream<Arguments> rolesAccessToProtectedPerson() {
      return Stream.of(
          Arguments.of(AJLA_DOCTOR_DTO)
      );
    }
  }

  @Nested
  @DisplayName(TYPE + "Ta bort utkast")
  class DeleteCertificate extends DeleteCertificateIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(TYPE + "Finns intyget i tjänsten")
  class ExistsCertificate extends ExistsCertificateIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(TYPE + "Aktiva versioner")
  class ExistsCertificateTypeInfo extends ExistsCertificateTypeInfoIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(TYPE + "Hämta intyg")
  class GetCertificate extends GetCertificateIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
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
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }

    protected static Stream<Arguments> rolesNoAccessToProtectedPerson() {
      return Stream.of(
          Arguments.of(ALVA_VARDADMINISTRATOR_DTO)
      );
    }
  }

  @Nested
  @DisplayName(TYPE + "Hämta intygspdf")
  class GetCertificatePdf extends GetCertificatePdfIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }

    protected static Stream<Arguments> rolesNoAccessToProtectedPerson() {
      return Stream.of(
          Arguments.of(ALVA_VARDADMINISTRATOR_DTO)
      );
    }
  }

  @Nested
  @DisplayName(TYPE + "Hämta intygstyp när den är aktiv")
  class GetCertificateTypeInfo extends GetCertificateTypeInfoIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(TYPE + "Hämta intygsxml")
  class GetCertificateXml extends GetCertificateXmlIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }

    protected static Stream<Arguments> rolesNoAccessToProtectedPerson() {
      return Stream.of(
          Arguments.of(ALVA_VARDADMINISTRATOR_DTO)
      );
    }
  }

  @Nested
  @DisplayName(TYPE + "Hämta tidigare intyg för patient")
  class GetPatientCertificates extends GetPatientCertificatesIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
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
  @DisplayName(TYPE + "Hämta ej signerade utkast sökkriterier")
  class GetUnitCertificatesInfo extends GetUnitCertificatesInfoIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(TYPE + "Hämta ej signerade utkast")
  class GetUnitCertificates extends GetUnitCertificatesIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(TYPE + "Hämta signerade intyg")
  class GetUnitCertificatesWhenSigned extends GetUnitCertificatesWhenSignedIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(TYPE + "Intern api")
  class InternalApi extends InternalApiIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(TYPE + "Intern api ärendekommunikation")
  class InternalApiMessages extends InternalApiMessagesIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(TYPE + "Rensning av utkast")
  class ObsoleteDrafts extends ObsoleteDraftsIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }


  @Nested
  @DisplayName(TYPE + "Finns meddelandet i tjänsten")
  class MessagesExists extends MessageExistsIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(TYPE + "Hantering av kompletteringsfrågor för intyg")
  class ComplementMessages extends ComplementMessagesIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
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
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(TYPE + "Ersätta")
  class ReplaceCertificate extends ReplaceCertificateIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(TYPE + "Makulera")
  class RevokeCertificate extends RevokeCertificateIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(TYPE + "Skicka")
  class SendCertificate extends SendCertificateIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(TYPE + "Markera klar för signering")
  class ReadyForSignCertificate extends CertificateReadyForSignIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(TYPE + "Signera")
  class SignCertificate extends SignCertificateIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(TYPE + "Uppdatera svarsalternativ")
  class UpdateCertificate extends UpdateCertificateIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(TYPE + "Validera utkast")
  class ValidateCertificate extends ValidateCertificateIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(TYPE + "Vidarebefodra utkast")
  class ForwardCertificate extends ForwardCertificateIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(TYPE + "ListCertificatesForCareWithQA")
  class IncludeCerificatesWithQA extends CertificatesWithQAForCareIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(TYPE + "Hämta statistik")
  class IncludeUnitStatistics extends UnitStatisticsIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
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
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(TYPE + "Skicka intyg som invånare från 1177 Intyg")
  class IncludeSendCitizenCertificateIT extends SendCitizenCertificateIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }


  @Nested
  @DisplayName(TYPE + "Administrativ ärendekommunikation")
  class AdministrativeMessages extends AdministrativeMessagesIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(TYPE + "Förnya intyg från extern källa")
  class RenewExternalCertificate extends RenewExternalCertificateIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }
}