#!/bin/bash

X=1
while [ ${X} -lt 11 ]
do
  a=`expr $a - 1`
  export a
  echo sub2_a=$a
  sleep 1

  X=`expr ${X} + 1`
done
