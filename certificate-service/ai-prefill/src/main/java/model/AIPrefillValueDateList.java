package model;

import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AIPrefillValueDateList implements AIPrefillValue {
  
    List<AIPrefillValueDate> dates;

    @Override
    public AIPrefillValueType getType() {
        return AIPrefillValueType.DATE_LIST;
    }
}

