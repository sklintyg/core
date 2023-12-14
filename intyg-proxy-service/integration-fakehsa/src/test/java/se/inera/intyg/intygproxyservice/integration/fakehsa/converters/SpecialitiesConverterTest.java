package se.inera.intyg.intygproxyservice.integration.fakehsa.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.intygproxyservice.integration.fakehsa.repository.model.ParsedHsaPerson.Speciality;

@ExtendWith(MockitoExtension.class)
class SpecialitiesConverterTest {

  @InjectMocks
  private SpecialitiesConverter specialitiesConverter;

  private static final String SPECIALITY_NAME = "specialityName";
  private static final String SPECIALITY_CODE = "specialityCode";
  private static final Speciality SPECIALITY = Speciality.builder()
      .specialityName(SPECIALITY_NAME)
      .specialityCode(SPECIALITY_CODE)
      .build();

  @Test
  void shouldConvertName() {
    final var result = specialitiesConverter.convert(SPECIALITY);
    assertEquals(SPECIALITY_NAME, result.getSpecialityName());
  }

  @Test
  void shouldConvertCode() {
    final var result = specialitiesConverter.convert(SPECIALITY);
    assertEquals(SPECIALITY_CODE, result.getSpecialityCode());
  }
}
