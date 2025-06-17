<?xml version="1.0" encoding="utf-8"?>
<iso:schema
  xmlns="http://purl.oclc.org/dsdl/schematron"
  xmlns:iso="http://purl.oclc.org/dsdl/schematron"
  queryBinding='xslt2'
  schemaVersion='ISO19757-3'>

  <iso:title>Schematron file for "Läkarutlåtande för omvårdnadsbidrag och merkostnadsersättning FK3221" - Version 1.
  </iso:title>

  <iso:ns prefix="xs" uri="http://www.w3.org/2001/XMLSchema"/>
  <iso:ns prefix="rg"
    uri="urn:riv:clinicalprocess:healthcond:certificate:RegisterCertificateResponder:3"/>
  <iso:ns prefix="gn" uri="urn:riv:clinicalprocess:healthcond:certificate:3"/>
  <iso:ns prefix="tp" uri="urn:riv:clinicalprocess:healthcond:certificate:types:3"/>

  <iso:pattern id="intyg">
    <iso:rule context="//rg:intyg">
      <iso:assert test="count(gn:svar[@id='1']) ge 1 and count(gn:svar[@id='1']) le 5">
        Ett 'Läkarutlåtande för omvårdnadsbidrag och merkostnadsersättning' måste ha mellan 1 och 5 'Grund för medicinskt underlag'
      </iso:assert>
      <iso:assert test="count(gn:svar[@id='3']) = 1">
        Ett 'Läkarutlåtande för omvårdnadsbidrag och merkostnadsersättning' måste ha ett 'Är utlåtandet även baserat på andra medicinska utredningar eller
        underlag?' svar
      </iso:assert>
      <iso:assert test="count(gn:svar[@id='4']) le 3">
        Ett 'Läkarutlåtande för omvårdnadsbidrag och merkostnadsersättning' får ha högst tre 'Andra medicinska utredningar eller underlag'
      </iso:assert>
      <iso:assert test="count(gn:svar[@id='58']) ge 1 and count(gn:svar[@id='58']) le 5">
        Ett 'Läkarutlåtande för omvårdnadsbidrag och merkostnadsersättning' måste ha minst en och högst fem 'Typ av diagnos'
      </iso:assert>
      <iso:assert test="count(gn:svar[@id='5']) = 1">
        Ett 'Läkarutlåtande för omvårdnadsbidrag och merkostnadsersättning' måste ha ett 'Sammanfatta historiken för diagnoserna' svar
      </iso:assert>
      <iso:assert test="count(gn:svar[@id='51']) = 1">
        Ett 'Läkarutlåtande för omvårdnadsbidrag och merkostnadsersättning' måste ha ett 'Prognos' svar
      </iso:assert>
      <iso:assert test="count(gn:svar[@id='8']) le 1">
        Ett 'Läkarutlåtande för omvårdnadsbidrag och merkostnadsersättning' får ha högst ett 'Funktionsnedsättning för intellektuell funktion'
      </iso:assert>
      <iso:assert test="count(gn:svar[@id='9']) le 1">
        Ett 'Läkarutlåtande för omvårdnadsbidrag och merkostnadsersättning' får ha högst ett 'Funktionsnedsättning övergripande psykosociala funktioner'
      </iso:assert>
      <iso:assert test="count(gn:svar[@id='10']) le 1">
        Ett 'Läkarutlåtande för omvårdnadsbidrag och merkostnadsersättning' får ha högst ett 'Funktionsnedsättning för uppmärksamhet, koncentration och
        exekutiv funktion'
      </iso:assert>
      <iso:assert test="count(gn:svar[@id='11']) le 1">
        Ett 'Läkarutlåtande för omvårdnadsbidrag och merkostnadsersättning' får ha högst ett 'Funktionsnedsättning för annan psykisk funktion'
      </iso:assert>
      <iso:assert test="count(gn:svar[@id='48']) le 1">
        Ett 'Läkarutlåtande för omvårdnadsbidrag och merkostnadsersättning' får ha högst ett 'Funktionsnedsättning för hörselfunktion'
      </iso:assert>
      <iso:assert test="count(gn:svar[@id='49']) le 1">
        Ett 'Läkarutlåtande för omvårdnadsbidrag och merkostnadsersättning' får ha högst ett 'Funktionsnedsättning för synfunktion'
      </iso:assert>
      <iso:assert test="count(gn:svar[@id='12']) le 1">
        Ett 'Läkarutlåtande för omvårdnadsbidrag och merkostnadsersättning' får ha högst ett 'Funktionsnedsättning för sinnesfunktioner och smärta'
      </iso:assert>
      <iso:assert test="count(gn:svar[@id='13']) le 1">
        Ett 'Läkarutlåtande för omvårdnadsbidrag och merkostnadsersättning' får ha högst ett 'Funktionsnedsättning för balans, koordination och motorik'
      </iso:assert>
      <iso:assert test="count(gn:svar[@id='14']) le 1">
        Ett 'Läkarutlåtande för omvårdnadsbidrag och merkostnadsersättning' får ha högst ett 'Funktionsnedsättning för annan kroppslig funktion'
      </iso:assert>
      <iso:assert test="count(gn:svar[matches(@id, '^(48|49|[89]|1[0-4])$')]) ge 1">
        Ett 'Läkarutlåtande för omvårdnadsbidrag och merkostnadsersättning' måste ha minst ett 'Funktionsnedsättning'
      </iso:assert>
    </iso:rule>
  </iso:pattern>








</iso:schema>