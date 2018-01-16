#!/bin/bash
###DESCRIPTION: test can connection to the database or not  
  #INPUT: database connection   
  #OUTPUT:0 if succeed, 1 if failed 
testdbconnection(){

   while [ ! X$1 = X ]
   do
     case $1 in
        -dbuser) shift
                 DB_USER_=$1
                 shift
        ;;
        -dbpassword) 
                 shift
                 DB_PASSWD_=$1
                 shift
        ;;
        -dbserver) 
                 shift
                 DB_SERVER_=$1
                 shift
        ;;
        -sid)    shift 
                 DB_SID_=$1
                 shift
        ;;
        *)      echo 5: 
                 return 1
        ;;
     esac
   done


   STMT_RET_=-1
   STMT_="select 3 as num from dual"
   STMT_RET_=` printf  "${STMT_};\n
              commit;\n
              quit;" |\
              sqlplus ${DB_USER_}/${DB_PASSWD_}@\"//${DB_SERVER_}/${DB_SID_}\" |\
              awk '{if (NF==1 && $1~/^[0-9]+$/) print$1}'`
  
   if [ ${STMT_RET_} -eq 3 ];then
      return 0
   else 
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
   STMTFILE_="./stmtfile"

   while [ ! X$1 = X ]
   do
     case $1 in
        -dbuser) shift
                 DB_USER_=$1
                 shift
        ;;
        -dbpassword)
                 shift
                 DB_PASSWD_=$1
                 shift
        ;;
        -dbserver)
                 shift
                 DB_SERVER_=$1
                 shift
        ;;
        -sid)    shift
                 DB_SID_=$1
                 shift
        ;;
        -stmt)   shift
                 STMT_=$1
                 shift 
        ;;
        *) STMT_=${STMT_}" "$1 
                  shift
        ;;
     esac
   done


   [ -f ${STMTFILE_} ] && rm ${STMTFILE_}  
   printf  "${STMT_};\n
     commit;\n
     quit;" |\
     sqlplus ${DB_USER_}/${DB_PASSWD_}@\"//${DB_SERVER_}/${DB_SID_}\" >${STMTFILE_}

   #detect error     
   ERROR=""
   STMT_RET_=""
   ERROR_=`cat ${STMTFILE_} |grep -i error |awk '{print$1}'`
   [ ! ${ERROR_}X = X ] && export STMT_RET_=-1 && return 1

   #get the row affected by delete or select or update 
   KEYWORD_=`echo ${STMT_} |awk '{print$1}'`
   case ${KEYWORD_} in
      Update|update) export STMT_RET_=`cat ${STMTFILE_} |grep 'updated.' |awk '{print $1}'` 
      ;;
      Select|select) export STMT_RET_=`cat ${STMTFILE_} |awk '{if (NF==1 && $1~/^[0-9]+$/) print$1}'` 
      ;;
      Delete|delete) export STMT_RET_=`cat ${STMTFILE_} |grep 'deleted.' |awk '{print $1}'`
      ;; 
      #Call|call)  `cat ${STMTFILE_} |grep -i 'call completed'` || return 1
      #;;
      *) export STMT_RET_=-1
      
   esac
   return 0
  
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

    IFS=$'\n'
    for LINE in `cat ${SQL_FILE}`
	do
	
	   ACTION=`echo ${LINE} |awk -F# '{print$1}'`
	   ACTION=${STATUS}_${ACTION}
	
	   case ${ACTION} in
	   pre_pre)
	            STMT=`echo ${LINE} |awk -F# '{print$3}'`
	            VAR=`echo ${LINE} |awk -F# '{print$2}'`
	            exesql ${STMT}
	            echo ${VAR} ${STMT_RET} >>${VAR_FILE}

	   ;;
	   post_post)
	            STMT=`echo ${LINE} |awk -F# '{print$3}'`
	            VAR=`echo ${LINE} |awk -F# '{print$2}'`
	            exesql ${STMT}
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
