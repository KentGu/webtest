package com.lombardrisk.pages.collateral;

import com.lombardrisk.pojo.*;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Kenny Wang on 2/3/2016.
 */
public class LetterSetupPage extends PageBase {
    public LetterSetupPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    /**
     * create letter
     *
     * @param letterSetupPage
     */
    public void setLetterSetupPage(LetterSetupDetails letterSetupPage) throws Exception {
    	if(letterSetupPage != null) {
			setPartyAndBranchInformation(letterSetupPage);
			setExposureDetails(letterSetupPage.getExposureDetails());
			setAdditionalInfo(letterSetupPage.getAdditionalInfo());
			setEmailAttachments(letterSetupPage.getEmailAttachments());
			setMarginCall(letterSetupPage.getMarginCall());
		}
    }

	public void selectLetterType(String type) throws Exception{
		element("ls.letter.type",type).click();
	}

	public void setMarginCall(MarginCall marginCall) throws Exception{
		if(marginCall != null){
			if(marginCall.getCounterpartyDisputedAmt() != null)
				element("ls.margincall.cptyDisAmt").input(marginCall.getCounterpartyDisputedAmt().getRealValue());
		}
	}

	public void enterNext() throws Exception{
    	element("ls.next").click();
    }

	public void goToAgreementAdminPage() throws Exception{
		element("ls.AgreementAdmin").click();
	}

	public void goToAgreementStatementPage() throws Exception{
		element("ls.AgreementStatement").click();
	}
    
    public void setPartyAndBranchInformation(LetterSetupDetails letterSetupPage) throws Exception{
    	//party branch information of principal details
    	if (letterSetupPage.getPrincipalDetails() != null){
    		if (letterSetupPage.getPrincipalDetails().getContactInfo() != null){
        		element("ls.principal.contactInfo").selectByVisibleText(letterSetupPage.getPrincipalDetails().getContactInfo().getRealValue());
        	}
        	if (letterSetupPage.getPrincipalDetails().getAdditionalSignatory() != null){
        		element("ls.principal.additionalSignatory").selectByVisibleText(letterSetupPage.getPrincipalDetails().getAdditionalSignatory().getRealValue());
        	}
    	}
    	// party branch information of counterparty details
    	if (letterSetupPage.getCounterpartyDetails() != null){
    		if (letterSetupPage.getCounterpartyDetails().getContactInfo() != null){
    			element("ls.cpty.contactInfo").selectByVisibleText(letterSetupPage.getCounterpartyDetails().getContactInfo().getRealValue());
    		}
    	}
    }
    
    public void setExposureDetails(ExposureDetails exposureDetails) throws Exception{
    	if(exposureDetails != null){
    		if(exposureDetails.getDisplayExposureDetailsInLetter() != null)
				if(exposureDetails.getDisplayExposureDetailsInLetter().isCheckToDisplay() != null)
					element("ls.displayExposureDetail").check(exposureDetails.getDisplayExposureDetailsInLetter().isCheckToDisplay());
			if(exposureDetails.getDisplayLockupDetailsInLetter() != null)
				if(exposureDetails.getDisplayLockupDetailsInLetter().isCheckToDisplay() != null)
					element("ls.displayLockupDetail").check(exposureDetails.getDisplayLockupDetailsInLetter().isCheckToDisplay());
		}
    }
    
    public void setAdditionalInfo(AdditionalInfo additionalInfo) throws Exception{
    	if (additionalInfo != null){
    		if (additionalInfo.getLetterName() != null)
    			element("ls.letterName").input(additionalInfo.getLetterName().getRealValue());
    		if (additionalInfo.getComments() != null)
    			element("ls.letterComments").input(additionalInfo.getComments().getRealValue());
    		if (additionalInfo.getVmCallReturnPayInstructionBucket() != null)
    			element("ls.vmCallReturn").selectByVisibleText(additionalInfo.getVmCallReturnPayInstructionBucket().getRealValue());
    		if (additionalInfo.getVmDeliveryCallPayInstructionBucket() != null)
    			element("ls.vmDeliveryRecall").selectByVisibleText(additionalInfo.getVmDeliveryCallPayInstructionBucket().getRealValue());
    		if (additionalInfo.getImCallReturnPayInstructionBucket() != null)
    			element("ls.imCallReturn").selectByVisibleText(additionalInfo.getImCallReturnPayInstructionBucket().getRealValue());
			if (additionalInfo.getImDeliveryCallPayInstructionBucket() != null)
				element("ls.imDeliveryRecall").selectByVisibleText(additionalInfo.getImDeliveryCallPayInstructionBucket().getRealValue());
    	}
    }
    
