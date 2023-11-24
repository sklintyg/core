/*
 * Copyright (C) 2023 Inera AB (http://www.inera.se)
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

package se.inera.intyg.intygproxyservice.integration.api.organization.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Unit {

    @Builder.Default
    List<String> businessType = new ArrayList<>();
    @Builder.Default
    List<BusinessClassification> businessClassification = new ArrayList<>();
    @Builder.Default
    List<String> careType = new ArrayList<>();
    String countyName;
    String countyCode;
    GeoCoordRt90 geographicalCoordinatesRt90;
    GeoCoordSweref99 geographicalCoordinatesSweref99;
    String municipalityName;
    String municipalityCode;
    String location;
    LocalDateTime unitStartDate;
    LocalDateTime unitEndDate;
    Boolean feignedUnit;
    String unitHsaId;
    String unitName;
    @Builder.Default
    List<String> postalAddress = new ArrayList<>();
    String postalCode;
    String mail;
    @Builder.Default
    List<String> management = new ArrayList<>();
}
