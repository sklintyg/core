package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProviderConstants.ALFA_REGIONEN_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProviderConstants.ALFA_REGIONEN_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.CERTIFICATE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.FK7210_CERTIFICATE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7210CertificateBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7210_CODE_TYPE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7210_VERSION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_ALLERGIMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_ADDRESS;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_CITY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_EMAIL;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_PHONENUMBER;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_WORKPLACE_CODE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnitConstants.ALFA_ALLERGIMOTTAGNINGEN_ZIP_CODE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_FULLNAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_HEALTH_CARE_PROFESSIONAL_LICENCES;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_HSA_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_PA_TITLES;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_SPECIALITIES;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.Relation;
import se.inera.intyg.certificateservice.domain.certificate.model.RelationType;
import se.inera.intyg.certificateservice.domain.certificate.model.Signature;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;
import se.inera.intyg.certificateservice.domain.common.model.PaTitle;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.model.MessageId;
import se.inera.intyg.certificateservice.domain.message.model.MessageStatus;
import se.inera.intyg.certificateservice.domain.message.model.MessageType;
import se.inera.intyg.certificateservice.domain.message.model.SenderReference;
import se.inera.intyg.certificateservice.domain.unit.model.WorkplaceCode;
import se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.validation.XmlValidationService;
import se.riv.clinicalprocess.healthcond.certificate.registerCertificate.v3.RegisterCertificateType;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.ArbetsplatsKod;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.Befattning;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.HsaId;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.IntygId;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.LegitimeratYrkeType;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.PersonId;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.Specialistkompetens;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.TypAvIntyg;
import se.riv.clinicalprocess.healthcond.certificate.v3.Enhet;
import se.riv.clinicalprocess.healthcond.certificate.v3.HosPersonal;
import se.riv.clinicalprocess.healthcond.certificate.v3.MeddelandeReferens;
import se.riv.clinicalprocess.healthcond.certificate.v3.Patient;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Vardgivare;

@ExtendWith(MockitoExtension.class)
class XmlGeneratorCertificateV4Test {

  private static final String SUB_ANSWER_ID = "SUB_ANSWER_ID";
  private static final String ANSWER_ID = "ANSWER_ID";

  @Mock
  XmlGeneratorValue xmlGeneratorValue;
  @Mock
  XmlValidationService xmlValidationService;
  @InjectMocks
  XmlGeneratorCertificateV4 xmlGeneratorCertificateV4;

  @Test
  void shouldReturnXmlThatCanBeSuccessfullyUnmarshalled() {
    final var response = xmlGeneratorCertificateV4.generate(FK7210_CERTIFICATE, true);

    assertDoesNotThrow(
        () -> unmarshal(response), () -> "Could not unmarshall xml '%s'".formatted(response)
    );
  }

  @Test
  void shouldIncludeIntygsId() {
    final var expected = new IntygId();
    expected.setExtension(CERTIFICATE_ID.id());
    expected.setRoot(ALFA_ALLERGIMOTTAGNINGEN.hsaId().id());

    final var intyg = unmarshal(
        xmlGeneratorCertificateV4.generate(FK7210_CERTIFICATE, true)
    ).getIntyg();

    assertAll(
        () -> assertEquals(expected.getExtension(), intyg.getIntygsId().getExtension()),
        () -> assertEquals(expected.getRoot(), intyg.getIntygsId().getRoot())
    );
  }

  @Test
  void shouldIncludeTypAvIntyg() {
    final var expected = new TypAvIntyg();
    expected.setCode(FK7210_CODE_TYPE.code());
    expected.setCodeSystem(FK7210_CODE_TYPE.codeSystem());
    expected.setDisplayName(FK7210_CODE_TYPE.displayName());

    final var typAvIntyg = unmarshal(
        xmlGeneratorCertificateV4.generate(FK7210_CERTIFICATE, true)
    ).getIntyg().getTyp();

    assertAll(
        () -> assertEquals(expected.getCode(), typAvIntyg.getCode()),
        () -> assertEquals(expected.getCodeSystem(), typAvIntyg.getCodeSystem()),
        () -> assertEquals(expected.getCodeSystemName(), typAvIntyg.getCodeSystemName()),
        () -> assertEquals(expected.getCodeSystemVersion(), typAvIntyg.getCodeSystemVersion()),
        () -> assertEquals(expected.getDisplayName(), typAvIntyg.getDisplayName()),
        () -> assertEquals(expected.getOriginalText(), typAvIntyg.getOriginalText())
    );
  }

