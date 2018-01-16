package com.lombardrisk.pages.collateral;


import com.lombardrisk.pojo.Agreement;
import com.lombardrisk.pojo.NavRuleTrigger;
import com.lombardrisk.pojo.RatingRuleTrigger;
import com.lombardrisk.pojo.TimeRuleTrigger;
import com.lombardrisk.util.PageHelper;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;


/**
 * Created by Kenny Wang on 2/3/2016.
 */
public class AgreementRuleTriggerSetupPage extends PageBase {
    public AgreementRuleTriggerSetupPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setupAgreementRuleTrigger(Agreement agmt) throws Exception {
		//net principal and counterparty together
		//principalFixTrigger  time nav rating
		// counterpartyFixTrigger time nav rating
		if (agmt.getPrincipalFixedTrigger() != null) {
			if (agmt.getPrincipalFixedTrigger() != null
					&& (agmt.getPrincipalFixedTrigger().getTimeContingentValues() != null
					|| agmt.getPrincipalFixedTrigger().getNavContingentValues() != null
					|| agmt.getPrincipalFixedTrigger().getRatingContingentValues() != null)) {
				if (agmt.getPrincipalFixedTrigger().getTimeContingentValues() != null) {
					setupTimeRuleTriggerRow(agmt.getPrincipalFixedTrigger().getTimeContingentValues());
					setupTimeRuleTirggerAll(agmt.getPrincipalFixedTrigger().getTimeContingentValues());
					enterNextStage();
				}
				if (agmt.getPrincipalFixedTrigger().getNavContingentValues() != null) {
					setupNAVRuleTriggerRow(agmt.getPrincipalFixedTrigger().getNavContingentValues());
					setupNAVRuleTriggerAll(agmt.getPrincipalFixedTrigger().getNavContingentValues());
					enterNextStage();
				}
				if (agmt.getPrincipalFixedTrigger().getRatingContingentValues() != null) {
					setupRatingRuleTriggerRow(agmt.getPrincipalFixedTrigger().getRatingContingentValues());
					setupRatingRuleTriggerAll(agmt.getPrincipalFixedTrigger().getRatingContingentValues());
					enterNextStage();
				}
			}
		}
		if (agmt.getCounterpartyFixedTrigger() != null) {
			if (agmt.getCounterpartyFixedTrigger() != null
					&& (agmt.getCounterpartyFixedTrigger().getTimeContingentValues() != null
					|| agmt.getCounterpartyFixedTrigger().getNavContingentValues() != null
					|| agmt.getCounterpartyFixedTrigger().getRatingContingentValues() != null)) {
				if (agmt.getCounterpartyFixedTrigger().getTimeContingentValues() != null) {
					setupTimeRuleTriggerRow(agmt.getCounterpartyFixedTrigger().getTimeContingentValues());
					setupTimeRuleTirggerAll(agmt.getCounterpartyFixedTrigger().getTimeContingentValues());
					enterNextStage();
				}
				if (agmt.getCounterpartyFixedTrigger().getNavContingentValues() != null) {
					setupNAVRuleTriggerRow(agmt.getCounterpartyFixedTrigger().getNavContingentValues());
					setupNAVRuleTriggerAll(agmt.getCounterpartyFixedTrigger().getNavContingentValues());
					enterNextStage();
				}
				if (agmt.getCounterpartyFixedTrigger().getRatingContingentValues() != null) {
					setupRatingRuleTriggerRow(agmt.getCounterpartyFixedTrigger().getRatingContingentValues());
					setupRatingRuleTriggerAll(agmt.getCounterpartyFixedTrigger().getRatingContingentValues());
					enterNextStage();
				}
			}
		}
		//not net agreement
		if (agmt.getVmPrincipalFixedTrigger() != null ) {
			if (agmt.getVmPrincipalFixedTrigger().getTimeContingentValues() != null ||
					agmt.getVmPrincipalFixedTrigger().getNavContingentValues() != null ||
					agmt.getVmPrincipalFixedTrigger().getRatingContingentValues() != null) {

				if (agmt.getVmPrincipalFixedTrigger().getTimeContingentValues() != null) {
					setupTimeRuleTriggerRow(agmt.getVmPrincipalFixedTrigger().getTimeContingentValues());
					setupTimeRuleTirggerAll(agmt.getVmPrincipalFixedTrigger().getTimeContingentValues());
					enterNextStage();
				}
				if (agmt.getVmPrincipalFixedTrigger().getNavContingentValues() != null) {
					setupNAVRuleTriggerRow(agmt.getVmPrincipalFixedTrigger().getNavContingentValues());
					setupNAVRuleTriggerAll(agmt.getVmPrincipalFixedTrigger().getNavContingentValues());
					enterNextStage();
				}
				if (agmt.getVmPrincipalFixedTrigger().getRatingContingentValues() != null) {
					setupRatingRuleTriggerRow(agmt.getVmPrincipalFixedTrigger().getRatingContingentValues());
					setupRatingRuleTriggerAll(agmt.getVmPrincipalFixedTrigger().getRatingContingentValues());
					enterNextStage();
				}
			}
		}if(agmt.getVmCounterpartyFixedTrigger() != null) {
			if (agmt.getVmCounterpartyFixedTrigger().getTimeContingentValues() != null ||
					agmt.getVmCounterpartyFixedTrigger().getNavContingentValues() != null ||
					agmt.getVmCounterpartyFixedTrigger().getRatingContingentValues() != null) {
				if (agmt.getVmCounterpartyFixedTrigger().getTimeContingentValues() != null) {
					setupTimeRuleTriggerRow(agmt.getVmCounterpartyFixedTrigger().getTimeContingentValues());
					setupTimeRuleTirggerAll(agmt.getVmCounterpartyFixedTrigger().getTimeContingentValues());
					enterNextStage();
				}
				if (agmt.getVmCounterpartyFixedTrigger().getNavContingentValues() != null) {
					setupNAVRuleTriggerRow(agmt.getVmCounterpartyFixedTrigger().getNavContingentValues());
					setupNAVRuleTriggerAll(agmt.getVmCounterpartyFixedTrigger().getNavContingentValues());
					enterNextStage();
				}
				if (agmt.getVmCounterpartyFixedTrigger().getRatingContingentValues() != null) {
					setupRatingRuleTriggerRow(agmt.getVmCounterpartyFixedTrigger().getRatingContingentValues());
					setupRatingRuleTriggerAll(agmt.getVmCounterpartyFixedTrigger().getRatingContingentValues());
					enterNextStage();
				}
			}
		}if(agmt.getImPrincipalFixedTrigger() != null ) {
			if (agmt.getImPrincipalFixedTrigger().getTimeContingentValues() != null ||
					agmt.getImPrincipalFixedTrigger().getNavContingentValues() != null ||
					agmt.getImPrincipalFixedTrigger().getRatingContingentValues() != null) {
				if (agmt.getImPrincipalFixedTrigger().getTimeContingentValues() != null) {
					setupTimeRuleTriggerRow(agmt.getImPrincipalFixedTrigger().getTimeContingentValues());
					setupTimeRuleTirggerAll(agmt.getImPrincipalFixedTrigger().getTimeContingentValues());
					enterNextStage();
				}
				if (agmt.getImPrincipalFixedTrigger().getNavContingentValues() != null) {
					setupNAVRuleTriggerRow(agmt.getImPrincipalFixedTrigger().getNavContingentValues());
					setupNAVRuleTriggerAll(agmt.getImPrincipalFixedTrigger().getNavContingentValues());
					enterNextStage();
				}
				if (agmt.getImPrincipalFixedTrigger().getRatingContingentValues() != null) {
					setupRatingRuleTriggerRow(agmt.getImPrincipalFixedTrigger().getRatingContingentValues());
					setupRatingRuleTriggerAll(agmt.getImPrincipalFixedTrigger().getRatingContingentValues());
					enterNextStage();
				}
			}
		}if (agmt.getImCounterpartyFixedTrigger() != null){
			if (agmt.getImCounterpartyFixedTrigger().getTimeContingentValues() != null ||
					agmt.getImCounterpartyFixedTrigger().getNavContingentValues() != null ||
					agmt.getImCounterpartyFixedTrigger().getRatingContingentValues()  != null){
				if (agmt.getImCounterpartyFixedTrigger().getTimeContingentValues() != null){
					setupTimeRuleTriggerRow(agmt.getImCounterpartyFixedTrigger().getTimeContingentValues());
					setupTimeRuleTirggerAll(agmt.getImCounterpartyFixedTrigger().getTimeContingentValues());
					enterNextStage();
				}
				if (agmt.getImCounterpartyFixedTrigger().getNavContingentValues() != null){
					setupNAVRuleTriggerRow(agmt.getImCounterpartyFixedTrigger().getNavContingentValues());
					setupNAVRuleTriggerAll(agmt.getImCounterpartyFixedTrigger().getNavContingentValues());
					enterNextStage();
				}
				if (agmt.getImCounterpartyFixedTrigger().getRatingContingentValues() != null){
					setupRatingRuleTriggerRow(agmt.getImCounterpartyFixedTrigger().getRatingContingentValues());
					setupRatingRuleTriggerAll(agmt.getImCounterpartyFixedTrigger().getRatingContingentValues());
					enterNextStage();
				}

			}
		}
    }

