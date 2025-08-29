<?xml version="1.0" encoding="utf-8"?>
<iso:schema
  xmlns="http://purl.oclc.org/dsdl/schematron"
  xmlns:iso="http://purl.oclc.org/dsdl/schematron"
  queryBinding='xslt2'
  schemaVersion='ISO19757-3'>

  <iso:title>Schematron file for "Läkarintyg för sjukpenning". (FK7804)</iso:title>

  <iso:ns prefix="xs" uri="http://www.w3.org/2001/XMLSchema"/>
  <iso:ns prefix="rg"
    uri="urn:riv:clinicalprocess:healthcond:certificate:RegisterCertificateResponder:3"/>
  <iso:ns prefix="gn" uri="urn:riv:clinicalprocess:healthcond:certificate:3"/>
  <iso:ns prefix="tp" uri="urn:riv:clinicalprocess:healthcond:certificate:types:3"/>

  <iso:pattern id="intyg">
    <iso:rule context="//rg:intyg">
      <iso:assert test="count(gn:svar[@id='6']) = 1">Ett 'LISJP' måste ha ett 'Diagnos' svar
      </iso:assert>
      <iso:assert test="count(gn:svar[@id='32']) le 4 and count(gn:svar[@id='32']) ge 1">Ett 'LISJP'
        måste ha ett 'Bedömning' svar
      </iso:assert>
      <iso:assert test="count(gn:svar[@id='1']) le 5">Ett 'LISJP' får ha högst 5 'Grund för
        medicinskt underlag'
      </iso:assert>
      <iso:assert test="count(gn:svar[@id='28']) le 4">Ett 'LISJP' får ha högst 4 'Typ av
        sysselsättning'
      </iso:assert>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q1">
    <iso:rule context="//gn:svar[@id='1']">
      <iso:assert test="count(gn:instans) = 1">
        'Grund för medicinskt underlag (MU)' måste ha ett instansnummer.
      </iso:assert>
      <iso:assert test="count(gn:delsvar[@id='1.1']) = 1">
        'Grund för medicinskt underlag (MU)' måste ha ett 'Typ av grund för MU'.
      </iso:assert>
      <iso:assert test="count(gn:delsvar[@id='1.2']) = 1">
        'Grund för medicinskt underlag (MU)' måste ha ett 'Datum som grund för MU'.
      </iso:assert>
      <iso:assert test="count(gn:delsvar[@id='1.3']) le 1">
        'Grund för medicinskt underlag (MU)' får ha högst ett 'Vilken annan grund finns för MU'.
      </iso:assert>
      <iso:assert
        test="not(preceding-sibling::gn:svar[@id='1']/gn:delsvar[@id='1.1']/tp:cv/tp:code/normalize-space() = normalize-space(gn:delsvar[@id='1.1']/tp:cv/tp:code))">
        Samma 'Typ av grund för MU' kan inte användas flera gånger i samma 'LISJP'.
      </iso:assert>
      <iso:let name="delsvarsIdExpr" value="'^1\.[123]$'"/>
      <iso:assert test="count(gn:delsvar[not(matches(@id, $delsvarsIdExpr))]) = 0">
        Oväntat delsvars-id i delsvar till svar "<value-of select="@id"/>". Delsvars-id:n måste
        matcha "<value-of select="$delsvarsIdExpr"/>".
      </iso:assert>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q1.1">
    <iso:rule context="//gn:delsvar[@id='1.1']">
      <iso:extends rule="cv"/>
      <iso:assert test="tp:cv/tp:codeSystem = 'KV_FKMU_0001'">'codeSystem' måste vara
        'KV_FKMU_0001'.
      </iso:assert>
      <iso:assert
        test="matches(normalize-space(tp:cv/tp:code), '^(FYSISKUNDERSOKNING|DIGITALUNDERSOKNING|TELEFONKONTAKT|JOURNALUPPGIFTER|ANNAT)$')">
        'Typ av grund för MU' kan ha ett av värdena FYSISKUNDERSOKNING, DIGITALUNDERSOKNING,
        TELEFONKONTAKT, JOURNALUPPGIFTER, ANNAT.
      </iso:assert>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q1.2">
    <iso:rule context="//gn:delsvar[@id='1.2']">
      <iso:extends rule="date"/>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q1.3">
    <iso:rule context="//gn:delsvar[@id='1.3']">
      <iso:extends rule="non-empty-string"/>
      <iso:assert
        test="count(../gn:delsvar[@id='1.1']/tp:cv/tp:code[normalize-space(.) != 'ANNAT']) = 0">
        Om 'Typ av grund för MU' inte är 'Annat' så får 'Vilken annan grund finns för MU' inte
        anges.
      </iso:assert>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q1.1-1.3">
    <iso:rule context="//gn:delsvar[@id='1.1']/tp:cv/tp:code[normalize-space(.) = 'ANNAT']">
      <iso:assert test="../../../gn:delsvar[@id='1.3']">
        Om 'Typ av grund för MU' är 'Annat' så måste 'Vilken annan grund finns för MU' anges.
      </iso:assert>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q27">
    <iso:rule context="//gn:svar[@id='27']">
      <iso:assert test="count(gn:delsvar[@id='27.1']) = 1">
        'Smittbärarpenning' måste ha ett 'Om smittbärarpenning'.
      </iso:assert>
      <iso:let name="delsvarsIdExpr" value="'^27\.[1]$'"/>
      <iso:assert test="count(gn:delsvar[not(matches(@id, $delsvarsIdExpr))]) = 0">
        Oväntat delsvars-id i delsvar till svar "<value-of select="@id"/>". Delsvars-id:n måste
        matcha "<value-of select="$delsvarsIdExpr"/>".
      </iso:assert>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q27.1">
    <iso:rule context="//gn:delsvar[@id='27.1']">
      <iso:extends rule="boolean"/>
      <iso:assert test="matches(normalize-space(.), '1|true') or count(//gn:svar[@id='1']) ge 1">
        Ett 'LISJP' måste ange minst en 'Grund för medicinskt underlag' om 'Om smittbärarpenning' är
        'false'.
      </iso:assert>
      <iso:assert test="matches(normalize-space(.), '1|true') or count(//gn:svar[@id='28']) ge 1">
        Ett 'LISJP' måste ange minst en 'Typ av sysselsättning' om 'Om smittbärarpenning' är
        'false'.
      </iso:assert>
      <iso:assert test="matches(normalize-space(.), '1|true') or count(//gn:svar[@id='35']) = 1">
        Ett 'LISJP' måste ange minst en 'Funktionsnedsättning' om 'Om smittbärarpenning' är 'false'.
      </iso:assert>
      <iso:assert test="matches(normalize-space(.), '1|true') or count(//gn:svar[@id='17']) = 1">
        Ett 'LISJP' måste ange minst en 'Aktivitetsbegränsning' om 'Om smittbärarpenning' är
        'false'.
      </iso:assert>
      <iso:assert test="matches(normalize-space(.), '1|true') or count(//gn:svar[@id='39']) = 1">
        Ett 'LISJP' måste ange 'Prognos' om 'Om smittbärarpenning' är 'false'.
      </iso:assert>
      <iso:assert test="matches(normalize-space(.), '1|true') or count(//gn:svar[@id='39']) ge 1">
        Ett 'LISJP' måste ange minst en 'Prognos' om 'Om smittbärarpenning' är 'false'.
      </iso:assert>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q28">
    <iso:rule context="//gn:svar[@id='28']">
      <iso:assert test="count(gn:instans) = 1">
        'Typ av sysselsättning' måste ha ett instansnummer.
      </iso:assert>
      <iso:assert test="count(gn:delsvar[@id='28.1']) = 1">
        'Typ av sysselsättning' måste ha ett 'Typ av sysselsättning'.
      </iso:assert>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q28.1">
    <iso:rule context="//gn:delsvar[@id='28.1']">
      <iso:extends rule="cv"/>
      <iso:assert test="tp:cv/tp:codeSystem = 'KV_FKMU_0002'">'codeSystem' måste vara
        'KV_FKMU_0002'.
      </iso:assert>
      <iso:assert
        test="matches(normalize-space(tp:cv/tp:code), '^(NUVARANDE_ARBETE|ARBETSSOKANDE|FORALDRALEDIG|STUDIER)$')">
        'Typ av sysselsättning' kan ha ett av värdena NUVARANDE_ARBETE, ARBETSSOKANDE, FORALDRALEDIG
        eller STUDIER.
      </iso:assert>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q28.1-q29">
    <iso:rule context="//gn:delsvar[@id='28.1']/tp:cv/tp:code[normalize-space(.) = 'NUVARANDE_ARBETE']">
      <iso:assert test="count(../../../../gn:svar[@id='29']) = 1">
        Om 'Typ av sysselsättning' besvarats med 1, måste 'Nuvarande arbete' besvaras
      </iso:assert>
    </iso:rule>
    <iso:rule context="//gn:svar[@id='29']">
      <iso:assert test="count(//gn:delsvar[@id='28.1']/tp:cv/tp:code[normalize-space(.) = 'NUVARANDE_ARBETE']) = 1">
        Om 'Typ av sysselsättning' inte besvarats med 1, får 'Ange yrke och arbetsuppgifter' inte besvaras
      </iso:assert>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q28.1-R5">
    <iso:rule context="//gn:svar[@id='28']">
      <iso:assert
        test="not(preceding-sibling::gn:svar[@id='28']/gn:delsvar[@id='28.1']/tp:cv/tp:code/normalize-space() = normalize-space(gn:delsvar[@id='28.1']/tp:cv/tp:code))">
        'Typ av sysselsättning' (DFR 28.1) får besvaras med flera olika koder (KV_FKMU_0002) men
        varje kod får bara förekomma en gång.
      </iso:assert>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q29">
    <iso:rule context="//gn:svar[@id='29']">
      <iso:assert test="count(gn:delsvar[@id='29.1']) = 1">
        'Nuvarande arbete' måste ha ett 'Yrke och arbetsuppgifter'.
      </iso:assert>
      <iso:let name="delsvarsIdExpr" value="'^29\.[1]$'"/>
      <iso:assert test="count(gn:delsvar[not(matches(@id, $delsvarsIdExpr))]) = 0">
        Oväntat delsvars-id i delsvar till svar "<value-of select="@id"/>". Delsvars-id:n måste
        matcha "<value-of select="$delsvarsIdExpr"/>".
      </iso:assert>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q29.1">
    <iso:rule context="//gn:delsvar[@id='29.1']">
      <iso:extends rule="non-empty-string"/>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q6">
    <iso:rule context="//gn:svar[@id='6']">
      <iso:assert test="count(gn:delsvar[@id='6.1']) = 1">'Diagnos' måste ha ett
        'Huvuddiagnostext'.
      </iso:assert>
      <iso:assert test="count(gn:delsvar[@id='6.2']) = 1">'Diagnos' måste ha ett
        'Huvuddiagnoskod'.
      </iso:assert>
      <iso:assert test="count(gn:delsvar[@id='6.3']) le 1">'Diagnos' får ha högst ett
        'Bidiagnostext'.
      </iso:assert>
      <iso:assert test="count(gn:delsvar[@id='6.4']) le 1">'Diagnos' får ha högst ett
        'Bidiagnoskod'.
      </iso:assert>
      <iso:assert test="count(gn:delsvar[@id='6.5']) le 1">'Diagnos' får ha högst ett
        'Bidiagnostext'.
      </iso:assert>
      <iso:assert test="count(gn:delsvar[@id='6.6']) le 1">'Diagnos' får ha högst ett
        'Bidiagnoskod'.
      </iso:assert>
      <iso:let name="delsvarsIdExpr" value="'^6\.[123456]$'"/>
      <iso:assert test="count(gn:delsvar[not(matches(@id, $delsvarsIdExpr))]) = 0">
        Oväntat delsvars-id i delsvar till svar "<value-of select="@id"/>". Delsvars-id:n måste
        matcha "<value-of select="$delsvarsIdExpr"/>".
      </iso:assert>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q35.1">
    <iso:rule context="//gn:delsvar[@id='35.1']">
      <iso:extends rule="non-empty-string"/>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q17.1">
    <iso:rule context="//gn:delsvar[@id='17.1']">
      <iso:extends rule="non-empty-string"/>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q19">
    <iso:rule context="//gn:svar[@id='19']">
      <iso:assert test="count(gn:delsvar[@id='19.1']) = 1">'Medicinsk behandling' måste ha ett
        textdelsvar.
      </iso:assert>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q25">
    <iso:rule context="//gn:svar[@id='25']">
      <iso:assert test="count(gn:delsvar[@id='25.1']) = 1">'Övrigt' måste ha ett textdelsvar.
      </iso:assert>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q32">
    <iso:rule context="//gn:svar[@id='32']">
      <iso:assert test="count(gn:delsvar[@id='32.1']) = 1">'Nedsättning av arbetsförmågan' måste ha
        ett kodverksdelsvar.
      </iso:assert>
      <iso:assert test="count(gn:delsvar[@id='32.2']) = 1">'Nedsättning av arbetsförmågan' måste ha
        ett datumdelsvar.
      </iso:assert>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q33">
    <iso:rule context="//gn:svar[@id='33']">
      <iso:assert test="count(gn:delsvar[@id='33.1']) = 1">'Arbetstidsförläggning' måste ha ett
        booleskt delsvar.
      </iso:assert>
      <iso:assert test="count(gn:delsvar[@id='33.2']) le 1">'Arbetstidsförläggning' måste ha ett
        textdelsvar.
      </iso:assert>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q34">
    <iso:rule context="//gn:svar[@id='34']">
      <iso:assert test="count(gn:delsvar[@id='34.1']) = 1">'Arbetsresor' måste ha ett booleskt
        delsvar.
      </iso:assert>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q37">
    <iso:rule context="//gn:svar[@id='37']">
      <iso:assert test="count(gn:delsvar[@id='37.1']) = 1">'Försäkringsmedicinskt beslutsstöd' måste
        ha ett textdelsvar.
      </iso:assert>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q39">
    <iso:rule context="//gn:svar[@id='39']">
      <iso:assert test="count(gn:delsvar[@id='39.1']) = 1">'Prognos' måste ha ett kodverksdelsvar.
      </iso:assert>
      <iso:assert test="count(gn:delsvar[@id='39.2']) le 1">'Prognos' får ha högst ett
        textdelsvar.
      </iso:assert>
      <iso:assert test="count(gn:delsvar[@id='39.4']) le 1">'Prognos' får ha högst ett textdelsvar
        för antal månader.
      </iso:assert>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q44">
    <iso:rule context="//gn:svar[@id='44']">
      <iso:assert test="count(gn:delsvar[@id='44.1']) = 1">'Åtgärder som kan främja återgången i
        arbete' måste ha ett textdelsvar.
      </iso:assert>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="instans-pattern">
    <iso:rule context="//gn:instans">
      <iso:assert test="number(.) ge 1">
        'Instans' måste vara större än 0.
      </iso:assert>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="non-empty-string-pattern">
    <iso:rule id="non-empty-string" abstract="true">
      <iso:assert test="count(*) = 0">Värdet får inte vara inbäddat i något element.</iso:assert>
      <iso:assert test="string-length(normalize-space(text())) > 0">Sträng kan inte vara tom.
      </iso:assert>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="boolean-pattern">
    <iso:rule id="boolean" abstract="true">
      <iso:assert test="count(*) = 0">Booleskt värde får inte vara inbäddat i något element.
      </iso:assert>
      <iso:assert test=". castable as xs:boolean">Kan bara vara 'true/1' eller 'false/0'
      </iso:assert>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="cv-pattern">
    <iso:rule id="cv" abstract="true">
      <iso:assert test="count(tp:cv) = 1">Ett värde av typen CV måste ha ett cv-element</iso:assert>
      <iso:assert test="count(tp:cv/tp:codeSystem) = 1">codeSystem är obligatoriskt</iso:assert>
      <iso:assert test="tp:cv/tp:codeSystem/count(*) = 0">'codeSystem' får inte vara inbäddat i
        något element.
      </iso:assert>
      <iso:assert test="count(tp:cv/tp:code) = 1">code är obligatoriskt</iso:assert>
      <iso:assert test="tp:cv/tp:code/count(*) = 0">'code' får inte vara inbäddat i något element.
      </iso:assert>
      <iso:assert test="count(tp:cv/tp:displayName) le 1">högst ett displayName kan anges
      </iso:assert>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="date-pattern">
    <iso:rule id="date" abstract="true">
      <iso:assert test="count(*) = 0">Datum får inte vara inbäddat i något element.</iso:assert>
      <iso:assert test=". castable as xs:date">Värdet måste vara ett giltigt datum.</iso:assert>
      <iso:assert test="matches(., '^\d{4}-\d\d-\d\d')">Datumet måste uttryckas som YYYY-MM-DD.
      </iso:assert>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="period-pattern">
    <iso:rule id="period" abstract="true">
      <iso:assert test="tp:datePeriod">En period måste inneslutas av ett 'datePeriod'-element
      </iso:assert>
      <iso:assert test="tp:datePeriod/tp:start/count(*) = 0">'from' får inte vara inbäddat i något
        element.
      </iso:assert>
      <iso:assert test="tp:datePeriod/tp:start castable as xs:date">'from' måste vara ett giltigt
        datum.
      </iso:assert>
      <iso:assert test="matches(tp:datePeriod/tp:start, '^\d{4}-\d\d-\d\d')">'from' måste uttryckas
        som YYYY-MM-DD.
      </iso:assert>
      <iso:assert test="tp:datePeriod/tp:end/count(*) = 0">'tom' får inte vara inbäddat i något
        element.
      </iso:assert>
      <iso:assert test="tp:datePeriod/tp:end castable as xs:date">'tom' måste vara ett giltigt
        datum.
      </iso:assert>
      <iso:assert test="matches(tp:datePeriod/tp:end, '^\d{4}-\d\d-\d\d')">'end' måste uttryckas som
        YYYY-MM-DD.
      </iso:assert>
      <iso:assert
        test="normalize-space(tp:datePeriod/tp:start) le normalize-space(tp:datePeriod/tp:end)">
        'from' måste vara mindre än eller lika med 'to'
      </iso:assert>
    </iso:rule>
  </iso:pattern>

</iso:schema>