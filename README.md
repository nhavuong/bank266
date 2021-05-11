# Welcome to the `Bank266` Web Application

## Start the `bank266` web application

```bash
./mvnw spring-boot:run
```

Then go to [http://localhost:8080/](http://localhost:8080/) in your browser.

## Registration

* click "Register here" on the home page

## Login

* There is already one account in the database, you can use the following
  credential to log in:
  
```
username: admin
password: admin
```

## Check Balance

* Once you login, your current balance display automatically
  on your account page.
  
## Deposit or withdraw

* In your account page, enter the amount you want to deposit or
  withdraw. The format has to follow the requirement on the canvas,
  otherwise you will get an "Invalid_input" warning message.
* Then click either "deposit" or "withdraw" button. Your new balance
  will be shown on the page.
* You can optionally add a comment for this transaction.