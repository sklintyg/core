package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDate;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueText;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueType;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class ElementValueConverterTextTest {

  private static final String TEXT_VALUE = "textValue";
  private static final String ID = "textId";
  private static final CertificateDataValueText CERTIFICATE_DATA_TEXT_VALUE = CertificateDataValueText.builder()
      .id(ID)
      .text(TEXT_VALUE)
      .build();
  private static final FieldId TEXT_ID = new FieldId(ID);
  private ElementValueConverterText elementValueConverterText;

  @BeforeEach
  void setUp() {
    elementValueConverterText = new ElementValueConverterText();
  }

  @Test
  void shallThrowIfTypeIsNotCertificateDataTextValue() {
    final var certificateDataValueDate = CertificateDataValueDate.builder().build();
    final var illegalStateException = assertThrows(IllegalStateException.class,
        () -> elementValueConverterText.convert(certificateDataValueDate)
    );
    assertTrue(illegalStateException.getMessage().contains("Invalid value type"));
  }

  @Test
  void shallReturnTypeText() {
    assertEquals(CertificateDataValueType.TEXT, elementValueConverterText.getType());
  }

  @Test
  void shallConvertId() {
    final var result = elementValueConverterText.convert(CERTIFICATE_DATA_TEXT_VALUE);
    final var actualResult = (ElementValueText) result;
    assertEquals(TEXT_ID, actualResult.textId());
  }

  @Test
  void shallConvertText() {
    final var result = elementValueConverterText.convert(CERTIFICATE_DATA_TEXT_VALUE);
    final var actualResult = (ElementValueText) result;
    assertEquals(TEXT_VALUE, actualResult.text());
  }

}