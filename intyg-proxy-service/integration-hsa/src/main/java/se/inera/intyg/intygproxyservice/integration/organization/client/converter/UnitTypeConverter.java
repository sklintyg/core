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

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.Unit;
import se.riv.infrastructure.directory.organization.getunitresponder.v4.UnitType;

@Service
@RequiredArgsConstructor
public class UnitTypeConverter {

  private final AddressTypeConverter addressTypeConverter;
  private final GeoCoordRt90TypeConverter geoCoordRt90TypeConverter;
  private final GeoCoordSweref99TypeConverter geoCoordSweref99TypeConverter;
  private final BusinessClassificationTypeConverter businessClassificationTypeConverter;

  public Unit convert(UnitType type) {
    return Unit.builder()
        .unitName(type.getUnitName())
        .unitHsaId(type.getUnitHsaId())
        .countyName(type.getCountyName())
        .countyCode(type.getCountyCode())
        .municipalityCode(type.getMunicipalityCode())
        .municipalityName(type.getMunicipalityName())
        .feignedUnit(type.isFeignedUnit())
        .location(type.getLocation())
        .mail(type.getMail())
        .unitStartDate(toLocalDate(type.getUnitStartDate()))
        .unitEndDate(toLocalDate(type.getUnitEndDate()))
        .postalCode(type.getPostalCode())
        .postalAddress(addressTypeConverter.convertV3(type.getPostalAddress()))
        .careType(type.getCareType())
        .businessType(type.getBusinessType())
        .management(type.getManagement())
        .telephoneNumber(type.getTelephoneNumber())
        .geographicalCoordinatesRt90(
            geoCoordRt90TypeConverter.convert(type.getGeographicalCoordinatesRt90())
        )
        .geographicalCoordinatesSweref99(
            geoCoordSweref99TypeConverter.convert(type.getGeographicalCoordinatesSWEREF99())
        )
        .businessClassification(type.getBusinessClassification().stream()
            .map(businessClassificationTypeConverter::convert)
            .toList()
        )
        .build();
  }

}
