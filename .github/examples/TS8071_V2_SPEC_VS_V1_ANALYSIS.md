# TS8071 V2.0 Specification vs V1 Implementation Analysis

## Overview

This document analyzes the TS8071 v2.0 specification against **V1 implementation only** (ignoring V2
implementations), comparing all text fields, descriptions, and help texts from the specification.

## Analysis Date

October 23, 2025

## Methodology

- Compared specification ID by ID against V1 implementation
- Checked all text fields, descriptions, and help texts (from bottom of spec)
- Ignored existing V2 implementations to identify true differences
- Focused on text accuracy, character limits, and descriptions

---

## Summary

- **Total Questions in V2 Spec**: ~95 questions (including sub-questions)
- **Questions Identical to V1**: ~75 questions
- **Questions with Text Differences**: 10 questions
- **New Question in V2**: 1 question (18.10 - remission status)
- **Removed from V2**: ADHD/Neuropsykiatrisk category (6 questions)

---

## Detailed Analysis by Question ID

### Question 9.0 - Hörsel (Hearing)

**Status**: ⚠️ **TEXT DIFFERENCE**

| Aspect            | V2 Spec                                                                                                      | V1 Implementation                                                                                           | Difference                                 |
|-------------------|--------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------|--------------------------------------------|
| **Question Text** | "Har personen svårt att uppfatta vanlig samtalsstämma på fyra meters avstånd **(hörapparat får användas)?**" | "Har personen svårt att uppfatta vanlig samtalsstämma på fyra meters avstånd**? Hörapparat får användas.**" | Spec uses parentheses vs separate sentence |
| **Element ID**    | 9.0                                                                                                          | 9                                                                                                           | ✅ IDENTICAL                                |
| **Field ID**      | 9.1                                                                                                          | 9.1                                                                                                         | ✅ IDENTICAL                                |

**Spec Help Text** (ID 9.0): None specified in help text section

**Recommendation**: V2 spec uses parentheses format. V1 uses separate sentence with question mark.

---

### Question 9.2 - Hörapparatsanvändning (Hearing Aid Use)

**Status**: ⚠️ **TEXT DIFFERENCE**

| Aspect            | V2 Spec                                                                                                           | V1 Implementation              | Difference                          |
|-------------------|-------------------------------------------------------------------------------------------------------------------|--------------------------------|-------------------------------------|
| **Question Text** | "**Behöver personen använda** hörapparat **för att kunna uppfatta vanlig samtalsstämma på fyra meters avstånd**?" | "Behöver hörapparat användas?" | V1 is much shorter, missing context |
| **Element ID**    | 9.2                                                                                                               | 9.2                            | ✅ IDENTICAL                         |
| **Field ID**      | 9.2                                                                                                               | 9.2                            | ✅ IDENTICAL                         |

**Spec Help Text** (ID 9.2): None specified

**Recommendation**: V2 spec requires full context including "personen" and the distance
specification.

---

### Question 11.7 - Synkope (Syncope)

**Status**: ⚠️ **TEXT DIFFERENCE**

| Aspect            | V2 Spec                             | V1 Implementation                                                                                                                                                                                                                                 | Difference         |
|-------------------|-------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------|
| **Question Text** | "**Har personen haft en** synkope?" | "Har personen **eller har personen haft någon** synkope?"                                                                                                                                                                                         | V1 is more verbose |
| **Element ID**    | 11.7                                | 11.7                                                                                                                                                                                                                                              | ✅ IDENTICAL        |
| **Field ID**      | 11.7                                | 11.7                                                                                                                                                                                                                                              | ✅ IDENTICAL        |
| **Description**   | None in spec description column     | "Med synkope avses här sådan som är utlöst av arytmi men även situationsutlöst synkope (till följd av exempelvis hosta, nysning, skratt eller ansträngning) och reflexsynkope (vasovagal synkope) som exempelvis utlösts av rädsla eller smärta." | V1 has description |

**Spec Help Text** (ID 11.7):
"Med synkope avses här sådan som är utlöst av arytmi men även situationsutlöst synkope (till följd
av exempelvis hosta, nysning, skratt eller ansträngning) och reflexsynkope (vasovagal synkope) som
exempelvis utlösts av rädsla eller smärta."

