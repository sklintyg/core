package se.inera.intyg.certificateservice.integrationtest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ANONYMA_REACT_ATTILA_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonPatientDTO.ATLAS_REACT_ABRAHAMSSON_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.AJLA_DOCTOR_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ALVA_VARDADMINISTRATOR_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.DAN_DENTIST_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonUserDTO.ajlaDoktorDtoBuilder;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.customCertificateTypeInfoRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.ApiRequestUtil.defaultCertificateTypeInfoRequest;
import static se.inera.intyg.certificateservice.integrationtest.util.CertificateTypeInfoUtil.certificateTypeInfo;
import static se.inera.intyg.certificateservice.integrationtest.util.ResourceLinkUtil.resourceLink;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkTypeDTO;

public abstract class GetCertificateTypeInfoIT extends BaseIntegrationIT {

  protected abstract String type();

  @Test
  @DisplayName("Om aktiverad ska intygstypen returneras i listan av tillgängliga intygstyper")
  void shallReturnCertificateWhenActive() {
    final var response = api.certificateTypeInfo(
        defaultCertificateTypeInfoRequest()
    );

    assertNotNull(
        certificateTypeInfo(response.getBody(), type()),
        "Should contain %s as it is active!".formatted(type())
    );
  }

  @Test
  @DisplayName("Om användaren har rollen tandläkare ska intygstypen inte returneras i listan av tillgängliga intygstyper")
  void shallNotReturnCertificateWhenUserIsDoctor() {
    final var response = api.certificateTypeInfo(
        customCertificateTypeInfoRequest()
            .user(DAN_DENTIST_DTO)
            .build()
    );

    assertNull(
        certificateTypeInfo(response.getBody(), type()),
        "Should not contain %s as user is dentist!".formatted(type())
    );
  }

  @Test
  @DisplayName("Om aktiverad ska 'Skapa utkast' vara tillgänglig")
  void shallReturnResourceLinkCreateCertificate() {
    final var response = api.certificateTypeInfo(
        defaultCertificateTypeInfoRequest()
    );

    final var resourceLink = resourceLink(certificateTypeInfo(response.getBody(), type()),
        ResourceLinkTypeDTO.CREATE_CERTIFICATE);

    assertNotNull(resourceLink,
        "Should contain %s!".formatted(ResourceLinkTypeDTO.CREATE_CERTIFICATE));
    assertTrue(resourceLink.isEnabled(), "Should be enabled");
  }

  @Test
  @DisplayName("Om patienten är avliden ska inte 'Skapa utkast' vara tillgänglig")
  void shallNotReturnResourceLinkCreateCertificateIfPatientIsDeceased() {
    final var response = api.certificateTypeInfo(
        customCertificateTypeInfoRequest()
            .patient(ATLAS_REACT_ABRAHAMSSON_DTO)
            .build()
    );

    final var resourceLink = resourceLink(certificateTypeInfo(response.getBody(), type()),
        ResourceLinkTypeDTO.CREATE_CERTIFICATE);

    assertNotNull(resourceLink,
        "Should contain %s!".formatted(ResourceLinkTypeDTO.CREATE_CERTIFICATE));
    assertFalse(resourceLink.isEnabled(), "Should be disabled");
  }

  @Test
  @DisplayName("Om användaren är blockerad ska inte 'Skapa utkast' vara tillgänglig")
  void shallNotReturnResourceLinkCreateCertificateIfUserIsBlocked() {
    final var response = api.certificateTypeInfo(
        customCertificateTypeInfoRequest()
            .user(
                ajlaDoktorDtoBuilder()
                    .blocked(Boolean.TRUE)
                    .build()
            )
            .build()
    );

    final var resourceLink = resourceLink(certificateTypeInfo(response.getBody(), type()),
        ResourceLinkTypeDTO.CREATE_CERTIFICATE);

    assertNotNull(resourceLink,
        "Should contain %s!".formatted(ResourceLinkTypeDTO.CREATE_CERTIFICATE));
    assertFalse(resourceLink.isEnabled(), "Should be disabled");
  }

  @Test
  @DisplayName("Om användaren är blockerad och patienten avliden ska inte 'Skapa utkast' vara tillgänglig")
  void shallNotReturnResourceLinkCreateCertificateIfUserIsBlockedAndPatientIsDeceased() {
    final var response = api.certificateTypeInfo(
        customCertificateTypeInfoRequest()
            .user(
                ajlaDoktorDtoBuilder()
                    .blocked(Boolean.TRUE)
                    .build()
            )
            .patient(ATLAS_REACT_ABRAHAMSSON_DTO)
            .build()
    );

    final var resourceLink = resourceLink(certificateTypeInfo(response.getBody(), type()),
        ResourceLinkTypeDTO.CREATE_CERTIFICATE);

    assertNotNull(resourceLink,
        "Should contain %s!".formatted(ResourceLinkTypeDTO.CREATE_CERTIFICATE));
    assertFalse(resourceLink.isEnabled(), "Should be disabled");
  }

  @Test
  @DisplayName("Vårdadmininstratör - Om patienten har skyddade personuppgifter ska inte 'Skapa utkast' vara tillgänglig")
  void shallNotReturnResourceLinkCreateCertificateIfUserIsCareAdminAndPatientIsProtected() {
    final var response = api.certificateTypeInfo(
        customCertificateTypeInfoRequest()
            .user(ALVA_VARDADMINISTRATOR_DTO)
            .patient(ANONYMA_REACT_ATTILA_DTO)
            .build()
    );

    final var resourceLink = resourceLink(certificateTypeInfo(response.getBody(), type()),
        ResourceLinkTypeDTO.CREATE_CERTIFICATE);

    assertNotNull(resourceLink,
        "Should contain %s!".formatted(ResourceLinkTypeDTO.CREATE_CERTIFICATE));
    assertFalse(resourceLink.isEnabled(), "Should be disabled");
  }

  @Test
  @DisplayName("Läkare - Om patienten har skyddade personuppgifter ska 'Skapa utkast' vara tillgänglig")
  void shallReturnResourceLinkCreateCertificateIfUserIsDoctorAndPatientIsProtected() {
    final var response = api.certificateTypeInfo(
        customCertificateTypeInfoRequest()
            .user(AJLA_DOCTOR_DTO)
            .patient(ANONYMA_REACT_ATTILA_DTO)
            .build()
    );

    final var resourceLink = resourceLink(certificateTypeInfo(response.getBody(), type()),
        ResourceLinkTypeDTO.CREATE_CERTIFICATE);

    assertNotNull(resourceLink,
        "Should contain %s!".formatted(ResourceLinkTypeDTO.CREATE_CERTIFICATE));
    assertTrue(resourceLink.isEnabled(), "Should be enabled");
  }
}
