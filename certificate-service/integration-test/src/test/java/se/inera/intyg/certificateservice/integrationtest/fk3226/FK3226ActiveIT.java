package se.inera.intyg.certificateservice.integrationtest.fk3226;


import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ALVA_VARDADMINISTRATOR_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ANNA_SJUKSKOTERSKA_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.BERTIL_BARNMORSKA_DTO;
import static se.inera.intyg.certificateservice.integrationtest.fk3226.FK3226TestSetup.fk3226TestSetup;

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
import se.inera.intyg.certificateservice.integrationtest.common.tests.AdministrativeMessagesIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.AnswerComplementIT;
import se.inera.intyg.certificateservice.integrationtest.common.tests.CertificateFromMessageIT;
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
import se.inera.intyg.certificateservice.integrationtest.common.tests.ForwardCertificateMessageIT;
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

public class FK3226ActiveIT extends ActiveCertificatesIT {

  public static final String TYPE = FK3226TestSetup.TYPE;

  @BeforeEach
  void setUp() {
    super.setUpBaseIT();

    baseTestabilityUtilities = fk3226TestSetup()
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
  @DisplayName(TYPE + "Administrativ ärendekommunikation")
  class AdministrativeMessages extends AdministrativeMessagesIT {

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
  @DisplayName(TYPE + "Hämta intyg utifrån id från meddelande")
  class CertificateFromMessage extends CertificateFromMessageIT {

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

    //TODO Add test for "Kompletteringsbegäran skall sättas som hanterad när förnyade intyget signeras" - see FK7472ActiveIT
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
  @DisplayName(TYPE + " Ta bort utkast")
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
  @DisplayName(TYPE + " Hämta ej signerade utkast sökkriterier")
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
  class MessageExists extends MessageExistsIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }

  @Nested
  @DisplayName(TYPE + "Hantering av frågor för intyg")
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

    @Nested
    @DisplayName(TYPE + "Uppdatera svar")
    class UpdateCertificateFK3226 extends UpdateCertificateFK3226IT {

      @Override
      protected BaseTestabilityUtilities testabilityUtilities() {
        return baseTestabilityUtilities;
      }

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
  @DisplayName(TYPE + "Vidarebefodra utkast med ärendekommunikation")
  class ForwardCertificateMessage extends ForwardCertificateMessageIT {

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
  @DisplayName(TYPE + "Förnya intyg från extern källa")
  class RenewExternalCertificate extends RenewExternalCertificateIT {

    @Override
    protected BaseTestabilityUtilities testabilityUtilities() {
      return baseTestabilityUtilities;
    }
  }
}