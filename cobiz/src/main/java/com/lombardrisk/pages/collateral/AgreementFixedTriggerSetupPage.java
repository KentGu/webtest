package com.lombardrisk.pages.collateral;

import com.lombardrisk.pojo.*;
import com.lombardrisk.util.PageHelper;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;


/**
 * Created by Kenny Wang on 2/3/2016.
 */
public class AgreementFixedTriggerSetupPage extends PageBase {
    public AgreementFixedTriggerSetupPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    public void saveAndExit() throws Exception{
		element("ap.save.and.exit").clickByJavaScript();
	}

    public void setupAgreementFixedTrigger(Agreement agmt) throws Exception {
		editAgreementFixedTrigger(agmt);
        enterNextStage();
		if(agmt.getFixedTriggerWarning() != null || agmt.getImFixedTriggerWarning() != null){
			Message message = null;
			if(agmt.getFixedTriggerWarning() != null)
				message = agmt.getFixedTriggerWarning();
			else if(agmt.getImFixedTriggerWarning() != null)
				message = agmt.getImFixedTriggerWarning();
			if (message != null) {
				if (message.getContent() != null && message.isAccept() != null && message.isAccept())
					element("ap.warning.message.ok", message.getContent().getRealValue()).clickByJavaScript();
			}else {
				logger.error("Message is null");
				throw new NullPointerException();
			}
		}
		waitThat().document().toBeReady();
		waitThat().jQuery().toBeInactive();
		//waitThat("ap.additionalFields").toBePresent();
        //waitThat("ap.tab.additionalFields").cssValueOf("style").toBeEmpty();
    }

	public void editAgreementFixedTrigger(Agreement agmt) throws Exception {
		if (agmt.getNettingIAandMtM() == null ||
				(agmt.getNettingIAandMtM()!= null && agmt.getNettingIAandMtM().value().equals("Net"))||
				(agmt.getNettingIAandMtM()!= null && agmt.getNettingIAandMtM().value().equals("Auto-Net"))){
			setupBothFixedTrigger(agmt);
		} else if (agmt.getBusinessLine() != null && agmt.getBusinessLine().value().equals("ETD")) {

        } else {
            if (agmt.getApplyThresholdMTARounding()==null ||
					(agmt.getApplyThresholdMTARounding() != null && agmt.getApplyThresholdMTARounding().value().equalsIgnoreCase(ApplyThresholdMTARoundingType.BOTH.toString()))) {
				//click vm threshold mta rounding
				element("ap.detail.vmThresholdMtaRounding").click();
				//this method is suite for net agreement or not-net agreement both
				setupBothFixedTrigger(agmt);
			}
			if (agmt.getApplyThresholdMTARounding() != null){
				if(agmt.getApplyThresholdMTARounding().value().equalsIgnoreCase(ApplyThresholdMTARoundingType.SEPARATE.toString())){
					//click VM/IM Threshold-MTA-Rounding Separated
					element("ap.detail.vmimThresholdMtaRoundingSeparated").click();
					setupSeperatedFixedTrigger(agmt);
				}
			}
		}
		if (agmt.isFinra4210Adjustment() != null){
			element("ap.detail.finra4210Adjustment").check(agmt.isFinra4210Adjustment());
			if (agmt.isFinra4210Adjustment() && agmt.getFinra4210Applicability() != null){
				for (Finra4210Applicability applicability : agmt.getFinra4210Applicability()) {
					element("ap.detail.finra4210Applicability").selectByVisibleText(applicability.value());
				}
			}
		}
//        enterNextStage();
//		waitThat("ap.additionalFields").toBePresent();
        //waitThat("ap.tab.additionalFields").cssValueOf("style").toBeEmpty();
    }

    public void setupDetails(Agreement agmt) throws Exception{

    	if (agmt.isApplyOrganisationThreshold() != null){
			element("ap.detail.applyOrganisationThreshold").check(agmt.isApplyOrganisationThreshold());
		}
		if (agmt.getRounding() != null) {
	            if (agmt.getRounding().getDelivery() != null) {
	                element("ap.detail.vm.delivery").selectByVisibleText(agmt.getRounding().getDelivery().value());
	            }
	            if (agmt.getRounding().getRecall() != null) {
	                element("ap.detail.vm.decall").selectByVisibleText(agmt.getRounding().getRecall().value());
	            }
	            if (agmt.getSplitTheDifferenceValue() != null){
	                element("ap.detail.vm.splitTheDifferenceValue").input(agmt.getSplitTheDifferenceValue().getRealValue());
	            }
	            if (agmt.getSplitTheDifferenceType() != null){
	                element("ap.detail.vm.splitTheDifferenceType").selectByVisibleText(agmt.getSplitTheDifferenceType().getRealValue());
	            }
	        }
      }

    public void setupVmDetail(Agreement agmt) throws Exception{

    	if (agmt.isApplyOrganisationThreshold() != null){
			element("ap.detail.applyOrganisationThreshold").check(agmt.isApplyOrganisationThreshold());
		}
		if (agmt.getVMrounding() != null) {
	            if (agmt.getVMrounding().getDelivery() != null) {
	                element("ap.detail.vm.delivery").selectByVisibleText(agmt.getVMrounding().getDelivery().value());
	            }
	            if (agmt.getVMrounding().getRecall() != null) {
	                element("ap.detail.vm.decall").selectByVisibleText(agmt.getVMrounding().getRecall().value());
	            }
	            if (agmt.getSplitTheDifferenceValue() != null){
	                element("ap.detail.vm.splitTheDifferenceValue").input(agmt.getSplitTheDifferenceValue().getRealValue());
	            }
	            if (agmt.getSplitTheDifferenceType() != null){
	                element("ap.detail.vm.splitTheDifferenceType").selectByVisibleText(agmt.getSplitTheDifferenceType().getRealValue());
	            }
	        }
    }

