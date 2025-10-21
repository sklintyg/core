# AI Analysis Instructions

This AI tool is designed to compare a specification against the V1 model in detail. Follow the
guidelines below to ensure a thorough and accurate analysis.

## 1. Match by ID

- **Do not** rely on names, file names, or other labels as identifiers.
- Compare elements **based on their `ID`** in the specification versus the `ID` in the V1 model.

## 2. Identify Non-Common Elements

- Highlight any IDs that exist in one source but not in the other.
- Include these non-common elements in the analysis for further review.

## 3. Compare Elements in Detail

For each matched ID, verify the following:

- Are all **text fields** (e.g., name, description, labels) identical?
- Are the **rules themselves** consistent between the specification and V1 model?
- Are the config the same?
- If code config is present, are the codes identical?
- Are the validations the same?

## 4. Ignore Non-Relevant Differences

- Differences in **file names or similar** that do not affect rules or content should **not** be
  treated as version differences.

## 5. Output

- Clearly list IDs that are **missing in either source**.
- For matching IDs, report **any differences** in rules or text content.
- An example of the file is shown in `TS8071_V2_ANALYSIS.md`
