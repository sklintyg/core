/*
 * Copyright (C) 2025 Inera AB (http://www.inera.se)
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

import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.GeoCoordRt90;
import se.riv.infrastructure.directory.organization.getunitresponder.v4.GeoCoordRt90Type;

@Component
public class GeoCoordRt90TypeConverter {

  public GeoCoordRt90 convert(GeoCoordRt90Type type) {
    if (type == null) {
      return GeoCoordRt90.builder().build();
    }

    return GeoCoordRt90.builder()
        .xCoordinate(type.getXCoordinate())
        .yCoordinate(type.getYCoordinate())
        .build();
  }

}
