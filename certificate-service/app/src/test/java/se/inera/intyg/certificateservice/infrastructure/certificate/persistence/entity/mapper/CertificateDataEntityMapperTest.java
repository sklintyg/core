package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateDataEntity;

class CertificateDataEntityMapperTest {

  private static final LocalDate DATE = LocalDate.parse("2024-02-16");
  private static final FieldId DATE_ID = new FieldId("DATE_ID");

  @Test
  void shouldConvertToEntity() {
    final var expected =
        "[{\"id\":\"F10\",\"value\":{\"type\":\"DATE\",\"dateId\":\"DATE_ID\",\"date\":\"2024-02-16\"}}]";
    final var element = ElementData.builder()
        .id(new ElementId("F10"))
        .value(ElementValueDate
            .builder()
            .date(DATE)
            .dateId(DATE_ID)
            .build())
        .build();

    final var response = CertificateDataEntityMapper.toEntity(List.of(element));

    assertEquals(expected, response.getData());
  }

  @Test
  void shouldConvertToDomain() {
    final var json =
        "[{\"id\":\"F10\",\"value\":{\"type\":\"DATE\",\"date\":\"2024-02-16\",\"dateId\":\"DATE_ID\"}}]";
    final var expected = List.of(
        ElementData.builder()
            .id(new ElementId("F10"))
            .value(ElementValueDate
                .builder()
                .date(DATE)
                .dateId(DATE_ID)
                .build())
            .build()
    );

    final var response = CertificateDataEntityMapper.toDomain(new CertificateDataEntity(json));

    assertEquals(expected, response);
  }

  @Test
  void shouldThrowErrorIfJsonStringIsFormattedWrong() {
    final var json =
        "[{\"id\":\"F10\",\"value\":{\"type\":\"DATE\",\"date\":\"2024\"}}]";
    final var object = new CertificateDataEntity(json);

    assertThrows(IllegalStateException.class,
        () -> CertificateDataEntityMapper.toDomain(object)
    );
  }
}