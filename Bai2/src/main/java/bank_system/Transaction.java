package bank_system;

import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a completed transaction and its before-and-after balances.
 */
public class Transaction {
  private static final Logger logger = LoggerFactory.getLogger(Transaction.class);

  public static final int TYPE_DEPOSIT_CHECKING = 1;
  public static final int TYPE_WITHDRAW_CHECKING = 2;
  public static final int TYPE_DEPOSIT_SAVINGS = 3;
  public static final int TYPE_WITHDRAW_SAVINGS = 4;

  private int type;
  private double amount;
  private double initialBalance;
  private double finalBalance;

  /**
   * Creates a transaction record.
   *
   * @param type transaction type constant
   * @param amount transaction amount
   * @param initialBalance balance before the transaction
   * @param finalBalance balance after the transaction
   */
  public Transaction(
      int type, double amount, double initialBalance, double finalBalance) {
    this.type = type;
    this.amount = amount;
    this.initialBalance = initialBalance;
    this.finalBalance = finalBalance;
  }

  /**
   * Returns the transaction type.
   *
   * @return transaction type constant
   */
  public int getType() {
    return type;
  }

  /**
   * Updates the transaction type.
   *
   * @param type transaction type constant
   */
  public void setType(int type) {
    this.type = type;
  }

  /**
   * Returns the transaction amount.
   *
   * @return transaction amount
   */
  public double getAmount() {
    return amount;
  }

  /**
   * Updates the transaction amount.
   *
   * @param amount transaction amount
   */
  public void setAmount(double amount) {
    this.amount = amount;
  }

  /**
   * Returns the balance before the transaction.
   *
   * @return initial balance
   */
  public double getInitialBalance() {
    return initialBalance;
  }

  /**
   * Updates the balance before the transaction.
   *
   * @param initialBalance balance before the transaction
   */
  public void setInitialBalance(double initialBalance) {
    this.initialBalance = initialBalance;
  }

  /**
   * Returns the balance after the transaction.
   *
   * @return final balance
   */
  public double getFinalBalance() {
    return finalBalance;
  }

  /**
   * Updates the balance after the transaction.
   *
   * @param finalBalance balance after the transaction
   */
  public void setFinalBalance(double finalBalance) {
    this.finalBalance = finalBalance;
  }

  /**
   * Converts a transaction type constant into a readable Vietnamese label.
   *
   * @param transactionType transaction type constant
   * @return readable transaction label
   */
  public static String getTypeString(int transactionType) {
    switch (transactionType) {
      case TYPE_DEPOSIT_CHECKING:
        return "Nạp tiền vãng lai";
      case TYPE_WITHDRAW_CHECKING:
        return "Rút tiền vãng lai";
      case TYPE_DEPOSIT_SAVINGS:
        return "Nạp tiền tiết kiệm";
      case TYPE_WITHDRAW_SAVINGS:
        return "Rút tiền tiết kiệm";
      default:
        return "Không rõ";
    }
  }

  /**
   * Returns a formatted summary of this transaction.
   *
   * @return formatted transaction summary
   */
  public String getTransactionSummary() {
    logger.trace("Formatting transaction summary for type={}", type);
    return String.format(
        Locale.US,
        "- Kiểu giao dịch: %s. Số dư ban đầu: $%.2f. Số tiền: $%.2f. "
            + "Số dư cuối: $%.2f.",
        getTypeString(type),
        initialBalance,
        amount,
        finalBalance);
  }
}
