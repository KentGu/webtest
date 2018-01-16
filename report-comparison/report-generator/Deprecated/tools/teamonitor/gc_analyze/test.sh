#!/bin/bash
HOME="/export/home/test/Colline2009DB_EAPCP07/jboss/server/CollineDBSP10.10/log"

TIMESTAMP=`date '+%y%m%d%H%M%S'`
OUTPUTFILE=node71_request_${TIMESTAMP}.csv
cat ${HOME}/server.log* |\
    grep -i "request trace" |\
    awk '{if($8=="end"){print$1,",",$2,",",$8,",",$10,",",$11,",",$12} else\
    {print$1,",",$2,",",$8,",",$9,",",$10,",",$11}}' >${OUTPUTFILE}

#cat ${OUTPUTFILE} |












