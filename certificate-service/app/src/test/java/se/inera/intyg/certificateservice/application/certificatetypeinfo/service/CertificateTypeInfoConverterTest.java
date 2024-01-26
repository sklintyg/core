package se.inera.intyg.certificateservice.application.certificatetypeinfo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.application.common.ResourceLinkConverter;
import se.inera.intyg.certificateservice.application.common.dto.ResourceLinkDTO;
import se.inera.intyg.certificateservice.domain.action.model.CertificateAction;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModelId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateType;

@ExtendWith(MockitoExtension.class)
class CertificateTypeInfoConverterTest {

  private static final String TYPE = "TYPE";
  private static final String NAME = "NAME";
  private static final String DESCRIPTION = "DESCRIPTION";
  private static final CertificateActionSpecification CERTIFICATE_ACTION_SPECIFICATION =
      CertificateActionSpecification.builder().build();
  private static final CertificateAction CERTIFICATE_ACTION = mock(CertificateAction.class);
  private static final List<CertificateAction> CERTIFICATE_ACTIONS = List.of(CERTIFICATE_ACTION);
  @Mock
  private ResourceLinkConverter resourceLinkConverter;
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
        .name(NAME)
        .description(DESCRIPTION)
        .certificateActionSpecifications(
            List.of(CERTIFICATE_ACTION_SPECIFICATION)
        )
        .build();
  }

  @Test
  void shallIncludeType() {
    final var result = certificateTypeInfoConverter.convert(certificateModel, CERTIFICATE_ACTIONS);
    assertEquals(TYPE, result.getType());
  }

  @Test
  void shallIncludeName() {
    final var result = certificateTypeInfoConverter.convert(certificateModel, CERTIFICATE_ACTIONS);
    assertEquals(NAME, result.getName());
  }

  @Test
  void shallIncludeDescription() {
    final var result = certificateTypeInfoConverter.convert(certificateModel, CERTIFICATE_ACTIONS);
    assertEquals(DESCRIPTION, result.getDescription());
  }

  @Test
  void shallIncludeLinks() {
    final var link = ResourceLinkDTO.builder().build();
    final var expectedLinks = List.of(link);
    doReturn(link).when(resourceLinkConverter).convert(CERTIFICATE_ACTION);
    final var result = certificateTypeInfoConverter.convert(certificateModel, CERTIFICATE_ACTIONS);
    assertEquals(expectedLinks, result.getLinks());
  }
}