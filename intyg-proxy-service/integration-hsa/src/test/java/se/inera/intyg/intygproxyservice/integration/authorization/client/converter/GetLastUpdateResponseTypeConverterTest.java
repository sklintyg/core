package se.inera.intyg.intygproxyservice.integration.authorization.client.converter;

import static org.junit.jupiter.api.Assertions.*;
import static se.inera.intyg.intygproxyservice.integration.common.TypeConverterHelper.toXMLGregorianCalendar;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.riv.infrastructure.directory.authorizationmanagement.gethosplastupdateresponder.v1.GetHospLastUpdateResponseType;

@ExtendWith(MockitoExtension.class)
class GetLastUpdateResponseTypeConverterTest {

  @InjectMocks
  GetLastUpdateResponseTypeConverter getLastUpdateResponseTypeConverter;

  @Test
  void shouldReturnNullIfTypeIsNull() {
    final var response = getLastUpdateResponseTypeConverter.convert(null);

    assertNull(response);
  }

  @Test
  void shouldReturnNullIfTLastUpdateIsNull() {
    final var type = new GetHospLastUpdateResponseType();

    final var response = getLastUpdateResponseTypeConverter.convert(type);

    assertNull(response);
  }

  @Test
  void shouldReturnLastUpdateIfAvailable() {
    final var expected = LocalDateTime.now();
    final var type = new GetHospLastUpdateResponseType();
    type.setLastUpdate(toXMLGregorianCalendar(expected));

    final var response = getLastUpdateResponseTypeConverter.convert(type);

    assertEquals(
        expected.truncatedTo(ChronoUnit.SECONDS),
        response.truncatedTo(ChronoUnit.SECONDS)
    );
  }

}