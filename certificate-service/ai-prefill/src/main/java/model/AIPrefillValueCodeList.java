package model;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Builder;
import lombok.Value;
import java.util.List;

@Value
@Builder
public class AIPrefillValueCodeList implements AIPrefillValue {
  
    List<String> codes;
    AIPrefillValueType type = AIPrefillValueType.CODE_LIST;

    @Override
    public AIPrefillValueType getType() {
        return type;
    }
}

