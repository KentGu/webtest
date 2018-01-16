#!/bin/sh
JBOSS_HOME="/export/home/test/Colline2009DB_EAPCP07/jboss/bin/"
#JBOSS_HOME=`pwd`/
#set -x
GC_FILE=`ls ${JBOSS_HOME} | grep gc_10`  
#GC_FILE=$1
 
. `dirname $0`/getstarttime_2.sh 

echo GC FILE: ${JBOSS_HOME}${GC_FILE}
echo basename= `basename ${GC_FILE}`
./format_4.sh ${JBOSS_HOME}${GC_FILE} $1

