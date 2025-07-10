package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class CertificateDataValueConverterDropdownTest {

  private static final String CODE_ID = "codeId";
  private static final String CODE = "code";
  private CertificateDataValueConverterDropdown certificateDataValueConverterDropdown;

  @BeforeEach
  void setUp() {
    certificateDataValueConverterDropdown = new CertificateDataValueConverterDropdown();
  }

  @Test
  void shallReturnCorrectType() {
    assertEquals(ElementType.DROPDOWN, certificateDataValueConverterDropdown.getType());
  }

  @Test
  void shallThrowIfWrongType() {
    final var elementValue = ElementValueDate.builder().build();
    assertThrows(IllegalStateException.class,
        () -> certificateDataValueConverterDropdown.convert(null, elementValue));
  }

  @Test
  void shallIncludeId() {
    final var elementValueCode = ElementValueCode.builder()
        .codeId(new FieldId(CODE_ID))
        .code(CODE)
        .build();

    final var certificateDataValue = (CertificateDataValueCode) certificateDataValueConverterDropdown.convert(
        null,
        elementValueCode);

    assertEquals(CODE_ID, certificateDataValue.getId());
  }

  @Test
  void shallIncludeCode() {
    final var elementValueCode = ElementValueCode.builder()
        .codeId(new FieldId(CODE_ID))
        .code(CODE)
        .build();

    final var certificateDataValue = (CertificateDataValueCode) certificateDataValueConverterDropdown.convert(
        null,
        elementValueCode);

    assertEquals(CODE, certificateDataValue.getCode());
  }
}