@base_flow
Feature: Use Search Button

  Scenario:
    Given I go to My Blog Home page
    When I click search button and enter "Powershell" in the input blank and click the first article
    Then I go to the article page with title containing "Powershell"