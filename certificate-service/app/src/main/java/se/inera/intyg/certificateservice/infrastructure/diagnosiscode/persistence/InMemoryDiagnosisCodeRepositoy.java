package se.inera.intyg.certificateservice.infrastructure.diagnosiscode.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import se.inera.intyg.certificateservice.domain.diagnosiscode.model.Diagnosis;
import se.inera.intyg.certificateservice.domain.diagnosiscode.model.DiagnosisCode;
import se.inera.intyg.certificateservice.domain.diagnosiscode.repository.DiagnosisCodeRepository;
import se.inera.intyg.certificateservice.infrastructure.diagnosiscode.DiagnosisCodeProvider;

@Slf4j
@Repository
@RequiredArgsConstructor
public class InMemoryDiagnosisCodeRepositoy implements DiagnosisCodeRepository {

  private Map<DiagnosisCode, Diagnosis> diagnosesMap;
  private final List<DiagnosisCodeProvider> diagnosisCodeProviders;

  @Override
  public Optional<Diagnosis> findByCode(DiagnosisCode code) {
    if (!getDiagnosesMap().containsKey(code)) {
      return Optional.empty();
    }
    return Optional.of(getDiagnosesMap().get(code));
  }

  @Override
  public Diagnosis getByCode(DiagnosisCode code) {
    if (!getDiagnosesMap().containsKey(code)) {
      throw new IllegalArgumentException(
          "Diagnosis value missing: %s".formatted(code.value())
      );
    }
    return getDiagnosesMap().get(code);
  }

  private Map<DiagnosisCode, Diagnosis> getDiagnosesMap() {
    if (diagnosesMap == null) {
      log.info("Initiate diagnosis value repository");
      diagnosesMap = new HashMap<>();
      diagnosisCodeProviders.forEach(diagnosisCodeProvider ->
          diagnosisCodeProvider.get()
              .forEach(diagnosis -> diagnosesMap.put(diagnosis.code(), diagnosis))
      );
      log.info("Loaded '{}' diagnoses to repository", diagnosesMap.size());
    }
    return diagnosesMap;
  }
}
