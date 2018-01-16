#!/bin/bash
#load function
. ./lib/sql.fun

#set -x

select_sql_1="select count(1) from colagreementheader a, COLSTATEMENTSTATUS s where a.id = s.agreementid and s.statusid =68 and a.region = 'New York';

select * from databaseversion;



"

sql_1_des="Number of Agreement statementstatus approved in New York is:"

. `dirname $0`/dbconf.sh
sql_select ${DB_USER} ${DB_PASSWD} ${DB_SERVER} ${S_ID} "${select_sql_1}" "${sql_1_des}"

#set +x
