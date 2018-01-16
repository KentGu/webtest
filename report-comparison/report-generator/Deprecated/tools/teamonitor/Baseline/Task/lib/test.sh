#!/bin/bash

. ./date.fun

DATE1="2010-01-02 10:10:10"
DATE2="2010-01-03 10:10:10"

D1=$(date2stamp ${DATE1})
D2=$(date2stamp ${DATE2})

echo ${D1} ${D2}
