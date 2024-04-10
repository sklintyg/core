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

package se.inera.intyg.certificateservice.domain.certificatemodel.model;

import lombok.Getter;

@Getter
public enum DateRangeType {
  EN_ATTANDEL("12,5 procent"),
  EN_FJARDEDEL("25 procent"),
  HALFTEN("50 procent"),
  TRE_FJARDEDELAR("75 procent"),
  HELT_NEDSATT("100 procent");

  private final String label;
  public static final String CODE_SYSTEM = "KV_FKMU_0003";

  DateRangeType(String label) {
    this.label = label;
  }

}
