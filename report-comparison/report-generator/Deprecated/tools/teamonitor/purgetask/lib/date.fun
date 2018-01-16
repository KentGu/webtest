

isnewday(){
   OLDDAY_=$1

   CURDAY_=`date '+%d'`

   if [ ${OLDDAY_}X = X ];then
      return 1
   fi 

   if [ ${OLDDAY_} -lt ${CURDAY_} ];then
      return 0
   else
      return 1
   fi 
}

#input 
#  FREQUENCY_   : ( Mon | Tue | Wed | Thu | Fri | Sat | Sun | Daily |  Weekday |  Weekend | 20100824 )  
#  TRRIGGERTIME_: ( 121212 ) 12:12:12
#
isqrtztime(){

   FREQUENCY_=$1
   TRRIGGERTIME_=$2

   CURTIME_=`date '+%H%M%S'`
   CURWEEKDAY_=`date |awk '{print$1}'`
   CURMONTH_=`date |awk '{print$2}'`
   CURSPECIFICTIME_=`date '+%Y%m%d%H%M%S'`
   ISWEEKLY_=1
   ISDAILY_=1
   ISWEEKDAYLY_=1
   ISWEEKENDLY_=1
   ISSPECIFICDATE_=1
   ISCURWEEKDAYLY_=1
   ISCURWEEKENDLY_=1

   #triggertime length verify 
   [ ! `echo ${TRRIGGERTIME_} |awk '{print length($0)}'` -eq 6 ] && return 1
    
   case ${FREQUENCY_} in
      Mon | Tue | Wed | Thu | Fri | Sat | Sun) 
          ISWEEKLY_=0 
        ;;
      Daily)
          ISDAILY_=0
        ;;
      Weekday)
          ISWEEKDAYLY_=0
        ;;
      Weekend)
          ISWEEKENDLY_=0
        ;;
      [0-9]*)
          ISSPECIFICDATE_=0
        ;;
       *)
          return 1
        ;;
   esac


   case ${CURWEEKDAY_} in
      Mon | Tue | Wed | Thu | Fri)
         ISCURWEEKDAYLY_=0
         ;;
      Sun | Sat) 
         ISCURWEEKENDLY_=0 
         ;;
   esac

   [ ${ISDAILY_} -eq 0 -a ${CURTIME_} -gt ${TRRIGGERTIME_} ] && return 0
   [ ${ISWEEKDAYLY_} -eq 0 -a ${ISCURWEEKDAYLY_} -eq 0 -a ${CURTIME_} -gt ${TRRIGGERTIME_} ] && return 0
   [ ${ISWEEKENDLY_} -eq 0 -a ${ISCURWEEKENDLY_} -eq 0 -a ${CURTIME_} -gt ${TRRIGGERTIME_} ] && return 0
   [ ${ISWEEKLY_} -eq 0 -a ${FREQUENCY_} = ${CURWEEKDAY_} -a ${CURTIME_} -gt ${TRRIGGERTIME_} ] && return 0
    
   if [ ${ISSPECIFICDATE_} -eq 0 ];then
      SPECIFICTIME_=${FREQUENCY_}${TRRIGGERTIME_}
      [ ${SPECIFICTIME_} -lt ${CURSPECIFICTIME_} ] && return 0

   fi   

   return 1
}



date2stamp () {
    date --utc --date "$1" +%s
}

stamp2date (){
    date --utc --date "1970-01-01 $1 sec" "+%Y-%m-%d %T"
}

