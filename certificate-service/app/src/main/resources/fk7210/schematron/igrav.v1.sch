<?xml version="1.0" encoding="utf-8"?>
<iso:schema
  xmlns="http://purl.oclc.org/dsdl/schematron"
  xmlns:iso="http://purl.oclc.org/dsdl/schematron"
  queryBinding='xslt2'
  schemaVersion='ISO19757-3'>

  <iso:title>Schematron file for "Intyg om graviditet FK7210" - Version 1.</iso:title>

  <iso:ns prefix="xs" uri="http://www.w3.org/2001/XMLSchema"/>
  <iso:ns prefix="rg"
    uri="urn:riv:clinicalprocess:healthcond:certificate:RegisterCertificateResponder:3"/>
  <iso:ns prefix="gn" uri="urn:riv:clinicalprocess:healthcond:certificate:3"/>
  <iso:ns prefix="tp" uri="urn:riv:clinicalprocess:healthcond:certificate:types:3"/>

  <iso:pattern id="intyg">
    <iso:rule context="//rg:intyg">
      <iso:assert test="count(gn:svar[@id='54']) = 1">
        Ett intyg måsta ha ett 'Beräknat födelsedatum' svar
      </iso:assert>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q54">
    <iso:rule context="//gn:svar[@id='54']">
      <iso:assert test="count(gn:delsvar[@id='54.1']) = 1">
        Ett 'Beräknat födelsedatum' svar måste ha ett delsvar
      </iso:assert>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q54.1">
    <iso:rule context="//gn:delsvar[@id='54.1']">
      <iso:let name="currDate" value="xs:date(current-date())"/>
      <iso:let name="currDatePlusOneYear" value="$currDate + xs:dayTimeDuration('P366D')"/>
      <iso:let name="expectedBirthDate" value="xs:date(.)"/>
      <iso:extends rule="date"/>
      <iso:assert test="$expectedBirthDate ge $currDate">
        Datum som anges som 'Beräknat födelsedatum' får inte vara tidigare än dagens datum.
      </iso:assert>
      <iso:assert test="$expectedBirthDate le $currDatePlusOneYear">
        Datum som anges som 'Beräknat födelsedatum' får inte vara längre än 366 dagar framåt i
        tiden.
      </iso:assert>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="date-pattern">
    <iso:rule id="date" abstract="true">
      <iso:assert test="count(*) = 0">Datum får inte vara inbäddat i något element.</iso:assert>
      <iso:assert test=". castable as xs:date">Värdet måste vara ett giltigt datum.</iso:assert>
      <iso:assert test="matches(., '^\d{4}-\d{2}-\d{2}')">Datumet måste uttryckas som YYYY-MM-DD.
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

</iso:schema>
