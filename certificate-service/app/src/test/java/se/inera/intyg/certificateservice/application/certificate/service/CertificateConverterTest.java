package se.inera.intyg.certificateservice.application.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.ZoneId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdDTO;
import se.inera.intyg.certificateservice.application.common.dto.PersonIdTypeDTO;
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
import se.inera.intyg.certificateservice.domain.patient.model.Patient;
import se.inera.intyg.certificateservice.domain.patient.model.PersonAddress;
import se.inera.intyg.certificateservice.domain.patient.model.PersonId;
import se.inera.intyg.certificateservice.domain.patient.model.PersonIdType;

class CertificateConverterTest {

  private static final String TYPE = "type";
  private static final String VERSION = "version";
  private static final String TYPE_NAME = "typeName";
  private static final String TYPE_DESCRIPTION = "typeDescription";
  private static final LocalDateTime CREATED = LocalDateTime.now(ZoneId.systemDefault());
  private static final String PATIENT_ID = "patientId";
  private static final String PATIENT_FIRST_NAME = "patientFirstName";
  private static final String PATIENT_MIDDLE_NAME = "patientMiddleName";
  private static final String PATIENT_LAST_NAME = "patientLastName";
  private static final String PATIENT_FULL_NAME = "patientFullName";
  private static final String PATIENT_STREET = "patientStreet";
  private static final String PATIENT_CITY = "patientCity";
  private static final String PATIENT_ZIPCODE = "patientZipcode";
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
                  .patient(
                      Patient.builder()
                          .id(
                              PersonId.builder()
                                  .id(PATIENT_ID)
                                  .type(PersonIdType.PERSONAL_IDENTITY_NUMBER)
                                  .build()
                          )
                          .name(
                              Name.builder()
                                  .firstName(PATIENT_FIRST_NAME)
                                  .middleName(PATIENT_MIDDLE_NAME)
                                  .lastName(PATIENT_LAST_NAME)
                                  .fullName(PATIENT_FULL_NAME)
                                  .build()
                          )
                          .address(
                              PersonAddress.builder()
                                  .street(PATIENT_STREET)
                                  .city(PATIENT_CITY)
                                  .zipCode(PATIENT_ZIPCODE)
                                  .build()
                          )
                          .build()
                  )
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
                                  .fullName(ISSUED_BY_NAME)
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
            .id(PATIENT_ID)
            .type(PersonIdTypeDTO.PERSONAL_IDENTITY_NUMBER)
            .build();

        assertEquals(expectedId,
            certificateConverter.convert(certificate).getMetadata().getPatient()
                .getId()
        );
      }

      @Test
      void shallIncludeFirstName() {
        assertEquals(PATIENT_FIRST_NAME,
            certificateConverter.convert(certificate).getMetadata().getPatient()
                .getFirstName()
        );
      }

      @Test
      void shallIncludeMiddleName() {
        assertEquals(PATIENT_MIDDLE_NAME,
            certificateConverter.convert(certificate).getMetadata().getPatient()
                .getMiddleName()
        );
      }

      @Test
      void shallIncludeLastName() {
        assertEquals(PATIENT_LAST_NAME,
            certificateConverter.convert(certificate).getMetadata().getPatient()
                .getLastName()
        );
      }

      @Test
      void shallIncludeFullName() {
        assertEquals(PATIENT_FULL_NAME,
            certificateConverter.convert(certificate).getMetadata().getPatient()
                .getFullName()
        );
      }

      @Test
      void shallIncludeStreet() {
        assertEquals(PATIENT_STREET,
            certificateConverter.convert(certificate).getMetadata().getPatient()
                .getStreet()
        );
      }

      @Test
      void shallIncludeCity() {
        assertEquals(PATIENT_CITY,
            certificateConverter.convert(certificate).getMetadata().getPatient()
                .getCity()
        );
      }

      @Test
      void shallIncludeZipCode() {
        assertEquals(PATIENT_ZIPCODE,
            certificateConverter.convert(certificate).getMetadata().getPatient()
                .getZipCode()
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