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
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.CredentialsForPerson;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.HCPSpecialityCodes;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.HealthCareProfessionalLicence;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.NursePrescriptionRight;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.Restriction;
import se.riv.infrastructure.directory.authorizationmanagement.gethospcredentialsforpersonresponder.v1.GetHospCredentialsForPersonResponseType;
import se.riv.infrastructure.directory.authorizationmanagement.v2.HCPSpecialityCodesType;
import se.riv.infrastructure.directory.authorizationmanagement.v2.HealthCareProfessionalLicenceType;
import se.riv.infrastructure.directory.authorizationmanagement.v2.IIType;
import se.riv.infrastructure.directory.authorizationmanagement.v2.NursePrescriptionRightType;
import se.riv.infrastructure.directory.authorizationmanagement.v2.RestrictionType;

@ExtendWith(MockitoExtension.class)
class GetCredentialsForPersonResponseTypeConverterTest {

  @InjectMocks
  GetCredentialsForPersonResponseTypeConverter getCredentialsForPersonResponseTypeConverter;

  @Mock
  NursePrescriptionRightTypeConverter nursePrescriptionRightTypeConverter;

  @Mock
  HCPSpecialityCodeTypeConverter hcpSpecialityCodeTypeConverter;

  @Mock
  HealthCareProfessionalLicenceTypeConverter healthCareProfessionalLicenceTypeConverter;

  @Mock
  RestrictionTypeConverter restrictionTypeConverter;

  @Test
  void shouldReturnEmptyIfTypeIsNull() {
    final var response = getCredentialsForPersonResponseTypeConverter.convert(null);

    assertEquals(CredentialsForPerson.builder().build(), response);
  }

  @Test
  void shouldConvertFeignedPerson() {
    final var type = getType();

    final var response = getCredentialsForPersonResponseTypeConverter.convert(type);

    assertTrue(response.getFeignedPerson());
  }

  @Test
  void shouldConvertPersonalIdentityNumber() {
    final var type = getType();

    final var response = getCredentialsForPersonResponseTypeConverter.convert(type);

    assertEquals(type.getPersonalIdentityNumber().getExtension(),
        response.getPersonalIdentityNumber());
  }

  @Test
  void shouldConvertPersonPrescriptionCode() {
    final var type = getType();

    final var response = getCredentialsForPersonResponseTypeConverter.convert(type);

    assertEquals(type.getPersonalPrescriptionCode(), response.getPersonalPrescriptionCode());
  }

  @Test
  void shouldConvertEducationCode() {
    final var type = getType();

    final var response = getCredentialsForPersonResponseTypeConverter.convert(type);

    assertEquals(type.getEducationCode(), response.getEducationCode());
  }

  @Test
  void shouldReturnConvertedNursePrescriptionRight() {
    final var expected = NursePrescriptionRight.builder().build();
    final var type = mock(GetHospCredentialsForPersonResponseType.class);
    when(type.getNursePrescriptionRight())
        .thenReturn(List.of(new NursePrescriptionRightType()));
    when(nursePrescriptionRightTypeConverter.convert(any(NursePrescriptionRightType.class)))
        .thenReturn(expected);

    final var response = getCredentialsForPersonResponseTypeConverter.convert(type);

    assertEquals(expected, response.getNursePrescriptionRight().get(0));
  }

  @Test
  void shouldReturnConvertedRestrictions() {
    final var expected = Restriction.builder().build();
    final var type = mock(GetHospCredentialsForPersonResponseType.class);
    when(type.getRestrictions())
        .thenReturn(List.of(new RestrictionType()));
    when(restrictionTypeConverter.convert(any(RestrictionType.class)))
        .thenReturn(expected);

    final var response = getCredentialsForPersonResponseTypeConverter.convert(type);

    assertEquals(expected, response.getRestrictions().get(0));
  }

  @Test
  void shouldReturnConvertedHCPCodes() {
    final var expected = HCPSpecialityCodes.builder().build();
    final var type = mock(GetHospCredentialsForPersonResponseType.class);
    when(type.getHealthCareProfessionalLicenceSpeciality())
        .thenReturn(List.of(new HCPSpecialityCodesType()));
    when(hcpSpecialityCodeTypeConverter.convert(any(HCPSpecialityCodesType.class)))
        .thenReturn(expected);

    final var response = getCredentialsForPersonResponseTypeConverter.convert(type);

    assertEquals(expected, response.getHealthCareProfessionalLicenceSpeciality().get(0));
  }

  @Test
  void shouldReturnConvertedLicence() {
    final var expected = HealthCareProfessionalLicence.builder().build();
    final var type = mock(GetHospCredentialsForPersonResponseType.class);
    when(type.getHealthCareProfessionalLicence())
        .thenReturn(List.of(new HealthCareProfessionalLicenceType()));
    when(healthCareProfessionalLicenceTypeConverter.convert(
        any(HealthCareProfessionalLicenceType.class)))
        .thenReturn(expected);

    final var response = getCredentialsForPersonResponseTypeConverter.convert(type);

    assertEquals(expected, response.getHealthCareProfessionalLicence().get(0));
  }

  @Test
  void shouldReturnConvertedLicenseIdentityNumber() {
    final var type = getType();

    final var response = getCredentialsForPersonResponseTypeConverter.convert(type);

    assertEquals(
        type.getHealthcareProfessionalLicenseIdentityNumber(),
        response.getHealthcareProfessionalLicenseIdentityNumber()
    );
  }

  private GetHospCredentialsForPersonResponseType getType() {
    final var type = new GetHospCredentialsForPersonResponseType();
    final var personalIdentityNumber = new IIType();
    personalIdentityNumber.setExtension("EXTENSION");

    type.setFeignedPerson(true);
    type.setPersonalPrescriptionCode("PERSON_PRESCRIPTION_CODE");
    type.setHealthcareProfessionalLicenseIdentityNumber("ID_NUMBER");
    type.setPersonalIdentityNumber(personalIdentityNumber);
    type.getEducationCode().add("E1");

    return type;
  }
}