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

import org.springframework.stereotype.Component;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfig;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigCheckboxDateRangeList;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CheckboxDateRange;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationCheckboxDateRangeList;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.SubField;

@Component
public class CertificateDataCheckboxDateRangeListConfigConverter implements
    CertificateDataConfigConverter {

  @Override
  public ElementType getType() {
    return ElementType.CHECKBOX_DATE_RANGE_LIST;
  }

  public CertificateDataConfig convert(ElementSpecification elementSpecification) {
    final var configuration = (ElementConfigurationCheckboxDateRangeList) elementSpecification.configuration();
    return CertificateDataConfigCheckboxDateRangeList.builder()
        .text(configuration.name())
        .list(
            configuration.fields().stream()
                .map(this::convertSubField)
                .toList()
        )
        .build();
  }

  private CheckboxDateRange convertSubField(SubField subField) {
    return CheckboxDateRange.builder()
        .id(subField.id())
        .label(subField.label())
        .build();
  }
}
