package se.inera.intyg.certificateservice.application.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonWebcertUnitDTO.alfaMedicincentrumDtoBuilder;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProvider.ALFA_REGIONEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProviderConstants.ALFA_REGIONEN_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareProviderConstants.ALFA_REGIONEN_NAME;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnit.ALFA_MEDICINCENTRUM;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCareUnitConstants.ALFA_MEDICINCENTRUM_NAME;
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
import static se.inera.intyg.certificateservice.domain.testdata.TestDataStaff.AJLA_DOKTOR;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataSubUnit.ALFA_ALLERGIMOTTAGNINGEN;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_HSA_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataUserConstants.AJLA_DOCTOR_NAME;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDataElement;
import se.inera.intyg.certificateservice.application.certificate.dto.PersonIdDTO;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkDTO;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateMetaData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateVersion;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCategory;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRule;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementRuleType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementValidationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.RuleExpression;
import se.inera.intyg.certificateservice.domain.patient.model.PersonIdType;

@ExtendWith(MockitoExtension.class)
class CertificateConverterTest {

  private static final String TYPE = "type";
  private static final String VERSION = "version";
  private static final String TYPE_NAME = "typeName";
  private static final String TYPE_DESCRIPTION = "typeDescription";
  private static final LocalDateTime CREATED = LocalDateTime.now(ZoneId.systemDefault());
  private static final LocalDate DATE = LocalDate.of(2024, 02, 8);
  private static final String Q_1 = "q1";
  private static final String ID = "valueId";
  private static final String EXPRESSION = "$beraknatnedkomstdatum";
  private static final String Q_2 = "q2";
  private static final String NAME = "Beräknat nedkomstdatum";
  private static final String KEY = "key";
  private static final long CERTIFICATE_VERSION = 3L;
  private List<ResourceLinkDTO> resourceLinkDTOs = Collections.emptyList();
  @Mock
  private CertificateMetaDataUnitConverter certificateMetaDataUnitConverter;
  @Mock
  private CertificateDataConverter certificateDataConverter;
  @InjectMocks
  private CertificateConverter certificateConverter;
  private static final String CERTIFICATE_ID = "certificateId";
  private Certificate certificate;

