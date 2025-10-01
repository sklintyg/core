package se.inera.intyg.certificateservice.application.certificate.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

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
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateMetadataDTO;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConverter;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateDataConverter;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateMetadataConverter;
import se.inera.intyg.certificateservice.application.certificate.service.converter.HandleComplementElementVisibilityService;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkDTO;
import se.inera.intyg.certificateservice.domain.action.certificate.model.ActionEvaluation;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.MedicalCertificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Relation;
import se.inera.intyg.certificateservice.domain.certificate.model.RelationType;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementVisibilityConfigurationsCheckboxMultipleCode;
import se.inera.intyg.certificateservice.domain.message.model.Complement;
import se.inera.intyg.certificateservice.domain.message.model.Message;

@ExtendWith(MockitoExtension.class)
class CertificateConverterTest {

  private final List<ResourceLinkDTO> resourceLinkDTOs = Collections.emptyList();
  private static final ActionEvaluation ACTION_EVALUATION = ActionEvaluation.builder().build();
  private Certificate certificate;
  private static final String KEY = "key";


  @Mock
  private HandleComplementElementVisibilityService handleComplementElementVisibilityService;
  @Mock
  private CertificateMetadataConverter certificateMetadataConverter;
  @Mock
  private CertificateDataConverter certificateDataConverter;
  @InjectMocks
  private CertificateConverter certificateConverter;

  @Nested
  class ConvertForCare {

    @BeforeEach
    void setUp() {
      certificate = MedicalCertificate.builder().build();
    }

    @Nested
    class CertificateMetadataTest {

      @Test
      void shallIncludeMetadata() {
        final var expectedMetadata = CertificateMetadataDTO.builder().build();
        doReturn(expectedMetadata).when(certificateMetadataConverter)
            .convert(certificate, ACTION_EVALUATION);
        final var actualMetadata = certificateMetadataConverter.convert(certificate,
            ACTION_EVALUATION);
        assertEquals(expectedMetadata, actualMetadata);
      }
    }

    @Nested
    class CertificateData {

      @Test
      void shallIncludeData() {
        final var expectedValue = Map.of(KEY, CertificateDataElement.builder().build());

        doReturn(expectedValue).when(certificateDataConverter)
            .convert(certificate, false);

        assertEquals(expectedValue,
            certificateConverter.convert(certificate, resourceLinkDTOs, ACTION_EVALUATION)
                .getData());
      }
    }

    @Nested
    class CertificateResourceLinks {

      @Test
      void shallIncludeLinks() {
        final var resourceLinkDTO = ResourceLinkDTO.builder().build();
        final var expectedLinks = List.of(resourceLinkDTO);
        assertEquals(expectedLinks,
            certificateConverter.convert(certificate, expectedLinks, ACTION_EVALUATION).getLinks());
      }
    }
  }

  @Nested
  class ConvertForCitizen {

    @BeforeEach
    void setUp() {
      certificate = MedicalCertificate.builder().build();
    }

    @Nested
    class CertificateMetadataTest {

      @Test
      void shallIncludeMetadata() {
        final var expectedMetadata = CertificateMetadataDTO.builder().build();

        doReturn(expectedMetadata).when(certificateMetadataConverter)
            .convert(certificate, ACTION_EVALUATION);

        final var actualMetadata = certificateMetadataConverter.convert(certificate,
            ACTION_EVALUATION);

        assertEquals(expectedMetadata, actualMetadata);
      }
    }

    @Nested
    class CertificateData {

      @Test
      void shallIncludeData() {
        final var expectedValue = Map.of(KEY, CertificateDataElement.builder().build());

        doReturn(expectedValue).when(certificateDataConverter)
            .convert(certificate, true);

        assertEquals(expectedValue,
            certificateConverter.convertForCitizen(certificate, resourceLinkDTOs)
                .getData());
      }
    }

    @Nested
    class CertificateResourceLinks {

      @Test
      void shallIncludeLinks() {
        final var resourceLinkDTO = ResourceLinkDTO.builder().build();
        final var expectedLinks = List.of(resourceLinkDTO);

        assertEquals(expectedLinks,
            certificateConverter.convertForCitizen(certificate, expectedLinks).getLinks());
      }
    }
  }

  @Nested
  class VisibilityConfigurationTests {

