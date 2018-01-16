#!/bin/bash

`nohup ./controller.sh test2 &`
echo "----------------------$?="$?
if [ $? = 0 ]; then
   echo "Task finished!"
else
   echo "Task failed!"
fi
