package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.FK7210_CERTIFICATE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.FK7472_CERTIFICATE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7210_PDF_FODELSEDATUM_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7210_QUESTION_BERAKNAT_FODELSEDATUM_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7472_PDF_PERIOD_FIELD_ID_PREFIX;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7472_PDF_SYMPTOM_FIELD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7472_QUESTION_PERIOD_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificateModelConstants.FK7472_QUESTION_SYMPTOM_ID;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfValueType;

@ExtendWith(MockitoExtension.class)
class PdfElementValueGeneratorTest {

  @Mock
  PdfDateValueGenerator pdfDateValueGenerator;

  @Mock
  PdfTextValueGenerator pdfTextValueGenerator;

  @Mock
  PdfDateRangeListValueGenerator pdfDateRangeListValueGenerator;

  PdfElementValueGenerator pdfElementValueGenerator;

  @Test
  void shouldUseDateValueGeneratorIfPdfValueTypeIsDate() {
    pdfElementValueGenerator = new PdfElementValueGenerator(
        List.of(pdfDateValueGenerator)
    );

    doReturn(PdfValueType.DATE).when(pdfDateValueGenerator).getType();

    pdfElementValueGenerator.getFields(FK7210_CERTIFICATE);

    verify(pdfDateValueGenerator, times(1))
        .generate(FK7210_CERTIFICATE, FK7210_QUESTION_BERAKNAT_FODELSEDATUM_ID,
            FK7210_PDF_FODELSEDATUM_FIELD_ID);
  }

  @Test
  void shouldUseTextValueGeneratorIfPdfValueTypeIsText() {
    pdfElementValueGenerator = new PdfElementValueGenerator(
        List.of(pdfTextValueGenerator, pdfDateRangeListValueGenerator)
    );

    doReturn(PdfValueType.DATE_RANGE_LIST).when(pdfDateRangeListValueGenerator).getType();
    doReturn(PdfValueType.TEXT).when(pdfTextValueGenerator).getType();

    pdfElementValueGenerator.getFields(FK7472_CERTIFICATE);

    verify(pdfTextValueGenerator, times(1))
        .generate(FK7472_CERTIFICATE, FK7472_QUESTION_SYMPTOM_ID,
            FK7472_PDF_SYMPTOM_FIELD_ID);
  }

  @Test
  void shouldUseDateRangeListValueGeneratorIfPdfValueTypeIsDateRangeList() {
    pdfElementValueGenerator = new PdfElementValueGenerator(
        List.of(pdfTextValueGenerator, pdfDateRangeListValueGenerator)
    );

    doReturn(PdfValueType.TEXT).when(pdfTextValueGenerator).getType();
    doReturn(PdfValueType.DATE_RANGE_LIST).when(pdfDateRangeListValueGenerator).getType();

    pdfElementValueGenerator.getFields(FK7472_CERTIFICATE);

    verify(pdfDateRangeListValueGenerator, times(1))
        .generate(FK7472_CERTIFICATE, FK7472_QUESTION_PERIOD_ID, FK7472_PDF_PERIOD_FIELD_ID_PREFIX);
  }

  @Test
  void shouldUseMultipleValueGeneratorsIfMultiplePdfValueTypesInCertificate() {
    pdfElementValueGenerator = new PdfElementValueGenerator(
        List.of(pdfTextValueGenerator, pdfDateRangeListValueGenerator)
    );

    doReturn(PdfValueType.TEXT).when(pdfTextValueGenerator).getType();
    doReturn(PdfValueType.DATE_RANGE_LIST).when(pdfDateRangeListValueGenerator).getType();

    pdfElementValueGenerator.getFields(FK7472_CERTIFICATE);

    verify(pdfDateRangeListValueGenerator, times(1))
        .generate(FK7472_CERTIFICATE, FK7472_QUESTION_PERIOD_ID,
            FK7472_PDF_PERIOD_FIELD_ID_PREFIX);

    verify(pdfTextValueGenerator, times(1))
        .generate(FK7472_CERTIFICATE, FK7472_QUESTION_SYMPTOM_ID,
            FK7472_PDF_SYMPTOM_FIELD_ID);

  }

  @Test
  void shouldThrowIllegalStateExceptionIfUnableToFindGeneratorForType() {
    pdfElementValueGenerator = new PdfElementValueGenerator(
        List.of(pdfDateValueGenerator)
    );

    doReturn(PdfValueType.DATE).when(pdfDateValueGenerator).getType();

    pdfElementValueGenerator.getFields(FK7210_CERTIFICATE);

    assertThrows(IllegalStateException.class,
        () -> pdfElementValueGenerator.getFields(FK7472_CERTIFICATE));
  }
}


