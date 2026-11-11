package bank_system;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a bank customer and the customer's accounts.
 */
public class Customer {
  private long idNumber;
  private String fullName;
  private List<Account> accountList;

  /**
   * Creates an empty customer.
   */
  public Customer() {
    this(0L, "");
  }

  /**
   * Creates a customer with an identity number and full name.
   *
   * @param idNumber customer identity number
   * @param fullName customer's full name
   */
  public Customer(long idNumber, String fullName) {
    this.idNumber = idNumber;
    this.fullName = fullName;
    this.accountList = new ArrayList<>();
  }

  /**
   * Returns the customer's identity number.
   *
   * @return identity number
   */
  public long getIdNumber() {
    return idNumber;
  }

  /**
   * Updates the customer's identity number.
   *
   * @param idNumber new identity number
   */
  public void setIdNumber(long idNumber) {
    this.idNumber = idNumber;
  }

  /**
   * Returns the customer's full name.
   *
   * @return full name
   */
  public String getFullName() {
    return fullName;
  }

  /**
   * Updates the customer's full name.
   *
   * @param fullName new full name
   */
  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  /**
   * Returns the customer's accounts.
   *
   * @return account list
   */
  public List<Account> getAccountList() {
    return accountList;
  }

  /**
   * Replaces the customer's account list.
   *
   * @param accountList replacement account list
   */
  public void setAccountList(List<Account> accountList) {
    if (accountList == null) {
      this.accountList = new ArrayList<>();
    } else {
      this.accountList = new ArrayList<>(accountList);
    }
  }

  /**
   * Adds an account if it is not null and is not already present.
   *
   * @param account account to add
   */
  public void addAccount(Account account) {
    if (account != null && !accountList.contains(account)) {
      accountList.add(account);
    }
  }

  /**
   * Removes an account from the customer.
   *
   * @param account account to remove
   */
  public void removeAccount(Account account) {
    if (account != null) {
      accountList.remove(account);
    }
  }

  /**
   * Returns the customer's identity number and full name.
   *
   * @return formatted customer information
   */
  public String getCustomerInfo() {
    return "Số CMND: " + idNumber + ". Họ tên: " + fullName + ".";
  }
}
