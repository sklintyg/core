package se.inera.intyg.intygproxyservice.integration.pu.v5.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuResponse;
import se.inera.intyg.intygproxyservice.integration.pu.v5.client.converter.GetPersonsForProfileResponseTypeConverterV5;
import se.riv.strategicresourcemanagement.persons.person.getpersonsforprofileresponder.v5.GetPersonsForProfileResponseType;

@Component
@Slf4j
@RequiredArgsConstructor
public class GetPersonsForProfileResponseTypeHandlerV5 {

  private final GetPersonsForProfileResponseTypeConverterV5 getPersonsForProfileResponseTypeConverterV5;

  public PuResponse handle(GetPersonsForProfileResponseType getPersonsForProfileResponseType) {
    if (responseTooMany(getPersonsForProfileResponseType)) {
      log.error(
          String.format("Number of persons returned was '%s', when one was expected.",
              getPersonsForProfileResponseType.getRequestedPersonRecord().size()
          )
      );
      return PuResponse.error();
    }

    if (responseIsEmpty(getPersonsForProfileResponseType)) {
      return PuResponse.notFound();
    }

    final var person = getPersonsForProfileResponseTypeConverterV5.convert(
        getPersonsForProfileResponseType.getRequestedPersonRecord().getFirst()
    );

    return PuResponse.found(person);
  }

  private static boolean responseIsEmpty(
      GetPersonsForProfileResponseType getPersonsForProfileResponseType) {
    final var requestedPersonRecord = getPersonsForProfileResponseType.getRequestedPersonRecord();
    if (requestedPersonRecord.isEmpty()) {
      return true;
    }
    return requestedPersonRecord.getFirst().getPersonRecord() == null;
  }

  private static boolean responseTooMany(
      GetPersonsForProfileResponseType getPersonsForProfileResponseType) {
    return getPersonsForProfileResponseType.getRequestedPersonRecord().size() > 1;
  }
}
