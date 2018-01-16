## **Prerequisites**
* * *

### **Please follow below steps to deploy this tool(for the first time)**
* Put the whole path to anywhere of Linux server;
* Unzip client tool
	> cd tool
	> unzip instantclient_10_2.zip
* Execute command:
	> dos2unix init.sh
	> chmod 755 init.sh
	> ./init.sh

## **How To Use**
* * *

### **Please follow below steps to run out all Reports via Colline task using coltaskmanager shell script**
* Set proper Report template/output format to corresponding task, e.g. PST_AssetSettlement for 'Asset Settlements Report 18', and output format is EXCEL
* Set task list under ${TASK}/list
	Format:
		<code>0:Reports:Asset Settlements Report 18:500:sa:password</code>
		*0* - run or not, 0 represents Run, 1 represents Not Run
		*Reports* - group name
		*Asset Settlements Report 18* - task name
		*500* - timeout
		*sa* - valid colline user
		*password* - user password
* Set proper configuration in config.init
* Execute command: `./startUp.sh -h` to see the usage
* Execute command: `./startUp.sh -s` (recommend to run as: `nohup ./startUp.sh -s &`)
* All reports output files are under ${TASK}/result/result_XXXXX and please check logs under ${TASK}/log
* You can get the final Report result zip result_XXXXX.zip under ${TASK}

## **Contact**
* * *

Please report bugs/problems to LRS Technical QA team.