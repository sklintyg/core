package se.inera.intyg.intygproxyservice.integration.authorization.client.converter;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.api.authorization.model.CommissionRight;
import se.riv.infrastructure.directory.authorizationmanagement.v2.CommissionRightType;

@ExtendWith(MockitoExtension.class)
class CommissionRightTypeConverterTest {

  private static final CommissionRightTypeConverter converter = new CommissionRightTypeConverter();

  @Test
  void shouldConvertNull() {
    final var response = converter.convert(null);

    assertEquals(CommissionRight.builder().build(), response);
  }

  @Test
  void shouldConvertActivity() {
    final var type = getType();

    final var response = converter.convert(type);

    assertEquals(type.getActivity(), response.getActivity());
  }

  @Test
  void shouldConvertScope() {
    final var type = getType();

    final var response = converter.convert(type);

    assertEquals(type.getScope(), response.getScope());
  }

  @Test
  void shouldConvertInformationClass() {
    final var type = getType();

    final var response = converter.convert(type);

    assertEquals(type.getInformationClass(), response.getInformationClass());
  }

  private CommissionRightType getType() {
    final var type = new CommissionRightType();
    type.setActivity("ACTIVITY");
    type.setScope("SCOPE");
    type.setInformationClass("IC");

    return type;
  }
}