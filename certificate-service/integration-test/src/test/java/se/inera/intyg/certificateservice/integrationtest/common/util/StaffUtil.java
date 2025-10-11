package se.inera.intyg.certificateservice.integrationtest.common.util;

import java.util.Collections;
import java.util.List;
import se.inera.intyg.certificateservice.application.certificate.dto.StaffDTO;
import se.inera.intyg.certificateservice.application.unit.dto.GetUnitCertificatesInfoResponse;

public class StaffUtil {

  public static List<StaffDTO> staff(GetUnitCertificatesInfoResponse response) {
    if (response == null || response.getStaffs() == null) {
      return Collections.emptyList();
    }
    return response.getStaffs();
  }
}
