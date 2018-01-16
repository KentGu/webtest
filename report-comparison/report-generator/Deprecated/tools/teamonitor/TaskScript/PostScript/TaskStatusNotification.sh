#!/bin/bash
CURTASK=`cat /home/test/tools/teamonitor/Baseline/Task/currenttask`
end_time=`date '+%Y-%m-%d %H:%M:%S.%N'`
end_time2_=`date '+%s.%N'`
echo $end_time2_ >num222
end_time2=`cat num222 | awk '{printf "%.3f\n", $1}'`

echo "${end_time}:${CURTASK}:End:${end_time2}" >/home/test/tools/teamonitor/TaskScript/TaskStatusFlag
echo "${end_time},${CURTASK},End,${end_time2}" >>/home/test/tools/teamonitor/TaskScript/Taskexeclog
