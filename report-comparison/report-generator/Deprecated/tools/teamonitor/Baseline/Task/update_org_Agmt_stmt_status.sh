#!/bin/bash

######################################################################################
# This script is for preparing init data from Approved tasks:
# 1. update organization status from Approved(68) to Amend(69)   
# 2. update Agreements status from Approved(68) to Amend(69)                                                                          
# 3. update Statements status from Approved(68) to Amend(69)                                                                                     
#                                                                                     
######################################################################################

. $AUTOTEST_CONFIG_PATH/config.ini

#sqlplus COLLINE201232_BASE_0911/creditderivative@172.20.20.99:1521/pst4 <<EOF
sqlplus ${DB_USER}/${DB_PASSWD}@${DB_SERVER}/${DB_SID} <<EOF


select count(1) AgmtStatus_69_Count from colagreementheader where statusid = 69;
select count(1) AgmtStatus_68_Count from colagreementheader where statusid = 68;

select count(1) StmtStatus_69_Count from COLSTATEMENTSTATUS where statusid = 69;
select count(1) StmtStatus_68_Count from COLSTATEMENTSTATUS where statusid = 68;

select count(1) OrgStatus_69_COunt from orgheader where status = 69;
select count(1) OrgStatus_68_COunt from orgheader where status = 68;

update colagreementheader set statusid = 69 where statusid = 68;
update colstatementstatus set statusid = 69 where statusid = 68;
update orgheader set status = 69 where status = 68;


commit;
quit;
EOF
