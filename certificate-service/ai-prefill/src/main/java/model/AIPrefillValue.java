package model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    property = "type")
@JsonSubTypes({
    @Type(value = AIPrefillValueText.class, name = "TEXT"),
    @Type(value = AIPrefillValueCodeList.class, name = "CODE_LIST"),
    @Type(value = AIPrefillValueDiagnosisList.class, name = "DIAGNOSIS_LIST"),
    @Type(value = AIPrefillValueDate.class, name = "DATE"),
    @Type(value = AIPrefillValueDateList.class, name = "DATE_LIST"),
})
public interface AIPrefillValue {
  AIPrefillValueType getType();
}
