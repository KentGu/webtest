package com.lombardrisk.pages.collateral;


import com.lombardrisk.pojo.*;
import com.lombardrisk.util.Biz;
import com.lombardrisk.util.PageHelper;
import org.openqa.selenium.WebDriverException;
import org.yiwan.webcore.util.PropHelper;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import java.io.File;
import java.util.List;

/**
 * @author Kenny Wang
 */
public final class CollateralPreferencesPage extends PageBase {

    public CollateralPreferencesPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    /**
     * set collateral preference on collateral->configuration->preference page,
     * once you used this method to change the default configuration, please
     * make sure to use method resetCollateralPreference() to restore the
     * configuration in the tearDown() method
     *
     * @param cp
     * see resetCollateralPreference
     */
    public void setCollateralPreference(CollateralPreference cp) throws Exception {
		//Letter Information Tab
		setLetterInformationTab(cp.getLetterInformation());

		//Collateral Rules Tab
		setCollateralRulesTab(cp.getCollateralRules());

		//General Tab
		setGeneralTab(cp.getGeneral());

		//Task Manager Tab
		setTaskManagerTab(cp.getTaskManager());
    }

	public void assertCollateralPreference(CollateralPreference cp) throws Exception {
		//Letter Information Tab

		//Collateral Rules Tab
		assertCollateralRulesTab(cp.getCollateralRules());

		//General Tab

		//Task Manager Tab
	}
	public void assertCollateralRulesTab(CollateralPreferenceCollateralRule cpr) throws Exception{
		if (cpr != null){
			element("co.cl.tab").clickByJavaScript();
			PageHelper.d31489Workaround(element("hm.return.col"), this, element("co.cl.tab"));
			if(cpr.isWorkflowFourEyes()!= null){
				assertThat(locator("co.workflow.four.eyes")).selected().isEqualTo(cpr.isWorkflowFourEyes());
			}
		}
	}

	public void setLetterInformationTab(CollateralPreferenceLetterInformation cpli) throws Exception{
		if(cpli != null){
			element("co.li.tab").clickByJavaScript();
			PageHelper.d31489Workaround(element("hm.return.col"), this, element("co.li.tab"));
			if(cpli.getLetterHeader() != null){
				element("co.li.letter.header").input(cpli.getLetterHeader().getRealValue());
			}
			if(cpli.getLetterFooter() != null){
				element("co.li.letter.footer").input(cpli.getLetterFooter().getRealValue());
			}
			if(cpli.getLetterEmailText() != null){
				element("co.li.letter.email.text").input(cpli.getLetterEmailText().getRealValue());
			}
			if(cpli.getSecondPrincipalLogo() != null ){
				String filePath = Biz.getWorkspace()+ PropHelper.getProperty("logo.path")+cpli.getSecondPrincipalLogo().getRealValue();
				element("co.li.file").type(filePath.replace("/", File.separator));
			}
			if(cpli.getLeftMargin() != null){
				element("co.li.left.margin").setValue("").type(cpli.getLeftMargin().getRealValue());
			}
			if(cpli.getRightMargin() != null){
				element("co.li.right.margin").setValue("").type(cpli.getRightMargin().getRealValue());
			}
			if(cpli.getTopMargin() != null){
				element("co.li.top.margin").setValue("").type(cpli.getTopMargin().getRealValue());
			}
			if(cpli.getBottomMargin() != null){
				element("co.li.bottom.margin").setValue("").type(cpli.getBottomMargin().getRealValue());
			}
			save();
		}
	}

