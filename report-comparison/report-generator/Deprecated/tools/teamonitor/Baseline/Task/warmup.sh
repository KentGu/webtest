#/bin/bash
. ./lib/sql.fun  

FILE_VAR=./filevar
FILE_SQL_RET=./filesqlret

for SQLFILE_ in `ls sql`
do 
   SQLFILE_=sql/${SQLFILE_} 
   #execute pre sql statement
   SQL_RET_STR=""
   VAR_STR=""
   [ -f ${FILE_VAR} ] && rm ${FILE_VAR}
   [ -f ${FILE_SQL_RET} ] && rm ${FILE_SQL_RET}
   [ ! ${SQLFILE_} = null ] &&  sql_validation ${SQLFILE_} pre ${FILE_VAR}  ${FILE_SQL_RET}



   [ ! ${SQLFILE_} = null ] &&  sql_validation ${SQLFILE_} post ${FILE_VAR} ${FILE_SQL_RET}

   SQL_RET_STR=`cat ${FILE_SQL_RET} |tr '\n' ''`
   VAR_STR=`cat ${FILE_VAR} |tr -s ' ' '=' |tr '\n' ''`


   echo ${SQLFILE_}: ${SQL_RET_STR}" "${VAR_STR}
done
