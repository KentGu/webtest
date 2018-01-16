#!/bin/bash

START_TIME=`date '+%s.%N'`
START_TIME=`echo ${START_TIME} | awk '{printf "%.3f\n", $1}'`

echo ${START_TIME} > ${CUR_TASK_TMP_DIR}/pre.properties