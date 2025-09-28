package se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.mapper.v1;

import se.inera.intyg.certificateanalyticsservice.infrastructure.persistance.entity.PatientEntity;

public class PatientEntityMapperV1 {

  public static PatientEntity map(String patientId) {
    return PatientEntity.builder()
        .patientId(patientId)
        .build();
  }
}
