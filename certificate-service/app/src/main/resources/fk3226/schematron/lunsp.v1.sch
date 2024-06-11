<?xml version="1.0" encoding="utf-8"?>
<iso:schema
    xmlns="http://purl.oclc.org/dsdl/schematron"
    xmlns:iso="http://purl.oclc.org/dsdl/schematron"
    queryBinding='xslt2'
    schemaVersion='ISO19757-3'>

  <iso:title>Schematron file for "Läkarutlåtande för närståendepenning FK3226" - Version 1.</iso:title>

  <iso:ns prefix="xs" uri="http://www.w3.org/2001/XMLSchema"/>
  <iso:ns prefix="rg" uri="urn:riv:clinicalprocess:healthcond:certificate:RegisterCertificateResponder:3"/>
  <iso:ns prefix="gn" uri="urn:riv:clinicalprocess:healthcond:certificate:3"/>
  <iso:ns prefix="tp" uri="urn:riv:clinicalprocess:healthcond:certificate:types:3"/>

  <iso:pattern id="intyg">
    <iso:rule context="//rg:intyg">
      <iso:assert test="count(gn:svar[@id='1']) ge 1">
        Ett 'LUNSP' måsta ha ett 'Grund för medicinskt underlag' svar
      </iso:assert>
      <iso:assert test="count(gn:svar[@id='1']) le 3">
        Ett 'LUNSP' får ha max 3 'Grund för medicinskt underlag'
      </iso:assert>
      <iso:assert test="count(gn:svar[@id='52']) = 1">
        Ett 'LUNSP' måste ha ett 'Påtagligt hot mot patientens liv' svar
      </iso:assert>
      <iso:assert test="count(gn:svar[@id='53']) = 1">
        Ett 'LUNSP' måste ha ett 'Samtycke för närståendes stöd' svar
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
      <iso:assert test="not(preceding-sibling::gn:svar[@id='1']/gn:delsvar[@id='1.1']/tp:cv/tp:code/normalize-space() = normalize-space(gn:delsvar[@id='1.1']/tp:cv/tp:code))">
        Samma 'Typ av grund för MU' kan inte användas flera gånger i samma 'MU'.
      </iso:assert>
   </iso:rule>
  </iso:pattern>

  <iso:pattern id="q1.1">
    <iso:rule context="//gn:delsvar[@id='1.1']">
      <iso:extends rule="cv"/>
      <iso:assert test="tp:cv/tp:codeSystem = 'KV_FKMU_0001'">'codeSystem' måste vara 'KV_FKMU_0001'.</iso:assert>
      <iso:assert test="matches(normalize-space(tp:cv/tp:code), '^(UNDERSOKNING|JOURNALUPPGIFTER|ANNAT)$')">
        'Typ av grund för MU' kan ha ett av värdena UNDERSOKNING, JOURNALUPPGIFTER, ANNAT.
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
      <iso:assert test="count(../gn:delsvar[@id='1.1']/tp:cv/tp:code[normalize-space(.) != 'ANNAT']) = 0">
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

  <iso:pattern id="q52">
   <iso:rule context="//gn:svar[@id='52']">
      <iso:assert test="count(gn:delsvar[@id='52.1']) = 1">
          'Påtagligt hot mot patientens liv' måste ha ett 'Patientens behandling och vårdsituation' svar.
      </iso:assert>
   </iso:rule>
   <iso:rule context="//gn:delsvar[@id='52.1']">
      <iso:assert test="matches(normalize-space(tp:cv/tp:code), '^(ENDAST_PALLIATIV|AKUT_LIVSHOTANDE|ANNAT)$')">
        'Patientens behandling och vårdsituation' kan ha ett av värdena ENDAST_PALLIATIV, AKUT_LIVSHOTANDE, ANNAT.
      </iso:assert>
   </iso:rule>
   <iso:rule context="//gn:delsvar[@id='52.1']/tp:cv/tp:code[normalize-space(.) = 'ENDAST_PALLIATIV']">
     <iso:assert test="../../../gn:delsvar[@id='52.2']">
       Om 'Patientens behandling och vårdsituation' är 'ENDAST_PALLIATIV' så måste 'Ange när den aktiva behandlingen avslutades' anges.
     </iso:assert>
   </iso:rule>
   <iso:rule context="//gn:delsvar[@id='52.1']/tp:cv/tp:code[normalize-space(.) = 'ANNAT']">
      <iso:assert test="../../../gn:delsvar[@id='52.7']">
        Om 'Patientens behandling och vårdsituation' är 'ANNAT' så måste 'Beskriv på vilket sätt sjukdomstillståndet utgör ett påtagligt hot mot patientens liv' anges.
      </iso:assert>
   </iso:rule>
   <iso:rule context="//gn:delsvar[@id='52.1']/tp:cv/tp:code[normalize-space(.) = 'AKUT_LIVSHOTANDE']">
       <iso:assert test="../../../gn:delsvar[@id='52.3']">
         Om 'Patientens behandling och vårdsituation' är 'AKUT_LIVSHOTANDE' så måste 'Ange när tillståndet blev akut livshotande' anges.
       </iso:assert>
       <iso:assert test="../../../gn:delsvar[@id='52.4']">
         Om 'Patientens behandling och vårdsituation' är 'AKUT_LIVSHOTANDE' så måste 'Beskriv på vilket sätt sjukdomstillståndet utgör ett påtagligt hot mot patientens liv' anges.
       </iso:assert>
       <iso:assert test="../../../gn:delsvar[@id='52.5']">
         Om 'Patientens behandling och vårdsituation' är 'AKUT_LIVSHOTANDE' så måste 'Kan du uppskatta hur länge tillståndet kommer vara livshotande?' anges.
       </iso:assert>
   </iso:rule>
   <iso:rule context="//gn:delsvar[@id='52.5' and matches(normalize-space(.), 'true')]">
       <iso:assert test="../gn:delsvar[@id='52.6']">
           Om 'Kan du uppskatta hur länge tillståndet kommer vara livshotande?' är 'Ja' så måste 'Till och med' anges.
       </iso:assert>
   </iso:rule>
  </iso:pattern>

  <iso:pattern id="date-pattern">
    <iso:rule id="date" abstract="true">
      <iso:assert test="count(*) = 0">Datum får inte vara inbäddat i något element.</iso:assert>
      <iso:assert test=". castable as xs:date">Värdet måste vara ett giltigt datum.</iso:assert>
      <iso:assert test="matches(., '^\d{4}-\d\d-\d\d')">Datumet måste uttryckas som YYYY-MM-DD.</iso:assert>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="cv-pattern">
    <iso:rule id="cv" abstract="true">
      <iso:assert test="count(tp:cv) = 1">Ett värde av typen CV måste ha ett cv-element</iso:assert>
      <iso:assert test="count(tp:cv/tp:codeSystem) = 1">codeSystem är obligatoriskt</iso:assert>
      <iso:assert test="tp:cv/tp:codeSystem/count(*) = 0">'codeSystem' får inte vara inbäddat i något element.</iso:assert>
      <iso:assert test="count(tp:cv/tp:code) = 1">code är obligatoriskt</iso:assert>
      <iso:assert test="tp:cv/tp:code/count(*) = 0">'code' får inte vara inbäddat i något element.</iso:assert>
      <iso:assert test="count(tp:cv/tp:displayName) le 1">högst ett displayName kan anges</iso:assert>
    </iso:rule>
  </iso:pattern>

</iso:schema>
