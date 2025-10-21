# TS8071 V2 Analysis - Common vs New Questions

## Overview

This document analyzes the TS8071 v2 specification to identify which questions are common with v1
and which are new.

## Summary

- **Total Questions in V2**: ~90+ questions (including sub-questions)
- **Truly Identical Questions**: ~84+ questions (IDs, texts, rules, validations all match)
- **Questions Needing Text/Rule Updates**: 7 questions (minor wording differences or rule
  adjustments)
- **New Questions for V2**: 1 question (18.10 - remission status)
- **Removed from V2**: ADHD/Neuropsykiatrisk category (5 questions)

## Detailed Analysis

### 1. Intyget avser (ID: 1)

**Status**: ⚠️ **NEEDS V2 VERSION** - Minor differences in alert message and one disable rule

#### V1 Implementation Analysis:

| Aspect                     | V1                                                                                                                                             | V2 Spec                                                                                                         | Status                |
|----------------------------|------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------------|-----------------------|
| **Codes**                  | gr_II, gr_II_III, forlang_gr_II, forlang_gr_II_III, utbyt_utl_kk, tax_leg, int_begar_ts                                                        | Same                                                                                                            | ✅ IDENTICAL           |
| **Display Texts**          | All 7 texts match V2 spec exactly                                                                                                              | Same                                                                                                            | ✅ IDENTICAL           |
| **Disable Rules (SR-008)** | 6 rules implemented (for gr_II, gr_II_III, forlang_gr_II, forlang_gr_II_III, utbyt_utl_kk, int_begar_ts)                                       | Same 6 rules                                                                                                    | ⚠️ ALMOST - see below |
| **Alert Message**          | "Välj \"ansökan om taxiförarlegitimation\" endast om personen saknar taxiförarlegitimation och ansöker om en sådan i samband med detta intyg." | "Endast ett alternativ kan väljas. Undantaget är om intyget avser taxiförarlegitimation, då kan två val göras." | ⚠️ DIFFERENT          |

**Differences Found:**

1. ⚠️ **Alert Message**: V2 has different text explaining the multi-select rule
2. ⚠️ **Disable Rule for int_begar_ts**:
    - V1: Disables gr_II, gr_II_III, forlang_gr_II, forlang_gr_II_III, utbyt_utl_kk
    - V2: Disables gr_II, gr_II_III, forlang_gr_II, forlang_gr_II_III, utbyt_utl_kk, **int_begar_ts
      ** (includes itself)

### 2. Intyget är baserat på (ID: 2.0, 2.2)

**Status**: ✅ **COMMON** - Already exists as `QuestionBaseratPa` and `QuestionBaseratPaDatum`

### 3. Identitet (ID: 3.0)

**Status**: ✅ **COMMON** - Already exists as `QuestionIdentitet`

### 4. Synfunktioner (ID: 4, 4.1)

**Status**: ✅ **COMMON** - Already exists as `QuestionSynfunktioner`

### 5. Synskärpa (ID: 5.0 - 5.6, 24, 25)

**Status**: ✅ **COMMON** - Already exists as `QuestionSynskarpa` with related questions

- Question 24 (tolerans problem) exists as `QuestionKorrigeringAvSynskarpaIngenStyrkaOver`
- Question 25 (styrka över plus 8 dioptrier) exists as `QuestionKorrigeringAvSynskarpaStyrkaOver`

### 6. Anamnesfrågor (ID: 7, 7.2, 7.3, 7.4)

**Status**: ✅ **COMMON** - Already exists

- 7.1: `QuestionSjukdomEllerSynnedsattning`
- 7.2: `QuestionSjukdomEllerSynnedsattningBeskrivning`
- 7.3: `QuestionSjukdomshistorik`
- 7.4: `QuestionSjukdomshistorikBeskrivning`

### 7. Balanssinne (ID: 8.0, 8.2)

**Status**: ✅ **COMMON** - Already exists as `QuestionBalanssinne` and
`QuestionBalanssinneBeskrivning`

### 8. Hörsel (ID: 9.0, 9.2, 9.3)

**Status**: ✅ **IDENTICAL** - Category and all questions are the same

- V1 has: `QuestionHorsel`, `QuestionHorselhjalpmedel`, `QuestionHorselhjalpmedelPosition`
- V2 has: 9.0 (same), 9.2 (same), 9.3 (same)
- Category show rule based on question 1.1 values - **ALREADY EXISTS IN V1**
    - V1 shows for: GR_II_III, TAXI, UTLANDSKT, FORLANG_GR_II_III, ANNAT
    - V2 spec requires: gr_II_III, forlang_gr_II_III, tax_leg (subset of V1 - fully compatible)

