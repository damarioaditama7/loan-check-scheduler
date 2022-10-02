# loan-check-scheduler
Scheduler for checking loan due date

  
# Information
- The scheduler always run the Loan Check every 1 minutes.

# How to add data (BankCustomer first followed by LoanRequestData)
  1. Import postman collection, located in postman-collection folder.
  2. Run the springboot application.
  3. Fill the data.
  4. Hit the url.
 
# How to check the scheduler work
  1. Scheduler change the data when loan_datetime value is 0 (zero).
  2. Use 'Force Expiry Loan Due Date' collection to set loan_datetime to zero.
  3. Wait the scheduler work.
  4. loan_datetime value will be extend to 30 days and the new total_amount (that customer need to pay) added.
