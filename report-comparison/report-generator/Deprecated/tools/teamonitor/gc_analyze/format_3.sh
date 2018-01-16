#!/bin/bash
. `dirname $0`/par.sh
echo STARTTIME=${STARTTIME}

GC_FILE=$1
BASE_NAME=`basename ${GC_FILE}`
TIMESTAMP=`date '+%y%m%d%H%M%S'`
TMPDIR=tmp_${TIMESTAMP}_$$/
OUTPUTFILE=${BASE_NAME}_OUTPUT_${TIMESTAMP}.csv
TMP=${TMPDIR}tmp

[ x${NEWSIZE} = x ] && echo "NEW SIZE NEED TO SPECIFY" && exit -1
[ x${OLDSIZE} = x ] && echo "OLD SIZE NEED TO SPECIFY" && exit -1
[ x${PERMSIZE} = x ] && echo "PERM SIZE NEED TO SPECIFY" && exit -1
[ x${GC_FILE} = x ] && echo " GC FILE NEED TO SPECIFY" && exit -1
[ -f ${GC_FILE} ] ||echo " NO SUCH GC FILE"   
[ -f ${GC_FILE} ] ||exit -1   
ENTIRESIZE=`expr ${NEWSIZE} + ${OLDSIZE}`

converttime(){
   START_TIME=$1
   RET_T=`echo $2 |awk -F. '{print$1}'`

   START_H=`echo ${START_TIME} | awk -F: '{print$1}'`
   START_M=`echo ${START_TIME} | awk -F: '{print$2}'`
   START_S=`echo ${START_TIME} | awk -F: '{print$3}'`

   START_H=`expr ${START_H} \* 3600`
   START_M=`expr ${START_M} \* 60`
   RET_T=`expr ${RET_T} + ${START_H} + ${START_M} + ${START_S}`

   RET_H=0
   RET_M=0
   RET_S=0
   [ ${RET_T} -gt 0 ]  && RET_S=`expr ${RET_T} % 60`
   [ ${RET_T} -gt 59 ] && RET_M=`expr ${RET_T} / 60` && RET_S=`expr ${RET_T} % 60`
   [ ${RET_M} -gt 59 ] && RET_H=`expr ${RET_M} / 60` && RET_M=`expr ${RET_M} % 60`
   [ ${RET_H} -gt 23 ] && RET_H=`expr ${RET_H} % 24`

   RET_TS=${RET_H}:${RET_M}:${RET_S}
   echo ${RET_TS},
}

clear && printf "%s" "has done 5%"

#make a tmp directory
[ -d {TMP} ] || mkdir ${TMPDIR} || echo failed to creat tmp dir please check your previlege

#format the orginal gc file

cat ${GC_FILE} |grep -v 'Total time' |\
   sed 's/[0-9]*\.[0-9][0-9][0-9]:/# &/g' |\
   tr -s '\n' ' ' |\
   tr -s '#' '\n' |\
   sed 's/ $//'   |\
   sed 's/]$/]\&/'|\
   tr -s '\n' ' '|\
   tr -s '\&' '\n'|\
   sed 's/^ //'  |\
   sed 's/^[0-9]*\.[0-9][0-9][0-9]: /&#/g' >${TMP}


#clear && printf "%s" "has done 25%"

####Get all the basic data####
cd ${TMPDIR} || echo failed to access dir ${TMPDIR}

#for NewGen OldGen PermGen EntireGen 
sed 's/[0-9]*K('${NEWSIZE}'K)/#&#/g'    tmp | awk -F# '{print$1,$3}' >1_ng 
sed 's/[0-9]*K('${OLDSIZE}'K)/#&#/g'    tmp | awk -F# '{print$1,$3}' >2_og
sed 's/[0-9]*K('${PERMSIZE}'K)/#&#/g'   tmp | awk -F# '{print$1,$3}' >3_pg
sed 's/[0-9]*K('${ENTIRESIZE}'K)/#&#/g' tmp | awk -F# '{print$1,$3}' >4_eg

#for PF,CMF,FGC,STOPTIME
sed 's/promotion failed/#&#/g' tmp  |\
   awk -F# '{if(NF==4){print$1,"pf"}else{print$1}}'  >5_pf