**Recommendation**: V2 spec simplifies the question text. Description should come from help text
section at bottom of spec (which V1 correctly implements).

---

### Question 11.10 - Stroke påverkan på syncentrum

**Status**: ⚠️ **TEXT DIFFERENCE**

| Aspect            | V2 Spec                                                                                                       | V1 Implementation                                                                                         | Difference                      |
|-------------------|---------------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------|---------------------------------|
| **Question Text** | "Om stroke förekommit, har **det** inträffat **eller** påverkat syncentrum (occipitalloben eller synnerven)?" | "Om stroke förekommit, har **den** inträffat **i/påverkat** syncentrum (occipitalloben eller synnerven)?" | "det" vs "den", "eller" vs "i/" |
| **Element ID**    | 11.10                                                                                                         | 11.10                                                                                                     | ✅ IDENTICAL                     |
| **Field ID**      | 11.10                                                                                                         | 11.10                                                                                                     | ✅ IDENTICAL                     |

**Recommendation**: V2 spec uses "det" and "eller" instead of "den" and "i/".

---

### Question 16.1 - Kognitiv störning

**Status**: ⚠️ **TEXT DIFFERENCE**

| Aspect            | V2 Spec                                     | V1 Implementation                                       | Difference                 |
|-------------------|---------------------------------------------|---------------------------------------------------------|----------------------------|
| **Question Text** | "Har personen allvarlig kognitiv störning?" | "Har personen **diagnos** allvarlig kognitiv störning?" | V1 includes word "diagnos" |
| **Element ID**    | 16                                          | 16                                                      | ✅ IDENTICAL                |
| **Field ID**      | 16.1                                        | 16.1                                                    | ✅ IDENTICAL                |

**Recommendation**: V2 spec removes the word "diagnos".

---

### Question 16.2 - Demens eller kognitiv störning

**Status**: ⚠️ **DESCRIPTION DIFFERENCE**

| Aspect            | V2 Spec                                                                                                                    | V1 Implementation                                                                                                                                                                                      | Difference         |
|-------------------|----------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------|
| **Question Text** | "Har personen diagnos demens eller annan kognitiv störning eller finns tecken på demens eller andra kognitiva störningar?" | Same                                                                                                                                                                                                   | ✅ IDENTICAL        |
| **Description**   | Not shown in main spec table                                                                                               | "Med demens avses diagnos ställd utifrån vedertagen praxis eller utifrån de kriterier som anges i DSM-IV, DSM-V eller ICD-10. Med kognitiv störning avses kognitiv störning/svikt som inte är demens." | V1 has description |

**Spec Help Text** (ID 16.2):
"Med demens avses diagnos ställd utifrån vedertagen praxis eller utifrån de kriterier som anges i
DSM-IV, DSM-V eller ICD-10. Med kognitiv störning avses kognitiv störning/svikt som inte är demens.
**Med grader avses lindrig, måttlig/medelsvår eller grav/allvarlig.**"

**Recommendation**: V2 spec adds additional sentence about grades: "Med grader avses lindrig,
måttlig/medelsvår eller grav/allvarlig."

---

### Question 18.1 - Missbruk/beroende diagnos

**Status**: ⚠️ **TEXT DIFFERENCE**

| Aspect            | V2 Spec                                                                                                               | V1 Implementation                                                                                    | Difference            |
|-------------------|-----------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------|-----------------------|
| **Question Text** | "Har personen eller har personen haft en diagnos **avseende alkohol, andra psykoaktiva substanser eller läkemedel**?" | "Har personen eller har personen haft en diagnos **missbruk, beroende eller substansbrukssyndrom**?" | Different terminology |
| **Element ID**    | 18                                                                                                                    | 18                                                                                                   | ✅ IDENTICAL           |
| **Field ID**      | 18.1                                                                                                                  | 18.1                                                                                                 | ✅ IDENTICAL           |

**Recommendation**: V2 spec changes from "missbruk, beroende eller substansbrukssyndrom" to "
avseende alkohol, andra psykoaktiva substanser eller läkemedel".

---

### Question 18.2 - Missbruk beskrivning

**Status**: ⚠️ **TEXT AND LIMIT DIFFERENCE**