  @Test
  void shouldIncludeVersion() {
    final var expectedVersion = FK7210_VERSION.version();

    final var intyg = unmarshal(
        xmlGeneratorCertificateV4.generate(FK7210_CERTIFICATE, true)
    ).getIntyg();

    assertAll(
        () -> assertEquals(expectedVersion, intyg.getVersion())
    );
  }

  @Test
  void shouldIncludePatient() {
    final var expected = new Patient();
    final var personId = new PersonId();
    personId.setExtension(ATHENA_REACT_ANDERSSON.id().idWithoutDash());
    personId.setRoot(ATHENA_REACT_ANDERSSON.id().type().oid());
    expected.setPersonId(personId);
    expected.setFornamn("");
    expected.setEfternamn("");
    expected.setPostadress("");
    expected.setPostnummer("");
    expected.setPostort("");

    final var patient = unmarshal(
        xmlGeneratorCertificateV4.generate(FK7210_CERTIFICATE, true)
    ).getIntyg().getPatient();

    assertAll(
        () -> assertEquals(expected.getPersonId().getRoot(), patient.getPersonId().getRoot()),
        () -> assertEquals(expected.getPersonId().getExtension(),
            patient.getPersonId().getExtension()),
        () -> assertEquals(expected.getFornamn(), patient.getFornamn()),
        () -> assertEquals(expected.getMellannamn(), patient.getMellannamn()),
        () -> assertEquals(expected.getEfternamn(), patient.getEfternamn()),
        () -> assertEquals(expected.getPostadress(), patient.getPostadress()),
        () -> assertEquals(expected.getPostnummer(), patient.getPostnummer()),
        () -> assertEquals(expected.getPostort(), patient.getPostort()),
        () -> assertEquals(expected.getKallaAdressuppgifter(), patient.getKallaAdressuppgifter())
    );
  }

  @Test
  void shouldIncludeHoSPersonalBasicInformation() {
    final var expected = new HosPersonal();
    final var hsaId = new HsaId();
    hsaId.setRoot("1.2.752.129.2.1.4.1");
    hsaId.setExtension(AJLA_DOCTOR_HSA_ID);
    expected.setPersonalId(hsaId);
    expected.setForskrivarkod("0000000");
    expected.setFullstandigtNamn(AJLA_DOCTOR_FULLNAME);

    final var skapadAv = unmarshal(
        xmlGeneratorCertificateV4.generate(FK7210_CERTIFICATE, true)
    ).getIntyg().getSkapadAv();

    assertAll(
        () -> assertEquals(expected.getPersonalId().getRoot(), skapadAv.getPersonalId().getRoot()),
        () -> assertEquals(expected.getPersonalId().getExtension(),
            skapadAv.getPersonalId().getExtension()),
        () -> assertEquals(expected.getForskrivarkod(), skapadAv.getForskrivarkod()),
        () -> assertEquals(expected.getFullstandigtNamn(), skapadAv.getFullstandigtNamn())
    );
  }

  @Test
  void shouldIncludeHoSPersonalBefattningar() {
    final var expectedOne = new Befattning();
    expectedOne.setCode(AJLA_DOCTOR_PA_TITLES.getFirst().code());
    expectedOne.setCodeSystem(PaTitle.OID);
    expectedOne.setDisplayName(AJLA_DOCTOR_PA_TITLES.getFirst().description());
    final var expectedTwo = new Befattning();
    expectedTwo.setCode(AJLA_DOCTOR_PA_TITLES.get(1).code());
    expectedTwo.setCodeSystem(PaTitle.OID);
    expectedTwo.setDisplayName(AJLA_DOCTOR_PA_TITLES.get(1).description());

    final var befattningar = unmarshal(
        xmlGeneratorCertificateV4.generate(FK7210_CERTIFICATE, true)
    ).getIntyg().getSkapadAv().getBefattning();

    assertAll(
        () -> assertEquals(expectedOne.getCode(), befattningar.getFirst().getCode()),
        () -> assertEquals(expectedOne.getCodeSystem(), befattningar.getFirst().getCodeSystem()),
        () -> assertEquals(expectedOne.getDisplayName(), befattningar.getFirst().getDisplayName()),
        () -> assertEquals(expectedTwo.getCode(), befattningar.get(1).getCode()),
        () -> assertEquals(expectedTwo.getCodeSystem(), befattningar.get(1).getCodeSystem()),
        () -> assertEquals(expectedTwo.getDisplayName(), befattningar.get(1).getDisplayName())
    );
  }