### 9. Rörelseorganens funktioner (ID: 10, 10.2, 10.3)

**Status**: ✅ **COMMON** - Already exists as `QuestionRorlighet`, `QuestionRorlighetBeskrivning`,
`QuestionRorlighetHjalpaPassagerare`

### 10. Hjärt- och kärlsjukdomar (ID: 11 - 11.10)

**Status**: ⚠️ **PARTIALLY COMMON** - Most questions identical, one needs text update

#### V1 Implementation Analysis:

| ID    | V1 Text                                                             | V2 Spec Text                                                       | Status                                                         |
|-------|---------------------------------------------------------------------|--------------------------------------------------------------------|----------------------------------------------------------------|
| 11.1  | "Har eller har personen haft någon hjärt- eller kärlsjukdom?"       | Same                                                               | ✅ IDENTICAL                                                    |
| 11.2  | "Ange vilken sjukdom" (TextArea, 250 chars)                         | "Ange vilken sjukdom och tidpunkt för diagnos" (SK-007, 250 chars) | ⚠️ TEXT DIFFERS - needs "och tidpunkt för diagnos"             |
| 11.3  | "Är tillståndet behandlat?" (Boolean)                               | "Är tillståndet behandlat?" (SK-002, Boolean)                      | ✅ IDENTICAL                                                    |
| 11.4  | "Ange när och hur" (TextArea, 250 chars)                            | "Ange när och hur" (SK-007, 250 chars)                             | ✅ IDENTICAL                                                    |
| 11.5  | "Har personen eller har personen haft någon arytmi?" + description  | Same                                                               | ✅ IDENTICAL                                                    |
| 11.6  | "Ange tidpunkt" (TextField, 50 chars)                               | "Ange tidpunkt" (SK-006, 50 chars)                                 | ✅ IDENTICAL                                                    |
| 11.7  | "Har personen eller har personen haft någon synkope?" + description | "Har personen haft en synkope?" + description                      | ⚠️ TEXT DIFFERS - "haft en" vs "eller har personen haft någon" |
| 11.8  | "Ange tidpunkt" (TextField, 50 chars)                               | "Ange tidpunkt" (SK-006, 50 chars)                                 | ✅ IDENTICAL                                                    |
| 11.9  | "Har personen haft en stroke..."                                    | Same                                                               | ✅ IDENTICAL                                                    |
| 11.10 | "Om stroke förekommit..." (ja/nej/vetinte)                          | Same                                                               | ✅ IDENTICAL                                                    |

**Differences Found:**

1. ⚠️ **11.2**: V1 text is shorter - needs to add "och tidpunkt för diagnos"
2. ⚠️ **11.7**: V1 text is longer - V2 simplifies to "Har personen haft en synkope?"

### 11. Diabetes (ID: 12, 12.2)

**Status**: ✅ **COMMON** - `QuestionDiabetes` and `MessageDiabetes`

### 12. Neurologiska sjukdomar (ID: 13, 13.2)

**Status**: ✅ **COMMON** - `QuestionNeurologiskSjukdom` and `QuestionNeurologiskSjukdomBeskrivning`

### 13. Epilepsi (ID: 14 - 14.9)

**Status**: ⚠️ **MOSTLY COMMON** - One text difference found

#### V1 Implementation Analysis:

| ID   | V1 Text                                                                                  | V2 Spec Text                                                                                | Status                                                                     |
|------|------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------|----------------------------------------------------------------------------|
| 14.1 | "Har eller har personen haft epilepsi?"                                                  | Same                                                                                        | ✅ IDENTICAL                                                                |
| 14.2 | "Ange tidpunkt för senaste epileptiska anfall" (TextField, 50 chars)                     | Same (SK-006, 50 chars)                                                                     | ✅ IDENTICAL                                                                |
| 14.3 | "Har eller har personen haft epileptiskt anfall..."                                      | "Har eller har personen haft epileptiskt anfall (utan att diagnosen epilepsi har ställts)?" | ✅ IDENTICAL                                                                |
| 14.4 | "Ange tidpunkt för senaste epileptiska anfall" (TextField, 50 chars)                     | Same (SK-006, 50 chars)                                                                     | ✅ IDENTICAL                                                                |
| 14.5 | "Har eller har personen haft någon krampförebyggande läkemedelsbehandling mot epilepsi?" | Same                                                                                        | ⚠️ **SHOW RULE DIFFERS** - V2 has different SR-003 rule (14.1 and/or 14.3) |
| 14.6 | "Ange vilket läkemedel" (TextArea, 250 chars)                                            | Same (SK-007, 250 chars)                                                                    | ✅ IDENTICAL                                                                |
| 14.7 | "Om läkemedelsbehandling avslutats, ange tidpunkt" (TextField, 50 chars)                 | "Om läkemedelsbehandling har avslutats, ange tidpunkt" (SK-006, 50 chars)                   | ⚠️ TEXT DIFFERS - missing "har"                                            |
| 14.8 | "Har eller har personen haft någon annan medvetandestörning?"                            | Same                                                                                        | ✅ IDENTICAL                                                                |
| 14.9 | "Ange tidpunkt" (TextField, 50 chars)                                                    | Same (SK-006, 50 chars)                                                                     | ✅ IDENTICAL                                                                |

**Differences Found:**

1. ⚠️ **14.5**: V1 has show rule based on both 14.1 and 14.3 - V2 spec says "SR-003 FrågeId: 14.1
   och/eller 14.3" - ALREADY CORRECT!
2. ⚠️ **14.7**: V1 text is "avslutats" - V2 has "har avslutats" (minor text difference)

### 14. Njursjukdomar (ID: 15, 15.2, 15.3)

**Status**: ✅ **IDENTICAL** - All questions match exactly

| ID   | V1 Text                                                       | V2 Spec Text | Status      |
|------|---------------------------------------------------------------|--------------|-------------|
| 15.1 | "Har personen allvarligt nedsatt njurfunktion?" + description | Same         | ✅ IDENTICAL |
| 15.2 | "Har njurtransplantation genomgåtts?"                         | Same         | ✅ IDENTICAL |
| 15.3 | "Ange tidpunkt för transplantationen" (TextField, 50 chars)   | Same         | ✅ IDENTICAL |

### 15. Demens (ID: 16, 16.1, 16.2, 16.3)

**Status**: ⚠️ **ONE TEXT DIFFERENCE IN V1**

| ID   | V1 Text                                                                      | V2 Spec Text                                | Status                                        |
|------|------------------------------------------------------------------------------|---------------------------------------------|-----------------------------------------------|
| 16.1 | "Har personen **diagnos** allvarlig kognitiv störning?"                      | "Har personen allvarlig kognitiv störning?" | ⚠️ TEXT DIFFERS - V1 has extra word "diagnos" |
| 16.2 | "Har personen diagnos demens eller annan kognitiv störning..." + description | Same                                        | ✅ IDENTICAL                                   |
| 16.3 | "Ange vilka tecken, eventuell diagnos och grad..."                           | Same                                        | ✅ IDENTICAL                                   |

**Difference Found:**

- ⚠️ **16.1**: V1 asks "Har personen **diagnos** allvarlig kognitiv störning?" but V2 spec asks "Har
  personen allvarlig kognitiv störning?" (without the word "diagnos")

### 16. Sömn- och vakenhetsstörningar (ID: 17, 17.2, 17.3)

**Status**: ✅ **COMMON** - `QuestionSomn`, `QuestionSomnBeskrivning`, `QuestionSomnBehandling`

### 17. Alkohol, andra psykoaktiva substanser och läkemedel (ID: 18 - 18.10)

**Status**: ⚠️ **ONE TEXT DIFFERENCE + ONE NEW QUESTION**

#### V1 Implementation Analysis:

