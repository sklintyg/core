package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.FK7210_CERTIFICATE;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7472CertificateBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigCheckboxDateRangeList;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CheckboxDateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.CertificateId;
import se.inera.intyg.certificateservice.domain.certificate.model.DateRange;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueDateRangeList;
import se.inera.intyg.certificateservice.domain.certificate.model.Relation;
import se.inera.intyg.certificateservice.domain.certificate.model.RelationType;
import se.inera.intyg.certificateservice.domain.certificate.model.Status;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxDateRangeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCode;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.common.model.Code;

class CertificateDataCheckboxDateRangeListConfigConverterTest {

  private static final String ID = "ID";
  private static final String RANGE_ID_ONE = "RANGE_ID_ONE";
  private static final String RANGE_ID_TWO = "RANGE_ID_TWO";
  private static final String RANGE_LABEL_ONE = "100%";
  private static final String RANGE_LABEL_TWO = "12,5%";
  CertificateDataCheckboxDateRangeListConfigConverter converter = new CertificateDataCheckboxDateRangeListConfigConverter();
  private ElementSpecification elementSpecification;

  @BeforeEach
  void setUp() {
    elementSpecification = ElementSpecification.builder()
        .id(new ElementId(ID))
        .configuration(
            ElementConfigurationCheckboxDateRangeList.builder()
                .id(new FieldId(ID))
                .hideWorkingHours(true)
                .name("NAME")
                .label("LABEL")
                .min(Period.ofMonths(-1))
                .max(Period.ofMonths(2))
                .dateRanges(
                    List.of(
                        new ElementConfigurationCode(new FieldId(RANGE_ID_ONE), RANGE_LABEL_ONE,
                            new Code("CODE", "CODE_SYSTEM", "DISPLAY_NAME")),
                        new ElementConfigurationCode(new FieldId(RANGE_ID_TWO), RANGE_LABEL_TWO,
                            new Code("CODE", "CODE_SYSTEM", "DISPLAY_NAME"))
                    )
                )
                .build()
        )
        .build();
  }

  @Test
  void shouldReturnCategoryType() {
    assertEquals(ElementType.CHECKBOX_DATE_RANGE_LIST, converter.getType());
  }

  @Test
  void shouldThrowExceptionIfWrongClass() {
    final var elementSpecification = ElementSpecification.builder()
        .configuration(ElementConfigurationDate.builder().build())
        .build();

    assertThrows(IllegalStateException.class,
        () -> converter.convert(elementSpecification, FK7210_CERTIFICATE)
    );
  }

  @Test
  void shouldReturnConvertedConfig() {
    final var expected = CertificateDataConfigCheckboxDateRangeList.builder()
        .text("NAME")
        .label("LABEL")
        .hideWorkingHours(true)
        .min(LocalDate.now().plusMonths(-1))
        .max(LocalDate.now().plusMonths(2))
        .list(
            List.of(
                CheckboxDateRange.builder()
                    .id(RANGE_ID_ONE)
                    .label(RANGE_LABEL_ONE)
                    .build(),
                CheckboxDateRange.builder()
                    .id(RANGE_ID_TWO)
                    .label(RANGE_LABEL_TWO)
                    .build()
            )
        )
        .build();

    final var response = converter.convert(
        elementSpecification,
        FK7210_CERTIFICATE
    );

    assertEquals(expected, response);
  }

  @Test
  void shallIncludePreviousDateRangeIfParentRelationRenewAndStatusDraft() {
    final var previousEndDate = LocalDate.parse("2024-04-15");
    final var expected = "På det ursprungliga intyget var slutdatumet för den sista perioden %s och omfattningen var %s."
        .formatted(previousEndDate, RANGE_LABEL_TWO);

    final var previousCertificate = fk7472CertificateBuilder()
        .id(new CertificateId("PreviousCertificateId"))
        .elementData(
            List.of(
                ElementData.builder()
                    .id(new ElementId(ID))
                    .value(
                        ElementValueDateRangeList.builder()
                            .dateRangeList(
                                List.of(
                                    DateRange.builder()
                                        .dateRangeId(new FieldId(RANGE_ID_ONE))
                                        .from(previousEndDate.minusDays(30))
                                        .to(previousEndDate.minusDays(25))
                                        .build(),
                                    DateRange.builder()
                                        .dateRangeId(new FieldId(RANGE_ID_TWO))
                                        .from(previousEndDate.minusDays(10))
                                        .to(previousEndDate)
                                        .build()
                                )
                            )
                            .dateRangeListId(new FieldId("3.2"))
                            .build())
                    .build()
            )
        )
        .build();

    final var certificate = fk7472CertificateBuilder()
        .status(Status.DRAFT)
        .parent(
            Relation.builder()
                .certificate(previousCertificate)
                .type(RelationType.RENEW)
                .created(LocalDateTime.now(ZoneId.systemDefault()))
                .build()
        )
        .build();

    final var response = (CertificateDataConfigCheckboxDateRangeList) converter.convert(
        elementSpecification,
        certificate
    );

    assertEquals(expected, response.getPreviousDateRangeText());
  }

