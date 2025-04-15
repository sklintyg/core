package se.inera.intyg.certificateservice.pdfboxgenerator.pdf.value;

import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfConstants.CHECKED_BOX_VALUE;
import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.codesystems.CodeSystemKvFkmu0008.EN_ATTONDEL;
import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.codesystems.CodeSystemKvFkmu0008.EN_FJARDEDEL;
import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.codesystems.CodeSystemKvFkmu0008.HALVA;
import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.codesystems.CodeSystemKvFkmu0008.HELA;
import static se.inera.intyg.certificateservice.pdfboxgenerator.pdf.codesystems.CodeSystemKvFkmu0008.TRE_FJARDEDELAR;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.domain.certificate.model.DateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRangeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationDateRange;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.PdfConfigurationDateRangeList;
import se.inera.intyg.certificateservice.pdfboxgenerator.pdf.PdfField;

@Component
public class PdfDateRangeValueGenerator implements PdfElementValue<ElementValueDateRange> {

  @Override
  public Class<ElementValueDateRange> getType() {
    return ElementValueDateRange.class;
  }

  @Override
  public List<PdfField> generate(ElementSpecification elementSpecification,
      ElementValueDateRange elementValue) {
    final var pdfConfiguration = (PdfConfigurationDateRange) elementSpecification.pdfConfiguration();

    return getPeriodFields(elementValue, pdfConfiguration);
  }

  private List<PdfField> getPeriodFields(ElementValueDateRange dateRange,
      PdfConfigurationDateRange pdfConfiguration) {
    final var fields = new ArrayList<PdfField>();

    if (dateRange.fromDate() != null) {
      fields.add(
          PdfField.builder()
              .id(pdfConfiguration.from().id())
              .value(dateRange.fromDate().toString())
              .build()
      );
    }

    if (dateRange.toDate() != null) {
      fields.add(
          PdfField.builder()
              .id(pdfConfiguration.to().id())
              .value(dateRange.toDate().toString())
              .build()
      );
    }

    return fields;
  }
}
