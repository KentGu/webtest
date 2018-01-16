#!/bin/bash
###DESCRIPTION: test can connection to the database or not  
  #INPUT: database connection   
  #OUTPUT:0 if suceed, 1 if failed 
testdbconnection(){
   DB_USER_=$1
   DB_PASSWD_=$2
   DB_SERVER_=$3
   S_ID_=$4
   STMT_RET_=-1

   STMT_="select 3 as num3 from  dual"
   STMT_RET_=` printf  "${STMT_};\n
              commit;\n
              quit;" |\
              sqlplus ${DB_USER_}/${DB_PASSWD_}@\"//${DB_SERVER_}/${S_ID_}\" |\
              awk '{if (NF==1 && $1~/^[0-9]+$/) print$1}'`
  # if [ ${STMT_RET_} -eq 3 ];then
   if [ ${STMT_RET_}X = 3X ];then 
      echo 0
      return 0
   else 
      echo 1
      return 1
   fi 

}

###DESCRIPTION: execute sql specify statement, and return STMT_RET which indicates the rows affected by the SQL
  #INPUT: sql statement 
      #'select': should only rerurn a numerical value, like 'select count(*) from table'
      #'update': 
      #'delete': 
  #OUTPUT: globle variable STMT_RET
exesql(){
   STMTFILE="./stmtfile"
set +x 
   STMT=$1
   DB_USER_=$2
   DB_PASSWD_=$3
   DB_SERVER_=$4
   S_ID_=$5


   [ -f ${STMTFILE} ] && rm ${STMTFILE}  


   printf  "${STMT};\n
     commit;\n
     quit;" |\
    # sqlplus baselinetest_u0510b/creditderivative@\"//172.20.30.13:1521/shacolb\" >${STMTFILE}
      sqlplus ${DB_USER_}/${DB_PASSWD_}@\"//${DB_SERVER_}/${S_ID_}\" >${STMTFILE}
   #detect error     
   ERROR=""
   STMT_RET=""
   IFS=' ' 
   ERROR=`cat ${STMTFILE} |grep -i error |awk '{print$1}'`
   [ ! ${ERROR}X = X ] && export STMT_RET=-1 && return 1

   #get the row affected by delete or select or update 
   KEYWORD=`echo ${STMT} |awk '{print$1}'`
   case ${KEYWORD} in
      Update|update) export STMT_RET=`cat ${STMTFILE} |grep 'updated.' |awk '{print $1}'` 
      ;;
      Select|select) export STMT_RET=`cat ${STMTFILE} |awk '{if (NF==1 && $1~/^[0-9]+$/) print$1}'` 
      ;;
      Delete|delete) export STMT_RET=`cat ${STMTFILE} |grep 'deleted.' |awk '{print $1}'`
      ;;
      *) export STMT_RET=-1
      
   esac
  
}

###DESCRIPTION: get the values of variable and validate the result 
#   #INPUT:      
#                      SQLFILE: validation file which specify the prescripts, postscripts,validations
#                       STATUS: indicate the pre or post
#                     VAR_FILE: specify the tmp file for storing parameter and values  
#                 SQL_RET_FILE: speficy the result file to store validation results
#   #OUTPUT:
#                 SQL_RET_FILE: validates results
           
sql_validation(){
#set -x
    SQL_FILE=$1
    STATUS=$2
    VAR_FILE=$3
    SQL_RET_FILE=$4	
    DB_USER_=$5
    DB_PASSWD_=$6
    DB_SERVER_=$7
    S_ID_=$8


    IFS=$'\n'
    for LINE in `cat ${SQL_FILE}`
	do
	
	   ACTION=`echo ${LINE} |awk -F# '{print$1}'`
	   ACTION=${STATUS}_${ACTION}
	
	   case ${ACTION} in
	   pre_pre)
	            STMT=`echo ${LINE} |awk -F# '{print$3}'`
	            VAR=`echo ${LINE} |awk -F# '{print$2}'`
	            exesql ${STMT} ${DB_USER_} ${DB_PASSWD_} ${DB_SERVER_} ${S_ID_}
	            echo ${VAR} ${STMT_RET} >>${VAR_FILE}

	   ;;
	   post_post)
	            STMT=`echo ${LINE} |awk -F# '{print$3}'`
	            VAR=`echo ${LINE} |awk -F# '{print$2}'`
	            exesql ${STMT} ${DB_USER_} ${DB_PASSWD_} ${DB_SERVER_} ${S_ID_}
	            echo ${VAR} ${STMT_RET} >>${VAR_FILE}
	
	   ;;
	   post_val)
	           IFS=$' \n'
	           while read VAR VALUE
	           do
	             eval `echo ${VAR}=${VALUE}`
                     [ ${VALUE} -eq -1 ] && echo "${VAR}=-1;" >>${SQL_RET_FILE}
	
	           done<${VAR_FILE}
	           IFS=$'\n'

                   C_NUM=`echo ${LINE} |awk -F# '{print $2}'`	
	           CONDITION=`echo ${LINE}  |awk -F# '{print $3}'`
	           eval test ${CONDITION} && echo "No${C_NUM}=Pass" >>${SQL_RET_FILE} 
	           eval test ${CONDITION} || echo "No${C_NUM}=Fail" >>${SQL_RET_FILE} 
	
	   ;;
	   *)
	
	
	   ;;
	   esac
	
	
	done
#set +x

}



#######################################################################
# DESCRIPTION: Input SQL and output Select Result
#       INPUT:            SELECT_SQL: Select SQL
#      OUTPUT:       SQL_DESCRIPTION: Select Results
#
#######################################################################
sql_select(){

DB_USER=$1
DB_PASSWD=$2
DB_SERBER=$3
S_ID=$4
SELECT_SQL=$5
SQL_DESCRIPTION=$6

#sql select, connect database with silence mode
#VALUE=`sqlplus -S ${DB_USER}/${DB_PASSWD}@//${DB_SERVER}/${S_ID}  <<EOF
VALUE=`sqlplus -S ${DB_USER}/${DB_PASSWD}@//${DB_SERVER}/${S_ID}  <<EOF

--set heading off feedback off pagesize 0 verify off echo off
 ${SELECT_SQL}
--select count(1) from COLSTATEMENTSTATUS where statusid in (68);
commit
EOF`

#output description and select result
#echo "The number of rows is" $VALUE
echo "${SQL_DESCRIPTION}" "$VALUE"

}
