@ignore
Feature: even java interop performance test reports are possible

  Background:
    * def Utils = Java.type('mock.MockUtils')

  Scenario: fifty
    * def payload = { sleep: 50 }
    * def response = Utils.myRpc(payload, karate)

  Scenario: seventy five
    * def payload = { sleep: 30010 }
    * def response = Utils.myRpc(payload, karate)
    # this is deliberately set up to fail

  Scenario: hundred
    * def payload = { sleep: 27062 }
    * def response = Utils.myRpc(payload, karate)

