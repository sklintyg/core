package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4.certificate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProviderConstants.ALFA_REGIONEN_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProviderConstants.ALFA_REGIONEN_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.CERTIFICATE_ID;
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
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUser.AJLA_DOKTOR;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Relation;
import se.inera.intyg.certificateservice.domain.certificate.model.RelationType;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificate.model.Xml;
import se.inera.intyg.certificateservice.domain.common.model.ExternalReference;
import se.inera.intyg.certificateservice.domain.common.model.PaTitle;
import se.inera.intyg.certificateservice.domain.message.model.Answer;
import se.inera.intyg.certificateservice.domain.message.model.Message;
import se.inera.intyg.certificateservice.domain.message.model.MessageStatus;
import se.inera.intyg.certificateservice.domain.staff.model.Staff;
import se.inera.intyg.certificateservice.domain.unit.model.WorkplaceCode;
import se.riv.clinicalprocess.healthcond.certificate.listCertificatesForCareWithQA.v3.ListCertificatesForCareWithQAResponseType;
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
import se.riv.clinicalprocess.healthcond.certificate.v3.Patient;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Svar.Delsvar;
import se.riv.clinicalprocess.healthcond.certificate.v3.Vardgivare;

@ExtendWith(MockitoExtension.class)
class XmlGeneratorCertificateWithQAV3Test {


  private static final String SUB_ANSWER_ID = "SUB_ANSWER_ID";
  private static final String ANSWER_ID = "ANSWER_ID";
  private Certificate certificate;

  @Mock
  XmlGeneratorValue xmlGeneratorValue;
  @InjectMocks
  XmlGeneratorCertificateWithQAV3 xmlGeneratorCertificateWithQAV3;

  @Nested
  class ListItemIntygTests {

    @BeforeEach
    void setUp() {
      certificate = fk7210CertificateBuilder()
          .xml(null)
          .signed(null)
          .build();
    }

