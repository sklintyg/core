package model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@JsonTypeName("DATE_LIST")
public class AIPrefillValueDateList implements AIPrefillValue {
  
    List<AIPrefillValueDate> dates;
    AIPrefillValueType type = AIPrefillValueType.DATE_LIST;

    @Override
    public AIPrefillValueType getType() {
        return type;
    }
}

