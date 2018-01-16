#!/bin/bash

######################################################################################
#For Task Baseline Test
#
#
######################################################################################

#load all the functions
. ./lib/errorhandler.fun
. ./lib/task.fun
. ./lib/sql.fun
. ./lib/getduration.fun
#. ./lib/report.fun

TASK_TIME=$2

. $AUTOTEST_CONFIG_PATH/config.ini

#set the target installation path
#FIRMAMENT_BASE=/export/home/test/Colline2009DB_EAPCP07
#FIRMAMENT_BASE=/export/home/test/server/colline/standalone
#FIRMAMENT_BASE=/home/test/server/colline/cluster5_2
#FIRMAMENT_BASE=/home/test/server/colline/cluster

FIRMAMENT_BASE=${CLUSTER_HOME}
TASKROWS_COLLECT=/home/test/tools/ReportRows_Collect
export TASKROWS_COLLECT
export BIN_DIR=${FIRMAMENT_BASE}/bin
export MONITOR_DIR=${MONITOR_HOME}

#file definition
#FILE_TASKEXECSUMMARY=./taskexec.summary
FILE_CURRENTTASK=./currenttask
FILE_TASKLIST=$1
FILE_VAR=./filevar
FILE_SQL_RET=./filesqlret
#FILE_TASKEXECLOG=./taskexec.log
#echo "--------FILE_VAR="$FILE_VAR
#echo "---------FILE_SQL_RET="$FILE_SQL_RET

#set -x
#get collie version
CUR_DIR=`pwd`
cd ${BIN_DIR} 
COLLINE_VERSION=`cat env.sh |grep 'export PROFILE_NAME=[0-9a-zA-Z]' |awk -F= '{print$2}'`
DB_USER=`cat env.sh|grep 'DATABASENAME='|awk -F= '{print$2}'`
DB_PASSWD=creditderivative
DB_SERVER=`cat env.sh|grep 'DATABASEURL='|awk -F= '{print$2}'|awk -F: '{print$4":"$5}'|awk -F@ '{print$2}'`
S_ID=`cat env.sh|grep 'DATABASEURL='|awk -F= '{print$2}'|awk -F: '{print$6}'|awk -F'"' '{print$1}'`
cd ${CUR_DIR}

#FILE_TASKEXECSUMMARY defined
#TIMESTRING=`date '+%Y%m%d%H%M%S'`
#FILE_TASKEXECSUMMARY=taskexec.summary_${NEW_VERSION}_${TIMESTRING}
#FILE_TASKEXECLOG=taskexec.log_${NEW_VERSION}_${TIMESTRING}

#FILE_TASKEXECSUMMARY=taskexec.summary_${NEW_VERSION}_${TASK_TIME}


#export FILE_TASKEXECSUMMARY FILE_TASKEXECLOG COLLINE_VERSION
#. `dirname $0`/dbconf.sh
echo "I'm FILE_TASKEXECLOG="${FILE_TASKEXECLOG}>>${FILE_TASKEXECLOG}
echo "I'm database---------="${DB_USER} ${DB_PASSWD} ${DB_SERVER} ${S_ID}>>${FILE_TASKEXECLOG}

#mv taskexec.* ./log/
#test connection with DataBase
TESTDBCONN=`testdbconnection ${DB_USER} ${DB_PASSWD} ${DB_SERVER} ${S_ID}`
[ ${TESTDBCONN} -eq 1 ]  && echo 'failed to connect to DataBase' && exit -1
[ ${TESTDBCONN} -eq 0 ]  && echo "Target DataBase: ${DB_USER}/${DB_PASSWD}/@${S_ID}  ${DB_SERVER}" >>${FILE_TASKEXECSUMMARY}  

#echo Target Version: ${COLLINE_VERSION} >>${FILE_TASKEXECSUMMARY}
#echo Target Path:${BIN_DIR} >>${FILE_TASKEXECSUMMARY}


echo Target Version: ${COLLINE_VERSION} >>${FILE_TASKEXECLOG}
echo Target Path:${BIN_DIR} >>${FILE_TASKEXECLOG}

#init task   
eraseprevioustask
  
