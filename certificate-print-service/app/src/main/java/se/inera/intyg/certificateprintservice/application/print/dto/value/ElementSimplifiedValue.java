package se.inera.intyg.certificateprintservice.application.print.dto.value;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    property = "type")
@JsonSubTypes({
    @Type(value = ElementSimplifiedValueList.class, name = "LIST"),
    @Type(value = ElementSimplifiedValueText.class, name = "TEXT"),
    @Type(value = ElementSimplifiedValueTable.class, name = "TABLE"),
})
public interface ElementSimplifiedValue {

  ElementSimplifiedType getType();

}