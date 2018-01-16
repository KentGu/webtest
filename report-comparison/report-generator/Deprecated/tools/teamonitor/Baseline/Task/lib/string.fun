#IFS=$' 
#
#

isfilecontainingstring(){
   ifcsSTRING_=$1
   ifcsFILE_=$2   
   
   ifcsRET_=`cat ${ifcsFILE_} |awk '{for(N=1;N<=NF;N++){if($N=="'${ifcsSTRING_}'"){print"0";exit 0}}}'` 
   [ ! X${ifcsRET_} = X ] && return 0
   return 1

}


islinecontainningstring(){
   ilcsSTRING_=$1
   shift
   
   while [ ! X$1 = X ]
   do
      if [ $1 = ${ilcsSTRING_} ];then
         return 0

      fi
      shift
   done

  return 1

}
