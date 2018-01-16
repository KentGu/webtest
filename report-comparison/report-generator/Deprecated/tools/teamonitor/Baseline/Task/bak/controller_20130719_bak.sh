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


#set the target installation path
#FIRMAMENT_BASE=/export/home/test/Colline2009DB_EAPCP07
#FIRMAMENT_BASE=/export/home/test/server/colline/standalone
FIRMAMENT_BASE=/home/test/server/colline/cluster
#FIRMAMENT_BASE=/home/test/server/colline/cluster4

export BIN_DIR=${FIRMAMENT_BASE}/bin
export MONITOR_DIR=${MONITOR_HOME}

#file definition
FILE_TASKEXECSUMMARY=./taskexec.summary
FILE_CURRENTTASK=./currenttask
FILE_TASKLIST=$1
FILE_VAR=./filevar
FILE_SQL_RET=./filesqlret
FILE_TASKEXECLOG=./taskexec.log
#echo "--------FILE_VAR="$FILE_VAR
#echo "---------FILE_SQL_RET="$FILE_SQL_RET

set +x
#get collie version
CUR_DIR=`pwd`
cd ${BIN_DIR} 
COLLINE_VERSION=`cat env.sh |grep 'export PROFILE_NAME=[0-9a-zA-Z]' |awk -F= '{print$2}'`
cd ${CUR_DIR}

. `dirname $0`/dbconf.sh


#test connection with DataBase
TESTDBCONN=`testdbconnection ${DB_USER} ${DB_PASSWD} ${DB_SERVER} ${S_ID}`
[ ${TESTDBCONN} -eq 1 ]  && echo 'failed to connect to DataBase' && exit -1
[ ${TESTDBCONN} -eq 0 ]  && echo "Target DataBase: ${DB_USER}/${DB_PASSWD}/@${S_ID}  ${DB_SERVER}" >>${FILE_TASKEXECSUMMARY}  

echo Target Version: ${COLLINE_VERSION} >>${FILE_TASKEXECSUMMARY}
echo Target Path:${BIN_DIR} >>${FILE_TASKEXECSUMMARY}

#init task   
eraseprevioustask
  
#retrieve the tasklist, trigger task    
IFS=$'\n'
for LINE in `cat ${FILE_TASKLIST}`
do

   TASKNAME_=`echo ${LINE} |awk -F: '{print$3}'`
   FLAG_=`echo ${LINE} |awk -F: '{print$1}'`
   ARG_=`echo ${LINE} |awk -F: '{print$2,":",$3}'`
   SQLFILE_=`echo ${LINE} |awk -F: '{print$4}'`
   REPORT_TEMPLATE_NAME=`echo ${LINE} |awk -F: '{print$5}'`
   TIME4TASKEXECUTION=`echo ${LINE} |awk -F: '{print$6}'`

   [ ${TIME4TASKEXECUTION}X = X ] && TIME4TASKEXECUTION=1


   if [ ${FLAG_} = 1 ]; then
      continue
   fi
   
   #update the file curtask       
   echo ${TASKNAME_} >${FILE_CURRENTTASK} 

   #execute pre sql statement
   SQL_RET_STR=""
   VAR_STR=""
 #  [ -f ${FILE_VAR} ] && rm ${FILE_VAR}
 #  [ -f ${FILE_SQL_RET} ] && rm ${FILE_SQL_RET} 
 #  [ ! ${SQLFILE_} = null ] &&  sql_validation ${SQLFILE_} pre ${FILE_VAR} "${FILE_SQL_RET}" "${DB_USER}" "${DB_PASSWD}" "${DB_SERVER}" "${S_ID}"
  
  
   #init task   
   eraseprevioustask

   #trigger task  
   triggertask "${ARG_}" "${BIN_DIR}" "${FILE_TASKEXECLOG}" 
   
   #set the start time
   STARTTIMESTRING_=`date '+%Y%m%d%H%M%S'`
   STARTTIME_=`date '+%Y-%m-%d %H:%M:%S'`

   #wait task complete
   echo "Waiting "${TIME4TASKEXECUTION}" second for Task " ${TASKNAME_} execute >>${FILE_TASKEXECLOG}
   sleep 2
   
   while true 
   do
      #initialize 
      _TASKNAME="NULL"
      _TASKSTATUS="NULL"
      
                        
      #get the task status
      gettaskstatus

      #break loop while task prescript didn't executed 
      [[ ${_TASKNAME} = "Default" ]] && echo ${STARTTIME_},${STARTTIME_},${TASKNAME_},Not Triggered >>${FILE_TASKEXECSUMMARY} && break
       
      if [ "${_TASKNAME}" = "${TASKNAME_}"  -a  "${_TASKSTATUS}" = "End" ];then
         ENDTIMESTRING_=`date '+%Y%m%d%H%M%S'`
         ENDTIME_=`date '+%Y-%m-%d %H:%M:%S'`
         getduration ${STARTTIME_} ${ENDTIME_}
    
         #execute the post sqlstatement and validate the result
 #        [ ! ${SQLFILE_} = null ] &&  sql_validation ${SQLFILE_} post ${FILE_VAR} ${FILE_SQL_RET}   
 #        SQL_RET_STR=`cat ${FILE_SQL_RET} |tr '\n' ''`
 #        VAR_STR=`cat ${FILE_VAR} |tr -s ' ' '=' |tr '\n' ''`

         #counts the result set if task is report
         #report_validation ${REPORT_TEMPLATE_NAME} ${REPORT_STRORAGE_PATH} ${REPORT_ARCHIEVE_PATH} ${BASE_REPORT_PATH} ${COLLINE_VERSION}

         #prin
         echo ${STARTTIME_},${ENDTIME_},${TASKNAME_},"TIME " $(( ${DURATION_} - 1 ))"s",${SQL_RET_STR},${VAR_STR} >>${FILE_TASKEXECSUMMARY}  
        break
      fi
        
      if [ "${_TASKNAME}" = "${TASKNAME_}"  -a "${_TASKSTATUS}" = "Start" ];then 
         ENDTIME_=`date '+%Y-%m-%d %H:%M:%S'`
         getduration "${STARTTIME_}" "${ENDTIME_}"

         if [ "${DURATION_}"0 -lt "${TIME4TASKEXECUTION}"0 ]; then  
            continue
         else
            echo "Sorry, no more waiting." "${TASKNAME_}," " TIME_Execute = ""${DURATION_}"", TIME4TASKEXECUTION ="${TIME4TASKEXECUTION}"." >>${FILE_TASKEXECSUMMARY}
            echo "Sorry, no more waiting." "${TASKNAME_}," " TIME_Execute = ""${DURATION_}"", TIME4TASKEXECUTION ="${TIME4TASKEXECUTION}"." >>${FILE_TASKEXECLOG}
            echo ${STARTTIME_},${ENDTIME_},${TASKNAME_},${DURATION_},${SQL_RET_STR},${VAR_STR} >>${FILE_TASKEXECSUMMARY}
            break
         fi
      fi 
   done
   
   #pacing
   sleep 10  
done
set -x
