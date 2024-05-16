package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.fk7210;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.ALFA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_MEDICINCENTRUM;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7210CertificateBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.AJLA_DOKTOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.ALVA_VARDADMINISTRATOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.ANNA_SJUKSKOTERSKA;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.BERTIL_BARNMORSKA;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_ALLERGIMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfConstants.CHECKED_BOX_VALUE;
import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.fk7210.FK7210PdfFillService.CERTIFIER_DOCTOR_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.fk7210.FK7210PdfFillService.CERTIFIER_MIDWIFE_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.fk7210.FK7210PdfFillService.CERTIFIER_NURSE_FIELD_ID;
import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.fk7210.FK7210PdfFillService.QUESTION_BERAKNAT_FODELSEDATUM_ID;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.staff.model.Staff;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value.PdfDateValueGenerator;

@ExtendWith(MockitoExtension.class)
class FK7210PdfFillServiceTest {

  private static final LocalDate DELIVERY_DATE = LocalDate.now();
  private static final ElementData BERAKNAT_FODELSE_DATUM_ELEMENT_DATA = ElementData.builder()
      .id(QUESTION_BERAKNAT_FODELSEDATUM_ID)
      .value(
          ElementValueDate.builder()
              .date(DELIVERY_DATE)
              .build()
      )
      .build();
  private static final String DATE = "DATE";
  private static final PdfField DATE_FIELD = PdfField.builder()
      .id(DATE)
      .value(DELIVERY_DATE.toString())
      .build();

  @Mock
  PdfDateValueGenerator pdfDateValueGenerator;

  @InjectMocks
  private FK7210PdfFillService fk7210PdfFillService;

  @Test
  void shouldReturnPatientIdFormId() {
    assertEquals("form1[0].#subform[0].flt_pnr[0]", fk7210PdfFillService.getPatientIdFieldId());
  }

  @Test
  void shouldReturnGeneratorType() {
    assertEquals(
        fk7210CertificateBuilder().build().certificateModel().id().type(),
        fk7210PdfFillService.getType()
    );
  }

  @Test
  void shouldReturnAvailableMcid() {
    assertEquals(100, fk7210PdfFillService.getAvailableMcid());
  }

  @Test
  void shouldReturnSignedTagIndex() {
    assertEquals(18, fk7210PdfFillService.getSignatureTagIndex());
  }

  @Test
  void shouldNotSetExpectedDeliveryDateIfDateIsNotProvided() {
    final var result = fk7210PdfFillService.getFields(
        buildCertificate(Collections.emptyList())
    );

    assertFalse(result.contains(DATE_FIELD),
        "Expected date field not to be included in result");
  }

  @Nested
  class PdfData {

    @BeforeEach
    void setUp() {
      when(pdfDateValueGenerator.generate(any(Certificate.class), any(ElementId.class),
          anyString())).thenReturn(List.of(DATE_FIELD));
    }


    @Nested
    class ExpectedDeliveryDate {

      @Test
      void shouldReturnExpectedDeliveryDateIfDateIsProvided() {
        final var result = fk7210PdfFillService.getFields(
            buildCertificate(List.of(BERAKNAT_FODELSE_DATUM_ELEMENT_DATA))
        );

        assertTrue(result.contains(DATE_FIELD), "Expected date field to be included in result");
      }

    }


    @Nested
    class Issuer {

      @Test
      void shouldSetDoctorAsCertifierIfIssuerIsDoctor() {
        final var expected = PdfField.builder()
            .id(CERTIFIER_DOCTOR_FIELD_ID)
            .value(CHECKED_BOX_VALUE)
            .build();

        final var result = fk7210PdfFillService.getFields(
            buildCertificate(AJLA_DOKTOR)
        );

        assertTrue(result.contains(expected),
            "Expected issuer doctor field to be included in result");
      }

      @Test
      void shouldSetMidwifeAsCertifierIfIssuerIsMidwife() {
        final var expected = PdfField.builder()
            .id(CERTIFIER_MIDWIFE_FIELD_ID)
            .value(CHECKED_BOX_VALUE)
            .build();

        final var result = fk7210PdfFillService.getFields(
            buildCertificate(BERTIL_BARNMORSKA)
        );

        assertTrue(result.contains(expected),
            "Expected issuer midwife field to be included in result");
      }

      @Test
      void shouldOnlySetNurseAsCertifierIfIssuerIsNurse() {
        final var expected = PdfField.builder()
            .id(CERTIFIER_NURSE_FIELD_ID)
            .value(CHECKED_BOX_VALUE)
            .build();

        final var result = fk7210PdfFillService.getFields(
            buildCertificate(ANNA_SJUKSKOTERSKA)
        );

        assertTrue(result.contains(expected),
            "Expected issuer nurse field to be included in result");
      }

      @Test
      void shouldNotSetCertifierIfRoleIsAdmin() {
        final var result = fk7210PdfFillService.getFields(
            buildCertificate(ALVA_VARDADMINISTRATOR)
        );

        assertEquals(1, result.size(),
            "Expected issuer admin to not be included in result");
      }

      @Test
      void shouldOnlySetOneIssuerRole() {
        final var result = fk7210PdfFillService.getFields(
            buildCertificate(AJLA_DOKTOR)
        );

        assertEquals(2, result.size(),
            "Expected issuer admin to not be included in result");
      }
    }
  }

  private Certificate buildCertificate(List<ElementData> elementData) {
    return fk7210CertificateBuilder()
        .elementData(elementData)
        .build();
  }

  private Certificate buildCertificate(Staff staff) {
    return fk7210CertificateBuilder()
        .certificateMetaData(CertificateMetaData.builder()
            .issuer(staff)
            .patient(ATHENA_REACT_ANDERSSON)
            .issuingUnit(ALFA_ALLERGIMOTTAGNINGEN)
            .careUnit(ALFA_MEDICINCENTRUM)
            .careProvider(ALFA_REGIONEN)
            .build())
        .status(Status.SIGNED)
        .build();
  }
}
