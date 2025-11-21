package se.inera.intyg.certificateservice.integrationtest.ts8071.v2;

import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ALVA_VARDADMINISTRATOR_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ANNA_SJUKSKOTERSKA_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.BERTIL_BARNMORSKA_DTO;
import static se.inera.intyg.certificateservice.integrationtest.ts8071.v2.TS8071V2TestSetup.ACTIVE_CERTIFICATE_TYPE_VERSION;
import static se.inera.intyg.certificateservice.integrationtest.ts8071.v2.TS8071V2TestSetup.ts8071V2TestSetup;

import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.provider.Arguments;
import se.inera.intyg.certificateservice.integrationtest.common.setup.ActiveCertificatesIT;
import se.inera.intyg.certificateservice.integrationtest.common.setup.BaseTestabilityUtilities;
import se.inera.intyg.certificateservice.integrationtest.common.setup.TestabilityUtilities;
import se.inera.intyg.certificateservice.integrationtest.common.tests.AccessLevelsDeepIntegrationIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.AccessLevelsSVODIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.CertificateReadyForSignIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.CertificatesWithQAForCareIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.CreateCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.DeleteCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.ExistsCertificateExternalTypeInfoIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.ExistsCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.ExistsCertificateTypeInfoIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.ForwardCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.GetCertificateEventsIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.GetCertificateGeneralPdfIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.GetCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.GetCertificateTypeInfoIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.GetCertificateXmlIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.GetPatientCertificatesIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.GetUnitCertificatesIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.GetUnitCertificatesInfoIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.GetUnitCertificatesWhenSignedIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.InternalApiIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.MessagingNotAvailableIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.ObsoleteDraftsIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.RenewExternalCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.RenewNotAvailableIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.ReplaceCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.RevokeCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.SendCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.SendCitizenCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.SignCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.UnitStatisticsIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.UpdateCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.ValidateCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.ts8071.v1.TS8071TestSetup;

public class TS8071V2ActiveIT extends ActiveCertificatesIT {

  public static final String TYPE = TS8071TestSetup.TYPE;

  @BeforeEach
  void setUp() {
    super.setUpBaseIT();

    baseTestabilityUtilities = ts8071V2TestSetup()
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
  @DisplayName(TYPE + ACTIVE_CERTIFICATE_TYPE_VERSION + " "
      + "Utökad behörighet vid djupintegration utan SVOD")
  class AccessLevelsDeepIntegration extends AccessLevelsDeepIntegrationIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(TYPE + ACTIVE_CERTIFICATE_TYPE_VERSION
      + "Utökad behörighet vid djupintegration och SVOD (sjf=true)")
  class AccessLevelsSVOD extends AccessLevelsSVODIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(TYPE + ACTIVE_CERTIFICATE_TYPE_VERSION + " " + "Hämta generell intygspdf")
  class GetCertificateGeneralPdf extends GetCertificateGeneralPdfIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }

