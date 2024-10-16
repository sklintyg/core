package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.message;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProviderConstants.ALFA_REGIONEN_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProviderConstants.ALFA_REGIONEN_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.CERTIFICATE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.FK7472_CERTIFICATE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessage.ANSWER;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessage.COMPLEMENT_MESSAGE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessage.CONTACT_MESSAGE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessage.answerBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessage.complementMessageBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessage.contactMessageBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.ANSWER_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.ANSWER_REFERENCE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.CONTENT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.MESSAGE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.REFERENCE_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataMessageConstants.SUBJECT;
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
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;
import se.inera.intyg.certificateservice.domain.common.model.PaTitle;
import se.inera.intyg.certificateservice.domain.unit.model.WorkplaceCode;
import se.riv.clinicalprocess.healthcond.certificate.sendMessageToRecipient.v2.SendMessageToRecipientType;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.Amneskod;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.ArbetsplatsKod;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.Befattning;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.HsaId;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.IntygId;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.LegitimeratYrkeType;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.PersonId;
import se.riv.clinicalprocess.healthcond.certificate.types.v3.Specialistkompetens;
import se.riv.clinicalprocess.healthcond.certificate.v3.Enhet;
import se.riv.clinicalprocess.healthcond.certificate.v3.HosPersonal;
import se.riv.clinicalprocess.healthcond.certificate.v3.Vardgivare;

@ExtendWith(MockitoExtension.class)
class XmlGeneratorMessageV4Test {

  @InjectMocks
  private XmlGeneratorMessageV4 xmlGeneratorMessageV4;

  @Nested
  class GenerateTests {

    @Test
    void shouldReturnXmlThatCanBeSuccessfullyUnmarshalled() {
      final var response = xmlGeneratorMessageV4.generate(CONTACT_MESSAGE, FK7472_CERTIFICATE);

      assertDoesNotThrow(
          () -> unmarshal(response), () -> "Could not unmarshall xml '%s'".formatted(response)
      );
    }

    @Test
    void shouldIncludeMeddelandeIdFromAnswer() {
      final var meddelandeId = unmarshal(
          xmlGeneratorMessageV4.generate(CONTACT_MESSAGE, FK7472_CERTIFICATE)
      ).getMeddelandeId();

      assertEquals(MESSAGE_ID, meddelandeId);
    }


    @Test
    void shouldIncludeReferensId() {
      final var referensId = unmarshal(
          xmlGeneratorMessageV4.generate(CONTACT_MESSAGE, FK7472_CERTIFICATE)
      ).getReferensId();

      assertEquals(REFERENCE_ID, referensId);
    }

    @Test
    void shouldExcludeReferensIdIfNull() {
      final var answerWithoutReference = answerBuilder()
          .reference(null)
          .build();

      final var referensId = unmarshal(
          xmlGeneratorMessageV4.generateAnswer(answerWithoutReference, CONTACT_MESSAGE,
              FK7472_CERTIFICATE)
      ).getReferensId();

      assertNull(referensId);
    }

    @Test
    void shouldIncludeSkickatTidpunktFromAnswer() {
      final var expectedValue = "2024-04-01T12:30:35";
      final var message = contactMessageBuilder()
          .sent(LocalDateTime.parse(expectedValue))
          .build();

      final var skickatTidpunkt = unmarshal(
          xmlGeneratorMessageV4.generate(message, FK7472_CERTIFICATE)
      ).getSkickatTidpunkt();

      assertEquals(expectedValue, skickatTidpunkt.toString());
    }

    @Test
    void shouldIncludeSkickatTidpunktOnExactMinute() {
      final var expectedValue = "2024-04-01T12:30:00";
      final var message = contactMessageBuilder()
          .sent(LocalDateTime.parse(expectedValue))
          .build();

      final var skickatTidpunkt = unmarshal(
          xmlGeneratorMessageV4.generate(message, FK7472_CERTIFICATE)
      ).getSkickatTidpunkt();

      assertEquals(expectedValue, skickatTidpunkt.toString());
    }

    @Test
    void shouldIncludeIntygsId() {
      final var expected = new IntygId();
      expected.setExtension(CERTIFICATE_ID.id());
      expected.setRoot(ALFA_ALLERGIMOTTAGNINGEN.hsaId().id());

      final var intygsId = unmarshal(
          xmlGeneratorMessageV4.generate(CONTACT_MESSAGE, FK7472_CERTIFICATE)
      ).getIntygsId();

      assertAll(
          () -> assertEquals(expected.getExtension(), intygsId.getExtension()),
          () -> assertEquals(expected.getRoot(), intygsId.getRoot())
      );
    }

