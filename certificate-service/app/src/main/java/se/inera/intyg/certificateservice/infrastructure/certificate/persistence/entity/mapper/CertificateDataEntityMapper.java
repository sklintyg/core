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

  public static CertificateDataEntity toEntity(List<ElementData> elements)
      throws JsonProcessingException {
    final var mappedElements = elements.stream()
        .map(ElementDataMapper::toMapped)
        .toList();

    final var out = new ByteArrayOutputStream();
    final var mapper = new ObjectMapper();

    try {
      mapper.writeValue(out, mappedElements);
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }

    final var data = out.toByteArray();

    return CertificateDataEntity.builder()
        .data(data)
        .build();
  }

  public static List<ElementData> toDomain(String jsonString) {
    final List<MappedElementData> elements;
    try {
      elements = new ObjectMapper().readValue(jsonString,
          new TypeReference<List<MappedElementData>>() {
          });
    } catch (JsonProcessingException e) {
      throw new IllegalStateException(e);
    }
    return elements.stream()
        .map(ElementDataMapper::toDomain)
        .toList();
  }
}
