#!/bin/bash

. ./sql.fun
DB_NAME=u0608
DB_PW=creditderivative
DB_SERVER=172.20.30.17:1521
SID=shacolc
STMT="call proc_Purge_Statment_History(to_date('2010-01-01 00:00:00','yyyy-mm-dd hh24:mi:ss'),4)"

set -x
echo `date`
if testdbconnection  -sid ${SID} -dbuser ${DB_NAME} -dbpassword ${DB_PW} -dbserver ${DB_SERVER} ;then
   echo "test success"
   if exesql -dbuser ${DB_NAME} -dbpassword ${DB_PW} -dbserver ${DB_SERVER} -sid ${SID} -stmt ${STMT} ; then
      echo "call completed" 
   else
      echo "failed to execute sql"
   fi 

else
   echo "failed to connection"

fi
