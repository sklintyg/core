package se.inera.intyg.intygproxyservice.integration.api.pu;

public record PersonId(String id
) {

  public PersonId(String id) {
    this.id = id != null ? id.replace("-", "").replace("+", "") : null;
  }

  public static PersonId of(String id) {
    return new PersonId(id);
  }

}