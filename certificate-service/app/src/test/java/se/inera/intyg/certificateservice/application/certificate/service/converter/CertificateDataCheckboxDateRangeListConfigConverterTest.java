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

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigCheckboxDateRangeList;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CheckboxDateRange;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxDateRangeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.FieldId;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.SubField;

class CertificateDataCheckboxDateRangeListConfigConverterTest {

  CertificateDataCheckboxDateRangeListConfigConverter converter = new CertificateDataCheckboxDateRangeListConfigConverter();

  @Test
  void shouldReturnCategoryType() {
    assertEquals(ElementType.CHECKBOX_DATE_RANGE_LIST, converter.getType());
  }

  @Test
  void shouldReturnConvertedConfig() {
    final var expected = CertificateDataConfigCheckboxDateRangeList.builder()
        .text("NAME")
        .list(
            List.of(
                CheckboxDateRange.builder()
                    .id("ID")
                    .label("LABEL")
                    .build(),
                CheckboxDateRange.builder()
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
                    .name("NAME")
                    .fields(
                        List.of(
                            new SubField("ID", "LABEL"),
                            new SubField("ID2", "LABEL2")
                        )
                    )
                    .build()
            ).build()
    );

    assertEquals(expected, response);
  }
}