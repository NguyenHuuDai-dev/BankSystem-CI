# BankSystem – refactored

## Code style
This project uses Google Java Style through `maven-checkstyle-plugin`.
The Checkstyle `check` goal is bound to Maven's `validate` phase.

## Logging
The application uses the SLF4J API with Logback Classic as the logging
implementation. The Logback configuration is in `src/main/resources/logback.xml`.

- `TRACE`: very detailed diagnostic information.
- `DEBUG`: diagnostic details useful during development.
- `INFO`: normal business milestones, such as completed deposits.
- `WARN`: rejected or malformed input that does not stop the application.
- `ERROR`: unexpected failures that prevent an operation from completing.

Do not log passwords, PINs, credentials, or full account identifiers.
The account-history logger masks account identifiers.

## Build
Use JDK 11 or later and Maven 3.6.3 or later.

```bash
mvn clean verify
```

Run Checkstyle alone:

```bash
mvn checkstyle:check
```

Run compilation only:

```bash
mvn clean compile
```

## Input format used by Bank.readCustomerList
A customer line ends in a nine-digit customer ID, for example:

```text
Nguyen Van An 123456789
```

An account line contains account number, account type, and opening balance:

```text
100001 CHECKING 2500000
100002 SAVINGS 8000000
```

The input stream is read as UTF-8. The method does not close the caller-owned stream.


## Unit tests and GitHub Actions CI

The project uses JUnit 5. Run the full Maven verification lifecycle locally with:

```bash
mvn clean verify
```

The GitHub Actions workflow is `.github/workflows/maven-ci.yml`. It runs for every
`push` and `pull_request`, executes `mvn clean verify` (including the `test` and
`package` phases), and uploads `target/*.jar` as the `banksystem-jar` artifact.

To inspect the result, open the repository on GitHub, choose **Actions**, open the
latest **BankSystem Maven CI** run, and expand the failed step to read its execution log.

### Safe failure demonstration

On a temporary branch, change one expected value in a test to an intentionally wrong
value (for example, expect `999.0` instead of `1500.0` in
`CheckingAccountTest.depositIncreasesBalanceAndRecordsTransaction`). Commit and push
that change. The workflow should fail in the Maven test step and the log will show
the failing test and expected/actual values. Revert the temporary change and push
again; the workflow should pass and upload the JAR. Do not leave the intentional
failure in the main branch.
