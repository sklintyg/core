<?xml version="1.0" encoding="utf-8"?>
<iso:schema
    xmlns="http://purl.oclc.org/dsdl/schematron"
    xmlns:iso="http://purl.oclc.org/dsdl/schematron"
    queryBinding='xslt2'
    schemaVersion='ISO19757-3'>

  <iso:title>Schematron file for "Intyg om tillfällig föräldrapenning 7443" - Version 1.</iso:title>

  <iso:ns prefix="xs" uri="http://www.w3.org/2001/XMLSchema"/>
  <iso:ns prefix="rg" uri="urn:riv:clinicalprocess:healthcond:certificate:RegisterCertificateResponder:3"/>
  <iso:ns prefix="gn" uri="urn:riv:clinicalprocess:healthcond:certificate:3"/>
  <iso:ns prefix="tp" uri="urn:riv:clinicalprocess:healthcond:certificate:types:3"/>

  <iso:pattern id="intyg">
    <iso:rule context="//rg:intyg">
      <iso:assert test="count(gn:svar[@id='1']) = 1">
        Måsta ange ett 'Barnets diagnos eller symtom' svar
      </iso:assert>
  <!--    <iso:assert test="count(gn:svar[@id='2']) le 1"> Måste ange ett 'Prognos' svar </iso:assert> -->
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q1">
    <iso:rule context="//gn:svar[@id='1']">
      <iso:assert test="count(gn:delsvar[@id='1.1']) = 1">
        Ett 'Barnets diagnos eller symtom' svar måste ha ett delsvar
      </iso:assert>
    </iso:rule>
  </iso:pattern>

  <iso:pattern id="q1.1">
    <iso:rule context="//gn:delsvar[@id='1.1']">
      <iso:let name="textValue" value="string(.)"/>
      <!-- Mandatory check -->
      <iso:assert test="normalize-space($textValue) != ''">
        Ange ett svar för frågan "Diagnos eller symtom".
      </iso:assert>
      <!-- Length limit check -->
      <iso:assert test="string-length($textValue) &lt;= 318">
        Ange en text som inte är längre än 318 tecken för frågan "Diagnos eller symtom".
      </iso:assert>
    </iso:rule>
  </iso:pattern>

</iso:schema>