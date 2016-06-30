@base_flow
Feature: Test home page
  I can go to my blog home page
  When I refresh page
  I can see background image changes
  When I search one article using one key word
  Then I can click the search result and enter one article page


  Scenario:
    Given I enter my blog address "http://yaowenjie.github.io:4000" and go to Home page
    # Random Background Image:
    And I can see the page header and footer have same background image
    Then I refresh current page to see the background image changes
    # Search Button Feature:
    When I click search button and enter "Powershell" in the input field and click the first result
    Then I go to the article page with title containing "Powershell"
    # Post Page Feature:
    Then I can see some tags
    And I can see share block with title containing "share" and "分享"
    Then I click the wechat button
    Then I see the QR code image
    Then I can see the avatar image below share block
    And I can see the textArea
    Then I enter "Bad Post!" in this textArea
    And I click sticker button to add a smile sticker
    Then I close current window
