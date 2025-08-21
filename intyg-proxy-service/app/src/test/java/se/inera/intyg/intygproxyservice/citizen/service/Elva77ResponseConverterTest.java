package se.inera.intyg.intygproxyservice.citizen.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.inera.intyg.intygproxyservice.citizen.dto.CitizenDTO;
import se.inera.intyg.intygproxyservice.citizen.dto.CitizenResponse;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Citizen;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Elva77Response;
import se.inera.intyg.intygproxyservice.integration.api.elva77.Result;
import se.inera.intyg.intygproxyservice.integration.api.pu.Status;

class Elva77ResponseConverterTest {

  private Elva77ResponseConverter elva77ResponseConverter;

  @BeforeEach
  void setUp() {
    elva77ResponseConverter = new Elva77ResponseConverter();
  }

  @Test
  void shouldConvertResponseWithErrorResult() {
    final var expectedResponse = CitizenResponse.builder()
        .status(Status.NOT_FOUND)
        .build();

    final var error = Elva77Response.error();
    final var response = elva77ResponseConverter.convert(error);

    assertEquals(expectedResponse, response);
  }

  @Test
  void shouldConvertResponseWithInfoResult() {
    final var personId = "personId";
    final var expectedResponse = CitizenResponse.builder()
        .citizen(
            CitizenDTO.builder()
                .personnummer(personId)
                .isActive(false)
                .build()
        )
        .status(Status.FOUND)
        .build();

    final var inactive = Elva77Response.inactive(personId);
    final var response = elva77ResponseConverter.convert(inactive);

    assertEquals(expectedResponse, response);
  }

  @Test
  void shouldConvertResponseWithOkResult() {
    final var personId = "personId";
    final var firstName = "firstName";
    final var lastName = "lastName";
    final var zip = "12345";
    final var streetAddress = "streetAddress";
    final var city = "city";
    final var expectedResponse = CitizenResponse.builder()
        .citizen(
            CitizenDTO.builder()
                .personnummer(personId)
                .fornamn(firstName)
                .efternamn(lastName)
                .postort(city)
                .postadress(streetAddress)
                .postnummer(zip)
                .isActive(true)
                .build()
        )
        .status(Status.FOUND)
        .build();

    final var elva77Response = Elva77Response.builder()
        .citizen(
            Citizen.builder()
                .subjectOfCareId(personId)
                .firstname(firstName)
                .lastname(lastName)
                .zip(zip)
                .streetAddress(streetAddress)
                .city(city)
                .isActive(true)
                .build()
        )
        .result(Result.OK)
        .build();

    final var response = elva77ResponseConverter.convert(elva77Response);

    assertEquals(expectedResponse, response);
  }
}