# TS8071 V2.0 Specification Analysis

## Analysis Method

This analysis compares the TS8071 V2.0 specification against the V1 implementation files ONLY. It
identifies which elements need to be created for V2 by examining what exists in V1 and what the V2
spec requires.

## Summary

- **Total Questions in V2 Spec**: ~95 questions/elements
- **Questions that can use V1 implementation (COMMON)**: ~75 questions
- **Questions that need NEW V2 implementation**: ~20 questions
- **Questions REMOVED from V2 (V1 only)**: 6 questions (ADHD category)

---

## Detailed Analysis by Category

### Category 1: Intyget avser (ID: 1)

**V2 Spec Requirements:**

- **ID**: 1 (KAT), 1 (FRG)
- **Field ID**: 1.1
- **Type**: SK-004a (villkorad checkbox, kodverk)
- **Options**: 7 codes (gr_II, gr_II_III, forlang_gr_II, forlang_gr_II_III, utbyt_utl_kk, tax_leg,
  int_begar_ts)
- **Multiplicitet**: 1..2
- **Alert**: "Endast ett alternativ kan väljas. Undantaget är om intyget avser
  taxiförarlegitimation, då kan två val göras."
- **Rules**: SR-001 (mandatory), SR-008 (5 disable rules)

**V1 Implementation:**

- **File**: `QuestionIntygetAvserV1.java`
- **Alert Message**: "Välj \"ansökan om taxiförarlegitimation\" endast om personen saknar
  taxiförarlegitimation och ansöker om en sådan i samband med detta intyg."
- All other aspects identical

**Decision**: ❌ **NEED NEW V2 QUESTION** - Alert message text is different

---

### Category 2: Intyget är baserat på (ID: 2.0, 2.2)

**V2 Spec Requirements:**

- **ID**: 2.0 (FRG), 2.2 (DFR)
- **Type**: SK-003 (checkbox), SK-005 (date)
- **Options**: Journaluppgifter, Distanskontakt, Undersökning av personen
- **Show rule**: SR-003 for datum

**V1 Implementation:**

- **Files**: `QuestionBaseratPa.java`, `QuestionBaseratPaDatum.java` (in common folder)
- All aspects identical

**Decision**: ✅ **USE V1 (COMMON)** - Identical

---

### Category 3: Identitet (ID: 3.0)

**V2 Spec Requirements:**

- **ID**: 3.0 (FRG)
- **Field ID**: 3.1
- **Type**: SK-002 (checkbox)
- **Options**: 6 codes from KV_ID_KONTROLL

**V1 Implementation:**

- **File**: `QuestionIdentitet.java` (in common folder)
- All aspects identical

**Decision**: ✅ **USE V1 (COMMON)** - Identical

---

### Category 4: Synfunktioner (ID: 4)

**V2 Spec Requirements:**

- **ID**: 4 (FRG)
- **Field ID**: 4.1
- **Type**: SK-002 (Boolean)
- **Text**: "Intyg om synfunktioner kommer att skickas in av legitimerad optiker"

**V1 Implementation:**

- **File**: `QuestionSynfunktioner.java` (in common folder)
- All aspects identical

**Decision**: ✅ **USE V1 (COMMON)** - Identical

---

### Category 5: Synskärpa (ID: 5.0 - 5.6, 24, 25)

**V2 Spec Requirements:**

- **ID**: 5.0-5.6 (KKSF-007 - Synskärpa matrix)
- **ID**: 24 (DFR) - SK-007 (text, 250 chars), SR-003 show rule, Text: "Ange eventuella problem med
  tolerans av korrektionen"
- **ID**: 25 (FRG) - SK-002 (Boolean), SR-003 show rule, Text: "Vid korrigering av synskärpa med
  glasögon, har något av glasen en styrka över plus 8 dioptrier..."

**V1 Implementation:**

- **Files**:
    - `QuestionSynskarpa.java` (common) - ✅ Identical
    - `QuestionKorrigeringAvSynskarpaV1.java` - Different structure
    - `QuestionKorrigeringAvSynskarpaKontaktlinserV1.java` - Not in V2 spec
    - `QuestionKorrigeringAvSynskarpaStyrkaOverV1.java` - Different structure
    - `QuestionKorrigeringAvSynskarpaIngenStyrkaOverV1.java` - Not in V2 spec

**Decision**:

- ✅ **USE V1 (COMMON)** for 5.0-5.6
- ❌ **NEED NEW V2 QUESTIONS** for 24 and 25 - Different structure/text from V1

---

### Category 6: Anamnesfrågor (ID: 7, 7.2, 7.3, 7.4)

**V2 Spec Requirements:**

- **ID**: 7.1 (FRG) - SK-002, Text: "Finns uppgift om ögonsjukdom eller synnedsättning?"
- **ID**: 7.2 (DFR) - SK-007 (250 chars), Text: "Ange sjukdom/synnedsättning"
- **ID**: 7.3 (FRG) - SK-002, Text: "Finns uppgift om annan sjukdomshistorik eller andra
  omständigheter som kan indikera påverkan på synfunktionerna?"