	public void setupNAVRuleTriggerRow(NavRuleTrigger navRuleTrigger) throws Exception {
		if (navRuleTrigger.getRow() != null && navRuleTrigger.getRow().size() > 0){
			for (int i=0;i<navRuleTrigger.getRow().size();i++){
				if (navRuleTrigger.getRow().get(i).getNavAmt() != null){
    				element("ap.navRuleTrigger.navAmt.row",String.valueOf(i)).input(navRuleTrigger.getRow().get(i).getNavAmt().getRealValue());
    			}
    			if (navRuleTrigger.getRow().get(i).getThreshold() != null){
    				element("ap.navRuleTrigger.threshold.row",String.valueOf(i)).input(navRuleTrigger.getRow().get(i).getThreshold().getRealValue());
    			}
    			if (navRuleTrigger.getRow().get(i).getThresholdCurrency() != null){
    				element("ap.navRuleTrigger.thresholdCurrency.row",String.valueOf(i)).selectByVisibleText(navRuleTrigger.getRow().get(i).getThresholdCurrency().getRealValue());
    			}
    			if (navRuleTrigger.getRow().get(i).getThresholdType() != null){
    				element("ap.navRuleTrigger.thresholdType.row",String.valueOf(i),navRuleTrigger.getRow().get(i).getThresholdType().value()).click();
    			}
    			if (navRuleTrigger.getRow().get(i).getMinimumTransfer() != null){
    				element("ap.navRuleTrigger.minimumTransfer.row",String.valueOf(i)).input(navRuleTrigger.getRow().get(i).getMinimumTransfer().getRealValue());
    			}
    			if (navRuleTrigger.getRow().get(i).getMinimumTransferType() != null){
    				element("ap.navRuleTrigger.minimumTransferType.row",String.valueOf(i),navRuleTrigger.getRow().get(i).getMinimumTransferType().value()).click();
    			}
    			if (navRuleTrigger.getRow().get(i).getRoundingAmount() != null){
    				element("ap.navRuleTrigger.roundingAmount.row",String.valueOf(i)).input(navRuleTrigger.getRow().get(i).getRoundingAmount().getRealValue());
    			}
    			if (navRuleTrigger.getRow().get(i).getNavBasedIA() != null){
    				element("ap.navRuleTrigger.navbasedIa.row",String.valueOf(i)).input(navRuleTrigger.getRow().get(i).getNavBasedIA().getRealValue());
    			}
    			if (navRuleTrigger.getRow().get(i).getNavBasedIACurrency() != null){
    				element("ap.navRuleTrigger.navbasedIaCurrency.row",String.valueOf(i)).selectByVisibleText(navRuleTrigger.getRow().get(i).getNavBasedIACurrency().getRealValue());
    			}
    			if (i != navRuleTrigger.getRow().size()-1){
    				//click + button
    				element("ap.navRuleTrigger.add.row").click();
    			}
			}
		}
	}

