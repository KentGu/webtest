package com.lombardrisk.pages.collateral;




import com.lombardrisk.pojo.*;
import com.lombardrisk.util.PageHelper;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import java.util.List;


/**
 * Created by Kenny Wang on 2/3/2016.
 */
public class AgreementReportingControlSetupPage extends PageBase {
    public AgreementReportingControlSetupPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setupAgreementReportingControl(Agreement agmt) throws Exception {
    	
    	setupDistributionTask(agmt);

		setupLettersAttachments(agmt);
    	
    	setupLettersAndDeliveryGroupDetails(agmt);
    	
    	setupStp(agmt);
       
    	setupFourEyeConditions(agmt);
        
//        if (agmt.getNotificationLetterType() != null) {
//            element("ap.letter.type").selectByVisibleText(agmt.getNotificationLetterType().getRealValue());
//        }
//        if (agmt.getStatementSetName() != null) {
//            element("ap.statement.set.mame").input(agmt.getStatementSetName().getRealValue());
//        }
//        if (agmt.getLetterDistributionMedium() != null)
//            for (LetterDistributionMediumType distribution : agmt.getLetterDistributionMedium()) {
//                element("ap.letter.dist").selectByVisibleText(distribution.value());
//            }
    	//this method is included in setupLettersAndDeliveryGroupDetails
//    	 if (agmt.getLetterDistributionMedium() != null){
//    		 for (LetterDistributionMediumType distribution : agmt.getLetterDistributionMedium()) {
//                 element("ap.letter.dist").selectByVisibleText(distribution.value());
//             }
//    	 }
        if (agmt.getStatementSetName() != null){
        	element("ap.statementSetName").input(agmt.getStatementSetName().getRealValue());
        }
        enterNextStage();
    }
    
    
    public void setupDistributionTask(Agreement agmt) throws Exception{
    	
    	 if (agmt.getDistributionTask() != null){
         	for(int i=0;i<agmt.getDistributionTask().size();i++){
         		if (agmt.getDistributionTask().get(i).getDistributionTaskName() != null){
         			element("ap.reporting.distributionTaskName").selectByVisibleText(agmt.getDistributionTask().get(i).getDistributionTaskName().getRealValue());
         		}
         		//click report
         		if (agmt.getDistributionTask().get(i).getReport() != null){
         			element("ap.reporting.reportType").click();
         		}
         		//search attached template and click linktext
         		if (agmt.getDistributionTask().get(i).getAttachedTemplate() != null){
         			element("ap.reporting.attachedTemplate").click();
//         			if (agmt.getDistributionTask().get(i).getAttachedTemplate().getFilter() != null){
//         				element("").input(agmt.getDistributionTask().get(i).getAttachedTemplate().getFilter().getRealValue());
//         			}
//         			if (agmt.getDistributionTask().get(i).getAttachedTemplate().getLinkText() != null){
//         				element("",agmt.getDistributionTask().get(i).getAttachedTemplate().getLinkText().getRealValue()).click();
//         			}
         		}
         		if (agmt.getDistributionTask().get(i).getFrequency() != null){
         			element("ap.reporting.frequency").selectByVisibleText(agmt.getDistributionTask().get(i).getFrequency().value());
         		}
         		if (agmt.getDistributionTask().get(i).getBusinessAdjustType() != null){
         			element("ap.reporting.businessAdjustType").selectByVisibleText(agmt.getDistributionTask().get(i).getBusinessAdjustType().value());
         		}
         		if (agmt.getDistributionTask().get(i).getFrequencyStart() != null){
         			element("ap.reporting.frequencyStart").input(agmt.getDistributionTask().get(i).getFrequencyStart().getRealValue());
         		}
         		if (agmt.getDistributionTask().get(i).getMedium() != null){
         			for(int j=0;j<agmt.getDistributionTask().get(i).getMedium().size();j++){
         				element("ap.reporting.medium").selectByVisibleText(agmt.getDistributionTask().get(i).getMedium().get(j).value());
         			}
         		}
         		if (agmt.getDistributionTask().get(i).getPrincipalContact() != null){
         			if (agmt.getDistributionTask().get(i).getPrincipalContact().getPrimaryContact() != null){
         				element("ap.reporting.principalPrimaryContact").selectByVisibleText(agmt.getDistributionTask().get(i).getPrincipalContact().getPrimaryContact().getRealValue());
         			}
         			if (agmt.getDistributionTask().get(i).getPrincipalContact().getCcEmailContact() != null){
         				for (int j = 0; j < agmt.getDistributionTask().get(i).getPrincipalContact().getCcEmailContact().size(); j++) {
 							element("ap.reporting.principalCcEmailContact").selectByVisibleText(agmt.getDistributionTask().get(i).getPrincipalContact().getCcEmailContact().get(j).getRealValue());
 						}
         			}
         		}
         		if (agmt.getDistributionTask().get(i).getCounterpartyContact() != null){
         			if (agmt.getDistributionTask().get(i).getCounterpartyContact().getPrimaryContact() != null){
         				element("ap.reporting.counterpartyPrimaryContact").selectByVisibleText(agmt.getDistributionTask().get(i).getCounterpartyContact().getPrimaryContact().getRealValue());
         			}
         			if (agmt.getDistributionTask().get(i).getCounterpartyContact().getCcEmailContact() != null){
         				for (int j = 0; j < agmt.getDistributionTask().get(i).getCounterpartyContact().getCcEmailContact().size(); j++) {
 							element("ap.reporting.counterpartyCcEmailContact").selectByVisibleText(agmt.getDistributionTask().get(i).getCounterpartyContact().getCcEmailContact().get(j).getRealValue());
 						}
         			}
         		}
         		if (i != agmt.getDistributionTask().size()-1){
         			// Add a new report frequency
         			element("ap.add.reporting").click();
         		}
         	}
         }
    }
    
    
    public void setupLettersAttachments(Agreement agmt) throws Exception {
		if (agmt.getLetterAttachment() != null && agmt.getLetterAttachment().size() > 0){
			for (LetterAttachment letterAttachment : agmt.getLetterAttachment()){
				if (letterAttachment.getLetterAttachmentReportType() != null && letterAttachment.getLetterAttachmentReportType() == LetterAttachmentReportType.CALL){
					setCallReport(letterAttachment.getLetterAttachmentDetail());
				}else if (letterAttachment.getLetterAttachmentReportType() != null && letterAttachment.getLetterAttachmentReportType() == LetterAttachmentReportType.RECALL){
					setRecallReport(letterAttachment.getLetterAttachmentDetail());
				}else if (letterAttachment.getLetterAttachmentReportType() != null && letterAttachment.getLetterAttachmentReportType() == LetterAttachmentReportType.CALL_RECALL){
					setCallAndRecallReport(letterAttachment.getLetterAttachmentDetail());
				}else if (letterAttachment.getLetterAttachmentReportType() != null && letterAttachment.getLetterAttachmentReportType() == LetterAttachmentReportType.DELIVERY){
					setDeliveryReport(letterAttachment.getLetterAttachmentDetail());
				}else if (letterAttachment.getLetterAttachmentReportType() != null && letterAttachment.getLetterAttachmentReportType() == LetterAttachmentReportType.RETURN){
					setReturnReport(letterAttachment.getLetterAttachmentDetail());
				}else if (letterAttachment.getLetterAttachmentReportType() != null && letterAttachment.getLetterAttachmentReportType() == LetterAttachmentReportType.DELIVERY_RETURN){
					setDeliveryAndRecallReport(letterAttachment.getLetterAttachmentDetail());
				}else if (letterAttachment.getLetterAttachmentReportType() != null && letterAttachment.getLetterAttachmentReportType() == LetterAttachmentReportType.DISPUTE){
					setDisputeReport(letterAttachment.getLetterAttachmentDetail());
				}else if (letterAttachment.getLetterAttachmentReportType() != null && letterAttachment.getLetterAttachmentReportType() == LetterAttachmentReportType.STATEMENT){
					setStatementReport(letterAttachment.getLetterAttachmentDetail());
				}
			}
		}
	}
	public void setDisputeReport(List<LetterAttachmentDetail> attachmentDetailList) throws Exception {
		for (int i=0;i<attachmentDetailList.size();i++){
			if (attachmentDetailList.get(i).getIncludeReport() != null){
				element("ap.letterAttachments.disputeIncludeReport").selectByVisibleText(attachmentDetailList.get(i).getIncludeReport().getRealValue());
			}
			if (attachmentDetailList.get(i).getAttachedTemplate() != null){
				element("ap.letterAttachments.disputeAttachTemplate.option").clickByJavaScript();
				element("ap.letterAttachments.disputeAttachTemplate.frame").switchTo();
				setAttachedTemplate(attachmentDetailList.get(i).getAttachedTemplate());
			}
			if (attachmentDetailList.get(i).getAttachedFormat() != null){
				element("ap.letterAttachments.disputeAttachFormat.option",String.valueOf(i+1)).clickByJavaScript();
				element("ap.letterAttachments.disputeAttachFormat.frame").switchTo();
				setAttachedFormat(attachmentDetailList.get(i).getAttachedFormat());
			}
		}
	}
	public void setDeliveryAndRecallReport(List<LetterAttachmentDetail> attachmentDetailList) throws Exception {
		for (int i=0;i<attachmentDetailList.size();i++){
			if (attachmentDetailList.get(i).getIncludeReport() != null){
				element("ap.letterAttachments.deliveryAndReturnIncludeReport").selectByVisibleText(attachmentDetailList.get(i).getIncludeReport().getRealValue());
			}
			if (attachmentDetailList.get(i).getAttachedTemplate() != null){
				element("ap.letterAttachments.deliveryAndReturnAttachTemplate.option").clickByJavaScript();
				element("ap.letterAttachments.deliveryAndReturnAttachTemplate.frame").switchTo();
				setAttachedTemplate(attachmentDetailList.get(i).getAttachedTemplate());
			}
			if (attachmentDetailList.get(i).getAttachedFormat() != null){
				element("ap.letterAttachments.deliveryAndReturnAttachFormat.option",String.valueOf(i+1)).clickByJavaScript();
				element("ap.letterAttachments.deliveryAndReturnAttachFormat.frame").switchTo();
				setAttachedFormat(attachmentDetailList.get(i).getAttachedFormat());
			}
		}
	}


