. ./report.fun
movetime=0

for REPORT_TEMP in "baseline_ahv_07" "baseline_agmt_07" "baseline_assetmanagement_07" "baseline_assetsettlement_07" "baseline_audagmt_07" "baseline_audasset_07" "baseline_audassetdef_07" "baseline_audhc_07" "baseline_audir_07" "baseline_audorg_07" "baseline_audstme_07" "baseline_audtrades_07" "baseline_cashandaccrual_07" "baseline_corpactions_07" "baseline_de_07" "baseline_histexposure_07" "baseline_histfx_07" "baseline_histir_07" "baseline_intapplied_07" "baseline_settdetails_07"
#for REPORT_TEMP in "baseline_agm_03"
do
  RETURN_VALUE=`report_validation  ${REPORT_TEMP} "/export/home/test/external" "/export/home/test/archievedreport" "report_baseline_result" "20101RC8"`
  echo ${REPORT_TEMP}:${RETURN_VALUE}
  movetime=`expr ${movetime} + 1`
done
