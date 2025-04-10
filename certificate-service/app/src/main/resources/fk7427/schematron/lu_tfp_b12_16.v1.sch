<?xml version="1.0" encoding="utf-8"?>
<iso:schema
  xmlns="http://purl.oclc.org/dsdl/schematron"
  xmlns:iso="http://purl.oclc.org/dsdl/schematron"
  queryBinding='xslt2'
  schemaVersion='ISO19757-3'>

  <iso:title>Schematron file for "Läkarutlåtande tillfällig föräldrapenning barn 12–16 år" - Version 1.</iso:title>

  <iso:ns prefix="xs" uri="http://www.w3.org/2001/XMLSchema"/>
  <iso:ns prefix="rg"
    uri="urn:riv:clinicalprocess:healthcond:certificate:RegisterCertificateResponder:3"/>
  <iso:ns prefix="gn" uri="urn:riv:clinicalprocess:healthcond:certificate:3"/>
  <iso:ns prefix="tp" uri="urn:riv:clinicalprocess:healthcond:certificate:types:3"/>


  <iso:pattern id="intyg">
    <iso:rule context="//rg:intyg">
      <iso:assert test="count(gn:svar[@id='1']) ge 1">
        Ett 'Läkarutlåtande tillfällig föräldrapenning barn 12–16 år' måsta ha ett 'Grund för medicinskt underlag' svar
      </iso:assert>
      <iso:assert test="count(gn:svar[@id='1']) le 5">
        Ett 'Läkarutlåtande tillfällig föräldrapenning barn 12–16 år' får ha max 5 'Grund för medicinskt underlag'
      </iso:assert>
      <iso:assert test="count(gn:svar[@id='59']) = 1">
        Ett 'Läkarutlåtande tillfällig föräldrapenning barn 12–16 år' måste ha ett 'Hälsotillstånd' svar
      </iso:assert>
      <iso:assert test="count(gn:svar[@id='62']) = 1">
        Ett 'Läkarutlåtande tillfällig föräldrapenning barn 12–16 år' måste ha ett 'Vård eller tillsyn' svar
      </iso:assert>
    </iso:rule>
  </iso:pattern>


</iso:schema>