	public void setReturnReport(List<LetterAttachmentDetail> attachmentDetailList) throws Exception {
		for (int i=0;i<attachmentDetailList.size();i++){
			if (attachmentDetailList.get(i).getIncludeReport() != null){
				element("ap.letterAttachments.returnIncludeReport").selectByVisibleText(attachmentDetailList.get(i).getIncludeReport().getRealValue());
			}
			if (attachmentDetailList.get(i).getAttachedTemplate() != null){
				element("ap.letterAttachments.returnAttachTemplate.option").clickByJavaScript();
				element("ap.letterAttachments.returnAttachTemplate.frame").switchTo();
				setAttachedTemplate(attachmentDetailList.get(i).getAttachedTemplate());
			}
			if (attachmentDetailList.get(i).getAttachedFormat() != null){
				element("ap.letterAttachments.returnAttachFormat.option",String.valueOf(i+1)).clickByJavaScript();
				element("ap.letterAttachments.returnAttachFormat.frame").switchTo();
				setAttachedFormat(attachmentDetailList.get(i).getAttachedFormat());
			}
		}
	}
	public void setDeliveryReport(List<LetterAttachmentDetail> attachmentDetailList) throws Exception {
		for (int i=0;i<attachmentDetailList.size();i++){
			if (attachmentDetailList.get(i).getIncludeReport() != null){
				element("ap.letterAttachments.deliveryIncludeReport").selectByVisibleText(attachmentDetailList.get(i).getIncludeReport().getRealValue());
			}
			if (attachmentDetailList.get(i).getAttachedTemplate() != null){
				element("ap.letterAttachments.deliveryAttachTemplate.option").clickByJavaScript();
				element("ap.letterAttachments.deliveryAttachTemplate.frame").switchTo();
				setAttachedTemplate(attachmentDetailList.get(i).getAttachedTemplate());
			}
			if (attachmentDetailList.get(i).getAttachedFormat() != null){
				element("ap.letterAttachments.deliveryAttachFormat.option",String.valueOf(i+1)).clickByJavaScript();
				element("ap.letterAttachments.deliveryAttachFormat.frame").switchTo();
				setAttachedFormat(attachmentDetailList.get(i).getAttachedFormat());
			}
		}
	}
	public void setCallAndRecallReport(List<LetterAttachmentDetail> attachmentDetailList) throws Exception {
		for (int i=0;i<attachmentDetailList.size();i++){
			if (attachmentDetailList.get(i).getIncludeReport() != null){
				element("ap.letterAttachments.callAndRecallIncludeReport").selectByVisibleText(attachmentDetailList.get(i).getIncludeReport().getRealValue());
			}
			if (attachmentDetailList.get(i).getAttachedTemplate() != null){
				element("ap.letterAttachments.callAndRecallAttachTemplate.option").clickByJavaScript();
				element("ap.letterAttachments.callAndRecallAttachTemplate.frame").switchTo();
				setAttachedTemplate(attachmentDetailList.get(i).getAttachedTemplate());
			}
			if (attachmentDetailList.get(i).getAttachedFormat() != null){
				element("ap.letterAttachments.callAndRecallAttachFormat.option",String.valueOf(i+1)).clickByJavaScript();
				element("ap.letterAttachments.callAndRecallAttachFormat.frame").switchTo();
				setAttachedFormat(attachmentDetailList.get(i).getAttachedFormat());
			}
		}
	}
	public void setRecallReport(List<LetterAttachmentDetail> attachmentDetailList) throws Exception {
		for (int i=0;i<attachmentDetailList.size();i++){
			if (attachmentDetailList.get(i).getIncludeReport() != null){
				element("ap.letterAttachments.recallIncludeReport").selectByVisibleText(attachmentDetailList.get(i).getIncludeReport().getRealValue());
			}
			if (attachmentDetailList.get(i).getAttachedTemplate() != null){
				element("ap.letterAttachments.recallAttachTemplate.option").clickByJavaScript();
				element("ap.letterAttachments.recallAttachTemplate.frame").switchTo();
				setAttachedTemplate(attachmentDetailList.get(i).getAttachedTemplate());
			}
			if (attachmentDetailList.get(i).getAttachedFormat() != null){
				element("ap.letterAttachments.recallAttachFormat.option",String.valueOf(i+1)).clickByJavaScript();
				element("ap.letterAttachments.recallAttachFormat.frame").switchTo();
				setAttachedFormat(attachmentDetailList.get(i).getAttachedFormat());
			}
		}
	}




