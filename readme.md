**Colline Automation Test Project**
===
An aggregation project to store all Colline automation test related modules

**Installation**
===
please refer to [How to Setup Automation Test Environment (Selenium)](https://confluence.lombardrisk.com/display/QA/Setup+Automation+Test+Development+Environment)

**Guideline**
===
For database backup
1. Log in to linux server (ip:172.20.20.235 username:oracle password:0racle1)
2. Go to folder /home/oracle/automation/lrm-db-datapump-1.4.0
3. Run script export240.sh
4. The image will be stroe on S3 server (AWS) with path "COL/14_1/ORACLE_12C_COL_14_1_RELEASE_INIT_AUT"
NOTE: the image only support oracle version 12c

To run the script
After the script and test enviornment are setted, the script can be trigged by meaven script "mvn clean verify -DxmlFileName=suites\regression.xml"

**OtherInformation**
===
Please refer to [Functional Automation Test](https://confluence.lombardrisk.com/display/QA/Functional+Automation+Test) for detailed information