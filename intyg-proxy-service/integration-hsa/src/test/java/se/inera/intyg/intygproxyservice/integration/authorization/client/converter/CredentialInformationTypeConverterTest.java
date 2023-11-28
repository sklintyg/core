package se.inera.intyg.intygproxyservice.integration.authorization.client.converter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.Commission;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.CredentialInformation;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.HCPSpecialityCodes;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.HsaSystemRole;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.NursePrescriptionRight;
import se.riv.infrastructure.directory.authorizationmanagement.v2.CommissionType;
import se.riv.infrastructure.directory.authorizationmanagement.v2.CredentialInformationType;
import se.riv.infrastructure.directory.authorizationmanagement.v2.HCPSpecialityCodesType;
import se.riv.infrastructure.directory.authorizationmanagement.v2.HsaSystemRoleType;
import se.riv.infrastructure.directory.authorizationmanagement.v2.IIType;
import se.riv.infrastructure.directory.authorizationmanagement.v2.NursePrescriptionRightType;

@ExtendWith(MockitoExtension.class)
class CredentialInformationTypeConverterTest {

  @Mock
  CommissionTypeConverter commissionTypeConverter;

  @Mock
  NursePrescriptionRightTypeConverter nursePrescriptionRightTypeConverter;

  @Mock
  HCPSpecialityCodeTypeConverter hcpSpecialityCodeTypeConverter;

  @Mock
  HsaSystemRoleTypeConverter hsaSystemRoleTypeConverter;

  @InjectMocks
  CredentialInformationTypeConverter credentialInformationTypeConverter;

  @Test
  void shouldConvertNull() {
    final var response = credentialInformationTypeConverter.convert(null);

    assertEquals(CredentialInformation.builder().build(), response);
  }

  @Test
  void shouldConvertHsaId() {
    final var type = getType();

    final var response = credentialInformationTypeConverter.convert(type);

    assertEquals(type.getPersonHsaId(), response.getPersonHsaId());
  }

  @Test
  void shouldConvertName() {
    final var type = getType();

    final var response = credentialInformationTypeConverter.convert(type);

    assertEquals(type.getGivenName(), response.getGivenName());
  }

  @Test
  void shouldConvertSurname() {
    final var type = getType();

    final var response = credentialInformationTypeConverter.convert(type);

    assertEquals(type.getMiddleAndSurName(), response.getMiddleAndSurName());
  }

  @Test
  void shouldConvertFeignedPerson() {
    final var type = getType();

    final var response = credentialInformationTypeConverter.convert(type);

    assertEquals(type.isFeignedPerson(), response.getFeignedPerson());
  }

  @Test
  void shouldConvertGroupPrescription() {
    final var type = mock(CredentialInformationType.class);
    when(type.getGroupPrescriptionCode())
        .thenReturn(List.of("String1"));

    final var response = credentialInformationTypeConverter.convert(type);

    assertEquals(type.getGroupPrescriptionCode(), response.getGroupPrescriptionCode());
  }

  @Test
  void shouldConvertLicense() {
    final var type = mock(CredentialInformationType.class);
    when(type.getHealthCareProfessionalLicence())
        .thenReturn(List.of("String1"));

    final var response = credentialInformationTypeConverter.convert(type);

    assertEquals(type.getHealthCareProfessionalLicence(),
        response.getHealthCareProfessionalLicence());
  }

  @Test
  void shouldConvertLicenseCode() {
    final var type = mock(CredentialInformationType.class);
    when(type.getHealthCareProfessionalLicenceCode())
        .thenReturn(List.of("String1"));

    final var response = credentialInformationTypeConverter.convert(type);

    assertEquals(type.getHealthCareProfessionalLicenceCode(),
        response.getHealthCareProfessionalLicenceCode());
  }

  @Test
  void shouldConvertLicenseId() {
    final var type = mock(CredentialInformationType.class);
    when(type.getHealthCareProfessionalLicence())
        .thenReturn(List.of("String1"));

    final var response = credentialInformationTypeConverter.convert(type);

    assertEquals(type.getHealthcareProfessionalLicenseIdentityNumber(),
        response.getHealthcareProfessionalLicenseIdentityNumber());
  }