  @Test
  void shouldIncludeHoSPersonalSpecialistkompetens() {
    final var expectedOne = new Specialistkompetens();
    expectedOne.setCode("N/A");
    expectedOne.setDisplayName(AJLA_DOCTOR_SPECIALITIES.getFirst().value());
    final var expectedTwo = new Specialistkompetens();
    expectedTwo.setCode("N/A");
    expectedTwo.setDisplayName(AJLA_DOCTOR_SPECIALITIES.get(1).value());

    final var specialistkompetens = unmarshal(
        xmlGeneratorCertificateV4.generate(FK7210_CERTIFICATE, true)
    ).getIntyg().getSkapadAv().getSpecialistkompetens();

    assertAll(
        () -> assertEquals(expectedOne.getCode(), specialistkompetens.getFirst().getCode()),
        () -> assertEquals(expectedOne.getDisplayName(),
            specialistkompetens.getFirst().getDisplayName()),
        () -> assertEquals(expectedTwo.getCode(), specialistkompetens.get(1).getCode()),
        () -> assertEquals(expectedTwo.getDisplayName(),
            specialistkompetens.get(1).getDisplayName())
    );
  }

  @Test
  void shouldIncludeHoSPersonalLegitimeratYrke() {
    final var expectedOne = new LegitimeratYrkeType();
    expectedOne.setCodeSystem("1.2.752.29.23.1.6");
    expectedOne.setCode("LK");
    expectedOne.setDisplayName(AJLA_DOCTOR_HEALTH_CARE_PROFESSIONAL_LICENCES.getFirst().value());

    final var legitimeradeYrken = unmarshal(
        xmlGeneratorCertificateV4.generate(FK7210_CERTIFICATE, true)
    ).getIntyg().getSkapadAv().getLegitimeratYrke();

    assertAll(
        () -> assertEquals(expectedOne.getCode(), legitimeradeYrken.getFirst().getCode()),
        () -> assertEquals(expectedOne.getDisplayName(),
            legitimeradeYrken.getFirst().getDisplayName()),
        () -> assertEquals(expectedOne.getCodeSystem(),
            legitimeradeYrken.getFirst().getCodeSystem())
    );
  }

  @Test
  void shouldIncludeHoSPersonalUnit() {
    final var expected = new Enhet();
    final var hsaId = new HsaId();
    hsaId.setRoot("1.2.752.129.2.1.4.1");
    hsaId.setExtension(ALFA_ALLERGIMOTTAGNINGEN_ID);
    expected.setEnhetsId(hsaId);
    expected.setEnhetsnamn(ALFA_ALLERGIMOTTAGNINGEN_NAME);
    expected.setPostadress(ALFA_ALLERGIMOTTAGNINGEN_ADDRESS);
    expected.setPostnummer(ALFA_ALLERGIMOTTAGNINGEN_ZIP_CODE);
    expected.setPostort(ALFA_ALLERGIMOTTAGNINGEN_CITY);
    expected.setTelefonnummer(ALFA_ALLERGIMOTTAGNINGEN_PHONENUMBER);
    expected.setEpost(ALFA_ALLERGIMOTTAGNINGEN_EMAIL);

    final var enhet = unmarshal(
        xmlGeneratorCertificateV4.generate(FK7210_CERTIFICATE, true)
    ).getIntyg().getSkapadAv().getEnhet();

    assertAll(
        () -> assertEquals(expected.getEnhetsId().getRoot(), enhet.getEnhetsId().getRoot()),
        () -> assertEquals(expected.getEnhetsId().getExtension(),
            enhet.getEnhetsId().getExtension()),
        () -> assertEquals(expected.getEnhetsnamn(), enhet.getEnhetsnamn()),
        () -> assertEquals(expected.getPostadress(), enhet.getPostadress()),
        () -> assertEquals(expected.getPostnummer(), enhet.getPostnummer()),
        () -> assertEquals(expected.getPostort(), enhet.getPostort()),
        () -> assertEquals(expected.getTelefonnummer(), enhet.getTelefonnummer()),
        () -> assertEquals(expected.getEpost(), enhet.getEpost())
    );
  }

  @Test
  void shouldIncludeHoSPersonalUnitArbetsplatskod() {
    final var expected = new ArbetsplatsKod();
    expected.setRoot(WorkplaceCode.OID);
    expected.setExtension(ALFA_ALLERGIMOTTAGNINGEN_WORKPLACE_CODE);

    final var arbetsplatskod = unmarshal(
        xmlGeneratorCertificateV4.generate(FK7210_CERTIFICATE, true)
    ).getIntyg().getSkapadAv().getEnhet().getArbetsplatskod();

    assertAll(
        () -> assertEquals(expected.getRoot(), arbetsplatskod.getRoot()),
        () -> assertEquals(expected.getExtension(), arbetsplatskod.getExtension())
    );
  }