	public void setStatementReport(List<LetterAttachmentDetail> attachmentDetailList) throws Exception {
		for (int i=0;i<attachmentDetailList.size();i++){
			if (attachmentDetailList.get(i).getIncludeReport() != null){
				element("ap.letterAttachments.statementIncludeReport").selectByVisibleText(attachmentDetailList.get(i).getIncludeReport().getRealValue());
			}
			if (attachmentDetailList.get(i).getAttachedTemplate() != null){
				element("ap.letterAttachments.statementAttachFormat.option",String.valueOf(i+1)).clickByJavaScript();
				element("ap.letterAttachments.statementAttachFormat.frame").switchTo();
				setAttachedTemplate(attachmentDetailList.get(i).getAttachedTemplate());
			}
			if (attachmentDetailList.get(i).getAttachedFormat() != null && attachmentDetailList.get(i).getAttachedFormat().size() > 0){
				element("ap.letterAttachments.statementAttachFormat.option",String.valueOf(i+1)).clickByJavaScript();
				element("ap.letterAttachments.statementAttachFormat.frame").switchTo();
				setAttachedFormat(attachmentDetailList.get(i).getAttachedFormat());
			}
		}
	}

	public void setAttachedFormat(List<AttachedFormatType> attachedFormatTypeList) throws Exception {
		for (AttachedFormatType attachedFormatType : attachedFormatTypeList){
			element("ap.letterAttachments.callAttachFormat.select").selectByVisibleText(attachedFormatType.value());
		}
		element("ap.letterAttachments.callAttachFormat.ok").click();
		switchTo().defaultContent();
	}

