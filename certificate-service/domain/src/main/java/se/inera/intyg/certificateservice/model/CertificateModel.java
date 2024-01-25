package se.inera.intyg.certificateservice.model;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CertificateModel {

  CertificateModelId id;
  String name;
  String description;
  LocalDateTime activeFrom;
  List<CertificateActionSpecification> certificateActionSpecifications;
}
