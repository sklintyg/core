<?xml version="1.0" encoding="utf-8"?>
<iso:schema
  xmlns="http://purl.oclc.org/dsdl/schematron"
  xmlns:iso="http://purl.oclc.org/dsdl/schematron"
  queryBinding='xslt2'
  schemaVersion='ISO19757-3'>

  <iso:title>Schematron file for "Intyg om tillfällig föräldrapenning FK7472" - Version 1.
  </iso:title>

  <iso:ns prefix="xs" uri="http://www.w3.org/2001/XMLSchema"/>
  <iso:ns prefix="rg"
    uri="urn:riv:clinicalprocess:healthcond:certificate:RegisterCertificateResponder:3"/>
  <iso:ns prefix="gn" uri="urn:riv:clinicalprocess:healthcond:certificate:3"/>
  <iso:ns prefix="tp" uri="urn:riv:clinicalprocess:healthcond:certificate:types:3"/>

  <iso:pattern id="intyg">
    <iso:rule context="//rg:intyg">
      <iso:assert test="count(gn:svar[@id='55']) = 1">
        Måsta ange ett 'Barnets diagnos eller symtom' svar
      </iso:assert>
      <iso:assert test="count(gn:svar[@id='56']) le 5 and count(gn:svar[@id='56']) ge 1">
        Måste ange ett 'Period som barnet inte bör vårdas i ordinarie tillsynsform' svar
      </iso:assert>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q55">
    <iso:rule context="//gn:svar[@id='55']">
      <iso:assert test="count(gn:delsvar[@id='55.1']) = 1">
        Ett 'Barnets diagnos eller symtom' svar måste ha ett delsvar
      </iso:assert>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q55.1">
    <iso:rule context="//gn:delsvar[@id='55.1']">
      <iso:let name="textValue" value="string(.)"/>
      <iso:assert test="normalize-space($textValue) != ''">
        Ange ett svar för frågan "Diagnos eller symtom".
      </iso:assert>
      <iso:assert test="string-length($textValue) &lt;= 318">
        Ange en text som inte är längre än 318 tecken för frågan "Diagnos eller symtom".
      </iso:assert>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q56">
    <iso:rule context="//gn:svar[@id='56']">
      <iso:assert
        test="not(preceding-sibling::gn:svar[@id='56']/gn:delsvar[@id='56.1']/tp:cv/tp:code/normalize-space() = normalize-space(gn:delsvar[@id='56.1']/tp:cv/tp:code))">
        'Ordinarie tillsynsform' (56.1) får besvaras med flera olika koder (KV_FKMU_0008) men varje
        kod får bara förekomma en gång.
      </iso:assert>
      <iso:assert test="count(gn:instans) = 1">
        Svaret för frågan "Period som barnet inte bör vårdas i ordinarie tillsynsform" måste ha ett
        instansnummer.
      </iso:assert>
      <iso:assert test="count(gn:delsvar[@id='56.1']) = 1">
        Svaret för frågan "Period som barnet inte bör vårdas i ordinarie tillsynsform" måste ha en
        'grad'.
      </iso:assert>
      <iso:assert test="count(gn:delsvar[@id='56.2']) = 1">
        Svaret för frågan "Period som barnet inte bör vårdas i ordinarie tillsynsform" måste ha en
        'period'.
      </iso:assert>
      <iso:let name="cstart"
        value="normalize-space(gn:delsvar[@id='56.2']/tp:datePeriod/tp:start)"/>
      <iso:let name="cend" value="normalize-space(gn:delsvar[@id='56.2']/tp:datePeriod/tp:end)"/>
      <iso:assert
        test="not(preceding-sibling::gn:svar[@id='56']/gn:delsvar[@id='56.2']/tp:datePeriod/tp:start[normalize-space(.) lt $cend and normalize-space(../tp:end) gt $cstart])">
        Två 'perioder' kan inte vara överlappande.
      </iso:assert>
    </iso:rule>

    <iso:rule context="//gn:delsvar[@id='56.1']">
      <iso:extends rule="cv"/>
      <iso:assert test="tp:cv/tp:codeSystem = 'KV_FKMU_0008'">
        'codeSystem' måste vara 'KV_FKMU_0008'.
      </iso:assert>
      <iso:assert
        test="matches(normalize-space(tp:cv/tp:code), '^(EN_ATTONDEL|HELA|TRE_FJARDEDELAR|HALVA|EN_FJARDEDEL)$')">
        'Ordinarie tillsynsform' kan ha ett av värdena EN_ATTONDEL, HELA, TRE_FJARDEDELAR, HALVA,
        EN_FJARDEDEL.
      </iso:assert>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q56.2">
    <iso:rule context="//gn:delsvar[@id='56.2']">
      <iso:extends rule="period"/>
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
      <iso:assert test="tp:datePeriod/tp:end castable as xs:date">'to' måste vara ett giltigt
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
