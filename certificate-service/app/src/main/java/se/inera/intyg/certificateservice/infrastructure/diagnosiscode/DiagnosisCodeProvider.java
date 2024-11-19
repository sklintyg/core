package se.inera.intyg.certificateservice.infrastructure.diagnosiscode;

import java.util.List;
import se.inera.intyg.certificateservice.domain.diagnosiscode.model.Diagnosis;

public interface DiagnosisCodeProvider {

  List<Diagnosis> get();
}