	public void setAttachedTemplate(SimpleSearch simpleSearch) throws Exception {
		if (simpleSearch.getFilter() != null){
			element("ap.letterAttachments.callAttachTemplate.filter").input(simpleSearch.getFilter().getRealValue()).fireEvent("onfocus").fireEvent("onkeypress");
		}
		if (simpleSearch.getLinkText() != null){
			element("ap.letterAttachments.callAttachTemplate.linktext",simpleSearch.getLinkText().getRealValue()).click();
		}
		switchTo().defaultContent();
	}

	public void setCallReport(List<LetterAttachmentDetail> attachmentDetailList) throws Exception {
		for (int i=0;i<attachmentDetailList.size();i++){
			if (attachmentDetailList.get(i).getIncludeReport() != null){
				element("ap.letterAttachments.callIncludeReport").selectByVisibleText(attachmentDetailList.get(i).getIncludeReport().getRealValue());
			}
			if (attachmentDetailList.get(i).getAttachedTemplate() != null){
				if (attachmentDetailList.get(i).getAttachedTemplate().getFilter() != null){
					element("ap.letterAttachments.callAttachTemplate.option",String.valueOf(i+1)).clickByJavaScript();
					element("ap.letterAttachments.callAttachTemplate.frame").switchTo();
					setAttachedTemplate(attachmentDetailList.get(i).getAttachedTemplate());
				}
			}
			if (attachmentDetailList.get(i).getAttachedFormat() != null && attachmentDetailList.get(i).getAttachedFormat().size() > 0){
				element("ap.letterAttachments.callAttachFormat.option",String.valueOf(i+1)).clickByJavaScript();
				element("ap.letterAttachments.callAttachFormat.frame").switchTo();
				setAttachedFormat(attachmentDetailList.get(i).getAttachedFormat());
			}
		}
	}

