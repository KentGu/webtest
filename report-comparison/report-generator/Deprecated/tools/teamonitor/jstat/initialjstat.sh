#!/bin/bash

#kill the jstat.sh running in the backgroud
echo "enable the heap.log"
ps -ef |grep jstat.sh |grep -v grep |grep -v ini|awk '{print$2}' |(while read PID; do [ X${PID} = X ] && continue;kill -9 ${PID};done)