    public void setEmailAttachments(EmailAttachments emailAttachments) throws Exception{
    	if (emailAttachments != null){
    		if (emailAttachments.isReconciliationTradesOutputReport() != null)
    			element("ls.includeTradesReport").check(emailAttachments.isReconciliationTradesOutputReport());
    		if (emailAttachments.isAssetHoldingsandValuationReport() != null)
    			element("ls.includeAssetHoldingsAndValuationReport").check(emailAttachments.isAssetHoldingsandValuationReport());
    		if (emailAttachments.isExposuresReport() != null)
    			element("ls.includeExposureReport").check(emailAttachments.isExposuresReport());
    		if (emailAttachments.isDailyExposureReport() != null)
    			element("ls.includeDailyExposureReport").check(emailAttachments.isDailyExposureReport());
			if (emailAttachments.getUploadFile() != null && !emailAttachments.getUploadFile().isEmpty()){
				for(StringType stringType : emailAttachments.getUploadFile()){
					element("ls.uploadFile").type(stringType.getRealValue());
					element("ls.uploadFile.btn").click();
				}
			}
			if (emailAttachments.getAttachmentsAdded() != null && !emailAttachments.getAttachmentsAdded().isEmpty()){
				for(AttachmentAdded attachmentAdded : emailAttachments.getAttachmentsAdded()){
					if(attachmentAdded.isRemove() != null && attachmentAdded.isRemove())
						deleteAddedAttachment(attachmentAdded);
				}
			}
    	}
    }
    
    public void deleteAddedAttachment(AttachmentAdded attachmentAdded) throws Exception{
    	element("ls.uploadFile.delete", getAttachmentsAddedRow(attachmentAdded)).click();
	}

    public void assertLetterSetupPage(LetterSetupDetails letterSetupDetails) throws Exception{
	    assertPartyAndBranchInformation(letterSetupDetails);
		assertExposureDetails(letterSetupDetails.getExposureDetails());
		assertResolutionTime(letterSetupDetails.getResolutionTime());
		assertMarginCall(letterSetupDetails.getMarginCall());
		assertAdditionalInfo(letterSetupDetails.getAdditionalInfo());
		assertEmailAttachments(letterSetupDetails.getEmailAttachments());
	}

	private void assertEmailAttachments(EmailAttachments emailAttachments) throws Exception{
		if(emailAttachments != null){
			if(emailAttachments.isReconciliationTradesOutputReport() != null)
				assertThat("ls.includeTradesReport").selected().isEqualTo(emailAttachments.isReconciliationTradesOutputReport());
			if(emailAttachments.isAssetHoldingsandValuationReport() != null)
				assertThat("ls.includeAssetHoldingsAndValuationReport").selected().isEqualTo(emailAttachments.isAssetHoldingsandValuationReport());
			if(emailAttachments.isExposuresReport() != null)
				assertThat("ls.includeExposureReport").selected().isEqualTo(emailAttachments.isExposuresReport());
			if(emailAttachments.isDailyExposureReport() != null)
				assertThat("ls.includeDailyExposureReport").selected().isEqualTo(emailAttachments.isDailyExposureReport());
			if(emailAttachments.getAttachmentsAdded() != null && ! emailAttachments.getAttachmentsAdded().isEmpty()){
				for(AttachmentAdded attachmentAdded : emailAttachments.getAttachmentsAdded()){
					assertThat("ls.uploadFile.delete", getAttachmentsAddedRow(attachmentAdded)).displayed().isTrue();
				}
			}
		}
	}

