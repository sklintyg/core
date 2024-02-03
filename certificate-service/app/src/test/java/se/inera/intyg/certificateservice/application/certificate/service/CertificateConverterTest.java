package se.inera.intyg.certificateservice.application.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatient.ATHENA_REACT_ANDERSSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_CITY;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_DECEASED;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_FIRST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_FULL_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_LAST_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_MIDDLE_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_PROTECTED_PERSON;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_STREET;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_TEST_INDICATED;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataPatientConstants.ATHENA_REACT_ANDERSSON_ZIP_CODE;

import java.time.LocalDateTime;
import java.time.ZoneId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.PersonIdDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.CareProvider;
import se.inera.intyg.certificateservice.domain.certificate.model.CareUnit;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.certificate.model.HsaId;
import se.inera.intyg.certificateservice.domain.certificate.model.Inactive;
import se.inera.intyg.certificateservice.domain.certificate.model.Staff;
import se.inera.intyg.certificateservice.domain.certificate.model.SubUnit;
import se.inera.intyg.certificateservice.domain.certificate.model.UnitAddress;
import se.inera.intyg.certificateservice.domain.certificate.model.UnitContactInfo;
import se.inera.intyg.certificateservice.domain.certificate.model.UnitName;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.patient.model.Name;
import se.inera.intyg.certificateservice.domain.patient.model.PersonIdType;

class CertificateConverterTest {

  private static final String TYPE = "type";
  private static final String VERSION = "version";
  private static final String TYPE_NAME = "typeName";
  private static final String TYPE_DESCRIPTION = "typeDescription";
  private static final LocalDateTime CREATED = LocalDateTime.now(ZoneId.systemDefault());
  private static final String UNIT_ID = "unitId";
  private static final String UNIT_NAME = "unitName";
  private static final String UNIT_ADDRESS = "unitAddress";
  private static final String UNIT_CITY = "unitCity";
  private static final String UNIT_ZIPCODE = "unitZipcode";
  private static final String UNIT_EMAIL = "unitEmail";
  private static final String UNIT_PHONENUMBER = "unitPhonenumber";
  private static final boolean UNIT_ISINACTIVE = true;
  private static final String CARE_UNIT_ID = "careUnitId";
  private static final String CARE_UNIT_NAME = "careUnitName";
  private static final String CARE_PROVIDER_ID = "careProviderId";
  private static final String CARE_PROVIDER_NAME = "careProviderName";
  private static final String ISSUED_BY_ID = "issuedById";
  private static final String ISSUED_BY_NAME = "issuedByName";
  private final CertificateConverter certificateConverter = new CertificateConverter();
  private static final String CERTIFICATE_ID = "certificateId";
  private Certificate certificate;

  @Nested
  class CertificateMetadata {

    @BeforeEach
    void setUp() {
      certificate = Certificate.builder()
          .id(new CertificateId(CERTIFICATE_ID))
          .created(CREATED)
          .certificateModel(
              CertificateModel.builder()
                  .id(
                      CertificateModelId.builder()
                          .type(new CertificateType(TYPE))
                          .version(new CertificateVersion(VERSION))
                          .build()
                  )
                  .name(TYPE_NAME)
                  .description(TYPE_DESCRIPTION)
                  .build()
          )
          .certificateMetaData(
              CertificateMetaData.builder()
                  .patient(ATHENA_REACT_ANDERSSON)
                  .issuingUnit(
                      SubUnit.builder()
                          .hsaId(new HsaId(UNIT_ID))
                          .name(new UnitName(UNIT_NAME))
                          .address(
                              UnitAddress.builder()
                                  .address(UNIT_ADDRESS)
                                  .city(UNIT_CITY)
                                  .zipCode(UNIT_ZIPCODE)
                                  .build()
                          )
                          .contactInfo(
                              UnitContactInfo.builder()
                                  .phoneNumber(UNIT_PHONENUMBER)
                                  .email(UNIT_EMAIL)
                                  .build()
                          )
                          .inactive(new Inactive(UNIT_ISINACTIVE))
                          .build()
                  )
                  .careUnit(
                      CareUnit.builder()
                          .hsaId(new HsaId(CARE_UNIT_ID))
                          .name(new UnitName(CARE_UNIT_NAME))
                          .build()
                  )
                  .careProvider(
                      CareProvider.builder()
                          .hsaId(new HsaId(CARE_PROVIDER_ID))
                          .name(new UnitName(CARE_PROVIDER_NAME))
                          .build()
                  )
                  .issuer(
                      Staff.builder()
                          .hsaId(new HsaId(ISSUED_BY_ID))
                          .name(
                              Name.builder()
                                  .lastName(ISSUED_BY_NAME)
                                  .build()
                          )
                          .build()
                  )
                  .build()
          )
          .build();
    }

