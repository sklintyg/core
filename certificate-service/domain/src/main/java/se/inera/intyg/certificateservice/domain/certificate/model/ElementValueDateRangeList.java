package se.inera.intyg.certificateservice.domain.certificate.model;

import java.util.Comparator;
import java.util.List;
import lombok.Builder;
import lombok.Value;
import lombok.With;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

@Value
@Builder
public class ElementValueDateRangeList implements ElementValue {

  FieldId dateRangeListId;
  @With
  List<DateRange> dateRangeList;

  public DateRange lastRange() {
    if (dateRangeList == null) {
      return null;
    }

    return dateRangeList.stream()
        .max(Comparator.comparing(DateRange::to))
        .orElse(null);
  }

  @Override
  public boolean isEmpty() {
    if (dateRangeList == null || dateRangeList.isEmpty()) {
      return true;
    }

    return dateRangeList.stream()
        .noneMatch(dateRange -> dateRange.to() != null && dateRange.from() != null);
  }
}
