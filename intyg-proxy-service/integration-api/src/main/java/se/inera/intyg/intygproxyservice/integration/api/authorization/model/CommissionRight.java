package se.inera.intyg.intygproxyservice.integration.api.authorization.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CommissionRight {

  String activity;
  String informationClass;
  String scope;
}