    public void setupImDetail(Agreement agmt) throws Exception{
    	if (agmt.getIMrounding() != null) {
            if (agmt.getIMrounding().getDelivery() != null) {
                element("ap.detail.vm.delivery").selectByVisibleText(agmt.getIMrounding().getDelivery().value());
            }
            if (agmt.getIMrounding().getRecall() != null) {
                element("ap.detail.vm.decall").selectByVisibleText(agmt.getIMrounding().getRecall().value());
            }
            if (agmt.getSplitTheDifferenceValue() != null){
                element("ap.detail.vm.splitTheDifferenceValue").input(agmt.getSplitTheDifferenceValue().getRealValue());
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

    public void setupAgreementRounding(Agreement agmt) throws Exception {
        if (agmt.getRounding() != null) {
            if (agmt.getRounding().getDelivery() != null) {
                element("ap.vm.delivery").selectByVisibleText(agmt.getRounding().getDelivery().value());
            }
            if (agmt.getRounding().getRecall() != null) {
                element("ap.vm.recall").selectByVisibleText(agmt.getRounding().getRecall().value());
            }
            if (agmt.getSplitTheDifferenceValue() != null){
                element("ap.vm.split.diff.value").input(agmt.getSplitTheDifferenceValue().getRealValue());
            }
            if (agmt.getSplitTheDifferenceType() != null){
                element("ap.vm.split.diff.type").selectByVisibleText(agmt.getSplitTheDifferenceType().getRealValue());
            }
        }
    }

    public void searchOrganisation(OrganisationSearch search) throws Exception {
        if (search.getCriteria() != null)
            element("ap.frame.criteria").selectByVisibleText(search.getCriteria().value());
        if (search.getType() != null)
            element("ap.frame.type").selectByVisibleText(search.getType().value());
        if (search.getFilter() != null)
            element("ap.frame.filter").input(search.getFilter().getRealValue());
        element("ap.frame.search").click();

        if (search.getLinkText() != null)
            element("ap.frame.link", search.getLinkText().getRealValue()).click();
    }

    public void setupBothFixedTrigger(Agreement agmt) throws Exception{
		setupDetails(agmt);
		setupPrincipalFixedTrigger(agmt);
		setupCounterpartyFixedTrigger(agmt);
    }

    public void setupSeperatedFixedTrigger(Agreement agmt) throws Exception{
    	setupVmDetail(agmt);
		setupVmPrincipalFixedTrigger(agmt);
		setupVmCounterpartyFixedTrigger(agmt);
		enterNextStage();
		if(agmt.getVmFixedTriggerWarning() != null && agmt.getVmFixedTriggerWarning().getContent() != null
				&& agmt.getVmFixedTriggerWarning().isAccept() != null && agmt.getVmFixedTriggerWarning().isAccept()){
			element("ap.warning.message.ok", agmt.getVmFixedTriggerWarning().getContent().getRealValue()).clickByJavaScript();
		}
		setupImDetail(agmt);
		setupImPrincipalFixedTrigger(agmt);
		setupImCounterpartyFixedTrigger(agmt);
    }

    public void setupPrincipalFixedTrigger(Agreement agmt) throws Exception{
    	//set principal fixtrigger
		if (agmt.getPrincipalFixedTrigger() != null){
			if (agmt.getPrincipalFixedTrigger().getFixedValues() != null){
				//click fixed values
				element("ap.principalFixedValues").click();
				//setup set principal fixed values threshold parameters
				if (agmt.getPrincipalFixedTrigger().getFixedValues().getThreshold() != null){
					if (agmt.getPrincipalFixedTrigger().getFixedValues().getThreshold().getInfinity() != null){
						element("ap.principalFixedValues.threshold.infinity").click();
					}
					if (agmt.getPrincipalFixedTrigger().getFixedValues().getThreshold().getFixedAmount() != null){
						//click fixed amount
						element("ap.principalFixedValues.threshold.fixedAmount").click();
						if (agmt.getPrincipalFixedTrigger().getFixedValues().getThreshold().getFixedAmount().getAmount() != null){
							element("ap.principalFixedValues.threshold.fixedAmountValue").input(agmt.getPrincipalFixedTrigger().getFixedValues().getThreshold().getFixedAmount().getAmount().getRealValue());
						}
						if (agmt.getPrincipalFixedTrigger().getFixedValues().getThreshold().getFixedAmount().getCurrency() != null){
							element("ap.principalFixedValues.threshold.fixedAmountCurrency").selectByVisibleText(agmt.getPrincipalFixedTrigger().getFixedValues().getThreshold().getFixedAmount().getCurrency().getRealValue());
						}
					}
					if (agmt.getPrincipalFixedTrigger().getFixedValues().getThreshold().getPercentageOfNAV() != null){
						//click % of NAV
						element("ap.principalFixedValues.threshold.percentageOfNav").click();
						element("ap.principalFixedValues.threshold.percentageOfNavValue").input(agmt.getPrincipalFixedTrigger().getFixedValues().getThreshold().getPercentageOfNAV().getRealValue());
					}
					if (agmt.getPrincipalFixedTrigger().getFixedValues().getThreshold().getPercentageOfNAVAddIA() != null){
						//click % of NAM+IA
						element("ap.principalFixedValues.threshold.percentageOfNavAddIa").click();
						element("ap.principalFixedValues.threshold.percentageOfNavAddIaValue").input(agmt.getPrincipalFixedTrigger().getFixedValues().getThreshold().getPercentageOfNAVAddIA().getRealValue());
					}
					if (agmt.getPrincipalFixedTrigger().getFixedValues().getThreshold().getPercentageOfIAAddMTM() != null){
						//click % of IA+MTM
						element("ap.principalFixedValues.threshold.percentageOfIaAddMtm").click();
						element("ap.principalFixedValues.threshold.percentageOfIaAddMtmValue").input(agmt.getPrincipalFixedTrigger().getFixedValues().getThreshold().getPercentageOfIAAddMTM().getRealValue());
					}
					if (agmt.getPrincipalFixedTrigger().getFixedValues().getThreshold().getPercentageOfEXPSubstractCOL() != null){
						//click % of EXP-COL
						element("ap.principalFixedValues.threshold.percentageOfExpSubstractCol").click();
						element("ap.principalFixedValues.threshold.percentageOfExpSubstractColValue").input(agmt.getPrincipalFixedTrigger().getFixedValues().getThreshold().getPercentageOfEXPSubstractCOL().getRealValue());
					}
					if (agmt.getPrincipalFixedTrigger().getFixedValues().getThreshold().getPercentageOfNotional() != null){
						//click % of notional
						element("ap.principalFixedValues.threshold.percentageOfNotional").click();
						element("ap.principalFixedValues.threshold.percentageOfNotionalValue").input(agmt.getPrincipalFixedTrigger().getFixedValues().getThreshold().getPercentageOfNotional().getRealValue());
					}
				}

				//set principal fixed values minimum transfer
				if (agmt.getPrincipalFixedTrigger().getFixedValues().getMinimumTransfer() != null){
					if (agmt.getPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getFixedAmount() != null){
						//click minimum transfer fixed amount
						element("ap.principalFixedValues.minimumTransfer.fixedAmount").click();
						if (agmt.getPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getFixedAmount().getAmount() != null){
							element("ap.principalFixedValues.minimumTransfer.fixedAmountValue").input(agmt.getPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getFixedAmount().getAmount().getRealValue());
						}
						if (agmt.getPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getFixedAmount().getCurrency() != null){
							element("ap.principalFixedValues.minimumTransfer.fixedAmountCurrency").selectByVisibleText(agmt.getPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getFixedAmount().getCurrency().getRealValue());
						}

						if (agmt.getPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfNAV() != null){
							//click % of NAV
							element("ap.principalFixedValues.minimumTransfer.percentageOfNav").click();
							element("ap.principalFixedValues.minimumTransfer.percentageOfNavValue").input(agmt.getPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfNAV().getRealValue());
						}
						if (agmt.getPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfNAVAddIA() != null){
							//click % of NAM+IA
							element("ap.principalFixedValues.minimumTransfer.percentageOfNavAddIa").click();
							element("ap.principalFixedValues.minimumTransfer.percentageOfNavAddIaValue").input(agmt.getPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfNAVAddIA().getRealValue());
						}
						if (agmt.getPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfNAVAddThreshold() != null){
							//click % of IA+Threshold
							element("ap.principalFixedValues.minimumTransfer.percentageOfIaAddThreshold").click();
							element("ap.principalFixedValues.minimumTransfer.percentageOfIaAddThresholdValue").input(agmt.getPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfNAVAddThreshold().getRealValue());
						}

						if (agmt.getPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfIAAddMTM() != null){
							//click % of IA+MTM
							element("ap.principalFixedValues.minimumTransfer.percentageOfIaAddMtm").click();
							element("ap.principalFixedValues.minimumTransfer.percentageOfIaAddMtmValue").input(agmt.getPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfIAAddMTM().getRealValue());
						}
						if (agmt.getPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfEXPSubstractCOL() != null){
							//click % of EXP-COL
							element("ap.principalFixedValues.minimumTransfer.percentageOfExpSubstractCol").click();
							element("ap.principalFixedValues.minimumTransfer.percentageOfExpSubstractColValue").input(agmt.getPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfEXPSubstractCOL().getRealValue());
						}
						if (agmt.getPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfNotional() != null){
							//click % of notional
							element("ap.principalFixedValues.minimumTransfer.percentageOfNotional").click();
							element("ap.principalFixedValues.minimumTransfer.percentageOfNotionalValue").input(agmt.getPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfNotional().getRealValue());
						}
					}
				}
				if (agmt.getPrincipalFixedTrigger().getFixedValues().getRoundingAmount() != null){
					//set rounding amount
					if (agmt.getPrincipalFixedTrigger().getFixedValues().getRoundingAmount().getAmount() != null){
						element("ap.principalFixedValues.roundingAmount").input(agmt.getPrincipalFixedTrigger().getFixedValues().getRoundingAmount().getAmount().getRealValue());
					}
					if (agmt.getPrincipalFixedTrigger().getFixedValues().getRoundingAmount().getCurrency() != null){
						element("ap.principalFixedValues.roundingAmountCurrency").selectByVisibleText(agmt.getPrincipalFixedTrigger().getFixedValues().getRoundingAmount().getCurrency().getRealValue());
					}
				}
			}
				//set principal fixed trigger time contingent values
			if (agmt.getPrincipalFixedTrigger().getTimeContingentValues() != null){
				//click time contingent
				element("ap.principalTimeContingentValue").click();
			}
			//set principal fixed trigger NAV Contingent values
			if (agmt.getPrincipalFixedTrigger().getNavContingentValues() != null){
				//click NAV contingent values
				element("ap.principalNavContingentValue").click();
			}
			//set principal fixed trigger rating contingent value
			if (agmt.getPrincipalFixedTrigger().getRatingContingentValues() != null){
				//click rating contingent values
				element("ap.principalRatingContingentValue").click();
				//click use principal rating
				if (agmt.getPrincipalFixedTrigger().getRatingContingentValues().isUsePrincipalRating() != null){
					element("ap.principalRatingContingentValue.usePrincipalRating").check(agmt.getPrincipalFixedTrigger().getRatingContingentValues().isUsePrincipalRating());
				}
				//click use credit support provider rating and search org
				if (agmt.getPrincipalFixedTrigger().getRatingContingentValues().getUseCounterpartyRating() != null){
					//click Use Credit Support Provider Rating
					element("ap.principalRatingContingentValue.useProviderRating").click();
					element("ap.principalRatingContingentValue.searchOrg").click();
		            waitThat("ap.frame.principalRatingContingentValue").frameToBeAvailableAndSwitchToIt();
		            searchOrganisation(agmt.getPrincipalFixedTrigger().getRatingContingentValues().getUseCounterpartyRating());
		            switchTo().defaultContent();
				}
				//choose reference rating agencies one or more
				if (agmt.getPrincipalFixedTrigger().getRatingContingentValues().getReferenceRatingAgencies() != null &&
						agmt.getPrincipalFixedTrigger().getRatingContingentValues().getReferenceRatingAgencies().size() >0){
					for (int i=0;i<agmt.getPrincipalFixedTrigger().getRatingContingentValues().getReferenceRatingAgencies().size();i++){
						if(agmt.getPrincipalFixedTrigger().getRatingContingentValues().getReferenceRatingAgencies().get(i).equals(ReferenceRatingAgenciesType.FITCH)){
							element("ap.principalRatingContingentValue.fitch").check(true);
						}
						if(agmt.getPrincipalFixedTrigger().getRatingContingentValues().getReferenceRatingAgencies().get(i).equals(ReferenceRatingAgenciesType.MOODYS)){
							element("ap.principalRatingContingentValue.moodys").check(true);
						}
						if(agmt.getPrincipalFixedTrigger().getRatingContingentValues().getReferenceRatingAgencies().get(i).equals(ReferenceRatingAgenciesType.SAND_P)){
							element("ap.principalRatingContingentValue.sandp").check(true);
						}
					}

				}
				//choose debt classifications
				if (agmt.getPrincipalFixedTrigger().getRatingContingentValues().getDebtClassification() != null &&
						agmt.getPrincipalFixedTrigger().getRatingContingentValues().getDebtClassification().size() > 0){
					for (int i=0;i<agmt.getPrincipalFixedTrigger().getRatingContingentValues().getDebtClassification().size();i++){
						if (agmt.getPrincipalFixedTrigger().getRatingContingentValues().getDebtClassification().get(i).equals(DebtClassificationType.SEC)){
							element("ap.principalRatingContingentValue.sec").click();
						}
						if (agmt.getPrincipalFixedTrigger().getRatingContingentValues().getDebtClassification().get(i).equals(DebtClassificationType.SEN)){
							element("ap.principalRatingContingentValue.sen").click();
						}
						if (agmt.getPrincipalFixedTrigger().getRatingContingentValues().getDebtClassification().get(i).equals(DebtClassificationType.SRSUB)){
							element("ap.principalRatingContingentValue.srub").click();
						}
						if (agmt.getPrincipalFixedTrigger().getRatingContingentValues().getDebtClassification().get(i).equals(DebtClassificationType.SUB)){
							element("ap.principalRatingContingentValue.sub").click();
						}
					}

				}
				if (agmt.getPrincipalFixedTrigger().getRatingContingentValues().getApplicationOfRatings() != null){
					if (agmt.getPrincipalFixedTrigger().getRatingContingentValues().getApplicationOfRatings().equals(ApplicationOfRatingsType.HIGHER)){
						element("ap.principalRatingContingentValue.useHigherRating").click();
					}
					if (agmt.getPrincipalFixedTrigger().getRatingContingentValues().getApplicationOfRatings().equals(ApplicationOfRatingsType.LOWER)){
						element("ap.principalRatingContingentValue.useLowerRating").click();
					}
				}
				if (agmt.getPrincipalFixedTrigger().getRatingContingentValues().getIfNotRatedByAnyAgency() != null){
					element("ap.principalRatingContingentValue.ifNotRated").click();
				}
			}
		}
    }

    public void setupCounterpartyFixedTrigger(Agreement agmt) throws Exception{
    	//set counterparty fixtrigger
		if (agmt.getCounterpartyFixedTrigger() != null){
			if (agmt.getCounterpartyFixedTrigger().getFixedValues() != null){
				//click fixed values
				element("ap.cptyFixedValues").click();
				//setup set counterparty fixed values threshold parameters
				if (agmt.getCounterpartyFixedTrigger().getFixedValues().getThreshold() != null){
					if (agmt.getCounterpartyFixedTrigger().getFixedValues().getThreshold().getInfinity() != null){
						element("ap.cptyFixedValues.threshold.infinity").click();
					}
					if (agmt.getCounterpartyFixedTrigger().getFixedValues().getThreshold().getFixedAmount() != null){
						//click cpty fixed amount
						element("ap.cptyFixedValues.threshold.fixedAmount").click();
						if (agmt.getCounterpartyFixedTrigger().getFixedValues().getThreshold().getFixedAmount().getAmount() != null){
							element("ap.cptyFixedValues.threshold.fixedAmountValue").input(agmt.getCounterpartyFixedTrigger().getFixedValues().getThreshold().getFixedAmount().getAmount().getRealValue());
						}
						if (agmt.getCounterpartyFixedTrigger().getFixedValues().getThreshold().getFixedAmount().getCurrency() != null){
							element("ap.cptyFixedValues.threshold.fixedAmountCurrency").selectByVisibleText(agmt.getCounterpartyFixedTrigger().getFixedValues().getThreshold().getFixedAmount().getCurrency().getRealValue());
						}
					}
					if (agmt.getCounterpartyFixedTrigger().getFixedValues().getThreshold().getPercentageOfNAV() != null){
						//click % of NAV
						element("ap.cptyFixedValues.threshold.percentageOfNav").click();
						element("ap.cptyFixedValues.threshold.percentageOfNavValue").input(agmt.getCounterpartyFixedTrigger().getFixedValues().getThreshold().getPercentageOfNAV().getRealValue());
					}
					if (agmt.getCounterpartyFixedTrigger().getFixedValues().getThreshold().getPercentageOfNAVAddIA() != null){
						//click % of NAM+IA
						element("ap.cptyFixedValues.threshold.percentageOfNavAddIa").click();
						element("ap.cptyFixedValues.threshold.percentageOfNavAddIaValue").input(agmt.getCounterpartyFixedTrigger().getFixedValues().getThreshold().getPercentageOfNAVAddIA().getRealValue());
					}
					if (agmt.getCounterpartyFixedTrigger().getFixedValues().getThreshold().getPercentageOfIAAddMTM() != null){
						//click % of IA+MTM
						element("ap.cptyFixedValues.threshold.percentageOfIaAddMtm").click();
						element("ap.cptyFixedValues.threshold.percentageOfIaAddMtmValue").input(agmt.getCounterpartyFixedTrigger().getFixedValues().getThreshold().getPercentageOfIAAddMTM().getRealValue());
					}
					if (agmt.getCounterpartyFixedTrigger().getFixedValues().getThreshold().getPercentageOfEXPSubstractCOL() != null){
						//click % of EXP-COL
						element("ap.cptyFixedValues.threshold.percentageOfExpSubstractCol").click();
						element("ap.cptyFixedValues.threshold.percentageOfExpSubstractColValue").input(agmt.getCounterpartyFixedTrigger().getFixedValues().getThreshold().getPercentageOfEXPSubstractCOL().getRealValue());
					}
					if (agmt.getCounterpartyFixedTrigger().getFixedValues().getThreshold().getPercentageOfNotional() != null){
						//click % of notional
						element("ap.cptyFixedValues.threshold.percentageOfNotional").click();
						element("ap.cptyFixedValues.threshold.percentageOfNotionalValue").input(agmt.getCounterpartyFixedTrigger().getFixedValues().getThreshold().getPercentageOfNotional().getRealValue());
					}
				}

				//set counterparty fixed values minimum trasfer
				if (agmt.getCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer() != null){
					if (agmt.getCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getFixedAmount() != null){
						//click fixed amount
						element("ap.cptyFixedValues.minimumTransfer.fixedAmount").click();
						if (agmt.getCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getFixedAmount().getAmount() != null){
							element("ap.cptyFixedValues.minimumTransfer.fixedAmountValue").input(agmt.getCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getFixedAmount().getAmount().getRealValue());
						}
						if (agmt.getCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getFixedAmount().getCurrency() != null){
							element("ap.cptyFixedValues.minimumTransfer.fixedAmountCurrency").selectByVisibleText(agmt.getCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getFixedAmount().getCurrency().getRealValue());
						}

						if (agmt.getCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfNAV() != null){
							//click % of NAV
							element("ap.cptyFixedValues.minimumTransfer.percentageOfNav").click();
							element("ap.cptyFixedValues.minimumTransfer.percentageOfNavValue").input(agmt.getCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfNAV().getRealValue());
						}
						if (agmt.getCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfNAVAddIA() != null){
							//click % of NAM+IA
							element("ap.cptyFixedValues.minimumTransfer.percentageOfNavAddIa").click();
							element("ap.cptyFixedValues.minimumTransfer.percentageOfNavAddIaValue").input(agmt.getCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfNAVAddIA().getRealValue());
						}
						if (agmt.getCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfNAVAddThreshold() != null){
							//click % of IA+Threshold
							element("ap.cptyFixedValues.minimumTransfer.percentageOfIaAddThreshold").click();
							element("ap.cptyFixedValues.minimumTransfer.percentageOfIaAddThresholdValue").input(agmt.getCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfNAVAddThreshold().getRealValue());
						}

						if (agmt.getCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfIAAddMTM() != null){
							//click % of IA+MTM
							element("ap.cptyFixedValues.minimumTransfer.percentageOfIaAddMtm").click();
							element("ap.cptyFixedValues.minimumTransfer.percentageOfIaAddMtmValue").input(agmt.getCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfIAAddMTM().getRealValue());
						}
						if (agmt.getCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfEXPSubstractCOL() != null){
							//click % of EXP-COL
							element("ap.cptyFixedValues.minimumTransfer.percentageOfExpSubstractCol").click();
							element("ap.cptyFixedValues.minimumTransfer.percentageOfExpSubstractColValue").input(agmt.getCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfEXPSubstractCOL().getRealValue());
						}
						if (agmt.getCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfNotional() != null){
							//click % of notional
							element("ap.cptyFixedValues.minimumTransfer.percentageOfNotional").click();
							element("ap.cptyFixedValues.minimumTransfer.percentageOfNotionalValue").input(agmt.getCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfNotional().getRealValue());
						}
					}
				}
				if (agmt.getCounterpartyFixedTrigger().getFixedValues().getRoundingAmount() != null){
					//set rounding amount
					if (agmt.getCounterpartyFixedTrigger().getFixedValues().getRoundingAmount().getAmount() != null){
						element("ap.cptyFixedValues.roundingAmount").input(agmt.getCounterpartyFixedTrigger().getFixedValues().getRoundingAmount().getAmount().getRealValue());
					}
					if (agmt.getCounterpartyFixedTrigger().getFixedValues().getRoundingAmount().getCurrency() != null){
						element("ap.cptyFixedValues.roundingAmountCurrency").selectByVisibleText(agmt.getCounterpartyFixedTrigger().getFixedValues().getRoundingAmount().getCurrency().getRealValue());
					}
				}
			}
				//set counterparty fixed trigger time contingent values
			if (agmt.getCounterpartyFixedTrigger().getTimeContingentValues() != null){
				//click time contingent
				element("ap.cptyTimeContingentValue").click();
			}
			//set counterparty fixed trigger NAV Contingent values
			if (agmt.getCounterpartyFixedTrigger().getNavContingentValues() != null){
				//click NAV contingent values
				element("ap.cptyNavContingentValue").click();
			}
			//set counterparty fixed trigger rating contingent value
			if (agmt.getCounterpartyFixedTrigger().getRatingContingentValues() != null){
				//click rating contingent values
				element("ap.cptyRatingContingentValue").click();
				if (agmt.getCounterpartyFixedTrigger().getRatingContingentValues().isUsePrincipalRating() != null){
					element("ap.cptyRatingContingentValue.usecptyRating").click();
				}
				if (agmt.getCounterpartyFixedTrigger().getRatingContingentValues().getUseCounterpartyRating() != null){
					//click Use Credit Support Provider Rating
					element("ap.cptyRatingContingentValue.useProviderRating").click();
					element("ap.cptyRatingContingentValue.searchOrg").click();
		            waitThat("ap.frame.cptyRatingContingentValue").frameToBeAvailableAndSwitchToIt();
		            searchOrganisation(agmt.getCounterpartyFixedTrigger().getRatingContingentValues().getUseCounterpartyRating());
		            switchTo().defaultContent();
				}
				if (agmt.getCounterpartyFixedTrigger().getRatingContingentValues().getReferenceRatingAgencies() != null &&
						agmt.getCounterpartyFixedTrigger().getRatingContingentValues().getReferenceRatingAgencies().size() > 0){
					element("ap.cptyRatingContingentValue.fitch").check(false);
					element("ap.cptyRatingContingentValue.moodys").check(false);
					element("ap.cptyRatingContingentValue.sandp").check(false);
					for (int i=0;i<agmt.getCounterpartyFixedTrigger().getRatingContingentValues().getReferenceRatingAgencies().size();i++){
						if(agmt.getCounterpartyFixedTrigger().getRatingContingentValues().getReferenceRatingAgencies().get(i).equals(ReferenceRatingAgenciesType.FITCH)){
							element("ap.cptyRatingContingentValue.fitch").check(true);
						}
						if(agmt.getCounterpartyFixedTrigger().getRatingContingentValues().getReferenceRatingAgencies().get(i).equals(ReferenceRatingAgenciesType.MOODYS)){
							element("ap.cptyRatingContingentValue.moodys").check(true);
						}
						if(agmt.getCounterpartyFixedTrigger().getRatingContingentValues().getReferenceRatingAgencies().get(i).equals(ReferenceRatingAgenciesType.SAND_P)){
							element("ap.cptyRatingContingentValue.sandp").check(true);
						}
					}
				}
				//choose debt classifications
				if (agmt.getCounterpartyFixedTrigger().getRatingContingentValues().getDebtClassification() != null &&
						agmt.getCounterpartyFixedTrigger().getRatingContingentValues().getDebtClassification().size() > 0){
					for (int i=0;i<agmt.getCounterpartyFixedTrigger().getRatingContingentValues().getDebtClassification().size();i++){
						if (agmt.getCounterpartyFixedTrigger().getRatingContingentValues().getDebtClassification().get(i).equals(DebtClassificationType.SEC)){
							element("ap.cptyRatingContingentValue.sec").click();
						}
						if (agmt.getCounterpartyFixedTrigger().getRatingContingentValues().getDebtClassification().get(i).equals(DebtClassificationType.SEN)){
							element("ap.cptyRatingContingentValue.sen").click();
						}
						if (agmt.getCounterpartyFixedTrigger().getRatingContingentValues().getDebtClassification().get(i).equals(DebtClassificationType.SRSUB)){
							element("ap.cptyRatingContingentValue.srub").click();
						}
						if (agmt.getCounterpartyFixedTrigger().getRatingContingentValues().getDebtClassification().get(i).equals(DebtClassificationType.SUB)){
							element("ap.cptyRatingContingentValue.sub").click();
						}
					}
				}
				if (agmt.getCounterpartyFixedTrigger().getRatingContingentValues().getApplicationOfRatings() != null){
					if (agmt.getCounterpartyFixedTrigger().getRatingContingentValues().getApplicationOfRatings().equals(ApplicationOfRatingsType.HIGHER)){
						element("ap.cptyRatingContingentValue.useHigherRating").click();
					}
					if (agmt.getCounterpartyFixedTrigger().getRatingContingentValues().getApplicationOfRatings().equals(ApplicationOfRatingsType.LOWER)){
						element("ap.cptyRatingContingentValue.useLowerRating").click();
					}
				}
				if (agmt.getCounterpartyFixedTrigger().getRatingContingentValues().getIfNotRatedByAnyAgency() != null){
					element("ap.cptyRatingContingentValue.ifNotRated").click();
				}
			}
		}
    }

    public  void setupImCounterpartyFixedTrigger(Agreement agmt) throws Exception{
    	//set Im counterparty fixtrigger
		if (agmt.getImCounterpartyFixedTrigger() != null){
			if (agmt.getImCounterpartyFixedTrigger().getFixedValues() != null){
				//click fixed values
				element("ap.cptyFixedValues").click();
				//setup set Im counterparty fixed values threshold parameters
				if (agmt.getImCounterpartyFixedTrigger().getFixedValues().getThreshold() != null){
					if (agmt.getImCounterpartyFixedTrigger().getFixedValues().getThreshold().getInfinity() != null){
						element("ap.cptyFixedValues.threshold.infinity").click();
					}
					if (agmt.getImCounterpartyFixedTrigger().getFixedValues().getThreshold().getFixedAmount() != null){
						element("ap.cptyFixedValues.threshold.fixedAmount").click();
						if (agmt.getImCounterpartyFixedTrigger().getFixedValues().getThreshold().getFixedAmount().getAmount() != null){
							element("ap.cptyFixedValues.threshold.fixedAmountValue").input(agmt.getImCounterpartyFixedTrigger().getFixedValues().getThreshold().getFixedAmount().getAmount().getRealValue());
						}
						if (agmt.getImCounterpartyFixedTrigger().getFixedValues().getThreshold().getFixedAmount().getCurrency() != null){
							element("ap.cptyFixedValues.threshold.fixedAmountCurrency").selectByVisibleText(agmt.getImCounterpartyFixedTrigger().getFixedValues().getThreshold().getFixedAmount().getCurrency().getRealValue());
						}
					}
					if (agmt.getImCounterpartyFixedTrigger().getFixedValues().getThreshold().getPercentageOfNAV() != null){
						//click % of NAV
						element("ap.cptyFixedValues.threshold.percentageOfNav").click();
						element("ap.cptyFixedValues.threshold.percentageOfNavValue").input(agmt.getImCounterpartyFixedTrigger().getFixedValues().getThreshold().getPercentageOfNAV().getRealValue());
					}
					if (agmt.getImCounterpartyFixedTrigger().getFixedValues().getThreshold().getPercentageOfNAVAddIA() != null){
						//click % of NAM+IA
						element("ap.cptyFixedValues.threshold.percentageOfNavAddIa").click();
						element("ap.cptyFixedValues.threshold.percentageOfNavAddIaValue").input(agmt.getImCounterpartyFixedTrigger().getFixedValues().getThreshold().getPercentageOfNAVAddIA().getRealValue());
					}
					if (agmt.getImCounterpartyFixedTrigger().getFixedValues().getThreshold().getPercentageOfIAAddMTM() != null){
						//click % of IA+MTM
						element("ap.cptyFixedValues.threshold.percentageOfIaAddMtm").click();
						element("ap.cptyFixedValues.threshold.percentageOfIaAddMtmValue").input(agmt.getImCounterpartyFixedTrigger().getFixedValues().getThreshold().getPercentageOfIAAddMTM().getRealValue());
					}
					if (agmt.getImCounterpartyFixedTrigger().getFixedValues().getThreshold().getPercentageOfEXPSubstractCOL() != null){
						//click % of EXP-COL
						element("ap.cptyFixedValues.threshold.percentageOfExpSubstractCol").click();
						element("ap.cptyFixedValues.threshold.percentageOfExpSubstractColValue").input(agmt.getImCounterpartyFixedTrigger().getFixedValues().getThreshold().getPercentageOfEXPSubstractCOL().getRealValue());
					}
					if (agmt.getImCounterpartyFixedTrigger().getFixedValues().getThreshold().getPercentageOfNotional() != null){
						//click % of notional
						element("ap.cptyFixedValues.threshold.percentageOfNotional").click();
						element("ap.cptyFixedValues.threshold.percentageOfNotionalValue").input(agmt.getImCounterpartyFixedTrigger().getFixedValues().getThreshold().getPercentageOfNotional().getRealValue());
					}
				}

				//set Im counterparty fixed values minimum trasfer
				if (agmt.getImCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer() != null){
					if (agmt.getImCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getFixedAmount() != null){
						//click fixed amount
						element("ap.cptyFixedValues.minimumTransfer.fixedAmount").click();
						if (agmt.getImCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getFixedAmount().getAmount() != null){
							element("ap.cptyFixedValues.minimumTransfer.fixedAmountValue").input(agmt.getImCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getFixedAmount().getAmount().getRealValue());
						}
						if (agmt.getImCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getFixedAmount().getCurrency() != null){
							element("ap.cptyFixedValues.minimumTransfer.fixedAmountCurrency").selectByVisibleText(agmt.getImCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getFixedAmount().getCurrency().getRealValue());
						}

						if (agmt.getImCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfNAV() != null){
							//click % of NAV
							element("ap.cptyFixedValues.minimumTransfer.percentageOfNav").click();
							element("ap.cptyFixedValues.minimumTransfer.percentageOfNavValue").input(agmt.getImCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfNAV().getRealValue());
						}
						if (agmt.getImCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfNAVAddIA() != null){
							//click % of NAM+IA
							element("ap.cptyFixedValues.minimumTransfer.percentageOfNavAddIa").click();
							element("ap.cptyFixedValues.minimumTransfer.percentageOfNavAddIaValue").input(agmt.getImCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfNAVAddIA().getRealValue());
						}
						if (agmt.getImCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfNAVAddThreshold() != null){
							//click % of IA+Threshold
							element("ap.cptyFixedValues.minimumTransfer.percentageOfIaAddThreshold").click();
							element("ap.cptyFixedValues.minimumTransfer.percentageOfIaAddThresholdValue").input(agmt.getImCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfNAVAddThreshold().getRealValue());
						}

						if (agmt.getImCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfIAAddMTM() != null){
							//click % of IA+MTM
							element("ap.cptyFixedValues.minimumTransfer.percentageOfIaAddMtm").click();
							element("ap.cptyFixedValues.minimumTransfer.percentageOfIaAddMtmValue").input(agmt.getImCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfIAAddMTM().getRealValue());
						}
						if (agmt.getImCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfEXPSubstractCOL() != null){
							//click % of EXP-COL
							element("ap.cptyFixedValues.minimumTransfer.percentageOfExpSubstractCol").click();
							element("ap.cptyFixedValues.minimumTransfer.percentageOfExpSubstractColValue").input(agmt.getImCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfEXPSubstractCOL().getRealValue());
						}
						if (agmt.getImCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfNotional() != null){
							//click % of notional
							element("ap.cptyFixedValues.minimumTransfer.percentageOfNotional").click();
							element("ap.cptyFixedValues.minimumTransfer.percentageOfNotionalValue").input(agmt.getImCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfNotional().getRealValue());
						}
						if (agmt.getImCounterpartyFixedTrigger().getFixedValues().getRoundingAmount() != null){
							//set rounding amount
							if (agmt.getImCounterpartyFixedTrigger().getFixedValues().getRoundingAmount().getAmount() != null){
								element("ap.cptyFixedValues.roundingAmount").input(agmt.getImCounterpartyFixedTrigger().getFixedValues().getRoundingAmount().getAmount().getRealValue());
							}
							if (agmt.getImCounterpartyFixedTrigger().getFixedValues().getRoundingAmount().getCurrency() != null){
								element("ap.cptyFixedValues.roundingAmountCurrency").selectByVisibleText(agmt.getImCounterpartyFixedTrigger().getFixedValues().getRoundingAmount().getCurrency().getRealValue());
							}
						}
					}
				}
			}
				//set Im counterparty fixed trigger time contingent values
			if (agmt.getImCounterpartyFixedTrigger().getTimeContingentValues() != null){
				//click time contingent
				element("ap.cptyTimeContingentValue").click();
			}
			//set Im counterparty fixed trigger NAV Contingent values
			if (agmt.getImCounterpartyFixedTrigger().getNavContingentValues() != null){
				//click NAV contingent values
				element("ap.cptyNavContingentValue").click();
			}
			//set Im counterparty fixed trugger rating contigent value
			if (agmt.getImCounterpartyFixedTrigger().getRatingContingentValues() != null){
				//click rating contingent values
				element("ap.cptyRatingContingentValue").click();
				if (agmt.getImCounterpartyFixedTrigger().getRatingContingentValues().isUsePrincipalRating() != null){
					element("ap.cptyRatingContingentValue.usecptyRating").click();
				}
				if (agmt.getImCounterpartyFixedTrigger().getRatingContingentValues().getUseCounterpartyRating() != null){
					element("ap.cptyRatingContingentValue.useProviderRating").click();
					element("ap.cptyRatingContingentValue.searchOrg").click();
		            waitThat("ap.frame.cptyRatingContingentValue").frameToBeAvailableAndSwitchToIt();
		            searchOrganisation(agmt.getImCounterpartyFixedTrigger().getRatingContingentValues().getUseCounterpartyRating());
		            switchTo().defaultContent();
				}
				if (agmt.getImCounterpartyFixedTrigger().getRatingContingentValues().getReferenceRatingAgencies() != null && agmt.getImCounterpartyFixedTrigger().getRatingContingentValues().getReferenceRatingAgencies().size()>0){
					for (ReferenceRatingAgenciesType referenceRatingAgency : agmt.getImCounterpartyFixedTrigger().getRatingContingentValues().getReferenceRatingAgencies()) {
						if (referenceRatingAgency.value().equals(ReferenceRatingAgenciesType.FITCH.value())) {
							element("ap.cptyRatingContingentValue.fitch").check(true);
						}
						if (referenceRatingAgency.value().equals(ReferenceRatingAgenciesType.MOODYS.value())) {
							element("ap.cptyRatingContingentValue.moodys").check(true);
						}
						if (referenceRatingAgency.value().equals(ReferenceRatingAgenciesType.SAND_P.value())) {
							element("ap.cptyRatingContingentValue.sandp").check(true);
						}
					}

				}

				//choose debt classifications
				if (agmt.getImCounterpartyFixedTrigger().getRatingContingentValues().getDebtClassification() != null
						&& !agmt.getImCounterpartyFixedTrigger().getRatingContingentValues().getDebtClassification().isEmpty()){
					for (DebtClassificationType debtClassificationType : agmt.getImCounterpartyFixedTrigger().getRatingContingentValues().getDebtClassification()) {
						switch (debtClassificationType){
							case SEC:
								element("ap.cptyRatingContingentValue.sec").click();
								break;
							case SEN:
								element("ap.cptyRatingContingentValue.sen").click();
								break;
							case SRSUB:
								element("ap.cptyRatingContingentValue.srub").click();
								break;
							case SUB:
								element("ap.cptyRatingContingentValue.sub").click();
								break;
						}
					}
				}
				if (agmt.getImCounterpartyFixedTrigger().getRatingContingentValues().getApplicationOfRatings() != null){
					if (agmt.getImCounterpartyFixedTrigger().getRatingContingentValues().getApplicationOfRatings().equals(ApplicationOfRatingsType.HIGHER)){
						element("ap.cptyRatingContingentValue.useHigherRating").click();
					}
					if (agmt.getImCounterpartyFixedTrigger().getRatingContingentValues().getApplicationOfRatings().equals(ApplicationOfRatingsType.LOWER)){
						element("ap.cptyRatingContingentValue.useLowerRating").click();
					}
				}
				if (agmt.getImCounterpartyFixedTrigger().getRatingContingentValues().getIfNotRatedByAnyAgency() != null){
					element("ap.cptyRatingContingentValue.ifNotRated").click();
				}
			}
		}

}


	public void setupImPrincipalFixedTrigger(Agreement agmt) throws Exception {
	//set  Im principal fixtrigger
	if (agmt.getImPrincipalFixedTrigger() != null){
		if (agmt.getImPrincipalFixedTrigger().getFixedValues() != null){
			//click fixed values
			element("ap.principalFixedValues").click();
			//setup set principal fixed values threshold parameters
			if (agmt.getImPrincipalFixedTrigger().getFixedValues().getThreshold() != null){
				if (agmt.getImPrincipalFixedTrigger().getFixedValues().getThreshold().getInfinity() != null){
					element("ap.principalFixedValues.threshold.infinity").click();
				}
				if (agmt.getImPrincipalFixedTrigger().getFixedValues().getThreshold().getFixedAmount() != null){
					//click fixed amount
					element("ap.principalFixedValues.threshold.fixedAmount").click();
					if (agmt.getImPrincipalFixedTrigger().getFixedValues().getThreshold().getFixedAmount().getAmount() != null){
						element("ap.principalFixedValues.threshold.fixedAmountValue").input(agmt.getImPrincipalFixedTrigger().getFixedValues().getThreshold().getFixedAmount().getAmount().getRealValue());
					}
					if (agmt.getImPrincipalFixedTrigger().getFixedValues().getThreshold().getFixedAmount().getCurrency() != null){
						element("ap.principalFixedValues.threshold.fixedAmountCurrency").selectByVisibleText(agmt.getImPrincipalFixedTrigger().getFixedValues().getThreshold().getFixedAmount().getCurrency().getRealValue());
					}
				}
				if (agmt.getImPrincipalFixedTrigger().getFixedValues().getThreshold().getPercentageOfNAV() != null){
					//click % of NAV
					element("ap.principalFixedValues.threshold.percentageOfNav").click();
					element("ap.principalFixedValues.threshold.percentageOfNavValue").input(agmt.getImPrincipalFixedTrigger().getFixedValues().getThreshold().getPercentageOfNAV().getRealValue());
				}
				if (agmt.getImPrincipalFixedTrigger().getFixedValues().getThreshold().getPercentageOfNAVAddIA() != null){
					//click % of NAM+IA
					element("ap.principalFixedValues.threshold.percentageOfNavAddIa").click();
					element("ap.principalFixedValues.threshold.percentageOfNavAddIaValue").input(agmt.getImPrincipalFixedTrigger().getFixedValues().getThreshold().getPercentageOfNAVAddIA().getRealValue());
				}
				if (agmt.getImPrincipalFixedTrigger().getFixedValues().getThreshold().getPercentageOfIAAddMTM() != null){
					//click % of IA+MTM
					element("ap.principalFixedValues.threshold.percentageOfIaAddMtm").click();
					element("ap.principalFixedValues.threshold.percentageOfIaAddMtmValue").input(agmt.getImPrincipalFixedTrigger().getFixedValues().getThreshold().getPercentageOfIAAddMTM().getRealValue());
				}
				if (agmt.getImPrincipalFixedTrigger().getFixedValues().getThreshold().getPercentageOfEXPSubstractCOL() != null){
					//click % of EXP-COL
					element("ap.principalFixedValues.threshold.percentageOfExpSubstractCol").click();
					element("ap.principalFixedValues.threshold.percentageOfExpSubstractColValue").input(agmt.getImPrincipalFixedTrigger().getFixedValues().getThreshold().getPercentageOfEXPSubstractCOL().getRealValue());
				}
				if (agmt.getImPrincipalFixedTrigger().getFixedValues().getThreshold().getPercentageOfNotional() != null){
					//click % of notional
					element("ap.principalFixedValues.threshold.percentageOfNotional").click();
					element("ap.principalFixedValues.threshold.percentageOfNotionalValue").input(agmt.getImPrincipalFixedTrigger().getFixedValues().getThreshold().getPercentageOfNotional().getRealValue());
				}
			}

			//set principal fixed values minimum trasfer
			if (agmt.getImPrincipalFixedTrigger().getFixedValues().getMinimumTransfer() != null){
				if (agmt.getImPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getFixedAmount() != null){
					//click fixed amount
					element("ap.principalFixedValues.minimumTransfer.fixedAmount").click();
					if (agmt.getImPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getFixedAmount().getAmount() != null){
						element("ap.principalFixedValues.minimumTransfer.fixedAmountValue").input(agmt.getImPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getFixedAmount().getAmount().getRealValue());
					}
					if (agmt.getImPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getFixedAmount().getCurrency() != null){
						element("ap.principalFixedValues.minimumTransfer.fixedAmountCurrency").selectByVisibleText(agmt.getImPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getFixedAmount().getCurrency().getRealValue());
					}

					if (agmt.getImPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfNAV() != null){
						//click % of NAV
						element("ap.principalFixedValues.minimumTransfer.percentageOfNav").click();
						element("ap.principalFixedValues.minimumTransfer.percentageOfNavValue").input(agmt.getImPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfNAV().getRealValue());
					}
					if (agmt.getImPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfNAVAddIA() != null){
						//click % of NAM+IA
						element("ap.principalFixedValues.minimumTransfer.percentageOfNavAddIa").click();
						element("ap.principalFixedValues.minimumTransfer.percentageOfNavAddIaValue").input(agmt.getImPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfNAVAddIA().getRealValue());
					}
					if (agmt.getImPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfNAVAddThreshold() != null){
						//click % of IA+Threshold
						element("ap.principalFixedValues.minimumTransfer.percentageOfIaAddThreshold").click();
						element("ap.principalFixedValues.minimumTransfer.percentageOfIaAddThresholdValue").input(agmt.getImPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfNAVAddThreshold().getRealValue());
					}

					if (agmt.getImPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfIAAddMTM() != null){
						//click % of IA+MTM
						element("ap.principalFixedValues.minimumTransfer.percentageOfIaAddMtm").click();
						element("ap.principalFixedValues.minimumTransfer.percentageOfIaAddMtmValue").input(agmt.getImPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfIAAddMTM().getRealValue());
					}
					if (agmt.getImPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfEXPSubstractCOL() != null){
						//click % of EXP-COL
						element("ap.principalFixedValues.minimumTransfer.percentageOfExpSubstractCol").click();
						element("ap.principalFixedValues.minimumTransfer.percentageOfExpSubstractColValue").input(agmt.getImPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfEXPSubstractCOL().getRealValue());
					}
					if (agmt.getImPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfNotional() != null){
						//click % of notional
						element("ap.principalFixedValues.minimumTransfer.percentageOfNotional").click();
						element("ap.principalFixedValues.minimumTransfer.percentageOfNotionalValue").input(agmt.getImPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfNotional().getRealValue());
					}
					if (agmt.getImPrincipalFixedTrigger().getFixedValues().getRoundingAmount() != null){
						//set rounding amount
						if (agmt.getImPrincipalFixedTrigger().getFixedValues().getRoundingAmount().getAmount() != null){
							element("ap.principalFixedValues.roundingAmount").input(agmt.getImPrincipalFixedTrigger().getFixedValues().getRoundingAmount().getAmount().getRealValue());
						}
						if (agmt.getImPrincipalFixedTrigger().getFixedValues().getRoundingAmount().getCurrency() != null){
							element("ap.principalFixedValues.roundingAmountCurrency").selectByVisibleText(agmt.getImPrincipalFixedTrigger().getFixedValues().getRoundingAmount().getCurrency().getRealValue());
						}
					}
				}
			}
		}
			//set principal fixed trigger time contingent values
		if (agmt.getImPrincipalFixedTrigger().getTimeContingentValues() != null){
			//click time contingent
			element("ap.principalTimeContingentValue").click();
		}
		//set principal fixed trigger NAV Contingent values
		if (agmt.getImPrincipalFixedTrigger().getNavContingentValues() != null){
			//click NAV contingent values
			element("ap.principalNavContingentValue").click();
		}
		//set principal fixed trugger rating contigent value
		if (agmt.getImPrincipalFixedTrigger().getRatingContingentValues() != null){
			//click rating contingent values
			element("ap.principalRatingContingentValue").click();
			if (agmt.getImPrincipalFixedTrigger().getRatingContingentValues().isUsePrincipalRating() != null){
				element("ap.principalRatingContingentValue.usePrincipalRating").click();
			}
			if (agmt.getImPrincipalFixedTrigger().getRatingContingentValues().getUseCounterpartyRating() != null){
				//click Use Credit Support Provider Rating
				element("ap.principalRatingContingentValue.useProviderRating").click();
				element("ap.principalRatingContingentValue.searchOrg").click();
	            waitThat("ap.principalRatingContingentValue.searchOrg").frameToBeAvailableAndSwitchToIt();
	            searchOrganisation(agmt.getImPrincipalFixedTrigger().getRatingContingentValues().getUseCounterpartyRating());
	            switchTo().defaultContent();
			}
			if (agmt.getImPrincipalFixedTrigger().getRatingContingentValues().getReferenceRatingAgencies() != null && agmt.getImPrincipalFixedTrigger().getRatingContingentValues().getReferenceRatingAgencies().size()>0){
				for (ReferenceRatingAgenciesType referenceRatingAgency : agmt.getImPrincipalFixedTrigger().getRatingContingentValues().getReferenceRatingAgencies()) {
					if (referenceRatingAgency.value().equals(ReferenceRatingAgenciesType.FITCH.value())) {
						element("ap.principalRatingContingentValue.fitch").check(true);
					}
					if (referenceRatingAgency.value().equals(ReferenceRatingAgenciesType.MOODYS.value())) {
						element("ap.principalRatingContingentValue.moodys").check(true);
					}
					if (referenceRatingAgency.value().equals(ReferenceRatingAgenciesType.SAND_P.value())) {
						element("ap.principalRatingContingentValue.sandp").check(true);
					}
				}
			}
			//choose debt classifications
			if (agmt.getImPrincipalFixedTrigger().getRatingContingentValues().getDebtClassification() != null
					&& !agmt.getImPrincipalFixedTrigger().getRatingContingentValues().getDebtClassification().isEmpty()){
				for (DebtClassificationType debtClassificationType : agmt.getImPrincipalFixedTrigger().getRatingContingentValues().getDebtClassification()) {
					switch (debtClassificationType) {
						case SEC:
							element("ap.principalRatingContingentValue.sec").click();
							break;
						case SEN:
							element("ap.principalRatingContingentValue.sen").click();
							break;
						case SRSUB:
							element("ap.principalRatingContingentValue.srub").click();
							break;
						case SUB:
							element("ap.principalRatingContingentValue.sub").click();
							break;
					}
				}
			}
			if (agmt.getImPrincipalFixedTrigger().getRatingContingentValues().getApplicationOfRatings() != null){
				if (agmt.getImPrincipalFixedTrigger().getRatingContingentValues().getApplicationOfRatings().equals(ApplicationOfRatingsType.HIGHER)){
					element("ap.principalRatingContingentValue.useHigherRating").click();
				}
				if (agmt.getImPrincipalFixedTrigger().getRatingContingentValues().getApplicationOfRatings().equals(ApplicationOfRatingsType.LOWER)){
					element("ap.principalRatingContingentValue.useLowerRating").click();
				}
			}
			if (agmt.getImPrincipalFixedTrigger().getRatingContingentValues().getIfNotRatedByAnyAgency() != null){
				element("ap.principalRatingContingentValue.ifNotRated").click();
			}
		}
	}

}

	public void setupVmCounterpartyFixedTrigger(Agreement agmt) throws Exception {
    	//set vm counterparty fixtrigger
    			if (agmt.getVmCounterpartyFixedTrigger() != null){
    				if (agmt.getVmCounterpartyFixedTrigger().getFixedValues() != null){
    					//click fixed values
    					element("ap.cptyFixedValues").click();
    					//setup set vm counterparty fixed values threshold parameters
    					if (agmt.getVmCounterpartyFixedTrigger().getFixedValues().getThreshold() != null){
    						if (agmt.getVmCounterpartyFixedTrigger().getFixedValues().getThreshold().getInfinity() != null){
    							element("ap.cptyFixedValues.threshold.infinity").click();
    						}
    						if (agmt.getVmCounterpartyFixedTrigger().getFixedValues().getThreshold().getFixedAmount() != null){
    							element("ap.cptyFixedValues.threshold.fixedAmount").click();
    							if (agmt.getVmCounterpartyFixedTrigger().getFixedValues().getThreshold().getFixedAmount().getAmount() != null){
    								element("ap.cptyFixedValues.threshold.fixedAmountValue").input(agmt.getVmCounterpartyFixedTrigger().getFixedValues().getThreshold().getFixedAmount().getAmount().getRealValue());
    							}
    							if (agmt.getVmCounterpartyFixedTrigger().getFixedValues().getThreshold().getFixedAmount().getCurrency() != null){
    								element("ap.cptyFixedValues.threshold.fixedAmountCurrency").selectByVisibleText(agmt.getVmCounterpartyFixedTrigger().getFixedValues().getThreshold().getFixedAmount().getCurrency().getRealValue());
    							}
    						}
    						if (agmt.getVmCounterpartyFixedTrigger().getFixedValues().getThreshold().getPercentageOfNAV() != null){
    							//click % of NAV
    							element("ap.cptyFixedValues.threshold.percentageOfNav").click();
    							element("ap.cptyFixedValues.threshold.percentageOfNavValue").input(agmt.getVmCounterpartyFixedTrigger().getFixedValues().getThreshold().getPercentageOfNAV().getRealValue());
    						}
    						if (agmt.getVmCounterpartyFixedTrigger().getFixedValues().getThreshold().getPercentageOfNAVAddIA() != null){
    							//click % of NAM+IA
    							element("ap.cptyFixedValues.threshold.percentageOfNavAddIa").click();
    							element("ap.cptyFixedValues.threshold.percentageOfNavAddIaValue").input(agmt.getVmCounterpartyFixedTrigger().getFixedValues().getThreshold().getPercentageOfNAVAddIA().getRealValue());
    						}
    						if (agmt.getVmCounterpartyFixedTrigger().getFixedValues().getThreshold().getPercentageOfIAAddMTM() != null){
    							//click % of IA+MTM
    							element("ap.cptyFixedValues.threshold.percentageOfIaAddMtm").click();
    							element("ap.cptyFixedValues.threshold.percentageOfIaAddMtmValue").input(agmt.getVmCounterpartyFixedTrigger().getFixedValues().getThreshold().getPercentageOfIAAddMTM().getRealValue());
    						}
    						if (agmt.getVmCounterpartyFixedTrigger().getFixedValues().getThreshold().getPercentageOfEXPSubstractCOL() != null){
    							//click % of EXP-COL
    							element("ap.cptyFixedValues.threshold.percentageOfExpSubstractCol").click();
    							element("ap.cptyFixedValues.threshold.percentageOfExpSubstractColValue").input(agmt.getVmCounterpartyFixedTrigger().getFixedValues().getThreshold().getPercentageOfEXPSubstractCOL().getRealValue());
    						}
    						if (agmt.getVmCounterpartyFixedTrigger().getFixedValues().getThreshold().getPercentageOfNotional() != null){
    							//click % of notional
    							element("ap.cptyFixedValues.threshold.percentageOfNotional").click();
    							element("ap.cptyFixedValues.threshold.percentageOfNotionalValue").input(agmt.getVmCounterpartyFixedTrigger().getFixedValues().getThreshold().getPercentageOfNotional().getRealValue());
    						}
    					}

    					//set vm counterparty fixed values minimum trasfer
    					if (agmt.getVmCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer() != null){
    						if (agmt.getVmCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getFixedAmount() != null){
    							//click fixed amount
    							element("ap.cptyFixedValues.minimumTransfer.fixedAmount").click();
    							if (agmt.getVmCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getFixedAmount().getAmount() != null){
    								element("ap.cptyFixedValues.minimumTransfer.fixedAmountValue").input(agmt.getVmCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getFixedAmount().getAmount().getRealValue());
    							}
    							if (agmt.getVmCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getFixedAmount().getCurrency() != null){
    								element("ap.cptyFixedValues.minimumTransfer.fixedAmountCurrency").selectByVisibleText(agmt.getVmCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getFixedAmount().getCurrency().getRealValue());
    							}

    							if (agmt.getVmCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfNAV() != null){
    								//click % of NAV
    								element("ap.cptyFixedValues.minimumTransfer.percentageOfNav").click();
    								element("ap.cptyFixedValues.minimumTransfer.percentageOfNavValue").input(agmt.getVmCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfNAV().getRealValue());
    							}
    							if (agmt.getVmCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfNAVAddIA() != null){
    								//click % of NAM+IA
    								element("ap.cptyFixedValues.minimumTransfer.percentageOfNavAddIa").click();
    								element("ap.cptyFixedValues.minimumTransfer.percentageOfNavAddIaValue").input(agmt.getVmCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfNAVAddIA().getRealValue());
    							}
    							if (agmt.getVmCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfNAVAddThreshold() != null){
    								//click % of IA+Threshold
    								element("ap.cptyFixedValues.minimumTransfer.percentageOfIaAddThreshold").click();
    								element("ap.cptyFixedValues.minimumTransfer.percentageOfIaAddThresholdValue").input(agmt.getVmCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfNAVAddThreshold().getRealValue());
    							}

    							if (agmt.getVmCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfIAAddMTM() != null){
    								//click % of IA+MTM
    								element("ap.cptyFixedValues.minimumTransfer.percentageOfIaAddMtm").click();
    								element("ap.cptyFixedValues.minimumTransfer.percentageOfIaAddMtmValue").input(agmt.getVmCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfIAAddMTM().getRealValue());
    							}
    							if (agmt.getVmCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfEXPSubstractCOL() != null){
    								//click % of EXP-COL
    								element("ap.cptyFixedValues.minimumTransfer.percentageOfExpSubstractCol").click();
    								element("ap.cptyFixedValues.minimumTransfer.percentageOfExpSubstractColValue").input(agmt.getVmCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfEXPSubstractCOL().getRealValue());
    							}
    							if (agmt.getVmCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfNotional() != null){
    								//click % of notional
    								element("ap.cptyFixedValues.minimumTransfer.percentageOfNotional").click();
    								element("ap.cptyFixedValues.minimumTransfer.percentageOfNotionalValue").input(agmt.getVmCounterpartyFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfNotional().getRealValue());
    							}
    							if (agmt.getVmCounterpartyFixedTrigger().getFixedValues().getRoundingAmount() != null){
    								//set rounding amount
    								if (agmt.getVmCounterpartyFixedTrigger().getFixedValues().getRoundingAmount().getAmount() != null){
    									element("ap.cptyFixedValues.roundingAmount").input(agmt.getVmCounterpartyFixedTrigger().getFixedValues().getRoundingAmount().getAmount().getRealValue());
    								}
    								if (agmt.getVmCounterpartyFixedTrigger().getFixedValues().getRoundingAmount().getCurrency() != null){
    									element("ap.cptyFixedValues.roundingAmountCurrency").selectByVisibleText(agmt.getVmCounterpartyFixedTrigger().getFixedValues().getRoundingAmount().getCurrency().getRealValue());
    								}
    							}
    						}
    					}
    				}
    					//set vm counterparty fixed trigger time contingent values
    				if (agmt.getVmCounterpartyFixedTrigger().getTimeContingentValues() != null){
    					//click time contingent
    					element("ap.cptyTimeContingentValue").click();
    				}
    				//set vm counterparty fixed trigger NAV Contingent values
    				if (agmt.getVmCounterpartyFixedTrigger().getNavContingentValues() != null){
    					//click NAV contingent values
    					element("ap.cptyNavContingentValue").click();
    				}
    				//set vm counterparty fixed trugger rating contigent value
    				if (agmt.getVmCounterpartyFixedTrigger().getRatingContingentValues() != null){
    					//click rating contingent values
    					element("ap.cptyRatingContingentValue").click();
    					if (agmt.getVmCounterpartyFixedTrigger().getRatingContingentValues().isUsePrincipalRating() != null){
    						element("ap.cptyRatingContingentValue.usecptyRating").click();
    					}
    					if (agmt.getVmCounterpartyFixedTrigger().getRatingContingentValues().getUseCounterpartyRating() != null){
    						element("ap.cptyRatingContingentValue.useProviderRating").click();
    						element("ap.cptyRatingContingentValue.searchOrg").click();
    			            waitThat("ap.frame.cptyRatingContingentValue").frameToBeAvailableAndSwitchToIt();
    			            searchOrganisation(agmt.getVmCounterpartyFixedTrigger().getRatingContingentValues().getUseCounterpartyRating());
    			            switchTo().defaultContent();
    					}
    					if (agmt.getVmCounterpartyFixedTrigger().getRatingContingentValues().getReferenceRatingAgencies() != null && agmt.getVmCounterpartyFixedTrigger().getRatingContingentValues().getReferenceRatingAgencies().size()>0){
    						for (ReferenceRatingAgenciesType referenceRatingAgency : agmt.getVmCounterpartyFixedTrigger().getRatingContingentValues().getReferenceRatingAgencies()) {
								if (referenceRatingAgency.value().equals(ReferenceRatingAgenciesType.FITCH.value())) {
									element("ap.cptyRatingContingentValue.fitch").check(true);
								}
								if (referenceRatingAgency.value().equals(ReferenceRatingAgenciesType.MOODYS.value())) {
									element("ap.cptyRatingContingentValue.moodys").check(true);
								}
								if (referenceRatingAgency.value().equals(ReferenceRatingAgenciesType.SAND_P.value())) {
									element("ap.cptyRatingContingentValue.sandp").check(true);
								}
							}
    					}

    					//choose debt classifications
    					if (agmt.getVmCounterpartyFixedTrigger().getRatingContingentValues().getDebtClassification() != null
								&& !agmt.getVmCounterpartyFixedTrigger().getRatingContingentValues().getDebtClassification().isEmpty()){
    						for (DebtClassificationType debtClassificationType : agmt.getVmCounterpartyFixedTrigger().getRatingContingentValues().getDebtClassification()) {
    							switch (debtClassificationType) {
									case SEC:
										element("ap.cptyRatingContingentValue.sec").click();
										break;
									case SEN:
										element("ap.cptyRatingContingentValue.sen").click();
										break;
									case SRSUB:
										element("ap.cptyRatingContingentValue.srub").click();
										break;
									case SUB:
										element("ap.cptyRatingContingentValue.sub").click();
										break;
								}
							}
    					}
    					if (agmt.getVmCounterpartyFixedTrigger().getRatingContingentValues().getApplicationOfRatings() != null){
    						if (agmt.getVmCounterpartyFixedTrigger().getRatingContingentValues().getApplicationOfRatings().equals(ApplicationOfRatingsType.HIGHER)){
    							element("ap.cptyRatingContingentValue.useHigherRating").click();
    						}
    						if (agmt.getVmCounterpartyFixedTrigger().getRatingContingentValues().getApplicationOfRatings().equals(ApplicationOfRatingsType.LOWER)){
    							element("ap.cptyRatingContingentValue.useLowerRating").click();
    						}
    					}
    					if (agmt.getVmCounterpartyFixedTrigger().getRatingContingentValues().getIfNotRatedByAnyAgency() != null){
    						element("ap.cptyRatingContingentValue.ifNotRated").click();
    					}
    				}
    			}

	}

	public void setupVmPrincipalFixedTrigger(Agreement agmt) throws Exception {
        	//set  vm principal fixtrigger
    		if (agmt.getVmPrincipalFixedTrigger() != null){
    			if (agmt.getVmPrincipalFixedTrigger().getFixedValues() != null){
    				//click fixed values
    				element("ap.principalFixedValues").click();
    				//setup set principal fixed values threshold parameters
    				if (agmt.getVmPrincipalFixedTrigger().getFixedValues().getThreshold() != null){
    					if (agmt.getVmPrincipalFixedTrigger().getFixedValues().getThreshold().getInfinity() != null){
    						element("ap.principalFixedValues.threshold.infinity").click();
    					}
    					if (agmt.getVmPrincipalFixedTrigger().getFixedValues().getThreshold().getFixedAmount() != null){
    						//click fixed amount
    						element("ap.principalFixedValues.threshold.fixedAmount").click();
    						if (agmt.getVmPrincipalFixedTrigger().getFixedValues().getThreshold().getFixedAmount().getAmount() != null){
    							element("ap.principalFixedValues.threshold.fixedAmountValue").input(agmt.getVmPrincipalFixedTrigger().getFixedValues().getThreshold().getFixedAmount().getAmount().getRealValue());
    						}
    						if (agmt.getVmPrincipalFixedTrigger().getFixedValues().getThreshold().getFixedAmount().getCurrency() != null){
    							element("ap.principalFixedValues.threshold.fixedAmountCurrency").selectByVisibleText(agmt.getVmPrincipalFixedTrigger().getFixedValues().getThreshold().getFixedAmount().getCurrency().getRealValue());
    						}
    					}
    					if (agmt.getVmPrincipalFixedTrigger().getFixedValues().getThreshold().getPercentageOfNAV() != null){
    						//click % of NAV
    						element("ap.principalFixedValues.threshold.percentageOfNav").click();
    						element("ap.principalFixedValues.threshold.percentageOfNavValue").input(agmt.getVmPrincipalFixedTrigger().getFixedValues().getThreshold().getPercentageOfNAV().getRealValue());
    					}
    					if (agmt.getVmPrincipalFixedTrigger().getFixedValues().getThreshold().getPercentageOfNAVAddIA() != null){
    						//click % of NAM+IA
    						element("ap.principalFixedValues.threshold.percentageOfNavAddIa").click();
    						element("ap.principalFixedValues.threshold.percentageOfNavAddIaValue").input(agmt.getVmPrincipalFixedTrigger().getFixedValues().getThreshold().getPercentageOfNAVAddIA().getRealValue());
    					}
    					if (agmt.getVmPrincipalFixedTrigger().getFixedValues().getThreshold().getPercentageOfIAAddMTM() != null){
    						//click % of IA+MTM
    						element("ap.principalFixedValues.threshold.percentageOfIaAddMtm").click();
    						element("ap.principalFixedValues.threshold.percentageOfIaAddMtmValue").input(agmt.getVmPrincipalFixedTrigger().getFixedValues().getThreshold().getPercentageOfIAAddMTM().getRealValue());
    					}
    					if (agmt.getVmPrincipalFixedTrigger().getFixedValues().getThreshold().getPercentageOfEXPSubstractCOL() != null){
    						//click % of EXP-COL
    						element("ap.principalFixedValues.threshold.percentageOfExpSubstractCol").click();
    						element("ap.principalFixedValues.threshold.percentageOfExpSubstractColValue").input(agmt.getVmPrincipalFixedTrigger().getFixedValues().getThreshold().getPercentageOfEXPSubstractCOL().getRealValue());
    					}
    					if (agmt.getVmPrincipalFixedTrigger().getFixedValues().getThreshold().getPercentageOfNotional() != null){
    						//click % of notional
    						element("ap.principalFixedValues.threshold.percentageOfNotional").click();
    						element("ap.principalFixedValues.threshold.percentageOfNotionalValue").input(agmt.getVmPrincipalFixedTrigger().getFixedValues().getThreshold().getPercentageOfNotional().getRealValue());
    					}
    				}

    				//set principal fixed values minimum trasfer
    				if (agmt.getVmPrincipalFixedTrigger().getFixedValues().getMinimumTransfer() != null){
    					if (agmt.getVmPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getFixedAmount() != null){
    						//click fixed amount
    						element("ap.principalFixedValues.minimumTransfer.fixedAmount").click();
    						if (agmt.getVmPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getFixedAmount().getAmount() != null){
    							element("ap.principalFixedValues.minimumTransfer.fixedAmountValue").input(agmt.getVmPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getFixedAmount().getAmount().getRealValue());
    						}
    						if (agmt.getVmPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getFixedAmount().getCurrency() != null){
    							element("ap.principalFixedValues.minimumTransfer.fixedAmountCurrency").selectByVisibleText(agmt.getVmPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getFixedAmount().getCurrency().getRealValue());
    						}

    						if (agmt.getVmPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfNAV() != null){
    							//click % of NAV
    							element("ap.principalFixedValues.minimumTransfer.percentageOfNav").click();
    							element("ap.principalFixedValues.minimumTransfer.percentageOfNavValue").input(agmt.getVmPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfNAV().getRealValue());
    						}
    						if (agmt.getVmPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfNAVAddIA() != null){
    							//click % of NAM+IA
    							element("ap.principalFixedValues.minimumTransfer.percentageOfNavAddIa").click();
    							element("ap.principalFixedValues.minimumTransfer.percentageOfNavAddIaValue").input(agmt.getVmPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfNAVAddIA().getRealValue());
    						}
    						if (agmt.getVmPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfNAVAddThreshold() != null){
    							//click % of IA+Threshold
    							element("ap.principalFixedValues.minimumTransfer.percentageOfIaAddThreshold").click();
    							element("ap.principalFixedValues.minimumTransfer.percentageOfIaAddThresholdValue").input(agmt.getVmPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfNAVAddThreshold().getRealValue());
    						}

    						if (agmt.getVmPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfIAAddMTM() != null){
    							//click % of IA+MTM
    							element("ap.principalFixedValues.minimumTransfer.percentageOfIaAddMtm").click();
    							element("ap.principalFixedValues.minimumTransfer.percentageOfIaAddMtmValue").input(agmt.getVmPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfIAAddMTM().getRealValue());
    						}
    						if (agmt.getVmPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfEXPSubstractCOL() != null){
    							//click % of EXP-COL
    							element("ap.principalFixedValues.minimumTransfer.percentageOfExpSubstractCol").click();
    							element("ap.principalFixedValues.minimumTransfer.percentageOfExpSubstractColValue").input(agmt.getVmPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfEXPSubstractCOL().getRealValue());
    						}
    						if (agmt.getVmPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfNotional() != null){
    							//click % of notional
    							element("ap.principalFixedValues.minimumTransfer.percentageOfNotional").click();
    							element("ap.principalFixedValues.minimumTransfer.percentageOfNotionalValue").input(agmt.getVmPrincipalFixedTrigger().getFixedValues().getMinimumTransfer().getPercentageOfNotional().getRealValue());
    						}
    						if (agmt.getVmPrincipalFixedTrigger().getFixedValues().getRoundingAmount() != null){
    							//set rounding amount
    							if (agmt.getVmPrincipalFixedTrigger().getFixedValues().getRoundingAmount().getAmount() != null){
    								element("ap.principalFixedValues.roundingAmount").input(agmt.getVmPrincipalFixedTrigger().getFixedValues().getRoundingAmount().getAmount().getRealValue());
    							}
    							if (agmt.getVmPrincipalFixedTrigger().getFixedValues().getRoundingAmount().getCurrency() != null){
    								element("ap.principalFixedValues.roundingAmountCurrency").selectByVisibleText(agmt.getVmPrincipalFixedTrigger().getFixedValues().getRoundingAmount().getCurrency().getRealValue());
    							}
    						}
    					}
    				}
    			}
    				//set principal fixed trigger time contingent values
    			if (agmt.getVmPrincipalFixedTrigger().getTimeContingentValues() != null){
    				//click time contingent
    				element("ap.principalTimeContingentValue").click();
    			}
    			//set principal fixed trigger NAV Contingent values
    			if (agmt.getVmPrincipalFixedTrigger().getNavContingentValues() != null){
    				//click NAV contingent values
    				element("ap.principalNavContingentValue").click();
    			}
    			//set principal fixed trugger rating contigent value
    			if (agmt.getVmPrincipalFixedTrigger().getRatingContingentValues() != null){
    				//click rating contingent values
    				element("ap.principalRatingContingentValue").click();
    				if (agmt.getVmPrincipalFixedTrigger().getRatingContingentValues().isUsePrincipalRating() != null){
    					element("ap.principalRatingContingentValue.usePrincipalRating").click();
    				}
    				if (agmt.getVmPrincipalFixedTrigger().getRatingContingentValues().getUseCounterpartyRating() != null){
    					//click Use Credit Support Provider Rating
    					element("ap.principalRatingContingentValue.useProviderRating").click();
    					element("ap.principalRatingContingentValue.searchOrg").click();
    		            waitThat("ap.frame.principalRatingContingentValue").frameToBeAvailableAndSwitchToIt();
    		            searchOrganisation(agmt.getVmPrincipalFixedTrigger().getRatingContingentValues().getUseCounterpartyRating());
    		            switchTo().defaultContent();
    				}
    				if (agmt.getVmPrincipalFixedTrigger().getRatingContingentValues().getReferenceRatingAgencies() != null && agmt.getVmPrincipalFixedTrigger().getRatingContingentValues().getReferenceRatingAgencies().size()>0){
						for (ReferenceRatingAgenciesType referenceRatingAgency : agmt.getVmPrincipalFixedTrigger().getRatingContingentValues().getReferenceRatingAgencies()) {
							if (referenceRatingAgency.value().equals(ReferenceRatingAgenciesType.FITCH.value())) {
								element("ap.cptyRatingContingentValue.fitch").check(true);
							}
							if (referenceRatingAgency.value().equals(ReferenceRatingAgenciesType.MOODYS.value())) {
								element("ap.principalRatingContingentValue.moodys").check(true);
							}
							if (referenceRatingAgency.value().equals(ReferenceRatingAgenciesType.SAND_P.value())) {
								element("ap.principalRatingContingentValue.sandp").check(true);
							}
						}

    				}
    				//choose debt classifications
    				if (agmt.getVmPrincipalFixedTrigger().getRatingContingentValues().getDebtClassification() != null
							&& !agmt.getVmPrincipalFixedTrigger().getRatingContingentValues().getDebtClassification().isEmpty()){
    					for (DebtClassificationType debtClassificationType : agmt.getVmPrincipalFixedTrigger().getRatingContingentValues().getDebtClassification()) {
    						switch (debtClassificationType) {
								case SEC:
									element("ap.principalRatingContingentValue.sec").click();
									break;
								case SEN:
									element("ap.principalRatingContingentValue.sen").click();
									break;
								case SRSUB:
									element("ap.principalRatingContingentValue.srub").click();
									break;
								case SUB:
									element("ap.principalRatingContingentValue.sub").click();
									break;
							}
						}
    				}
    				if (agmt.getVmPrincipalFixedTrigger().getRatingContingentValues().getApplicationOfRatings() != null){
    					if (agmt.getVmPrincipalFixedTrigger().getRatingContingentValues().getApplicationOfRatings().equals(ApplicationOfRatingsType.HIGHER)){
    						element("ap.principalRatingContingentValue.useHigherRating").click();
    					}
    					if (agmt.getVmPrincipalFixedTrigger().getRatingContingentValues().getApplicationOfRatings().equals(ApplicationOfRatingsType.LOWER)){
    						element("ap.principalRatingContingentValue.useLowerRating").click();
    					}
    				}
    				if (agmt.getVmPrincipalFixedTrigger().getRatingContingentValues().getIfNotRatedByAnyAgency() != null){
    					element("ap.principalRatingContingentValue.ifNotRated").click();
    				}
    			}
    		}
        }
}
