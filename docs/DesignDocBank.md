#Design Doc: Bank

##Data
 + class BankAccount{CustomerAgent customer, int accountNum, double funds, double credit, double moneyToWithdraw, AccountState state}
 + List<BankAccount> accounts
 + List<BankTellerAgent> tellers
 + AccountState(Opened,WantAccountNumber,Deposit,Withdraw,Loan,NoMoney)
 + int uniqueAccountNumber = 0
 + double moneyInBank = 10000000
 + TellerAssignerAgent tellerAssigner
	
##Scheduler
 + if ∃ a in accounts ∋ a.state = WantAccountNumber
  then CreateAccount(a);
 + if ∃ a in accounts ∋ a.state = Deposit
  then DepositMoney(a);
 + if ∃ a in accounts ∋ a.state = Withdraw
	then GiveCustomerMoney(a);
 + if ∃ a in accounts ∋ a.state = Loan
	then GiveLoan(a);

##Messages
```
msgOpenAccount(CustomerAgent customer){
	accounts.add(customer,uniqueAccountNumber,0,0,AccountState.WantAccountNumber);
	uniqueAccountNumber++;
	stateChanged();
   }
```
```
msgDeposit(int accountNumber, double money){
	for(BankAccount tempAccount : accounts){
		if(tempAccount.accountNum == accountNumber){
			tempAccount.funds += money;
			tempAccount.state = AccountState.Deposit;
		}
	}
	stateChanged();
   }
```
```
msgWithdraw(int accountNumber, double money){
	for(BankAccount tempAccount : accounts){
		if(tempAccount.accountNum == accountNumber){
			tempAccount.state = AccountState.Withdraw;
		}
	}
	stateChanged();
   }
```
```
msgIWantLoan(int accountNumber, double moneyRequest){
	for(BankAccount tempAccount : accounts){
		if(tempAccount.accountNum == accountNumber){
			tempAccount.credit += moneyRequest;
			tempAccount.state = AccountState.Loan;
		}
	}
	stateChanged();
}
```
##Actions
```
CreateAccount(BankAccount account){
  account.customer.msgHereIsAccountNumber(account.accountNum);
  account.state = AccountState.Opened;
  stateChanged();
}
```
```
DepositMoney(BankAccount account){
  account.state = AccountState.Opened;
  stateChanged();
}
```
```
GiveCustomerMoney(BankAccount account){
  account.funds -= account.moneyToWithdraw;
  account.customer.msgHereIsMoney(account.moneyToWithdraw);            
  account.moneyToWithdraw = 0;
  account.state = AccountState.Opened;
  stateChanged();  
}
```
```
GiveLoan(BankAccount account){
  account.customer.msgHereIsLoan(account.credit);
  moneyInBank -= account.credit;
  account.state = AccountState.Opened;
  stateChanged();
}
```