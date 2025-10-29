package se.inera.intyg.certificateservice.certificate.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GeneralPrintTextDTO {

  String leftMarginInfoText;
  String draftAlertInfoText;

}
