package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.elementdata.ElementDataMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.elementdata.MappedElementData;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.elementdata.MappedElementValueDate;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateDataEntity;

@ExtendWith(MockitoExtension.class)
class CertificateDataEntityMapperTest {

  private static final LocalDate DATE = LocalDate.parse("2024-02-16");
  private static final FieldId DATE_ID = new FieldId("DATE_ID");
  private static final String ID = "F10";
  @Mock
  private ElementDataMapper elementDataMapper;
  @InjectMocks
  private CertificateDataEntityMapper certificateDataEntityMapper;


  @Test
  void shouldConvertToEntity() {
    final var mappedElementData = MappedElementData.builder()
        .id(ID)
        .value(
            MappedElementValueDate.builder()
                .dateId(DATE_ID.value())
                .date(DATE)
                .build()
        )
        .build();

    final var expected =
        "[{\"id\":\"F10\",\"value\":{\"type\":\"DATE\",\"dateId\":\"DATE_ID\",\"date\":\"2024-02-16\"}}]";

    final var element = ElementData.builder()
        .id(new ElementId(ID))
        .value(ElementValueDate
            .builder()
            .date(DATE)
            .dateId(DATE_ID)
            .build())
        .build();

    doReturn(mappedElementData).when(elementDataMapper).toMapped(element);
    final var response = certificateDataEntityMapper.toEntity(List.of(element));

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

    doReturn(expected.get(0)).when(elementDataMapper).toDomain(any());
    final var response = certificateDataEntityMapper.toDomain(new CertificateDataEntity(json));

    assertEquals(expected, response);
  }

  @Test
  void shouldThrowErrorIfJsonStringIsFormattedWrong() {
    final var json =
        "[{\"id\":\"F10\",\"value\":{\"type\":\"DATE\",\"date\":\"2024\"}}]";
    final var object = new CertificateDataEntity(json);

    assertThrows(IllegalStateException.class,
        () -> certificateDataEntityMapper.toDomain(object)
    );
  }
}