| Aspect              | V2 Spec                                                                             | V1 Implementation                                                                              | Difference           |
|---------------------|-------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------|----------------------|
| **Question Text**   | "Ange diagnos, tidpunkt för när diagnosen ställdes och för vilken/vilka substanser" | "Ange **vilken** diagnos, tidpunkt för när diagnosen ställdes och för vilken/vilka substanser" | V1 includes "vilken" |
| **Character Limit** | **400 tecken**                                                                      | 250 tecken                                                                                     | Increased limit      |
| **Element ID**      | 18.2                                                                                | 18.2                                                                                           | ✅ IDENTICAL          |
| **Field ID**        | 18.2                                                                                | 18.2                                                                                           | ✅ IDENTICAL          |

**Recommendation**: V2 spec removes "vilken" and increases character limit from 250 to 400.

---

### Question 18.10 - Remission status (NEW IN V2)

**Status**: ✅ **NEW QUESTION**

| Aspect            | V2 Spec                                                                | V1 Implementation | Status      |
|-------------------|------------------------------------------------------------------------|-------------------|-------------|
| **Question Text** | "Om diagnos beroende, är beroendet i fullständig långvarig remission?" | NOT EXISTS        | ❌ NOT IN V1 |
| **Element ID**    | 18.10                                                                  | -                 | NEW         |
| **Field ID**      | 18.10                                                                  | -                 | NEW         |
| **Type**          | SK-002 (Radio: Ja/Nej/Vet inte)                                        | -                 | NEW         |
| **Show Rule**     | SR-003: Show when 18.1 = true                                          | -                 | NEW         |
| **Code System**   | TS-001 (ja/nej/vetinte)                                                | -                 | NEW         |

**Spec Help Text** (ID 18.10):
"Här avses exempelvis beroende eller skadligt mönster av bruk enligt ICD-11, skadligt bruk enligt
ICD-10, missbruk enligt DSM-IV eller substansbrukssyndrom enligt DSM-5"

**Recommendation**: This is a completely new question in V2 that doesn't exist in V1.

---

### Question 19.1 - Psykisk sjukdom

**Status**: ⚠️ **TEXT DIFFERENCE**

| Aspect            | V2 Spec                                                                | V1 Implementation                                                                                                                                        | Difference           |
|-------------------|------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------|
| **Question Text** | "Har personen eller har personen haft psykisk sjukdom eller störning?" | "Har personen eller har personen haft psykisk sjukdom eller störning**, till exempel schizofreni, annan psykos eller bipolär (manodepressiv) sjukdom**?" | V1 includes examples |
| **Element ID**    | 19                                                                     | 19                                                                                                                                                       | ✅ IDENTICAL          |
| **Field ID**      | 19.1                                                                   | 19.1                                                                                                                                                     | ✅ IDENTICAL          |

**Spec Help Text** (ID 19.1):
"Här avses sjukdomar och störningar som kan påverka beteendet, så att det kan utgöra en
trafiksäkerhetsrisk. Med sjukdomar avses exempelvis schizofreni, annan psykos eller affektiva
syndrom såsom bipolär sjukdom. Med störningar avses exempelvis olika personlighetsstörningar såsom
paranoid, antisocial, narcissistisk eller emotionellt instabil personlighetsstörning och schizotyp
personlighetsstörning. I normalfallet medför paniksyndrom, utmattningssyndrom, ångest (PTSD),
generaliserat ångestsyndrom (GAD), årstidsbundna depressioner inte en trafiksäkerhetsrisk och
behöver i sådant fall inte anges."

**Recommendation**: V2 spec removes examples from question text and moves them to help text. V1
includes examples directly in question.

---

### Question 24.1 - Intellektuell funktionsnedsättning (MOVED FROM V1 ID 20)

**Status**: ⚠️ **ID CHANGED, TEXT IDENTICAL**

| Aspect            | V2 Spec                                                  | V1 Implementation                                     | Difference            |
|-------------------|----------------------------------------------------------|-------------------------------------------------------|-----------------------|
| **Question Text** | "Har personen någon intellektuell funktionsnedsättning?" | "Har personen någon **psykisk utvecklingsstörning**?" | Different terminology |
| **Element ID**    | **24**                                                   | **20** (under ADHD category)                          | ID changed            |
| **Field ID**      | 24.1                                                     | 20.6                                                  | Changed               |