	private String getAttachmentsAddedRow(AttachmentAdded attachmentAdded) throws Exception{
		StringBuffer xpath = new StringBuffer();
		Method[] methods = xpath.getClass().getDeclaredMethods();
		for(Method method : methods){
			if(method.invoke(attachmentAdded) != null && method.getReturnType() == StringType.class) {
				StringType stringType = (StringType) method.invoke(attachmentAdded);
//				xpath.append("[td='" + stringType.getRealValue() + "']");
				xpath.append("[td='").append(stringType.getRealValue()).append("']");
			}
		}
		return xpath.toString();
	}

	private void assertAdditionalInfo(AdditionalInfo additionalInfo) throws Exception{
		if(additionalInfo != null){
			if(additionalInfo.getLetterName() != null)
				assertThat("ls.letterName").attributeValueOf("value").isEqualTo(additionalInfo.getLetterName().getRealValue());
			if(additionalInfo.getComments() != null)
				assertThat("ls.letterComments").innerText().isEqualToIgnoringWhitespace(additionalInfo.getComments().getRealValue());
			if(additionalInfo.getVmCallReturnPayInstructionBucket() != null)
				assertThat("ls.vmCallReturn").selectedText().isEqualToIgnoringWhitespace(additionalInfo.getVmCallReturnPayInstructionBucket().getRealValue());
			if(additionalInfo.getVmDeliveryCallPayInstructionBucket() != null)
				assertThat("ls.vmDeliveryRecall").selectedText().isEqualToIgnoringWhitespace(additionalInfo.getVmDeliveryCallPayInstructionBucket().getRealValue());
		}
	}

