#!/bin/bash
echo "------------i am prexxxx" >>/home/test/tools/teamonitor/TaskScript/Taskexeclog
CURTASK=`cat /home/test/tools/teamonitor/Baseline/Task/currenttask`
start_time=`date '+%Y-%m-%d %H:%M:%S.%N'`
start_time2_=`date '+%s.%N'`
echo $start_time2_ >num111
start_time2=`cat num111 | awk '{printf "%.3f\n", $1}'`

echo "${start_time}:${CURTASK}:Start:${start_time2}" >/home/test/tools/teamonitor/TaskScript/TaskStatusFlag
echo "${start_time},${CURTASK},Start,${start_time2}" >>/home/test/tools/teamonitor/TaskScript/Taskexeclog
