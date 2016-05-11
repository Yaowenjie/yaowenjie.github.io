@base_flow
Feature: Test home page
  I can go to my blog home page
  When I refresh page
  I can see background image changes
  When I search one article using one key word
  Then I can click the search result and enter one article page


  Scenario:
    Given I enter my blog address "http://localhost:4000" and go to Home page
    # Random Background Image:
    And I can see the page header and footer have same backgroud image
    Then I refresh current page to see the backgroud image changes
    # Search Button Feature:
    When I click search button and enter "Powershell" in the input field and click the first result
    Then I go to the article page with title containing "Powershell"
    Then I close current window
    # Post Page Feature:
