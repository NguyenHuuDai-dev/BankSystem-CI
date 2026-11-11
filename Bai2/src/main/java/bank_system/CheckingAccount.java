package bank_system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a checking account.
 */
public class CheckingAccount extends Account {
  private static final Logger logger = LoggerFactory.getLogger(CheckingAccount.class);

  /**
   * Creates a checking account.
   *
   * @param accountNumber account identifier
   * @param balance opening balance
   */
  public CheckingAccount(long accountNumber, double balance) {
    super(accountNumber, balance);
  }

  /**
   * Deposits money into the checking account and records the transaction.
   *
   * @param amount amount to deposit
   */
  @Override
  public void deposit(double amount) {
    double initialBalance = getBalance();
    try {
      doDepositing(amount);
      addTransaction(new Transaction(
          Transaction.TYPE_DEPOSIT_CHECKING,
          amount,
          initialBalance,
          getBalance()));
      logger.info(
          "Checking deposit completed: amount={}, resultingBalance={}",
          amount,
          getBalance());
    } catch (BankException exception) {
      logger.warn("Checking deposit rejected: reason={}", exception.getMessage());
    }
  }

  /**
   * Withdraws money from the checking account and records the transaction.
   *
   * @param amount amount to withdraw
   */
  @Override
  public void withdraw(double amount) {
    double initialBalance = getBalance();
    try {
      doWithdrawing(amount);
      addTransaction(new Transaction(
          Transaction.TYPE_WITHDRAW_CHECKING,
          amount,
          initialBalance,
          getBalance()));
      logger.info(
          "Checking withdrawal completed: amount={}, resultingBalance={}",
          amount,
          getBalance());
    } catch (BankException exception) {
      logger.warn("Checking withdrawal rejected: reason={}", exception.getMessage());
    }
  }
}
