1. please put tools folder under /home/test/
2. go to homen/test/tools/auto_test, change config.ini 
   DB_USER=your db user
   CLUSTER_HOME=your colline server dir   etc."/home/test/colline/server/RELEASE_14_1_0"
	

3. please put coltaskmanager_Baseline.sh file under bin folder of colline
4. modify env.sh of colline, add 2 rows
   
   DATABASENAME=REPORT_12_3(your value)
   DATABASEURL="jdbc:oracle:thin:@172.20.20.197:1521:qa11g1"(your value)

5. go to /home/test/tools/teammonitor/Baseline/Task, excute 
   ./controller.sh all_report_list
   to generate all reports