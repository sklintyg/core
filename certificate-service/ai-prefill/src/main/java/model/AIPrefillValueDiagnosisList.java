package model;

import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AIPrefillValueDiagnosisList implements AIPrefillValue {
  
    List<AIPrefillValueDiagnosis> diagnoses;

    @Override
    public AIPrefillValueType getType() {
        return AIPrefillValueType.DIAGNOSIS_LIST;
    }
}

