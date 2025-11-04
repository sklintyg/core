package se.inera.intyg.certificateservice.patient.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.inera.intyg.certificateservice.domain.common.model.PersonId;
import se.inera.intyg.certificateservice.domain.patient.model.Patient;
import se.inera.intyg.certificateservice.patient.converter.PatientConverter;
import se.inera.intyg.certificateservice.patient.dto.PersonDTO;
import se.inera.intyg.certificateservice.patient.dto.PersonResponseDTO;
import se.inera.intyg.certificateservice.patient.dto.PersonsRequestDTO;
import se.inera.intyg.certificateservice.patient.dto.PersonsResponseDTO;
import se.inera.intyg.certificateservice.patient.dto.StatusDTOType;
import se.inera.intyg.certificateservice.patient.integration.IPSIntegrationService;

@ExtendWith(MockitoExtension.class)
class FindPatientsServiceTest {

  private static final String ID_1 = "id1";
  private static final String ID_2 = "id2";
  @Mock
  IPSIntegrationService ipsIntegrationService;
  @Mock
  PatientConverter patientConverter;
  @InjectMocks
  FindPatientsService findPatientsService;

  @Test
  void shouldReturnListOfPatients() {
    final var patient1 = getPatient(ID_1);
    final var patient2 = getPatient(ID_2);
    final var expectedResult = List.of(
        patient1,
        patient2
    );

    final var personIds = List.of(
        PersonId.builder()
            .id(ID_1)
            .build(),
        PersonId.builder()
            .id(ID_2)
            .build()
    );

    final var personsRequestDTO = PersonsRequestDTO.builder()
        .personIds(List.of(ID_1, ID_2))
        .build();

    final var personResponse1 = getPersonResponseDTO(ID_1, StatusDTOType.FOUND);
    final var personResponse2 = getPersonResponseDTO(ID_2, StatusDTOType.FOUND);
    final var personResponseDTOS = PersonsResponseDTO.builder()
        .persons(
            List.of(
                personResponse1,
                personResponse2
            )
        )
        .build();

    when(ipsIntegrationService.findPersons(personsRequestDTO)).thenReturn(personResponseDTOS);
    when(patientConverter.convert(personResponse1.getPerson())).thenReturn(patient1);
    when(patientConverter.convert(personResponse2.getPerson())).thenReturn(patient2);

    final var result = findPatientsService.find(personIds);
    assertEquals(expectedResult, result);
  }

  @Test
  void shouldExcludePatientsIfNotFound() {
    final var patient1 = getPatient(ID_1);
    final var expectedResult = List.of(
        patient1
    );

    final var personIds = List.of(
        PersonId.builder()
            .id(ID_1)
            .build(),
        PersonId.builder()
            .id(ID_2)
            .build()
    );

    final var personsRequestDTO = PersonsRequestDTO.builder()
        .personIds(List.of(ID_1, ID_2))
        .build();

    final var personResponse1 = getPersonResponseDTO(ID_1, StatusDTOType.FOUND);
    final var personResponse2 = getPersonResponseDTO(ID_2, StatusDTOType.NOT_FOUND);
    final var personResponseDTOS = PersonsResponseDTO.builder()
        .persons(
            List.of(
                personResponse1,
                personResponse2
            )
        )
        .build();

    when(ipsIntegrationService.findPersons(personsRequestDTO)).thenReturn(personResponseDTOS);
    when(patientConverter.convert(personResponse1.getPerson())).thenReturn(patient1);

    final var result = findPatientsService.find(personIds);
    assertEquals(expectedResult, result);
  }

  @Test
  void shouldFilterOutDuplicatedPersonIdsInRequest() {
    final var personIds = List.of(
        PersonId.builder()
            .id(ID_1)
            .build(),
        PersonId.builder()
            .id(ID_1)
            .build()
    );

    final var personsRequestDTO = PersonsRequestDTO.builder()
        .personIds(List.of(ID_1))
        .build();

    when(ipsIntegrationService.findPersons(personsRequestDTO)).thenReturn(
        PersonsResponseDTO.builder()
            .persons(Collections.emptyList())
            .build()
    );

    findPatientsService.find(personIds);

    verify(ipsIntegrationService).findPersons(personsRequestDTO);
  }


  private static Patient getPatient(String id2) {
    return Patient.builder()
        .id(
            PersonId.builder()
                .id(id2)
                .build()
        )
        .build();
  }

  private static PersonResponseDTO getPersonResponseDTO(String id1, StatusDTOType status) {
    return PersonResponseDTO.builder()
        .person(
            PersonDTO.builder()
                .personnummer(id1)
                .build()
        )
        .status(status)
        .build();
  }
}