package se.inera.intyg.intygproxyservice.integration.api.pu;

import java.io.Serializable;


public class PuResponse implements Serializable {

  private static final long serialVersionUID = 2L;

  public static PuResponse found(final Person person) {
    return new PuResponse(person, Status.FOUND);
  }

  public static PuResponse notFound() {
    return new PuResponse(null, Status.NOT_FOUND);
  }

  public static PuResponse error() {
    return new PuResponse(null, Status.ERROR);
  }

  private final Person person;
  private final Status status;

  protected PuResponse(Person person, Status status) {
    this.person = person;
    this.status = status;
  }

  public Person getPerson() {
    return person;
  }

  public Status getStatus() {
    return status;
  }

  public enum Status {
    FOUND,
    NOT_FOUND,
    ERROR
  }
}
