#!/bin/bash

sqlplus COLLINE2013_1011/creditderivative@\"//172.20.20.66:1521/pst2\">tmp_log <<EOF
select * from databaseversion;
delete from F3Monitor;
select count(*) from f3systemmonitor;
exit
EOF