  @Test
  void shouldIncludeHoSPersonalVardgivare() {
    final var expected = new Vardgivare();
    final var hsaId = new HsaId();
    hsaId.setRoot("1.2.752.129.2.1.4.1");
    hsaId.setExtension(ALFA_REGIONEN_ID);
    expected.setVardgivareId(hsaId);
    expected.setVardgivarnamn(ALFA_REGIONEN_NAME);

    final var vardgivare = unmarshal(
        xmlGeneratorCertificateV4.generate(FK7210_CERTIFICATE, true)
    ).getIntyg().getSkapadAv().getEnhet().getVardgivare();

    assertAll(
        () -> assertEquals(expected.getVardgivareId().getRoot(),
            vardgivare.getVardgivareId().getRoot()),
        () -> assertEquals(expected.getVardgivareId().getExtension(),
            vardgivare.getVardgivareId().getExtension()),
        () -> assertEquals(expected.getVardgivarnamn(), vardgivare.getVardgivarnamn())
    );
  }

  @Test
  void shouldIncludeAnswers() {
    final var answer = new Svar();
    final var subAnswer = new Delsvar();
    subAnswer.setId(SUB_ANSWER_ID);
    answer.setId(ANSWER_ID);
    answer.getDelsvar().add(subAnswer);
    final var expectedAnswers = List.of(answer);
    when(xmlGeneratorValue.generate(FK7210_CERTIFICATE)).thenReturn(expectedAnswers);

    final var answers = unmarshal(
        xmlGeneratorCertificateV4.generate(FK7210_CERTIFICATE, true)
    ).getIntyg().getSvar();

    assertAll(
        () -> assertEquals(ANSWER_ID, answers.getFirst().getId()),
        () -> assertEquals(SUB_ANSWER_ID, answers.getFirst().getDelsvar().getFirst().getId())
    );
  }

  @Test
  void shouldNotIncludeUnderskrift() {
    final var underskrift = unmarshal(
        xmlGeneratorCertificateV4.generate(FK7210_CERTIFICATE, true)
    ).getIntyg().getUnderskrift();

    assertAll(
        () -> assertNull(underskrift)
    );
  }

