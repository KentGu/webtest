#!/bin/bash


DIR=`pwd`
WAIT_TIME=2

[ $# -eq 1 ] && WAIT_TIME=$1
[ $# -gt 1 ] && echo only 1 parameter allowed && exit -1 

#sleep 60
PID=`jps |grep -i main |awk '{print$1}'`

while [ 1 ]
do
   jstat -gc ${PID}  ${WAIT_TIME}s 100 |\
      (while read -r line ;do echo $line |awk '{for(i=2;i<16;i++){str=str", "$i}{print str}}';done)|\
      (while read -r line ;do echo `date '+%Y-%m-%d %H:%M:%S'` $line;done)|\
      tee -a ${DIR}/heapusage_`date '+%Y%m%d'`.csv

done