	public void setupNAVRuleTriggerAll(NavRuleTrigger navRuleTrigger) throws Exception {
		if (navRuleTrigger.getAll() != null){
    			if (navRuleTrigger.getAll().getThreshold() != null){
    				element("ap.navRuleTrigger.threshold.all").input(navRuleTrigger.getAll().getThreshold().getRealValue());
    				element("ap.navRuleTrigger.threshold.applyAllSelected").click();
    			}
    			if (navRuleTrigger.getAll().getThresholdType() != null){
    				element("ap.navRuleTrigger.thresholdType.all",navRuleTrigger.getAll().getThresholdType().value()).click();
    				element("ap.navRuleTrigger.thresholdType.applyAllSelected").click();
    			}
    			if (navRuleTrigger.getAll().getMinimumTransfer() != null){
    				element("ap.navRuleTrigger.minimumTransfer.all").input(navRuleTrigger.getAll().getMinimumTransfer().getRealValue());
    				element("ap.navRuleTrigger.minimumTransfer.applyAllSelected").click();
    			}

    			if (navRuleTrigger.getAll().getMinimumTransferType() != null){
    				element("ap.navRuleTrigger.minimumTransferType.all",navRuleTrigger.getAll().getMinimumTransferType().value()).click();
    				element("ap.navRuleTrigger.minimumTransferType.applyAllSelected").click();
    			}

    			if (navRuleTrigger.getAll().getRoundingAmount() != null){
    				element("ap.navRuleTrigger.roundingAmount.all").input(navRuleTrigger.getAll().getRoundingAmount().getRealValue());
    				element("ap.navRuleTrigger.roundingAmount.applyAllSelected").click();
    			}
    			if (navRuleTrigger.getAll().getNavBasedIA() != null){
    				element("ap.navRuleTrigger.navbasedIa.all").input(navRuleTrigger.getAll().getNavBasedIA().getRealValue());
    				element("ap.navRuleTrigger.navbasedIa.applyAllSelected").click();
    			}
    		}
	}
    


