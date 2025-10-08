package se.inera.intyg.certificateservice.domain.certificate.service;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.Pdf;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

public interface PdfGenerator {

  Pdf generate(Certificate certificate, String additionalInfoText, boolean isCitizenFormat,
      List<ElementId> hiddenElements);

}
