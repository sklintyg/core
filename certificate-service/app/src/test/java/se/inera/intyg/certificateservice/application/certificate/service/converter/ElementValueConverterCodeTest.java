package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueCode;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueText;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueType;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class ElementValueConverterCodeTest {

  private static final String ID = "CODE_ID";
  private static final String CODE = "CODE";
  private static final CertificateDataValueCode CERTIFICATE_DATA_VALUE_CODE = CertificateDataValueCode.builder()
      .id(ID)
      .code(CODE)
      .build();

  private static final FieldId CODE_ID = new FieldId(ID);
  private ElementValueConverterCode elementValueConverterCode;

  @BeforeEach
  void setUp() {
    elementValueConverterCode = new ElementValueConverterCode();
  }

  @Test
  void shallThrowIfTypeIsNotCertificateDataValueCode() {
    final var certificateDataTextValue = CertificateDataValueText.builder().build();
    final var illegalStateException = assertThrows(IllegalStateException.class,
        () -> elementValueConverterCode.convert(certificateDataTextValue)
    );
    assertTrue(illegalStateException.getMessage().contains("Invalid value type"));
  }

  @Test
  void shallReturnTypeDate() {
    assertEquals(CertificateDataValueType.CODE, elementValueConverterCode.getType());
  }

  @Test
  void shallConvertId() {
    final var result = elementValueConverterCode.convert(CERTIFICATE_DATA_VALUE_CODE);
    final var actualResult = (ElementValueCode) result;
    assertEquals(CODE_ID, actualResult.codeId());
  }

  @Test
  void shallConvertCode() {
    final var result = elementValueConverterCode.convert(CERTIFICATE_DATA_VALUE_CODE);
    final var actualResult = (ElementValueCode) result;
    assertEquals(CODE, actualResult.code());
  }
}
