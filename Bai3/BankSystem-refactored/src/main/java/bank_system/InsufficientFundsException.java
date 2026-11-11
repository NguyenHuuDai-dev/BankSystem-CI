package bank_system;

import java.util.Locale;

/**
 * Exception thrown when an account does not have sufficient funds.
 */
public class InsufficientFundsException extends BankException {
  private static final long serialVersionUID = 1L;

  /**
   * Creates an exception for a withdrawal that cannot be completed.
   *
   * @param amount requested withdrawal amount
   */
  public InsufficientFundsException(double amount) {
    super("Insufficient funds for transaction amount: $"
        + String.format(Locale.US, "%.2f", amount));
  }
}
