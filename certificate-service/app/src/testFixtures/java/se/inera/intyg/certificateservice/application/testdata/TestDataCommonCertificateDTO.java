package se.inera.intyg.certificateservice.application.testdata;

import static se.inera.intyg.certificateservice.application.testdata.TestDataCertificatePatientDTO.ATHENA_REACT_ANDERSSON_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonStaffDTO.AJLA_DOKTOR;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonWebcertUnitDTO.ALFA_MEDICINCENTRUM_DTO;
import static se.inera.intyg.certificateservice.application.testdata.TestDataCommonWebcertUnitDTO.ALFA_REGIONEN_DTO;

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

  private static final String QUESTION_ID = "questionId";
  private static final String FK7210_CONFIG_ID = "beraknatFodelsedatum";
  private static final String FK7210_CONFIG_TEXT = "Beräknat fodelsedatum";
  public static final CertificateMetadataDTO METADATA = certificateMetadata().build();

  public static CertificateMetadataDTO.CertificateMetadataDTOBuilder certificateMetadata() {
    return CertificateMetadataDTO.builder()
        .patient(ATHENA_REACT_ANDERSSON_DTO)
        .unit(ALFA_MEDICINCENTRUM_DTO)
        .careUnit(ALFA_MEDICINCENTRUM_DTO)
        .careProvider(ALFA_REGIONEN_DTO)
        .issuedBy(AJLA_DOKTOR);
  }

  public static CertificateDTOBuilder fk7210Certificate() {
    return CertificateDTO.builder()
        .data(
            Map.of(
                QUESTION_ID,
                CertificateDataElement.builder()
                    .config(
                        CertificateDataConfigDate.builder()
                            .id(FK7210_CONFIG_ID)
                            .maxDate(LocalDate.MAX)
                            .minDate(LocalDate.MIN)
                            .text(FK7210_CONFIG_TEXT)
                            .build()
                    )
                    .value(
                        CertificateDataValueDate.builder()
                            .id(FK7210_CONFIG_ID)
                            .date(LocalDate.now())
                            .build()
                    )
                    .build()
            )
        )
        .metadata(
            certificateMetadata().build()
        );
  }
}