  @Test
  void shallIncludePreviousDateRangeIfParentRelationRenewAndStatusDraftButMissingLabel() {
    final var previousEndDate = LocalDate.parse("2024-04-15");
    final var expected = "På det ursprungliga intyget var slutdatumet för den sista perioden %s och omfattningen var <saknas>."
        .formatted(previousEndDate);

    final var previousCertificate = fk7472CertificateBuilder()
        .id(new CertificateId("PreviousCertificateId"))
        .elementData(
            List.of(
                ElementData.builder()
                    .id(new ElementId(ID))
                    .value(
                        ElementValueDateRangeList.builder()
                            .dateRangeList(
                                List.of(
                                    DateRange.builder()
                                        .dateRangeId(new FieldId(RANGE_ID_ONE))
                                        .from(previousEndDate.minusDays(30))
                                        .to(previousEndDate.minusDays(25))
                                        .build(),
                                    DateRange.builder()
                                        .dateRangeId(new FieldId("RANGE_ID_MISSING"))
                                        .from(previousEndDate.minusDays(10))
                                        .to(previousEndDate)
                                        .build()
                                )
                            )
                            .dateRangeListId(new FieldId("3.2"))
                            .build())
                    .build()
            )
        )
        .build();

    final var certificate = fk7472CertificateBuilder()
        .status(Status.DRAFT)
        .parent(
            Relation.builder()
                .certificate(previousCertificate)
                .type(RelationType.RENEW)
                .created(LocalDateTime.now(ZoneId.systemDefault()))
                .build()
        )
        .build();

    final var response = (CertificateDataConfigCheckboxDateRangeList) converter.convert(
        elementSpecification,
        certificate
    );

    assertEquals(expected, response.getPreviousDateRangeText());
  }

  @Test
  void shallNotIncludePreviousDateRangeTextIfParentNotRenew() {
    final var previousCertificate = fk7472CertificateBuilder()
        .id(new CertificateId("PreviousCertificateId"))
        .build();

    final var certificate = fk7472CertificateBuilder()
        .status(Status.DRAFT)
        .parent(
            Relation.builder()
                .certificate(previousCertificate)
                .type(RelationType.REPLACE)
                .created(LocalDateTime.now(ZoneId.systemDefault()))
                .build()
        )
        .build();

    final var response = (CertificateDataConfigCheckboxDateRangeList) converter.convert(
        elementSpecification,
        certificate
    );

    assertNull(response.getPreviousDateRangeText());
  }

  @Test
  void shallNotIncludePreviousDateRangeTextIfNoParent() {
    final var certificate = fk7472CertificateBuilder()
        .status(Status.DRAFT)
        .build();

    final var response = (CertificateDataConfigCheckboxDateRangeList) converter.convert(
        elementSpecification,
        certificate
    );

    assertNull(response.getPreviousDateRangeText());
  }

  @Test
  void shallNotIncludePreviousDateRangeTextIfNotDraft() {
    final var previousEndDate = LocalDate.parse("2024-04-15");

    final var previousCertificate = fk7472CertificateBuilder()
        .id(new CertificateId("PreviousCertificateId"))
        .elementData(
            List.of(
                ElementData.builder()
                    .id(new ElementId(ID))
                    .value(
                        ElementValueDateRangeList.builder()
                            .dateRangeList(
                                List.of(
                                    DateRange.builder()
                                        .dateRangeId(new FieldId(RANGE_ID_ONE))
                                        .from(previousEndDate.minusDays(30))
                                        .to(previousEndDate.minusDays(25))
                                        .build(),
                                    DateRange.builder()
                                        .dateRangeId(new FieldId("RANGE_ID_MISSING"))
                                        .from(previousEndDate.minusDays(10))
                                        .to(previousEndDate)
                                        .build()
                                )
                            )
                            .dateRangeListId(new FieldId("3.2"))
                            .build())
                    .build()
            )
        )
        .build();

    final var certificate = fk7472CertificateBuilder()
        .status(Status.SIGNED)
        .parent(
            Relation.builder()
                .certificate(previousCertificate)
                .type(RelationType.RENEW)
                .created(LocalDateTime.now(ZoneId.systemDefault()))
                .build()
        )
        .build();

    final var response = (CertificateDataConfigCheckboxDateRangeList) converter.convert(
        elementSpecification,
        certificate
    );

    assertNull(response.getPreviousDateRangeText());
  }

  @Test
  void shallNotIncludePreviousDateRangeTextIfNoPreviousValue() {
    final var previousCertificate = fk7472CertificateBuilder()
        .id(new CertificateId("PreviousCertificateId"))
        .build();

    final var certificate = fk7472CertificateBuilder()
        .status(Status.DRAFT)
        .parent(
            Relation.builder()
                .certificate(previousCertificate)
                .type(RelationType.RENEW)
                .created(LocalDateTime.now(ZoneId.systemDefault()))
                .build()
        )
        .build();

    final var response = (CertificateDataConfigCheckboxDateRangeList) converter.convert(
        elementSpecification,
        certificate
    );

    assertNull(response.getPreviousDateRangeText());
  }

  @Test
  void shallNotIncludePreviousDateRangeTextIfNoDateRanges() {
    final var previousCertificate = fk7472CertificateBuilder()
        .id(new CertificateId("PreviousCertificateId"))
        .elementData(
            List.of(
                ElementData.builder()
                    .id(new ElementId(ID))
                    .value(
                        ElementValueDateRangeList.builder()
                            .dateRangeList(Collections.emptyList())
                            .dateRangeListId(new FieldId("3.2"))
                            .build())
                    .build()
            )
        )
        .build();

    final var certificate = fk7472CertificateBuilder()
        .status(Status.DRAFT)
        .parent(
            Relation.builder()
                .certificate(previousCertificate)
                .type(RelationType.RENEW)
                .created(LocalDateTime.now(ZoneId.systemDefault()))
                .build()
        )
        .build();

    final var response = (CertificateDataConfigCheckboxDateRangeList) converter.convert(
        elementSpecification,
        certificate
    );

    assertNull(response.getPreviousDateRangeText());
  }
}