    @Test
    void shallIncludeCertificateId() {
      assertEquals(CERTIFICATE_ID,
          certificateConverter.convert(certificate).getMetadata().getId()
      );
    }

    @Test
    void shallIncludeCertificateType() {
      assertEquals(TYPE,
          certificateConverter.convert(certificate).getMetadata().getType()
      );
    }

    @Test
    void shallIncludeCertificateTypeName() {
      assertEquals(TYPE,
          certificateConverter.convert(certificate).getMetadata().getTypeName()
      );
    }

    @Test
    void shallIncludeCertificateTypeVersion() {
      assertEquals(VERSION,
          certificateConverter.convert(certificate).getMetadata()
              .getTypeVersion()
      );
    }

    @Test
    void shallIncludeCertificateName() {
      assertEquals(TYPE_NAME,
          certificateConverter.convert(certificate).getMetadata().getName()
      );
    }

    @Test
    void shallIncludeCertificateTypeDescription() {
      assertEquals(TYPE_DESCRIPTION,
          certificateConverter.convert(certificate).getMetadata()
              .getDescription()
      );
    }

    @Test
    void shallIncludeCreated() {
      assertEquals(CREATED,
          certificateConverter.convert(certificate).getMetadata().getCreated()
      );
    }

    @Nested
    class PatientConvert {

      @Test
      void shallIncludeId() {
        final var expectedId = PersonIdDTO.builder()
            .id(ATHENA_REACT_ANDERSSON_ID)
            .type(PersonIdType.PERSONAL_IDENTITY_NUMBER.name())
            .build();

        assertEquals(expectedId,
            certificateConverter.convert(certificate).getMetadata().getPatient().getPersonId()
        );
      }

      @Test
      void shallIncludeFirstName() {
        assertEquals(ATHENA_REACT_ANDERSSON_FIRST_NAME,
            certificateConverter.convert(certificate).getMetadata().getPatient()
                .getFirstName()
        );
      }

      @Test
      void shallIncludeMiddleName() {
        assertEquals(ATHENA_REACT_ANDERSSON_MIDDLE_NAME,
            certificateConverter.convert(certificate).getMetadata().getPatient()
                .getMiddleName()
        );
      }

      @Test
      void shallIncludeLastName() {
        assertEquals(ATHENA_REACT_ANDERSSON_LAST_NAME,
            certificateConverter.convert(certificate).getMetadata().getPatient()
                .getLastName()
        );
      }

      @Test
      void shallIncludeFullName() {
        assertEquals(ATHENA_REACT_ANDERSSON_FULL_NAME,
            certificateConverter.convert(certificate).getMetadata().getPatient()
                .getFullName()
        );
      }

      @Test
      void shallIncludeStreet() {
        assertEquals(ATHENA_REACT_ANDERSSON_STREET,
            certificateConverter.convert(certificate).getMetadata().getPatient()
                .getStreet()
        );
      }

      @Test
      void shallIncludeCity() {
        assertEquals(ATHENA_REACT_ANDERSSON_CITY,
            certificateConverter.convert(certificate).getMetadata().getPatient()
                .getCity()
        );
      }

      @Test
      void shallIncludeZipCode() {
        assertEquals(ATHENA_REACT_ANDERSSON_ZIP_CODE,
            certificateConverter.convert(certificate).getMetadata().getPatient()
                .getZipCode()
        );
      }

