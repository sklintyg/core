package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.infrastructure.certificate.persistence.entity.CertificateDataEntity;

public class CertificateDataEntityMapper {

  private CertificateDataEntityMapper() {
    throw new IllegalStateException("Utility class");
  }

  public static CertificateDataEntity toEntity(List<ElementData> entity)
      throws JsonProcessingException {
    final var mapper = new ObjectMapper();
    final var root = mapper.createObjectNode();

    entity.forEach(element -> root.put(element.id().id(), element.value().toString()));

    return new CertificateDataEntity(
        mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root)
    );
  }
}
