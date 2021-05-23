Feature: Access operation history
  Access previously executed operations on the account

  Scenario: making a deposit, a withdraw, and verifying history
    Given The newly created bank account of client Paul Guillemet
    When I deposit 500 then withdraw 200
    Then The account history contains 2 operations with the correct data
