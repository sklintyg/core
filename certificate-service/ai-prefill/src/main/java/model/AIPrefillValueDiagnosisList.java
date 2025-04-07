package model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonTypeName("DIAGNOSIS_LIST")
public class AIPrefillValueDiagnosisList implements AIPrefillValue {
  
    List<AIPrefillValueDiagnosis> diagnoses;
    AIPrefillValueType type = AIPrefillValueType.DIAGNOSIS_LIST;

    @Override
    public AIPrefillValueType getType() {
        return type;
    }
}