	private void assertMarginCall(MarginCall marginCall) throws Exception{
		if(marginCall != null){
			if(marginCall.getMarginCallStatus() != null)
				assertThat("ls.margincall.margincallstatus").innerText().isEqualToIgnoringWhitespace(marginCall.getMarginCallStatus().getRealValue());
			if(marginCall.getAgreementRequirementAmt() != null)
				assertThat("ls.margincall.agrreqamt").innerText().isEqualToIgnoringWhitespace(marginCall.getAgreementRequirementAmt().getRealValue());
			if(marginCall.getAgreementRequirementAmtCall() != null)
				assertThat("ls.margincall.agrreqamt.call").innerText().isEqualToIgnoringWhitespace(marginCall.getAgreementRequirementAmtCall().getRealValue());
			if(marginCall.getAgreementRequirementAmtRecall() != null)
				assertThat("ls.margincall.agrreqamt.recall").innerText().isEqualToIgnoringWhitespace(marginCall.getAgreementRequirementAmtRecall().getRealValue());
			if(marginCall.getAgreementRequirementVMAmt() != null)
				assertThat("ls.margincall.agrreqamt.vm").innerText().isEqualToIgnoringWhitespace(marginCall.getAgreementRequirementVMAmt().getRealValue());
			if(marginCall.getAgreementRequirementIMAmt() != null)
				assertThat("ls.margincall.agrreqamt.im").innerText().isEqualToIgnoringWhitespace(marginCall.getAgreementRequirementIMAmt().getRealValue());
			if(marginCall.getAgreementRequirementTSAAmt() != null)
				assertThat("ls.margincall.agrreqamt.tsa").innerText().isEqualToIgnoringWhitespace(marginCall.getAgreementRequirementTSAAmt().getRealValue());
			if(marginCall.getAgreementRequirementVMAmtDelivery() != null)
				assertThat("ls.margincall.agrreqamt.vm.deli").innerText().isEqualToIgnoringWhitespace(marginCall.getAgreementRequirementVMAmtDelivery().getRealValue());
			if(marginCall.getAgreementRequirementVMAmtReturn() != null)
				assertThat("ls.margincall.agrreqamt.vm.ret").innerText().isEqualToIgnoringWhitespace(marginCall.getAgreementRequirementVMAmtReturn().getRealValue());
			if(marginCall.getAgreementRequirementIMAmtDelivery() != null)
				assertThat("ls.margincall.agrreqamt.im.deli").innerText().isEqualToIgnoringWhitespace(marginCall.getAgreementRequirementIMAmtDelivery().getRealValue());
			if(marginCall.getAgreementRequirementIMAmtReturn() != null)
				assertThat("ls.margincall.agrreqamt.im.ret").innerText().isEqualToIgnoringWhitespace(marginCall.getAgreementRequirementIMAmtReturn().getRealValue());
			if(marginCall.getAgreementRequirementVMAmtCall() != null)
				assertThat("ls.margincall.agrreqamt.vm.cal").innerText().isEqualToIgnoringWhitespace(marginCall.getAgreementRequirementVMAmtCall().getRealValue());
			if(marginCall.getAgreementRequirementVMAmtRecall() != null)
				assertThat("ls.margincall.agrreqamt.vm.recal").innerText().isEqualToIgnoringWhitespace(marginCall.getAgreementRequirementVMAmtRecall().getRealValue());
			if(marginCall.getAgreementRequirementIMAmtCall() != null)
				assertThat("ls.margincall.agrreqamt.im.cal").innerText().isEqualToIgnoringWhitespace(marginCall.getAgreementRequirementIMAmtCall().getRealValue());
			if(marginCall.getAgreementRequirementIMAmtRecall() != null)
				assertThat("ls.margincall.agrreqamt.im.recal").innerText().isEqualToIgnoringWhitespace(marginCall.getAgreementRequirementIMAmtRecall().getRealValue());
			if(marginCall.getCounterpartyRequestAmt() != null)
				assertThat("ls.margincall.cptyreqamt").innerText().isEqualToIgnoringWhitespace(marginCall.getCounterpartyRequestAmt().getRealValue());
			if(marginCall.getAgreedAmt() != null)
				assertThat("ls.margincall.agramt").innerText().isEqualToIgnoringWhitespace(marginCall.getAgreedAmt().getRealValue());
			if(marginCall.getAgreedAmtCall() != null)
				assertThat("ls.margincall.agramt.cal").innerText().isEqualToIgnoringWhitespace(marginCall.getAgreedAmtCall().getRealValue());
			if(marginCall.getAgreedAmtRecall() != null)
				assertThat("ls.margincall.agramt.recal").innerText().isEqualToIgnoringWhitespace(marginCall.getAgreedAmtRecall().getRealValue());
			if(marginCall.getAgreedAmtDelivery() != null)
				assertThat("ls.margincall.agramt.deli").innerText().isEqualToIgnoringWhitespace(marginCall.getAgreedAmtDelivery().getRealValue());
			if(marginCall.getAgreedAmtReturn() != null)
				assertThat("ls.margincall.agramt.ret").innerText().isEqualToIgnoringWhitespace(marginCall.getAgreedAmtReturn().getRealValue());
			if(marginCall.getAgreedAmtNet() != null)
				assertThat("ls.margincall.agramt.net").innerText().isEqualToIgnoringWhitespace(marginCall.getAgreedAmtNet().getRealValue());
			if(marginCall.getAgreementRequirementSTDTolerance() != null)
				assertThat("ls.margincall.agrreqstdtol").innerText().isEqualToIgnoringWhitespace(marginCall.getAgreementRequirementSTDTolerance().getRealValue());
			if(marginCall.getImSTDTolerance() != null)
				assertThat("ls.margincall.imstdtol").innerText().isEqualToIgnoringWhitespace(marginCall.getImSTDTolerance().getRealValue());
			if(marginCall.getCounterpartyDisputedAmt() != null)
				assertThat("ls.margincall.cptyDisAmt").attributeValueOf("value").isEqualToIgnoringWhitespace(marginCall.getCounterpartyDisputedAmt().getRealValue());
		}
	}

