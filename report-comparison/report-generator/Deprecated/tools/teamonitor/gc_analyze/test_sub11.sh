#!/bin/bash

export a=0

while [ "$a" -lt 10 ] 
do

  echo sub1_a=$a 

  . `dirname $0`/test_sub22.sh


 # ./test_sub22.sh
  a=`expr ${a} + 1`

done
