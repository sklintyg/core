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

package se.inera.intyg.intygproxyservice.integration.organization.client.converter;

import static se.inera.intyg.intygproxyservice.integration.common.TypeConverterHelper.toLocalDate;

import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.HealthCareUnit;
import se.riv.infrastructure.directory.organization.gethealthcareunitresponder.v2.HealthCareUnitType;

@Service
public class HealthCareUnitTypeConverter {

  public HealthCareUnit convert(HealthCareUnitType type) {
    return HealthCareUnit.builder()
        .archivedHealthCareUnit(type.isArchivedHealthCareUnit())
        .archivedHealthCareProvider(type.isArchivedHealthCareProvider())
        .archivedHealthCareUnitMember(type.isArchivedHealthCareUnitMember())
        .feignedHealthCareUnit(type.isFeignedHealthCareUnit())
        .feignedHealthCareProvider(type.isFeignedHealthCareProvider())
        .feignedHealthCareUnitMember(type.isFeignedHealthCareUnitMember())
        .unitIsHealthCareUnit(type.isUnitIsHealthCareUnit())
        .healthCareUnitHsaId(type.getHealthCareUnitHsaId())
        .healthCareUnitName(type.getHealthCareUnitName())
        .healthCareUnitPublicName(type.getHealthCareUnitPublicName())
        .healthCareUnitMemberHsaId(type.getHealthCareUnitMemberHsaId())
        .healthCareUnitMemberName(type.getHealthCareUnitMemberName())
        .healthCareUnitMemberPublicName(type.getHealthCareUnitMemberPublicName())
        .healthCareProviderHsaId(type.getHealthCareProviderHsaId())
        .healthCareProviderName(type.getHealthCareProviderName())
        .healthCareProviderOrgNo(type.getHealthCareProviderOrgNo())
        .healthCareProviderPublicName(type.getHealthCareProviderPublicName())
        .healthCareProviderEndDate(toLocalDate(type.getHealthCareProviderEndDate()))
        .healthCareProviderStartDate(toLocalDate(type.getHealthCareProviderStartDate()))
        .healthCareUnitEndDate(toLocalDate(type.getHealthCareUnitEndDate()))
        .healthCareUnitStartDate(toLocalDate(type.getHealthCareUnitStartDate()))
        .healthCareUnitMemberEndDate(toLocalDate(type.getHealthCareUnitMemberEndDate()))
        .healthCareUnitMemberStartDate(toLocalDate(type.getHealthCareUnitMemberStartDate()))
        .build();
  }

}
