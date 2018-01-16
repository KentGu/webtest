#!/bin/bash

# DESCRIPTION: check if oracle database can be connected
# INPUT:
#    $1(Mandatory) - user name 
#    $2(Mandatory) - user password 
#    $3(Mandatory) - db server ip
#    $4(Mandatory) - db sid
# OUTPUT:
#    0 - true
#    1 - false

check_db_connection() {
	DB_USER_NAME=$1
	DB_USER_PASS=$2
	DB_IP=$3
	DB_SID=$4
	
	DB_STMT="select 1 as result from dual"
	STMT_RESULT=`printf "${DB_STMT};\n
				commit;\n
				quit;" |\
				sqlplus ${DB_USER_NAME}/${DB_USER_PASS}@//${DB_IP}/${DB_SID} |\
				awk '{if (NF==1 && $1~/^[0-9]+$/) print $1}'`
				
	if [ ! ${STMT_RESULT}"X" = "X" ]; then
		[ ${STMT_RESULT} -eq 1 ] && return 0 || return 1
	else
		return 1
	fi
}

# DESCRIPTION: execute statement
# INPUT:
#    $1(Mandatory) - user name 
#    $2(Mandatory) - user password 
#    $3(Mandatory) - db server ip
#    $4(Mandatory) - db sid
#    $5(Mandatory) - statement
# OUTPUT:
#    0 - exec true
#    1 - exec false
#    STMT_RET - result set size, but not pricise for SELECT

exec_sql(){
	DB_USER_NAME=$1
	DB_USER_PASS=$2
	DB_IP=$3
	DB_SID=$4
	shift 4
	DB_STMT=$@
	
	STMT_FILE="./stmtfile"

	printf "${DB_STMT};\n
	commit;\n
	quit;" |\
	sqlplus ${DB_USER_NAME}/${DB_USER_PASS}@//${DB_IP}/${DB_SID} > ${STMT_FILE}
				
	IF_ERROR=`cat ${STMT_FILE} | grep -i error`
	if [ ! "${IF_ERROR}""X" = "X" ]; then
		ERROR_NUMBER=`cat -n ${STMT_FILE} | grep -i error | awk '{print $1}'`
		ERROR_NUMBER=`echo ${ERROR_NUMBER} | awk '{print $1}'`
		((ERROR_NUMBER++))
		export ERROR_CODE=`cat ${STMT_FILE} | sed -n ${ERROR_NUMBER}p`
		rm -f ${STMT_FILE} && return 1
	fi
	
	#get the row affected by delete or select or update 
	KEYWORD=`echo ${DB_STMT} | awk '{print $1}' | tr 'a-z' 'A-Z'`
	case ${KEYWORD} in
		UPDATE)
		export STMT_RET=`cat ${STMT_FILE} | grep 'updated.' | awk '{print $1}'`
		;;
		SELECT)
		export STMT_RET=`cat ${STMT_FILE} | awk '{if (NF==1 && $1~/^[0-9]+$/) print$1}'`
		;;
		DELETE)
		export STMT_RET=`cat ${STMT_FILE} | grep 'deleted.' | awk '{print $1}'`
		;;
		*)
		export STMT_RET=-1
		;;
    esac
	rm -f ${STMT_FILE} && return 0
}

# DESCRIPTION: delete from table
# INPUT:
#    $1(Mandatory) - user name 
#    $2(Mandatory) - user password 
#    $3(Mandatory) - db server ip
#    $4(Mandatory) - db sid
#    $5(Mandatory) - table name 1
#    $6(Mandatory) - table name 2
#    ...
# OUTPUT:
#    0 - exec true
#    1 - exec false

del_tables() {
	DB_USER_NAME=$1
	DB_USER_PASS=$2
	DB_IP=$3
	DB_SID=$4
	RESULT=0
	
	shift 4
	
	while [ ! $# -eq 0 ]
	do
		TABLE_NAME=$1
		DB_STMT="DELETE FROM ${TABLE_NAME}"
		exec_sql ${DB_USER_NAME} ${DB_USER_PASS} ${DB_IP} ${DB_SID} ${DB_STMT}
		if [ ! $? -eq 0 ]; then
			RESULT=1
		fi
		shift
	done
	return ${RESULT}
}