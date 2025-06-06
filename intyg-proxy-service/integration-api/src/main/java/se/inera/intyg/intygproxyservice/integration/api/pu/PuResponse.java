package se.inera.intyg.intygproxyservice.integration.api.pu;


public record PuResponse(Person person, Status status) {

  public static PuResponse found(final Person person) {
    return new PuResponse(person, Status.FOUND);
  }

  public static PuResponse notFound() {
    return new PuResponse(null, Status.NOT_FOUND);
  }

  public static PuResponse notFound(String patientId) {
    return new PuResponse(Person.builder().personnummer(PersonId.of(patientId)).build(),
        Status.NOT_FOUND);
  }

  public static PuResponse error(String patientId) {
    return new PuResponse(Person.builder().personnummer(PersonId.of(patientId)).build(),
        Status.ERROR);
  }

  public static PuResponse error() {
    return new PuResponse(null, Status.ERROR);
  }

}