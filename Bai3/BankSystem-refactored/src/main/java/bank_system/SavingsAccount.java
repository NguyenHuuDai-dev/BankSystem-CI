package bank_system;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a savings account with withdrawal restrictions.
 */
public class SavingsAccount extends Account {
  private static final Logger logger = LoggerFactory.getLogger(SavingsAccount.class);

  private static final double MAX_WITHDRAWAL_AMOUNT = 1000.0;
  private static final double MINIMUM_REMAINING_BALANCE = 5000.0;

  /**
   * Creates a savings account.
   *
   * @param accountNumber account identifier
   * @param balance opening balance
   */
  public SavingsAccount(long accountNumber, double balance) {
    super(accountNumber, balance);
  }

  /**
   * Deposits money into the savings account and records the transaction.
   *
   * @param amount amount to deposit
   */
  @Override
  public void deposit(double amount) {
    double initialBalance = getBalance();
    try {
      doDepositing(amount);
      addTransaction(new Transaction(
          Transaction.TYPE_DEPOSIT_SAVINGS,
          amount,
          initialBalance,
          getBalance()));
      logger.info(
          "Savings deposit completed: amount={}, resultingBalance={}",
          amount,
          getBalance());
    } catch (BankException exception) {
      logger.warn("Savings deposit rejected: reason={}", exception.getMessage());
    }
  }

  /**
   * Withdraws money when savings-account limits are satisfied.
   *
   * @param amount amount to withdraw
   */
  @Override
  public void withdraw(double amount) {
    double initialBalance = getBalance();
    try {
      if (amount > MAX_WITHDRAWAL_AMOUNT) {
        throw new InvalidFundingAmountException(amount);
      }
      if (initialBalance - amount < MINIMUM_REMAINING_BALANCE) {
        throw new InsufficientFundsException(amount);
      }

      doWithdrawing(amount);
      addTransaction(new Transaction(
          Transaction.TYPE_WITHDRAW_SAVINGS,
          amount,
          initialBalance,
          getBalance()));
      logger.info(
          "Savings withdrawal completed: amount={}, resultingBalance={}",
          amount,
          getBalance());
    } catch (BankException exception) {
      logger.warn("Savings withdrawal rejected: reason={}", exception.getMessage());
    }
  }
}
