@base_flow
Feature: Use Search Button

  Scenario:
    Given I enter my blog address "http://localhost:4000" and go to Home page
    Then I refresh current page
    When I click search button and enter "Powershell" in the input field and click the first result
    Then I go to the article page with title containing "Powershell"
    Then I close current window