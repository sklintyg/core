package se.inera.intyg.certificateservice.integrationtest.ag7804;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ANONYMA_REACT_ATTILA_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ALVA_VARDADMINISTRATOR_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ANNA_SJUKSKOTERSKA_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.BERTIL_BARNMORSKA_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataIncomingMessage.incomingComplementDTOBuilder;
import static se.inera.intyg.certificateservice.application.testdata.TestDataIncomingMessage.incomingComplementMessageBuilder;
import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804.elements.QuestionMedicinskBehandling.QUESTION_MEDICINSK_BEHANDLING_ID;
import static se.inera.intyg.certificateservice.integrationtest.ag7804.AG7804Constants.CODE;
import static se.inera.intyg.certificateservice.integrationtest.ag7804.AG7804Constants.CODE_SYSTEM;
import static se.inera.intyg.certificateservice.integrationtest.fk7804.FK7804Constants.FK7804;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customCreateCertificateFromTemplateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customTestabilityCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultComplementCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultCreateCertificateFromTemplateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultReplaceCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultSendCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultSignCertificateRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.certificate;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.certificateId;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateUtil.version;

import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDataElement;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateStatusTypeDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.integrationtest.AccessLevelsDeepIntegrationIT;
import se.inera.intyg.certificateservice.integrationtest.AccessLevelsSVODIT;
import se.inera.intyg.certificateservice.integrationtest.BaseIntegrationIT;
import se.inera.intyg.certificateservice.integrationtest.CertificateReadyForSignIT;
import se.inera.intyg.certificateservice.integrationtest.CertificatesWithQAForCareIT;
import se.inera.intyg.certificateservice.integrationtest.CreateCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.DeleteCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.ExistsCertificateExternalTypeInfoIT;
import se.inera.intyg.certificateservice.integrationtest.ExistsCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.ExistsCertificateTypeInfoIT;
import se.inera.intyg.certificateservice.integrationtest.ForwardCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.GetCertificateEventsIT;
import se.inera.intyg.certificateservice.integrationtest.GetCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.GetCertificateTypeInfoIT;
import se.inera.intyg.certificateservice.integrationtest.GetCertificateXmlIT;
import se.inera.intyg.certificateservice.integrationtest.GetPatientCertificatesIT;
import se.inera.intyg.certificateservice.integrationtest.GetUnitCertificatesIT;
import se.inera.intyg.certificateservice.integrationtest.GetUnitCertificatesInfoIT;
import se.inera.intyg.certificateservice.integrationtest.GetUnitCertificatesWhenSignedIT;
import se.inera.intyg.certificateservice.integrationtest.RenewCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.RenewExternalCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.ReplaceCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.RevokeCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.SignCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.UnitStatisticsIT;
import se.inera.intyg.certificateservice.integrationtest.UpdateCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.ValidateCertificateIT;
import se.inera.intyg.certificateservice.integrationtest.fk7804.FK7804Constants;
import se.inera.intyg.certificateservice.testability.certificate.dto.TestabilityFillTypeDTO;


public class AG7804ActiveIT {

  private static final String CERTIFICATE_TYPE = AG7804Constants.AG7804;
  private static final String ACTIVE_VERSION = AG7804Constants.VERSION;
  private static final String WRONG_VERSION = AG7804Constants.WRONG_VERSION;
  private static final String TYPE = AG7804Constants.TYPE;
  private static final String VALUE = "Svarstext för pågående behandling.";
  private static final ElementId ELEMENT_ID = QUESTION_MEDICINSK_BEHANDLING_ID;


  @DynamicPropertySource
  static void testProperties(DynamicPropertyRegistry registry) {
    registry.add("certificate.model.ag7804.v2_0.active.from", () -> "2024-01-01T00:00:00");
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
          Arguments.of(ALVA_VARDADMINISTRATOR_DTO)
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
      return false;
    }

    @Override
    protected boolean midwifeCanMarkReadyForSignCertificate() {
      return false;
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
      return false;
    }

    @Override
    protected boolean midwifeCanForwardCertificate() {
      return false;
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
      return false;
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
  @DisplayName(TYPE + "Förnya intyg från extern källa")
  class RenewExternalCertificate extends RenewExternalCertificateIT {

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
  @DisplayName(TYPE + "Skapa ett AG7804 utifrån ett FK7804")
  class CreateCertificateFromTemplatIT extends BaseIntegrationIT {

    private static final String QUESTION_KONTAKT_ID = "26";

    @Test
    @DisplayName("Skall skapa ett AG7804 med intygsinnehåll från ett signerat FK7804")
    void shallCreateAG7804FromSignedFK7804() {
      final var createCertificateResponse = testabilityApi.addCertificate(
          customTestabilityCertificateRequest(
              FK7804,
              FK7804Constants.VERSION,
              CertificateStatusTypeDTO.SIGNED
          )
              .fillType(TestabilityFillTypeDTO.MAXIMAL)
              .build()
      );

      final var response = api.createCertificateFromTemplate(
          defaultCreateCertificateFromTemplateRequest(),
          certificateId(createCertificateResponse.getBody())
      );

      final var templateCertificate = certificate(createCertificateResponse.getBody());
      final var certificateData = Objects.requireNonNull(certificate(response.getBody())).getData();

      assertNotNull(templateCertificate);

      templateCertificate.getData().entrySet().forEach(data -> {
        if (excludeIcfAndQuestionKontakt(data)) {
          return;
        }

        final var certificateDataElement = certificateData.get(data.getKey());

        assertEquals(data.getValue().getValue(), certificateDataElement.getValue());
      });
    }

    private static boolean excludeIcfAndQuestionKontakt(
        Entry<String, CertificateDataElement> data) {
      return data.getValue().getValue() == null || data.getValue().getValue().getType()
          .equals(CertificateDataValueType.ICF) || data.getValue().getId().contains(
          QUESTION_KONTAKT_ID);
    }

    @Test
    @DisplayName("Skall returnera felkod 403 om man försöker skapa ett AG7804 från ett osignerat FK7804")
    void shallReturn403IfCreateAG7804FromUnsignedFK7804() {
      final var createCertificateResponse = testabilityApi.addCertificate(
          customTestabilityCertificateRequest(
              FK7804,
              FK7804Constants.VERSION,
              CertificateStatusTypeDTO.UNSIGNED
          )
              .fillType(TestabilityFillTypeDTO.MAXIMAL)
              .build()
      );

      final var response = api.createCertificateFromTemplate(
          defaultCreateCertificateFromTemplateRequest(),
          certificateId(createCertificateResponse.getBody())
      );

      assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    @DisplayName("Skall returnera felkod 403 om man försöker skapa ett AG7804 från ett makulerat FK7804")
    void shallReturn403IfCreateAG7804FromRevokedFK7804() {
      final var createCertificateResponse = testabilityApi.addCertificate(
          customTestabilityCertificateRequest(
              FK7804,
              FK7804Constants.VERSION,
              CertificateStatusTypeDTO.REVOKED
          )
              .fillType(TestabilityFillTypeDTO.MAXIMAL)
              .build()
      );

      final var response = api.createCertificateFromTemplate(
          defaultCreateCertificateFromTemplateRequest(),
          certificateId(createCertificateResponse.getBody())
      );

      assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    @DisplayName("Skall returnera felkod 403 om man försöker skapa ett AG7804 från ett ersatt FK7804")
    void shallReturn403IfCreateAG7804FromReplacedAndSignedFK7804() {
      final var createCertificateResponse = testabilityApi.addCertificate(
          customTestabilityCertificateRequest(
              FK7804,
              FK7804Constants.VERSION,
              CertificateStatusTypeDTO.SIGNED
          )
              .fillType(TestabilityFillTypeDTO.MAXIMAL)
              .build()
      );

      final var replaceCertificateResponse = api.replaceCertificate(
          defaultReplaceCertificateRequest(),
          certificateId(createCertificateResponse.getBody())
      );

      api.signCertificate(
          defaultSignCertificateRequest(),
          certificateId(replaceCertificateResponse.getBody()),
          version(replaceCertificateResponse.getBody())
      );

      final var response = api.createCertificateFromTemplate(
          defaultCreateCertificateFromTemplateRequest(),
          certificateId(createCertificateResponse.getBody())
      );

      assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }


    @Test
    @DisplayName("Skall returnera felkod 403 om man försöker skapa ett AG7804 från ett kompletterat FK7804")
    void shallReturn403IfCreateAG7804FromComplementedAndSignedFK7804() {
      final var createCertificateResponse = testabilityApi.addCertificate(
          customTestabilityCertificateRequest(
              FK7804,
              FK7804Constants.VERSION,
              CertificateStatusTypeDTO.SIGNED
          )
              .fillType(TestabilityFillTypeDTO.MAXIMAL)
              .build()
      );

      api.sendCertificate(
          defaultSendCertificateRequest(),
          certificateId(createCertificateResponse.getBody())
      );

      api.receiveMessage(
          incomingComplementMessageBuilder()
              .certificateId(certificateId(createCertificateResponse.getBody()))
              .complements(List.of(incomingComplementDTOBuilder()
                  .questionId(QUESTION_MEDICINSK_BEHANDLING_ID.id())
                  .build()))
              .build()
      );

      final var complementCertificateResponse = api.complementCertificate(
          defaultComplementCertificateRequest(),
          certificateId(createCertificateResponse.getBody())
      );

      api.signCertificate(
          defaultSignCertificateRequest(),
          certificateId(complementCertificateResponse.getBody()),
          version(complementCertificateResponse.getBody())
      );

      final var response = api.createCertificateFromTemplate(
          defaultCreateCertificateFromTemplateRequest(),
          certificateId(createCertificateResponse.getBody())
      );

      assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }


    @Test
    @DisplayName("Skall returnera felkod 403 om man försöker skapa ett AG7804 från ett FK7804 som är utfärdat på en patient med skyddade personuppgifter som vårdadmin")
    void shallReturn403IfCreateAG7804FromFK7804OnCertificateWithProtectedPersonAsCareAdmin() {
      final var createCertificateResponse = testabilityApi.addCertificate(
          customTestabilityCertificateRequest(
              FK7804,
              FK7804Constants.VERSION,
              CertificateStatusTypeDTO.SIGNED
          )
              .patient(ANONYMA_REACT_ATTILA_DTO)
              .fillType(TestabilityFillTypeDTO.MAXIMAL)
              .build()
      );

      final var response = api.createCertificateFromTemplate(
          customCreateCertificateFromTemplateRequest()
              .user(ALVA_VARDADMINISTRATOR_DTO)
              .build(),
          certificateId(createCertificateResponse.getBody())
      );

      assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }
  }
}