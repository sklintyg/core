package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import java.time.Period;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import lombok.Value;
import se.inera.intyg.certificateservice.domain.common.model.Code;

@Value
@Builder
public class MedicalInvestigationConfig {

  FieldId id;
  FieldId investigationTypeId;
  FieldId informationSourceId;
  FieldId dateId;
  List<Code> typeOptions;
  Period max;
  Period min;
  @Builder.Default
  Map<String, Code> legacyMapping = Collections.emptyMap();

}