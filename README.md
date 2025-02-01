1.	Role Management For Operations
Before getting into detail of operations, it will be beneficial to mention the role-based system. There are two roles which are authorized two different operation group;

1.1.	Admin Role: 
This role belongs to the bank personnel who is responsible and authorized for all operations including customer-based operations.

1.2.	Customer Role:
This role covers the limited operation which is allowed for customers to be used by their own. 


2.	Customer Loan Management System:
First, users encounter the login screen as below;

 
After the successful start of application, localhost screen redirect users to this url: http://localhost:8080/login

After typing the related user information mentioned previous topic as “admin” and “customer” roles, the screen is redirected to Swagger API operation screen which is the centre of the operation management. 

In case admin user login occurs, all of the operations is open for the use of admin. There is no restriction for this user (as requested). However,  if customer user login is the issue, most of the operations are strictly restricted.
  OPERATION	                                     ADMIN USER ALLOWED       	CUSTOMER USER ALLOWED
Loan Application
url path: /create-loan	                                +	                            -

Loan Detail Inquiry By Customer Info
url path: /inquiry-loan-by-customer	                    +	                            -

Installment Detail Inquiry By Loan Info
url path: /inquiry-installment-by-loan	                +	                            -

Payment Operation For Loan
url path: /operate-payment-process	                    +	                            +

Note: Customer user is only allowed for the payment process because of the abstraction principle, data privacy and global rules covering the protection of personal data. From this point of view, it will be ideal to allow ordinary customer users for only payment operations.

In case customer role-based users attempt to operate forbidden functions, it is currently validated by Security Configurations of project and accordingly, “403 Forbidden Error” will appear as below example;
 
To sum up, user roles are validated and authenticated successfully.

As for the operation list:

Loan Application Operation: creating new loan can be achieved by using this function;
url path:/admin /create-loan
 
Loan Detail Inquiry By Customer Info Operation: With the help of customer id, name and surname information, this operation gives the opportunity to inquiry existing active loans which belongs to the related customer.
url path:/admin/inquiry-loan-by-customer
 
Installment Detail Inquiry By Loan Info Operation: as well as being similar with the above operation, this function provides users with acessing the installment details as list for the active loan related fields given by the user in request; loan id, loanAmount, number of installment.
url path: /admin/inquiry-installment-by-loan
 
Payment Operation For Loan Operation: This function is used for the payment of specific loan and only with two fields of information (loan id and paidAmount) payment process is executed.
url path: /admin /operate-payment-process