	private void assertResolutionTime(List<ResolutionTime> list) throws Exception{
		if(list != null && !list.isEmpty()) {
			for(ResolutionTime resolutionTime : list) {
				if (resolutionTime != null) {
					if (resolutionTime.getType() == null) {
						if (resolutionTime.getTime() != null)
							assertThat("ls.resoltime.time").innerText().isEqualToIgnoringWhitespace(resolutionTime.getTime().getRealValue());
						if (resolutionTime.getTimeZone() != null)
							assertThat("ls.resoltime.timezone").innerText().isEqualToIgnoringWhitespace(resolutionTime.getTimeZone().getRealValue());
						if (resolutionTime.getDay() != null)
							assertThat("ls.resoltime.day").innerText().isEqualToIgnoringWhitespace(resolutionTime.getDay().getRealValue());
					} else {
						if (resolutionTime.getType().getRealValue().equalsIgnoreCase("IM")) {
							if (resolutionTime.getTime() != null)
								assertThat("ls.resoltime.time.im").innerText().isEqualToIgnoringWhitespace(resolutionTime.getTime().getRealValue());
							if (resolutionTime.getTimeZone() != null)
								assertThat("ls.resoltime.timezone.im").innerText().isEqualToIgnoringWhitespace(resolutionTime.getTimeZone().getRealValue());
							if (resolutionTime.getDay() != null)
								assertThat("ls.resoltime.day.im").innerText().isEqualToIgnoringWhitespace(resolutionTime.getDay().getRealValue());
						}
						if (resolutionTime.getType().getRealValue().equalsIgnoreCase("VM")) {
							if (resolutionTime.getTime() != null)
								assertThat("ls.resoltime.time.vm").innerText().isEqualToIgnoringWhitespace(resolutionTime.getTime().getRealValue());
							if (resolutionTime.getTimeZone() != null)
								assertThat("ls.resoltime.timezone.vm").innerText().isEqualToIgnoringWhitespace(resolutionTime.getTimeZone().getRealValue());
							if (resolutionTime.getDay() != null)
								assertThat("ls.resoltime.day.vm").innerText().isEqualToIgnoringWhitespace(resolutionTime.getDay().getRealValue());
						}
					}
				}
			}
		}
	}

