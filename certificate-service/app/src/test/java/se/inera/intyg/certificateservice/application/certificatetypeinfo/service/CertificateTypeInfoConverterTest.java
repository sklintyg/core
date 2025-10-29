package se.inera.intyg.certificateservice.application.certificatetypeinfo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataAction.ACTION_EVALUATION;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateConfirmationModalDTO;
import se.inera.intyg.certificateservice.application.certificate.service.converter.CertificateConfirmationModalConverter;
import se.inera.intyg.certificateservice.application.certificatetypeinfo.service.converter.CertificateTypeInfoConverter;
import se.inera.intyg.certificateservice.application.common.converter.ResourceLinkConverter;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkDTO;
import se.inera.intyg.certificateservice.domain.action.certificate.model.CertificateAction;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateConfirmationModal;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateConfirmationModalProvider;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateTypeName;

@ExtendWith(MockitoExtension.class)
class CertificateTypeInfoConverterTest {

  private static final String TYPE = "TYPE";
  private static final String TYPE_NAME = "TYPE_NAME";
  private static final String NAME = "NAME";
  private static final String DESCRIPTION = "DESCRIPTION";
  private static final CertificateActionSpecification CERTIFICATE_ACTION_SPECIFICATION =
      CertificateActionSpecification.builder().build();
  private static final CertificateAction CERTIFICATE_ACTION = mock(CertificateAction.class);
  private static final List<CertificateAction> CERTIFICATE_ACTIONS = List.of(CERTIFICATE_ACTION);
  private static final CertificateConfirmationModal MODAL = CertificateConfirmationModal.builder()
      .build();
  private static final CertificateConfirmationModalDTO CONVERTED_MODAL = CertificateConfirmationModalDTO.builder()
      .build();

  @Mock
  private ResourceLinkConverter resourceLinkConverter;
  @Mock
  CertificateConfirmationModalConverter confirmationModalConverter;
  @Mock
  CertificateConfirmationModalProvider modalProvider;
  @InjectMocks
  private CertificateTypeInfoConverter certificateTypeInfoConverter;

  private CertificateModel certificateModel;

  @BeforeEach
  void setUp() {
    certificateModel = CertificateModel.builder()
        .id(
            CertificateModelId.builder()
                .type(new CertificateType(TYPE))
                .build()
        )
        .typeName(new CertificateTypeName(TYPE_NAME))
        .name(NAME)
        .description(DESCRIPTION)
        .certificateActionSpecifications(
            List.of(CERTIFICATE_ACTION_SPECIFICATION)
        )
        .confirmationModalProvider(modalProvider)
        .build();

    when(modalProvider.of(null, ACTION_EVALUATION))
        .thenReturn(MODAL);
    when(confirmationModalConverter.convert(MODAL))
        .thenReturn(CONVERTED_MODAL);
  }

  @Test
  void shallIncludeModal() {
    final var result = certificateTypeInfoConverter.convert(certificateModel, CERTIFICATE_ACTIONS,
        ACTION_EVALUATION);
    assertEquals(CONVERTED_MODAL, result.getConfirmationModal());
  }

  @Test
  void shallIncludeType() {
    final var result = certificateTypeInfoConverter.convert(certificateModel, CERTIFICATE_ACTIONS,
        ACTION_EVALUATION);
    assertEquals(TYPE, result.getType());
  }

  @Test
  void shallIncludeTypeName() {
    final var result = certificateTypeInfoConverter.convert(certificateModel, CERTIFICATE_ACTIONS,
        ACTION_EVALUATION);
    assertEquals(TYPE_NAME, result.getTypeName());
  }

  @Test
  void shallIncludeName() {
    final var result = certificateTypeInfoConverter.convert(certificateModel, CERTIFICATE_ACTIONS,
        ACTION_EVALUATION);
    assertEquals(NAME, result.getName());
  }

  @Test
  void shallIncludeDescription() {
    final var result = certificateTypeInfoConverter.convert(certificateModel, CERTIFICATE_ACTIONS,
        ACTION_EVALUATION);
    assertEquals(DESCRIPTION, result.getDescription());
  }

  @Test
  void shallIncludeLinks() {
    final var link = ResourceLinkDTO.builder().build();
    final var expectedLinks = List.of(link);
    doReturn(link).when(resourceLinkConverter).convert(CERTIFICATE_ACTION, Optional.empty(),
        ACTION_EVALUATION);
    final var result = certificateTypeInfoConverter.convert(certificateModel, CERTIFICATE_ACTIONS,
        ACTION_EVALUATION);
    assertEquals(expectedLinks, result.getLinks());
  }
}