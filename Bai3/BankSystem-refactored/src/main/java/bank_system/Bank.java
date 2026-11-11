package bank_system;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Coordinates customers and reads customer/account data.
 */
public class Bank {
  private static final Logger logger = LoggerFactory.getLogger(Bank.class);

  private static final String CUSTOMER_ID_PATTERN = "\\d{9}";
  private static final String ACCOUNT_NUMBER_PATTERN = "CHECKING";
  private static final String SAVINGS_ACCOUNT_PATTERN = "SAVINGS";

  private List<Customer> customerList;

  /**
   * Creates an empty bank.
   */
  public Bank() {
    customerList = new ArrayList<>();
  }

  /**
   * Returns the bank's customer list.
   *
   * @return customer list
   */
  public List<Customer> getCustomerList() {
    return customerList;
  }

  /**
   * Replaces the bank's customer list.
   *
   * @param customerList replacement customer list
   */
  public void setCustomerList(List<Customer> customerList) {
    if (customerList == null) {
      this.customerList = new ArrayList<>();
    } else {
      this.customerList = new ArrayList<>(customerList);
    }
  }

  /**
   * Reads customer records and their accounts from a text input stream.
   *
   * <p>A customer line ends with a nine-digit identity number. An account line
   * contains an account number, an account type, and an opening balance.</p>
   *
   * @param inputStream input stream containing customer data
   */
  public void readCustomerList(InputStream inputStream) {
    if (inputStream == null) {
      logger.warn("Customer import skipped: input stream is null");
      return;
    }

    logger.info("Customer import started");
    BufferedReader reader = new BufferedReader(
        new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    Customer currentCustomer = null;
    int importedCustomers = 0;
    int importedAccounts = 0;

    try {
      String line;
      while ((line = reader.readLine()) != null) {
        String trimmedLine = line.trim();
        if (trimmedLine.isEmpty()) {
          continue;
        }

        String[] parts = trimmedLine.split("\\s+");
        if (parts.length >= 2
            && parts[parts.length - 1].matches(CUSTOMER_ID_PATTERN)) {
          StringBuilder name = new StringBuilder();
          for (int index = 0; index < parts.length - 1; index++) {
            if (index > 0) {
              name.append(' ');
            }
            name.append(parts[index]);
          }

          currentCustomer = new Customer(
              Long.parseLong(parts[parts.length - 1]),
              name.toString());
          customerList.add(currentCustomer);
          importedCustomers++;
          continue;
        }

        if (currentCustomer == null || parts.length < 3) {
          logger.warn("Skipping malformed customer data line");
          continue;
        }

        try {
          long accountNumber = Long.parseLong(parts[0]);
          double openingBalance = Double.parseDouble(parts[2]);

          if (ACCOUNT_NUMBER_PATTERN.equals(parts[1])) {
            currentCustomer.addAccount(new CheckingAccount(accountNumber, openingBalance));
            importedAccounts++;
          } else if (SAVINGS_ACCOUNT_PATTERN.equals(parts[1])) {
            currentCustomer.addAccount(new SavingsAccount(accountNumber, openingBalance));
            importedAccounts++;
          } else {
            logger.warn("Skipping account with unsupported account type={}", parts[1]);
          }
        } catch (NumberFormatException exception) {
          logger.warn("Skipping malformed account data: reason={}", exception.getMessage());
        }
      }
    } catch (IOException exception) {
      logger.error("Customer import failed while reading input", exception);
      return;
    }

    logger.info(
        "Customer import completed: customerCount={}, accountCount={}",
        importedCustomers,
        importedAccounts);
  }

  /**
   * Returns customer information sorted by identity number.
   *
   * @return customer information in ascending identity-number order
   */
  public String getCustomersInfoByIdOrder() {
    List<Customer> sortedCustomers = new ArrayList<>(customerList);
    sortedCustomers.sort(Comparator.comparingLong(Customer::getIdNumber));
    return formatCustomerInformation(sortedCustomers);
  }

  /**
   * Returns customer information sorted by full name and then identity number.
   *
   * @return customer information in ascending name order
   */
  public String getCustomersInfoByNameOrder() {
    List<Customer> sortedCustomers = new ArrayList<>(customerList);
    sortedCustomers.sort(
        Comparator.comparing(Customer::getFullName)
            .thenComparingLong(Customer::getIdNumber));
    return formatCustomerInformation(sortedCustomers);
  }

  private String formatCustomerInformation(List<Customer> customers) {
    StringBuilder result = new StringBuilder();
    for (Customer customer : customers) {
      if (result.length() > 0) {
        result.append(System.lineSeparator());
      }
      result.append(customer.getCustomerInfo());
    }
    return result.toString();
  }
}
