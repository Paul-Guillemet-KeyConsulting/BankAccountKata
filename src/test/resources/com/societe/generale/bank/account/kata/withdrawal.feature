Feature: Make a withdrawal
  Withdraw money from a bank account as a client

  Scenario: withdrawing 400 from an account which has 1200
    Given A newly created bank account where i made a 1200 deposit
    When I withdraw 400
    Then The balance is 800

  Scenario: withdrawing 400 from an account with insufficient funds
    Given A newly created bank account where i only made a 300 deposit
    When I try to withdraw 400
    Then The balance is still 300