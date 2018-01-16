#!/bin/bash

# DESCRIPTION: this is for report compare
# AUTHOR: tiny yin
# VERSION: 1.0
# DATE: 2017-05-18

# get current directory as base directory
export BASE_DIR=`pwd`

# load configuration
. ${BASE_DIR}/config.ini

# load functions
. ${BASE_DIR}/lib/common.fun
. ${BASE_DIR}/lib/db.fun

# get parameters
ACTION=$1
FILE_OUTPUT_PATH=$2
PRAM_NUM=$#
[ ${PRAM_NUM} -gt 2 ] && echo "ERROR: parameters number should not be greater than 2, please use -h option to see the usage." && exit 1

# usage
usage(){
echo "startUp.sh(1)                                                                Command Usage"
echo ""
echo -e "\033[1mNAME\033[0m"
echo "       startUp.sh - try to run out all reports via coltaskmanager for report compare"
echo "                    (additional functionanity: 1. extract timestamp of report output files name; 2. unzip files)"
echo ""
echo -e "\033[1mSYNOPSIS\033[0m"
echo -e "       \033[1mstartUp.sh  \033[0m\033[4mOPTIONS\033[0m  [\033[4mPARAMETERS\033[0m]  (recommend to use nohup to run this)"
echo ""
echo -e "\033[1mDESCRIPTION\033[0m"
echo "       You can use this shell to run out all report templates listed in special list, before that, also you can do some "
echo "       additional operations to these reports output files, such as extract timestamp or unzip files."
echo -e "       \033[41;37mATTENTION: please make sure no duplicate result after extract/unzip, or it will be overwritten!\033[0m"
echo ""
echo "       Mandatory options and optional parameters below."
echo ""
echo -e "\033[1m       -h, --help\033[0m"
echo ""
echo "              Display the usage."
echo ""
echo -e "\033[1m       -s, --start\033[0m"
echo ""
echo "              Start to run out all reports files."
echo ""
echo -e "\033[1m       -e, --extract\033[0m  files_path"
echo ""
echo "              Extract timestamp of report output files name."
echo ""
echo -e "\033[1m       -u, --unzip\033[0m  files_path"
echo ""
echo "              Unzip files."
echo ""
echo ""
echo -e "\033[1mAUTHOR\033[0m"
echo "       Written by Tiny Yin"
echo ""
echo ""
echo "                                   May 2017"
echo ""
}

# distribute function
case ${ACTION} in
	"-h" | "--help")
	usage
	exit 0
	;;
	"-s" | "--start")
	;;
	"-e" | "--extract")
	extract ${FILE_OUTPUT_PATH}
	exit 0
	;;
	"-u" | "--unzip")
	unzip_files ${FILE_OUTPUT_PATH}
	exit 0
	;;
	*)
	usage
	exit 0
	;;
esac

# get current time as base time
export CUR_TIME=`date '+%Y-%m-%d %H:%M:%S'`
# get current time as base postfix
export CUR_TIME_POSTFIX=`date '+%Y%m%d%H%M%S'`

# export oracle client tool to PATH to use sqlplus
export LD_LIBRARY_PATH=${BASE_DIR}/tool/instantclient_10_2
export PATH=${PATH}:${LD_LIBRARY_PATH}

# init
# create directory
[ ! -d ${BASE_DIR}/log ] && mkdir ${BASE_DIR}/log
[ ! -d ${BASE_DIR}/log/back ] && mkdir ${BASE_DIR}/log/back
[ ! -d ${BASE_DIR}/tmp ] && mkdir ${BASE_DIR}/tmp
export CUR_TMP_DIR=${BASE_DIR}/tmp/tmp_${CUR_TIME_POSTFIX}
[ ! -d ${CUR_TMP_DIR} ] && mkdir ${CUR_TMP_DIR}
export RESULT_DIR=${BASE_DIR}/result
[ ! -d ${RESULT_DIR} ] && mkdir ${RESULT_DIR}
[ ! -d ${BASE_DIR}/result ] && mkdir ${BASE_DIR}/result
[ ! -d ${BASE_DIR}/result/back ] && mkdir ${BASE_DIR}/result/back

