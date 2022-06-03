package se.inera.intyg.css.application.dto;

public record EmailRequestDTO(String to,
                              String html,
                              String subject,
                              String email_from_name
) {

  private static final String EMAIL_REGEX =
      "^email:(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

  public EmailRequestDTO {
    if (!to.matches(EMAIL_REGEX)) {
      throw new IllegalArgumentException(
          String.format("Email address '%s' must match format 'email@address.com'.", to)
      );
    }
    if (html == null || html.equals("")) {
      throw new IllegalArgumentException("Empty email message is not allowed.");
    }
    if (subject == null || subject.equals("")){
      throw new IllegalArgumentException("Empty email subject is not allowed.");
    }
    if (email_from_name == null || email_from_name.equals("")){
      throw new IllegalArgumentException("Empty email_from_name is not allowed.");
    }
  }
}
