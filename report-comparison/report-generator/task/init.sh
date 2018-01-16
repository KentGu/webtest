#!/bin/bash

BASE_DIR=`pwd`

find ${BASE_DIR} -type f | xargs -i chmod 755 {}
find ${BASE_DIR} -type f | grep -E "sh$|ini$|fun$|list$" | xargs -i dos2unix {}