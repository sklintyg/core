package model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AIPrefillValueText implements AIPrefillValue {
  
    String text;

    @Override
    public AIPrefillValueType getType() {
        return AIPrefillValueType.TEXT;
    }
}