      @Test
      void shallIncludeDeceased() {
        assertEquals(ATHENA_REACT_ANDERSSON_DECEASED.value(),
            certificateConverter.convert(certificate).getMetadata().getPatient()
                .getDeceased()
        );
      }

      @Test
      void shallIncludeTestIndicated() {
        assertEquals(ATHENA_REACT_ANDERSSON_TEST_INDICATED.value(),
            certificateConverter.convert(certificate).getMetadata().getPatient()
                .getTestIndicated()
        );
      }

      @Test
      void shallIncludeProtectedPerson() {
        assertEquals(ATHENA_REACT_ANDERSSON_PROTECTED_PERSON.value(),
            certificateConverter.convert(certificate).getMetadata().getPatient()
                .getProtectedPerson()
        );
      }
    }

    @Nested
    class UnitConvert {

      @Test
      void shallIncludeId() {
        assertEquals(UNIT_ID,
            certificateConverter.convert(certificate).getMetadata().getUnit()
                .getUnitId()
        );
      }

      @Test
      void shallIncludeName() {
        assertEquals(UNIT_NAME,
            certificateConverter.convert(certificate).getMetadata().getUnit()
                .getUnitName()
        );
      }

      @Test
      void shallIncludeAddress() {
        assertEquals(UNIT_ADDRESS,
            certificateConverter.convert(certificate).getMetadata().getUnit()
                .getAddress()
        );
      }

      @Test
      void shallIncludeCity() {
        assertEquals(UNIT_CITY,
            certificateConverter.convert(certificate).getMetadata().getUnit()
                .getCity()
        );
      }

      @Test
      void shallIncludeZipCode() {
        assertEquals(UNIT_ZIPCODE,
            certificateConverter.convert(certificate).getMetadata().getUnit()
                .getZipCode()
        );
      }

      @Test
      void shallIncludeEmail() {
        assertEquals(UNIT_EMAIL,
            certificateConverter.convert(certificate).getMetadata().getUnit()
                .getEmail()
        );
      }

      @Test
      void shallIncludePhonenumber() {
        assertEquals(UNIT_PHONENUMBER,
            certificateConverter.convert(certificate).getMetadata().getUnit()
                .getPhoneNumber()
        );
      }

      @Test
      void shallIncludeIsInactive() {
        assertEquals(UNIT_ISINACTIVE,
            certificateConverter.convert(certificate).getMetadata().getUnit()
                .getIsInactive()
        );
      }
    }

    @Nested
    class CareUnitConvert {

      @Test
      void shallIncludeId() {
        assertEquals(CARE_UNIT_ID,
            certificateConverter.convert(certificate).getMetadata()
                .getCareUnit()
                .getUnitId()
        );
      }

      @Test
      void shallIncludeName() {
        assertEquals(CARE_UNIT_NAME,
            certificateConverter.convert(certificate).getMetadata()
                .getCareUnit()
                .getUnitName()
        );
      }
    }

    @Nested
    class CareProviderConvert {

      @Test
      void shallIncludeId() {
        assertEquals(CARE_PROVIDER_ID,
            certificateConverter.convert(certificate).getMetadata()
                .getCareProvider().getUnitId()
        );
      }

      @Test
      void shallIncludeName() {
        assertEquals(CARE_PROVIDER_NAME,
            certificateConverter.convert(certificate).getMetadata()
                .getCareProvider().getUnitName()
        );
      }
    }

    @Nested
    class IssuedByConvert {

      @Test
      void shallIncludeId() {
        assertEquals(ISSUED_BY_ID,
            certificateConverter.convert(certificate).getMetadata()
                .getIssuedBy()
                .getPersonId()
        );
      }

      @Test
      void shallIncludeName() {
        assertEquals(ISSUED_BY_NAME,
            certificateConverter.convert(certificate).getMetadata()
                .getIssuedBy()
                .getFullName()
        );
      }
    }
  }
}