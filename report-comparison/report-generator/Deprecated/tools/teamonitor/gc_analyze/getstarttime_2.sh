#!/bin/bash

###INPUT : gc file name with detailed timestamp  *_yyyymmddHHMMSS
###OUTPUT: JVM start time HH:MM:SS

FILENAME=${GC_FILE}

TEMP=tmp_`date '+%y%m%d%H%M%S'`_$$
echo ${FILENAME} >${TEMP}

TIME_STRING=`cat ${TEMP} |cut -d_ -f3 |cut -c 1-14`
yy=`cat ${TEMP} |cut -d_ -f3 |cut -c 1-4`
mm=`cat ${TEMP} |cut -d_ -f3 |cut -c 5-6`
dd=`cat ${TEMP} |cut -d_ -f3 |cut -c 7-8`
HH=`cat ${TEMP} |cut -d_ -f3 |cut -c 9-10`
MM=`cat ${TEMP} |cut -d_ -f3 |cut -c 11-12`
SS=`cat ${TEMP} |cut -d_ -f3 |cut -c 13-14`

#add 4 sec, so will be more realistic as JVM start time 
SS=`expr ${SS} + 4`

rm ${TEMP} 
STARTDATE=${yy}-${mm}-${dd}
STARTTIME=${HH}:${MM}:${SS}
export STARTTIME
export STARTDATE
export TIME_STRING