    public void setupLettersAndDeliveryGroupDetails(Agreement agmt) throws Exception{
    	if (agmt.getLetterAndDeliveryGroupDetails() != null){
    		if (agmt.getLetterAndDeliveryGroupDetails().getNotificationLetterType() != null){
    			element("ap.letterAndDeliveryGroupDetails.notificationLetterType").selectByVisibleText(agmt.getLetterAndDeliveryGroupDetails().getNotificationLetterType().getRealValue());
    		}
    		if (agmt.getLetterAndDeliveryGroupDetails().getLetterTemplateType() != null){
    			element("ap.letterAndDeliveryGroupDetails.letterTemplateType").selectByVisibleText(agmt.getLetterAndDeliveryGroupDetails().getLetterTemplateType().getRealValue());
    		}
    		if (agmt.getLetterAndDeliveryGroupDetails().getExposureStatementTemplateType() != null){
    			element("ap.letterAndDeliveryGroupDetails.exposureStatementTemplateType").selectByVisibleText(agmt.getLetterAndDeliveryGroupDetails().getExposureStatementTemplateType().getRealValue());
    		}
    		if (agmt.getLetterAndDeliveryGroupDetails().getLetterDistributionMedium() !=null){
				try{
					element("ap.letterAndDeliveryGroupDetails.letterDistributionMedium").deselectAll();
				}catch (UnsupportedOperationException oue){
					logger.debug("ap.letterAndDeliveryGroupDetails.letterDistributionMedium This element is single-selector");
				}
    			for (int i = 0; i < agmt.getLetterAndDeliveryGroupDetails().getLetterDistributionMedium().size(); i++) {
					element("ap.letterAndDeliveryGroupDetails.letterDistributionMedium").selectByVisibleText(agmt.getLetterAndDeliveryGroupDetails().getLetterDistributionMedium().get(i).value());
				}
    		}
    		if (agmt.getLetterAndDeliveryGroupDetails().getMessageWorkflow() != null){
    			for (int i = 0; i < agmt.getLetterAndDeliveryGroupDetails().getMessageWorkflow().size(); i++) {
					element("ap.letterAndDeliveryGroupDetails.messageWorkflow").selectByVisibleText(agmt.getLetterAndDeliveryGroupDetails().getMessageWorkflow().get(i).getRealValue());
				}
    		}
    		if (agmt.getLetterAndDeliveryGroupDetails().getLetterAttachmentMedium() != null){
    			for (int i = 0; i < agmt.getLetterAndDeliveryGroupDetails().getLetterAttachmentMedium().size(); i++) {
					element("ap.letterAndDeliveryGroupDetails.letterAttachementMedium").selectByVisibleText(agmt.getLetterAndDeliveryGroupDetails().getLetterAttachmentMedium().get(i).getRealValue());
				}
    		}
    		if (agmt.getLetterAndDeliveryGroupDetails().getPricipalDeliveryGroup() != null
					&& agmt.getLetterAndDeliveryGroupDetails().getPricipalDeliveryGroup().size() > 0){
    			element("ap.letterAndDeliveryGroupDetails.searchPrincipalDeliveryGroup").click();
    			for (int i = 0; i < agmt.getLetterAndDeliveryGroupDetails().getPricipalDeliveryGroup().size(); i++) {
    				searchOrganisation(agmt.getLetterAndDeliveryGroupDetails().getPricipalDeliveryGroup().get(i));
				}
    			switchTo().defaultContent();
    		}
    		if (agmt.getLetterAndDeliveryGroupDetails().getInventoryDeliveryGroup() != null){
    			element("ap.letterAndDeliveryGroupDetails.inventoryDeliveryGroup").selectByVisibleText(agmt.getLetterAndDeliveryGroupDetails().getInventoryDeliveryGroup().getRealValue());
    		}
    	}
    }
    
