package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDate;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueText;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueType;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class ElementValueConverterDateTest {

  private static final LocalDate DATE_VALUE = LocalDate.now();
  private static final String ID = "dateId";
  private static final CertificateDataValueDate CERTIFICATE_DATA_VALUE_DATE = CertificateDataValueDate.builder()
      .id(ID)
      .date(DATE_VALUE)
      .build();
  private static final FieldId DATE_ID = new FieldId(ID);
  private ElementValueConverterDate elementValueConverterDate;

  @BeforeEach
  void setUp() {
    elementValueConverterDate = new ElementValueConverterDate();
  }

  @Test
  void shallThrowIfTypeIsNotCertificateDataValueDate() {
    final var certificateDataTextValue = CertificateDataValueText.builder().build();
    final var illegalStateException = assertThrows(IllegalStateException.class,
        () -> elementValueConverterDate.convert(certificateDataTextValue)
    );
    assertTrue(illegalStateException.getMessage().contains("Invalid value type"));
  }

  @Test
  void shallReturnTypeDate() {
    assertEquals(CertificateDataValueType.DATE, elementValueConverterDate.getType());
  }

  @Test
  void shallConvertId() {
    final var result = elementValueConverterDate.convert(CERTIFICATE_DATA_VALUE_DATE);
    final var actualResult = (ElementValueDate) result;
    assertEquals(DATE_ID, actualResult.dateId());
  }

  @Test
  void shallConvertDate() {
    final var result = elementValueConverterDate.convert(CERTIFICATE_DATA_VALUE_DATE);
    final var actualResult = (ElementValueDate) result;
    assertEquals(DATE_VALUE, actualResult.date());
  }
}
