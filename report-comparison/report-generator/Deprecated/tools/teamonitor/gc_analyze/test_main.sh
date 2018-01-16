#!/bin/bash
export a=0
rm output

(. `dirname $0`/test_sub1.sh 2>&1 1>>output ) 

(. `dirname $0`/test_sub2.sh 2>&1 1>>output )



#while true 
#do

#   echo main --${a}
#   sleep 1

#done
