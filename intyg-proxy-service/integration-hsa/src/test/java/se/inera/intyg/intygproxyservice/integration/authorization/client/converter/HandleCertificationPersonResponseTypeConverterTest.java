package se.inera.intyg.intygproxyservice.integration.authorization.client.converter;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.Result;
import se.riv.infrastructure.directory.authorizationmanagement.handlehospcertificationpersonresponder.v1.HandleHospCertificationPersonResponseType;
import se.riv.infrastructure.directory.authorizationmanagement.handlehospcertificationpersonresponder.v1.ResultCodeEnum;

@ExtendWith({MockitoExtension.class})
class HandleCertificationPersonResponseTypeConverterTest {

  @InjectMocks
  private HandleCertificationPersonResponseTypeConverter converter;

  @Test
  void shouldReturnEmptyIfNull() {
    final var response = converter.convert(null);

    assertEquals(Result.builder().build(), response);
  }

  @Test
  void shouldConvertCode() {
    final var type = new HandleHospCertificationPersonResponseType();
    type.setResultCode(ResultCodeEnum.OK);

    final var response = converter.convert(type);

    assertEquals(type.getResultCode().toString(), response.getResultCode());
  }

  @Test
  void shouldConvertText() {
    final var type = new HandleHospCertificationPersonResponseType();
    type.setResultText("text");

    final var response = converter.convert(type);

    assertEquals(type.getResultText(), response.getResultText());
  }
}