  @Test
  void shouldConvertPaTitleCode() {
    final var type = mock(CredentialInformationType.class);
    when(type.getPaTitleCode())
        .thenReturn(List.of("String1"));

    final var response = credentialInformationTypeConverter.convert(type);

    assertEquals(type.getPaTitleCode(), response.getPaTitleCode());
  }

  @Test
  void shouldConvertOccupationalCode() {
    final var type = mock(CredentialInformationType.class);
    when(type.getOccupationalCode())
        .thenReturn(List.of("String1"));

    final var response = credentialInformationTypeConverter.convert(type);

    assertEquals(type.getOccupationalCode(), response.getOccupationalCode());
  }

  @Test
  void shouldConvertPersonId() {
    final var personalIdentity = new IIType();
    personalIdentity.setExtension("EXT");
    personalIdentity.setRoot("ROOT");
    final var type = mock(CredentialInformationType.class);
    when(type.getPersonalIdentity())
        .thenReturn(personalIdentity);

    final var response = credentialInformationTypeConverter.convert(type);

    assertEquals(personalIdentity.getExtension(), response.getPersonalIdentity());
  }

  @Test
  void shouldConvertProtectedPerson() {
    final var type = getType();

    final var response = credentialInformationTypeConverter.convert(type);

    assertEquals(type.isProtectedPerson(), response.getProtectedPerson());
  }

  @Test
  void shouldConvertPersonalCode() {
    final var type = getType();

    final var response = credentialInformationTypeConverter.convert(type);

    assertEquals(type.getPersonalPrescriptionCode(), response.getPersonalPrescriptionCode());
  }

  @Test
  void shouldConvertCommission() {
    final var expectedCommission = Commission.builder().build();
    final var type = mock(CredentialInformationType.class);
    when(type.getCommission())
        .thenReturn(List.of(new CommissionType()));
    when(commissionTypeConverter.convert(any(CommissionType.class)))
        .thenReturn(expectedCommission);

    final var response = credentialInformationTypeConverter.convert(type);

    assertEquals(expectedCommission, response.getCommission().get(0));
  }

  @Test
  void shouldConvertNurseRight() {
    final var expected = NursePrescriptionRight.builder().build();
    final var type = mock(CredentialInformationType.class);
    when(type.getNursePrescriptionRight())
        .thenReturn(List.of(new NursePrescriptionRightType()));
    when(nursePrescriptionRightTypeConverter.convert(any(NursePrescriptionRightType.class)))
        .thenReturn(expected);

    final var response = credentialInformationTypeConverter.convert(type);

    assertEquals(expected, response.getNursePrescriptionRight().get(0));
  }

  @Test
  void shouldConvertHCPCodes() {
    final var expected = HCPSpecialityCodes.builder().build();
    final var type = mock(CredentialInformationType.class);
    when(type.getHealthCareProfessionalLicenceSpeciality())
        .thenReturn(List.of(new HCPSpecialityCodesType()));
    when(hcpSpecialityCodeTypeConverter.convert(any(HCPSpecialityCodesType.class)))
        .thenReturn(expected);

    final var response = credentialInformationTypeConverter.convert(type);

    assertEquals(expected, response.getHealthCareProfessionalLicenceSpeciality().get(0));
  }

  @Test
  void shouldConvertHsaSystemRole() {
    final var expected = HsaSystemRole.builder().build();
    final var type = mock(CredentialInformationType.class);
    when(type.getHsaSystemRole())
        .thenReturn(List.of(new HsaSystemRoleType()));
    when(hsaSystemRoleTypeConverter.convert(any(HsaSystemRoleType.class)))
        .thenReturn(expected);

    final var response = credentialInformationTypeConverter.convert(type);

    assertEquals(expected, response.getHsaSystemRole().get(0));
  }

  private CredentialInformationType getType() {
    final var type = new CredentialInformationType();
    type.setPersonHsaId("HSA_ID");
    type.setGivenName("NAME");
    type.setMiddleAndSurName("SURNAME");
    type.setFeignedPerson(true);
    type.setPersonalPrescriptionCode("CODE");
    type.setHealthcareProfessionalLicenseIdentityNumber("ID_NUMBER");
    type.setProtectedPerson(true);
    type.setPersonalPrescriptionCode("P_CODE");

    return type;
  }

}