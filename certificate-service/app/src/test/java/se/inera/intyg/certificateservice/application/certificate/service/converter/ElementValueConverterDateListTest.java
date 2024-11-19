package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDate;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDateList;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueText;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueType;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateList;

class ElementValueConverterDateListTest {

  private static final String DATE_LIST_ID = "DATE_LIST_ID";

  private static final CertificateDataValueDateList CERTIFICATE_DATA_VALUE = CertificateDataValueDateList.builder()
      .id(DATE_LIST_ID)
      .list(
          List.of(
              CertificateDataValueDate.builder()
                  .date(LocalDate.now())
                  .id("ID")
                  .build()
          )
      )
      .build();

  private ElementValueConverterDateList converter;

  @BeforeEach
  void setUp() {
    converter = new ElementValueConverterDateList();
  }

  @Test
  void shallThrowIfTypeIsNotCertificateDataValueDateList() {
    final var certificateDataTextValue = CertificateDataValueText.builder().build();
    final var illegalStateException = assertThrows(IllegalStateException.class,
        () -> converter.convert(certificateDataTextValue)
    );
    assertTrue(illegalStateException.getMessage().contains("Invalid value type"));
  }

  @Test
  void shallReturnTypeDateList() {
    assertEquals(CertificateDataValueType.DATE_LIST, converter.getType());
  }

  @Test
  void shallReturnDateListId() {
    final var result = converter.convert(CERTIFICATE_DATA_VALUE);
    final var actualResult = (ElementValueDateList) result;
    assertEquals(
        CERTIFICATE_DATA_VALUE.getId(),
        actualResult.dateListId().value()
    );
  }

  @Test
  void shallConvertIdOfDateInList() {
    final var result = converter.convert(CERTIFICATE_DATA_VALUE);
    final var actualResult = (ElementValueDateList) result;
    assertEquals(
        CERTIFICATE_DATA_VALUE.getList().get(0).getId(),
        actualResult.dateList().get(0).dateId().value()
    );
  }

  @Test
  void shallConvertDate() {
    final var result = converter.convert(CERTIFICATE_DATA_VALUE);
    final var actualResult = (ElementValueDateList) result;
    assertEquals(
        CERTIFICATE_DATA_VALUE.getList().get(0).getDate(),
        actualResult.dateList().get(0).date()
    );
  }
}
