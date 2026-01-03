# UC-18: Analyze Member Analytics

## Description
The Gym Admin analyzes member data to gain insights into member demographics, growth trends, body statistics, fitness goals distribution, and membership patterns.

## Actors
- Gym Admin

## Preconditions
1. User is logged in as Gym Admin.
2. System has member data available in MemberFile.

## Postconditions
An analytics report providing member insights including demographics summary, growth trends, body statistics analysis, fitness goal distribution, and membership patterns is generated and displayed.

## Main Flow
1. Admin enters option 6 (Generate Report) from the Main Menu.
2. System displays the 'Generate Report' screen.
3. Admin navigates to the 'Member Analytics' section (or selects a specific analytics option).
4. System displays the Member Analytics menu with the following options:
   - a. Option 1: View member demographics
   - b. Option 2: Analyze growth trends
   - c. Option 3: View body statistics analysis
   - d. Option 4: View fitness goal distribution
5. Admin enters a choice (1, 2, 3, or 4) in the analytics option field.
6. Admin clicks on the 'Generate' or 'Analyze' button.
7. System validates the entered option.
8. System retrieves member data from MemberFile.
9. System performs the selected analytics calculation:
   - a. For Demographics: Calculates age group distribution, gender distribution, and total member count
   - b. For Growth Trends: Analyzes new member registrations by month/year, calculates growth rate
   - c. For Body Statistics: Calculates average BMI, average weight, average height, BMI category distribution
   - d. For Fitness Goals: Counts and displays distribution of fitness goals among members
10. System generates the analytics report with formatted statistics, trends, and distributions.
11. System displays the generated analytics report in the report text area.
12. Admin can view the analytics report in the scrollable text area.

## Alternative Flow
**@Step 12: Admin clicks "RESET" button:**
- 12.1 System clears the analytics report text area.
- 12.2 System returns to empty report display state.
- 12.3 Admin can generate a new analytics report.

**@Step 12: Admin clicks "GO BACK" button:**
- 12.1 System closes the Member Analytics window or returns to Generate Report menu.
- 12.2 Admin can navigate to other report options.

## Exception Flow
**@Step 7: Validation failure due to invalid option:**
- 7.1 System detects that the entered option is not 1, 2, 3, or 4.
- 7.2 System displays an error message dialog: "You enter wrong choice, enter again."
- 7.3 System returns admin to the Member Analytics screen.
- 7.4 Admin can re-enter a valid option.

**@Step 8: System finds insufficient data for analysis:**
- 8.1 System detects that there are very few or no member records in the system.
- 8.2 System generates a report indicating insufficient data for meaningful analysis.
- 8.3 System displays a message: "Insufficient member data available for analytics. Please ensure member records exist."
- 8.4 Flow continues to Step 11.