	public void setCollateralRulesTab(CollateralPreferenceCollateralRule cpr) throws Exception{
		if (cpr != null){
			element("co.cl.tab").clickByJavaScript();
			PageHelper.d31489Workaround(element("hm.return.col"), this, element("co.cl.tab"));
			if(cpr.isAgreementFourEyes() != null)
				element("co.agreement.four.eyes").check(cpr.isAgreementFourEyes());
			if(cpr.isTradesFourEyes() != null)
				element("co.trade.four.eyes").check(cpr.isTradesFourEyes());
			if(cpr.isWorkflowFourEyes()!= null)
				element("co.workflow.four.eyes").check(cpr.isWorkflowFourEyes());
			if(cpr.isSettlementStatusFourEyes() != null)
				element("co.settlement.status.four.eyes").check(cpr.isSettlementStatusFourEyes());
			if(cpr.getSettlementStatusFourEyesControl() != null){
				element("co.apply.four.eyes.control").clickByJavaScript();
				element("co.four.eye.control.frame").switchTo();
				setFourEyesControl(cpr.getSettlementStatusFourEyesControl());
				switchTo().defaultContent();
			}
			if(cpr.isSettlementDataFourEyes() != null)
				element("co.settlement.data.four.eyes").check(cpr.isSettlementDataFourEyes());
			if(cpr.isSecuritiesFourEyes() != null)
				element("co.securities.four.eyes").check(cpr.isSecuritiesFourEyes());
			if(cpr.isOrganisationFourEyes() != null)
				element("co.organisation.four.eyes").check(cpr.isOrganisationFourEyes());
			if(cpr.isRuleTemplateFourEyes() != null)
				element("co.eligibility.rule.template.four.eyes").check(cpr.isRuleTemplateFourEyes());
			if(cpr.isUserAdministrationFourEyesFor() != null)
				element("co.user.admin.four.eyes").check(cpr.isUserAdministrationFourEyesFor());
			if(cpr.isRoleAdministrationFourEyes() != null)
				element("co.role.admin.four.eyes").check(cpr.isRoleAdministrationFourEyes());
			if(cpr.isConditionSettlementStatusFourEyes() != null)
				element("co.condition.settlement.status.four.eyes").check(cpr.isConditionSettlementStatusFourEyes());
			if(cpr.getConditionSettlementStatusFourEyesControl() != null){
				element("co.four.eyes.conditional.approval.control").clickByJavaScript();
				element("co.four.eye.control.condition.frame").switchTo();
				setFourEyesControl(cpr.getConditionSettlementStatusFourEyesControl());
				switchTo().defaultContent();
			}
			if(cpr.isNoAuthorisedStatus() != null)
				element("co.no.authorised.status").check(cpr.isNoAuthorisedStatus());
			if(cpr.isIncludePendingCancelled() != null)
				element("co.include.pending.cancelled").check(cpr.isIncludePendingCancelled());
			if(cpr.isWorkflowOverwrite() != null)
				element("co.workflow.overwrite").check(cpr.isWorkflowOverwrite());
			if(cpr.isWorkflowWaive() != null)
				element("co.workflow.waive").check(cpr.isWorkflowWaive());
			if(cpr.isWorkflowDispute()!= null)
				element("co.workflow.dispute").check(cpr.isWorkflowDispute());
			if(cpr.isApplyRoundingRuleToSubstitutionLetter() != null)
				element("co.apply.rounding.rule.for.sub.letter").check(cpr.isApplyRoundingRuleToSubstitutionLetter());
			if(cpr.isMandatoryCommentWhenDisputeCondition() != null)
				element("co.mandatory.comment.when.dispute.condition").check(cpr.isMandatoryCommentWhenDisputeCondition());
			if(cpr.getConcentrationLimitCalc() != null){
				element("co.con.lim.cal").selectByVisibleText(cpr.getConcentrationLimitCalc().value());
			}
			save();
		}
	}

	public void setGeneralTab(CollateralPreferenceGeneral cpg) throws Exception{
		if (cpg != null){
			element("co.general").clickByJavaScript();
			PageHelper.d31489Workaround(element("hm.return.col"), this, element("co.general"));
			if(cpg.getDefaultFXRates() != null)
				element("co.fx.rate").selectByVisibleText(cpg.getDefaultFXRates().getRealValue());
			if(cpg.getDefaultPriceSource() != null)
				element("co.price.source").selectByVisibleText(cpg.getDefaultPriceSource().getRealValue());
			if(cpg.getFeedStatusDays() != null)
				element("co.feed.status.days").setValue(cpg.getFeedStatusDays().getRealValue());
			if(cpg.getRefreshCachedDataOfInventoryManager() != null){
				element("co.refresh.cached.data").selectByVisibleText(cpg.getRefreshCachedDataOfInventoryManager().getRealValue());
			}
			if(cpg.getFeedResultDisplayNumber() != null)
				element("co.feed.result.display.no").setValue(cpg.getFeedResultDisplayNumber().getRealValue());
			if(cpg.getOrganizationThresholdLineNumber() != null)
				element("co.org.threshold.line.no").setValue(cpg.getOrganizationThresholdLineNumber().getRealValue());
			if(cpg.isAllowTradeFeedToOverrideManuallyLockedTrades() != null)
				element("co.override.locked.trade.feed").check(cpg.isAllowTradeFeedToOverrideManuallyLockedTrades());
			if(cpg.isOnlyAllowAssetBookingFeedForApprovedAgreements() != null)
				element("co.only.allow.asset.booking.feed.for.approved.agreements").check(cpg.isOnlyAllowAssetBookingFeedForApprovedAgreements());
			save(cpg.getAlertHandling());
		}
	}

