<?xml version="1.0" encoding="utf-8"?>
<iso:schema
    xmlns="http://purl.oclc.org/dsdl/schematron"
    xmlns:iso="http://purl.oclc.org/dsdl/schematron"
    queryBinding='xslt2'
    schemaVersion='ISO19757-3'>

  <iso:title>Schematron file for "Intyg om graviditet FK7210" - Version 1.</iso:title>

  <iso:ns prefix="xs" uri="http://www.w3.org/2001/XMLSchema"/>
  <iso:ns prefix="rg" uri="urn:riv:clinicalprocess:healthcond:certificate:RegisterCertificateResponder:3"/>
  <iso:ns prefix="gn" uri="urn:riv:clinicalprocess:healthcond:certificate:3"/>
  <iso:ns prefix="tp" uri="urn:riv:clinicalprocess:healthcond:certificate:types:3"/>

  <iso:pattern id="intyg">
    <iso:rule context="//rg:intyg">
      <iso:assert test="count(gn:svar[@id='1']) = 1">
        Ett intyg måsta ha ett 'Beräknat nedkomstdatum' svar
      </iso:assert>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q1">
    <iso:rule context="//gn:svar[@id='1']">
      <iso:assert test="count(gn:delsvar[@id='1.1']) = 1">
        Ett 'Beräknat nedkomstdatum' svar måste ha ett delsvar
      </iso:assert>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q1.1">
    <iso:rule context="//gn:delsvar[@id='1.1']">
      <iso:let name="currDate" value="xs:date(current-date())"/>
      <iso:let name="currDatePlusOneYear" value="$currDate + xs:dayTimeDuration('P366D')"/>
      <iso:let name="expectedBirthDate" value="xs:date(.)"/>
      <iso:extends rule="date"/>
      <iso:assert test="$expectedBirthDate ge $currDate">
        Datum som anges som 'Beräknat nedkomstdatum' får inte vara tidigare än dagens datum.
      </iso:assert>
      <iso:assert test="$expectedBirthDate le $currDatePlusOneYear">
        Datum som anges som 'Beräknat nedkomstdatum' får inte vara längre än 366 dagar framåt i tiden.
      </iso:assert>
    </iso:rule>
  </iso:pattern>

   <iso:pattern id="q1.2">
         <iso:rule context="//gn:delsvar[@id='1.2']">
              <iso:extends rule="cv"/>
              <iso:assert test="tp:cv/tp:codeSystem = 'KV_FKMU_0008'">
                  'codeSystem' måste vara 'KV_FKMU_0008'.
              </iso:assert>
              <iso:assert test="matches(normalize-space(tp:cv/tp:code), '^(LÄKARE|BARNMORSKA|SJUKSKÖTERSKA)$')">
                  'Intygsutfärdare' kan ha ett av värdena LÄKARE, BARNMORSKA eller SJUKSKÖTERSKA.
              </iso:assert>
          </iso:rule>
</iso:pattern>

  <iso:pattern id="date-pattern">
    <iso:rule id="date" abstract="true">
      <iso:assert test="count(*) = 0">Datum får inte vara inbäddat i något element.</iso:assert>
      <iso:assert test=". castable as xs:date">Värdet måste vara ett giltigt datum.</iso:assert>
      <iso:assert test="matches(., '^\d{4}-\d{2}-\d{2}')">Datumet måste uttryckas som YYYY-MM-DD.</iso:assert>
    </iso:rule>
  </iso:pattern>

</iso:schema>