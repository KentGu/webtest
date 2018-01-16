#!/bin/bash
. ./lib/date.fun

set -x
if ! isqrtztime $1 $2 ;then

  echo yes 

fi
