package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProviderConstants.ALFA_REGIONEN_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProviderConstants.ALFA_REGIONEN_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.CERTIFICATE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.FK7211_CERTIFICATE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7211_CODE_TYPE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7211_VERSION;
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
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_HSA_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_PA_TITLES;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_SPECIALITIES;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import java.io.StringReader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;
import se.inera.intyg.certificateservice.domain.common.model.PaTitle;
import se.inera.intyg.certificateservice.domain.unit.model.WorkplaceCode;
import se.riv.clinicalprocess.healthcond.certificate.registerCertificate.v3.RegisterCertificateType;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.ArbetsplatsKod;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.Befattning;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.HsaId;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.IntygId;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.PersonId;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.Specialistkompetens;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.TypAvIntyg;
import se.riv.clinicalprocess.healthcond.certificate.v3.Enhet;
import se.riv.clinicalprocess.healthcond.certificate.v3.HosPersonal;
import se.riv.clinicalprocess.healthcond.certificate.v3.Patient;
import se.riv.clinicalprocess.healthcond.certificate.v3.Vardgivare;

@ExtendWith(MockitoExtension.class)
class XmlGeneratorCertificateV4Test {

  @InjectMocks
  XmlGeneratorCertificateV4 xmlGeneratorCertificateV4;

  @Test
  void shouldReturnXmlThatCanBeSuccessfullyUnmarshalled() {
    final var response = xmlGeneratorCertificateV4.generate(FK7211_CERTIFICATE);

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
        xmlGeneratorCertificateV4.generate(FK7211_CERTIFICATE)
    ).getIntyg();

    assertAll(
        () -> assertEquals(expected.getExtension(), intyg.getIntygsId().getExtension()),
        () -> assertEquals(expected.getRoot(), intyg.getIntygsId().getRoot())
    );
  }

  @Test
  void shouldIncludeTypAvIntyg() {
    final var expected = new TypAvIntyg();
    expected.setCode(FK7211_CODE_TYPE.code());
    expected.setCodeSystem(FK7211_CODE_TYPE.codeSystem());
    expected.setDisplayName(FK7211_CODE_TYPE.displayName());

    final var typAvIntyg = unmarshal(
        xmlGeneratorCertificateV4.generate(FK7211_CERTIFICATE)
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
    final var expectedVersion = FK7211_VERSION.version();

    final var intyg = unmarshal(
        xmlGeneratorCertificateV4.generate(FK7211_CERTIFICATE)
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
        xmlGeneratorCertificateV4.generate(FK7211_CERTIFICATE)
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
        xmlGeneratorCertificateV4.generate(FK7211_CERTIFICATE)
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
    expectedOne.setCode(AJLA_DOCTOR_PA_TITLES.get(0).code());
    expectedOne.setCodeSystem(PaTitle.OID);
    expectedOne.setDisplayName(AJLA_DOCTOR_PA_TITLES.get(0).description());
    final var expectedTwo = new Befattning();
    expectedTwo.setCode(AJLA_DOCTOR_PA_TITLES.get(1).code());
    expectedTwo.setCodeSystem(PaTitle.OID);
    expectedTwo.setDisplayName(AJLA_DOCTOR_PA_TITLES.get(1).description());

    final var befattningar = unmarshal(
        xmlGeneratorCertificateV4.generate(FK7211_CERTIFICATE)
    ).getIntyg().getSkapadAv().getBefattning();

    assertAll(
        () -> assertEquals(expectedOne.getCode(), befattningar.get(0).getCode()),
        () -> assertEquals(expectedOne.getCodeSystem(), befattningar.get(0).getCodeSystem()),
        () -> assertEquals(expectedOne.getDisplayName(), befattningar.get(0).getDisplayName()),
        () -> assertEquals(expectedTwo.getCode(), befattningar.get(1).getCode()),
        () -> assertEquals(expectedTwo.getCodeSystem(), befattningar.get(1).getCodeSystem()),
        () -> assertEquals(expectedTwo.getDisplayName(), befattningar.get(1).getDisplayName())
    );
  }

  @Test
  void shouldIncludeHoSPersonalSpecialistkompetens() {
    final var expectedOne = new Specialistkompetens();
    expectedOne.setCode("N/A");
    expectedOne.setDisplayName(AJLA_DOCTOR_SPECIALITIES.get(0).value());
    final var expectedTwo = new Specialistkompetens();
    expectedTwo.setCode("N/A");
    expectedTwo.setDisplayName(AJLA_DOCTOR_SPECIALITIES.get(1).value());

    final var specialistkompetens = unmarshal(
        xmlGeneratorCertificateV4.generate(FK7211_CERTIFICATE)
    ).getIntyg().getSkapadAv().getSpecialistkompetens();

    assertAll(
        () -> assertEquals(expectedOne.getCode(), specialistkompetens.get(0).getCode()),
        () -> assertEquals(expectedOne.getDisplayName(),
            specialistkompetens.get(0).getDisplayName()),
        () -> assertEquals(expectedTwo.getCode(), specialistkompetens.get(1).getCode()),
        () -> assertEquals(expectedTwo.getDisplayName(),
            specialistkompetens.get(1).getDisplayName())
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
        xmlGeneratorCertificateV4.generate(FK7211_CERTIFICATE)
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
        xmlGeneratorCertificateV4.generate(FK7211_CERTIFICATE)
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
        xmlGeneratorCertificateV4.generate(FK7211_CERTIFICATE)
    ).getIntyg().getSkapadAv().getEnhet().getVardgivare();

    assertAll(
        () -> assertEquals(expected.getVardgivareId().getRoot(),
            vardgivare.getVardgivareId().getRoot()),
        () -> assertEquals(expected.getVardgivareId().getExtension(),
            vardgivare.getVardgivareId().getExtension()),
        () -> assertEquals(expected.getVardgivarnamn(), vardgivare.getVardgivarnamn())
    );
  }

  private RegisterCertificateType unmarshal(Xml response) {
    try {
      System.out.println(response);
      final var context = JAXBContext.newInstance(RegisterCertificateType.class);
      final var unmarshaller = context.createUnmarshaller();
      final var stringReader = new StringReader(response.xml());
      final var jaxbElement = (JAXBElement<RegisterCertificateType>) unmarshaller.unmarshal(
          stringReader);
      return jaxbElement.getValue();
    } catch (Exception ex) {
      ex.printStackTrace();
      throw new IllegalStateException(ex);
    }
  }

}