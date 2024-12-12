package se.inera.intyg.certificateprintservice.print.api.value;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    property = "type")
@JsonSubTypes({
    @Type(value = ElementValueList.class, name = "LIST"),
    @Type(value = ElementValueText.class, name = "TEXT"),
})
public interface ElementValue {

}