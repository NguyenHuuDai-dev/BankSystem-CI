package bank_system;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for checking and savings accounts.
 */
public abstract class Account {
  private static final Logger logger = LoggerFactory.getLogger(Account.class);

  public static final String CHECKING_TYPE = "CHECKING";
  public static final String SAVINGS_TYPE = "SAVINGS";

  private long accountNumber;
  private double balance;
  private List<Transaction> transactions;

  /**
   * Creates an account with its account number and opening balance.
   *
   * @param accountNumber account identifier
   * @param balance opening balance
   */
  public Account(long accountNumber, double balance) {
    this.accountNumber = accountNumber;
    this.balance = balance;
    this.transactions = new ArrayList<>();
  }

  /**
   * Returns the account number.
   *
   * @return account number
   */
  public long getAccountNumber() {
    return accountNumber;
  }

  /**
   * Updates the account number.
   *
   * @param accountNumber new account number
   */
  public void setAccountNumber(long accountNumber) {
    this.accountNumber = accountNumber;
  }

  /**
   * Returns the current account balance.
   *
   * @return current balance
   */
  public double getBalance() {
    return balance;
  }

  /**
   * Updates the account balance for subclasses.
   *
   * @param balance new balance
   */
  protected void setBalance(double balance) {
    this.balance = balance;
  }

  /**
   * Returns a copy of the transaction list.
   *
   * @return transaction list copy
   */
  public List<Transaction> getTransactionList() {
    return new ArrayList<>(transactions);
  }

  /**
   * Replaces the transaction list with a defensive copy.
   *
   * @param transactionList replacement transaction list
   */
  public void setTransactionList(List<Transaction> transactionList) {
    if (transactionList == null) {
      this.transactions = new ArrayList<>();
    } else {
      this.transactions = new ArrayList<>(transactionList);
    }
  }

  /**
   * Deposits money into this account.
   *
   * @param amount amount to deposit
   */
  public abstract void deposit(double amount);

  /**
   * Withdraws money from this account.
   *
   * @param amount amount to withdraw
   */
  public abstract void withdraw(double amount);

  /**
   * Adds a positive amount to the account balance.
   *
   * @param amount amount to deposit
   * @throws InvalidFundingAmountException if the amount is not positive
   */
  protected void doDepositing(double amount) throws InvalidFundingAmountException {
    if (amount <= 0) {
      throw new InvalidFundingAmountException(amount);
    }
    balance += amount;
  }

  /**
   * Subtracts an amount from the account balance.
   *
   * @param amount amount to withdraw
   * @throws BankException if the amount is invalid or funds are insufficient
   */
  protected void doWithdrawing(double amount) throws BankException {
    if (amount <= 0) {
      throw new InvalidFundingAmountException(amount);
    }
    if (amount > balance) {
      throw new InsufficientFundsException(amount);
    }
    balance -= amount;
  }

  /**
   * Adds a transaction when the supplied record is not null.
   *
   * @param transaction transaction to add
   */
  public void addTransaction(Transaction transaction) {
    if (transaction != null) {
      transactions.add(transaction);
    }
  }

  /**
   * Builds the account's transaction history.
   *
   * @return formatted transaction history
   */
  public String getTransactionHistory() {
    StringBuilder history = new StringBuilder();
    history.append("Lịch sử giao dịch của tài khoản ")
        .append(accountNumber)
        .append(":");

    for (Transaction transaction : transactions) {
      history.append(System.lineSeparator())
          .append(transaction.getTransactionSummary());
    }

    logger.debug(
        "Transaction history retrieved: accountReference={}, transactionCount={}",
        maskAccountNumber(accountNumber),
        transactions.size());
    return history.toString();
  }

  /**
   * Compares accounts by account number.
   *
   * @param object object to compare
   * @return true when both objects represent the same account number
   */
  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (!(object instanceof Account)) {
      return false;
    }
    Account other = (Account) object;
    return accountNumber == other.accountNumber;
  }

  /**
   * Returns a hash code based on the account number.
   *
   * @return account hash code
   */
  @Override
  public int hashCode() {
    return Long.hashCode(accountNumber);
  }

  private String maskAccountNumber(long number) {
    String accountReference = Long.toString(number);
    if (accountReference.length() <= 4) {
      return "****";
    }
    return "****" + accountReference.substring(accountReference.length() - 4);
  }
}
