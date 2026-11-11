package bank_system;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Unit tests for savings-account withdrawal rules. */
class SavingsAccountTest {
  private SavingsAccount account;

  @BeforeEach
  void setUp() {
    account = new SavingsAccount(87654321L, 7000.0);
  }

  @Test
  void allowedWithdrawalKeepsMinimumBalance() {
    account.withdraw(1000.0);

    assertEquals(6000.0, account.getBalance(), 0.001);
    assertEquals(1, account.getTransactionList().size());
  }

  @Test
  void withdrawalAboveLimitIsRejected() {
    account.withdraw(1500.0);

    assertEquals(7000.0, account.getBalance(), 0.001);
    assertTrue(account.getTransactionList().isEmpty());
  }

  @Test
  void withdrawalBelowMinimumRemainingBalanceIsRejected() {
    SavingsAccount lowBalanceAccount = new SavingsAccount(11223344L, 5500.0);
    lowBalanceAccount.withdraw(1000.0);

    assertEquals(5500.0, lowBalanceAccount.getBalance(), 0.001);
    assertTrue(lowBalanceAccount.getTransactionList().isEmpty());
  }
}
