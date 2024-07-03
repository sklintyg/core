package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueCodeList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCode;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCodeList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxMultipleCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class CertificateDataValueConverterCheckboxMultipleCodeTest {

  private static final String CODE_LIST_ID = "CODE_LIST_ID";

  private final CertificateDataValueConverterCheckboxMultipleCode converter =
      new CertificateDataValueConverterCheckboxMultipleCode();
  private ElementSpecification configuration;

  @BeforeEach
  void setUp() {
    configuration = ElementSpecification.builder()
        .configuration(
            ElementConfigurationCheckboxMultipleCode.builder()
                .id(new FieldId(CODE_LIST_ID))
                .build()
        )
        .build();
  }

  @Test
  void shallThrowExceptionIfWrongClassOfValue() {
    final var elementValue = ElementValueText.builder().build();

    assertThrows(IllegalStateException.class,
        () -> converter.convert(configuration, elementValue)
    );
  }

  @Test
  void shallNotThrowExceptionForWrongClassOfValueIfElementValueIsNull() {
    final var result = converter.convert(configuration, null);
    assertEquals(Collections.emptyList(), ((CertificateDataValueCodeList) result).getList());
  }

  @Test
  void shallReturnType() {
    assertEquals(ElementType.CHECKBOX_MULTIPLE_CODE, converter.getType());
  }

  @Test
  void shallReturnCodeListId() {
    final var elementValue = ElementValueCodeList.builder().build();

    final var result = converter.convert(configuration, elementValue);

    assertEquals(CODE_LIST_ID, ((CertificateDataValueCodeList) result).getId());
  }

  @Test
  void shallCreateCertificateDataValueCode() {
    final var elementValue = ElementValueCodeList.builder().build();

    final var result = converter.convert(configuration, elementValue);

    assertInstanceOf(CertificateDataValueCodeList.class, result);
  }

  @Test
  void shallSetCorrectIdForCodeValue() {
    final var elementValue = ElementValueCodeList.builder()
        .list(
            List.of(
                ElementValueCode.builder()
                    .codeId(new FieldId("CODE_ID"))
                    .code("code")
                    .build()
            )
        )
        .build();

    final var result = converter.convert(configuration, elementValue);

    assertEquals(elementValue.list().get(0).codeId().value(),
        ((CertificateDataValueCodeList) result).getList().get(0).getId()
    );
  }

  @Test
  void shallSetCorrectCodeForCodeValue() {
    final var elementValue = ElementValueCodeList.builder()
        .list(
            List.of(
                ElementValueCode.builder()
                    .codeId(new FieldId("CODE_ID"))
                    .code("code")
                    .build()
            )
        )
        .build();

    final var result = converter.convert(configuration, elementValue);

    assertEquals(elementValue.list().get(0).code(),
        ((CertificateDataValueCodeList) result).getList().get(0).getCode()
    );
  }

  @Test
  void shallSetValueToEmpty() {
    final var elementValue = ElementValueCodeList.builder().build();

    final var result = converter.convert(configuration, elementValue);

    assertTrue(((CertificateDataValueCodeList) result).getList().isEmpty(),
        "If no value is provided value should be empty list");
  }
}
