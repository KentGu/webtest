######### Baseline Task Trigger ###########
triggertask(){
   CURPATH=`pwd`
   AVG=$1
   BIN_DIR=$2
   FILE_TASKEXECLOG=$3
   myUsername=$4
   myPasseord="password"


   cd ${BIN_DIR}

   echo "Task is "${AVG} >> ${CURPATH}/${FILE_TASKEXECLOG}
   echo `date '+%Y-%m-%d %H:%M:%S'` >> ${CURPATH}/${FILE_TASKEXECLOG}

  # nohup ./coltaskmanager.sh "${AVG}" ${myUsername} ${myPassword} >${CURPATH}/TMP_LOG 2>&1 & 
   nohup ./coltaskmanager_Baseline.sh "${AVG}" ${myUsername} ${myPassword} >>${CURPATH}/${FILE_TASKEXECLOG} 2>&1 &
  # ./coltaskmanager_Baseline.sh "${AVG}" ${myUsername} ${myPassword} >>${CURPATH}/${FILE_TASKEXECLOG}  #For debug
   #echo "--------------------------------dd------"
   #aa=${CURPATH}/TMP_LOG
   #cat ${CURPATH}/TMP_LOG>>${CURPATH}/${FILE_TASKEXECLOG}
   #echo "--------------end---------------------"
   #`cat ${aa}`>>${CURPATH}/${FILE_TASKEXECLOG}
   #echo "---------------------00--"
   #TASKLOG=`cat ${CURPATH}/TMP_LOG` 
   #TASKLOG=`cat ${aa}`
   
   export TASKLOG    
   cd ${CURPATH} 
}

gettaskstatus(){

  LINE=`cat ${MONITOR_DIR}/TaskScript/TaskStatusFlag`
  echo "----------------TaskStatusFlag=""${LINE}""-------" 
#  export  _EXECUTETIME=`echo ${LINE} |awk -F: -v OFS=':' '{print$1,$2,$3}'`  
  export _EXECUTETIME=`echo ${LINE} |awk -F: -v OFS=':' '{print$6}'`
  export  _TASKNAME=`echo ${LINE} |awk -F: '{print$4}'`
  export  _TASKSTATUS=`echo ${LINE} |awk -F: '{print$5}'`
}

eraseprevioustask(){
  echo ":::Default:Default" >${MONITOR_DIR}/TaskScript/TaskStatusFlag

}


#### Loadtest Task Trigger #####
istasktriggered(){
   local AVG=$1
   local BIN_DIR=$2
   local FILE_TASKEXECLOG=$3
   local CURPATH=`pwd`

   cd ${BIN_DIR};

   echo "" >>${FILE_TASKEXECLOG}
   echo `date '+%Y-%m-%d %H:%M:%S'` >>${FILE_TASKEXECLOG}

   local ittRET_=`(./coltaskmanager.sh ${AVG} )&`
   echo ${ittRET_}>>${FILE_TASKEXECLOG} 

   cd ${CURPATH};
 
   if ! islinecontainningstring "Error" ${ittRET_} && ! islinecontainningstring "syntax:" ${ittRET_}; then
     return 0 
   else
     return 1
   fi

}
