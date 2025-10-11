package se.inera.intyg.certificateservice.integrationtest.fk7804;

import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ALVA_VARDADMINISTRATOR_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ANNA_SJUKSKOTERSKA_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.BERTIL_BARNMORSKA_DTO;
import static se.inera.intyg.certificateservice.integrationtest.fk7804.FK7804TestSetup.fk7804TestSetup;

import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.provider.Arguments;
import se.inera.intyg.certificateservice.integrationtest.AccessLevelsDeepIntegrationIT;
import se.inera.intyg.certificateservice.integrationtest.AccessLevelsSVODIT;
import se.inera.intyg.certificateservice.integrationtest.AdministrativeMessagesIT;
import se.inera.intyg.certificateservice.integrationtest.AnswerComplementIT;
import se.inera.intyg.certificateservice.integrationtest.BaseTestabilityUtilities;
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
import se.inera.intyg.certificateservice.integrationtest.RenewExternalCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.ReplaceCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.RevokeCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.SendCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.SendCitizenCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.SignCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.TestabilityUtilities;
import se.inera.intyg.certificateservice.integrationtest.UnitStatisticsIT;
import se.inera.intyg.certificateservice.integrationtest.UpdateCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.ValidateCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.ag114.ActiveCertificatesIT;

public class FK7804ActiveIT extends ActiveCertificatesIT {

  public static final String TYPE = FK7804TestSetup.TYPE;

  @BeforeEach
  void setUp() {
    super.setUpBaseIT();

    baseTestabilityUtilities = fk7804TestSetup()
        .testabilityUtilities(
            TestabilityUtilities.builder()
                .api(api)
                .internalApi(internalApi)
                .testabilityApi(testabilityApi)
                .testListener(testListener)
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
  @DisplayName(TYPE + "Hämta intygspdf")
  class GetCertificatePdf extends GetCertificatePdfIT {

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