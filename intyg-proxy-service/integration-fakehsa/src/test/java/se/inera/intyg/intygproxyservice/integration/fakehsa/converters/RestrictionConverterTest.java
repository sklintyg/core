package se.inera.intyg.intygproxyservice.integration.fakehsa.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedHsaPerson.Restrictions;

@ExtendWith(MockitoExtension.class)
class RestrictionConverterTest {

  private static final String RESTRICTION_NAME = "restrictionName";
  private static final String RESTRICTION_CODE = "restrictionCode";
  private static final Restrictions RESTRICTION = Restrictions.builder()
      .restrictionName(RESTRICTION_NAME)
      .restrictionCode(RESTRICTION_CODE)
      .build();
  @InjectMocks
  private RestrictionConverter restrictionConverter;

  @Test
  void shouldConvertName() {
    final var result = restrictionConverter.convert(RESTRICTION);
    assertEquals(RESTRICTION_NAME, result.getRestrictionName());
  }


  @Test
  void shouldConvertCode() {
    final var result = restrictionConverter.convert(RESTRICTION);
    assertEquals(RESTRICTION_CODE, result.getRestrictionCode());
  }
}
