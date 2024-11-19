package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueBoolean;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDate;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueType;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueBoolean;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class ElementValueConverterBooleanTest {

  private static final String ID = "textId";
  private static final CertificateDataValueBoolean.CertificateDataValueBooleanBuilder CERTIFICATE_DATA_VALUE_BOOLEAN_BUILDER = CertificateDataValueBoolean.builder()
      .id(ID);

  private static final FieldId TEXT_ID = new FieldId(ID);
  private ElementValueConverterBoolean elementValueConverterBoolean;

  @BeforeEach
  void setUp() {
    elementValueConverterBoolean = new ElementValueConverterBoolean();
  }

  @Test
  void shallThrowIfTypeIsNotCertificateDataValueBoolean() {
    final var certificateDataValueDate = CertificateDataValueDate.builder().build();
    final var illegalStateException = assertThrows(IllegalStateException.class,
        () -> elementValueConverterBoolean.convert(certificateDataValueDate)
    );
    assertTrue(illegalStateException.getMessage().contains("Invalid value type"));
  }

  @Test
  void shallReturnTypeBoolean() {
    assertEquals(CertificateDataValueType.BOOLEAN, elementValueConverterBoolean.getType());
  }

  @Test
  void shallConvertId() {
    final var certificateDataValueBoolean = CERTIFICATE_DATA_VALUE_BOOLEAN_BUILDER.build();
    final var result = elementValueConverterBoolean.convert(certificateDataValueBoolean);
    final var actualResult = (ElementValueBoolean) result;
    assertEquals(TEXT_ID, actualResult.booleanId());
  }

  @Test
  void shallConvertValueToNull() {
    final var certificateDataValueBoolean = CERTIFICATE_DATA_VALUE_BOOLEAN_BUILDER.build();
    final var result = elementValueConverterBoolean.convert(certificateDataValueBoolean);
    final var actualResult = (ElementValueBoolean) result;
    assertNull(actualResult.value());
  }

  @Test
  void shallConvertValueToTrue() {
    final var certificateDataValueBoolean = CERTIFICATE_DATA_VALUE_BOOLEAN_BUILDER
        .selected(true)
        .build();
    final var result = elementValueConverterBoolean.convert(certificateDataValueBoolean);
    final var actualResult = (ElementValueBoolean) result;
    assertTrue(actualResult.value());
  }

  @Test
  void shallConvertValueToFalse() {
    final var certificateDataValueBoolean = CERTIFICATE_DATA_VALUE_BOOLEAN_BUILDER
        .selected(false)
        .build();
    final var result = elementValueConverterBoolean.convert(certificateDataValueBoolean);
    final var actualResult = (ElementValueBoolean) result;
    assertFalse(actualResult.value());
  }
}