| ID    | V1 Text                                                                                                          | V2 Spec Text                                                                                                      | Status                                                                             |
|-------|------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------|
| 18.1  | "Har personen eller har personen haft en diagnos missbruk, beroende eller substansbrukssyndrom?"                 | "Har personen eller har personen haft en diagnos avseende alkohol, andra psykoaktiva substanser eller läkemedel?" | ⚠️ TEXT DIFFERS - V2 asks "avseende alkohol..." instead of "missbruk, beroende..." |
| 18.2  | "Ange vilken diagnos, tidpunkt för när diagnosen ställdes och för vilken/vilka substanser" (TextArea, 250 chars) | "Ange diagnos, tidpunkt för när diagnosen ställdes och för vilken/vilka substanser"                               | ⚠️ TEXT DIFFERS - V1 has "vilken", V2 has just "diagnos"                           |
| 18.3  | Same                                                                                                             | Same                                                                                                              | ✅ IDENTICAL                                                                        |
| 18.4  | Same                                                                                                             | Same                                                                                                              | ✅ IDENTICAL                                                                        |
| 18.5  | Same                                                                                                             | Same                                                                                                              | ✅ IDENTICAL                                                                        |
| 18.6  | Same                                                                                                             | Same                                                                                                              | ✅ IDENTICAL                                                                        |
| 18.7  | Same                                                                                                             | Same                                                                                                              | ✅ IDENTICAL                                                                        |
| 18.8  | Same                                                                                                             | Same                                                                                                              | ✅ IDENTICAL                                                                        |
| 18.9  | Same                                                                                                             | Same                                                                                                              | ✅ IDENTICAL                                                                        |
| 18.10 | NOT EXISTS                                                                                                       | "Om diagnos beroende, är beroendet i fullständig långvarig remission?" (RadioMultipleCode: ja/nej/vetinte)        | ❌ NEW QUESTION IN V2                                                               |

**Differences Found:**

1. ⚠️ **18.1**: V1 uses "missbruk, beroende eller substansbrukssyndrom" but V2 uses "avseende
   alkohol, andra psykoaktiva substanser eller läkemedel"
2. ⚠️ **18.2**: V1 has "Ange **vilken** diagnos" but V2 has "Ange diagnos"
3. ❌ **18.10**: NEW question not in V1 - needs to be created for V2

### 18. Psykiska sjukdomar och störningar (ID: 19, 19.2, 19.3)

**Status**: ✅ **COMMON** - `QuestionPsykisk`, `QuestionPsykiskBeskrivning`,
`QuestionPsykiskTidpunkt`

### 19. Intellektuell funktionsnedsättning (ID: 24, 24.2)

**Status**: ✅ **COMMON** - `QuestionPsykiskUtvecklingsstorning` and related questions
**Note**: In V2, this is ID 24 (was part of ADHD category in V1)

### 20. Övrig medicinering (ID: 21, 21.2)

**Status**: ✅ **COMMON** - `QuestionMedicinering`, `QuestionMedicineringBeskrivning`

### 21. Övrig kommentar (ID: 22)

**Status**: ✅ **COMMON** - `QuestionOvrigBeskrivning`

### 22. Bedömning (ID: 23, 23.2, 23.3)

**Status**: ✅ **COMMON** - `QuestionBedomning`, `QuestionBedomningOkand`, `QuestionBedomningRisk`

### 23. Vårdenhetens adress

**Status**: ✅ **COMMON** - `issuingUnitContactInfo()`

---

## Questions NOT in V2 Specification

These questions exist in V1 but are NOT in the V2 specification:

1. **ADHD/Neuropsykiatrisk section** (CategoryAdhdAutismPsykiskUtvecklingsstorning):
    - `QuestionNeuropsykiatrisk` - NOT in V2
    - `QuestionNeuropsykiatriskTrafikrisk` - NOT in V2
    - `QuestionNeuropsykiatriskTidpunkt` - NOT in V2
    - `QuestionNeuropsykiatriskLakemedel` - NOT in V2
    - `QuestionNeuropsykiatriskLakemedelBeskrivning` - NOT in V2
    - `QuestionPsykiskUtvecklingsstorningAllvarlig` - NOT in V2 (V2 has similar but different
      structure)

---

## CORRECTED ANALYSIS: Actual Changes Needed for V2

After examining the actual V1 implementation code, here are the **REAL** differences:

### ✏️ Text Updates Required (7 questions):

1. **Question 1** - `QuestionIntygetAvser` (common folder):
    - Current Alert: "Välj \"ansökan om taxiförarlegitimation\" endast om personen saknar
      taxiförarlegitimation och ansöker om en sådan i samband med detta intyg."
    - V2 Needs Alert: "Endast ett alternativ kan väljas. Undantaget är om intyget avser
      taxiförarlegitimation, då kan två val göras."
    - Current Disable Rule for int_begar_ts: Disables 5 other options
    - V2 Needs: Disable 5 other options + itself (int_begar_ts)

2. **Question 11.2** - `QuestionHjartsjukdomBeskrivning` (v1 folder):
    - Current: "Ange vilken sjukdom"
    - V2 Needs: "Ange vilken sjukdom **och tidpunkt för diagnos**"

