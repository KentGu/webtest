#!/bin/bash
#DB_USER=$1
#DB_PASSWD=$2
#S_ID=$3
#DB_SERBER=$4
SELECT_SQL=$1
SQL_DESCRIPTION=$2


VALUE=`sqlplus -S ${DB_USER}/${DB_PASSWD}@\"//${DB_SERVER}/${S_ID}\"  <<EOF

set heading off feedback off pagesize 0 verify off echo off
{SELECT_SQL}
#select count(1)  from colagreementheader;
commit
EOF`
#VALUE="$?"
#echo "The number of rows is" $VALUE
echo "${SQL_DESCRIPTION}" $VALUE
