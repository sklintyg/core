/*
 * Copyright (C) 2024 Inera AB (http://www.inera.se)
 *
 * This file is part of sklintyg (https://github.com/sklintyg).
 *
 * sklintyg is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * sklintyg is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package se.inera.intyg.certificateservice.application.certificate.service.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigCheckboxDateRangeList;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CheckboxDateRangeConfig;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CheckboxDateRange;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxDateRangeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationDate;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;

class CertificateDataCheckboxDateRangeListConfigConverterTest {

  CertificateDataCheckboxDateRangeListConfigConverter converter = new CertificateDataCheckboxDateRangeListConfigConverter();

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
        () -> converter.convert(elementSpecification)
    );
  }

  @Test
  void shouldReturnConvertedConfig() {
    final var expected = CertificateDataConfigCheckboxDateRangeList.builder()
        .text("NAME")
        .hideWorkingHours(true)
        .previousDateRangeText("TEST TEST")
        .list(
            List.of(
                CheckboxDateRangeConfig.builder()
                    .id("ID")
                    .label("LABEL")
                    .build(),
                CheckboxDateRangeConfig.builder()
                    .id("ID2")
                    .label("LABEL2")
                    .build()
            )
        )
        .build();

    final var response = converter.convert(
        ElementSpecification.builder()
            .configuration(
                ElementConfigurationCheckboxDateRangeList.builder()
                    .id(new FieldId("ID"))
                    .hideWorkingHours(true)
                    .previousDateRangeText("TEST TEST")
                    .name("NAME")
                    .dateRanges(
                        List.of(
                            new CheckboxDateRange(new FieldId("ID"), "LABEL"),
                            new CheckboxDateRange(new FieldId("ID2"), "LABEL2")
                        )
                    )
                    .build()
            ).build()
    );

    assertEquals(expected, response);
  }
}