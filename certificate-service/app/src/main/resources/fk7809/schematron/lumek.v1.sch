<?xml version="1.0" encoding="utf-8"?>
<iso:schema
  xmlns="http://purl.oclc.org/dsdl/schematron"
  xmlns:iso="http://purl.oclc.org/dsdl/schematron"
  queryBinding='xslt2'
  schemaVersion='ISO19757-3'>

  <iso:title>Schematron file for "Läkarutlåtande för merkostnadsersättning FK7809" - Version 1.
  </iso:title>

  <iso:ns prefix="xs" uri="http://www.w3.org/2001/XMLSchema"/>
  <iso:ns prefix="rg"
    uri="urn:riv:clinicalprocess:healthcond:certificate:RegisterCertificateResponder:3"/>
  <iso:ns prefix="gn" uri="urn:riv:clinicalprocess:healthcond:certificate:3"/>
  <iso:ns prefix="tp" uri="urn:riv:clinicalprocess:healthcond:certificate:types:3"/>

  <iso:pattern id="intyg">
    <iso:rule context="//rg:intyg">
      <iso:assert test="count(gn:svar[@id='1']) ge 1 and count(gn:svar[@id='1']) le 4">
        Ett 'LUMEK' måste ha mellan 1 och 4 'Grund för medicinskt underlag'
      </iso:assert>
      <iso:assert test="count(gn:svar[@id='3']) = 1">
        Ett 'LUMEK' måste ha ett 'Är utlåtandet även baserat på andra medicinska utredningar eller
        underlag?' svar
      </iso:assert>
      <iso:assert test="count(gn:svar[@id='4']) le 3">
        Ett 'LUMEK' får ha högst tre 'Andra medicinska utredningar eller underlag'
      </iso:assert>
      <iso:assert test="count(gn:svar[@id='58']) ge 1 and count(gn:svar[@id='58']) le 5">
        Ett 'LUNSP' måste ha minst en och högst fem 'Typ av diagnos'
      </iso:assert>
      <iso:assert test="count(gn:svar[@id='5']) = 1">
        Ett 'LUMEK' måste ha ett 'Sammanfatta historiken för diagnoserna' svar
      </iso:assert>
      <iso:assert test="count(gn:svar[@id='51']) = 1">
        Ett 'LUMEK' måste ha ett 'Prognos' svar
      </iso:assert>
      <iso:assert test="count(gn:svar[@id='8']) le 1">
        Ett 'LUMEK' får ha högst ett 'Funktionsnedsättning för intellektuell funktion'
      </iso:assert>
      <iso:assert test="count(gn:svar[@id='9']) le 1">
        Ett 'LUMEK' får ha högst ett 'Funktionsnedsättning övergripande psykosociala funktioner'
      </iso:assert>
      <iso:assert test="count(gn:svar[@id='10']) le 1">
        Ett 'LUMEK' får ha högst ett 'Funktionsnedsättning för uppmärksamhet, koncentration och
        exekutiv funktion'
      </iso:assert>
      <iso:assert test="count(gn:svar[@id='11']) le 1">
        Ett 'LUMEK' får ha högst ett 'Funktionsnedsättning för annan psykisk funktion'
      </iso:assert>
      <iso:assert test="count(gn:svar[@id='48']) le 1">
        Ett 'LUMEK' får ha högst ett 'Funktionsnedsättning för hörselfunktion'
      </iso:assert>
      <iso:assert test="count(gn:svar[@id='49']) le 1">
        Ett 'LUMEK' får ha högst ett 'Funktionsnedsättning för synfunktion'
      </iso:assert>
      <iso:assert test="count(gn:svar[@id='12']) le 1">
        Ett 'LUMEK' får ha högst ett 'Funktionsnedsättning för sinnesfunktioner och smärta'
      </iso:assert>
      <iso:assert test="count(gn:svar[@id='13']) le 1">
        Ett 'LUMEK' får ha högst ett 'Funktionsnedsättning för balans, koordination och motorik'
      </iso:assert>
      <iso:assert test="count(gn:svar[@id='14']) le 1">
        Ett 'LUMEK' får ha högst ett 'Funktionsnedsättning för annan kroppslig funktion'
      </iso:assert>
      <iso:assert test="count(gn:svar[matches(@id, '^(48|49|[89]|1[0-4])$')]) ge 1">
        Ett 'LUMEK' måste ha minst ett 'Funktionsnedsättning'
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
        'Grund för medicinskt underlag (MU)' får ha högst ett 'Ange vad annat är' svar.
      </iso:assert>
      <iso:assert test="count(gn:delsvar[@id='1.4']) le 1">
        'Grund för medicinskt underlag (MU)' får ha högst ett 'Ange anhörig eller annas relation
        till patienten' svar.
      </iso:assert>
      <iso:assert
        test="not(some $prev in preceding-sibling::gn:svar[@id='1']/gn:delsvar[@id='1.1']/tp:cv/tp:code satisfies normalize-space($prev) = normalize-space(gn:delsvar[@id='1.1']/tp:cv/tp:code))">
        Samma 'Typ av grund för MU' kan inte användas flera gånger i samma 'LUMEK'.
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
        test="matches(normalize-space(tp:cv/tp:code), '^(UNDERSOKNING|JOURNALUPPGIFTER|ANNAT|ANHORIG)$')">
        'Typ av grund för MU' kan ha ett av värdena UNDERSOKNING, JOURNALUPPGIFTER, ANNAT, ANHORIG.
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
        Om 'Typ av grund för MU' inte är 'Annat' så får 'Ange vad annat är' inte anges.
      </iso:assert>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q1.1-1.3">
    <iso:rule context="//gn:delsvar[@id='1.1']/tp:cv/tp:code[normalize-space(.) = 'ANNAT']">
      <iso:assert test="../../../gn:delsvar[@id='1.3']">
        Om 'Typ av grund för MU' är 'Annat' så måste 'Ange vad annat är' anges.
      </iso:assert>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q1.4">
    <iso:rule context="//gn:delsvar[@id='1.4']">
      <iso:extends rule="non-empty-string"/>
      <iso:assert
        test="count(../gn:delsvar[@id='1.1']/tp:cv/tp:code[normalize-space(.) != 'ANHORIG']) = 0">
        Om 'Typ av grund för MU' inte är 'Anhörig' så får 'Ange anhörig eller annas relation till
        patienten' inte anges.
      </iso:assert>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q1.1-1.4">
    <iso:rule context="//gn:delsvar[@id='1.1']/tp:cv/tp:code[normalize-space(.) = 'ANHORIG']">
      <iso:assert test="../../../gn:delsvar[@id='1.4']">
        Om 'Typ av grund för MU' är 'Anhorig' så måste 'Ange anhörig eller annas relation till
        patienten' anges.
      </iso:assert>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q3">
    <iso:rule context="//gn:svar[@id='3']">
      <iso:assert test="count(gn:delsvar[@id='3.1']) = 1">
        'Är utlåtandet även baserat på andra medicinska utredningar eller underlag?' måste ha ett
        delsvar.
      </iso:assert>
      <iso:let name="delsvarsIdExpr" value="'^3\.[1]$'"/>
      <iso:assert test="count(gn:delsvar[not(matches(@id, $delsvarsIdExpr))]) = 0">
        Oväntat delsvars-id i delsvar till svar "<value-of select="@id"/>". Delsvars-id:n måste
        matcha "<value-of select="$delsvarsIdExpr"/>".
      </iso:assert>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q3.1">
    <iso:rule context="//gn:delsvar[@id='3.1']">
      <iso:extends rule="boolean"/>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q3.1-4">
    <iso:rule
      context="//gn:delsvar[@id='3.1' and (normalize-space(.)='1' or normalize-space(.)='true')]">
      <iso:assert test="count(../../gn:svar[@id='4']) ge 1">
        Om 'Är utlåtandet även baserat på andra medicinska utredningar eller underlag?' besvarats
        med sant måste minst en 'Andra medicinska utredningar eller underlag' anges.
      </iso:assert>
    </iso:rule>
    <iso:rule
      context="//gn:delsvar[@id='3.1' and (normalize-space(.)='0' or normalize-space(.)='false')]">
      <iso:assert test="count(../../gn:svar[@id='4']) = 0">
        Om 'Är utlåtandet även baserat på andra medicinska utredningar eller underlag?' besvarats
        med falskt får 'Andra medicinska utredningar eller underlag' inte anges.
      </iso:assert>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q4">
    <iso:rule context="//gn:svar[@id='4']">
      <iso:assert test="count(gn:instans) = 1">
        'Andra medicinska utredningar eller underlag' måste ha ett instansnummer.
      </iso:assert>
      <iso:assert test="count(gn:delsvar[@id='4.1']) = 1">
        'Andra medicinska utredningar eller underlag' måste ha ett 'Utredning eller underlagstyp?'.
      </iso:assert>
      <iso:assert test="count(gn:delsvar[@id='4.2']) = 1">
        'Andra medicinska utredningar eller underlag' måste ha ett 'Datum för utredning eller
        underlag'.
      </iso:assert>
      <iso:assert test="count(gn:delsvar[@id='4.3']) = 1">
        'Andra medicinska utredningar eller underlag' måste ha ett 'Var utredningen kan hämtas in?'.
      </iso:assert>
      <iso:let name="delsvarsIdExpr" value="'^4\.[123]$'"/>
      <iso:assert test="count(gn:delsvar[not(matches(@id, $delsvarsIdExpr))]) = 0">
        Oväntat delsvars-id i delsvar till svar "<value-of select="@id"/>". Delsvars-id:n måste
        matcha "<value-of select="$delsvarsIdExpr"/>".
      </iso:assert>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q4.1">
    <iso:rule context="//gn:delsvar[@id='4.1']">
      <iso:extends rule="cv"/>
      <iso:assert test="tp:cv/tp:codeSystem = 'KV_FKMU_0005'">'codeSystem' måste vara
        'KV_FKMU_0005'.
      </iso:assert>
      <iso:assert
        test="matches(normalize-space(tp:cv/tp:code), '^(NEUROPSYKIATRISKT|HABILITERING|ARBETSTERAPEUT|FYSIOTERAPEUT|LOGOPED|PSYKOLOG|SPECIALISTKLINIK|VARD_UTOMLANDS|HORSELHABILITERING|SYNHABILITERINGEN|AUDIONOM|DIETIST|ORTOPTIST|ORTOPEDTEKNIKER|OVRIGT_UTLATANDE|)$')">
        'Utredning eller underlagstyp?' kan ha ett av värdena NEUROPSYKIATRISKT, HABILITERING,
        ARBETSTERAPEUT, FYSIOTERAPEUT, LOGOPED, PSYKOLOG, SPECIALISTKLINIK, VARD_UTOMLANDS,
        HORSELHABILITERING, SYNHABILITERINGEN, AUDIONOM, DIETIST, ORTOPTIST, ORTOPEDTEKNIKER,
        OVRIGT_UTLATANDE.
      </iso:assert>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q4.2">
    <iso:rule context="//gn:delsvar[@id='4.2']">
      <iso:extends rule="date"/>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q4.3">
    <iso:rule context="//gn:delsvar[@id='4.3']">
      <iso:extends rule="non-empty-string"/>
    </iso:rule>
  </iso:pattern>


  <iso:pattern id="q58">
    <iso:rule context="//gn:svar[@id='58']">
      <iso:assert test="count(gn:instans) = 1">
        'Typ av diagnos' måste ha ett instansnummer.
      </iso:assert>
      <iso:assert test="count(gn:delsvar[@id='58.1']) = 1">
        'Typ av diagnos' måste ha en 'Diagnostext'.
      </iso:assert>
      <iso:assert test="count(gn:delsvar[@id='58.2']) = 1">
        'Typ av diagnos' måste ha en 'Diagnoskod ICD-10'.
      </iso:assert>
      <iso:let name="delsvarsIdExpr" value="'^58\.(10|[1-9])$'"/>
      <iso:assert test="count(gn:delsvar[not(matches(@id, $delsvarsIdExpr))]) = 0">
        Oväntat delsvars-id i delsvar till svar "<value-of select="@id"/>". Delsvars-id:n måste
        matcha "<value-of select="$delsvarsIdExpr"/>".
      </iso:assert>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q58.1">
    <iso:rule context="//gn:delsvar[@id='58.1']">
      <iso:extends rule="non-empty-string"/>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q58.2">
    <iso:rule context="//gn:delsvar[@id='58.2']">
      <iso:extends rule="cv"/>
      <iso:assert test="matches(normalize-space(tp:cv/tp:codeSystem), '(1.2.752.116.1.1.1.1.8)')">
        Diagnoskodsystem måste vara OID för ICD-10-SE
      </iso:assert>
      <iso:assert
        test="matches(normalize-space(tp:cv/tp:code),'^([A-EG-Ya-eg-y][0-9]{2}[A-Za-z0-9-]*|[Zz][0-689][0-9][A-Za-z0-9-]*|[Zz]7[0-24-9][A-Za-z0-9-]*|[Zz]73[A-Za-z0-9-]+|[Ff][0-9]{2}[A-Za-z0-9-]+)$')">
        Diagnoskod måste anges som bokstav följt av två siffror följt av noll eller flera bokstäver,
        siffror eller bindestreck, d.v.s. minst tre positioner
        måste anges. Om diagnoskoden börjar med F eller Z73 måste bokstav och två siffor följas av
        minst en bokstav, siffra eller bindestreck, d.v.s. minst fyra
        positioner måste anges.
      </iso:assert>
      <iso:assert test="string-length(normalize-space(tp:cv/tp:code)) le 5">
        Diagnoskod får inte vara längre än 5 tecken.
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

</iso:schema>