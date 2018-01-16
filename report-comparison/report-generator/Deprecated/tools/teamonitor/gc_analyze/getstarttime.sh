#!/bin/bash

###INPUT : gc file name with detailed timestamp  *_yyyymmddHHMMSS
###OUTPUT: JVM start time HH:MM:SS


FILENAME=${GC_FILE}

TEMP=tmp_`date '+%y%m%d%H%M%S'`_$$
echo ${FILENAME} >${TEMP}

HH=`cat ${TEMP} |cut -d_ -f3 |cut -c 9-10`
MM=`cat ${TEMP} |cut -d_ -f3 |cut -c 11-12`
SS=`cat ${TEMP} |cut -d_ -f3 |cut -c 13-14`

#add 4 sec, so will be more realistic as JVM start time 
SS=`expr ${SS} + 4`

rm ${TEMP} 

STARTTIME=${HH}:${MM}:${SS}

export STARTTIME

