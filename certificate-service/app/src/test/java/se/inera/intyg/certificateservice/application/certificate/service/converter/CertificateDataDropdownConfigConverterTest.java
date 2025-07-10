package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.FK7810_CERTIFICATE;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigDropdown;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDropdownCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.common.model.Code;

class CertificateDataDropdownConfigConverterTest {

  private CertificateDataDropdownConfigConverter converter;

  private static final ElementSpecification ELEMENT_SPECIFICATION = ElementSpecification.builder()
      .configuration(
          ElementConfigurationDropdownCode.builder()
              .name("NAME")
              .list(List.of(
                  new ElementConfigurationCode(
                      new FieldId("ID_1"),
                      "Display Name 1",
                      new Code("CODE", "CODE_SYSTEM", "DISPLAY_NAME")
                  ),
                  new ElementConfigurationCode(
                      new FieldId("ID_2"),
                      "Display Name 2",
                      new Code("CODE", "CODE_SYSTEM", "DISPLAY_NAME")

                  ),
                  new ElementConfigurationCode(
                      new FieldId("ID_3"),
                      "Display Name 3",
                      new Code("CODE", "CODE_SYSTEM", "DISPLAY_NAME")
                  )
              ))
              .build()
      ).build();

  @BeforeEach
  void setUp() {
    converter = new CertificateDataDropdownConfigConverter();
  }

  @Test
  void shouldThrowExceptionIfWrongClass() {
    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationDate.builder().build()
        )
        .build();

    assertThrows(IllegalStateException.class,
        () -> converter.convert(elementSpecification,
            FK7810_CERTIFICATE)
    );
  }

  @Test
  void shouldReturnDropdownType() {
    assertEquals(ElementType.DROPDOWN, converter.getType());
  }


  @Test
  void shouldSetCorrectValuesForList() {
    final CertificateDataConfigDropdown result = (CertificateDataConfigDropdown)
        converter.convert(ELEMENT_SPECIFICATION, FK7810_CERTIFICATE);

    final var listItem1 = result.getList().get(0);
    final var listItem2 = result.getList().get(1);
    final var listItem3 = result.getList().get(2);

    assertAll(
        () -> assertEquals("ID_1", listItem1.getId()),
        () -> assertEquals("Display Name 1", listItem1.getLabel()),
        () -> assertEquals("ID_2", listItem2.getId()),
        () -> assertEquals("Display Name 2", listItem2.getLabel()),
        () -> assertEquals("ID_3", listItem3.getId()),
        () -> assertEquals("Display Name 3", listItem3.getLabel())
    );
  }
}