package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk3226;

import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementDiagnosisTerminology;

public class CodeSystemIcd10Se {

  private CodeSystemIcd10Se() {
    throw new IllegalStateException("Utility class");
  }

  public static final String ICD_10_SE_CODE_SYSTEM = "1.2.752.116.1.1.1.1.8";
  public static final String DIAGNOS_ICD_10_ID = "ICD_10_SE";
  private static final String DIAGNOS_ICD_10_LABEL = "ICD-10-SE";

  public static ElementDiagnosisTerminology terminology() {
    return new ElementDiagnosisTerminology(
        DIAGNOS_ICD_10_ID,
        DIAGNOS_ICD_10_LABEL,
        ICD_10_SE_CODE_SYSTEM
    );
  }
}
