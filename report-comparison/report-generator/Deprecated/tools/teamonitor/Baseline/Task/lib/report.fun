#!/bin/bash
#set -x
report_validation(){

#if the file existing or not

  REPORT_TEMPLATE_NAME=$1
  REPORT_STRORAGE_PATH=$2
  REPORT_ARCHIEVE_PATH=$3
  BASE_REPORT_NAME=$4
  COLLINE_VERSION=$5

  REPORT_MOVETO="Archievedreport_"$5_`date '+%Y%m%d%H%M%S'`
  REPORT_RESULT_FILE=$3/${REPORT_MOVETO}/"report_rowcount"
  ROW_COUNT=0
  FLAG="FAILED"


#get rows of the new report from REPORT_STORAGE_PATH} by REPORT_TEMPLATE_NAME, write to reoportrows file

  CUR_PATH=`pwd`
  cd $2
  REPORT_FULLNAME=`ls $1*`
  REPORT_NAME=`echo ${REPORT_FULLNAME} | awk -F/ '{print $6}'`
  cd $3
  if [ "${movetime}" -eq "0" ]; then
     mkdir ${REPORT_MOVETO}
  fi

  if [ "${REPORT_NAME}" ]; then
     echo -n $1 >>${REPORT_RESULT_FILE}
     echo -n "," >>${REPORT_RESULT_FILE}
     cd ${REPORT_MOVETO}

    #move the to REPORT_ARCHIEVE_PATH, make a new folder named by COLLINE_VERION and timestamp

     cp $2/${REPORT_NAME} $3/${REPORT_MOVETO}/${REPORT_NAME}
     echo ${REPORT_MOVETO} >>/export/home/test/reportname.txt
     pwd >>/export/home/test/reportname.txt
     ROW_COUNT=`cat ${REPORT_NAME} |tr -s '\n' '~'|tr -s '~' '\n'|wc -l`
     echo -n ${ROW_COUNT} >>${REPORT_RESULT_FILE}

#get the rows of the old report from BASE_REPORT_PATH, do comparation, export REPORT_VAL_RET=PASS/FAIL
     cd $3
     if [ -f ${BASE_REPORT_NAME} ]; then
        ROW_BASELINE=`cat ${BASE_REPORT_NAME}|grep $1|awk -F: '{print $2}'` 
        if [ "${ROW_BASELINE}" -eq "${ROW_COUNT}" ]; then
           FLAG="PASS"
        else
           FLAG="FAIL"
        fi
     else
        FLAG="PASS_WITHOUTCOMPARE"
     fi
     echo ","${FLAG}>>${REPORT_RESULT_FILE}
  else
     FLAG="FAIL"
     echo $1",,FAIL:CSV file doesn't exist" >>${REPORT_RESULT_FILE}
  fi
  echo ${ROW_COUNT}:${FLAG}
}
