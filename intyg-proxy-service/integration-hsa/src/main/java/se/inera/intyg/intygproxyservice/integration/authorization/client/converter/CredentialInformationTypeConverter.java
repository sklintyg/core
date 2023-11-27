package se.inera.intyg.intygproxyservice.integration.authorization.client.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.CredentialInformation;
import se.riv.infrastructure.directory.authorizationmanagement.v2.CredentialInformationType;
import se.riv.infrastructure.directory.authorizationmanagement.v2.IIType;

@Component
@RequiredArgsConstructor
public class CredentialInformationTypeConverter {

  private final CommissionTypeConverter commissionTypeConverter;
  private final NursePrescriptionRightTypeConverter nursePrescriptionRightTypeConverter;
  private final HCPSpecialityCodeTypeConverter hcpSpecialityCodeTypeConverter;
  private final HsaSystemRoleTypeConverter hsaSystemRoleTypeConverter;

  public CredentialInformation convert(CredentialInformationType type) {
    return CredentialInformation.builder()
        .personHsaId(type.getPersonHsaId())
        .personalIdentity(convertPersonalIdentity(type.getPersonalIdentity()))
        .givenName(type.getGivenName())
        .middleAndSurName(type.getMiddleAndSurName())
        .feignedPerson(type.isFeignedPerson())
        .groupPrescriptionCode(type.getGroupPrescriptionCode())
        .occupationalCode(type.getOccupationalCode())
        .healthCareProfessionalLicence(type.getHealthCareProfessionalLicence())
        .healthCareProfessionalLicenceCode(type.getHealthCareProfessionalLicenceCode())
        .healthcareProfessionalLicenseIdentityNumber(
            type.getHealthcareProfessionalLicenseIdentityNumber()
        )
        .paTitleCode(type.getPaTitleCode())
        .protectedPerson(type.isProtectedPerson())
        .occupationalCode(type.getOccupationalCode())
        .personalPrescriptionCode(type.getPersonalPrescriptionCode())
        .commission(
            type.getCommission().stream()
                .map(commissionTypeConverter::convert)
                .toList()
        )
        .nursePrescriptionRight(
            type.getNursePrescriptionRight().stream()
                .map(nursePrescriptionRightTypeConverter::convert)
                .toList()
        )
        .healthCareProfessionalLicenceSpeciality(
            type.getHealthCareProfessionalLicenceSpeciality().stream()
                .map(hcpSpecialityCodeTypeConverter::convert)
                .toList()
        )
        .hsaSystemRole(
            type.getHsaSystemRole().stream()
                .map(hsaSystemRoleTypeConverter::convert)
                .toList()
        )
        .build();
  }

  private String convertPersonalIdentity(IIType type) {
    return type == null ? "" : type.getExtension();
  }
}
