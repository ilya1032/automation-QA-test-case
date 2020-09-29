@upload
Feature: upload and download files to/from FTP server

  Anonymous client should be able to download files from server, upload files to "/upload" directory. After uploading
  file "/upload" dir should be empty.

  Background:
    Given client connects to ftp server
    And client logins anonymously

  Scenario Outline: validate files downloading completes properly

    When client downloads <file_name> file
    Then file <file_name> download completes properly
    Examples:
      | file_name |
      | 100KB.zip |
      | 3MB.zip   |
      | 50MB.zip  |
      | 512KB.zip |
      | 5MB.zip   |

  Scenario Outline: validate files upload completes properly

    When client downloads <file_name> file
    And client changes directory to upload
    And client uploads <file_name> to server
    Then file <file_name> upload completes properly
    And file <file_name> being deleted from upload folder by server
    Examples:
      | file_name |
      | 100KB.zip |
      | 3MB.zip   |
      | 50MB.zip  |
      | 512KB.zip |
      | 5MB.zip   |