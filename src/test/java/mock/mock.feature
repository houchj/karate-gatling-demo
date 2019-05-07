Feature: cats stateful crud

  Background:
    * def uuid = function(){ return java.util.UUID.randomUUID() + '' }
    * def cats = {}
    * def tomcats = {name : 'Tom'}
    * def delay = function(){ java.lang.Thread.sleep(1001) }

  Scenario: pathMatches('/cats') && methodIs('post')
    * def cat = request
    * def id = uuid()
    * set cat.id = id
    * eval cats[id] = cat
    * def response = cat

  Scenario: pathMatches('/cats')
    * def response = $cats.*

  Scenario: pathMatches('/cats?$filter=name eq Tom')
    * def response = $tomcats.*

  Scenario: pathMatches('/cats/{id}') && methodIs('put')
    * eval cats[pathParams.id] = request
    * def response = request

  Scenario: pathMatches('/cats/{id}') && methodIs('delete')
    * eval karate.remove('cats', '$.' + pathParams.id)
    * def response = ''
    * def afterScenario = delay

  Scenario: pathMatches('/cats/{id}')
    * def response = cats[pathParams.id]
    * def responseStatus = response ? 200 : 404
