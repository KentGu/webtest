#!/bin/bash
#set -x
echo  "Enter the tablename you want to select:"
read TABLENAME
sqlplus -S COLLINE2013_1011/creditderivative@\"//172.20.20.66:1521/pst2\" <<EOF
select * from ${TABLENAME};
exit

#set +x