    @Test
    void shouldIncludePatientPersonId() {
      final var expected = new PersonId();
      expected.setExtension(ATHENA_REACT_ANDERSSON.id().idWithoutDash());
      expected.setRoot(ATHENA_REACT_ANDERSSON.id().type().oid());

      final var patientPersonId = unmarshal(
          xmlGeneratorMessageV4.generate(CONTACT_MESSAGE, FK7472_CERTIFICATE)
      ).getPatientPersonId();

      assertAll(
          () -> assertEquals(expected.getRoot(), patientPersonId.getRoot()),
          () -> assertEquals(expected.getExtension(), patientPersonId.getExtension())
      );
    }

    @Test
    void shouldIncludeLogiskAdressMottagare() {
      final var logiskAdressMottagare = unmarshal(
          xmlGeneratorMessageV4.generate(CONTACT_MESSAGE, FK7472_CERTIFICATE)
      ).getLogiskAdressMottagare();

      assertEquals(FK7472_CERTIFICATE.certificateModel().recipient().logicalAddress(),
          logiskAdressMottagare);
    }

    @Test
    void shouldIncludeAmneskod() {
      final var expected = new Amneskod();
      expected.setCodeSystem("ffa59d8f-8d7e-46ae-ac9e-31804e8e8499");
      expected.setCode("KONTKT");
      expected.setDisplayName("Kontakt");

      final var amneskod = unmarshal(
          xmlGeneratorMessageV4.generate(CONTACT_MESSAGE, FK7472_CERTIFICATE)
      ).getAmne();

      assertAll(
          () -> assertEquals(expected.getCode(), amneskod.getCode()),
          () -> assertEquals(expected.getDisplayName(), amneskod.getDisplayName()),
          () -> assertEquals(expected.getCodeSystem(), amneskod.getCodeSystem())
      );
    }

    @Test
    void shouldIncludeRubrikFromAnswer() {
      final var rubrik = unmarshal(
          xmlGeneratorMessageV4.generate(CONTACT_MESSAGE, FK7472_CERTIFICATE)
      ).getRubrik();

      assertEquals(SUBJECT, rubrik);
    }


    @Test
    void shouldIncludeMeddelandeFromAnswer() {
      final var meddelande = unmarshal(
          xmlGeneratorMessageV4.generate(CONTACT_MESSAGE, FK7472_CERTIFICATE)
      ).getMeddelande();

      assertEquals(CONTENT, meddelande);
    }


    @Test
    void shouldIncludeSvarPa() {
      final var svarPa = unmarshal(
          xmlGeneratorMessageV4.generate(CONTACT_MESSAGE, FK7472_CERTIFICATE)
      ).getSvarPa();

      assertNull(svarPa);
    }


    @Test
    void shouldIncludeSvarPaWithoutReferens() {
      final var messageWithoutReference = complementMessageBuilder()
          .reference(null)
          .build();
      final var svarPa = unmarshal(
          xmlGeneratorMessageV4.generateAnswer(ANSWER, messageWithoutReference, FK7472_CERTIFICATE)
      ).getSvarPa();

      assertAll(
          () -> assertEquals(MESSAGE_ID, svarPa.getMeddelandeId()),
          () -> assertNull(svarPa.getReferensId())
      );
    }

