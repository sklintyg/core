package se.inera.intyg.certificateservice.infrastructure.certificate.persistence.elementdata;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes(
    {
        @JsonSubTypes.Type(value = MappedElementValueDate.class, name = "DATE"),
        @JsonSubTypes.Type(value = MappedElementValueIssuingUnit.class, name = "ISSUING_UNIT"),
        @JsonSubTypes.Type(value = MappedElementValueText.class, name = "TEXT"),
        @JsonSubTypes.Type(value = MappedElementValueDateRangeList.class, name = "DATE_RANGE_LIST"),
        @JsonSubTypes.Type(value = MappedElementValueDateList.class, name = "DATE_LIST"),
        @JsonSubTypes.Type(value = MappedElementValueCode.class, name = "CODE"),
        @JsonSubTypes.Type(value = MappedElementValueBoolean.class, name = "BOOLEAN"),
        @JsonSubTypes.Type(value = MappedElementValueDiagnosisList.class, name = "DIAGNOSIS_LIST"),
    }
)
public interface MappedElementValue {

}
