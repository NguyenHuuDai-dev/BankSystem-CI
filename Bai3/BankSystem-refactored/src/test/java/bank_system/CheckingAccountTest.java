package bank_system;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/** Unit tests for checking-account operations. */
class CheckingAccountTest {
  private CheckingAccount account;

  @BeforeEach
  void setUp() {
    account = new CheckingAccount(12345678L, 1000.0);
  }

  @Test
  void depositIncreasesBalanceAndRecordsTransaction() {
    account.deposit(500.0);

    assertEquals(1500.0, account.getBalance(), 0.001);
    assertEquals(1, account.getTransactionList().size());
  }

  @Test
  void withdrawDecreasesBalanceAndRecordsTransaction() {
    account.withdraw(300.0);

    assertEquals(700.0, account.getBalance(), 0.001);
    assertEquals(1, account.getTransactionList().size());
  }

  @Test
  void invalidDepositDoesNotChangeBalanceOrRecordTransaction() {
    account.deposit(0.0);

    assertEquals(1000.0, account.getBalance(), 0.001);
    assertTrue(account.getTransactionList().isEmpty());
  }
}
