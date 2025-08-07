package se.inera.intyg.certificateservice.application.certificate.dto.value;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    property = "type")
@JsonSubTypes({
    @Type(value = CertificateDataValueDate.class, name = "DATE"),
    @Type(value = CertificateDataValueText.class, name = "TEXT"),
    @Type(value = CertificateDataValueDateRange.class, name = "DATE_RANGE"),
    @Type(value = CertificateDataValueDateRangeList.class, name = "DATE_RANGE_LIST"),
    @Type(value = CertificateDataValueDateList.class, name = "DATE_LIST"),
    @Type(value = CertificateDataValueCode.class, name = "CODE"),
    @Type(value = CertificateDataValueBoolean.class, name = "BOOLEAN"),
    @Type(value = CertificateDataValueDiagnosisList.class, name = "DIAGNOSIS_LIST"),
    @Type(value = CertificateDataValueDiagnosis.class, name = "DIAGNOSIS"),
    @Type(value = CertificateDataValueBoolean.class, name = "BOOLEAN"),
    @Type(value = CertificateDataValueMedicalInvestigation.class, name = "MEDICAL_INVESTIGATION"),
    @Type(value = CertificateDataValueMedicalInvestigationList.class, name = "MEDICAL_INVESTIGATION_LIST"),
    @Type(value = CertificateDataValueCodeList.class, name = "CODE_LIST"),
    @Type(value = CertificateDataValueVisualAcuities.class, name = "VISUAL_ACUITIES"),
    @Type(value = CertificateDataValueVisualAcuity.class, name = "VISUAL_ACUITY"),
    @Type(value = CertificateDataValueDouble.class, name = "DOUBLE"),
    @Type(value = CertificateDataValueInteger.class, name = "INTEGER"),
    @Type(value = CertificateDataIcfValue.class, name = "ICF"),
})
public interface CertificateDataValue {

  CertificateDataValueType getType();
}