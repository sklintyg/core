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
        @JsonSubTypes.Type(value = MappedElementValueMedicalInvestigationList.class, name = "MEDICAL_INVESTIGATION_LIST"),
        @JsonSubTypes.Type(value = MappedElementValueCodeList.class, name = "CODE_LIST"),
        @JsonSubTypes.Type(value = MappedElementValueVisualAcuities.class, name = "VISUAL_ACUITIES"),
        @JsonSubTypes.Type(value = MappedElementValueVisualAcuity.class, name = "VISUAL_ACUITY"),
        @JsonSubTypes.Type(value = MappedElementValueDouble.class, name = "DOUBLE"),
        @JsonSubTypes.Type(value = MappedElementValueDateRange.class, name = "DATE_RANGE"),
        @JsonSubTypes.Type(value = MappedElementValueInteger.class, name = "INTEGER"),
        @JsonSubTypes.Type(value = MappedElementValueIcf.class, name = "ICF"),
    }
)
public interface MappedElementValue {

}