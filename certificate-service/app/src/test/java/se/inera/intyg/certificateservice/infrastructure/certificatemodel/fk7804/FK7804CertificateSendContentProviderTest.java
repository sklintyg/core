package se.inera.intyg.certificateservice.infrastructure.certificatemodel.fk7804;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.DateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRangeList;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueText;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

class FK7804CertificateSendContentProviderTest {

  private static final ElementId QUESTION_NEDSATTNING_ARBETSFORMAGA_ID = new ElementId("32");

  private static final String SHORT_BODY =
      "<p>Om du går vidare kommer intyget skickas direkt till "
          + "Försäkringskassans system vilket ska göras i samråd med patienten.</p>"
          + "<p>Upplys patienten om att även göra en ansökan om sjukpenning hos Försäkringskassan.</p>";

  private static final String ALERT_BODY =
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

  private final FK7804CertificateSendContentProvider provider = new FK7804CertificateSendContentProvider();

  @Test
  void shouldThrowIfValueIsNotElementValueDateRangeList() {
    final var data = ElementData.builder()
        .id(QUESTION_NEDSATTNING_ARBETSFORMAGA_ID)
        .value(ElementValueText.builder().build())
        .build();

    final var cert = Certificate.builder()
        .elementData(List.of(data))
        .build();

    final var illegalStateException = assertThrows(IllegalStateException.class,
        () -> provider.body(cert));
    assertEquals(illegalStateException.getMessage()
        , "Invalid value type. Type was '%s'".formatted(ElementValueText.class));
  }

  @Test
  void shouldThrowIfDateRangeListIsEmpty() {
    final var cert = certificateWithDateRanges(List.of());
    assertThrows(IllegalStateException.class, () -> provider.body(cert));
  }

  @Test
  void shouldReturnAlertBodyIfTotalDaysLessThan15() {
    final var cert = certificateWithDateRanges(
        List.of(
            DateRange.builder()
                .from(LocalDate.of(2023, 1, 1))
                .to(LocalDate.of(2023, 1, 14))
                .build()
        )
    );

    final var result = provider.body(cert);
    assertEquals(ALERT_BODY, result);
  }

  @Test
  void shouldReturnShortBodyIfTotalDaysEquals15() {
    final var cert = certificateWithDateRanges(
        List.of(
            DateRange.builder()
                .from(LocalDate.of(2023, 1, 1))
                .to(LocalDate.of(2023, 1, 15))
                .build()
        )
    );
    final var result = provider.body(cert);
    assertEquals(SHORT_BODY, result);
  }

  @Test
  void shouldReturnShortBodyIfTotalDaysMoreThan15() {
    final var cert = certificateWithDateRanges(
        List.of(
            DateRange.builder()
                .from(LocalDate.of(2023, 1, 1))
                .to(LocalDate.of(2023, 1, 20))
                .build()
        )
    );
    final var result = provider.body(cert);
    assertEquals(SHORT_BODY, result);
  }

  private static Certificate certificateWithDateRanges(List<DateRange> ranges) {
    final var value = ElementValueDateRangeList.builder()
        .dateRangeList(ranges)
        .build();
    final var data = ElementData.builder()
        .id(QUESTION_NEDSATTNING_ARBETSFORMAGA_ID)
        .value(value)
        .build();
    return Certificate.builder()
        .elementData(List.of(data))
        .build();
  }
}