  @Test
  void shouldIncludeUnderskrift() {
    final var signature = new Signature(
        "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><ds:Signature xmlns:ns60=\"urn:riv:informationsecurity:auditing:log:2\" xmlns:ns4=\"urn:riv:clinicalprocess:healthcond:certificate:types:1\" xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:ns3=\"urn:riv:clinicalprocess:healthcond:certificate:1\" xmlns:ns6=\"urn:riv:clinicalprocess:healthcond:certificate:CertificateStatusUpdateForCareResponder:1\" xmlns:ns53=\"urn:riv:infrastructure:directory:organization:GetUnitResponder:3\" xmlns:ns5=\"urn:riv:clinicalprocess:healthcond:certificate:types:3\" xmlns:ns52=\"urn:riv:infrastructure:directory:organization:GetHealthCareUnitMembersResponder:2\" xmlns:ns8=\"urn:riv:clinicalprocess:healthcond:certificate:CertificateStatusUpdateForCareResponder:3\" xmlns:ns51=\"urn:riv:infrastructure:directory:organization:2.1\" xmlns:ns7=\"urn:riv:clinicalprocess:healthcond:certificate:3\" xmlns:ns50=\"urn:riv:infrastructure:directory:organization:2\" xmlns:ns13=\"urn:riv:clinicalprocess:healthcond:certificate:CreateDraftCertificateResponder:3\" xmlns:ns57=\"urn:riv:strategicresourcemanagement:persons:person:3\" xmlns:ns9=\"urn:riv:clinicalprocess:healthcond:certificate:3.2\" xmlns:ns12=\"urn:riv:clinicalprocess:healthcond:certificate:CreateDraftCertificateResponder:1\" xmlns:ns56=\"urn:riv:infrastructure:directory:organization:GetHealthCareProviderResponder:1\" xmlns:ns11=\"urn:riv:clinicalprocess:healthcond:certificate:CertificateStatusUpdateForCareResponder:3.2\" xmlns:ns55=\"urn:riv:infrastructure:directory:organization:GetUnitResponder:3.1\" xmlns:ns10=\"urn:riv:clinicalprocess:healthcond:certificate:3.4\" xmlns:ns54=\"urn:riv:infrastructure:directory:organization:3\" xmlns:ns17=\"urn:riv:clinicalprocess:healthcond:certificate:RegisterCertificateResponder:1\" xmlns:ns16=\"urn:riv:clinicalprocess:healthcond:certificate:ListCertificatesForCareResponder:3\" xmlns:ns15=\"urn:riv:clinicalprocess:healthcond:certificate:ListCertificatesForCareResponder:1\" xmlns:ns59=\"urn:riv:strategicresourcemanagement:persons:person:GetPersonsForProfileResponder:3\" xmlns:ns14=\"urn:riv:clinicalprocess:healthcond:certificate:3.3\" xmlns:ns58=\"urn:riv:strategicresourcemanagement:persons:person:3.1\" xmlns:ns19=\"urn:riv:clinicalprocess:healthcond:certificate:GetCertificateResponder:2\" xmlns:ns18=\"urn:riv:clinicalprocess:healthcond:certificate:RegisterCertificateResponder:3\" xmlns:ns42=\"urn:riv:infrastructure:directory:authorizationmanagement:HandleHospCertificationPersonResponder:1\" xmlns:ns41=\"urn:riv:infrastructure:directory:authorizationmanagement:2.3\" xmlns:ns40=\"urn:riv:infrastructure:directory:authorizationmanagement:2.1\" xmlns:ns46=\"urn:riv:infrastructure:directory:employee:2.2\" xmlns:ns45=\"urn:riv:infrastructure:directory:employee:GetEmployeeIncludingProtectedPersonResponder:2\" xmlns:dsf=\"http://www.w3.org/2002/06/xmldsig-filter2\" xmlns:ns44=\"urn:riv:infrastructure:directory:authorizationmanagement:GetHospLastUpdateResponder:1\" xmlns:ns43=\"urn:riv:infrastructure:directory:authorizationmanagement:GetHospCredentialsForPersonResponder:1\" xmlns:ns49=\"urn:riv:infrastructure:directory:organization:GetHealthCareUnitResponder:2\" xmlns:ns48=\"urn:riv:infrastructure:directory:employee:2.1\" xmlns:ns47=\"urn:riv:infrastructure:directory:employee:2\" xmlns:ns82=\"http://www.w3.org/2005/08/addressing\" xmlns:ns81=\"urn:local:se:intygstjanster:services:RegisterTSDiabetesResponder:1\" xmlns:ns80=\"urn:local:se:intygstjanster:services:GetTSDiabetesResponder:1\" xmlns:ns31=\"urn:riv:infrastructure:directory:privatepractitioner:terms:1\" xmlns:ns75=\"urn:riv:insuranceprocess:healthreporting:ReceiveMedicalCertificateQuestionResponder:1\" xmlns:ns30=\"urn:riv:clinicalprocess:healthcond:certificate:types:2\" xmlns:ns74=\"urn:riv:insuranceprocess:healthreporting:ReceiveMedicalCertificateAnswerResponder:1\" xmlns:ns73=\"urn:riv:insuranceprocess:healthreporting:SetCertificateStatusResponder:1\" xmlns:ns72=\"urn:riv:insuranceprocess:healthreporting:SendMedicalCertificateQuestionResponder:1\" xmlns:ns35=\"urn:riv:infrastructure:directory:privatepractitioner:ValidatePrivatePractitionerResponder:1\" xmlns:ns79=\"urn:local:se:intygstjanster:services:RegisterTSBasResponder:1\" xmlns:ns34=\"urn:riv:infrastructure:directory:privatepractitioner:GetPrivatePractitionerResponder:1\" xmlns:ns78=\"urn:local:se:intygstjanster:services:types:1\" xmlns:ns33=\"urn:riv:infrastructure:directory:privatepractitioner:1\" xmlns:ns77=\"urn:local:se:intygstjanster:services:GetTSBasResponder:1\" xmlns:ns32=\"urn:riv:infrastructure:directory:privatepractitioner:GetPrivatePractitionerTermsResponder:1\" xmlns:ns76=\"urn:local:se:intygstjanster:services:1\" xmlns:ns39=\"urn:riv:infrastructure:directory:authorizationmanagement:2.2\" xmlns:ns38=\"urn:riv:infrastructure:directory:authorizationmanagement:2\" xmlns:ns37=\"urn:riv:infrastructure:directory:authorizationmanagement:GetCredentialsForPersonIncludingProtectedPersonResponder:2.1\" xmlns:ns36=\"urn:riv:infrastructure:directory:authorizationmanagement:GetCredentialsForPersonIncludingProtectedPersonResponder:2\" xmlns:ns71=\"urn:riv:insuranceprocess:healthreporting:SendMedicalCertificateResponder:1\" xmlns:ns70=\"urn:riv:insuranceprocess:healthreporting:SendMedicalCertificateAnswerResponder:1\" xmlns:ns20=\"urn:riv:clinicalprocess:healthcond:certificate:SendMessageToCareResponder:2\" xmlns:ns64=\"urn:riv:insuranceprocess:healthreporting:2\" xmlns:ns63=\"urn:riv:insuranceprocess:certificate:1\" xmlns:ns62=\"urn:riv:insuranceprocess:healthreporting:GetCertificateResponder:1\" xmlns:ns61=\"urn:riv:informationsecurity:auditing:log:StoreLogResponder:2\" xmlns:ns24=\"urn:riv:clinicalprocess:healthcond:certificate:ListCertificatesForCitizenResponder:4\" xmlns:ns68=\"urn:riv:insuranceprocess:healthreporting:RevokeMedicalCertificateResponder:1\" xmlns:ns23=\"urn:riv:clinicalprocess:healthcond:certificate:ListCertificatesForCitizenResponder:3\" xmlns:ns67=\"urn:riv:insuranceprocess:healthreporting:RegisterMedicalCertificateResponder:3\" xmlns:ns22=\"urn:riv:clinicalprocess:healthcond:certificate:SendCertificateToRecipientResponder:2\" xmlns:ns66=\"urn:riv:insuranceprocess:healthreporting:mu7263:3\" xmlns:ns21=\"urn:riv:clinicalprocess:healthcond:certificate:SendMessageToRecipientResponder:2\" xmlns:ns65=\"urn:riv:insuranceprocess:healthreporting:ListCertificatesResponder:1\" xmlns:ns28=\"urn:riv:clinicalprocess:healthcond:certificate:ListCertificatesForCareWithQAResponder:3\" xmlns:ns27=\"urn:riv:clinicalprocess:healthcond:certificate:ListSickLeavesForCareResponder:1\" xmlns:ns26=\"urn:riv:clinicalprocess:healthcond:certificate:SetCertificateStatusResponder:2\" xmlns:ns25=\"urn:riv:clinicalprocess:healthcond:certificate:RevokeCertificateResponder:2\" xmlns:ns69=\"urn:riv:insuranceprocess:healthreporting:medcertqa:1\" xmlns:ns29=\"urn:riv:clinicalprocess:healthcond:rehabilitation:1\"><ds:SignedInfo><ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/><ds:SignatureMethod Algorithm=\"http://www.w3.org/2001/04/xmldsig-more#rsa-sha256\"/><ds:Reference URI=\"\"><ds:Transforms><ds:Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\"/><ds:Transform Algorithm=\"http://www.w3.org/TR/1999/REC-xslt-19991116\"><xsl:stylesheet version=\"1.0\" xmlns:xsl=\"http://www.w3.org/1999/XSL/Transform\"><xsl:output indent=\"no\" omit-xml-declaration=\"yes\"/><xsl:strip-space elements=\"*\"/><xsl:template match=\"*\"><xsl:element name=\"{local-name(.)}\"><xsl:apply-templates select=\"node()|@*\"/></xsl:element></xsl:template><xsl:template match=\"@*\"><xsl:copy/></xsl:template></xsl:stylesheet></ds:Transform><ds:Transform Algorithm=\"http://www.w3.org/2002/06/xmldsig-filter2\"><dsf:XPath Filter=\"intersect\">//extension[text()='ffddecb7-b8ef-42c2-85fb-4b6064a53429']/../..</dsf:XPath></ds:Transform><ds:Transform Algorithm=\"http://www.w3.org/2002/06/xmldsig-filter2\"><dsf:XPath Filter=\"subtract\">//*[local-name() = 'skickatTidpunkt']|//*[local-name() = 'relation']|//*[local-name() = 'status']|//*[local-name() = 'underskrift']</dsf:XPath></ds:Transform><ds:Transform Algorithm=\"http://www.w3.org/2001/10/xml-exc-c14n#\"/></ds:Transforms><ds:DigestMethod Algorithm=\"http://www.w3.org/2001/04/xmlenc#sha256\"/><ds:DigestValue>FLmzfFM3WokzqRqyhIGbSb7WyITUrjAzzXF7vpbw4bU=</ds:DigestValue></ds:Reference></ds:SignedInfo><ds:SignatureValue>TD5mzAS/u01LG/GP4EUSfjESEiofNQZPLpBc4c4/LBkHg7x0Fl8ue2B1lDKqCjXGHCA27H2d3tzIGiVW6Kay23kS5zdGAN19bETFfAsJo6vicZ6yNlYia99zBBT0+h0NseCqrxF2mJnXQd0zgHAAK1uDXp6PUzib2fYVfle0Big=</ds:SignatureValue><ds:KeyInfo><ds:X509Data><ds:X509Certificate>MIICSjCCAbOgAwIBAgIECXEBjDANBgkqhkiG9w0BAQsFADBYMQswCQYDVQQGEwJTRTENMAsGA1UECBMEVGVzdDENMAsGA1UEBxMEVGVzdDENMAsGA1UEChMEVGVzdDENMAsGA1UECxMEVGVzdDENMAsGA1UEAxMEVGVzdDAeFw0xODEwMTUxMzA0NDRaFw0yODEwMTIxMzA0NDRaMFgxCzAJBgNVBAYTAlNFMQ0wCwYDVQQIEwRUZXN0MQ0wCwYDVQQHEwRUZXN0MQ0wCwYDVQQKEwRUZXN0MQ0wCwYDVQQLEwRUZXN0MQ0wCwYDVQQDEwRUZXN0MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCIxf1C12hBqkeG0K+sc2HtyfgZ81FODkkWrtavbfFbSIPEu/mjbxAVEIlwFk+kFM9s/RE9SBPGdvkJsRaR3Ls+JVpsVGxYnjN7F/K+qqk7LSRY6Wy7QzP9cY5uCODe3+ZeCFLda7WLOjLDRDq+vHhPTjgP0UQRozICjeRuowx69QIDAQABoyEwHzAdBgNVHQ4EFgQU92RmXIvRBJ68H/VKQSxAEbEe5eQwDQYJKoZIhvcNAQELBQADgYEAS76np3wn7qUfB+nQLnf+BMNblNagog5lOw5QCnLK6/kgpNnth3HcBijqP/GgYt73GOOL1KJXrR7vJu+j7sK10OYmUzZPU1ZAbFjieqx/XaNsT15CxCKS0njwWjAc2+N8asN/NH3dpEZ4t/Svg3iNqe2XRNRmpOUebc17VxqhhJA=</ds:X509Certificate></ds:X509Data></ds:KeyInfo></ds:Signature>"
    );

    final var underskrift = unmarshal(
        xmlGeneratorCertificateV4.generate(FK7210_CERTIFICATE, signature)
    ).getIntyg().getUnderskrift();

    assertAll(
        () -> assertNotNull(underskrift)
    );
  }

