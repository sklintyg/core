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

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import org.springframework.stereotype.Component;
import se.riv.infrastructure.directory.organization.v2.AddressType;

@Component
public class AddressTypeConverter {

  public List<String> convertV2(AddressType type) {
    return convert(type, AddressType::getAddressLine);
  }

  public List<String> convertV3(se.riv.infrastructure.directory.organization.v3.AddressType type) {
    return convert(type,
        se.riv.infrastructure.directory.organization.v3.AddressType::getAddressLine);
  }

  private <T> List<String> convert(T type, Function<T, List<String>> getValues) {
    return type != null && getValues.apply(type) != null
        ? getValues.apply(type)
        : Collections.emptyList();
  }
}
