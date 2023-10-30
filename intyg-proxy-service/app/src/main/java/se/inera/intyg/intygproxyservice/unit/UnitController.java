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

package se.inera.intyg.intygproxyservice.unit;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.inera.intyg.intygproxyservice.unit.dto.UnitRequest;
import se.inera.intyg.intygproxyservice.unit.dto.UnitResponse;
import se.inera.intyg.intygproxyservice.unit.service.UnitService;

@RestController()
@RequestMapping("/api/v2/healthcareunit")
@AllArgsConstructor
public class UnitController {

  private final UnitService unitService;

  @PostMapping("")
  UnitResponse getHealthCareUnit(@RequestBody UnitRequest request) {
    return unitService.get(request);
  }
}
