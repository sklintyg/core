package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueCode;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueCodeList;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueText;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueType;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCodeList;

class ElementValueConverterCodeListTest {

  private static final String CODE_LIST_ID = "CODE_LIST_ID";

  private static final CertificateDataValueCodeList CERTIFICATE_DATA_VALUE = CertificateDataValueCodeList.builder()
      .id(CODE_LIST_ID)
      .list(
          List.of(
              CertificateDataValueCode.builder()
                  .code("code")
                  .id("ID")
                  .build()
          )
      )
      .build();

  private ElementValueConverterCodeList converter;

  @BeforeEach
  void setUp() {
    converter = new ElementValueConverterCodeList();
  }

  @Test
  void shallThrowIfTypeIsNotCertificateDataValueCodeList() {
    final var certificateDataTextValue = CertificateDataValueText.builder().build();
    final var illegalStateException = assertThrows(IllegalStateException.class,
        () -> converter.convert(certificateDataTextValue)
    );
    assertTrue(illegalStateException.getMessage().contains("Invalid value type"));
  }

  @Test
  void shallReturnTypeCodeList() {
    assertEquals(CertificateDataValueType.CODE_LIST, converter.getType());
  }

  @Test
  void shallReturnCodeListId() {
    final var result = converter.convert(CERTIFICATE_DATA_VALUE);
    final var actualResult = (ElementValueCodeList) result;
    assertEquals(
        CERTIFICATE_DATA_VALUE.getId(),
        actualResult.id().value()
    );
  }

  @Test
  void shallConvertIdOfCodeInList() {
    final var result = converter.convert(CERTIFICATE_DATA_VALUE);
    final var actualResult = (ElementValueCodeList) result;
    assertEquals(
        CERTIFICATE_DATA_VALUE.getList().get(0).getId(),
        actualResult.list().get(0).codeId().value()
    );
  }

  @Test
  void shallConvertCode() {
    final var result = converter.convert(CERTIFICATE_DATA_VALUE);
    final var actualResult = (ElementValueCodeList) result;
    assertEquals(
        CERTIFICATE_DATA_VALUE.getList().get(0).getCode(),
        actualResult.list().get(0).code()
    );
  }
}
