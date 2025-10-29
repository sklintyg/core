package se.inera.intyg.certificateservice.infrastructure.certificatemodel.ag7804;

import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateGeneralPrintText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateGenerelPrintProvider;

public class AG7804CertificateGeneralPrintProvider implements CertificateGenerelPrintProvider {

  @Override
  public CertificateGeneralPrintText generalPrintText() {
    return CertificateGeneralPrintText.builder()
        .leftMarginInfoText("%s %s %s - Fastställd av %s")
        .draftAlertInfoText("arbetsgivaren")
        .build();
  }
}