**Note**: In V1, this was part of the ADHD/Neuropsykiatrisk category (ID 20). In V2, it's a
standalone category (ID 24) with new terminology "intellektuell funktionsnedsättning" instead of "
psykisk utvecklingsstörning".

---

## Questions REMOVED in V2 (V1 Only)

### CategoryAdhdAutismPsykiskUtvecklingsstorning (V1 Only)

**Status**: ❌ **REMOVED IN V2**

The following V1 questions do NOT exist in V2 spec:

| V1 ID | V1 Question Text                                                                                                                   | Status in V2                          |
|-------|------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------|
| 20.1  | "Har personen någon neuropsykiatrisk funktionsnedsättning till exempel ADHD, ADD, DCD, Aspergers syndrom eller Tourettes syndrom?" | ❌ REMOVED                             |
| 20.2  | Traffic risk question for neuropsychiatric conditions                                                                              | ❌ REMOVED                             |
| 20.3  | Timing question for neuropsychiatric conditions                                                                                    | ❌ REMOVED                             |
| 20.4  | Medication question for neuropsychiatric conditions                                                                                | ❌ REMOVED                             |
| 20.5  | Medication description for neuropsychiatric conditions                                                                             | ❌ REMOVED                             |
| 20.6  | "Har personen någon psykisk utvecklingsstörning?"                                                                                  | ✅ MOVED to ID 24 with new terminology |

**Note**: Only question 20.6 was preserved and moved to category 24 (Intellektuell
funktionsnedsättning) with updated terminology.

---

## Questions with IDENTICAL Text (Common to Both Versions)

The following questions are **identical** between V2 spec and V1 implementation:

### Category 1 - Intyget avser (ID 1.1)

- ✅ Codes identical (7 codes)
- ✅ Alert message different but both correct for respective versions
- ✅ Disable rules match spec

### Category 2 - Baserat på (ID 2.0, 2.2)

- ✅ Text identical
- ✅ Codes identical

### Category 3 - Identitet (ID 3.0)

- ✅ Text identical
- ✅ Codes identical

### Category 4 - Synfunktioner (ID 4)

- ✅ Text identical

### Category 5 - Synskärpa (ID 5.0-5.6, 24, 25)

- ✅ Matrix configuration identical
- ✅ Validation rules identical

### Category 7 - Anamnesfrågor (ID 7.1-7.4)

- ✅ All texts identical

### Category 8 - Balanssinne (ID 8.0, 8.2)

- ✅ Texts identical

### Category 10 - Rörelseorganens funktioner (ID 10, 10.2, 10.3)

- ✅ All texts identical

### Category 11 - Hjärt- och kärlsjukdomar (ID 11.1-11.9)

- ✅ Most texts identical (except 11.7 and 11.10)

### Category 12 - Diabetes (ID 12, 12.2)

- ✅ Text identical
- ✅ Alert message identical

### Category 13 - Neurologiska sjukdomar (ID 13, 13.2)

- ✅ Texts identical

### Category 14 - Epilepsi (ID 14.1-14.9)

- ✅ All texts identical

### Category 15 - Njursjukdomar (ID 15.1-15.3)

- ✅ All texts identical

### Category 17 - Sömn (ID 17, 17.2, 17.3)

- ✅ All texts identical

### Category 18 - Alkohol/substanser (ID 18.3-18.9)

- ✅ Most texts identical (except 18.1, 18.2, and new 18.10)

### Category 19 - Psykiska sjukdomar (ID 19.2, 19.3)

- ✅ Sub-questions identical (except 19.1)

### Category 21 - Övrig medicinering (ID 21, 21.2)

- ✅ Texts identical

### Category 22 - Övrig kommentar (ID 22)

- ✅ Text identical
- ✅ Character limit 400 identical

### Category 23 - Bedömning (ID 23, 23.2, 23.3)

- ✅ All texts identical

---

## Help Texts Comparison

The V2 specification includes a "Hjälptexter" (Help Texts) section at the bottom. Analysis shows:

### Help Texts Correctly Implemented in V1:

