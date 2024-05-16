package se.inera.intyg.certificateservice.domain.common.model;

public record HealthCareProfessionalLicence(String value) {

  public static final String OID = "1.2.752.29.23.1.6";

  public Code code() {
    return new Code(
        code(value),
        OID,
        value
    );
  }

  private String code(String value) {
    switch (value) {
      case "Apotekare":
        return "AP";
      case "Arbetsterapeut":
        return "AT";
      case "Audionom":
        return "AU";
      case "Barnmorska":
        return "BM";
      case "Biomedicinsk analytiker":
        return "BA";
      case "Dietist":
        return "DT";
      case "Fysioterapeut":
        return "FT";
      case "Hälso- och sjukvårdskurator":
        return "HK";
      case "Kiropraktor":
        return "KP";
      case "Logoped":
        return "LG";
      case "Läkare":
        return "LK";
      case "Naprapat":
        return "NA";
      case "Optiker":
        return "OP";
      case "Ortopedingenjör":
        return "OT";
      case "Psykolog":
        return "PS";
      case "Psykoterapeut":
        return "PT";
      case "Receptarie":
        return "RC";
      case "Röntgensjuksköterska":
        return "RS";
      case "Sjukgymnast":
        return "SG";
      case "Sjukhusfysiker":
        return "SF";
      case "Sjuksköterska":
        return "SJ";
      case "Tandhygienist":
        return "TH";
      case "Tandläkare":
        return "TL";
      default:
        return value;
    }
  }
}
