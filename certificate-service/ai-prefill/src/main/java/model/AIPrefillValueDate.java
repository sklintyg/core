package model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonTypeName("DATE")
public class AIPrefillValueDate implements  AIPrefillValue {
  
    String id;
    LocalDate date;
    AIPrefillValueType type = AIPrefillValueType.DATE;

    @Override
    public AIPrefillValueType getType() {
        return type;
    }
}