- **ID**: 7.4 (DFR) - SK-007 (250 chars), Text: "Ange vad"

**V1 Implementation:**

- **Files**:
    - `QuestionSjukdomEllerSynnedsattning.java` (common) - ✅ 7.1 Identical
    - `QuestionSjukdomEllerSynnedsattningBeskrivning.java` (common) - ✅ 7.2 Identical
    - `QuestionSjukdomshistorik.java` (common) - ✅ 7.3 Identical
    - `QuestionSjukdomshistorikBeskrivningV1.java` - Text: "Ange vilken eller vilka sjukdomar"

**Decision**:

- ✅ **USE V1 (COMMON)** for 7.1, 7.2, 7.3
- ❌ **NEED NEW V2 QUESTION** for 7.4 - Text is different ("Ange vad" vs "Ange vilken eller vilka
  sjukdomar")

---

### Category 7: Balanssinne (ID: 8.0, 8.2)

**V2 Spec Requirements:**

- **ID**: 8.0 (FRG) - SK-002, Text: "Har personen överraskande anfall av balansrubbningar eller
  yrsel som kan innebära en trafiksäkerhetsrisk?"
- **ID**: 8.2 (DFR) - SK-007 (250 chars), Text: "Ange vilken typ av anfall och tidpunkt för senaste
  anfall"

**V1 Implementation:**

- **Files**:
    - `QuestionBalanssinne.java` (common) - ✅ 8.0 Identical
    - `QuestionBalanssinneBeskrivningV1.java` (common) - SK-006 (TextField, 50 chars), same text

**Decision**:

- ✅ **USE V1 (COMMON)** for 8.0
- ❌ **NEED NEW V2 QUESTION** for 8.2 - Configuration changed from SK-006 (TextField, 50 chars) to
  SK-007 (TextArea, 250 chars)

---

### Category 8: Hörsel (ID: 9.0, 9.2, 9.3)

**V2 Spec Requirements:**

- **Category**: SR-003 show rule - Show when 1.1 = <gr_II_III, forlang_gr_II_III, tax_leg>
- **ID**: 9.0 (FRG) - SK-002, Text: "Har personen svårt att uppfatta vanlig samtalsstämma på fyra
  meters avstånd..."
- **ID**: 9.2 (FRG) - SK-002, Text: "Behöver personen använda hörapparat..."
- **ID**: 9.3 (DFR) - SK-004 (checkbox), Options: Vänster, Höger

**V1 Implementation:**

- **Files**:
    - `CategoryHorselV1.java` (common) - Different show rule (includes more values)
    - `QuestionHorsel.java` (common) - ✅ 9.0 Identical
    - `QuestionHorselhjalpmedel.java` (common) - ✅ 9.2 Identical
    - `QuestionHorselhjalpmedelPositionV1.java` (common) - Same text, same config

**Decision**:

- ❌ **NEED NEW V2 CATEGORY** - Show rule is different
- ✅ **USE V1 (COMMON)** for 9.0, 9.2
- ❌ **NEED NEW V2 QUESTION** for 9.3 - To ensure it's under the correct V2 category

---

### Category 9: Rörelseorganens funktioner (ID: 10, 10.2, 10.3)

**V2 Spec Requirements:**

- **ID**: 10 (FRG) - SK-002, Text: "Har personen någon sjukdom eller funktionsnedsättning som
  påverkar rörligheten..."
- **ID**: 10.2 (DFR) - SK-007 (250 chars), Text: "Ange nedsättning eller sjukdom"
- **ID**: 10.3 (FRG) - SK-002, Text: "Har personen en nedsättning av rörelseförmågan som gör att
  personen inte kan hjälpa passagerare..."

**V1 Implementation:**

- **Files**:
    - `QuestionRorlighet.java` (common) - ✅ 10 Identical
    - `QuestionRorlighetBeskrivning.java` (common) - ✅ 10.2 Identical
    - `QuestionRorlighetHjalpaPassagerare.java` (common) - ✅ 10.3 Identical

**Decision**: ✅ **USE V1 (COMMON)** - All identical

---

### Category 10: Hjärt- och kärlsjukdomar (ID: 11-11.10)

**V2 Spec Requirements:**

- **ID**: 11.1 (FRG) - SK-002, Text: "Har eller har personen haft någon hjärt- eller kärlsjukdom?"
- **ID**: 11.2 (FRG) - SK-007 (250 chars), Text: "Ange vilken sjukdom **och tidpunkt för diagnos**"
- **ID**: 11.3 (DFR) - SK-002, Text: "Är tillståndet behandlat?"
- **ID**: 11.4 (DFR) - SK-007 (250 chars), Text: "Ange när och hur"
- **ID**: 11.5 (FRG) - SK-002, Text: "Har personen eller har personen haft någon arytmi?"
- **ID**: 11.6 (DFR) - SK-006 (50 chars), Text: "Ange tidpunkt"
- **ID**: 11.7 (FRG) - SK-002, Text: "Har personen **haft en** synkope?"
- **ID**: 11.8 (DFR) - SK-006 (50 chars), Text: "Ange tidpunkt"
- **ID**: 11.9 (FRG) - SK-002, Text: "Har personen haft en stroke eller finns tecken på
  hjärnskada..."
- **ID**: 11.10 (DFR) - SK-002 (Radio: Ja/Nej/Vet inte), Text: "Om stroke förekommit, har det
  inträffat eller påverkat syncentrum..."

**V1 Implementation:**

- **Files**:
    - `QuestionHjartsjukdom.java` (common) - ✅ 11.1 Identical
    - `QuestionHjartsjukdomBeskrivningV1.java` - Text: "Ange vilken sjukdom" (missing "och tidpunkt
      för diagnos")
    - `QuestionHjartsjukdomBehandlad.java` (common) - ✅ 11.3 Identical
    - `QuestionHjartsjukdomBehandladBeskrivningV1.java` (common) - SK-006 (TextField, 50 chars),
      Text: "Ange när och hur tillståndet behandlats"
    - `QuestionArytmi.java` (common) - ✅ 11.5 Identical
    - `QuestionArytmiBeskrivning.java` (common) - ✅ 11.6 Identical
    - `QuestionSynkopeV1.java` - Text: "Har personen eller har personen haft någon synkope?" (
      different)
    - `QuestionSynkopeBeskrivning.java` (common) - ✅ 11.8 Identical
    - `QuestionStroke.java` (common) - ✅ 11.9 Identical
    - `QuestionStrokePavarkan.java` (common) - ✅ 11.10 Identical

**Decision**:

- ✅ **USE V1 (COMMON)** for 11.1, 11.3, 11.5, 11.6, 11.8, 11.9, 11.10
- ❌ **NEED NEW V2 QUESTION** for 11.2 - Text adds "och tidpunkt för diagnos"
- ❌ **NEED NEW V2 QUESTION** for 11.4 - Config changed from SK-006 (TextField, 50) to SK-007 (
  TextArea, 250), Text changed
- ❌ **NEED NEW V2 QUESTION** for 11.7 - Text changed from "Har personen eller har personen haft
  någon synkope?" to "Har personen haft en synkope?"

---

### Category 11: Diabetes (ID: 12, 12.2)

**V2 Spec Requirements:**

- **ID**: 12 (FRG) - SK-002, Text: "Har personen läkemedelsbehandlad diabetes?"
- **ID**: 12.2 - SK-A01 (alert), Text: "Har personen läkemedelsbehandlad diabetes krävs normalt ett
  särskilt läkarintyg..."

**V1 Implementation:**

- **Files**:
    - `QuestionDiabetes.java` (common) - ✅ 12 Identical
    - `MessageDiabetes.java` (common) - ✅ 12.2 Identical

**Decision**: ✅ **USE V1 (COMMON)** - All identical

---

### Category 12: Neurologiska sjukdomar (ID: 13, 13.2)

**V2 Spec Requirements:**

- **ID**: 13 (FRG) - SK-002, Text: "Har personen någon neurologisk sjukdom eller finns tecken på
  neurologisk sjukdom?"
- **Help Text**: "Med neurologisk sjukdom avses exempelvis Parkinson, MS eller motoriska tics. Här
  avses även andra medfödda och tidigt förvärvade skador i nervsystemet som lett till begränsad
  rörelseförmåga och där behov av hjälpmedel för anpassat fordon föreligger."
- **ID**: 13.2 (DFR) - SK-007 (text, 250 chars), Text: "Ange vilken sjukdom och vilka tecken"

**V1 Implementation:**

- **Files**:
    - `QuestionNeurologiskSjukdom.java` (common) - Has description but INCOMPLETE: Missing
      examples "Med neurologisk sjukdom avses exempelvis Parkinson, MS eller motoriska tics."
    - `QuestionNeurologiskSjukdomBeskrivning.java` (common) - TextField, 50 chars

**Decision**:

- ❌ **NEED NEW V2 QUESTION** for 13 - Description incomplete (missing examples sentence)
- ❌ **NEED NEW V2 QUESTION** for 13.2 - Config changed TextField→TextArea, 50→250 chars

**V2 Implementation Created:**

- ✅ `QuestionNeurologiskSjukdomV2.java` - With complete help text including examples
- ✅ `QuestionNeurologiskSjukdomV2Test.java` - Test file created
- ✅ Added to `CertificateModelFactoryTS8071V2.java`

---

### Category 13: Epilepsi (ID: 14-14.9)

**V2 Spec Requirements:**

- **ID**: 14.1 (FRG) - SK-002, Text: "Har eller har personen haft epilepsi?"
- **ID**: 14.2 (DFR) - SK-006 (50 chars), Text: "Ange tidpunkt för senaste epileptiska anfall"
- **ID**: 14.3 (FRG) - SK-002, Text: "Har eller har personen haft epileptiskt anfall..."
- **ID**: 14.4 (DFR) - SK-006 (50 chars), Text: "Ange tidpunkt för senaste epileptiska anfall"
- **ID**: 14.5 (DFR) - SK-002, SR-003 show rule (14.1 **och/eller** 14.3), Text: "Har eller har
  personen haft någon krampförebyggande läkemedelsbehandling..."
- **ID**: 14.6 (DFR) - SK-007 (250 chars), Text: "Ange vilket läkemedel"
- **ID**: 14.7 (DFR) - SK-006 (50 chars), Text: "Om läkemedelsbehandling **har** avslutats, ange
  tidpunkt"
- **ID**: 14.8 (FRG) - SK-002, Text: "Har eller har personen haft någon annan medvetandestörning?"
- **ID**: 14.9 (DFR) - SK-006 (50 chars), Text: "Ange tidpunkt"

**V1 Implementation:**

- **Files**:
    - `QuestionEpilepsi.java` (common) - ✅ 14.1 Identical
    - `QuestionEpilepsiBeskrivning.java` (common) - ✅ 14.2 Identical
    - `QuestionEpilepsiAnfall.java` (common) - ✅ 14.3 Identical
    - `QuestionEpilepsiAnfallBeskrivning.java` (common) - ✅ 14.4 Identical
    - `QuestionEpilepsiMedicin.java` (common) - ✅ 14.5 Identical
    - `QuestionEpilepsiMedicinBeskrivning.java` (common) - ✅ 14.6 Identical
    - `QuestionEpilepsiMedicinTidpunktV1.java` - Text: "Om läkemedelsbehandling avslutats..." (
      missing "har")
    - `QuestionMedvetandestorning.java` (common) - ✅ 14.8 Identical
    - `QuestionMedvetandestorningTidpunkt.java` (common) - ✅ 14.9 Identical

**Decision**:

- ✅ **USE V1 (COMMON)** for 14.1, 14.2, 14.3, 14.4, 14.5, 14.6, 14.8, 14.9
- ❌ **NEED NEW V2 QUESTION** for 14.7 - Text adds "har" → "har avslutats"

---

### Category 14: Njursjukdomar (ID: 15, 15.2, 15.3)

**V2 Spec Requirements:**

- **ID**: 15.1 (FRG) - SK-002, Text: "Har personen allvarligt nedsatt njurfunktion?"
- **ID**: 15.2 (FRG) - SK-002, Text: "Har njurtransplantation genomgåtts?"
- **ID**: 15.3 (DFR) - SK-006 (50 chars), Text: "Ange tidpunkt för transplantationen"

**V1 Implementation:**

- **Files**:
    - `QuestionNjurfunktion.java` (common) - ✅ 15.1 Identical
    - `QuestionNjurtransplatation.java` (common) - ✅ 15.2 Identical
    - `QuestionNjurtransplatationTidpunkt.java` (common) - ✅ 15.3 Identical

**Decision**: ✅ **USE V1 (COMMON)** - All identical

---

### Category 15: Demens och andra kognitiva störningar (ID: 16, 16.2, 16.3)

**V2 Spec Requirements:**

- **ID**: 16.1 (DFR) - SK-002, Text: "Har personen allvarlig kognitiv störning?"
- **ID**: 16.2 (DFR) - SK-002, Text: "Har personen diagnos demens eller annan kognitiv störning..."
- **ID**: 16.3 (DFR) - SK-007 (250 chars), Text: "Ange vilka tecken, eventuell diagnos och grad"

**V1 Implementation:**

- **Files**:
    - `QuestionKognitivStorningV1.java` - Text: "Har personen **diagnos** allvarlig kognitiv
      störning?" (extra word "diagnos")
    - `QuestionDemens.java` (common) - ✅ 16.2 Identical
    - `QuestionDemensBeskrivningV1.java` (common) - Text includes extra parenthetical: "(Med grader
      avses lindrig, måttlig/medelsvår eller grav/allvarlig.)"

**Decision**:

- ❌ **NEED NEW V2 QUESTION** for 16.1 - Text removes word "diagnos"
- ✅ **USE V1 (COMMON)** for 16.2
- ❌ **NEED NEW V2 QUESTION** for 16.3 - Text is shorter (removes parenthetical explanation)

---

### Category 16: Sömn- och vakenhetsstörningar (ID: 17, 17.2, 17.3)

**V2 Spec Requirements:**

- **ID**: 17.1 (FRG) - SK-002, Text: "Har personen en sömn- eller vakenhetsstörning eller symtom på
  sådan problematik?"
- **Help Text**: "Här avses sömnapné och narkolepsi. Här avses även snarksjukdom som kan utgöra en
  trafiksäkerhetsrisk och annan sjukdom med sömnstörning. Insomningsbesvär som behandlas med
  läkemedel och inte utgör en trafiksäkerhetsrisk omfattas inte."
- **ID**: 17.2 (DFR) - SK-007 (250 chars), Text: "Ange vilken diagnos/vilka symtom"
- **ID**: 17.3 (FRG) - SK-002, Text: "Förekommer behandling mot sömn- och vakenhetsstörning?"

**V1 Implementation:**

- **Files**:
    - `QuestionSomn.java` (common) - Has description but INCORRECT wording: "Insomningsbesvär som
      läkemedelbehandlas..." instead of "Insomningsbesvär som behandlas med läkemedel..."
    - `QuestionSomnBeskrivning.java` (common) - ✅ 17.2 Identical
    - `QuestionSomnBehandling.java` (common) - ✅ 17.3 Identical

**Decision**:

- ❌ **NEED NEW V2 QUESTION** for 17.1 - Description text differs (compound word vs. phrase)
- ✅ **USE V1 (COMMON)** for 17.2, 17.3

**V2 Implementation Created:**

- ✅ `QuestionSomnV2.java` - With corrected help text wording
- ✅ `QuestionSomnV2Test.java` - Test file created
- ✅ Added to `CertificateModelFactoryTS8071V2.java`

---

### Category 17: Alkohol, andra psykoaktiva substanser och läkemedel (ID: 18-18.10)

**V2 Spec Requirements:**

- **ID**: 18.1 (FRG) - SK-002, Text: "Har personen eller har personen haft en diagnos **avseende
  alkohol, andra psykoaktiva substanser eller läkemedel**?"
- **ID**: 18.2 (DFR) - SK-007 (**400 chars**), Text: "Ange **diagnos**, tidpunkt för när diagnosen
  ställdes och för vilken/vilka substanser"
- **ID**: 18.10 (DFR) - **NEW** SK-002 (Radio: Ja/Nej/Vet inte), SR-003 show (18.1=true), Text: "Om
  diagnos beroende, är beroendet i fullständig långvarig remission?"
- **ID**: 18.3 (FRG) - SK-002, Text: "Finns journaluppgifter, anamnestiska uppgifter, resultat av
  laboratorieprover eller andra tecken på pågående skadligt bruk, missbruk eller beroende av
  alkohol, narkotika eller läkemedel?"
- **Help Text**: "Här avses uppgifter om eller tecken på beroende av psykoaktiv substans oavsett när
  i tid detta förekommit. Här avses också uppgifter om eller tecken på aktuellt skadligt mönster av
  bruk, skadligt bruk eller överkonsumtion av alkohol som inte är tillfälligt under de senaste tolv
  månaderna."
- **ID**: 18.4 (DFR) - SK-007 (250 chars), Text: "Ange vilka uppgifter eller tecken och när det var"
- **ID**: 18.5 (DFR) - SK-007 (250 chars), Text: "Om provtagning gjorts ska resultatet redovisas
  nedan. Ange datum för provtagning och resultat"
- **ID**: 18.6 (FRG) - SK-002, Text: "Har personen vid något tillfälle vårdats eller sökt hjälp för
  substansrelaterad diagnos..."
- **ID**: 18.7 (DFR) - SK-007 (250 chars), Text: "Ange vilken form av hjälp eller vård och när det
  var"
- **ID**: 18.8 (FRG) - SK-002, Text: "Pågår läkarordinerat bruk av läkemedel som kan innebära en
  trafiksäkerhetsrisk?"
- **ID**: 18.9 (DFR) - SK-007 (250 chars), Text: "Ange läkemedel och ordinerad dos"

**V1 Implementation:**

- **Files**:
    - `QuestionMissbrukV1.java` - Text: "Har personen eller har personen haft en diagnos **missbruk,
      beroende eller substansbrukssyndrom**?" (different)
    - `QuestionMissbrukBeskrivningV1.java` - SK-007 (250 chars), Text: "Ange **vilken**
      diagnos..." (different text, different limit)
    - **NO V1 FILE for 18.10** - This is NEW in V2
    - `QuestionMissbrukJournaluppgifter.java` (common) - Has description but INCOMPLETE: "
      ...aktuellt skadligt bruk eller missbruk under de senaste tolv månaderna." Missing "skadligt
      mönster av bruk" and "överkonsumtion av alkohol som inte är tillfälligt"
    - `QuestionMissbrukJournaluppgifterBeskrivning.java` (common) - SK-006 (TextField, 50 chars),
      Text: "Ange vilka uppgifter eller tecken och när **i tid det gäller**"
    - `QuestionMissbrukProvtagning.java` (common) - ✅ 18.5 Identical
    - `QuestionMissbrukVard.java` (common) - ✅ 18.6 Identical
    - `QuestionMissbrukVardBeskrivning.java` (common) - Text: "Ange vilken form av hjälp eller vård
      och när det var. **Beskriv vilken typ av insats det rör sig om.**"
    - `QuestionLakemedelV1.java` (common) - ✅ 18.8 Identical
    - `QuestionLakemedelBeskrivningV1.java` (common) - ✅ 18.9 Identical

**Decision**:

- ❌ **NEED NEW V2 QUESTION** for 18.1 - Text completely different
- ❌ **NEED NEW V2 QUESTION** for 18.2 - Text different, limit increased to 400 chars
- ❌ **NEED NEW V2 QUESTION** for 18.10 - **NEW question in V2**
- ❌ **NEED NEW V2 QUESTION** for 18.3 - Description incomplete, missing spec terminology
- ✅ **USE V1 (COMMON)** for 18.5
- ✅ **USE V1 (COMMON)** for 18.6
- ❌ **NEED NEW V2 QUESTION** for 18.4 - Config changed from SK-006 (50) to SK-007 (250), text
  slightly different
- ❌ **NEED NEW V2 QUESTION** for 18.7 - Text shortened (removed extra sentence)
- ✅ **USE V1 (COMMON)** for 18.8, 18.9

**V2 Implementation Created:**

- ✅ `QuestionMissbrukJournaluppgifterV2.java` - With complete help text from spec
- ✅ `QuestionMissbrukJournaluppgifterV2Test.java` - Test file created
- ✅ Added to `CertificateModelFactoryTS8071V2.java`

---

### Category 18: Psykiska sjukdomar och störningar (ID: 19, 19.2, 19.3)

**V2 Spec Requirements:**

- **ID**: 19.1 (FRG) - SK-002, Text: "Har personen eller har personen haft psykisk sjukdom eller
  störning?"
- **ID**: 19.2 (DFR) - SK-007 (250 chars), Text: "Ange vilken sjukdom eller störning"
- **ID**: 19.3 (DFR) - SK-006 (50 chars), Text: "När hade personen senast läkarkontakt med anledning
  av sin diagnos? Ange tidpunkt"

**V1 Implementation:**

- **Files**:
    - `QuestionPsykiskV1.java` (common) - ✅ 19.1 Identical (but in common, might need V2 for
      mapping)
    - `QuestionPsykiskBeskrivningV1.java` (common) - ✅ 19.2 Identical
    - `QuestionPsykiskTidpunktV1.java` (common) - ✅ 19.3 Identical

**Decision**: ✅ **USE V1 (COMMON)** - All identical (but may need V2 versions for proper parent
mapping)

---

### Category 19: Intellektuell funktionsnedsättning (ID: 24, 24.2)

**V2 Spec Requirements:**

- **ID**: 24.1 (DFR) - SK-002, Text: "Har personen någon intellektuell funktionsnedsättning?"
- **ID**: 24.2 (DFR) - SK-007 (250 chars), Text: "Vilken diagnos och grad?"

**V1 Implementation:**

- **Files**:
    - In V1, this was under ADHD category (KAT_14) with ID 20.6
    - `QuestionPsykiskUtvecklingsstorningV1.java` - Different text
    - `QuestionPsykiskUtvecklingsstorningAllvarligV1.java` - Different structure

**Decision**: ❌ **NEED NEW V2 QUESTIONS** for 24.1 and 24.2 - Different IDs, different structure,
moved to own category

---

### Category 20: Övrig medicinering (ID: 21, 21.2)

**V2 Spec Requirements:**

- **ID**: 21.1 (DFR) - SK-002, Text: "Har personen någon stadigvarande medicinering som inte nämnts
  i något avsnitt ovan?"
- **ID**: 21.2 (DFR) - SK-007 (250 chars), Text: "Ange vilken eller vilka mediciner"

**V1 Implementation:**

- **Files**:
    - `QuestionMedicinering.java` (common) - Different text?
    - Need to check V1 files

**Decision**: ❌ **NEED NEW V2 QUESTIONS** for 21.1 and 21.2 (need to verify against V1)

---

### Category 21: Övrig kommentar (ID: 22)

**V2 Spec Requirements:**

- **ID**: 22.1 (FRG) - SK-007 (400 chars), Text: "Ange övriga upplysningar"

**V1 Implementation:**

- **File**: `QuestionOvrigBeskrivning.java` (common) - ✅ Identical

**Decision**: ✅ **USE V1 (COMMON)** - Identical

---

### Category 22: Bedömning (ID: 23, 23.2, 23.3)

**V2 Spec Requirements:**

- **ID**: 23.1 (FRG) - SK-002 (Radio: Ja/Nej/Kan inte ta ställning), Text: "Bedöms personen utifrån
  Transportstyrelsens föreskrifter..."
- **ID**: 23.2 (FRG) - SK-007 (250 chars), SR-003 show (23.1=ejstalln), Text: "Du kan inte ta
  ställning till om det finns en risk, ange orsaken till detta"
- **ID**: 23.3 (DFR) - SK-007 (250 chars), SR-003 show (23.1=ja), Text: "Du bedömer att det finns en
  risk, ange orsaken till detta"

**V1 Implementation:**

- **Files**:
    - `QuestionBedomning.java` (common) - ✅ 23.1 Identical
    - `QuestionBedomningOkand.java` (common) - ✅ 23.2 Identical
    - `QuestionBedomningRisk.java` (common) - Different text?

**Decision**:

- ✅ **USE V1 (COMMON)** for 23.1, 23.2
- ❌ **NEED NEW V2 QUESTION** for 23.3 (need to verify text difference)

---

### REMOVED in V2 (V1 Only)

**Category**: ADHD, autismspektrumtillstånd och likartade tillstånd samt psykisk
utvecklingsstörning (KAT_14)

**V1 Files** (NOT in V2 spec):

- `CategoryAdhdAutismPsykiskUtvecklingsstorningV1.java`
- `QuestionNeuropsykiatriskV1.java` (20.1)
- `QuestionNeuropsykiatriskTrafikriskV1.java` (20.2)
- `QuestionNeuropsykiatriskTidpunktV1.java` (20.3)
- `QuestionNeuropsykiatriskLakemedelV1.java` (20.4)
- `QuestionNeuropsykiatriskLakemedelBeskrivningV1.java` (20.5)

**Decision**: ✅ **EXCLUDE FROM V2** - Entire category removed

---

## Summary Table: V2 Implementation Needs

| ID                                 | Element                     | V1 Status       | V2 Action Required                           |
|------------------------------------|-----------------------------|-----------------|----------------------------------------------|
| 1                                  | IntygetAvser                | Common exists   | ❌ NEW V2 - Alert text different              |
| 2.0, 2.2                           | BaseratPa                   | Common          | ✅ USE COMMON                                 |
| 3.0                                | Identitet                   | Common          | ✅ USE COMMON                                 |
| 4                                  | Synfunktioner               | Common          | ✅ USE COMMON                                 |
| 5.0-5.6                            | Synskarpa                   | Common          | ✅ USE COMMON                                 |
| 24                                 | ToleransKorrektion          | V1 different    | ❌ NEW V2                                     |
| 25                                 | GlasogonStyrka              | V1 different    | ❌ NEW V2                                     |
| 7.1-7.3                            | Anamnes (7.1-7.3)           | Common          | ✅ USE COMMON                                 |
| 7.4                                | SjukdomshistorikBeskrivning | V1 exists       | ❌ NEW V2 - Text different                    |
| 8.0                                | Balanssinne                 | Common          | ✅ USE COMMON                                 |
| 8.2                                | BalanssinneBeskrivning      | V1 exists       | ❌ NEW V2 - Config changed TextField→TextArea |
| 9 (cat)                            | Hörsel Category             | V1 exists       | ❌ NEW V2 - Show rule different               |
| 9.0, 9.2                           | Hörsel questions            | Common          | ✅ USE COMMON                                 |
| 9.3                                | HorselhjalpmedelPosition    | V1 exists       | ❌ NEW V2 - Under new category                |
| 10                                 | Rorlighet                   | Common          | ✅ USE COMMON                                 |
| 11.1, 11.3, 11.5, 11.6, 11.8-11.10 | Hjart (most)                | Common          | ✅ USE COMMON                                 |
| 11.2                               | HjartsjukdomBeskrivning     | V1 exists       | ❌ NEW V2 - Text adds "och tidpunkt"          |
| 11.4                               | HjartBehandladBeskrivning   | V1 exists       | ❌ NEW V2 - Config/text changed               |
| 11.7                               | Synkope                     | V1 exists       | ❌ NEW V2 - Text simplified                   |
| 12                                 | Diabetes                    | Common          | ✅ USE COMMON                                 |
| 13                                 | Neurologisk                 | Common          | ✅ USE COMMON                                 |
| 13.2                               | NeurologiskBeskrivning      | Need check      | ❌ NEW V2                                     |
| 14.1-14.6, 14.8-14.9               | Epilepsi (most)             | Common          | ✅ USE COMMON                                 |
| 14.7                               | EpilepsiMedicinTidpunkt     | V1 exists       | ❌ NEW V2 - Text adds "har"                   |
| 15                                 | Njur                        | Common          | ✅ USE COMMON                                 |
| 16.2                               | Demens                      | Common          | ✅ USE COMMON                                 |
| 16.1                               | KognitivStorning            | V1 exists       | ❌ NEW V2 - Text removes "diagnos"            |
| 16.3                               | DemensBeskrivning           | V1 exists       | ❌ NEW V2 - Text shortened                    |
| 17                                 | Somn                        | Common          | ✅ USE COMMON                                 |
| 18.3, 18.5, 18.6                   | Missbruk (some)             | Common          | ✅ USE COMMON                                 |
| 18.1                               | Missbruk                    | V1 exists       | ❌ NEW V2 - Text completely different         |
| 18.2                               | MissbrukBeskrivning         | V1 exists       | ❌ NEW V2 - Text/limit different              |
| 18.10                              | MissbrukRemission           | NOT IN V1       | ❌ NEW V2 - **NEW QUESTION**                  |
| 18.4                               | MissbrukJournalBeskrivning  | V1 exists       | ❌ NEW V2 - Config/text changed               |
| 18.7                               | MissbrukVardBeskrivning     | V1 exists       | ❌ NEW V2 - Text shortened                    |
| 18.8, 18.9                         | Lakemedel                   | Common          | ✅ USE COMMON (or V2 for mapping)             |
| 19                                 | Psykisk                     | Common          | ✅ USE COMMON (or V2 for mapping)             |
| 24                                 | IntellektuellFunktion       | V1 different ID | ❌ NEW V2 - New category, different structure |
| 21                                 | OvrigMedicinering           | Need check      | ❌ NEW V2                                     |
| 22                                 | OvrigKommentar              | Common          | ✅ USE COMMON                                 |
| 23.1, 23.2                         | Bedomning (most)            | Common          | ✅ USE COMMON                                 |
| 23.3                               | BedomningRisk               | V1 exists       | ❌ NEW V2 - Text different                    |

---

## V2 Implementation Checklist

### 1. Create V2-Specific Questions (NEW)

These questions need to be created as V2-specific because they differ from V1:

1. ✅ `QuestionIntygetAvserV2` - Different alert message
2. ✅ `QuestionToleransKorrektionV2` - New structure
3. ✅ `QuestionGlasogonStyrkaV2` - New structure
4. ✅ `QuestionSjukdomshistorikBeskrivningV2` - Different text
5. ✅ `QuestionBalanssinneBeskrivningV2` - TextField→TextArea, 50→250 chars
6. ✅ `CategoryHorselV2` - Different show rule
7. ✅ `QuestionHorselhjalpmedelPositionV2` - Under new category
8. ✅ `QuestionHjartsjukdomBeskrivningV2` - Text adds "och tidpunkt för diagnos"
9. ✅ `QuestionHjartsjukdomBehandladBeskrivningV2` - Config/text changed
10. ✅ `QuestionSynkopeV2` - Text simplified
11. ✅ `QuestionNeurologiskSjukdomBeskrivningV2` - Config check needed
12. ✅ `QuestionEpilepsiMedicinTidpunktV2` - Text adds "har"
13. ✅ `QuestionKognitivStorningV2` - Text removes "diagnos"
14. ✅ `QuestionDemensBeskrivningV2` - Text shortened
15. ✅ `QuestionMissbrukV2` - Text completely different
16. ✅ `QuestionMissbrukBeskrivningV2` - Text/limit different (400 chars)
17. ✅ `QuestionMissbrukRemissionV2` - **NEW QUESTION** (Radio: ja/nej/vetinte)
18. ✅ `QuestionMissbrukJournaluppgifterBeskrivningV2` - Config/text changed
19. ✅ `QuestionMissbrukVardV2` - For proper parent reference
20. ✅ `QuestionMissbrukVardBeskrivningV2` - Text shortened
21. ✅ `QuestionLakemedelV2` - For proper parent reference
22. ✅ `QuestionLakemedelBeskrivningV2` - For proper parent reference
23. ✅ `QuestionPsykiskV2` - For proper parent reference
24. ✅ `QuestionPsykiskBeskrivningV2` - For proper parent reference
25. ✅ `QuestionPsykiskTidpunktV2` - For proper parent reference
26. ✅ `CategoryIntellektuellFunktionsnedsattningV2` - New category
27. ✅ `QuestionIntellektuellFunktionsnedsattningV2` - New ID/structure
28. ✅ `QuestionIntellektuellFunktionsnedsattningBeskrivningV2` - New ID/structure
29. ✅ `QuestionMedicineringV2` - Need to verify
30. ✅ `QuestionMedicineringBeskrivningV2` - Need to verify
31. ✅ `QuestionBedomningRiskV2` - Text different
32. ✅ `CategoryAlkoholOchLakemedelV2` - New structure with 18.10

### 2. Reuse Common Questions (USE V1)

These can be imported from common folder:

- All questions marked ✅ USE COMMON in the table above (~75 questions)

### 3. Exclude V1-Only Content

Do NOT include in V2:

- `CategoryAdhdAutismPsykiskUtvecklingsstorningV1`
- All V1 neuropsykiatrisk questions (20.1-20.5)

---

## Conclusion

The V2 implementation requires:

- **~32 NEW V2-specific question/category files**
- **~75 questions can REUSE V1 common implementation**
- **6 V1-only questions to EXCLUDE**

This analysis is based solely on comparing the V2 specification against existing V1 implementation
files.

