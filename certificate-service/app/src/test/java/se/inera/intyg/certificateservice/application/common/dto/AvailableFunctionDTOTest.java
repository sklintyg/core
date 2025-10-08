package se.inera.intyg.certificateservice.application.common.dto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.AvailableFunction;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.AvailableFunctionInformation;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.AvailableFunctionInformationType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.AvailableFunctionType;
import se.inera.intyg.certificateservice.domain.certificatemodel.model.ElementId;

class AvailableFunctionDTOTest {

  @Test
  void shouldConvertDomainToDTO() {
    final var availableFunction = AvailableFunction.builder()
        .type(AvailableFunctionType.PRINT_CERTIFICATE)
        .name("Print")
        .description("Print description")
        .title("Print title")
        .body("Print body")
        .information(
            List.of(
                AvailableFunctionInformation.builder()
                    .id(new ElementId("ID"))
                    .type(AvailableFunctionInformationType.ALERT)
                    .text("Some info text")
                    .build()
            )
        )
        .enabled(true)
        .build();

    final var expected = AvailableFunctionDTO.builder()
        .type(
            se.inera.intyg.certificateservice.application.common.dto.AvailableFunctionType.PRINT_CERTIFICATE)
        .name("Print")
        .description("Print description")
        .title("Print title")
        .body("Print body")
        .enabled(true)
        .information(List.of(
            InformationDTO.builder()
                .id("ID")
                .type(InformationType.ALERT)
                .text("Some info text")
                .build()
        ))
        .build();

    final var actual = AvailableFunctionDTO.toDTO(availableFunction);

    assertThat(actual).isEqualTo(expected);
  }
}
