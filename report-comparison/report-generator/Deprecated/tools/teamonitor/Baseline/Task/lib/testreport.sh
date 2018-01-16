. ./report.fun
movetime=0


for REPORT_TEMP in `ls /export/home/test/external/baseline_*`
do
  RETURN_VALUE=`report_validation  ${REPORT_TEMP} "/export/home/test/external" "/export/home/test/archievedreport" "report_baseline_result" "20101SP1"`
  echo ${REPORT_TEMP}:${RETURN_VALUE}
  movetime=`expr ${movetime} + 1`
done