  @Test
  void shouldNotIncludeSigneringsTidpunktIfSignedIsNull() {
    final var unsignedCertificate = fk7210CertificateBuilder()
        .signed(null)
        .build();

    final var signeringstidpunkt = unmarshal(
        xmlGeneratorCertificateV4.generate(unsignedCertificate, true)
    ).getIntyg().getSigneringstidpunkt();

    assertNull(signeringstidpunkt);
  }

  @Test
  void shouldNotIncludeSkickatTidpunktIfSignedIsNull() {
    final var unsignedCertificate = fk7210CertificateBuilder()
        .signed(null)
        .build();

    final var skickatTidpunkt = unmarshal(
        xmlGeneratorCertificateV4.generate(unsignedCertificate, true)
    ).getIntyg().getSkickatTidpunkt();

    assertNull(skickatTidpunkt);
  }

  @Test
  void shouldIncludeSigneringtidpunkt() {
    final var expectedValue = "2024-04-01T12:30:35";
    final var signedCertificate = fk7210CertificateBuilder()
        .status(Status.SIGNED)
        .signed(LocalDateTime.parse(expectedValue))
        .build();

    final var signeringstidpunkt = unmarshal(
        xmlGeneratorCertificateV4.generate(signedCertificate, true)
    ).getIntyg().getSigneringstidpunkt();

    assertEquals(
        expectedValue,
        signeringstidpunkt.toString()
    );
  }

