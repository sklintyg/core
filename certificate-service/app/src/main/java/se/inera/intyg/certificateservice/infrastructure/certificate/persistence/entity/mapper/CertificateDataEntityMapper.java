package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.elementdata.ElementDataMapper;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.elementdata.MappedElementData;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateDataEntity;

public class CertificateDataEntityMapper {

  private CertificateDataEntityMapper() {
    throw new IllegalStateException("Utility class");
  }

  // TODO: Fix error handling

  public static CertificateDataEntity toEntity(List<ElementData> elements) {
    final var mappedElements = elements.stream()
        .map(ElementDataMapper::toMapped)
        .toList();

    final var out = new ByteArrayOutputStream();
    final var mapper = new ObjectMapper();

    try {
      mapper.writeValue(out, mappedElements);
    } catch (IOException e) {
      throw new IllegalStateException("Error when processing element data to JSON", e);
    }

    final var data = out.toByteArray();

    return CertificateDataEntity.builder()
        .data(data)
        .build();
  }

  public static List<ElementData> toDomain(CertificateDataEntity entity) {
    try {
      final var elements = new ObjectMapper().readValue(entity.getData(),
          new TypeReference<List<MappedElementData>>() {
          });

      return elements.stream()
          .map(ElementDataMapper::toDomain)
          .toList();

    } catch (JsonProcessingException e) {
      throw new IllegalStateException("Error when processing json to ElementData", e);
    }
  }
}
