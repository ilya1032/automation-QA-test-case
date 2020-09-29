@content
Feature: ftp server content check

  Background:
    Given client connects to ftp server
    And client logins anonymously

  Scenario Outline: validate files for download

    When client get list of file and directory names
    Then the file <file_name> exists
    Examples:
      | file_name  |
      | 1000GB.zip |
      | 100GB.zip  |
      | 100KB.zip  |
      | 100MB.zip  |
      | 10GB.zip   |
      | 10MB.zip   |
      | 1GB.zip    |
      | 1KB.zip    |
      | 1MB.zip    |
      | 200MB.zip  |
      | 20MB.zip   |
      | 2MB.zip    |
      | 3MB.zip    |
      | 500MB.zip  |
      | 50GB.zip   |
      | 50MB.zip   |
      | 512KB.zip  |
      | 5MB.zip    |

  Scenario: upload folder exists
    When client get list of file and directory names
    Then the folder upload exists
    And client can change directory to upload

  Scenario: upload folder should be empty
    When client changes directory to upload
    Then current directory is empty

  Scenario Outline: check size and modified date of files on ftp server
    When client get list of files
    Then the file <file_name> is size of <file_size> and last modified on <date> <time>
    Examples:
      | file_name  | file_size  | date       | time    |
      | 1000GB.zip | 1048576000 | 19.02.2016 | 3:00:00 |
      | 100GB.zip  | 104857600  | 19.02.2016 | 3:00:00 |
      | 100KB.zip  | 100        | 19.02.2016 | 3:00:00 |
      | 100MB.zip  | 102400     | 19.02.2016 | 3:00:00 |
      | 10GB.zip   | 10485760   | 19.02.2016 | 3:00:00 |
      | 10MB.zip   | 10240      | 19.02.2016 | 3:00:00 |
      | 1GB.zip    | 1048576    | 19.02.2016 | 3:00:00 |
      | 1KB.zip    | 1          | 19.02.2016 | 3:00:00 |
      | 1MB.zip    | 1024       | 19.02.2016 | 3:00:00 |
      | 200MB.zip  | 204800     | 19.02.2016 | 3:00:00 |
      | 20MB.zip   | 20480      | 19.02.2016 | 3:00:00 |
      | 2MB.zip    | 2048       | 19.02.2016 | 3:00:00 |
      | 3MB.zip    | 3072       | 19.02.2016 | 3:00:00 |
      | 500MB.zip  | 512000     | 19.02.2016 | 3:00:00 |
      | 50GB.zip   | 52428800   | 24.07.2014 | 4:00:00 |
      | 50MB.zip   | 51200      | 19.02.2016 | 3:00:00 |
      | 512KB.zip  | 512        | 19.02.2016 | 3:00:00 |
      | 5MB.zip    | 5120       | 19.02.2016 | 3:00:00 |