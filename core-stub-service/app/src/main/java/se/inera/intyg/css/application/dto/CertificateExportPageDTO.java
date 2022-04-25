package se.inera.intyg.css.application.dto;

import java.util.List;

public record CertificateExportPageDTO(String careProviderId, int page, int count, long total,
                                       long totalRevoked, List<CertificateXmlDTO> certificateXmls) {

}