  @BeforeEach
  void setUp() {
    certificate = Certificate.builder()
        .id(new CertificateId(CERTIFICATE_ID))
        .created(CREATED)
        .version(CERTIFICATE_VERSION)
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
                .elementSpecifications(
                    List.of(
                        ElementSpecification.builder()
                            .id(new ElementId(Q_1))
                            .configuration(
                                ElementConfigurationCategory.builder()
                                    .name(NAME)
                                    .build()
                            )
                            .children(
                                List.of(
                                    ElementSpecification.builder()
                                        .id(new ElementId(ID))
                                        .configuration(
                                            ElementConfigurationDate.builder()
                                                .id(ID)
                                                .name(NAME)
                                                .minDate(LocalDate.now().minus(Period.ofDays(0)))
                                                .maxDate(LocalDate.now().plus(Period.ofYears(1)))
                                                .build()
                                        )
                                        .rules(
                                            List.of(
                                                ElementRule.builder()
                                                    .id(new ElementId(ID))
                                                    .type(ElementRuleType.MANDATORY)
                                                    .expression(
                                                        new RuleExpression(EXPRESSION))
                                                    .build()
                                            )
                                        )
                                        .validations(
                                            List.of(
                                                ElementValidationDate.builder()
                                                    .mandatory(true)
                                                    .min(Period.ofDays(0))
                                                    .max(Period.ofYears(1))
                                                    .build()
                                            )
                                        )
                                        .build()
                                )
                            )
                            .build()
                    )
                )
                .build()
        )
        .certificateMetaData(
            CertificateMetaData.builder()
                .patient(ATHENA_REACT_ANDERSSON)
                .issuingUnit(ALFA_ALLERGIMOTTAGNINGEN)
                .careUnit(ALFA_MEDICINCENTRUM)
                .careProvider(ALFA_REGIONEN)
                .issuer(AJLA_DOKTOR)
                .build()
        )
        .elementData(
            List.of(
                ElementData.builder()
                    .id(new ElementId(Q_1))
                    .value(null)
                    .build(),
                ElementData.builder()
                    .id(new ElementId(Q_2))
                    .value(
                        ElementValueDate.builder()
                            .date(DATE)
                            .build()
                    )
                    .build()
            )
        )
        .build();
  }

  @Nested
  class CertificateMetadata {

    @Test
    void shallIncludeCertificateId() {
      assertEquals(CERTIFICATE_ID,
          certificateConverter.convert(certificate, resourceLinkDTOs).getMetadata().getId()
      );
    }

    @Test
    void shallIncludeCertificateType() {
      assertEquals(TYPE,
          certificateConverter.convert(certificate, resourceLinkDTOs).getMetadata().getType()
      );
    }

    @Test
    void shallIncludeCertificateTypeName() {
      assertEquals(TYPE,
          certificateConverter.convert(certificate, resourceLinkDTOs).getMetadata().getTypeName()
      );
    }

    @Test
    void shallIncludeCertificateTypeVersion() {
      assertEquals(VERSION,
          certificateConverter.convert(certificate, resourceLinkDTOs).getMetadata()
              .getTypeVersion()
      );
    }

    @Test
    void shallIncludeCertificateName() {
      assertEquals(TYPE_NAME,
          certificateConverter.convert(certificate, resourceLinkDTOs).getMetadata().getName()
      );
    }

    @Test
    void shallIncludeCertificateTypeDescription() {
      assertEquals(TYPE_DESCRIPTION,
          certificateConverter.convert(certificate, resourceLinkDTOs).getMetadata()
              .getDescription()
      );
    }

    @Test
    void shallIncludeCreated() {
      assertEquals(CREATED,
          certificateConverter.convert(certificate, resourceLinkDTOs).getMetadata().getCreated()
      );
    }

    @Test
    void shallIncludeVersion() {
      assertEquals(CERTIFICATE_VERSION,
          certificateConverter.convert(certificate, resourceLinkDTOs).getMetadata().getVersion()
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
            certificateConverter.convert(certificate, resourceLinkDTOs).getMetadata().getPatient()
                .getPersonId()
        );
      }

      @Test
      void shallIncludeFirstName() {
        assertEquals(ATHENA_REACT_ANDERSSON_FIRST_NAME,
            certificateConverter.convert(certificate, resourceLinkDTOs).getMetadata().getPatient()
                .getFirstName()
        );
      }

      @Test
      void shallIncludeMiddleName() {
        assertEquals(ATHENA_REACT_ANDERSSON_MIDDLE_NAME,
            certificateConverter.convert(certificate, resourceLinkDTOs).getMetadata().getPatient()
                .getMiddleName()
        );
      }

      @Test
      void shallIncludeLastName() {
        assertEquals(ATHENA_REACT_ANDERSSON_LAST_NAME,
            certificateConverter.convert(certificate, resourceLinkDTOs).getMetadata().getPatient()
                .getLastName()
        );
      }

      @Test
      void shallIncludeFullName() {
        assertEquals(ATHENA_REACT_ANDERSSON_FULL_NAME,
            certificateConverter.convert(certificate, resourceLinkDTOs).getMetadata().getPatient()
                .getFullName()
        );
      }

      @Test
      void shallIncludeStreet() {
        assertEquals(ATHENA_REACT_ANDERSSON_STREET,
            certificateConverter.convert(certificate, resourceLinkDTOs).getMetadata().getPatient()
                .getStreet()
        );
      }

      @Test
      void shallIncludeCity() {
        assertEquals(ATHENA_REACT_ANDERSSON_CITY,
            certificateConverter.convert(certificate, resourceLinkDTOs).getMetadata().getPatient()
                .getCity()
        );
      }

      @Test
      void shallIncludeZipCode() {
        assertEquals(ATHENA_REACT_ANDERSSON_ZIP_CODE,
            certificateConverter.convert(certificate, resourceLinkDTOs).getMetadata().getPatient()
                .getZipCode()
        );
      }

      @Test
      void shallIncludeDeceased() {
        assertEquals(ATHENA_REACT_ANDERSSON_DECEASED.value(),
            certificateConverter.convert(certificate, resourceLinkDTOs).getMetadata().getPatient()
                .getDeceased()
        );
      }

      @Test
      void shallIncludeTestIndicated() {
        assertEquals(ATHENA_REACT_ANDERSSON_TEST_INDICATED.value(),
            certificateConverter.convert(certificate, resourceLinkDTOs).getMetadata().getPatient()
                .getTestIndicated()
        );
      }

      @Test
      void shallIncludeProtectedPerson() {
        assertEquals(ATHENA_REACT_ANDERSSON_PROTECTED_PERSON.value(),
            certificateConverter.convert(certificate, resourceLinkDTOs).getMetadata().getPatient()
                .getProtectedPerson()
        );
      }
    }

    @Nested
    class UnitConvert {

      @Test
      void shallIncludeUnit() {
        final var expectedUnit = alfaMedicincentrumDtoBuilder().build();
        doReturn(expectedUnit).when(certificateMetaDataUnitConverter).convert(
            certificate.certificateMetaData().issuingUnit(),
            Collections.emptyList()
        );
        assertEquals(expectedUnit,
            certificateConverter.convert(certificate, resourceLinkDTOs).getMetadata().getUnit()
        );
      }
    }

    @Nested
    class CareUnitConvert {

      @Test
      void shallIncludeId() {
        assertEquals(ALFA_MEDICINCENTRUM_ID,
            certificateConverter.convert(certificate, resourceLinkDTOs).getMetadata().getCareUnit()
                .getUnitId()
        );
      }

      @Test
      void shallIncludeName() {
        assertEquals(ALFA_MEDICINCENTRUM_NAME,
            certificateConverter.convert(certificate, resourceLinkDTOs).getMetadata().getCareUnit()
                .getUnitName()
        );
      }
    }

    @Nested
    class CareProviderConvert {

      @Test
      void shallIncludeId() {
        assertEquals(ALFA_REGIONEN_ID,
            certificateConverter.convert(certificate, resourceLinkDTOs).getMetadata()
                .getCareProvider().getUnitId()
        );
      }

      @Test
      void shallIncludeName() {
        assertEquals(ALFA_REGIONEN_NAME,
            certificateConverter.convert(certificate, resourceLinkDTOs).getMetadata()
                .getCareProvider().getUnitName()
        );
      }
    }

    @Nested
    class IssuedByConvert {

      @Test
      void shallIncludeId() {
        assertEquals(AJLA_DOCTOR_HSA_ID,
            certificateConverter.convert(certificate, resourceLinkDTOs).getMetadata().getIssuedBy()
                .getPersonId()
        );
      }

      @Test
      void shallIncludeName() {
        assertEquals(AJLA_DOCTOR_NAME,
            certificateConverter.convert(certificate, resourceLinkDTOs).getMetadata().getIssuedBy()
                .getFullName()
        );
      }
    }
  }

  @Nested
  class CertificateData {

    @Test
    void shallIncludeData() {
      final var expectedValue = Map.of(KEY, CertificateDataElement.builder().build());

      doReturn(expectedValue).when(certificateDataConverter)
          .convert(certificate.certificateModel(), certificate.elementData());

      assertEquals(expectedValue,
          certificateConverter.convert(certificate, resourceLinkDTOs).getData());
    }
  }

  @Nested
  class CertificateResourceLinks {

    @Test
    void shallIncludeLinks() {
      final var resourceLinkDTO = ResourceLinkDTO.builder().build();
      final var expectedLinks = List.of(resourceLinkDTO);
      assertEquals(expectedLinks,
          certificateConverter.convert(certificate, expectedLinks).getLinks());
    }
  }
}