    @Test
    void shouldReturnXmlThatCanBeSuccessfullyUnmarshalled() {
      final var response = xmlGeneratorCertificateWithQAV3.generate(List.of(certificate));

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
          xmlGeneratorCertificateWithQAV3.generate(List.of(certificate))
      ).getList().getItem().getFirst().getIntyg();

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
          xmlGeneratorCertificateWithQAV3.generate(List.of(certificate))
      ).getList().getItem().getFirst().getIntyg().getTyp();

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
          xmlGeneratorCertificateWithQAV3.generate(List.of(certificate))
      ).getList().getItem().getFirst().getIntyg();

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
          xmlGeneratorCertificateWithQAV3.generate(List.of(certificate))
      ).getList().getItem().getFirst().getIntyg().getPatient();

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
          xmlGeneratorCertificateWithQAV3.generate(List.of(certificate))
      ).getList().getItem().getFirst().getIntyg().getSkapadAv();

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
      expectedOne.setCode(AJLA_DOCTOR_PA_TITLES.getFirst().code());
      expectedOne.setCodeSystem(PaTitle.OID);
      expectedOne.setDisplayName(AJLA_DOCTOR_PA_TITLES.getFirst().description());
      final var expectedTwo = new Befattning();
      expectedTwo.setCode(AJLA_DOCTOR_PA_TITLES.get(1).code());
      expectedTwo.setCodeSystem(PaTitle.OID);
      expectedTwo.setDisplayName(AJLA_DOCTOR_PA_TITLES.get(1).description());

      final var befattningar = unmarshal(
          xmlGeneratorCertificateWithQAV3.generate(List.of(certificate))
      ).getList().getItem().getFirst().getIntyg().getSkapadAv().getBefattning();

      assertAll(
          () -> assertEquals(expectedOne.getCode(), befattningar.getFirst().getCode()),
          () -> assertEquals(expectedOne.getCodeSystem(), befattningar.getFirst().getCodeSystem()),
          () -> assertEquals(expectedOne.getDisplayName(),
              befattningar.getFirst().getDisplayName()),
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
          xmlGeneratorCertificateWithQAV3.generate(List.of(certificate))
      ).getList().getItem().getFirst().getIntyg().getSkapadAv().getSpecialistkompetens();

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
          xmlGeneratorCertificateWithQAV3.generate(List.of(certificate))
      ).getList().getItem().getFirst().getIntyg().getSkapadAv().getLegitimeratYrke();

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
          xmlGeneratorCertificateWithQAV3.generate(List.of(certificate))
      ).getList().getItem().getFirst().getIntyg().getSkapadAv().getEnhet();

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
          xmlGeneratorCertificateWithQAV3.generate(List.of(certificate))
      ).getList().getItem().getFirst().getIntyg().getSkapadAv().getEnhet().getArbetsplatskod();

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
          xmlGeneratorCertificateWithQAV3.generate(List.of(certificate))
      ).getList().getItem().getFirst().getIntyg().getSkapadAv().getEnhet().getVardgivare();

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
      when(xmlGeneratorValue.generate(certificate)).thenReturn(expectedAnswers);

      final var answers = unmarshal(
          xmlGeneratorCertificateWithQAV3.generate(List.of(certificate))
      ).getList().getItem().getFirst().getIntyg().getSvar();

      assertAll(
          () -> assertEquals(ANSWER_ID, answers.getFirst().getId()),
          () -> assertEquals(SUB_ANSWER_ID, answers.getFirst().getDelsvar().getFirst().getId())
      );
    }

    @Test
    void shouldNotIncludeUnderskrift() {
      final var underskrift = unmarshal(
          xmlGeneratorCertificateWithQAV3.generate(List.of(certificate))
      ).getList().getItem().getFirst().getIntyg().getUnderskrift();

      assertAll(
          () -> assertNull(underskrift)
      );
    }

    @Test
    void shouldNotIncludeSigneringsTidpunktIfSignedIsNull() {
      final var signeringstidpunkt = unmarshal(
          xmlGeneratorCertificateWithQAV3.generate(List.of(certificate))
      ).getList().getItem().getFirst().getIntyg().getSigneringstidpunkt();

      assertNull(signeringstidpunkt);
    }

    @Test
    void shouldNotIncludeSkickatTidpunktIfSignedIsNull() {
      final var skickatTidpunkt = unmarshal(
          xmlGeneratorCertificateWithQAV3.generate(List.of(certificate))
      ).getList().getItem().getFirst().getIntyg().getSkickatTidpunkt();

      assertNull(skickatTidpunkt);
    }

    @Test
    void shouldIncludeSigneringtidpunkt() {
      final var expectedValue = "2024-04-01T12:30:35";
      final var signedCertificate = fk7210CertificateBuilder()
          .xml(null)
          .status(Status.SIGNED)
          .signed(LocalDateTime.parse(expectedValue))
          .build();

      final var signeringstidpunkt = unmarshal(
          xmlGeneratorCertificateWithQAV3.generate(List.of(signedCertificate))
      ).getList().getItem().getFirst().getIntyg().getSigneringstidpunkt();

      assertEquals(
          expectedValue,
          signeringstidpunkt.toString()
      );
    }

    @Test
    void shouldIncludeSigneringtidpunktOnExactMinute() {
      final var expectedValue = "2024-04-01T12:30:00";
      final var signedCertificate = fk7210CertificateBuilder()
          .xml(null)
          .status(Status.SIGNED)
          .signed(LocalDateTime.parse(expectedValue))
          .build();

      final var signeringstidpunkt = unmarshal(
          xmlGeneratorCertificateWithQAV3.generate(List.of(signedCertificate))
      ).getList().getItem().getFirst().getIntyg().getSigneringstidpunkt();

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
          .xml(null)
          .signed(LocalDateTime.parse(expectedValue))
          .build();

      final var skickatTidpunkt = unmarshal(
          xmlGeneratorCertificateWithQAV3.generate(List.of(signedCertificate))
      ).getList().getItem().getFirst().getIntyg().getSkickatTidpunkt();

      assertEquals(
          expectedValue,
          skickatTidpunkt.toString()
      );
    }

    @Test
    void shouldNotIncludeRelationIfParentIsMissing() {
      final var relation = unmarshal(
          xmlGeneratorCertificateWithQAV3.generate(List.of(certificate))).getList().getItem()
          .getFirst().getIntyg()
          .getRelation();
      assertTrue(relation.isEmpty());
    }

    @Test
    void shouldIncludeRelationIfParentIsPresent() {
      final var parentCertificate = certificate;
      final var certificate = fk7210CertificateBuilder()
          .xml(null)
          .parent(
              Relation.builder()
                  .type(RelationType.REPLACE)
                  .certificate(parentCertificate)
                  .build()
          )
          .build();

      final var relation = unmarshal(
          xmlGeneratorCertificateWithQAV3.generate(List.of(certificate))).getList().getItem()
          .getFirst()
          .getIntyg().getRelation();

      assertAll(
          () -> assertEquals("c2362fcd-eda0-4f9a-bd13-b3bbaf7f2146",
              relation.getFirst().getTyp().getCodeSystem()),
          () -> assertEquals("ERSATT", relation.getFirst().getTyp().getCode()),
          () -> assertEquals("Ersätter", relation.getFirst().getTyp().getDisplayName()),
          () -> assertEquals(parentCertificate.id().id(),
              relation.getFirst().getIntygsId().getExtension())
      );
    }
  }

  @Nested
  class ListItemMessageTests {

    @Nested
    class MessagesFromCare {

      private static final Message MESSAGE_WITH_ANSWER = Message.builder()
          .authoredStaff(Staff.create(AJLA_DOKTOR))
          .answer(Answer.builder().build())
          .build();
      private static final Message MESSAGE_WITHOUT_ANSWER = Message.builder()
          .authoredStaff(Staff.create(AJLA_DOKTOR))
          .build();
      private static final Message HANDLED_MESSAGE = Message.builder()
          .authoredStaff(Staff.create(AJLA_DOKTOR))
          .status(MessageStatus.HANDLED)
          .build();

      @BeforeEach
      void setUp() {
        certificate = fk7210CertificateBuilder()
            .xml(null)
            .messages(
                List.of(
                    MESSAGE_WITH_ANSWER,
                    MESSAGE_WITHOUT_ANSWER,
                    HANDLED_MESSAGE
                )
            )
            .build();
      }

      @Test
      void shallIncludeTotal() {
        final var messagesFromCare = unmarshal(
            xmlGeneratorCertificateWithQAV3.generate(List.of(certificate))).getList().getItem()
            .getFirst().getSkickadeFragor();

        assertEquals(3, messagesFromCare.getTotalt());
      }

      @Test
      void shallIncludeAnswered() {
        final var messagesFromCare = unmarshal(
            xmlGeneratorCertificateWithQAV3.generate(List.of(certificate))).getList().getItem()
            .getFirst().getSkickadeFragor();

        assertEquals(1, messagesFromCare.getBesvarade());
      }

      @Test
      void shallIncludeNotAnswered() {
        final var messagesFromCare = unmarshal(
            xmlGeneratorCertificateWithQAV3.generate(List.of(certificate))).getList().getItem()
            .getFirst().getSkickadeFragor();

        assertEquals(2, messagesFromCare.getEjBesvarade());
      }

      @Test
      void shallIncludeHandled() {
        final var messagesFromCare = unmarshal(
            xmlGeneratorCertificateWithQAV3.generate(List.of(certificate))).getList().getItem()
            .getFirst().getSkickadeFragor();

        assertEquals(1, messagesFromCare.getHanterade());
      }
    }

    @Nested
    class MessagesFromRecipient {

      private static final Message MESSAGE_WITH_ANSWER = Message.builder()
          .answer(Answer.builder().build())
          .build();
      private static final Message MESSAGE_WITHOUT_ANSWER = Message.builder()
          .build();
      private static final Message HANDLED_MESSAGE = Message.builder()
          .status(MessageStatus.HANDLED)
          .build();

      @BeforeEach
      void setUp() {
        certificate = fk7210CertificateBuilder()
            .xml(null)
            .messages(
                List.of(
                    MESSAGE_WITH_ANSWER,
                    MESSAGE_WITHOUT_ANSWER,
                    HANDLED_MESSAGE
                )
            )
            .build();
      }

      @Test
      void shallIncludeTotal() {
        final var messagesFromCare = unmarshal(
            xmlGeneratorCertificateWithQAV3.generate(List.of(certificate))).getList().getItem()
            .getFirst().getMottagnaFragor();

        assertEquals(3, messagesFromCare.getTotalt());
      }

      @Test
      void shallIncludeAnswered() {
        final var messagesFromCare = unmarshal(
            xmlGeneratorCertificateWithQAV3.generate(List.of(certificate))).getList().getItem()
            .getFirst().getMottagnaFragor();

        assertEquals(1, messagesFromCare.getBesvarade());
      }

      @Test
      void shallIncludeNotAnswered() {
        final var messagesFromCare = unmarshal(
            xmlGeneratorCertificateWithQAV3.generate(List.of(certificate))).getList().getItem()
            .getFirst().getMottagnaFragor();

        assertEquals(2, messagesFromCare.getEjBesvarade());
      }

      @Test
      void shallIncludeHandled() {
        final var messagesFromCare = unmarshal(
            xmlGeneratorCertificateWithQAV3.generate(List.of(certificate))).getList().getItem()
            .getFirst().getMottagnaFragor();

        assertEquals(1, messagesFromCare.getHanterade());
      }
    }
  }

  @Nested
  class RefTests {

    private static final String EXPECTED_REF = "expectedRef";

    @Test
    void shallIncludeRef() {
      certificate = fk7210CertificateBuilder()
          .xml(null)
          .externalReference(new ExternalReference(EXPECTED_REF))
          .build();

      final var actualRef = unmarshal(
          xmlGeneratorCertificateWithQAV3.generate(List.of(certificate))).getList().getItem()
          .getFirst().getRef();

      assertEquals(EXPECTED_REF, actualRef);
    }

    @Test
    void shallNotIncludeRef() {
      certificate = fk7210CertificateBuilder()
          .xml(null)
          .externalReference(null)
          .build();

      final var ref = unmarshal(
          xmlGeneratorCertificateWithQAV3.generate(List.of(certificate))).getList().getItem()
          .getFirst().getRef();

      assertNull(ref);
    }
  }

  private ListCertificatesForCareWithQAResponseType unmarshal(Xml response) {
    try {
      final var context = JAXBContext.newInstance(
          ListCertificatesForCareWithQAResponseType.class);
      final var unmarshaller = context.createUnmarshaller();
      final var stringReader = new StringReader(response.xml());
      final var jaxbElement = (JAXBElement<ListCertificatesForCareWithQAResponseType>) unmarshaller.unmarshal(
          stringReader);
      return jaxbElement.getValue();
    } catch (Exception ex) {
      throw new IllegalStateException(ex);
    }
  }
}
