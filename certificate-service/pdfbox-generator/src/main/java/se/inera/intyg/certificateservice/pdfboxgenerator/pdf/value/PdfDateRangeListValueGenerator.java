package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfConstants.CHECKED_BOX_VALUE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.DateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRangeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationDateRangeCheckbox;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationDateRangeList;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

@Component
public class PdfDateRangeListValueGenerator implements PdfElementValue<ElementValueDateRangeList> {

  @Override
  public Class<ElementValueDateRangeList> getType() {
    return ElementValueDateRangeList.class;
  }

  @Override
  public List<PdfField> generate(ElementSpecification elementSpecification,
      ElementValueDateRangeList elementValue) {
    final var pdfConfiguration = (PdfConfigurationDateRangeList) elementSpecification.pdfConfiguration();

    return elementValue.dateRangeList().stream()
        .map(dateRange ->
            getPeriodFields(dateRange, pdfConfiguration.dateRanges())
        )
        .flatMap(Collection::stream)
        .toList();
  }

  private List<PdfField> getPeriodFields(DateRange dateRange,
      Map<FieldId, PdfConfigurationDateRangeCheckbox> dateRanges) {
    final var fields = new ArrayList<PdfField>();
    final var dateRangeConfig = dateRanges.get(dateRange.dateRangeId());

    fields.add(
        PdfField.builder()
            .id(dateRangeConfig.checkbox().id())
            .value(CHECKED_BOX_VALUE)
            .build()
    );

    if (dateRange.from() != null) {
      fields.add(
          PdfField.builder()
              .id(dateRangeConfig.from().id())
              .value(dateRange.from().toString())
              .build()
      );
    }

    if (dateRange.to() != null) {
      fields.add(
          PdfField.builder()
              .id(dateRangeConfig.to().id())
              .value(dateRange.to().toString())
              .build()
      );
    }

    return fields;
  }
}
