**Colline Letter Comparison Test**
===
A place to store all the letter comparison test cases/scenarios/data and the other relevant automation test documents

**Guideline**
===
1.  restore database with dump files located at \\sha-nas-a\CollineQA\database backup\automation\letter-compare

2.  Use BDD feature LetterCompare.feature to generate PDF file located at src\test\resources\features\LetterCompare.feature

3.  The generated PDF file will be saved to the folder '/home/test/Feed' on the server

4.  After PDF files were generated, copy all files to .\backup\letters\CurrentVersion

5.  Set up all configuration items in .\config.ini

6.  Execute run.bat

7.  Result letter will send out after script execution

8.  If differences find, the detailed information will be stored in .\backup\letters\Result folder