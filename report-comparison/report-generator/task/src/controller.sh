#!/bin/bash

# DESCRIPTION: main function to run task
# AUTHOR: tiny yin
# VERSION: 1.0
# DATE: 2017-05-18

# load configuration
. ${BASE_DIR}/config.ini

# load functions
. ${BASE_DIR}/lib/common.fun
. ${BASE_DIR}/lib/db.fun
. ${BASE_DIR}/lib/task.fun

TASK_FILE_LIST=$1

logger_info "start to run task list: \"${TASK_FILE_LIST}\""
echo "no,groupName,taskName,reportOutputName,elapseTime(s),comment" > ${RESULT_CSV}

if [ -d ${COLLINE_BASE_DIR}/jboss/standalone ]; then
	COLTASKMANAGER_RUN_LEVEL=2
else
	COLTASKMANAGER_RUN_LEVEL=1
fi

CUR_TASK_NO=0

#retrieve the tasklist, trigger task
cat ${TASK_FILE_LIST} | while read LINE
do
	FLAG=`echo ${LINE} | awk -F: '{print $1}'`
	TASK_GROUP=`echo ${LINE} | awk -F: '{print $2}'`
	TASK_NAME=`echo ${LINE} | awk -F: '{print $3}'`
	TIME_OUT=`echo ${LINE} | awk -F: '{print $4}'`
	USER_NAME=`echo ${LINE} | awk -F: '{print $5}'`
	USER_PASS=`echo ${LINE} | awk -F: '{print $6}'`
	
	((CUR_TASK_NO++))
	
	case ${COLTASKMANAGER_RUN_LEVEL} in
		1)
		cp ${BASE_DIR}/src/coltaskmanager_base.sh ${COLLINE_BASE_DIR}/bin
		COLTASKMANAGER_SHELL=${COLLINE_BASE_DIR}/bin/coltaskmanager_base.sh
		;;
		2)
		cp ${BASE_DIR}/src/coltaskmanager2_base.sh ${COLLINE_BASE_DIR}/bin/utility
		COLTASKMANAGER_SHELL=${COLLINE_BASE_DIR}/bin/utility/coltaskmanager2_base.sh
		;;
		*)
		;;
	esac
	
	chmod 777 ${COLTASKMANAGER_SHELL}
	export COLTASKMANAGER_SHELL
	
	COLLINE_BASE_DIR_=`echo ${COLLINE_BASE_DIR} | awk 'gsub(/\//,"\\\/",$1)'`
	sed -i 's/\${COLLINE_BASE_DIR}/'${COLLINE_BASE_DIR_}'/g' ${COLTASKMANAGER_SHELL}
	sed -i 's/127.0.0.1/'${COL_SERVER_IP}'/g' ${COLTASKMANAGER_SHELL}
	sed -i 's/=sa/='${USER_NAME}'/g' ${COLTASKMANAGER_SHELL}
	sed -i 's/=password/='${USER_PASS}'/g' ${COLTASKMANAGER_SHELL}
	
	sleep 1
	
	logger_info "${CUR_TASK_NO}. start to run '${TASK_GROUP} - ${TASK_NAME}'"
	logger_info "${CUR_TASK_NO}. start to run '${TASK_GROUP} - ${TASK_NAME}'" ${RESULT_LOG}
	
	[ ! ${FLAG} -eq 0 ] && logger_warn ">>> task flag is not 0, will ignore this task" && echo "${CUR_TASK_NO},${TASK_GROUP},${TASK_NAME},N/A,N/A,no need to run" >> ${RESULT_CSV} \
	&& logger_info "${CUR_TASK_NO}. end\r\n\r\n" ${RESULT_LOG} && continue
	
	TASK_NAME_=`echo ${TASK_NAME} | sed 's/ //g'`
	CUR_TASK_TIME_POSTFIX=`date '+%Y%m%d%H%M%S'`
	CUR_TASK_TMP_DIR=${CUR_TMP_DIR}/${TASK_NAME_}_${CUR_TASK_TIME_POSTFIX} && mkdir ${CUR_TASK_TMP_DIR} && export CUR_TASK_TMP_DIR
	
	cp ${BASE_DIR}/src/pre.sh ${CUR_TASK_TMP_DIR}
	cp ${BASE_DIR}/src/post.sh ${CUR_TASK_TMP_DIR}
	
	chmod 777 ${CUR_TASK_TMP_DIR}/pre.sh
	chmod 777 ${CUR_TASK_TMP_DIR}/post.sh
	
	CUR_TASK_TMP_DIR_=`echo ${CUR_TASK_TMP_DIR} | awk 'gsub(/\//,"\\\/",$1)'`
	sed -i 's/\${CUR_TASK_TMP_DIR}/'${CUR_TASK_TMP_DIR_}'/g' ${CUR_TASK_TMP_DIR}/pre.sh
	sed -i 's/\${CUR_TASK_TMP_DIR}/'${CUR_TASK_TMP_DIR_}'/g' ${CUR_TASK_TMP_DIR}/post.sh
	
	sleep 1
	
	logger_info ">>> start to trigger task"
	trigger_task ${COLTASKMANAGER_SHELL} "${TASK_GROUP}" "${TASK_NAME}" ${CUR_TASK_TMP_DIR}/pre.sh ${CUR_TASK_TMP_DIR}/post.sh ${RESULT_DIR} ${RESULT_LOG}
	
	sleep 1
	
	logger_info ">>> check if task is triggered"
	check_task_started ${CUR_TASK_TMP_DIR}
	
	if [ $? -eq 0 ]; then
		logger_info ">>> task is started"
		TASK_PROCESS_ID=`get_task_process_id ${TASK_NAME}`
		logger_info ">>> task process id is: ${TASK_PROCESS_ID}"
	else
		logger_error ">>> after 60 seconds, task is not triggered, will kill it"
		TASK_PROCESS_ID=`get_task_process_id ${TASK_NAME}`
		[ ! ${TASK_PROCESS_ID} -eq 1 ] && kill -9 ${TASK_PROCESS_ID}
		del_tables ${DB_USER_NAME} ${DB_USER_PASS} ${DB_IP} ${DB_SID} ${CLEAN_TABLE_NAMES}
		echo "${CUR_TASK_NO},${TASK_GROUP},${TASK_NAME},N/A,N/A,not start" >> ${RESULT_CSV}
		logger_info "${CUR_TASK_NO}. end\r\n\r\n" ${RESULT_LOG}
		continue
	fi
	
	logger_info ">>> start to check if task ended within timeout - ${TIME_OUT}s"
	
	check_task_ended ${CUR_TASK_TMP_DIR} ${TIME_OUT}
	
	if [ $? -eq 0 ]; then
		logger_info ">>> task is ended"
		START_TIME=`cat ${CUR_TASK_TMP_DIR}/pre.properties`
		END_TIME=`cat ${CUR_TASK_TMP_DIR}/post.properties`
		# as some linux distribution not support command bc, so use awk instead of calc ${ELAPSE_TIME}
		#ELAPSE_TIME=$(echo "${END_TIME} - ${START_TIME}" | bc)
		ELAPSE_TIME=$(awk 'BEGIN{a='${END_TIME}' - '${START_TIME}'; print a}')
		REPORT_OUTPUT_NAME=`ls -lt ${RESULT_DIR} | grep "^-" | awk '{print $NF}' | sed -n 1p`
		echo "${CUR_TASK_NO},${TASK_GROUP},${TASK_NAME},${REPORT_OUTPUT_NAME},${ELAPSE_TIME},succeed" >> ${RESULT_CSV}
		[ ! "${REPORT_OUTPUT_NAME}""X" = "X" ] && mv ${RESULT_DIR}/${REPORT_OUTPUT_NAME} ${CUR_RESULT_DIR}
	else
		logger_error ">>> after ${TIME_OUT} seconds, task is not end, will mark as TIMEOUT"
		[ ! ${TASK_PROCESS_ID}"X" = "X" ] && kill -9 ${TASK_PROCESS_ID}
		del_tables ${DB_USER_NAME} ${DB_USER_PASS} ${DB_IP} ${DB_SID} ${CLEAN_TABLE_NAMES}
		echo "${CUR_TASK_NO},${TASK_GROUP},${TASK_NAME},N/A,${TIME_OUT},timeout" >> ${RESULT_CSV}
		logger_info "${CUR_TASK_NO}. end\r\n\r\n" ${RESULT_LOG}
		continue
	fi
	
	logger_info "${CUR_TASK_NO}. end\r\n\r\n" ${RESULT_LOG}
	
	sleep 1
done
