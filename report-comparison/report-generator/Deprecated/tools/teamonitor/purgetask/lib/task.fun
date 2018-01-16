
triggertask(){
   CURPATH=`pwd`
   AVG=$1
   BIN_DIR=$2
   FILE_TASKEXECLOG=$3

   cd ${BIN_DIR}
   echo "" >> ${CURPATH}/${FILE_TASKEXECLOG}
   echo `date '+%Y-%m-%d %H:%M:%S'` >> ${CURPATH}/${FILE_TASKEXECLOG}
   (nohup ./coltaskmanager.sh ${AVG} >>${CURPATH}/${FILE_TASKEXECLOG} 2>&1) &
   cd ${CURPATH} 

}

gettaskstatus(){

  LINE=`cat ${MONITOR_DIR}/TaskScript/TaskStatusFlag`

  export  _TASKNAME=`echo ${LINE} |awk -F: '{print$1}'`
  export  _TASKSTATUS=`echo ${LINE} |awk -F: '{print$2}'`

}

eraseprevioustask(){

  echo "Default:Default" >${MONITOR_DIR}/TaskScript/TaskStatusFlag

}

