package se.inera.intyg.certificateservice.infrastructure.certificatemodel.common;

import java.util.List;
import java.util.function.Predicate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueCodeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.common.model.Code;

public class FieldHasValueFactory {

  private FieldHasValueFactory() {
    throw new IllegalStateException("Utility class");
  }

  public static Predicate<List<ElementData>> codeList(ElementId questionId, Code code) {
    return elementData -> elementData.stream()
        .filter(data -> questionId.equals(data.id()))
        .map(element -> (ElementValueCodeList) element.value())
        .map(ElementValueCodeList::list)
        .flatMap(List::stream)
        .anyMatch(valueCode -> valueCode.code().equals(code.code()));
  }
}