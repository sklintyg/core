package se.inera.intyg.certificateservice.domain.diagnosiscode.repository;

import java.util.Optional;
import se.inera.intyg.certificateservice.domain.diagnosiscode.model.Diagnosis;
import se.inera.intyg.certificateservice.domain.diagnosiscode.model.DiagnosisCode;

public interface DiagnosisCodeRepository {

  Optional<Diagnosis> findByCode(DiagnosisCode code);

  Diagnosis getByCode(DiagnosisCode code);
}