	public void setupRatingRuleTriggerRow(RatingRuleTrigger ratingRuleTrigger) throws Exception {
		if (ratingRuleTrigger.getRow() != null && ratingRuleTrigger.getRow().size() > 0){
			for (int i=0;i<ratingRuleTrigger.getRow().size();i++){
				if (ratingRuleTrigger.getRow().get(i).getRatingLevel() != null){
					element("ap.ratingRuleTrigger.rating",ratingRuleTrigger.getRow().get(i).getRatingLevel().getRealValue()).check(true);
				}
				if (ratingRuleTrigger.getRow().get(i).getThreshold() != null){
					element("ap.ratingRuleTrigger.threshold.row",String.valueOf(i)).input(ratingRuleTrigger.getRow().get(i).getThreshold().getRealValue());
				}
				if (ratingRuleTrigger.getRow().get(i).getThresholdType() != null){
					element("ap.ratingRuleTrigger.thresholdType.row",String.valueOf(i),ratingRuleTrigger.getRow().get(i).getThresholdType().value()).click();
				}
				if (ratingRuleTrigger.getRow().get(i).getMinimumTransfer() != null){
					element("ap.ratingRuleTrigger.minimumTransfer.row",String.valueOf(i)).input(ratingRuleTrigger.getRow().get(i).getMinimumTransfer().getRealValue());
				}
				if (ratingRuleTrigger.getRow().get(i).getMinimumTransferType() != null){
					element("ap.ratingRuleTrigger.minimumTransferType.row",String.valueOf(i),ratingRuleTrigger.getRow().get(i).getMinimumTransferType().value()).click();
				}
				if (ratingRuleTrigger.getRow().get(i).getRoundingAmount() != null){
					element("ap.ratingRuleTrigger.roundingAmount.row",String.valueOf(i)).input(ratingRuleTrigger.getRow().get(i).getRoundingAmount().getRealValue());
				}
				if (ratingRuleTrigger.getRow().get(i).getRatingBasedIA() != null){
					element("ap.ratingRuleTrigger.ratingBasedIa.row",String.valueOf(i)).input(ratingRuleTrigger.getRow().get(i).getRatingBasedIA().getRealValue());
				}
				if (ratingRuleTrigger.getRow().get(i).getRatingBasedIAType() != null){
					element("ap.ratingRuleTrigger.ratingBasedIaType.row",String.valueOf(i), ratingRuleTrigger.getRow().get(i).getRatingBasedIAType().value()).click();
				}
				if (i != ratingRuleTrigger.getRow().size()-1){
					//click + button
					element("ap.ratingRuleTrigger.add.row").click();
				}
			}
		}
	}
	public void setupRatingRuleTriggerAll(RatingRuleTrigger ratingRuleTrigger) throws Exception {
		if (ratingRuleTrigger.getAll() != null) {
			if (ratingRuleTrigger.getAll().isSelectAll() != null) {
				element("ap.ratingRuleTrigger.selectAll").check(ratingRuleTrigger.getAll().isSelectAll());
			}
			if (ratingRuleTrigger.getAll().getThreshold() != null) {
				//set apply all value
				element("ap.ratingRuleTrigger.threshold.all").input(ratingRuleTrigger.getAll().getThreshold().getRealValue());
				//click apply all
				element("ap.ratingRuleTrigger.threshold.applyAllSelected").click();
			}
			if (ratingRuleTrigger.getAll().getThresholdType() != null) {
				//choose 1234567 for threshold type
				element("ap.ratingRuleTrigger.thresholdType.all", ratingRuleTrigger.getAll().getThresholdType().value()).click();
				//click apply all threshold type
				element("ap.ratingRuleTrigger.thresholdType.applyAllSelected").click();
			}
			if (ratingRuleTrigger.getAll().getMinimumTransfer() != null) {
				//set minimun transfer value
				element("ap.ratingRuleTrigger.minimumTransfer.all").input(ratingRuleTrigger.getAll().getMinimumTransfer().getRealValue());
				//click apply all minimum transfer value
				element("ap.ratingRuleTrigger.minimumTransfer.applyAllSelected").click();
			}
			if (ratingRuleTrigger.getAll().getMinimumTransferType() != null) {
				//choose 2344567 for minimum transfer type
				element("ap.ratingRuleTrigger.minimumTransferType.all", ratingRuleTrigger.getAll().getMinimumTransferType().value()).click();
				//click apply all minimum transfer type
				element("ap.ratingRuleTrigger.minimumTransferType.applyAllSelected").click();
			}
			if (ratingRuleTrigger.getAll().getRoundingAmount() != null) {
				//set rounding amount all value
				element("ap.ratingRuleTrigger.roundingAmount.all").input(ratingRuleTrigger.getAll().getRoundingAmount().getRealValue());
				//click apply all
				element("ap.ratingRuleTrigger.roundingAmount.applyAllSelected").click();
			}
//			if (ratingRuleTrigger.getAll().getRatingBasedIA() != null) {
//				//set time based ia all
//				element("").input(ratingRuleTrigger.getAll().getRatingBasedIA().getRealValue());
//				//click apply all time based ia value
//				element("").click();
//			}
//			if (ratingRuleTrigger.getAll().getRatingBasedIAType() != null) {
//				//choose 2378910 rating based ia type
//				element("", ratingRuleTrigger.getAll().getRatingBasedIAType().value()).click();
//				//click apply all ating based ia type
//				element("").click();
//			}
		}
	}
    
    
    