    @Test
    void shouldIncludeHoSPersonalBasicInformationFromAnswer() {
      final var expected = new HosPersonal();
      final var hsaId = new HsaId();
      hsaId.setRoot("1.2.752.129.2.1.4.1");
      hsaId.setExtension(AJLA_DOCTOR_HSA_ID);
      expected.setPersonalId(hsaId);
      expected.setForskrivarkod("0000000");
      expected.setFullstandigtNamn(AJLA_DOCTOR_FULLNAME);

      final var skapadAv = unmarshal(
          xmlGeneratorMessageV4.generate(CONTACT_MESSAGE, FK7472_CERTIFICATE)
      ).getSkickatAv();

      assertAll(
          () -> assertEquals(expected.getPersonalId().getRoot(),
              skapadAv.getPersonalId().getRoot()),
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
          xmlGeneratorMessageV4.generate(CONTACT_MESSAGE, FK7472_CERTIFICATE)
      ).getSkickatAv().getBefattning();

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
          xmlGeneratorMessageV4.generate(CONTACT_MESSAGE, FK7472_CERTIFICATE)
      ).getSkickatAv().getSpecialistkompetens();

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
    void shouldIncludeHoSPersonalLegitimeratYrke() {
      final var expectedOne = new LegitimeratYrkeType();
      expectedOne.setCodeSystem("1.2.752.29.23.1.6");
      expectedOne.setCode("LK");
      expectedOne.setDisplayName(AJLA_DOCTOR_HEALTH_CARE_PROFESSIONAL_LICENCES.get(0).value());

      final var legitimeradeYrken = unmarshal(
          xmlGeneratorMessageV4.generate(CONTACT_MESSAGE, FK7472_CERTIFICATE)
      ).getSkickatAv().getLegitimeratYrke();

      assertAll(
          () -> assertEquals(expectedOne.getCode(), legitimeradeYrken.get(0).getCode()),
          () -> assertEquals(expectedOne.getDisplayName(),
              legitimeradeYrken.get(0).getDisplayName()),
          () -> assertEquals(expectedOne.getCodeSystem(), legitimeradeYrken.get(0).getCodeSystem())
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
          xmlGeneratorMessageV4.generate(CONTACT_MESSAGE, FK7472_CERTIFICATE)
      ).getSkickatAv().getEnhet();

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
          xmlGeneratorMessageV4.generate(CONTACT_MESSAGE, FK7472_CERTIFICATE)
      ).getSkickatAv().getEnhet().getArbetsplatskod();

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
          xmlGeneratorMessageV4.generate(CONTACT_MESSAGE, FK7472_CERTIFICATE)
      ).getSkickatAv().getEnhet().getVardgivare();

