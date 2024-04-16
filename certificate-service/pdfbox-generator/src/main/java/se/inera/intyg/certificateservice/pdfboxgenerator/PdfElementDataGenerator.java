package se.inera.intyg.certificateservice.pdfboxgenerator;

import java.util.List;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

public interface PdfElementDataGenerator {

  void generate(List<ElementData> elementData, ElementId id, String fieldName);

}
