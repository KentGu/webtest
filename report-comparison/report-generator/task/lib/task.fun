#!/bin/bash

# DESCRIPTION: use coltaskmanager script to trigger task
# INPUT:
#    $1(Mandatory) - aboslute path of coltaskmanager shell script
#    $2(Mandatory) - task group
#    $3(Mandatory) - task name
#    $4(Mandatory) - pre script path
#    $5(Mandatory) - post script path
#    $6(Mandatory) - ouput path
#    $7 - output file
# OUTPUT:
#    none

trigger_task() {
	COLTASKMANAGER_SHELL=$1
	TASK_GROUP=$2
	TASK_NAME=$3
	PRE_OPTION=$4
	POST_OPTION=$5
	OUTPUT_OPTION=$6
	OUTPUT_FILE=$7
	
	COLTASKMANAGER_SHELL_DEST=`dirname ${COLTASKMANAGER_SHELL}`
	COLTASKMANAGER_SHELL_SCRIPT=`basename ${COLTASKMANAGER_SHELL}`
	
	pushd ${COLTASKMANAGER_SHELL_DEST} > /dev/null
	if [ "${OUTPUT_FILE}""X" = "X" ]; then
		nohup sh ${COLTASKMANAGER_SHELL_SCRIPT} -tg "${TASK_GROUP}" -tn "${TASK_NAME}" -pre "${PRE_OPTION}" -post "${POST_OPTION}" -m2 "${OUTPUT_OPTION}" > /dev/null 2>&1 &
	else
		nohup sh ${COLTASKMANAGER_SHELL_SCRIPT} -tg "${TASK_GROUP}" -tn "${TASK_NAME}" -pre "${PRE_OPTION}" -post "${POST_OPTION}" -m2 "${OUTPUT_OPTION}" >> ${OUTPUT_FILE} 2>&1 &
	fi
	popd > /dev/null
}

# DESCRIPTION: check if pre.properties(via pre.sh) generated
# INPUT:
#    $1(Mandatory) - aboslute path of pre.properties would be generated at
# OUTPUT:
#    0 - true
#    1 - false

check_task_started() {
	CUR_TASK_TMP_DIR=$1
	
	for((i=0;i<12;i++))
	do
		[ -f ${CUR_TASK_TMP_DIR}/pre.properties ] && return 0
		sleep 5
	done
	
	return 1
}

# DESCRIPTION: check if post.properties(via post.sh) generated
# INPUT:
#    $1(Mandatory) - aboslute path of post.properties would be generated at
# OUTPUT:
#    0 - true
#    1 - false

check_task_ended() {
	CUR_TASK_TMP_DIR=$1
	TIME_OUT=$2
	
	((TIMES=${TIME_OUT}/5))
	
	for((i=0;i<${TIMES};i++))
	do
		[ -f ${CUR_TASK_TMP_DIR}/post.properties ] && return 0
		sleep 5
	done
	
	return 1
}

# DESCRIPTION: get task runtime process id
# INPUT:
#    $1(Mandatory) - task name
# OUTPUT:
#    task runtime process id

get_task_process_id() {
	TASK_NAME=$1
	
	ID=`ps -ef | grep -v coltaskmanager | grep -v grep | grep "${TASK_NAME}" | awk '{print $2}'`
	
	if [ "${ID}""X" = "X" ]; then
		echo 1
	else
		echo ${ID}
	fi
}