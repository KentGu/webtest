#!/bin/bash
#sleep 2000
./update_org_Agmt_stmt_status.sh >update_org_Agmt_stmt_status_sql.log
sleep 10
nohup ./controller.sh COLLINE_2012_3_5_Baseline_ApproveTask_20140905 &

