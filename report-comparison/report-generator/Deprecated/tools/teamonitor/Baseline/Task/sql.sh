#!/bin/bash

#sqlplus COLLINE2013_1011/creditderivative@\"//172.20.20.66:1521/pst2\"

#STMT_="select * from databaseversion"
STMT_="select count(1) from colagreementheader a, COLSTATEMENTSTATUS s where a.id = s.agreementid and s.statusid =68 and a.region = 'New York'"

STMT_2="SELECT count(1) FROM ColStatementCalcreqView,ColAgreementHeader  WHERE (ColStatementCalcreqView.agreementId = ColAgreementHeader.id) AND (ColStatementCalcreqView.maxDefCalcReqId > 0 OR ColStatementCalcreqView.maxColCalcReqId > 0 OR ColStatementCalcreqView.maxExpCalcReqId >0 OR ColStatementCalcreqView.maxArchiveReqId > 0) AND ColAgreementHeader.Statusid = 68"

printf  "${STMT_};\n
         ${STMT_2};\n
              commit;\n
              quit;" |sqlplus COLLINE2013_1011/creditderivative@\"//172.20.20.66:1521/pst2\"
