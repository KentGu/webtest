
restartserver(){

   CURPATH=`pwd`

   cd ${BIN_DIR}   
   #./firmament.sh stop

   #sleep 100 
   #Kill_Java.sh
   
   cd ${CURPATH}
   delete7tables

   cd ${BIN_DIR}
   #./firmament.sh start d
   cd ${CURPATH}

}
