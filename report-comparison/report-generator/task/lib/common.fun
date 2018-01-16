#!/bin/bash

# DESCRIPTION: write INFO log to file
# INPUT:
#    $1(Mandatory) - content to wirte 
#    $2 - target log file path (default to ${SERVER_LOG} defined in startUp.sh)
# OUTPUT:
#    none

logger_info() {
	CONTENT=$1
	FILE=$2
	if [ "${CONTENT}""X" != "X" ]; then
		if [ "${FILE}""X" = "X" ]; then
			echo -e `date '+%Y-%m-%d %H:%M:%S'`\\tINFO\\t"${CONTENT}" | tee -a ${SERVER_LOG}
		else
			echo -e `date '+%Y-%m-%d %H:%M:%S'`\\tINFO\\t"${CONTENT}" >> ${FILE}
		fi
	fi
}

# DESCRIPTION: write ERROR log to file
# INPUT:
#    $1(Mandatory) - content to wirte 
#    $2 - target log file path (default to ${SERVER_LOG} defined in startUp.sh)
# OUTPUT:
#    none

logger_error() {
	CONTENT=$1
	FILE=$2
	if [ "${CONTENT}""X" != "X" ]; then
		if [ "${FILE}""X" = "X" ]; then
			echo -e `date '+%Y-%m-%d %H:%M:%S'`\\tERROR\\t"${CONTENT}" | tee -a ${SERVER_LOG}
		else
			echo -e `date '+%Y-%m-%d %H:%M:%S'`\\tERROR\\t"${CONTENT}" >> ${FILE}
		fi
	fi
}

# DESCRIPTION: write WARN log to file
# INPUT:
#    $1(Mandatory) - content to wirte 
#    $2 - target log file path (default to ${SERVER_LOG} defined in startUp.sh)
# OUTPUT:
#    none

logger_warn() {
	CONTENT=$1
	FILE=$2
	if [ "${CONTENT}""X" != "X" ]; then
		if [ "${FILE}""X" = "X" ]; then
			echo -e `date '+%Y-%m-%d %H:%M:%S'`\\tWARN\\t"${CONTENT}" | tee -a ${SERVER_LOG}
		else
			echo -e `date '+%Y-%m-%d %H:%M:%S'`\\tWARN\\t"${CONTENT}" >> ${FILE}
		fi
	fi
}

# DESCRIPTION: extract timestamp of report output files name
# INPUT:
#    $1(Mandatory) - files_path
# OUTPUT:
#    none

extract() {
	FILES_PATH=$1
	
	[ ! -d ${FILES_PATH} ] && echo "ERROR: unknown path ${FILES_PATH}" && exit
	
	pushd ${FILES_PATH} > /dev/null
	ls | while read LINE
	do 
		FILE_NAME=`echo ${LINE} | awk -F"." '{print $1}'`
		FILE_EXT=`echo ${LINE} | awk -F"." '{print $2}'`
		FILE_NAME_NEW=`echo ${FILE_NAME} | awk -F"_" -v name='' '{split($0,a,"_" ); for(i=1;i<=NF-3;i++) {name=(name"_"a[i])} printf("%s\n",name)}'`
		FILE_NAME_NEW=${FILE_NAME_NEW:1}
		mv ${LINE} ${FILE_NAME_NEW}.${FILE_EXT}
	done
	popd > /dev/null
}

# DESCRIPTION: unzip files
# INPUT:
#    $1(Mandatory) - files_path
# OUTPUT:
#    none

unzip_files() {
	FILES_PATH=$1
	
	[ ! -d ${FILES_PATH} ] && echo "ERROR: unknown path ${FILES_PATH}" && exit
	
	pushd ${FILES_PATH} > /dev/null
	ls | while read LINE
	do 
		FILE_NAME=`echo ${LINE} | awk -F"." '{print $1}'`
		FILE_EXT=`echo ${LINE} | awk -F"." '{print $2}'`
		if [ ${FILE_EXT} = "zip" ]; then
			mkdir temp && mv ${LINE} temp
			pushd temp > /dev/null
			unzip ${LINE} && rm ${LINE}
			FILE_UNZIPPED=`ls`
			FILE_EXT_UNZIPPED=`ls | awk -F"." '{print $2}'`
			cp "${FILE_UNZIPPED}" "../${FILE_NAME}.${FILE_EXT_UNZIPPED}"
			popd > /dev/null
			rm -r temp
		fi
	done
	popd > /dev/null
}