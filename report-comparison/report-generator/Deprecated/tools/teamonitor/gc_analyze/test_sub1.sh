#!/bin/bash

X=1
while [ ${X} -lt 11 ]
do
  a=`expr $a + 1`
  export a
  echo sub1_a=$a
  sleep 1

  X=`expr ${X} + 1`
done
