package se.inera.intyg.certificateservice.certificate.dto;

import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PrintCertificateCategoryDTO {

  String id;
  String name;
  List<PrintCertificateQuestionDTO> questions;

}
