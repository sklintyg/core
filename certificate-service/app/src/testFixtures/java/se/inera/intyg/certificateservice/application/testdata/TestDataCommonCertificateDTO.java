package se.inera.intyg.certificateservice.application.testdata;

import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.FK7211_CONFIG_ID;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.FK7211_CONFIG_TEXT;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.QUESTION_ID;

import java.time.LocalDate;
import java.util.Map;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDTO.CertificateDTOBuilder;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateDataElement;
import se.inera.intyg.certificateservice.application.certificate.dto.CertificateMetadataDTO;
import se.inera.intyg.certificateservice.application.certificate.dto.config.CertificateDataConfigDate;
import se.inera.intyg.certificateservice.application.certificate.dto.value.CertificateDataValueDate;

public class TestDataCommonCertificateDTO {

  private TestDataCommonCertificateDTO() {
    throw new IllegalStateException("Utility class");
  }

  public static final CertificateDTO CERTIFICATE = certificate().build();
  public static final CertificateDTO FK7211_CERTIFICATE = fk7211Certificate().build();

  public static CertificateDTOBuilder certificate() {
    return CertificateDTO.builder()
        .data(
            Map.of(
                QUESTION_ID,
                CertificateDataElement.builder()
                    .build()
            )
        )
        .metadata(
            CertificateMetadataDTO.builder()
                .build()
        );
  }

  public static CertificateDTOBuilder fk7211Certificate() {
    return CertificateDTO.builder()
        .data(
            Map.of(
                QUESTION_ID,
                CertificateDataElement.builder()
                    .config(
                        CertificateDataConfigDate.builder()
                            .id(FK7211_CONFIG_ID)
                            .maxDate(LocalDate.MAX)
                            .minDate(LocalDate.MIN)
                            .text(FK7211_CONFIG_TEXT)
                            .build()
                    )
                    .value(
                        CertificateDataValueDate.builder()
                            .id(FK7211_CONFIG_ID)
                            .date(LocalDate.now())
                            .build()
                    )
                    .build()
            )
        )
        .metadata(
            //TODO: Create test data for metadata
            CertificateMetadataDTO.builder()
                .build()
        );
  }
}
