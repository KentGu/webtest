#!/bin/bash


test_return(){
 X=$1

 echo `expr ${X} + 1`
 
}


for Y in 9 8 7 6 5
do
   Z=`test_return ${Y}`
   echo Z=${Z}
done
