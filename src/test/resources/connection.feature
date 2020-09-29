@connection
Feature: Connection to ftp server
  Client tries to connect to ftp server with different login names, while only anonymous connection is permitted.

  Scenario Outline: client tries to connect to server
    Given client has <login> and <password>
    When client tries to connect
    Then result should be <result>
    Examples:
      | login         | password     | result  |
      | 'randomLogin' | 'randomPass' | 'False' |
      | 'randomLogin' | ''           | 'False' |
      | 'anonymous'   | 'Password'   | 'True'  |
