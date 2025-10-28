package se.inera.intyg.certificateservice.infrastructure.certificatemodel.common.codesystems;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementDiagnosisTerminology;

public class CodeSystemIcd10Se {

  private CodeSystemIcd10Se() {
    throw new IllegalStateException("Utility class");
  }

  private static final String DEPRECATED_ICD_10_SE_CODE_SYSTEM = "1.2.752.116.1.1.1.1.8";
  private static final String DEPRECATED_LEGACY_ICD_10_SE_CODE_SYSTEM = "1.2.752.116.1.1.1.1.3";
  public static final String ICD_10_SE_CODE_SYSTEM = "1.2.752.116.1.1.1";
  public static final String DIAGNOS_ICD_10_ID = "ICD_10_SE";
  private static final String DIAGNOS_ICD_10_LABEL = "ICD-10-SE";

  /**
   * @deprecated Use {@link #terminology()} for all new certificate types. This method returns a
   * deprecated ICD-10-SE code system OID that was faulty.
   */
  @Deprecated
  public static ElementDiagnosisTerminology deprecatedTerminology() {
    return new ElementDiagnosisTerminology(
        DIAGNOS_ICD_10_ID,
        DIAGNOS_ICD_10_LABEL,
        DEPRECATED_ICD_10_SE_CODE_SYSTEM,
        List.of(
            ICD_10_SE_CODE_SYSTEM,
            DEPRECATED_LEGACY_ICD_10_SE_CODE_SYSTEM
        ));
  }

  public static ElementDiagnosisTerminology terminology() {
    return new ElementDiagnosisTerminology(
        DIAGNOS_ICD_10_ID,
        DIAGNOS_ICD_10_LABEL,
        ICD_10_SE_CODE_SYSTEM,
        List.of(
            DEPRECATED_ICD_10_SE_CODE_SYSTEM,
            DEPRECATED_LEGACY_ICD_10_SE_CODE_SYSTEM
        )
    );
  }
}
