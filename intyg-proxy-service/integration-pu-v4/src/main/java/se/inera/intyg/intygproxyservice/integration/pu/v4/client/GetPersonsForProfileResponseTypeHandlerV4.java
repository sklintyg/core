package se.inera.intyg.intygproxyservice.integration.pu.v4.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuResponse;
import se.inera.intyg.intygproxyservice.integration.pu.v4.client.converter.GetPersonsForProfileResponseTypeConverterV4;
import se.riv.strategicresourcemanagement.persons.person.getpersonsforprofileresponder.v4.GetPersonsForProfileResponseType;

@Component
@Slf4j
@RequiredArgsConstructor
public class GetPersonsForProfileResponseTypeHandlerV4 {

  private final GetPersonsForProfileResponseTypeConverterV4 getPersonsForProfileResponseTypeConverterV4;

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

    final var person = getPersonsForProfileResponseTypeConverterV4.convert(
        getPersonsForProfileResponseType.getRequestedPersonRecord().get(0)
    );

    return PuResponse.found(person);
  }

  private static boolean responseIsEmpty(
      GetPersonsForProfileResponseType getPersonsForProfileResponseType) {
    final var requestedPersonRecord = getPersonsForProfileResponseType.getRequestedPersonRecord();
    if (requestedPersonRecord.isEmpty()) {
      return true;
    }
    return requestedPersonRecord.get(0).getPersonRecord() == null;
  }

  private static boolean responseTooMany(
      GetPersonsForProfileResponseType getPersonsForProfileResponseType) {
    return getPersonsForProfileResponseType.getRequestedPersonRecord().size() > 1;
  }
}