  @Test
  void shouldIncludeSigneringtidpunktOnExactMinute() {
    final var expectedValue = "2024-04-01T12:30:00";
    final var signedCertificate = fk7210CertificateBuilder()
        .status(Status.SIGNED)
        .signed(LocalDateTime.parse(expectedValue))
        .build();

    final var signeringstidpunkt = unmarshal(
        xmlGeneratorCertificateV4.generate(signedCertificate, true)
    ).getIntyg().getSigneringstidpunkt();

    assertEquals(
        expectedValue,
        signeringstidpunkt.toString()
    );
  }

  @Test
  void shouldIncludeSkickatTidpunkt() {
    final var expectedValue = "2024-04-01T12:30:17";
    final var signedCertificate = fk7210CertificateBuilder()
        .status(Status.SIGNED)
        .signed(LocalDateTime.parse(expectedValue))
        .build();

    final var skickatTidpunkt = unmarshal(
        xmlGeneratorCertificateV4.generate(signedCertificate, true)
    ).getIntyg().getSkickatTidpunkt();

    assertEquals(
        expectedValue,
        skickatTidpunkt.toString()
    );
  }

  @Test
  void shouldValidateXmlIfValidateIsTrue() {
    final var xml = xmlGeneratorCertificateV4.generate(FK7210_CERTIFICATE, true);
    verify(xmlValidationService).validate(
        xml,
        FK7210_CERTIFICATE.certificateModel().schematronPath(),
        FK7210_CERTIFICATE.id()
    );
  }

