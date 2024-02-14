package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateDataEntity;

class CertificateDataEntityMapperTest {

  private static final LocalDate NOW = LocalDate.now();

  @Test
  void shouldConvertToEntity() {
    final var expected =
        "[{\"id\":\"F10\",\"value\":{\"type\":\"DATE\",\"date\":[" + NOW.getYear() + ","
            + NOW.getMonthValue() + "," + NOW.getDayOfMonth() + "]}}]";
    final var element = ElementData.builder()
        .id(new ElementId("F10"))
        .value(ElementValueDate
            .builder()
            .date(NOW)
            .build())
        .build();

    final var response = CertificateDataEntityMapper.toEntity(List.of(element));

    assertEquals(expected, response.getData());
  }

  @Test
  void shouldConvertToDomain() {
    final var json =
        "[{\"id\":\"F10\",\"value\":{\"type\":\"DATE\",\"date\":[" + NOW.getYear() + ","
            + NOW.getMonthValue() + "," + NOW.getDayOfMonth() + "]}}]";
    final var expected = List.of(
        ElementData.builder()
            .id(new ElementId("F10"))
            .value(ElementValueDate
                .builder()
                .date(NOW)
                .build())
            .build()
    );

    final var response = CertificateDataEntityMapper.toDomain(new CertificateDataEntity(json));

    assertEquals(expected, response);
  }

  @Test
  void shouldThrowErrorIfJsonStringIsFormattedWrong() {
    final var json =
        "[{\"id\":\"F10\",\"value\":{\"type\":\"DATE\",\"date\":[" + NOW.getYear() + ",";
    final var object = new CertificateDataEntity(json);

    assertThrows(IllegalStateException.class,
        () -> CertificateDataEntityMapper.toDomain(object));
  }

}