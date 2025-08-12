package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804;

import static se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804.elements.QuestionNedsattningArbetsformaga.QUESTION_NEDSATTNING_ARBETSFORMAGA_ID;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.DateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRangeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateActionContentProvider;

public class FK7804CertificateSendContentProvider implements CertificateActionContentProvider {

  private static final String LONG_SICK_LEAVE_PERIOD_BODY =
      "<p>Om du går vidare kommer intyget skickas direkt till "
          + "Försäkringskassans system vilket ska göras i samråd med patienten.</p>"
          + "<p>Upplys patienten om att även göra en ansökan om sjukpenning hos Försäkringskassan.</p>";

  private static final String SHORT_SICK_LEAVE_PERIOD_BODY =
      "<div class='ic-alert ic-alert--status ic-alert--info'><div>"
          + "<i class='ic-alert__icon ic-info-icon' style='float: left; margin-top: 3px;'></i>"
          + "<p style='margin-left: 10px'>Om sjukperioden är kortare än 15 dagar ska intyget inte skickas"
          + " till Försäkringskassan utom i vissa undantagsfall.</p></div></div></br>"
          + "Intyget ska skickas till Försäkringskassan från dag 8 i sjukperioden om patienten är:</br>"
          + "<ul><li>Egenföretagare</li>"
          + "<li>Arbetssökande</li>"
          + "<li>Anställd men arbetsgivaren betalar inte ut sjuklön</li>"
          + "<li>Studerande och arbetar med rätt till sjukpenning (tjänar mer än 10 700 per år)</li>"
          + "<li>Ledig med föräldrapenning</li>"
          + "<li>Ledig med graviditetspenning</li></ul>"
          + "</br>Om du går vidare kommer intyget skickas direkt till "
          + "Försäkringskassans system vilket ska göras i samråd med patienten.</br>"
          + "</br>Upplys patienten om att även göra en ansökan om sjukpenning hos Försäkringskassan.";

  @Override
  public String body(Certificate certificate) {
    final var questionBedomning = certificate.elementData().stream()
        .filter(data -> data.id().equals(QUESTION_NEDSATTNING_ARBETSFORMAGA_ID))
        .findFirst()
        .orElseThrow();

    if (!(questionBedomning.value() instanceof ElementValueDateRangeList elementValueDateList)) {
      throw new IllegalStateException(
          "Invalid value type. Type was '%s'".formatted(questionBedomning.value().getClass()));
    }

    final var ranges = getDateRanges(elementValueDateList);

    var totalDays = ranges.stream()
        .mapToLong(range -> ChronoUnit.DAYS.between(range.from(), range.to()) + 1)
        .sum();

    return totalDays < 15
        ? SHORT_SICK_LEAVE_PERIOD_BODY
        : LONG_SICK_LEAVE_PERIOD_BODY;
  }

  private static ArrayList<DateRange> getDateRanges(
      ElementValueDateRangeList elementValueDateList) {
    final var dateRanges = elementValueDateList.dateRangeList();
    if (dateRanges == null || dateRanges.isEmpty()) {
      throw new IllegalStateException("Inconsistent date range list provided.");
    }

    return new ArrayList<>(mergeConnectedDates(dateRanges));
  }

  private static ArrayList<DateRange> mergeConnectedDates(List<DateRange> dateRanges) {
    final var sortedRanges = new ArrayList<>(dateRanges);
    sortedRanges.sort(Comparator.comparing(DateRange::to));

    final var ranges = new ArrayList<DateRange>();
    sortedRanges.forEach(date -> {
          final var dateRange = ranges.isEmpty() ? null : ranges.getLast();

          if (dateRange != null && hasOverlappingDates(date, dateRange)) {
            final var dateRangeWithAddedDays = dateRange.withTo(date.to());
            ranges.remove(dateRange);
            ranges.add(dateRangeWithAddedDays);
          } else {
            ranges.add(date);
          }
        }
    );

    return ranges;
  }

  private static boolean hasOverlappingDates(DateRange date, DateRange dateRange) {
    return dateRange.to().plusDays(1).equals(date.from());
  }
}