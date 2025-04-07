package model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AIPrefillValueText implements AIPrefillValue {
  
    String text;
    AIPrefillValueType type = AIPrefillValueType.TEXT;

    @Override
    public AIPrefillValueType getType() {
        return type;
    }
}