sed 's/concurrent mode failure/#&#/g' tmp |\
   awk -F# '{if(NF==4){print$1,"cmf"}else{print$1}}' >6_cmf                 
sed 's/Full GC/#&#/g' tmp |\
   awk -F# '{if(NF==4){print$1,"fgc"}else{print$1}}' >7_fgc                
sed 's/ [0-9]*\.[0-9][0-9][0-9][0-9][0-9][0-9][0-9] /#&#/g' tmp |\
   awk -F# '{a=NF-1}{if(NF>4){print$1,$a}else{print $1,$3}}' >8_st        
   #clear && printf "%s" "has done 50%"
   
#clear && printf "%s" "has done 50%"

####combine to a file####
#format to 2 field
for FILE in `ls *_*`
do
   cat ${FILE} | awk '{if(NF==2){print $1,",",$2,","}else{print $1,",",","}}' \
      >${FILE}_1
done

#paste all the files
NO=1
NO2=2
>${NO}
for FILE in `ls *_1`
do
   paste ${NO} ${FILE} >${NO2}
   NO=`expr ${NO} + 1`
   NO2=`expr ${NO} + 1`
done

##get the info from the pasted file
cat ${NO}|\
   awk -F, '{print$1,",",$2,",",$4,",",$6,",",$8,",",$10,",",$12,",",$14,",",$16,","}' \
   >format_file 

#clear && printf "%s" "has done 75%" 

##erase the dirty marks
#add the header
#echo "TimeStamp,NewGen,OldGen,PermGen,EntireGen,PF,CMF,FULLGC,StopTime" >${OUTPUTFILE}

cat format_file |\
   tr -s ':' ' '|\
   sed 's/K('${NEWSIZE}'K)/ /g' |\
   sed 's/K('${OLDSIZE}'K)/ /g' |\
   sed 's/K('${PERMSIZE}'K)/ /g' |\
   sed 's/K('${ENTIRESIZE}'K)/ /g' |\
   tr -s '\t' ' ' |\
   sed 's/ //g' |\
   grep -v '^,*$' |\
   grep -v '^$' |\
   grep -v [0-9]*\.[0-9][0-9][0-9],,,,,,,,\
   >>${OUTPUTFILE} 

#clear && printf "%s\n" "has done 100%" && clear

###summary
tail -1 tmp |awk -F: '{print $1}' |tr -s '\t' ' ' |sed 's/ //g' >TotalTime
TotalTime=`cat TotalTime`
printf "THE APP HAD BEEN RUN FOR  %ss\n" ${TotalTime}
echo "=================================================================================="
printf "%-10s %-10s %-10s %-10s %-10s %-10s %-10s\n"\
      "EVENT" "AMOUNT" "MIN" "MAX" "AVG" "TTL" "PCTG OF WHOLE TIME"

#normal gc
cat ${OUTPUTFILE} |\
grep -v pf |\
grep -v cmf |\
grep -v fgc |\
awk -F, '{print$9}' | sort -n |\
   awk '{Tot+=$1}{if(NR==1){Min=$1}}{Max=$1};\
      END{if(NR>0){printf "%-10s %-10s %-10s %-10s %-10s %-10s %-10s\n",\
     "GC",NR, Min,Max,Tot/NR,Tot,Tot/'${TotalTime}'}\
      else{printf "%-10s %-10s %-10s %-10s %-10s %-10s %-10s\n","GC",0,0,0,0,0,0,0}}' 


#pf
cat ${OUTPUTFILE} |\
grep 'pf' |\
awk -F, '{print$9}' |\
sort -n |\
   awk '{Tot+=$1}{if(NR==1){Min=$1}}{Max=$1};\
      END{if(NR>0){printf "%-10s %-10s %-10s %-10s %-10s %-10s %-10s\n",\
     "PF", NR, Min,Max,Tot/NR,Tot,Tot/'${TotalTime}'}\
      else{printf "%-10s %-10s %-10s %-10s %-10s %-10s %-10s\n","PF",0,0,0,0,0,0,0}}'

