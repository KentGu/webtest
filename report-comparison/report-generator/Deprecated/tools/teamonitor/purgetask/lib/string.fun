#IFS=$' 
#
#

isfilecontainingstring(){
   STRING_=$1
   FILE_=$2   

   for STR_ in `cat ${FILE_}`
   do
      if [ ${STRING_} = ${STR_} ];then
         return 0
      fi
   done

   return 1 

}
