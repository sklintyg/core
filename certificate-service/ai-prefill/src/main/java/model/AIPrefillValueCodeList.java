package model;

import lombok.Builder;
import lombok.Value;
import java.util.List;

@Value
@Builder
public class AIPrefillValueCodeList implements AIPrefillValue {
  
    List<String> codes;

    @Override
    public AIPrefillValueType getType() {
        return AIPrefillValueType.CODE_LIST;
    }
}