  @Test
  void shouldNotValidateXmlIfValidateIsFalse() {
    xmlGeneratorCertificateV4.generate(FK7210_CERTIFICATE, false);
    verifyNoInteractions(xmlValidationService);

  }

  @Test
  void shouldNotIncludeRelationIfParentIsMissing() {
    final var relation = unmarshal(
        xmlGeneratorCertificateV4.generate(FK7210_CERTIFICATE, true)).getIntyg().getRelation();
    assertTrue(relation.isEmpty());
  }

  @Test
  void shouldIncludeRelationIfParentIsPresent() {
    final var parentCertificate = FK7210_CERTIFICATE;
    final var certificate = fk7210CertificateBuilder()
        .parent(
            Relation.builder()
                .type(RelationType.REPLACE)
                .certificate(parentCertificate)
                .build()
        )
        .build();

    final var relation = unmarshal(
        xmlGeneratorCertificateV4.generate(certificate, true)).getIntyg().getRelation();

    assertAll(
        () -> assertEquals("c2362fcd-eda0-4f9a-bd13-b3bbaf7f2146",
            relation.getFirst().getTyp().getCodeSystem()),
        () -> assertEquals("ERSATT", relation.getFirst().getTyp().getCode()),
        () -> assertEquals("Ersätter", relation.getFirst().getTyp().getDisplayName()),
        () -> assertEquals(parentCertificate.id().id(),
            relation.getFirst().getIntygsId().getExtension())
    );
  }

  @Test
  void shouldIncludeMeddelandeReferensBasedOnLatestNotHandledComplement() {
    final var expected = new MeddelandeReferens();
    expected.setMeddelandeId("MESSAGE_ID");
    expected.setReferensId("REFERENCE_ID");

    final var certificate = fk7210CertificateBuilder()
        .parent(
            Relation.builder()
                .type(RelationType.COMPLEMENT)
                .certificate(
                    fk7210CertificateBuilder()
                        .messages(
                            List.of(
                                Message.builder()
                                    .type(MessageType.ANSWER)
                                    .build(),
                                Message.builder()
                                    .type(MessageType.COMPLEMENT)
                                    .sent(LocalDateTime.now().minusDays(5))
                                    .id(new MessageId("MESSAGE_ID"))
                                    .reference(new SenderReference("REFERENCE_ID"))
                                    .build(),
                                Message.builder()
                                    .type(MessageType.COMPLEMENT)
                                    .sent(LocalDateTime.now().minusDays(10))
                                    .build(),
                                Message.builder()
                                    .type(MessageType.COMPLEMENT)
                                    .status(MessageStatus.HANDLED)
                                    .sent(LocalDateTime.now())
                                    .build()
                            )
                        ).build()
                )
                .build()
        ).build();

    final var svarPa = unmarshal(
        xmlGeneratorCertificateV4.generate(certificate, true)
    ).getSvarPa();

    assertAll(
        () -> assertEquals(expected.getMeddelandeId(), svarPa.getMeddelandeId()),
        () -> assertEquals(expected.getReferensId(), svarPa.getReferensId())
    );
  }

  @Test
  void shouldNotIncludeMeddelandeReferensIfNotComplemented() {
    final var expected = new MeddelandeReferens();
    expected.setMeddelandeId("MESSAGE_ID");
    expected.setReferensId("REFERENCE_ID");

    final var certificate = fk7210CertificateBuilder()
        .parent(
            Relation.builder()
                .type(RelationType.RENEW)
                .certificate(FK7210_CERTIFICATE)
                .build()
        )
        .messages(
            List.of(
                Message.builder()
                    .type(MessageType.ANSWER)
                    .build()
            )
        )
        .build();

    final var svarPa = unmarshal(
        xmlGeneratorCertificateV4.generate(certificate, true)
    ).getSvarPa();

    assertNull(svarPa);
  }

  private RegisterCertificateType unmarshal(Xml response) {
    try {
      final var context = JAXBContext.newInstance(RegisterCertificateType.class);
      final var unmarshaller = context.createUnmarshaller();
      final var stringReader = new StringReader(response.xml());
      final var jaxbElement = (JAXBElement<RegisterCertificateType>) unmarshaller.unmarshal(
          stringReader);
      return jaxbElement.getValue();
    } catch (Exception ex) {
      throw new IllegalStateException(ex);
    }
  }
}