#retrieve the tasklist, trigger task    
IFS=$'\n'
for LINE in `cat ${FILE_TASKLIST}`
do

   TASKNAME_=`echo ${LINE} |awk -F: '{print$3}'|awk -v v="'" '{gsub(v,"")}1'`
   FLAG_=`echo ${LINE} |awk -F: '{print$1}'`
   ARG_=`echo ${LINE} |awk -F: '{print$2,":",$3}'`
   SQLFILE_=`echo ${LINE} |awk -F: '{print$4}'`
   REPORT_TEMPLATE_NAME=`echo ${LINE} |awk -F: '{print$5}'`
   TIME4TASKEXECUTION=`echo ${LINE} |awk -F: '{print$6}'`
   USERNAME=`echo ${LINE} |awk -F: '{print$7}'`
   PASSWORD=`echo ${LINE} |awk -F: '{print$8}'`



   [ ${TIME4TASKEXECUTION}X = X ] && TIME4TASKEXECUTION=1


   if [ ${FLAG_} = 1 ]; then
      continue
   fi
   
   #update the file curtask       
   echo ${TASKNAME_} >${FILE_CURRENTTASK} 
   echo "-------------I am task ="${TASKNAME_}>>${FILE_TASKEXECLOG}

   #execute pre sql statement
   SQL_RET_STR=""
   VAR_STR=""
 #  [ -f ${FILE_VAR} ] && rm ${FILE_VAR}
 #  [ -f ${FILE_SQL_RET} ] && rm ${FILE_SQL_RET} 
 #  [ ! ${SQLFILE_} = null ] &&  sql_validation ${SQLFILE_} pre ${FILE_VAR} "${FILE_SQL_RET}" "${DB_USER}" "${DB_PASSWD}" "${DB_SERVER}" "${S_ID}"
 
   #select sql
    if [ "${TASKNAME_}" = "Recalculate Statements 1" ]; then
       echo "-----------SQL Select before task execute ------" >>${FILE_TASKEXECLOG}
       
       select_sql_1="select count(1) from colagreementheader a, COLSTATEMENTSTATUS s where a.id = s.agreementid and s.statusid =68 and a.region = 'New York';"
       sql_1_des="Number of Agreement statementstatus approved in New York is:"
       sql_select ${DB_USER} ${DB_PASSWD} ${DB_SERVER} ${S_ID} "${select_sql_1}" "${sql_1_des}" >>${FILE_TASKEXECLOG}

    fi 
 
    if [ "${TASKNAME_}" = "Auto Email Call/Recall 1" ]; then
       echo "-----------SQL Select before task execute ------" >>${FILE_TASKEXECLOG}
       
       select_sql_1="SELECT count(1) FROM ColStatementCalcreqView,ColAgreementHeader  WHERE (ColStatementCalcreqView.agreementId = ColAgreementHeader.id) AND (ColStatementCalcreqView.maxDefCalcReqId > 0 OR ColStatementCalcreqView.maxColCalcReqId > 0 OR ColStatementCalcreqView.maxExpCalcReqId >0 OR ColStatementCalcreqView.maxArchiveReqId > 0) AND ColAgreementHeader.Statusid = 68"

       sql_1_des="Number of Auto Email Call/Recall 1 is:"
       sql_select ${DB_USER} ${DB_PASSWD} ${DB_SERVER} ${S_ID} "${select_sql_1}" "${sql_1_des}" >>${FILE_TASKEXECLOG}

    fi 
 
   #init task   
   eraseprevioustask
   trigger_times=0
   
   trigger_analysis(){
  
   #set the start time
   #STARTTIMESTRING_=`date '+%Y%m%d%H%M%S'`
   #STARTTIME_=`date '+%Y-%m-%d %H:%M:%S'`

   #trigger task  
   triggertask "${ARG_}" "${BIN_DIR}" "${FILE_TASKEXECLOG}" "${USERNAME}" "${PASSWORD}"

   #set the start time
   #ENDTIMESTRING_=`date '+%Y%m%d%H%M%S'`
   #ENDTIME_=`date '+%Y-%m-%d %H:%M:%S'`
   
   #wait task complete
   echo "Waiting "${TIME4TASKEXECUTION}" second for Task " ${TASKNAME_} execute >>${FILE_TASKEXECLOG}
   sleep 5

   #initialize 
   _TASKNAME="NULL"
   _TASKSTATUS="NULL"


   #get the task status
   gettaskstatus
   # echo "Test Test Test"${_TASKNAME} >>${FILE_TASKEXECLOG}

   #set the start time
   STARTTIME_=${_EXECUTETIME}
   
   
   while true 
   do
      #get the task status
      gettaskstatus
      
      #TASKLOG

#echo "----------------------------TASKLOG="${TASKLOG}
      #TASKLOG=./TMP_LOG
      TASKFlag_=`tail -10 ${FILE_TASKEXECLOG}|grep 'Error 6001, Unable to trigger task'|wc -l`
      #TASKFlag_=`echo "${TASKLOG}"|grep 'Error 6001, Unable to trigger task'|wc -l`
      
      #break loop while task prescript didn't executed 
      #[[ ${_TASKNAME} = "Default" ]] && echo ${STARTTIME_},${STARTTIME_},${TASKNAME_},Not Triggered >>${FILE_TASKEXECSUMMARY} && break
      # echo "Test Test Test"${_TASKNAME} >>${FILE_TASKEXECLOG}
      if [ "${_TASKNAME}"X = "Default"X ]; then
         MYTIME=`date '+%Y-%m-%d %H:%M:%S'`
         echo ${MYTIME},${MYTIME},${TASKNAME_},Not Triggered >>${FILE_TASKEXECSUMMARY}
         sleep 10

         ########## Error 6001 verified ###########
         if [ "${TASKFlag_}" = 1 ] && [ "${trigger_times}" -lt 3 ]; then
             echo "--------Error 6001,Task not triggered,try to trigger again! -------------trigger_times="$trigger_times >>${FILE_TASKEXECLOG}
             $((trigger_times++))
            # trigger_analysis             
         else
             echo "----------break now 111 --------" >>${FILE_TASKEXECLOG}
             break
         fi
      fi

      if [ "${_TASKNAME}" = "${TASKNAME_}"  -a "${_TASKSTATUS}" = "Start" ]; then 
#         ENDTIME_2=`date '+%Y-%m-%d %H:%M:%S'`
         ENDTIME_2=`date '+%s.%N'`
#         getduration "${STARTTIME_}" "${ENDTIME_2}"
         getNanoDuration "${STARTTIME_}" "${ENDTIME_2}"


#         if [ "${DURATION_}"0 -lt "${TIME4TASKEXECUTION}"0 ]; then  
         #-eq 1 means true, -eq 0 means false
         if [ $(echo "${Nano_DURATION_} < ${TIME4TASKEXECUTION}"|bc) -eq 1 ]; then
            sleep 1
            continue
         else
#            echo "Sorry, no more waiting." "${TASKNAME_}," " TIME_Execute = ""${DURATION_}"", TIME4TASKEXECUTION ="${TIME4TASKEXECUTION}"." >>${FILE_TASKEXECSUMMARY}
            echo "Sorry, no more waiting." "${TASKNAME_}," " TIME_Execute = ""${Nano_DURATION_}"", TIME4TASKEXECUTION ="${TIME4TASKEXECUTION}"." >>${FILE_TASKEXECSUMMARY}
#            echo "Sorry, no more waiting." "${TASKNAME_}," " TIME_Execute = ""${DURATION_}"", TIME4TASKEXECUTION ="${TIME4TASKEXECUTION}"." >>${FILE_TASKEXECLOG}
            echo "Sorry, no more waiting." "${TASKNAME_}," " TIME_Execute = ""${Nano_DURATION_}"", TIME4TASKEXECUTION ="${TIME4TASKEXECUTION}"." >>${FILE_TASKEXECLOG}
#            echo ${STARTTIME_},${ENDTIME_2},${TASKNAME_},${DURATION_}"s time out",${SQL_RET_STR},${VAR_STR} >>${FILE_TASKEXECSUMMARY}
            echo ${STARTTIME_},${ENDTIME_2},${TASKNAME_},${Nano_DURATION_}"s time out",${SQL_RET_STR},${VAR_STR} >>${FILE_TASKEXECSUMMARY}
            break
         fi
      fi 

             
      if [ "${_TASKNAME}" = "${TASKNAME_}"  -a  "${_TASKSTATUS}" = "End" ]; then
         #ENDTIMESTRING_=`date '+%Y%m%d%H%M%S'`
         #ENDTIME_=`date '+%Y-%m-%d %H:%M:%S'`
         ENDTIME_=${_EXECUTETIME}         

#         getduration ${STARTTIME_} ${ENDTIME_}
         getNanoDuration ${STARTTIME_} ${ENDTIME_}
    
         #execute the post sqlstatement and validate the result
 #        [ ! ${SQLFILE_} = null ] &&  sql_validation ${SQLFILE_} post ${FILE_VAR} ${FILE_SQL_RET}   
 #        SQL_RET_STR=`cat ${FILE_SQL_RET} |tr '\n' ''`
 #        VAR_STR=`cat ${FILE_VAR} |tr -s ' ' '=' |tr '\n' ''`

         #counts the result set if task is report
         #report_validation ${REPORT_TEMPLATE_NAME} ${REPORT_STRORAGE_PATH} ${REPORT_ARCHIEVE_PATH} ${BASE_REPORT_PATH} ${COLLINE_VERSION}

         #prin
#         echo ${STARTTIME_},${ENDTIME_},${TASKNAME_},${DURATION_},${SQL_RET_STR},${VAR_STR} >>${FILE_TASKEXECSUMMARY}  
         echo ${STARTTIME_},${ENDTIME_},${TASKNAME_},${Nano_DURATION_},${SQL_RET_STR},${VAR_STR} >>${FILE_TASKEXECSUMMARY}
         
         #If is Approve All Statements task, sleep 900 seconds for msg calc
         if [ "${TASKNAME_}" = "Approve All Statements 5" ]; then
            echo "-----------It's Approve All Statements task, sleep 900 seconds for msg calc. ------" >>${FILE_TASKEXECLOG}
            sleep 900 
         fi
        
         #If is Recalculate Securities 1 task, sleep 1800 seconds for msg calc
         if [ "${TASKNAME_}" = "Recalculate Securities 1" ]; then
            echo "-----------It's Recalculate Securities 1 task, sleep 1800 seconds for msg calc. ------" >>${FILE_TASKEXECLOG}
            sleep 1800
         fi

         #If is Reset Trading Inactive task, sleep 1800 seconds for msg calc
         if [ "${TASKNAME_}" = "Reset Trading Inactive" ]; then
            echo "-----------It's Reset Trading Inactive task, sleep 1800 seconds for msg calc. ------" >>${FILE_TASKEXECLOG}
            sleep 1800
         fi

         #If it is Import Counterparty Amount task, sleep 7200 seconds for avoid database lock
         if [ "${TASKNAME_}" = "Import Counterparty Amount 1" ]; then
            echo "-----------It's Import Counterparty Amount task, sleep 7200 seconds for avoid database lock. ------" >>${FILE_TASKEXECLOG}
            sleep 7200
         fi
 
         #If it is Import Agreement UDF task, sleep 3600 seconds for avoid database lock
         if [ "${TASKNAME_}" = "Import Agreement UDF 1" ]; then
            echo "-----------It's Import Agreement UDF task, sleep 3600 seconds for avoid database lock. ------" >>${FILE_TASKEXECLOG}
            sleep 3600
         fi 
 
         break
      fi
      sleep 1
   done
}
   #trigger task, and analysis
   trigger_analysis
   #pacing
   sleep 10  
done

sleep 30

#Merge log and summary file[Test]
cat ${FILE_TASKEXECSUMMARY} >>${FILE_TASKEXECMERGESUMMARY}
cat ${FILE_TASKEXECLOG} >>${FILE_TASKEXECMERGELOG}

#Analysis all task result and create result report
#cd "${TASKROWS_COLLECT}"
#. ./task_sum.sh 


#set +x
