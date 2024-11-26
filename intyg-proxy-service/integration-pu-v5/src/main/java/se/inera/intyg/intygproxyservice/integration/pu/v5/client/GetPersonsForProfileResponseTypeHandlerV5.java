package se.inera.intyg.intygproxyservice.integration.pu.v5.client;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuPersonsResponse;
import se.inera.intyg.intygproxyservice.integration.api.pu.PuResponse;
import se.inera.intyg.intygproxyservice.integration.pu.v5.client.converter.GetPersonsForProfileResponseTypeConverterV5;
import se.inera.intyg.intygproxyservice.integration.pu.v5.client.converter.PersonalIdentityTypeConverter;
import se.riv.strategicresourcemanagement.persons.person.getpersonsforprofileresponder.v5.GetPersonsForProfileResponseType;
import se.riv.strategicresourcemanagement.persons.person.v5.RequestedPersonRecordType;

@Component
@Slf4j
@RequiredArgsConstructor
public class GetPersonsForProfileResponseTypeHandlerV5 {

  public static final int EXPECTED_NUMBER_OF_RESULTS = 1;
  private final GetPersonsForProfileResponseTypeConverterV5 getPersonsForProfileResponseTypeConverterV5;

  public PuResponse handle(GetPersonsForProfileResponseType getPersonsForProfileResponseType) {
    return handle(null, getPersonsForProfileResponseType.getRequestedPersonRecord());
  }

  private PuResponse handle(String id, List<RequestedPersonRecordType> records) {
    if (responseTooMany(records)) {
      return error(id, records);
    }

    if (responseIsEmpty(records)) {
      return PuResponse.notFound(id);
    }

    return PuResponse.found(
        getPersonsForProfileResponseTypeConverterV5.convert(records.getFirst())
    );
  }

  private static PuResponse error(String id, List<RequestedPersonRecordType> records) {
    log.error(
        String.format(
            "Number of persons returned was '%s', when %s was expected.",
            records.size(), EXPECTED_NUMBER_OF_RESULTS
        )
    );
    return PuResponse.error(id);
  }

  public PuPersonsResponse handle(List<String> personIds,
      GetPersonsForProfileResponseType getPersonsForProfileResponseType) {
    final var records = getPersonsForProfileResponseType.getRequestedPersonRecord();
    return PuPersonsResponse.builder()
        .persons(
            personIds
                .stream()
                .map(personId -> handleRecordsForPerson(personId, records))
                .toList()
        )
        .build();
  }

  private PuResponse handleRecordsForPerson(String personId,
      List<RequestedPersonRecordType> records) {
    final var personRecords = records.stream()
        .filter(
            requestedPersonRecordType -> personId.equals(
                PersonalIdentityTypeConverter.extension(
                    requestedPersonRecordType.getPersonRecord().getPersonalIdentity()
                )
            )
        )
        .toList();

    return handle(personId, personRecords);
  }

  private static boolean responseIsEmpty(List<RequestedPersonRecordType> requestedPersonRecords) {
    if (requestedPersonRecords.isEmpty()) {
      return true;
    }

    return requestedPersonRecords.stream()
        .noneMatch(requestedPersonRecord -> requestedPersonRecord.getPersonRecord() != null);
  }

  private static boolean responseTooMany(List<RequestedPersonRecordType> records) {
    return records.size() > EXPECTED_NUMBER_OF_RESULTS;
  }
}
