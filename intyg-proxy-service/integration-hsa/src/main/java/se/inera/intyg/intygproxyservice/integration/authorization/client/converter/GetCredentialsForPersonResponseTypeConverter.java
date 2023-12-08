package se.inera.intyg.intygproxyservice.integration.authorization.client.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.CredentialsForPerson;
import se.riv.infrastructure.directory.authorizationmanagement.gethospcredentialsforpersonresponder.v1.GetHospCredentialsForPersonResponseType;
import se.riv.infrastructure.directory.authorizationmanagement.v2.IIType;

@Service
@RequiredArgsConstructor
public class GetCredentialsForPersonResponseTypeConverter {

  private final NursePrescriptionRightTypeConverter nursePrescriptionRightTypeConverter;
  private final HCPSpecialityCodeTypeConverter hcpSpecialityCodeTypeConverter;
  private final HealthCareProfessionalLicenceTypeConverter healthCareProfessionalLicenceTypeConverter;
  private final RestrictionTypeConverter restrictionTypeConverter;

  public CredentialsForPerson convert(GetHospCredentialsForPersonResponseType type) {
    if (type == null) {
      return CredentialsForPerson.builder().build();
    }

    return CredentialsForPerson.builder()
        .feignedPerson(type.isFeignedPerson())
        .educationCode(type.getEducationCode())
        .personalIdentityNumber(convertPersonalIdentity(type.getPersonalIdentityNumber()))
        .personalPrescriptionCode(type.getPersonalPrescriptionCode())
        .nursePrescriptionRight(
            type.getNursePrescriptionRight()
                .stream()
                .map(nursePrescriptionRightTypeConverter::convert)
                .toList()
        )
        .healthcareProfessionalLicenseIdentityNumber(
            type.getHealthcareProfessionalLicenseIdentityNumber())
        .healthCareProfessionalLicenceSpeciality(
            type.getHealthCareProfessionalLicenceSpeciality()
                .stream()
                .map(hcpSpecialityCodeTypeConverter::convert)
                .toList()
        )
        .healthCareProfessionalLicence(
            type.getHealthCareProfessionalLicence()
                .stream()
                .map(healthCareProfessionalLicenceTypeConverter::convert)
                .toList()
        )
        .restrictions(
            type.getRestrictions()
                .stream()
                .map(restrictionTypeConverter::convert)
                .toList()
        )
        .build();
  }

  private String convertPersonalIdentity(IIType type) {
    return type == null ? null : type.getExtension();
  }
}
