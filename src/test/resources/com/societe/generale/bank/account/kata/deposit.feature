Feature: Make a deposit
  Deposit money on a bank account as a client

  Scenario: making a 500 deposit
    Given The newly created bank account of a client named Paul Guillemet
    When I deposit 500
    Then The account balance is 500

  Scenario: making a 500 deposit and then a 200 deposit
    Given An other newly created bank account of a client named Paul Guillemet
    When I deposit 500 and then 200 more
    Then The new account balance is 700