      assertAll(
          () -> assertEquals(expected.getVardgivareId().getRoot(),
              vardgivare.getVardgivareId().getRoot()),
          () -> assertEquals(expected.getVardgivareId().getExtension(),
              vardgivare.getVardgivareId().getExtension()),
          () -> assertEquals(expected.getVardgivarnamn(), vardgivare.getVardgivarnamn())
      );
    }
  }


  @Nested
  class GenerateAnswerTests {

    @Test
    void shouldReturnXmlThatCanBeSuccessfullyUnmarshalled() {
      final var response = xmlGeneratorMessageV4.generateAnswer(ANSWER, COMPLEMENT_MESSAGE,
          FK7472_CERTIFICATE);

      assertDoesNotThrow(
          () -> unmarshal(response), () -> "Could not unmarshall xml '%s'".formatted(response)
      );
    }

    @Test
    void shouldIncludeMeddelandeIdFromAnswer() {
      final var meddelandeId = unmarshal(
          xmlGeneratorMessageV4.generateAnswer(ANSWER, COMPLEMENT_MESSAGE, FK7472_CERTIFICATE)
      ).getMeddelandeId();

      assertEquals(ANSWER_ID, meddelandeId);
    }


    @Test
    void shouldIncludeReferensId() {
      final var referensId = unmarshal(
          xmlGeneratorMessageV4.generateAnswer(ANSWER, COMPLEMENT_MESSAGE, FK7472_CERTIFICATE)
      ).getReferensId();

      assertEquals(ANSWER_REFERENCE_ID, referensId);
    }

    @Test
    void shouldExcludeReferensIdIfNull() {
      final var answerWithoutReference = answerBuilder()
          .reference(null)
          .build();

      final var referensId = unmarshal(
          xmlGeneratorMessageV4.generateAnswer(answerWithoutReference, COMPLEMENT_MESSAGE,
              FK7472_CERTIFICATE)
      ).getReferensId();

      assertNull(referensId);
    }

    @Test
    void shouldIncludeSkickatTidpunktFromAnswer() {
      final var expectedValue = "2024-04-01T12:30:35";
      final var answer = answerBuilder()
          .sent(LocalDateTime.parse(expectedValue))
          .build();

      final var skickatTidpunkt = unmarshal(
          xmlGeneratorMessageV4.generateAnswer(answer, COMPLEMENT_MESSAGE, FK7472_CERTIFICATE)
      ).getSkickatTidpunkt();

      assertEquals(expectedValue, skickatTidpunkt.toString());
    }

    @Test
    void shouldIncludeSkickatTidpunktOnExactMinute() {
      final var expectedValue = "2024-04-01T12:30:00";
      final var answer = answerBuilder()
          .sent(LocalDateTime.parse(expectedValue))
          .build();

      final var skickatTidpunkt = unmarshal(
          xmlGeneratorMessageV4.generateAnswer(answer, COMPLEMENT_MESSAGE, FK7472_CERTIFICATE)
      ).getSkickatTidpunkt();

      assertEquals(expectedValue, skickatTidpunkt.toString());
    }

    @Test
    void shouldIncludeIntygsId() {
      final var expected = new IntygId();
      expected.setExtension(CERTIFICATE_ID.id());
      expected.setRoot(ALFA_ALLERGIMOTTAGNINGEN.hsaId().id());

      final var intygsId = unmarshal(
          xmlGeneratorMessageV4.generateAnswer(ANSWER, COMPLEMENT_MESSAGE, FK7472_CERTIFICATE)
      ).getIntygsId();

      assertAll(
          () -> assertEquals(expected.getExtension(), intygsId.getExtension()),
          () -> assertEquals(expected.getRoot(), intygsId.getRoot())
      );
    }

    @Test
    void shouldIncludePatientPersonId() {
      final var expected = new PersonId();
      expected.setExtension(ATHENA_REACT_ANDERSSON.id().idWithoutDash());
      expected.setRoot(ATHENA_REACT_ANDERSSON.id().type().oid());

      final var patientPersonId = unmarshal(
          xmlGeneratorMessageV4.generateAnswer(ANSWER, COMPLEMENT_MESSAGE, FK7472_CERTIFICATE)
      ).getPatientPersonId();

      assertAll(
          () -> assertEquals(expected.getRoot(), patientPersonId.getRoot()),
          () -> assertEquals(expected.getExtension(), patientPersonId.getExtension())
      );
    }

    @Test
    void shouldIncludeLogiskAdressMottagare() {
      final var logiskAdressMottagare = unmarshal(
          xmlGeneratorMessageV4.generateAnswer(ANSWER, COMPLEMENT_MESSAGE, FK7472_CERTIFICATE)
      ).getLogiskAdressMottagare();

      assertEquals(FK7472_CERTIFICATE.certificateModel().recipient().logicalAddress(),
          logiskAdressMottagare);
    }

    @Test
    void shouldIncludeAmneskod() {
      final var expected = new Amneskod();
      expected.setCodeSystem("ffa59d8f-8d7e-46ae-ac9e-31804e8e8499");
      expected.setCode("KOMPLT");
      expected.setDisplayName("Komplettering");

      final var amneskod = unmarshal(
          xmlGeneratorMessageV4.generateAnswer(ANSWER, COMPLEMENT_MESSAGE, FK7472_CERTIFICATE)
      ).getAmne();

      assertAll(
          () -> assertEquals(expected.getCode(), amneskod.getCode()),
          () -> assertEquals(expected.getDisplayName(), amneskod.getDisplayName()),
          () -> assertEquals(expected.getCodeSystem(), amneskod.getCodeSystem())
      );
    }

    @Test
    void shouldIncludeRubrikFromAnswer() {
      final var rubrik = unmarshal(
          xmlGeneratorMessageV4.generateAnswer(ANSWER, COMPLEMENT_MESSAGE, FK7472_CERTIFICATE)
      ).getRubrik();

      assertEquals(SUBJECT, rubrik);
    }


    @Test
    void shouldIncludeMeddelandeFromAnswer() {
      final var meddelande = unmarshal(
          xmlGeneratorMessageV4.generateAnswer(ANSWER, COMPLEMENT_MESSAGE, FK7472_CERTIFICATE)
      ).getMeddelande();

      assertEquals(CONTENT, meddelande);
    }


    @Test
    void shouldIncludeSvarPa() {
      final var svarPa = unmarshal(
          xmlGeneratorMessageV4.generateAnswer(ANSWER, COMPLEMENT_MESSAGE, FK7472_CERTIFICATE)
      ).getSvarPa();

      assertAll(
          () -> assertEquals(MESSAGE_ID, svarPa.getMeddelandeId()),
          () -> assertEquals(REFERENCE_ID, svarPa.getReferensId())
      );
    }


    @Test
    void shouldIncludeSvarPaWithoutReferens() {
      final var messageWithoutReference = complementMessageBuilder()
          .reference(null)
          .build();
      final var svarPa = unmarshal(
          xmlGeneratorMessageV4.generateAnswer(ANSWER, messageWithoutReference, FK7472_CERTIFICATE)
      ).getSvarPa();

      assertAll(
          () -> assertEquals(MESSAGE_ID, svarPa.getMeddelandeId()),
          () -> assertNull(svarPa.getReferensId())
      );
    }

    @Test
    void shouldIncludeHoSPersonalBasicInformationFromAnswer() {
      final var expected = new HosPersonal();
      final var hsaId = new HsaId();
      hsaId.setRoot("1.2.752.129.2.1.4.1");
      hsaId.setExtension(AJLA_DOCTOR_HSA_ID);
      expected.setPersonalId(hsaId);
      expected.setForskrivarkod("0000000");
      expected.setFullstandigtNamn(AJLA_DOCTOR_FULLNAME);

      final var skapadAv = unmarshal(
          xmlGeneratorMessageV4.generateAnswer(ANSWER, COMPLEMENT_MESSAGE, FK7472_CERTIFICATE)
      ).getSkickatAv();

      assertAll(
          () -> assertEquals(expected.getPersonalId().getRoot(),
              skapadAv.getPersonalId().getRoot()),
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
          xmlGeneratorMessageV4.generateAnswer(ANSWER, COMPLEMENT_MESSAGE, FK7472_CERTIFICATE)
      ).getSkickatAv().getBefattning();

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
          xmlGeneratorMessageV4.generateAnswer(ANSWER, COMPLEMENT_MESSAGE, FK7472_CERTIFICATE)
      ).getSkickatAv().getSpecialistkompetens();

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
    void shouldIncludeHoSPersonalLegitimeratYrke() {
      final var expectedOne = new LegitimeratYrkeType();
      expectedOne.setCodeSystem("1.2.752.29.23.1.6");
      expectedOne.setCode("LK");
      expectedOne.setDisplayName(AJLA_DOCTOR_HEALTH_CARE_PROFESSIONAL_LICENCES.get(0).value());

      final var legitimeradeYrken = unmarshal(
          xmlGeneratorMessageV4.generateAnswer(ANSWER, COMPLEMENT_MESSAGE, FK7472_CERTIFICATE)
      ).getSkickatAv().getLegitimeratYrke();

      assertAll(
          () -> assertEquals(expectedOne.getCode(), legitimeradeYrken.get(0).getCode()),
          () -> assertEquals(expectedOne.getDisplayName(),
              legitimeradeYrken.get(0).getDisplayName()),
          () -> assertEquals(expectedOne.getCodeSystem(), legitimeradeYrken.get(0).getCodeSystem())
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
          xmlGeneratorMessageV4.generateAnswer(ANSWER, COMPLEMENT_MESSAGE, FK7472_CERTIFICATE)
      ).getSkickatAv().getEnhet();

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
          xmlGeneratorMessageV4.generateAnswer(ANSWER, COMPLEMENT_MESSAGE, FK7472_CERTIFICATE)
      ).getSkickatAv().getEnhet().getArbetsplatskod();

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
          xmlGeneratorMessageV4.generateAnswer(ANSWER, COMPLEMENT_MESSAGE, FK7472_CERTIFICATE)
      ).getSkickatAv().getEnhet().getVardgivare();

      assertAll(
          () -> assertEquals(expected.getVardgivareId().getRoot(),
              vardgivare.getVardgivareId().getRoot()),
          () -> assertEquals(expected.getVardgivareId().getExtension(),
              vardgivare.getVardgivareId().getExtension()),
          () -> assertEquals(expected.getVardgivarnamn(), vardgivare.getVardgivarnamn())
      );
    }
  }

  private SendMessageToRecipientType unmarshal(Xml response) {
    try {
      System.out.println(response.xml());
      final var context = JAXBContext.newInstance(SendMessageToRecipientType.class);
      final var unmarshaller = context.createUnmarshaller();
      final var stringReader = new StringReader(response.xml());
      final var jaxbElement = (JAXBElement<SendMessageToRecipientType>) unmarshaller.unmarshal(
          stringReader);
      return jaxbElement.getValue();
    } catch (Exception ex) {
      throw new IllegalStateException(ex);
    }
  }
}