    @Test
    void shallHandleVisibilityConfigurationIfCertificateIsDraftWithComplementOnParent() {
      final var elementId = new ElementId("id");

      final var visibilityConfiguration = ElementVisibilityConfigurationsCheckboxMultipleCode
          .builder()
          .build();

      final var medicalCertificate = MedicalCertificate.builder()
          .status(Status.DRAFT)
          .certificateModel(
              CertificateModel.builder()
                  .elementSpecifications(
                      List.of(
                          ElementSpecification.builder()
                              .id(elementId)
                              .visibilityConfiguration(
                                  visibilityConfiguration
                              )
                              .build()
                      )
                  )
                  .build()
          )
          .parent(
              Relation.builder()
                  .type(RelationType.COMPLEMENT)
                  .certificate(
                      MedicalCertificate.builder()
                          .messages(
                              List.of(
                                  Message.builder()
                                      .complements(
                                          List.of(
                                              Complement.builder()
                                                  .elementId(elementId)
                                                  .build()
                                          )
                                      )
                                      .build()
                              )
                          )
                          .build()
                  )
                  .build()
          )
          .build();

      final var dataElementMap = Map.of(KEY, CertificateDataElement.builder().build());

      doReturn(dataElementMap).when(certificateDataConverter)
          .convert(medicalCertificate, false);

      certificateConverter.convert(medicalCertificate, resourceLinkDTOs, ACTION_EVALUATION);

      verify(handleComplementElementVisibilityService).handle(
          elementId,
          dataElementMap,
          medicalCertificate,
          visibilityConfiguration
      );
    }

    @Test
    void shallNotHandleVisibilityConfigurationIfCertificateIsDraftWithoutComplementOnParent() {
      final var elementId = new ElementId("id");

      final var visibilityConfiguration = ElementVisibilityConfigurationsCheckboxMultipleCode
          .builder()
          .build();

      final var medicalCertificate = MedicalCertificate.builder()
          .status(Status.DRAFT)
          .certificateModel(
              CertificateModel.builder()
                  .elementSpecifications(
                      List.of(
                          ElementSpecification.builder()
                              .id(elementId)
                              .visibilityConfiguration(
                                  visibilityConfiguration
                              )
                              .build()
                      )
                  )
                  .build()
          )
          .build();

      final var dataElementMap = Map.of(KEY, CertificateDataElement.builder().build());

      doReturn(dataElementMap).when(certificateDataConverter)
          .convert(medicalCertificate, false);

      certificateConverter.convert(medicalCertificate, resourceLinkDTOs, ACTION_EVALUATION);

      verifyNoInteractions(handleComplementElementVisibilityService);
    }

    @Test
    void shallNotHandleVisibilityConfigurationIfCertificateIsDraftWithComplementOnParentWithoutVisibilityConfiguration() {
      final var elementId = new ElementId("id");

      final var medicalCertificate = MedicalCertificate.builder()
          .status(Status.DRAFT)
          .certificateModel(
              CertificateModel.builder()
                  .elementSpecifications(
                      List.of(
                          ElementSpecification.builder()
                              .id(elementId)
                              .build()
                      )
                  )
                  .build()
          )
          .parent(
              Relation.builder()
                  .type(RelationType.COMPLEMENT)
                  .certificate(
                      MedicalCertificate.builder()
                          .messages(
                              List.of(
                                  Message.builder()
                                      .complements(
                                          List.of(
                                              Complement.builder()
                                                  .elementId(elementId)
                                                  .build()
                                          )
                                      )
                                      .build()
                              )
                          )
                          .build()
                  )
                  .build()
          )
          .build();

      final var dataElementMap = Map.of(KEY, CertificateDataElement.builder().build());

      doReturn(dataElementMap).when(certificateDataConverter)
          .convert(medicalCertificate, false);

      certificateConverter.convert(medicalCertificate, resourceLinkDTOs, ACTION_EVALUATION);

      verifyNoInteractions(handleComplementElementVisibilityService);
    }

    @Test
    void shallNotHandleVisibilityConfigurationIfCertificateIsNotDraft() {
      final var medicalCertificate = MedicalCertificate.builder()
          .status(Status.SIGNED)
          .build();

      certificateConverter.convert(medicalCertificate, resourceLinkDTOs, ACTION_EVALUATION);

      verifyNoInteractions(handleComplementElementVisibilityService);
    }
  }
}