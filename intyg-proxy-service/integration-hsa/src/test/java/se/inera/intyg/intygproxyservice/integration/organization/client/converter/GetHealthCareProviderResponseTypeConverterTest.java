package se.inera.intyg.intygproxyservice.integration.organization.client.converter;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.organization.model.HealthCareProvider;
import se.riv.infrastructure.directory.organization.gethealthcareproviderresponder.v1.GetHealthCareProviderResponseType;
import se.riv.infrastructure.directory.organization.gethealthcareproviderresponder.v1.HealthCareProviderType;

@ExtendWith(MockitoExtension.class)
class GetHealthCareProviderResponseTypeConverterTest {

  @InjectMocks
  private GetHealthCareProviderResponseTypeConverter getHealthCareProviderResponseTypeConverter;

  @Mock
  private HealthCareProviderTypeConverter healthCareProviderTypeConverter;

  @Test
  void shouldReturnEmptyListIfResponseIsNull() {
    final var response = getHealthCareProviderResponseTypeConverter.convert(null);

    assertEquals(Collections.emptyList(), response);
  }

  @Test
  void shouldReturnEmptyListIfCareProviderIsNull() {
    final var type = new GetHealthCareProviderResponseType();

    final var response = getHealthCareProviderResponseTypeConverter.convert(type);

    assertEquals(Collections.emptyList(), response);
  }

  @Test
  void shouldReturnConvertedCareProviders() {
    final var expected = HealthCareProvider.builder().build();
    final var type = new GetHealthCareProviderResponseType();
    type.getHealthCareProvider().add(new HealthCareProviderType());
    type.getHealthCareProvider().add(new HealthCareProviderType());
    when(healthCareProviderTypeConverter.convertV1(any(HealthCareProviderType.class)))
        .thenReturn(expected);

    final var response = getHealthCareProviderResponseTypeConverter.convert(type);

    assertEquals(2, response.size());
    assertEquals(expected, response.get(0));
  }

}