	public void setupTimeRuleTirggerAll(TimeRuleTrigger timeRuleTrigger) throws Exception{
		if (timeRuleTrigger.getAll() != null) {
			if (timeRuleTrigger.getAll().getThreshold() != null) {
				//set apply all value
				element("ap.timeRuleTrigger.threshold.all").input(timeRuleTrigger.getAll().getThreshold().getRealValue());
				//click apply all
				element("ap.timeRuleTrigger.threshold.applyAllSelected").click();
			}
			if (timeRuleTrigger.getAll().getThresholdType() != null) {
				//choose 1234567 for threshold type
				element("ap.timeRuleTrigger.thresholdType.all", timeRuleTrigger.getAll().getThresholdType().value()).click();
				//click apply all threshold type
				element("ap.timeRuleTrigger.thresholdType.applyAllSelected").click();
			}
			if (timeRuleTrigger.getAll().getMinimumTransfer() != null) {
				//set minimun transfer value
				element("ap.timeRuleTrigger.minimumTransfer.all").input(timeRuleTrigger.getAll().getMinimumTransfer().getRealValue());
				//click apply all minimum transfer value
				element("ap.timeRuleTrigger.minimumTransfer.applyAllSelected").click();
			}
			if (timeRuleTrigger.getAll().getMinimumTransferType() != null) {
				//choose 2344567 for minimum transfer type
				element("ap.timeRuleTrigger.minimumTransferType.all", timeRuleTrigger.getAll().getMinimumTransferType().value()).click();
				//click apply all minimum transfer type
				element("ap.timeRuleTrigger.minimumTransferType.applyAllSelected").click();
			}
			if (timeRuleTrigger.getAll().getRoundingAmount() != null) {
				//set rounding amount all value
				element("ap.timeRuleTrigger.roundingAmount.all").input(timeRuleTrigger.getAll().getRoundingAmount().getRealValue());
				//click apply all
				element("ap.timeRuleTrigger.roundingAmount.applyAllSelected").click();
			}
			if (timeRuleTrigger.getAll().getTimeBasedIA() != null) {
				//set time based ia all
				element("ap.timeRuleTrigger.timebasedIa.all").input(timeRuleTrigger.getAll().getTimeBasedIA().getRealValue());
				//click apply all time based ia value
				element("ap.timeRuleTrigger.timebasedIa.applyAllSelected").click();
			}
		}
	}
    

