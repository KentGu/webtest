#!/bin/bash


#set the target installation path
FIRMAMENT_BASE=/export/home/test/Colline2009DB_EAPCP07
BIN_DIR=${FIRMAMENT_BASE}/bin
export MONITOR_DIR=/export/home/test/Colline2009DB/bin/Monitor
DIR_TASKFILE="/data/dbmargin"

#kill the old
PID=`ps -ef |grep "manualtask" |grep -v "grep" |grep -v "task_manualtask" |awk '{print $2}'`;
echo ${PID}

for ITEM in ${PID}
do

  [ X${ITEM} = X ] || kill -9 ${ITEM};

done

#get collie version
CUR_DIR=`pwd`
cd ${BIN_DIR}
COLLINE_VERSION=`cat env.sh |grep 'export PROFILE_NAME=[0-9a-zA-Z]' |awk -F= '{print$2}'`
cd ${CUR_DIR}

#check feed file
DIR_TASKFILE_VERSION="/data/"${COLLINE_VERSION}

if [ -d ${DIR_TASKFILE_VERSION} ]; then
  cp -r ${DIR_TASKFILE} ${DIR_TASKFILE}_backup
  rm -r ${DIR_TASKFILE}
  cp -r ${DIR_TASKFILE_VERSION} ${DIR_TASKFILE}
  echo "Copy folder "${DIR_TASKFILE_VERSION} "to "${DIR_TASKFILE}>>${FILE_TASKEXECSUMMARY}
else
  echo "No feed folder related to current version. If choose continue,it will use the defalt one."
  echo "Continue(Y/N)?"
  read IF_CONTINUE
  if [ ${IF_CONTINUE} = "Y"  -o  ${IF_CONTINUE} = "y" ]; then
    echo "Use floder "${DIR_TASKFILE}
  else
    exit
  fi

fi


nohup ${MONITOR_DIR}/Baseline/Task/manualtask.sh 2>&1 1>/dev/null &