2. **Question 11.7** - `QuestionSynkope` (common folder):
    - Current: "Har personen **eller har personen haft någon** synkope?"
    - V2 Needs: "Har personen **haft en** synkope?"

3. **Question 14.7** - `QuestionEpilepsiMedicinTidpunkt` (common folder):
    - Current: "Om läkemedelsbehandling avslutats, ange tidpunkt"
    - V2 Needs: "Om läkemedelsbehandling **har** avslutats, ange tidpunkt"

4. **Question 16.1** - `QuestionKognitivStorning` (common folder):
    - Current: "Har personen **diagnos** allvarlig kognitiv störning?"
    - V2 Needs: "Har personen allvarlig kognitiv störning?" (remove "diagnos")

5. **Question 18.1** - `QuestionMissbruk` (common folder):
    - Current: "Har personen eller har personen haft en diagnos **missbruk, beroende eller
      substansbrukssyndrom**?"
    - V2 Needs: "Har personen eller har personen haft en diagnos **avseende alkohol, andra
      psykoaktiva substanser eller läkemedel**?"

6. **Question 18.2** - `QuestionMissbrukBeskrivning` (common folder):
    - Current: "Ange **vilken** diagnos, tidpunkt för när diagnosen ställdes och för vilken/vilka
      substanser"
    - V2 Needs: "Ange diagnos, tidpunkt för när diagnosen ställdes och för vilken/vilka
      substanser" (remove "vilken")

### ➕ New Question to Create (1 question):

7. **Question 18.10** - NEW question (needs to be created):
    - ID: "18.10"
    - Field ID: "18.10"
    - Type: ElementConfigurationRadioMultipleCode
    - Text: "Om diagnos beroende, är beroendet i fullständig långvarig remission?"
    - Options: ja/nej/vetinte (from CodeSystemKvTs001)
    - Show rule: Show when 18.1 = true
    - Mandatory: Yes
    - Parent mapping: Question 18 (QUESTION_MISSBRUK_ID)

### 🗑️ Category to Remove (1 category):

8. **CategoryAdhdAutismPsykiskUtvecklingsstorning** - DO NOT include in V2:
    - All 6 questions in this category should be excluded from V2

### ✅ Already Correct (everything else):

**All other questions (~85+) are IDENTICAL between V1 and V2**, including:

- All IDs match exactly
- All texts match exactly
- All validation rules match
- All show/hide rules match
- All character limits match
- All mandatory requirements match

---

## Action Plan

### Step 1: Move Common Questions

Move these questions from `common` folder (they are truly common between v1 and v2):

- All questions in categories: Intyget avser, Identitet, Intyget baserat på, Synfunktion,
  Synskärpa (mostly), Anamnes, Balanssinne, Rörelseorganens funktioner, Diabetes message, Sömn,
  Psykiska sjukdomar, Övrig medicinering, Övrig kommentar, Bedömning

### Step 2: Create V2-Specific Questions

Create new questions in `elements/v2` folder:

- Hjärt- och kärlsjukdomar: 10 new questions (11.2, 11.3, 11.4, 11.6, 11.8 and adjusted versions of
  existing)
- Epilepsi: 9 new questions (14.2, 14.4, 14.6, 14.7, 14.9 and adjusted versions)
- Njursjukdomar: 3 adjusted questions
- Demens: 1 new question (16.1)
- Alkohol: 1 new question (18.10)
- Neurologisk: 2 adjusted questions

### Step 3: Update V2 Factory

Update `CertificateModelFactoryTS8071V2.java` to include all element specifications

### Step 4: Remove V1-Specific Questions

The ADHD/Neuropsykiatrisk category is NOT in V2, so keep those in V1 only

### Step 5: Update Tests

Create test files for all new V2 questions in the test directory

---

## Key Differences Between V1 and V2

1. **ADHD Category Removed**: V2 does not have the CategoryAdhdAutismPsykiskUtvecklingsstorning
2. **More Structured Date Fields**: V2 uses more date fields (SK-006) instead of free-text
   descriptions
3. **New Validation Rules**: V2 has SR-008 disable rules for "Intyget avser"
4. **Category Show Rules**: Hörsel category has show rule based on question 1.1 values
5. **New Sub-Questions**: Several new sub-questions for timing/dates (11.6, 11.8, 14.2, 14.4, etc.)
6. **Remission Question**: New question 18.10 for substance abuse remission status
7. **Allvarlig Kognitiv Störning**: New specific question 16.1 about severe cognitive impairment