    protected static Stream<Arguments> rolesNoAccessToProtectedPerson() {
      return Stream.of(
          Arguments.of(ALVA_VARDADMINISTRATOR_DTO, BERTIL_BARNMORSKA_DTO, ANNA_SJUKSKOTERSKA_DTO)
      );
    }
  }

  @Nested
  @DisplayName(TYPE + ACTIVE_CERTIFICATE_TYPE_VERSION + " " + "Skapa utkast")
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
  @DisplayName(TYPE + ACTIVE_CERTIFICATE_TYPE_VERSION + " " + "Ta bort utkast")
  class DeleteCertificate extends DeleteCertificateIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(TYPE + ACTIVE_CERTIFICATE_TYPE_VERSION + " " + "Finns intyget i tjänsten")
  class ExistsCertificate extends ExistsCertificateIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(TYPE + ACTIVE_CERTIFICATE_TYPE_VERSION + " " + "Aktiva versioner")
  class ExistsCertificateTypeInfo extends ExistsCertificateTypeInfoIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(TYPE + ACTIVE_CERTIFICATE_TYPE_VERSION + " " + "Hämta intyg")
  class GetCertificate extends GetCertificateIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }

    protected static Stream<Arguments> rolesNoAccessToProtectedPerson() {
      return Stream.of(
          Arguments.of(ALVA_VARDADMINISTRATOR_DTO),
          Arguments.of(ANNA_SJUKSKOTERSKA_DTO),
          Arguments.of(BERTIL_BARNMORSKA_DTO)
      );
    }

    protected static Stream<Arguments> rolesAccessToProtectedPerson() {
      return Stream.of(
          Arguments.of(AJLA_DOCTOR_DTO)
      );
    }
  }

  @Nested
  @DisplayName(TYPE + ACTIVE_CERTIFICATE_TYPE_VERSION + " " + "Hämta intygets händelser")
  class GetCertificateEvents extends GetCertificateEventsIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }

    protected static Stream<Arguments> rolesNoAccessToProtectedPerson() {
      return Stream.of(
          Arguments.of(ALVA_VARDADMINISTRATOR_DTO),
          Arguments.of(ANNA_SJUKSKOTERSKA_DTO),
          Arguments.of(BERTIL_BARNMORSKA_DTO)
      );
    }
  }

  @Nested
  @DisplayName(TYPE + ACTIVE_CERTIFICATE_TYPE_VERSION + " " + "Hämta intygstyp när den är aktiv")
  class GetCertificateTypeInfo extends GetCertificateTypeInfoIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(TYPE + ACTIVE_CERTIFICATE_TYPE_VERSION + " " + "Hämta intygsxml")
  class GetCertificateXml extends GetCertificateXmlIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }

    protected static Stream<Arguments> rolesNoAccessToProtectedPerson() {
      return Stream.of(
          Arguments.of(ALVA_VARDADMINISTRATOR_DTO),
          Arguments.of(ANNA_SJUKSKOTERSKA_DTO),
          Arguments.of(BERTIL_BARNMORSKA_DTO)
      );
    }
  }

  @Nested
  @DisplayName(TYPE + ACTIVE_CERTIFICATE_TYPE_VERSION + " " + "Hämta tidigare intyg för patient")
  class GetPatientCertificates extends GetPatientCertificatesIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }

    protected static Stream<Arguments> rolesNoAccessToProtectedPerson() {
      return Stream.of(
          Arguments.of(ALVA_VARDADMINISTRATOR_DTO),
          Arguments.of(ANNA_SJUKSKOTERSKA_DTO),
          Arguments.of(BERTIL_BARNMORSKA_DTO)
      );
    }

    protected static Stream<Arguments> rolesAccessToProtectedPerson() {
      return Stream.of(
          Arguments.of(AJLA_DOCTOR_DTO)
      );
    }
  }

  @Nested
  @DisplayName(
      TYPE + ACTIVE_CERTIFICATE_TYPE_VERSION + " " + "Hämta ej signerade utkast sökkriterier")
  class GetUnitCertificatesInfo extends GetUnitCertificatesInfoIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(TYPE + ACTIVE_CERTIFICATE_TYPE_VERSION + " " + "Hämta ej signerade utkast")
  class GetUnitCertificates extends GetUnitCertificatesIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(TYPE + ACTIVE_CERTIFICATE_TYPE_VERSION + " " + "Hämta signerade intyg")
  class GetUnitCertificatesWhenSigned extends GetUnitCertificatesWhenSignedIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(TYPE + ACTIVE_CERTIFICATE_TYPE_VERSION + " " + "Intern api")
  class InternalApi extends InternalApiIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(TYPE + ACTIVE_CERTIFICATE_TYPE_VERSION + " " + "Rensning av utkast")
  class ObsoleteDrafts extends ObsoleteDraftsIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }


  @Nested
  @DisplayName(TYPE + ACTIVE_CERTIFICATE_TYPE_VERSION
      + "Ärendekommunikation skall ej vara tillgänglig")
  class MessagingNotAvailable extends MessagingNotAvailableIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(TYPE + ACTIVE_CERTIFICATE_TYPE_VERSION + " " + "Förnya skall ej vara tillgänglig")
  class RenewNotAvailable extends RenewNotAvailableIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(TYPE + ACTIVE_CERTIFICATE_TYPE_VERSION + " " + "Ersätta")
  class ReplaceCertificate extends ReplaceCertificateIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(TYPE + ACTIVE_CERTIFICATE_TYPE_VERSION + " " + "Makulera")
  class RevokeCertificate extends RevokeCertificateIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(TYPE + ACTIVE_CERTIFICATE_TYPE_VERSION + " " + "Skicka")
  class SendCertificate extends SendCertificateIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(TYPE + ACTIVE_CERTIFICATE_TYPE_VERSION + " " + "Markera klar för signering")
  class ReadyForSignCertificate extends CertificateReadyForSignIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(TYPE + ACTIVE_CERTIFICATE_TYPE_VERSION + " " + "Signera")
  class SignCertificate extends SignCertificateIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(TYPE + ACTIVE_CERTIFICATE_TYPE_VERSION + " " + "Uppdatera svarsalternativ")
  class UpdateCertificate extends UpdateCertificateIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(TYPE + ACTIVE_CERTIFICATE_TYPE_VERSION + " " + "Validera utkast")
  class ValidateCertificate extends ValidateCertificateIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(TYPE + ACTIVE_CERTIFICATE_TYPE_VERSION + " " + "Vidarebefodra utkast")
  class ForwardCertificate extends ForwardCertificateIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(TYPE + ACTIVE_CERTIFICATE_TYPE_VERSION + " " + "ListCertificatesForCareWithQA")
  class IncludeCerificatesWithQA extends CertificatesWithQAForCareIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(TYPE + ACTIVE_CERTIFICATE_TYPE_VERSION + " " + "Hämta statistik")
  class IncludeUnitStatistics extends UnitStatisticsIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }

    protected static Stream<Arguments> rolesNoAccessToProtectedPerson() {
      return Stream.of(
          Arguments.of(ALVA_VARDADMINISTRATOR_DTO),
          Arguments.of(ANNA_SJUKSKOTERSKA_DTO),
          Arguments.of(BERTIL_BARNMORSKA_DTO)
      );
    }

    protected static Stream<Arguments> rolesAccessToProtectedPerson() {
      return Stream.of(
          Arguments.of(AJLA_DOCTOR_DTO)
      );
    }
  }

  @Nested
  @DisplayName(TYPE + ACTIVE_CERTIFICATE_TYPE_VERSION
      + "Aktiva versioner utifrån intygstyp och kodsystem")
  class IncludeExistsCertificateExternalTypeInfo extends ExistsCertificateExternalTypeInfoIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(
      TYPE + ACTIVE_CERTIFICATE_TYPE_VERSION + " " + "Skicka intyg som invånare från 1177 Intyg")
  class IncludeSendCitizenCertificateIT extends SendCitizenCertificateIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(TYPE + ACTIVE_CERTIFICATE_TYPE_VERSION + " " + "Förnya intyg från extern källa")
  class RenewExternalCertificate extends RenewExternalCertificateIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }
}