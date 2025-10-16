package se.inera.intyg.certificateservice.integrationtest.common.util;

import java.util.Collections;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import se.inera.intyg.certificateservice.application.unit.dto.UnitStatisticsDTO;
import se.inera.intyg.certificateservice.application.unit.dto.UnitStatisticsResponse;

public class UnitStatisticsUtil {

  public static Map<String, UnitStatisticsDTO> unitStatistics(
      ResponseEntity<UnitStatisticsResponse> response) {
    if (response == null || response.getBody() == null
        || response.getBody().getUnitStatistics() == null) {
      return Collections.emptyMap();
    }
    return response.getBody().getUnitStatistics();
  }
}
