package se.inera.intyg.certificateservice.domain.action.certificate.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionSpecification;

class CertificateActionCreateFromTemplateTest {

  private static final String AG7804_BODY = """
      <div><div class="ic-alert ic-alert--status ic-alert--info">
      <i class="ic-alert__icon ic-info-icon"></i>
      Kom ihåg att stämma av med patienten om hen vill att du skickar Läkarintyget för sjukpenning till Försäkringskassan. Gör detta i så fall först.</div>
      <p class='iu-pt-400'>Skapa ett Läkarintyg om arbetsförmåga - arbetsgivaren (AG7804) utifrån ett Läkarintyg för sjukpenning innebär att informationsmängder som är gemensamma för båda intygen automatiskt förifylls.
      </p></div>
      """;
  private static final String AG7804_DESCRIPTION = "Skapar ett intyg till arbetsgivaren utifrån Försäkringskassans intyg.";
  private static final String AG7804_NAME = "Skapa AG7804";
  private CertificateActionCreateFromTemplate certificateActionCreateFromTemplate;

  @BeforeEach
  void setUp() {
    certificateActionCreateFromTemplate = CertificateActionCreateFromTemplate.builder()
        .certificateActionSpecification(
            CertificateActionSpecification.builder()
                .certificateActionType(CertificateActionType.CREATE_FROM_TEMPLATE)
                .build()
        )
        .build();
  }

  @Test
  void shouldReturnType() {
    assertEquals(CertificateActionType.CREATE_FROM_TEMPLATE,
        certificateActionCreateFromTemplate.getType());
  }

  @Test
  void shouldReturnName() {
    assertEquals(AG7804_NAME, certificateActionCreateFromTemplate.getName(Optional.empty()));
  }

  @Test
  void shouldReturnDescription() {
    assertEquals(
        AG7804_DESCRIPTION, certificateActionCreateFromTemplate.getDescription(Optional.empty()));
  }

  @Test
  void shouldReturnBody() {
    assertEquals(
        AG7804_BODY, certificateActionCreateFromTemplate.getBody(Optional.empty(), Optional.empty())
    );
  }
}