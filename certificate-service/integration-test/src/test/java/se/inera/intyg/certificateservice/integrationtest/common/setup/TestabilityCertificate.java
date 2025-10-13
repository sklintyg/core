package se.inera.intyg.certificateservice.integrationtest.common.setup;

import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;

@Value
@Builder
public class TestabilityCertificate {

  String type;
  String code;
  String activeVersion;

  String recipient;

  ElementData valueForTest;

  TestabilityAccess testabilityAccess;
}
