<?xml version="1.0" encoding="utf-8"?>
<iso:schema
  xmlns="http://purl.oclc.org/dsdl/schematron"
  xmlns:iso="http://purl.oclc.org/dsdl/schematron"
  queryBinding='xslt2'
  schemaVersion='ISO19757-3'>

  <iso:title>Schematron file for "Läkarutlåtande tillfällig föräldrapenning för ett allvarligt sjukt
    barn som inte har fyllt 18" - Version
    1.
  </iso:title>

  <iso:ns prefix="xs" uri="http://www.w3.org/2001/XMLSchema"/>
  <iso:ns prefix="rg"
    uri="urn:riv:clinicalprocess:healthcond:certificate:RegisterCertificateResponder:3"/>
  <iso:ns prefix="gn" uri="urn:riv:clinicalprocess:healthcond:certificate:3"/>
  <iso:ns prefix="tp" uri="urn:riv:clinicalprocess:healthcond:certificate:types:3"/>


  <iso:pattern id="intyg">
    <iso:rule context="//rg:intyg">
      <iso:assert test="count(gn:svar[@id='1']) ge 1">
        Ett 'Läkarutlåtande tillfällig föräldrapenning för ett allvarligt sjukt barn som inte har
        fyllt 18' måsta ha ett 'Grund för
        medicinskt underlag' svar
      </iso:assert>
      <iso:assert test="count(gn:svar[@id='1']) le 5">
        Ett 'Läkarutlåtande tillfällig föräldrapenning för ett allvarligt sjukt barn som inte har
        fyllt 18' får ha max 5 'Grund för
        medicinskt underlag'
      </iso:assert>
      <iso:assert test="count(gn:svar[@id='58']) = 1 or count(gn:svar[@id='55']) = 1">
        Ett 'Läkarutlåtande tillfällig föräldrapenning för ett allvarligt sjukt barn som inte har
        fyllt 18' måste minst ha ett svar på
        någon av frågorna 'Barnets diagnos' eller 'Fyll i vilka symtom barnet har om diagnos inte är
        fastställd'
      </iso:assert>
      <iso:assert test="count(gn:svar[@id='71']) = 1 or count(gn:svar[@id='72']) = 1">
        Ett 'Läkarutlåtande tillfällig föräldrapenning för ett allvarligt sjukt barn som inte har
        fyllt 18' måste ha ett 'Barnets hälsotillstånd' svar
      </iso:assert>
      <iso:assert test="count(gn:svar[@id='60']) = 1">
        Ett 'Läkarutlåtande tillfällig föräldrapenning för ett allvarligt sjukt barn som inte har
        fyllt 18' måste ha ett 'Grund för bedömning' svar
      </iso:assert>
      <iso:assert test="count(gn:svar[@id='61']) = 1">
        Ett 'Läkarutlåtande tillfällig föräldrapenning för ett allvarligt sjukt barn som inte har
        fyllt 18' måste ha ett 'För vilken period bedömer du att barnet är allvarligt sjukt?' svar
      </iso:assert>
      <iso:assert test="count(gn:svar[@id='62']) = 1">
        Ett 'Läkarutlåtande tillfällig föräldrapenning för ett allvarligt sjukt barn som inte har
        fyllt 18' måste ha ett 'Sjukhusvård eller hemsjukvård' svar
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
      <iso:assert
        test="not(some $prev in preceding-sibling::gn:svar[@id='1']/gn:delsvar[@id='1.1']/tp:cv/tp:code satisfies normalize-space($prev) = normalize-space(gn:delsvar[@id='1.1']/tp:cv/tp:code))">
        Samma 'Typ av grund för MU' kan inte användas flera gånger i samma 'MU'.
      </iso:assert>
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

  <iso:pattern id="q55">
    <iso:rule context="//gn:delsvar[@id='55.1']">
      <iso:extends rule="non-empty-string"/>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q60">
    <iso:rule context="//gn:svar[@id='60']">
      <iso:assert test="count(gn:delsvar[@id='60.1']) = 1">
        Ett 'Grund för bedömning' svar måste ha ett delsvar
      </iso:assert>
    </iso:rule>
    <iso:rule context="//gn:svar[@id='55']">
      <iso:assert test="count(gn:delsvar[@id='55.1']) = 1">
        Ett 'Fyll i vilka symtom barnet har om diagnos inte är fastställd' svar måste ha ett delsvar
      </iso:assert>
    </iso:rule>
    <iso:rule context="//gn:svar[@id='19']">
      <iso:assert test="count(gn:delsvar[@id='19.1']) = 1 or count(gn:delsvar[@id='19.2']) = 1">
        Ett 'Behandling' svar måste ha ett delsvar
      </iso:assert>
    </iso:rule>
    <iso:rule context="//gn:svar[@id='61']">
      <iso:assert test="count(gn:delsvar[@id='61.2']) = 1">
        Ett 'Period för sjukdom' svar måste ha ett delsvar
      </iso:assert>
    </iso:rule>
    <iso:rule context="//gn:svar[@id='62']">
      <iso:assert test="count(gn:delsvar[@id='62.1']) = 1 and count(gn:delsvar[@id='62.3']) = 1">
        Ett 'Sjukhusvård eller hemsjukvård' svar måste ha två delsvar
      </iso:assert>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q62">
    <iso:rule context="//gn:svar[@id='62']">
      <iso:assert test="count(gn:delsvar[@id='62.1']) = 1">
        Ett 'Vård eller tillsyn' svar måste ha ett delsvar av frågan 'Vårdas barnet inneliggande på
        sjukhus?'
      </iso:assert>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q62-3">
    <iso:rule context="//gn:svar[@id='62']">
      <iso:assert test="count(gn:delsvar[@id='62.3']) = 1">
        Ett 'Vård eller tillsyn' svar måste ha ett delsvar av frågan 'Är barnet inskrivet med
        hemsjukvård?'
      </iso:assert>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q61">
    <iso:rule context="//gn:delsvar[@id='61.1']">
      <iso:extends rule="period"/>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q62.2">
    <iso:rule context="//gn:delsvar[@id='62.2']">
      <iso:extends rule="period"/>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q62.4">
    <iso:rule context="//gn:delsvar[@id='62.4']">
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

  <iso:pattern id="non-empty-string-pattern">
    <iso:rule id="non-empty-string" abstract="true">
      <iso:assert test="count(*) = 0">Värdet får inte vara inbäddat i något element.</iso:assert>
      <iso:assert test="string-length(normalize-space(text())) > 0">Sträng kan inte vara tom.
      </iso:assert>
    </iso:rule>
  </iso:pattern>
</iso:schema>