	public void setTaskManagerTab(CollateralPreferenceTaskManager cptm) throws Exception{
		if(cptm != null){
			element("co.tm.tab").clickByJavaScript();
			PageHelper.d31489Workaround(element("hm.return.col"), this, element("co.tm.tab"));
			alert().disable();
			if(cptm.getFileThresholdToSplit() != null){
				element("co.critical.file.size").setValue("").type(cptm.getFileThresholdToSplit().getRealValue());
			}
			if(cptm.isDeletePartialFile() != null){
                element("co.tm.delparfile", cptm.isDeletePartialFile()?"1":"2").check(true);
			}
			if(cptm.getSplitFileSize() != null){
				element("co.max.row.num.of.file.size.after.split").input(cptm.getSplitFileSize().getRealValue());
			}
			if(cptm.getThreadPoolSize() != null){
				element("co.pool.size").input(cptm.getThreadPoolSize().getRealValue());
			}
			if(cptm.getMaxErrorNumber() != null){
				element("co.error.trade.count").input(cptm.getMaxErrorNumber().getRealValue());
			}
			if(cptm.getTempFolderLocation() != null){
				element("co.temp.file.location").input(cptm.getTempFolderLocation().getRealValue());
			}
			alert().enable();
			save();
		}
	}

	private void save(List<Message> list) throws Exception{
		element("co.save").click();
		if (list != null && !list.isEmpty()) {
			for (Message handle : list) {
				if (handle.getContent() != null && !handle.getContent().getRealValue().isEmpty()) {
					if (alert().getText().matches(handle.getContent().getRealValue())) {
						if (handle.isAccept())
							alert().accept();
						else
							alert().dismiss();
					} else {
						throw new WebDriverException(String.format("alert %s was not found", handle.getContent().getRealValue()));
					}
				} else if (alert().isPresent()) {
					if (handle.isAccept()) {
						alert().accept();
					} else {
						alert().dismiss();
					}
				}

			}
		}
		PageHelper.d31489Workaround(element("hm.return.col"), this);
	}

	private void save() throws Exception{
		element("co.save").fireEvent(Biz.FIRE_EVENT_ONCLICK);
		PageHelper.d31489Workaround(element("hm.return.col"), this);
		if(alert().isPresent()){
			alert().accept();
		}
		waitThat().document().toBeReady();
		waitThat().jQuery().toBeInactive();
		PageHelper.d33640Workaround();
	}

	private void setFourEyesControl(FourEyesControl fourEyesControl) throws Exception{
		element("co.four.eye.control.frame.clear").click();
		if(fourEyesControl.getFromStatus() != null && !fourEyesControl.getFromStatus().isEmpty()){
			element("co.four.eye.control.frame.fromstatus").deselectAll();
			for(StringType value : fourEyesControl.getFromStatus()){
				element("co.four.eye.control.frame.fromstatus").selectByVisibleText(value.getRealValue());
			}
		}
		if(fourEyesControl.getExcludeFromStatus() !=null && !fourEyesControl.getExcludeFromStatus().isEmpty()){
			element("co.four.eye.control.frame.excludefromstatus").deselectAll();
			for(StringType value : fourEyesControl.getExcludeFromStatus()){
				element("co.four.eye.control.frame.excludefromstatus").selectByVisibleText(value.getRealValue());
			}
		}
		if(fourEyesControl.getToStatus() != null && !fourEyesControl.getToStatus().isEmpty()){
			element("co.four.eye.control.frame.tostatus").deselectAll();
			for(StringType value : fourEyesControl.getToStatus()){
				element("co.four.eye.control.frame.tostatus").selectByVisibleText(value.getRealValue());
			}
		}
		if(fourEyesControl.getExcludeToStatus() != null && !fourEyesControl.getExcludeToStatus().isEmpty()){
			element("co.four.eye.control.frame.excludetostatus").deselectAll();
			for(StringType value : fourEyesControl.getExcludeToStatus()){
				element("co.four.eye.control.frame.excludetostatus").selectByVisibleText(value.getRealValue());
			}
		}
		element("co.four.eye.control.frame.save").click();
	}
}