	public void setupTimeRuleTriggerRow(TimeRuleTrigger timeRuleTrigger) throws Exception{
		if (timeRuleTrigger.getRow() != null && timeRuleTrigger.getRow().size() > 0 ){
			for (int i=0; i<timeRuleTrigger.getRow().size(); i++){
				if (timeRuleTrigger.getRow().get(i).getDate() != null){
					element("ap.timeRuleTrigger.date.row",String.valueOf(i)).setValue(timeRuleTrigger.getRow().get(i).getDate().getRealValue());
				}
				if (timeRuleTrigger.getRow().get(i).getThreshold() != null){
					element("ap.timeRuleTrigger.threshold.row",String.valueOf(i)).input(timeRuleTrigger.getRow().get(i).getThreshold().getRealValue());
				}
				if (timeRuleTrigger.getRow().get(i).getThresholdCurrency() != null){
					element("ap.timeRuleTrigger.thresholdCurrency.row",String.valueOf(i)).selectByVisibleText(timeRuleTrigger.getRow().get(i).getThresholdCurrency().getRealValue());
				}
				if (timeRuleTrigger.getRow().get(i).getThresholdType() != null){
					element("ap.timeRuleTrigger.thresholdType.row",String.valueOf(i),timeRuleTrigger.getRow().get(i).getThresholdType().value()).click();
				}
				if (timeRuleTrigger.getRow().get(i).getMinimumTransfer() != null){
					element("ap.timeRuleTrigger.minimumTransfer.row",String.valueOf(i)).input(timeRuleTrigger.getRow().get(i).getMinimumTransfer().getRealValue());
				}
				if (timeRuleTrigger.getRow().get(i).getMinimumTransferType() != null){
					element("ap.timeRuleTrigger.minimumTransferType.row",String.valueOf(i),timeRuleTrigger.getRow().get(i).getMinimumTransferType().value()).click();
				}
				if (timeRuleTrigger.getRow().get(i).getRoundingAmount() != null){
					element("ap.timeRuleTrigger.roundingAmount.row",String.valueOf(i)).input(timeRuleTrigger.getRow().get(i).getRoundingAmount().getRealValue());
				}
				if (timeRuleTrigger.getRow().get(i).getTimeBasedIA() != null){
					element("ap.timeRuleTrigger.timebasedIa.row",String.valueOf(i)).input(timeRuleTrigger.getRow().get(i).getTimeBasedIA().getRealValue());
				}
				if (timeRuleTrigger.getRow().get(i).getTimeBasedIACurrency() != null){
					element("ap.timeRuleTrigger.timebasedIaCurrency.row",String.valueOf(i)).selectByVisibleText(timeRuleTrigger.getRow().get(i).getTimeBasedIACurrency().getRealValue());
				}
				if (i != timeRuleTrigger.getRow().size()-1){
					//click + button
					element("ap.timeRuleTrigger.add.row").click();
				}
			}
		}
	}


    
    
    /**
     * enter next stage while setup agreement
     */
    public void enterNextStage() throws Exception {
    	waitThat().document().toBeReady();
    	waitThat().jQuery().toBeInactive();
        element("ap.next").clickByJavaScript();
		PageHelper.d33640Workaround();
    }
}
