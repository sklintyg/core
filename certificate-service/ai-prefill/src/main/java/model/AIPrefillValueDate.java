package model;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AIPrefillValueDate implements  AIPrefillValue {
  
    String id;
    LocalDate date;

    @Override
    public AIPrefillValueType getType() {
        return AIPrefillValueType.DATE;
    }
}