    public void setupStp(Agreement agmt) throws Exception{
    	if (agmt.getStp() != null){
    		if (agmt.getStp().value().equals(StpType.DEFAULT.value())){
    			element("ap.stp.default").click();
    		}
    		if (agmt.getStp().value().equals(StpType.YES.value())){
    			element("ap.stp.yes").click();
    		}
    		if (agmt.getStp().value().equals(StpType.NO.value())){
    			element("ap.stp.no").click();
    		}
    	}
    }
    
    public void setupFourEyeConditions(Agreement agmt) throws Exception{
    	if (agmt.getPayFourEyeConditions() != null){
    		if (agmt.getPayFourEyeConditions().getConditionRule() != null){
    			element("ap.fourEyeConditions.payConditionRule").selectByVisibleText(agmt.getPayFourEyeConditions().getConditionRule().value());
    		}
    		if (agmt.getPayFourEyeConditions().getFixedValue().getAmount() != null){
    			element("ap.fourEyeConditions.payFixedValue").input(agmt.getPayFourEyeConditions().getFixedValue().getAmount().getRealValue());
    		}
    		if (agmt.getPayFourEyeConditions().getFixedValue().getCurrency() != null){
    			element("ap.fourEyeConditions.payFixedValueCurrency").selectByVisibleText(agmt.getPayFourEyeConditions().getFixedValue().getCurrency().getRealValue());
    		}
    		if (agmt.getPayFourEyeConditions().getPercentageOftotalExposure() != null){
    			element("ap.fourEyeConditions.payPercentageOfTotalExposure").input(agmt.getPayFourEyeConditions().getPercentageOftotalExposure().getRealValue());
    		}
    	}
    	if (agmt.getReceiveFourEyeConditions() != null){
    		if (agmt.getReceiveFourEyeConditions().getConditionRule() != null){
    			element("ap.fourEyeConditions.receiveConditionRule").selectByVisibleText(agmt.getReceiveFourEyeConditions().getConditionRule().value());
    		}
    		if (agmt.getReceiveFourEyeConditions().getFixedValue().getAmount() != null){
    			element("ap.fourEyeConditions.receiveFixedValue").input(agmt.getReceiveFourEyeConditions().getFixedValue().getAmount().getRealValue());
    		}
    		if (agmt.getReceiveFourEyeConditions().getFixedValue().getCurrency() != null){
    			element("ap.fourEyeConditions.receiveFixedValueCurrency").selectByVisibleText(agmt.getReceiveFourEyeConditions().getFixedValue().getCurrency().getRealValue());
    		}
    		if (agmt.getReceiveFourEyeConditions().getPercentageOftotalExposure() != null){
    			element("ap.fourEyeConditions.receivePercentageOfTotalExposure").input(agmt.getReceiveFourEyeConditions().getPercentageOftotalExposure().getRealValue());
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