	private void assertExposureDetails(ExposureDetails exposureDetails) throws Exception{
		if(exposureDetails != null){
			if(exposureDetails.getBaseCurrency() != null)
				assertThat("ls.expodetail.baseccy").innerText().isEqualToIgnoringWhitespace(exposureDetails.getBaseCurrency().getRealValue());
			if(exposureDetails.getTotalExposure() != null)
				assertThat("ls.expodetail.tolexpo").innerText().isEqualToIgnoringWhitespace(exposureDetails.getTotalExposure().getRealValue());
			if(exposureDetails.getMinTransferAmountPrincipal() != null)
				assertThat("ls.expodetail.mintransamt").innerText().startsWith(exposureDetails.getMinTransferAmountPrincipal().getRealValue() + "(Principal)");
			if(exposureDetails.getMinTransferAmountCounterparty() != null)
				assertThat("ls.expodetail.mintransamt").innerText().endsWith(exposureDetails.getMinTransferAmountCounterparty().getRealValue() + "(Counterparty)");
			if(exposureDetails.getRoundingAmountPrincipal() != null)
				assertThat("ls.expodetail.roundamt").innerText().startsWith(exposureDetails.getRoundingAmountPrincipal().getRealValue() + "(Principal)");
			if(exposureDetails.getRoundingAmountCounterparty() != null)
				assertThat("ls.expodetail.roundamt").innerText().contains(" " + exposureDetails.getRoundingAmountCounterparty().getRealValue() + "(Counterparty)");
			if(exposureDetails.getDisplayExposureDetailsInLetter() != null){
				DisplayExposureDetailsInLetter displayExposureDetailsInLetter = exposureDetails.getDisplayExposureDetailsInLetter();
				if(displayExposureDetailsInLetter.isCheckToDisplay() != null)
					assertThat("ls.displayExposureDetail").selected().isEqualTo(displayExposureDetailsInLetter.isCheckToDisplay());
				if(displayExposureDetailsInLetter.getLeftPrincipalIA() != null)
					assertThat("ls.expodetail.prinia.lt").innerText().isEqualToIgnoringWhitespace(displayExposureDetailsInLetter.getLeftPrincipalIA().getRealValue());
				if(displayExposureDetailsInLetter.getRightPrincipalIA() != null)
					assertThat("ls.expodetail.prinia.rt").innerText().isEqualToIgnoringWhitespace(displayExposureDetailsInLetter.getRightPrincipalIA().getRealValue());
				if(displayExposureDetailsInLetter.getLeftCounterpartyIA() != null)
					assertThat("ls.expodetail.cptyia.lt").innerText().isEqualToIgnoringWhitespace(displayExposureDetailsInLetter.getLeftCounterpartyIA().getRealValue());
				if(displayExposureDetailsInLetter.getRightCounterpartyIA() != null)
					assertThat("ls.expodetail.cptyia.rt").innerText().isEqualToIgnoringWhitespace(displayExposureDetailsInLetter.getRightCounterpartyIA().getRealValue());
				if(displayExposureDetailsInLetter.getLeftPrincipalThreshold() != null)
					assertThat("ls.expodetail.printhre.lt").innerText().isEqualToIgnoringWhitespace(displayExposureDetailsInLetter.getLeftPrincipalThreshold().getRealValue());
				if(displayExposureDetailsInLetter.getRightPrincipalThreshold() != null)
					assertThat("ls.expodetail.printhre.rt").innerText().isEqualToIgnoringWhitespace(displayExposureDetailsInLetter.getRightPrincipalThreshold().getRealValue());
				if(displayExposureDetailsInLetter.getLeftCounterpartyThreshold() != null)
					assertThat("ls.expodetail.cptythre.lt").innerText().isEqualToIgnoringWhitespace(displayExposureDetailsInLetter.getLeftCounterpartyThreshold().getRealValue());
				if(displayExposureDetailsInLetter.getRightCounterpartyThreshold() != null)
					assertThat("ls.expodetail.cptythre.rt").innerText().isEqualToIgnoringWhitespace(displayExposureDetailsInLetter.getRightCounterpartyThreshold().getRealValue());
				if(displayExposureDetailsInLetter.getLeftAdjustedExposureAmount() != null)
					assertThat("ls.expodetail.adjexpoamt.lt").innerText().isEqualToIgnoringWhitespace(displayExposureDetailsInLetter.getLeftAdjustedExposureAmount().getRealValue());
				if(displayExposureDetailsInLetter.getRightAdjustedExposureAmount() != null)
					assertThat("ls.expodetail.adjexpoamt.rt").innerText().isEqualToIgnoringWhitespace(displayExposureDetailsInLetter.getRightAdjustedExposureAmount().getRealValue());
				if(displayExposureDetailsInLetter.getLeftValueOfConfirmedCollateralHeld() != null)
					assertThat("ls.expodetail.valconcolhel.lt").innerText().isEqualToIgnoringWhitespace(displayExposureDetailsInLetter.getLeftValueOfConfirmedCollateralHeld().getRealValue());
				if(displayExposureDetailsInLetter.getRightValueOfConfirmedCollateralHeld() != null)
					assertThat("ls.expodetail.valconcolhel.rt").innerText().isEqualToIgnoringWhitespace(displayExposureDetailsInLetter.getRightValueOfConfirmedCollateralHeld().getRealValue());
				if(displayExposureDetailsInLetter.getLeftValueOfPendingCollateralHeld() != null)
					assertThat("ls.expodetail.valpendcolhel.lt").innerText().isEqualToIgnoringWhitespace(displayExposureDetailsInLetter.getLeftValueOfPendingCollateralHeld().getRealValue());
				if(displayExposureDetailsInLetter.getRightValueOfPendingCollateralHeld() != null)
					assertThat("ls.expodetail.valpendcolhel.rt").innerText().isEqualToIgnoringWhitespace(displayExposureDetailsInLetter.getRightValueOfPendingCollateralHeld().getRealValue());
				if(displayExposureDetailsInLetter.getLeftValueOfConfirmedCollateralDelivered() != null)
					assertThat("ls.expodetail.valconcoldeli.lt").innerText().isEqualToIgnoringWhitespace(displayExposureDetailsInLetter.getLeftValueOfConfirmedCollateralDelivered().getRealValue());
				if(displayExposureDetailsInLetter.getRightValueOfConfirmedCollateralDelivered() != null)
					assertThat("ls.expodetail.valconcoldeli.rt").innerText().isEqualToIgnoringWhitespace(displayExposureDetailsInLetter.getRightValueOfConfirmedCollateralDelivered().getRealValue());
				if(displayExposureDetailsInLetter.getLeftValueOfPendingCollateralDelivered() != null)
					assertThat("ls.expodetail.valpendcoldeli.lt").innerText().isEqualToIgnoringWhitespace(displayExposureDetailsInLetter.getLeftValueOfPendingCollateralDelivered().getRealValue());
				if(displayExposureDetailsInLetter.getRightValueOfPendingCollateralDelivered() != null)
					assertThat("ls.expodetail.valpendcoldeli.rt").innerText().isEqualToIgnoringWhitespace(displayExposureDetailsInLetter.getRightValueOfPendingCollateralDelivered().getRealValue());
				if(displayExposureDetailsInLetter.getLeftNetMarginRequirement() != null)
					assertThat("ls.expodetail.netmarreq.lt").innerText().isEqualToIgnoringWhitespace(displayExposureDetailsInLetter.getLeftNetMarginRequirement().getRealValue());
				if(displayExposureDetailsInLetter.getRightNetMarginRequirement() != null)
					assertThat("ls.expodetail.netmarreq.rt").innerText().isEqualToIgnoringWhitespace(displayExposureDetailsInLetter.getRightNetMarginRequirement().getRealValue());
				if(displayExposureDetailsInLetter.getLeftDeliveryAmount() != null)
					assertThat("ls.expodetail.deliamt.lt").innerText().isEqualToIgnoringWhitespace(displayExposureDetailsInLetter.getLeftDeliveryAmount().getRealValue());
				if(displayExposureDetailsInLetter.getRightDeliveryAmount() != null)
					assertThat("ls.expodetail.deliamt.rt").innerText().isEqualToIgnoringWhitespace(displayExposureDetailsInLetter.getRightDeliveryAmount().getRealValue());
				if(displayExposureDetailsInLetter.getLeftRecallAmount() != null)
					assertThat("ls.expodetail.recallamt.lt").innerText().isEqualToIgnoringWhitespace(displayExposureDetailsInLetter.getLeftRecallAmount().getRealValue());
				if(displayExposureDetailsInLetter.getRightRecallAmount() != null)
					assertThat("ls.expodetail.recallamt.rt").innerText().isEqualToIgnoringWhitespace(displayExposureDetailsInLetter.getRightRecallAmount().getRealValue());
				if(displayExposureDetailsInLetter.getLeftIADeliveryAmount() != null)
					assertThat("ls.expodetail.iadeliamt.lt").innerText().isEqualToIgnoringWhitespace(displayExposureDetailsInLetter.getLeftIADeliveryAmount().getRealValue());
				if(displayExposureDetailsInLetter.getRightIADeliveryAmount() != null)
					assertThat("ls.expodetail.iadeliamt.rt").innerText().isEqualToIgnoringWhitespace(displayExposureDetailsInLetter.getRightIADeliveryAmount().getRealValue());
				if(displayExposureDetailsInLetter.getLeftIARecallAmount() != null)
					assertThat("ls.expodetail.iarecallamt.lt").innerText().isEqualToIgnoringWhitespace(displayExposureDetailsInLetter.getLeftIARecallAmount().getRealValue());
				if(displayExposureDetailsInLetter.getRightIARecallAmount() != null)
					assertThat("ls.expodetail.iarecallamt.rt").innerText().isEqualToIgnoringWhitespace(displayExposureDetailsInLetter.getRightIARecallAmount().getRealValue());
			}
			if(exposureDetails.getDisplayLockupDetailsInLetter() != null){
				DisplayLockupDetailsInLetter displayLockupDetailsInLetter = exposureDetails.getDisplayLockupDetailsInLetter();
				if(displayLockupDetailsInLetter.isCheckToDisplay() != null)
					assertThat("ls.displayLockupDetail").selected().isEqualTo(displayLockupDetailsInLetter.isCheckToDisplay());
				if(displayLockupDetailsInLetter.getLeftLockupMarginRequired() != null)
					assertThat("ls.expodetail.lkupmargreq.lt").innerText().isEqualToIgnoringWhitespace(displayLockupDetailsInLetter.getLeftLockupMarginRequired().getRealValue());
				if(displayLockupDetailsInLetter.getRightLockupMarginRequired() != null)
					assertThat("ls.expodetail.lkupmargreq.rt").innerText().isEqualToIgnoringWhitespace(displayLockupDetailsInLetter.getRightLockupMarginRequired().getRealValue());
				if(displayLockupDetailsInLetter.getLeftCurrentValueOfLockupMargin() != null)
					assertThat("ls.expodetail.cutvaloflkupmarg.lt").innerText().isEqualToIgnoringWhitespace(displayLockupDetailsInLetter.getLeftCurrentValueOfLockupMargin().getRealValue());
				if(displayLockupDetailsInLetter.getRightCurrentValueOfLockupMargin() != null)
					assertThat("ls.expodetail.cutvaloflkupmarg.rt").innerText().isEqualToIgnoringWhitespace(displayLockupDetailsInLetter.getRightCurrentValueOfLockupMargin().getRealValue());
				if(displayLockupDetailsInLetter.getLeftLockupMarginCall() != null)
					assertThat("ls.expodetail.lkupmargcall.lt").innerText().isEqualToIgnoringWhitespace(displayLockupDetailsInLetter.getLeftLockupMarginCall().getRealValue());
				if(displayLockupDetailsInLetter.getRightLockupMarginCall() != null)
					assertThat("ls.expodetail.lkupmargcall.rt").innerText().isEqualToIgnoringWhitespace(displayLockupDetailsInLetter.getRightLockupMarginCall().getRealValue());
			}
		}
	}

