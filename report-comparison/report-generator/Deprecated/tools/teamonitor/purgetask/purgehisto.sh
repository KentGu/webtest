#!/bin/bash

#config current dir
CURDIR=/home/test/Builds/Colline2009DB/bin/purgetask

#config database info
DBUSER=u0608
DBPASSWORD=creditderivative
DBSERVER=172.20.30.17:1521
SID=shacolc
PURGEDATES=5


#config sqlplus dir
PATH=/home/test/instantclient_10_2:${PATH}
LD_LIBRARY_PATH=/home/test/instantclient_10_2:${LD_LIBRARY_PATH}
export PATH LD_LIBRARY_PATH


. ${CURDIR}/lib/sql.fun
. ${CURDIR}/lib/date.fun
LOGFILE=scriptexe.log
PURGEDATES=`expr ${PURGEDATES} \* 86400`
CURTIME=`date '+%Y-%m-%d %H:%M:%S'`
TIMESTAMP=$(date2stamp ${CURTIME})
TIMESTAMP=`expr ${TIMESTAMP} - ${PURGEDATES}`
PURGETIME=$(stamp2date ${TIMESTAMP})

LOGFILE=${CURDIR}/script.log
STMT="call proc_Purge_Statment_History(to_date('${PURGETIME}','yyyy-mm-dd hh24:mi:ss'),4)"


if testdbconnection -dbuser ${DBUSER} -dbpassword ${DBPASSWORD} -dbserver ${DBSERVER} -sid ${SID};then
   echo `date` 'test connection success' >>${LOGFILE} 

   if exesql  -dbuser ${DBUSER} -dbpassword ${DBPASSWORD} -dbserver ${DBSERVER} -sid ${SID} -stmt ${STMT};then
      echo `date` 'sql execute success' >>${LOGFILE}
   else
      echo `date` 'sql execute failed' >>${LOGFILE}
   fi

else
   echo `date` 'test connection failed' >>${LOGFILE}
fi
