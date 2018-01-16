#!/bin/bash

######################################################################################
#For Task Baseline Test
#
#
######################################################################################

#load all the functions
. ${TEA_HOME}/lib/sh/errorhandler.fun
. ${TEA_HOME}/lib/sh/task.fun
. ${TEA_HOME}/lib/sh/sql.fun
. ${TEA_HOME}/lib/sh/getduration.fun
. ${TEA_HOME}/lib/sh/string.fun
. ${TEA_HOME}/lib/sh/date.fun

#debug
#set -x

#set the target installation path
FIRMAMENT_BASE=/export/home/test/Colline2009DB_EAPCP07
BIN_DIR=${FIRMAMENT_BASE}/bin
export MONITOR_DIR=/export/home/test/Colline2009DB/bin/Monitor

#file definition
BASEDIR=/export/home/test/Monitor/Baseline/Task
FILE_TASKEXECSUMMARY=${BASEDIR}/taskexec.summary
FILE_CURRENTTASK=${BASEDIR}/currenttask
FILE_TASKLIST=${BASEDIR}/manualtasklist
FILE_FINISHLIST=${BASEDIR}/filefinishlist
FILE_TASKEXELOG=${BASEDIR}/taskexec.log

logger(){
   echo `date '+%Y-%m-%d %H:%M:%S'` $* >>${FILE_TASKEXELOG};
}


#get collie version
CUR_DIR=`pwd`
cd ${BIN_DIR} 
COLLINE_VERSION=`cat env.sh |grep 'export PROFILE_NAME=[0-9a-zA-Z]' |awk -F= '{print$2}'`
cd ${CUR_DIR}

echo Target Version: ${COLLINE_VERSION} >>${FILE_TASKEXECSUMMARY}
echo Target Path:${BIN_DIR} >>${FILE_TASKEXECSUMMARY}

OLD_DATE=`date '+%d'`
>${FILE_FINISHLIST}
#retrieve the tasklist, trigger task    
IFS=$'\n'

for LINE in `cat ${FILE_TASKLIST}`
do
   ID_=`echo ${LINE} |awk -F: '{print$1}'`
   FLAG_=`echo ${LINE} |awk -F: '{print$2}'`
   FREQUENCY_=`echo ${LINE} |awk -F: '{print$3}'`
   TRRIGGERTIME_=`echo ${LINE} |awk -F: '{print$4}'`
   TASKNAME_=`echo ${LINE} |awk -F: '{print$6}'`
   ARG_=`echo ${LINE} |awk -F: '{print$5,":",$6}'`


   if  isqrtztime ${FREQUENCY_} ${TRRIGGERTIME_} ; then
      echo ${ID_} >>${FILE_FINISHLIST}   
   fi

done

while true 
do
   for LINE in `cat ${FILE_TASKLIST}`
   do
      ID_=`echo ${LINE} |awk -F: '{print$1}'`
      FLAG_=`echo ${LINE} |awk -F: '{print$2}'`
      FREQUENCY_=`echo ${LINE} |awk -F: '{print$3}'`
      TRRIGGERTIME_=`echo ${LINE} |awk -F: '{print$4}'`
      TASKNAME_=`echo ${LINE} |awk -F: '{print$6}'`
      ARG_=`echo ${LINE} |awk -F: '{print$5,":",$6}'`

echo "==============================================windytest======================="
      if  isnewday ${OLD_DATE} ; then
         [ -f ${FILE_FINISHLIST} ] &&  >${FILE_FINISHLIST}
         logger "new date , refresh the finished list"; 
         OLD_DATE=`date '+%d'`;
      fi 

      if [ ${FLAG_} = 1 ]; then
         logger "${TASKNAME_} is unabled";
         continue;
      fi

      if isfilecontainingstring ${ID_} ${FILE_FINISHLIST} ; then
         continue;
      fi 

      if ! isqrtztime ${FREQUENCY_} ${TRRIGGERTIME_} ; then
         continue     
      fi 
     
      logger "trigger task ${TASKNAME_}";  
   
      #trigger task    
      STARTTIME_=`date '+%Y-%m-%d %H:%M:%S'`
      if istasktriggered ${ARG_} ${BIN_DIR} "${FILE_TASKEXELOG}" ;then
         echo ${STARTTIME_},${TASKNAME_},Triggered >>${FILE_TASKEXECSUMMARY} 
         echo ${ID_} >>${FILE_FINISHLIST}
      else
         echo ${STARTTIME_},${TASKNAME_},Not Triggered >>${FILE_TASKEXECSUMMARY} 
         echo ${ID_} >>${FILE_FINISHLIST}
      fi

   done
   sleep 5
done