	private void assertPartyAndBranchInformation(LetterSetupDetails letterSetupDetails) throws Exception{
		if(letterSetupDetails.getAgreementDescription() != null)
			assertThat("ls.partybranch.agrdesc", letterSetupDetails.getAgreementDescription().getRealValue()).displayed().isTrue();
		BranchInformation branchInformation;
		if(letterSetupDetails.getPrincipalDetails() != null){
			branchInformation = letterSetupDetails.getPrincipalDetails();
			if(branchInformation.getShortName() != null)
				assertThat("ls.partybranch.shortname.prin").innerText().isEqualToIgnoringWhitespace(branchInformation.getShortName().getRealValue());
			if(branchInformation.getLongName() != null)
				assertThat("ls.partybranch.longname.prin").innerText().isEqualToIgnoringWhitespace(branchInformation.getLongName().getRealValue());
			if(branchInformation.getCode() != null)
				assertThat("ls.partybranch.code.prin").innerText().isEqualToIgnoringWhitespace(branchInformation.getCode().getRealValue());
			if(branchInformation.getStatus() != null)
				assertThat("ls.partybranch.status.prin").innerText().isEqualToIgnoringWhitespace(branchInformation.getStatus().getRealValue());
			if(branchInformation.getContactInfo() != null)
				assertThat("ls.principal.contactInfo").selectedText().isEqualToIgnoringWhitespace(branchInformation.getContactInfo().getRealValue());
			if(branchInformation.getAdditionalSignatory() != null)
				assertThat("ls.principal.additionalSignatory").selectedText().isEqualToIgnoringWhitespace(branchInformation.getAdditionalSignatory().getRealValue());
			if(branchInformation.getCcEmailContact() != null)
				assertThat("ls.partybranch.cc.prin").innerText().isEqualToIgnoringWhitespace(branchInformation.getCcEmailContact().getRealValue());
		}
		if(letterSetupDetails.getCounterpartyDetails() != null){
			branchInformation = letterSetupDetails.getCounterpartyDetails();
			if(branchInformation.getShortName() != null)
				assertThat("ls.partybranch.shortname.cpty").innerText().isEqualToIgnoringWhitespace(branchInformation.getShortName().getRealValue());
			if(branchInformation.getLongName() != null)
				assertThat("ls.partybranch.longname.cpty").innerText().isEqualToIgnoringWhitespace(branchInformation.getLongName().getRealValue());
			if(branchInformation.getCode() != null)
				assertThat("ls.partybranch.code.cpty").innerText().isEqualToIgnoringWhitespace(branchInformation.getCode().getRealValue());
			if(branchInformation.getStatus() != null)
				assertThat("ls.partybranch.status.cpty").innerText().isEqualToIgnoringWhitespace(branchInformation.getStatus().getRealValue());
			if(branchInformation.getContactInfo() != null)
				assertThat("ls.cpty.contactInfo").selectedText().isEqualToIgnoringWhitespace(branchInformation.getContactInfo().getRealValue());
			if(branchInformation.getCcEmailContact() != null)
				assertThat("ls.partybranch.cc.cpty").innerText().isEqualToIgnoringWhitespace(branchInformation.getCcEmailContact().getRealValue());
		}
	}
}
