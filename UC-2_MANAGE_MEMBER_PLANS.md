# Use Case Specification: UC-2 - Manage Member Plans

## Description
The Admin manages member plan records, including creating, modifying, searching, and removing membership plans.

## Actors
Gym Admin

## Preconditions
User is logged in as Admin.

## Postconditions
- **Add:** A new member plan record is created in the database.
- **Modify:** Existing plan details are updated.
- **Remove:** The plan record is deleted or archived.

## Main Flow (Adding a Plan)
1. Admin enters option 2 (Member Plans).
2. Admin enters choice 1 (Add).
3. System displays the Add Member Plan form.
4. Admin clicks on the "GENERATE NUMBER" button to create a unique Plan ID.
5. Admin enters Plan Details:
   - a. Plan Name (e.g., Basic, Premium, VIP)
   - b. Plan Duration (Monthly / Quarterly / Annual)
   - c. Plan Price
   - d. Plan Description
6. Admin selects Plan Features:
   - a. Access to all machines (Yes / No)
   - b. Personal trainer included (Yes / No)
   - c. Group classes included (Yes / No)
   - d. Locker access (Yes / No)
7. Admin clicks "SAVE".
8. System validates the data, saves the record, and displays a success message.

## Alternate Flow

### @Step 2: Admin enters choice 2 (Search)
- 2.1 System displays 'View Plan Information' page.
- 2.2 Admin enters a Plan ID or Plan Name.
- 2.3 Admin clicks on 'Search' button.
- 2.4 System displays Plan details in the text area.

### @Step 2: Admin enters choice 3 (Modify)
- 2.1 System displays the Modify Plan Information screen.
- 2.2 Admin enters the Plan ID in the search field.
- 2.3 Admin clicks "SEARCH PLAN".
- 2.4 Admin modifies the Plan information.
- 2.5 Admin clicks on "SAVE" button.
- 2.6 System updates Plan information and displays a successful update message.

### @Step 2: Admin enters choice 4 (View All)
- 2.1 System displays View All Plans screen with all Plan names and Plan IDs.

### @Step 2: Admin enters choice 5 (Remove)
- 2.1 System displays Remove Plan screen.
- 2.2 Admin enters Plan ID.
- 2.3 Admin clicks on "REMOVE" button.
- 2.4 System displays successfully removed Plan notification.

### @Step 8: Validation failure due to incomplete information, or wrong format:
- 8.1 System displays an error message indicating an invalid field and returns the admin to the screen.

