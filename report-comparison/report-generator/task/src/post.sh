#!/bin/bash

END_TIME=`date '+%s.%N'`
END_TIME=`echo ${END_TIME} | awk '{printf "%.3f\n", $1}'`

echo ${END_TIME} > ${CUR_TASK_TMP_DIR}/post.properties