#cmf
cat ${OUTPUTFILE} |\
   grep 'cmf'  |\
   awk -F, '{print$9}' |\
   sort -n |\
   awk '{Tot+=$1}{if(NR==1){Min=$1}}{Max=$1};\
      END{if(NR>0){printf "%-10s %-10s %-10s %-10s %-10s %-10s %-10s\n",\
     "CMF", NR, Min,Max,Tot/NR,Tot,Tot/'${TotalTime}'}\
      else{printf "%-10s %-10s %-10s %-10s %-10s %-10s %-10s\n","CMF",0,0,0,0,0,0,0}}'

#fgc
cat ${OUTPUTFILE} |\
   grep 'fgc' |\
   awk -F, '{print$9}' |\
   sort -n |\
   awk '{Tot+=$1}{if(NR==1){Min=$1}}{Max=$1};\
      END{if(NR>0){printf "%-10s %-10s %-10s %-10s %-10s %-10s %-10s\n",\
     "FULLGC", NR, Min,Max,Tot/NR,Tot,Tot/'${TotalTime}'}\
      else{printf "%-10s %-10s %-10s %-10s %-10s %-10s %-10s\n","FULLGC",0,0,0,0,0,0,0}}'

#total
cat ${OUTPUTFILE} | awk -F, '{print$9}' |\
   sort -n |\
   awk '{Tot+=$1}{if(NR==1){Min=$1}}{Max=$1};\
      END{if(NR>0){printf "%-10s %-10s %-10s %-10s %-10s %-10s %-10s\n",\
      "TOTAL", NR, Min,Max,Tot/NR,Tot,Tot/'${TotalTime}'}\
      else{printf "%-10s %-10s %-10s %-10s %-10s %-10s %-10s\n","TOTAL",0,0,0,0,0,0,0}}'

echo "=================================================================================="

###convert time  start
echo `date '+%H:%M:%S'`
echo converting the time format, this may take several miniutes

#SIPLIT_SIGNAL=0
echo  "0" >file_split_signal
>./../file_signal
SPLIT_COUNTER=1

OUTPUTFILE_LINE=`cat ${OUTPUTFILE} |wc -l`
OUTPUTFILE_SPLIT=`expr ${OUTPUTFILE_LINE} / 4`

split -${OUTPUTFILE_SPLIT} ${OUTPUTFILE} SPLIT

#
split_signal_up(){
   SPLIT_SIGNAL=`cat file_split_signal`
   SPLIT_SIGNAL=`expr ${SPLIT_SIGNAL} + 1`
   echo  ${SPLIT_SIGNAL} >file_split_signal
   echo up to ${SPLIT_SIGNAL} >>./../file_signal
}

split_signal_down(){
   [ -f flag ] && sleep 1
   >flag
   SPLIT_SIGNAL=`cat file_split_signal`
   SPLIT_SIGNAL=`expr ${SPLIT_SIGNAL} - 1`
   echo  ${SPLIT_SIGNAL} >file_split_signal
   echo down to ${SPLIT_SIGNAL} >>./../file_signal
   rm flag
}

#
for FILE in `ls SPLIT*`
do
   split_signal_up
   cat ${FILE} | awk -F, '{print$1}' |\
      (while read X;do converttime ${STARTTIME} ${X};done; split_signal_down)\
       >>converttimefile_${SPLIT_COUNTER} &
   SPLIT_COUNTER=`expr ${SPLIT_COUNTER} + 1`
done

#
SPLIT_SIGNAL=`cat file_split_signal`
while [ ${SPLIT_SIGNAL} -ne 0 ]
do
  SPLIT_SIGNAL=`cat file_split_signal`
  echo now ${SPLIT_SIGNAL} >>./../file_signal
  
  sleep 1
done

cat converttimefile_* >converttimefile


paste converttimefile ${OUTPUTFILE}|\
sed '1 i\
TimeStamp,Seconds,NewGen,OldGen,PermGen,EntireGen,PF,CMF,FULLGC,StopTime'|\
tr -s '\t' ' '|\
sed 's/ //g'\
>./../${OUTPUTFILE}
 
echo `date '+%H:%M:%S'`
###convert time end 

printf "***%s\n" "Detailed file ${OUTPUTFILE}"
##delete tmp file
cd ./../
rm -rf *tmp* 