# backup previous files
ls -l ${BASE_DIR}/log | grep -E "*.log" | awk '{print $NF}' | xargs -i mv ${BASE_DIR}/log/{} ${BASE_DIR}/log/back
ls -l ${BASE_DIR}/log | grep -E "*.csv" | awk '{print $NF}' | xargs -i mv ${BASE_DIR}/log/{} ${BASE_DIR}/log/back
ls -l ${BASE_DIR}/result | grep -E "result_*" | awk '{print $NF}' | xargs -i mv ${BASE_DIR}/result/{} ${BASE_DIR}/result/back

# create result destination
export CUR_RESULT_DIR=${BASE_DIR}/result/result_${CUR_TIME_POSTFIX}
[ ! -d ${CUR_RESULT_DIR} ] && mkdir ${CUR_RESULT_DIR}

# create log files
export SERVER_LOG=${BASE_DIR}/log/server_${CUR_TIME_POSTFIX}.log
export RESULT_LOG=${BASE_DIR}/log/result_${CUR_TIME_POSTFIX}.log
export RESULT_CSV=${BASE_DIR}/log/result_${CUR_TIME_POSTFIX}.csv
touch ${SERVER_LOG}
touch ${RESULT_LOG}
touch ${RESULT_CSV}

logger_info "start"
logger_info "task started time at ${CUR_TIME}"

sleep 1
# check environment
logger_info "start to check environment?"
# check app launched
GREP_COLLINE_PROCESS=`ps -ef | grep java | grep jboss`
if [ "${GREP_COLLINE_PROCESS}""X" = "X" ]; then
	logger_error "colline is not started, please check!" && exit 1
else
	logger_info "colline is launched"
	JBOSS_HOME_DIR=`echo ${GREP_COLLINE_PROCESS} | awk '{for(i=1;i<=NF;i++) printf("%s\n",$i)}' | grep jboss.home.dir | awk -F'=' '{print $2}' | sed -n 1p`
	export COLLINE_BASE_DIR=`dirname ${JBOSS_HOME_DIR}`
	logger_info "colline base directory is \""${COLLINE_BASE_DIR}"\""
fi

sleep 1
# check database
logger_info "start to check database?"
logger_info "oracle database info: DB_USER_NAME=${DB_USER_NAME}, DB_USER_PASS=${DB_USER_PASS}, DB_IP=${DB_IP}, DB_SID=${DB_SID}"
check_db_connection ${DB_USER_NAME} ${DB_USER_PASS} ${DB_IP} ${DB_SID}
if [ $? -eq 0 ]; then
	logger_info "oracle database can be connected successfully"
else
	logger_error "oracle database cannot be connected, please check!"
	logger_error "error code: ${ERROR_CODE}"
	exit 1
fi

logger_info "start to clean database"
del_tables ${DB_USER_NAME} ${DB_USER_PASS} ${DB_IP} ${DB_SID} ${CLEAN_TABLE_NAMES}
if [ $? -eq 0 ]; then
	logger_info "clean oracle database successfully"
else
	logger_error "clean oracle database failed!"
	logger_error "error code: ${ERROR_CODE}"
	exit 1
fi

sleep 1
# start main function
TASK_FILE_LIST=${BASE_DIR}/list/${TASK_FILE_LIST}
sh ${BASE_DIR}/src/controller.sh ${TASK_FILE_LIST}

sleep 1

logger_info "compress result file result_${CUR_TIME_POSTFIX}.zip under ${BASE_DIR}"
export RESULT_ZIP_DIR=${BASE_DIR}/result_${CUR_TIME_POSTFIX}
[ ! -d ${RESULT_ZIP_DIR} ] && mkdir ${RESULT_ZIP_DIR} && mkdir ${RESULT_ZIP_DIR}/result
cp -r ${CUR_RESULT_DIR}/* ${RESULT_ZIP_DIR}/result
cp ${SERVER_LOG} ${RESULT_ZIP_DIR}
cp ${RESULT_LOG} ${RESULT_ZIP_DIR}
cp ${RESULT_CSV} ${RESULT_ZIP_DIR}
zip -r ${RESULT_ZIP_DIR}.zip result_${CUR_TIME_POSTFIX} > /dev/null && rm -r ${RESULT_ZIP_DIR}

logger_info "end"