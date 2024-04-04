package se.inera.intyg.certificateservice.infrastructure.clinicalprocesscertificatev4;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.certificate.model.ElementData;

@ExtendWith(MockitoExtension.class)
class XmlGeneratorDefaultTest {

  @InjectMocks
  XmlGeneratorDefault xmlGeneratorDefault;

  @Test
  void shouldReturnNull() {
    assertNull(xmlGeneratorDefault.generate(ElementData.builder().build()));
  }

}