| ID    | Help Text Topic           | V1 Implementation               | Match                       |
|-------|---------------------------|---------------------------------|-----------------------------|
| 11.7  | Synkope definition        | Has description                 | ✅ MATCHES                   |
| 7.1   | Ögonsjukdom examples      | No description in V1            | ⚠️ MISSING                  |
| 7.3   | Sjukdomshistorik examples | No description in V1            | ⚠️ MISSING                  |
| 8.1   | Balansrubbningar          | No description in V1            | ⚠️ MISSING                  |
| 10.1  | Rörelseförmåga            | No description in V1            | ⚠️ MISSING                  |
| 11.5  | Arytmi definition         | No description in V1            | ⚠️ MISSING                  |
| 13.1  | Neurologisk sjukdom       | No description in V1            | ⚠️ MISSING                  |
| 15.1  | Njurfunktion              | No description in V1            | ⚠️ MISSING                  |
| 16.2  | Demens/kognitiv störning  | Has partial description         | ⚠️ PARTIAL (missing grades) |
| 17.1  | Sömn-/vakenhetsstörning   | No description in V1            | ⚠️ MISSING                  |
| 17.3  | Behandling definition     | No description in V1            | ⚠️ MISSING                  |
| 18.3  | Journaluppgifter          | No description in V1            | ⚠️ MISSING                  |
| 18.6  | Vårdats/sökt hjälp        | No description in V1            | ⚠️ MISSING                  |
| 18.8  | Läkemedel                 | No description in V1            | ⚠️ MISSING                  |
| 18.10 | Remission definition      | NEW IN V2                       | ❌ NOT IN V1                 |
| 19.1  | Psykisk sjukdom           | Examples in question text in V1 | ⚠️ IN QUESTION TEXT         |
| 21.1  | Medicinering              | No description in V1            | ⚠️ MISSING                  |

**Note**: V1 implementation does NOT systematically include help texts as descriptions. Some are
missing, some are partially included.

---

## Validation and Configuration Differences

### Character Limits

| Question ID          | V2 Spec        | V1 Implementation | Status       |
|----------------------|----------------|-------------------|--------------|
| 18.2                 | 400 characters | 250 characters    | ⚠️ DIFFERENT |
| All SK-007           | 250 characters | 250 characters    | ✅ IDENTICAL  |
| All SK-006           | 50 characters  | 50 characters     | ✅ IDENTICAL  |
| 22 (Övrig kommentar) | 400 characters | 400 characters    | ✅ IDENTICAL  |

---

## Show Rules Differences

### Question 9 - Hörsel Category

**V2 Spec Show Rule**:

```
SR-003: Show when 1.1 = <gr_II_III, forlang_gr_II_III, tax_leg>
```

**V1 Implementation Show Rule**:

```
Show when 1.1 = <gr_II_III, forlang_gr_II_III, tax_leg, utbyt_utl_kk, int_begar_ts>
```

**Status**: ⚠️ V1 has MORE values than V2 spec requires

---

## Summary of Required Changes from V1 to Match V2 Spec

### Text Changes Needed (10 questions):

1. **Question 9.0** - Change "? Hörapparat får användas." to "(hörapparat får användas)?"
2. **Question 9.2** - Expand from "Behöver hörapparat användas?" to full context
3. **Question 11.7** - Simplify from "eller har personen haft någon" to "haft en"
4. **Question 11.10** - Change "den...i/påverkat" to "det...eller påverkat"
5. **Question 16.1** - Remove word "diagnos"
6. **Question 16.2** - Add description about "grader"
7. **Question 18.1** - Change terminology to "avseende alkohol..."
8. **Question 18.2** - Remove "vilken" and increase to 400 chars
9. **Question 18.10** - ADD NEW QUESTION (remission status)
10. **Question 19.1** - Remove examples from question text (move to help)
11. **Question 24** - Move from ID 20.6, change terminology to "intellektuell funktionsnedsättning"

### Removed from V2 (5 questions):

- Questions 20.1-20.5 (ADHD/Neuropsykiatrisk category)

### Show Rule Changes:

- Question 9 category: Reduce show rule values to match spec

---

## Conclusion

The V1 implementation is **mostly aligned** with the V2 specification, but there are:

- **10 questions with text differences** that need updating
- **1 new question (18.10)** that needs to be added
- **5 questions (20.1-20.5)** that need to be removed
- **1 question (20.6→24)** that needs to be moved and renamed
- **Multiple help texts missing** as descriptions
- **1 character limit change** (18.2: 250→400)
- **1 show rule** that needs adjustment

Most differences are minor text variations, but they should be addressed for full V2 compliance.

