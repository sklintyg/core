package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;

@ExtendWith(MockitoExtension.class)
class CertificateDataCategoryConfigConverterTest {

  @InjectMocks
  CertificateDataCategoryConfigConverter certificateDataCategoryConfigConverter;

  @Test
  void shouldThrowExceptionIfWrongClass() {
    final var elementSpecification = ElementSpecification.builder()
        .configuration(
            ElementConfigurationDate.builder().build()
        )
        .build();

    assertThrows(IllegalStateException.class,
        () -> certificateDataCategoryConfigConverter.convert(elementSpecification)
    );
  }

  @Test
  void shouldReturnCategoryType() {
    assertEquals(ElementType.CATEGORY, certificateDataCategoryConfigConverter.getType());
  }

  @Test
  void shouldReturnConvertedCategory() {
    final var expected = CertificateDataConfigCategory.builder()
        .text("NAME")
        .build();

    final var response = certificateDataCategoryConfigConverter.convert(
        ElementSpecification.builder()
            .configuration(
                ElementConfigurationCategory.builder()
                    .name("NAME")
                    .build()
            ).build()
    );

    assertEquals(expected, response);
  }
}