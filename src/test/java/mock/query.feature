Feature: Test basic query scenarios

  Background:
    * url karate.properties['mock.cats.url']

  Scenario: Test query successfully

    * print '######## in query'
    * header karate-name = 'one'
    When method get
    Then status 200
    And assert responseTime < 10

