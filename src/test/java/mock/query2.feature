Feature: Test basic workers query scenarios

  Background:
    * url karate.properties['mock.cats.url']

  Scenario: Test query worker successfully

    * print '######## in query2'
    * header karate-name = 'two'
    And params { $filter: 'name eq Tom' }
    When method get
    Then status 200
    And assert responseTime < 10
