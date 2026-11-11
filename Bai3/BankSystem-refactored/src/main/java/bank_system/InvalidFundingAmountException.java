package bank_system;

import java.util.Locale;

/**
 * Exception thrown when a deposit or withdrawal amount is invalid.
 */
public class InvalidFundingAmountException extends BankException {
  private static final long serialVersionUID = 1L;

  /**
   * Creates an exception for the supplied invalid amount.
   *
   * @param amount invalid transaction amount
   */
  public InvalidFundingAmountException(double amount) {
    super("Invalid transaction amount: $" + String.format(Locale.US, "%.2f", amount));
  }
}
