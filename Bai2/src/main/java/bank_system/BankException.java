package bank_system;

/**
 * Base checked exception for errors raised by the banking domain.
 */
public class BankException extends Exception {
  private static final long serialVersionUID = 1L;

  /**
   * Creates a bank exception with a descriptive message.
   *
   * @param message description of the error
   */
  public BankException(String message) {
    super(message);
  }
}
