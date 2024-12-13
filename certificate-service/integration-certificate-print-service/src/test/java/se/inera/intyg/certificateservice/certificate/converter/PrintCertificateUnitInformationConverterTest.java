package se.inera.intyg.certificateservice.certificate.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationUnitContactInformation.UNIT_CONTACT_INFORMATION;
import static se.inera.intyg.certificateservice.domain.testdata.TestDataCertificate.fk7210CertificateBuilder;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementValueUnitContactInformation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.CertificateModel;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementConfigurationUnitContactInformation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementSpecification;

class PrintCertificateUnitInformationConverterTest {

  private final PrintCertificateUnitInformationConverter printCertificateUnitInformationConverter = new PrintCertificateUnitInformationConverter();

  private static final Certificate certificate = fk7210CertificateBuilder()
      .certificateModel(
          CertificateModel.builder()
              .elementSpecifications(
                  List.of(
                      ElementSpecification.builder()
                          .id(UNIT_CONTACT_INFORMATION)
                          .configuration(
                              ElementConfigurationUnitContactInformation.builder().build()
                          )
                          .build()
                  )
              )
              .build()
      )
      .elementData(
          List.of(
              ElementData.builder()
                  .id(UNIT_CONTACT_INFORMATION)
                  .value(ElementValueUnitContactInformation.builder()
                      .address("Address 1")
                      .city("City 1")
                      .zipCode("83156")
                      .phoneNumber("070 070")
                      .build()
                  ).build()
          )
      ).build();

  @Test
  void shouldConvertUnitInformation() {
    assertEquals(
        List.of(
            "Address 1",
            "83156 City 1",
            "070 070"
        ),
        printCertificateUnitInformationConverter.convert(certificate)
    );
  }
}