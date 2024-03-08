package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.StringReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.Certificate;
import se.riv.clinicalprocess.healthcond.certificate.registerCertificate.v3.RegisterCertificateType;

@ExtendWith(MockitoExtension.class)
class XmlGeneratorCertificateV4Test {

  @InjectMocks
  XmlGeneratorCertificateV4 xmlGeneratorCertificateV4;

  @Test
  void shouldReturnXmlRepresentationOfEmptyCertificate() throws JAXBException {
    final var response = xmlGeneratorCertificateV4.generate(Certificate.builder().build());
    final var context = JAXBContext.newInstance(RegisterCertificateType.class);
    final var unmarshaller = context.createUnmarshaller();
    final var stringReader = new StringReader(response);

    assertDoesNotThrow(() -> unmarshaller.unmarshal(stringReader));
  }

}