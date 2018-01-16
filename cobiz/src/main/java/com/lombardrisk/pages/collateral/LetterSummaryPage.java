package com.lombardrisk.pages.collateral;


import com.lombardrisk.pojo.*;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Kenny Wang on 2/3/2016.
 */
public class LetterSummaryPage extends PageBase {
    public LetterSummaryPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void accept() throws Exception{
    	element("letterSummary.accept").clickSmartly();
    }

    public void edit() throws Exception{
        element("letterSummary.edit").click();
    }
    
    public void switchToLetterAndPartyInformationTab() throws Exception{
    	element("letterSummary.letterTab").click();
    }
    
    public void switchToActionAndExposureTab() throws Exception{
    	element("letterSummary.actionTab").click();
    }
    
    public void switchToCommentsAndAttachementTab() throws Exception{
    	element("letterSummary.timingTab").click();
    }
    
    public void switchToRequestTypeAndAssetSelectionTab() throws Exception{
    	element("letterSummary.requestTab").click();
    }
    
    public void switchToAssetDetailsTab() throws Exception{
    	element("letterSummary.assetsDetailTab").click();
    }
    
    public void switchToLetterSummaryTab() throws Exception{
    	element("letterSummary.letterSummaryTab").click();
    }
    
    public void switchToAgreementStatementPage() throws Exception{
    	element("letterSummary.agreementStatementTab").click();
    }

    public void switchToAgreementAdminPage() throws Exception{
    	element("letterSummary.agreementAdminTab").click();
    }
    
    public void switchToLetterAdminPage() throws Exception{
    	element("letterSummary.letterAdminTab").click();
    }

    public void assertLetterSummaryPage(MarginLetterSummary marginLetterSummary) throws Exception{
        if(marginLetterSummary != null){
            switchToLetterSummaryTab();
            assertLetterAndPartyInformation(marginLetterSummary.getLetterAndPartyInformation());
            assertActionAndExposure(marginLetterSummary.getActionAndExposure());
            assertTimingCommentsAndAttachment(marginLetterSummary.getTimingCommentsAndAttachment());
            assertRequestTypeAndAssetSelection(marginLetterSummary.getRequestTypeAndAssetSelection());
            assertAssetsDetails(marginLetterSummary.getAssetsDetails());
        }
    }

    public void assertLetterSummaryPage(MarginLetter marginLetter) throws Exception{
        if(marginLetter != null){
            assertLetterAndPartyInformation(marginLetter);
            assertActionAndExposure(marginLetter);
            assertTimingCommentsAndAttachment(marginLetter);
            assertRequestTypeAndAssetSelection(marginLetter);
            assertAssetsDetails(marginLetter);
        }
    }

    private void assertAssetsDetails(MarginLetter marginLetter) throws Exception{
        if(marginLetter.getRequestTypeAndAssetSelection() != null){
            RequestTypeAndAssetSelection requestTypeAndAssetSelection = marginLetter.getRequestTypeAndAssetSelection();
            if(requestTypeAndAssetSelection.getEligibleAssetSelection() != null && !requestTypeAndAssetSelection.getEligibleAssetSelection().isEmpty()){
                for(EligibleAssetSelection eligibleAssetSelection : requestTypeAndAssetSelection.getEligibleAssetSelection()){
                    if(eligibleAssetSelection.getMovementAssets() != null && !eligibleAssetSelection.getMovementAssets().isEmpty()){
                        for(MovementAsset movementAsset : eligibleAssetSelection.getMovementAssets()){
                            if(movementAsset.getEligibleAsset() != null)
                                assertThat("letterSummary.eligibleasset.row", getEligibleAssetRow(movementAsset.getEligibleAsset())).displayed().isTrue();
                        }
                    }
                }
            }
        }
    }

    private void assertAssetsDetails(List<RequestTypeAndAssetSelection> list) throws Exception{
        if(list != null && !list.isEmpty()){
            for(RequestTypeAndAssetSelection requestTypeAndAssetSelection : list){
                for(EligibleAssetSelection eligibleAssetSelection : requestTypeAndAssetSelection.getEligibleAssetSelection()){
                    if(eligibleAssetSelection.getMovementAssets() != null && !eligibleAssetSelection.getMovementAssets().isEmpty()){
                        for(MovementAsset movementAsset : eligibleAssetSelection.getMovementAssets()){
                            if(movementAsset.getEligibleAsset() != null){
                                assertThat("letterSummary.eligibleasset.row", getEligibleAssetRow(movementAsset.getEligibleAsset())).displayed().isTrue();
                            }
                        }
                    }
                }
            }
        }
    }

    private String getEligibleAssetRow(EligibleAsset eligibleAsset) throws Exception{
        StringBuffer xpath = new StringBuffer();
        Method[] methods = eligibleAsset.getClass().getDeclaredMethods();
        for(Method method : methods){
            if(method.getName().startsWith("get") && method.invoke(eligibleAsset) != null
                    && method.getReturnType() == StringType.class
                    && !method.getName().equals("getAssetClassName")){
                StringType stringType = (StringType) method.invoke(eligibleAsset);
//                xpath.append("[td[normalize-space(text()[2])='" + stringType.getRealValue() + "']]");
                xpath.append("[td[normalize-space(text()[2])='").append(stringType.getRealValue()).append("']]");
            }
        }
        return xpath.toString();
    }

    private void assertRequestTypeAndAssetSelection(MarginLetter marginLetter) throws Exception{
        if(marginLetter.getRequestTypeAndAssetSelection() != null){
            if(marginLetter.getRequestTypeAndAssetSelection().getRequestType() != null)
                assertThat("letterSummary.request.type").innerText().isEqualToIgnoringWhitespace("Request Type: " + marginLetter.getRequestTypeAndAssetSelection().getRequestType().getRealValue());
        }
        if(marginLetter.getLetterSetupDetails() != null && marginLetter.getLetterSetupDetails().getExposureDetails() != null && marginLetter.getLetterSetupDetails().getExposureDetails().getBaseCurrency() != null)
            assertThat("letterSummary.baseccy.act").innerText().isEqualToIgnoringWhitespace("Base Currency: " + marginLetter.getLetterSetupDetails().getExposureDetails().getBaseCurrency().getRealValue());
    }

    private void assertRequestTypeAndAssetSelection(RequestTypeAndAssetSummary requestTypeAndAssetSummary) throws Exception{
        if(requestTypeAndAssetSummary != null){
            if(requestTypeAndAssetSummary.getRequestType() != null)
                assertThat("letterSummary.request.type").innerText().isEqualToIgnoringWhitespace("Request Type: " + requestTypeAndAssetSummary.getRequestType().getRealValue());
            if(requestTypeAndAssetSummary.getBaseCurrency() != null)
                assertThat("letterSummary.baseccy.req").innerText().isEqualToIgnoringWhitespace("Base Currency: " + requestTypeAndAssetSummary.getBaseCurrency().getRealValue());
        }
    }

    private void assertTimingCommentsAndAttachment(MarginLetter marginLetter) throws Exception{
        if(marginLetter.getLetterSetupDetails() != null){
            LetterSetupDetails letterSetupDetails = marginLetter.getLetterSetupDetails();
            if(letterSetupDetails.getResolutionTime() != null && !letterSetupDetails.getResolutionTime().isEmpty()){
                for(ResolutionTime resolutionTime : letterSetupDetails.getResolutionTime()){
                    if(resolutionTime.getTime() != null && resolutionTime.getTimeZone() != null && resolutionTime.getDay() != null) {
                        String timeVal = resolutionTime.getTime().getRealValue() + " " + resolutionTime.getTimeZone().getRealValue() + " " + resolutionTime.getDay().getRealValue();
                        if (resolutionTime.getType() == null) {
                            assertThat("letterSummary.resol.time").innerText().isEqualToIgnoringWhitespace("Resolution Time: " + timeVal);
                        } else {
                            if(resolutionTime.getType().getRealValue().equalsIgnoreCase("VM")){
                                assertThat("letterSummary.resol.time.vm").innerText().isEqualToIgnoringWhitespace("VM Resolution Time: " + timeVal);
                            }else if(resolutionTime.getType().getRealValue().equalsIgnoreCase("IM")){
                                assertThat("letterSummary.resol.time.im").innerText().isEqualToIgnoringWhitespace("IM Resolution Time: " + timeVal);
                            }
                        }
                    }
                }
            }
            if(letterSetupDetails.getAdditionalInfo() != null){
                AdditionalInfo additionalInfo = letterSetupDetails.getAdditionalInfo();
                if(additionalInfo.getComments() != null)
                    assertThat("letterSummary.additional.comments").innerText().isEqualToIgnoringWhitespace("Additional Comments: " + additionalInfo.getComments().getRealValue());
                if(additionalInfo.getLetterName() != null)
                    assertThat("letterSummary.letter.name").innerText().isEqualToIgnoringWhitespace("Letter Name: " + additionalInfo.getLetterName().getRealValue());
            }
            if(letterSetupDetails.getEmailAttachments() != null){
                EmailAttachments emailAttachments = letterSetupDetails.getEmailAttachments();
                if(emailAttachments.getAttachmentsAdded() != null && !emailAttachments.getAttachmentsAdded().isEmpty()){
                    StringBuffer attachments = new StringBuffer();
                    for(AttachmentAdded attachmentAdded : emailAttachments.getAttachmentsAdded()){
                        if(attachmentAdded.getFileName() != null) {
//                            attachments.append(attachmentAdded.getFileName().getRealValue() + ",");
                            attachments.append(attachmentAdded.getFileName().getRealValue()).append(",");
                        }
                    }
                    assertThat("letterSummary.email.attachments").innerText().isEqualToIgnoringWhitespace("Email Attachments: " + attachments.deleteCharAt(attachments.length() - 1).toString());
                }
            }
        }
    }

    private void assertTimingCommentsAndAttachment(TimingCommentsAndAttachment timingCommentsAndAttachment) throws Exception{
        if(timingCommentsAndAttachment != null){
            if(timingCommentsAndAttachment.getResolutionTime() != null)
                assertThat("letterSummary.resol.time").innerText().isEqualToIgnoringWhitespace("Resolution Time: " + timingCommentsAndAttachment.getResolutionTime().getRealValue());
            if(timingCommentsAndAttachment.getVmResolutionTime() != null)
                assertThat("letterSummary.resol.time.vm").innerText().isEqualToIgnoringWhitespace("VM Resolution Time: " + timingCommentsAndAttachment.getVmResolutionTime().getRealValue());
            if(timingCommentsAndAttachment.getImResolutionTime() != null)
                assertThat("letterSummary.resol.time.im").innerText().isEqualToIgnoringWhitespace("IM Resolution Time: " + timingCommentsAndAttachment.getImResolutionTime().getRealValue());
            if(timingCommentsAndAttachment.getAdditionalComments() != null)
                assertThat("letterSummary.additional.comments").innerText().isEqualToIgnoringWhitespace("Additional Comments: " + timingCommentsAndAttachment.getAdditionalComments().getRealValue());
            if(timingCommentsAndAttachment.getLetterName() != null)
                assertThat("letterSummary.letter.name").innerText().isEqualToIgnoringWhitespace("Letter Name: " + timingCommentsAndAttachment.getLetterName().getRealValue());
            if(timingCommentsAndAttachment.getEmailAttachments() != null)
                assertThat("letterSummary.email.attachments").innerText().isEqualToIgnoringWhitespace("Email Attachments: " + timingCommentsAndAttachment.getEmailAttachments().getRealValue());
        }
    }

    private void assertActionAndExposure(MarginLetter marginLetter) throws Exception{
        if(marginLetter.getRequestTypeAndAssetSelection() != null
                && marginLetter.getRequestTypeAndAssetSelection().getRequestType() != null)
            assertThat("letterSummary.action.required").innerText().isEqualToIgnoringWhitespace("Action Required: " + marginLetter.getRequestTypeAndAssetSelection().getRequestType().getRealValue());
        if(marginLetter.getLetterSetupDetails() != null){
            LetterSetupDetails letterSetupDetails = marginLetter.getLetterSetupDetails();
            if(letterSetupDetails.getExposureDetails() != null) {
                ExposureDetails exposureDetails = letterSetupDetails.getExposureDetails();
                if (exposureDetails.getBaseCurrency() != null)
                    assertThat("letterSummary.baseccy.act").innerText().isEqualToIgnoringWhitespace("Base Currency: " + exposureDetails.getBaseCurrency().getRealValue());
                if (exposureDetails.getTotalExposure() != null)
                    assertThat("letterSummary.tol.expo").innerText().isEqualToIgnoringWhitespace("Total Exposure Amount: " + exposureDetails.getTotalExposure().getRealValue());
                if (exposureDetails.getMinTransferAmountPrincipal() != null)
                    assertThat("letterSummary.mintransamt").innerText().contains(" " + exposureDetails.getMinTransferAmountPrincipal().getRealValue() + "(Principal) ");
                if (exposureDetails.getMinTransferAmountCounterparty() != null)
                    assertThat("letterSummary.mintransamt").innerText().endsWith(" " + exposureDetails.getMinTransferAmountCounterparty().getRealValue() + "(Counterparty)");
                if (exposureDetails.getRoundingAmountPrincipal() != null)
                    assertThat("letterSummary.roundamt").innerText().contains(" " + exposureDetails.getRoundingAmountPrincipal().getRealValue() + "(Principal) ");
                if (exposureDetails.getRoundingAmountCounterparty() != null)
                    assertThat("letterSummary.roundamt").innerText().endsWith(" " + exposureDetails.getRoundingAmountCounterparty().getRealValue() + "(Counterparty)");
                if (exposureDetails.getDisplayExposureDetailsInLetter() != null){
                    DisplayExposureDetailsInLetter displayExposureDetailsInLetter = exposureDetails.getDisplayExposureDetailsInLetter();
                    if (displayExposureDetailsInLetter.isCheckToDisplay() != null && displayExposureDetailsInLetter.isCheckToDisplay()){
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
                        if(displayExposureDetailsInLetter.isCheckToDisplay() != null && displayExposureDetailsInLetter.isCheckToDisplay()){
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
            }
            if(letterSetupDetails.getMarginCall() != null && letterSetupDetails.getMarginCall().getAgreementRequirementSTDTolerance() != null)
                assertThat("letterSummary.agrreqamt.stdtoler").innerText().isEqualToIgnoringWhitespace("Split The Difference (STD) Type:Agreement Requirement Amount: " + letterSetupDetails.getMarginCall().getAgreementRequirementSTDTolerance().getRealValue());
        }
    }

    private void assertActionAndExposure(ActionAndExposure actionAndExposure) throws Exception{
        if(actionAndExposure != null){
            if(actionAndExposure.getActionRequired() != null)
                assertThat("letterSummary.action.required").innerText().isEqualToIgnoringWhitespace("Action Required: " + actionAndExposure.getActionRequired().getRealValue());
            if(actionAndExposure.getBaseCurrency() != null)
                assertThat("letterSummary.baseccy.act").innerText().isEqualToIgnoringWhitespace("Base Currency: " + actionAndExposure.getBaseCurrency().getRealValue());
            if(actionAndExposure.getTotalExposure() != null)
                assertThat("letterSummary.tol.expo").innerText().isEqualToIgnoringWhitespace("Total Exposure Amount: " + actionAndExposure.getTotalExposure().getRealValue());
            if(actionAndExposure.getMinTransferAmountPrincipal() != null)
                assertThat("letterSummary.mintransamt").innerText().contains(" " + actionAndExposure.getMinTransferAmountPrincipal().getRealValue() + "(Principal) ");
            if(actionAndExposure.getMinTransferAmountCounterparty() != null)
                assertThat("letterSummary.mintransamt").innerText().contains(" " + actionAndExposure.getMinTransferAmountCounterparty().getRealValue() + "(Counterparty) ");
            if(actionAndExposure.getRoundingAmountPrincipal() != null)
                assertThat("letterSummary.roundamt").innerText().contains(" " + actionAndExposure.getRoundingAmountPrincipal().getRealValue() + "(Principal) ");
            if(actionAndExposure.getRoundingAmountCounterparty() != null)
                assertThat("letterSummary.roundamt").innerText().contains(" " + actionAndExposure.getRoundingAmountCounterparty().getRealValue() + "(Counterparty) ");
            if(actionAndExposure.getAgreementRequirementSTDTolerance() != null)
                assertThat("letterSummary.agrreqamt.stdtoler").innerText().isEqualToIgnoringWhitespace("Split The Difference (STD) Type:Agreement Requirement Amount: " + actionAndExposure.getAgreementRequirementSTDTolerance().getRealValue());
            if(actionAndExposure.getLeftPrincipalIA() != null)
                assertThat("ls.expodetail.prinia.lt").innerText().isEqualToIgnoringWhitespace(actionAndExposure.getLeftPrincipalIA().getRealValue());
            if(actionAndExposure.getRightPrincipalIA() != null)
                assertThat("ls.expodetail.prinia.rt").innerText().isEqualToIgnoringWhitespace(actionAndExposure.getRightPrincipalIA().getRealValue());
            if(actionAndExposure.getLeftCounterpartyIA() != null)
                assertThat("ls.expodetail.cptyia.lt").innerText().isEqualToIgnoringWhitespace(actionAndExposure.getLeftCounterpartyIA().getRealValue());
            if(actionAndExposure.getRightCounterpartyIA() != null)
                assertThat("ls.expodetail.cptyia.rt").innerText().isEqualToIgnoringWhitespace(actionAndExposure.getRightCounterpartyIA().getRealValue());
            if(actionAndExposure.getLeftPrincipalThreshold() != null)
                assertThat("ls.expodetail.printhre.lt").innerText().isEqualToIgnoringWhitespace(actionAndExposure.getLeftPrincipalThreshold().getRealValue());
            if(actionAndExposure.getRightPrincipalThreshold() != null)
                assertThat("ls.expodetail.printhre.rt").innerText().isEqualToIgnoringWhitespace(actionAndExposure.getRightPrincipalThreshold().getRealValue());
            if(actionAndExposure.getLeftCounterpartyThreshold() != null)
                assertThat("ls.expodetail.cptythre.lt").innerText().isEqualToIgnoringWhitespace(actionAndExposure.getLeftCounterpartyThreshold().getRealValue());
            if(actionAndExposure.getRightCounterpartyThreshold() != null)
                assertThat("ls.expodetail.cptythre.rt").innerText().isEqualToIgnoringWhitespace(actionAndExposure.getRightCounterpartyThreshold().getRealValue());
            if(actionAndExposure.getLeftAdjustedExposureAmount() != null)
                assertThat("ls.expodetail.adjexpoamt.lt").innerText().isEqualToIgnoringWhitespace(actionAndExposure.getLeftAdjustedExposureAmount().getRealValue());
            if(actionAndExposure.getRightAdjustedExposureAmount() != null)
                assertThat("ls.expodetail.adjexpoamt.rt").innerText().isEqualToIgnoringWhitespace(actionAndExposure.getRightAdjustedExposureAmount().getRealValue());
            if(actionAndExposure.getLeftValueOfConfirmedCollateralHeld() != null)
                assertThat("ls.expodetail.valconcolhel.lt").innerText().isEqualToIgnoringWhitespace(actionAndExposure.getLeftValueOfConfirmedCollateralHeld().getRealValue());
            if(actionAndExposure.getRightValueOfConfirmedCollateralHeld() != null)
                assertThat("ls.expodetail.valconcolhel.rt").innerText().isEqualToIgnoringWhitespace(actionAndExposure.getRightValueOfConfirmedCollateralHeld().getRealValue());
            if(actionAndExposure.getLeftValueOfPendingCollateralHeld() != null)
                assertThat("ls.expodetail.valpendcolhel.lt").innerText().isEqualToIgnoringWhitespace(actionAndExposure.getLeftValueOfPendingCollateralHeld().getRealValue());
            if(actionAndExposure.getRightValueOfPendingCollateralHeld() != null)
                assertThat("ls.expodetail.valpendcolhel.rt").innerText().isEqualToIgnoringWhitespace(actionAndExposure.getRightValueOfPendingCollateralHeld().getRealValue());
            if(actionAndExposure.getLeftValueOfConfirmedCollateralDelivered() != null)
                assertThat("ls.expodetail.valconcoldeli.lt").innerText().isEqualToIgnoringWhitespace(actionAndExposure.getLeftValueOfConfirmedCollateralDelivered().getRealValue());
            if(actionAndExposure.getRightValueOfConfirmedCollateralDelivered() != null)
                assertThat("ls.expodetail.valconcoldeli.rt").innerText().isEqualToIgnoringWhitespace(actionAndExposure.getRightValueOfConfirmedCollateralDelivered().getRealValue());
            if(actionAndExposure.getLeftValueOfPendingCollateralDelivered() != null)
                assertThat("ls.expodetail.valpendcoldeli.lt").innerText().isEqualToIgnoringWhitespace(actionAndExposure.getLeftValueOfPendingCollateralDelivered().getRealValue());
            if(actionAndExposure.getRightValueOfPendingCollateralDelivered() != null)
                assertThat("ls.expodetail.valpendcoldeli.rt").innerText().isEqualToIgnoringWhitespace(actionAndExposure.getRightValueOfPendingCollateralDelivered().getRealValue());
            if(actionAndExposure.getLeftNetMarginRequirement() != null)
                assertThat("ls.expodetail.netmarreq.lt").innerText().isEqualToIgnoringWhitespace(actionAndExposure.getLeftNetMarginRequirement().getRealValue());
            if(actionAndExposure.getRightNetMarginRequirement() != null)
                assertThat("ls.expodetail.netmarreq.rt").innerText().isEqualToIgnoringWhitespace(actionAndExposure.getRightNetMarginRequirement().getRealValue());
            if(actionAndExposure.getLeftDeliveryAmount() != null)
                assertThat("ls.expodetail.deliamt.lt").innerText().isEqualToIgnoringWhitespace(actionAndExposure.getLeftDeliveryAmount().getRealValue());
            if(actionAndExposure.getRightDeliveryAmount() != null)
                assertThat("ls.expodetail.deliamt.rt").innerText().isEqualToIgnoringWhitespace(actionAndExposure.getRightDeliveryAmount().getRealValue());
            if(actionAndExposure.getLeftRecallAmount() != null)
                assertThat("ls.expodetail.recallamt.lt").innerText().isEqualToIgnoringWhitespace(actionAndExposure.getLeftRecallAmount().getRealValue());
            if(actionAndExposure.getRightRecallAmount() != null)
                assertThat("ls.expodetail.recallamt.rt").innerText().isEqualToIgnoringWhitespace(actionAndExposure.getRightRecallAmount().getRealValue());
            if(actionAndExposure.getLeftIADeliveryAmount() != null)
                assertThat("ls.expodetail.iadeliamt.lt").innerText().isEqualToIgnoringWhitespace(actionAndExposure.getLeftIADeliveryAmount().getRealValue());
            if(actionAndExposure.getRightIADeliveryAmount() != null)
                assertThat("ls.expodetail.iadeliamt.rt").innerText().isEqualToIgnoringWhitespace(actionAndExposure.getRightIADeliveryAmount().getRealValue());
            if(actionAndExposure.getLeftIARecallAmount() != null)
                assertThat("ls.expodetail.iarecallamt.lt").innerText().isEqualToIgnoringWhitespace(actionAndExposure.getLeftIARecallAmount().getRealValue());
            if(actionAndExposure.getRightIARecallAmount() != null)
                assertThat("ls.expodetail.iarecallamt.rt").innerText().isEqualToIgnoringWhitespace(actionAndExposure.getRightIARecallAmount().getRealValue());
            if(actionAndExposure.getLeftLockupMarginRequired() != null)
                assertThat("ls.expodetail.lkupmargreq.lt").innerText().isEqualToIgnoringWhitespace(actionAndExposure.getLeftLockupMarginRequired().getRealValue());
            if(actionAndExposure.getRightLockupMarginRequired() != null)
                assertThat("ls.expodetail.lkupmargreq.rt").innerText().isEqualToIgnoringWhitespace(actionAndExposure.getRightLockupMarginRequired().getRealValue());
            if(actionAndExposure.getLeftCurrentValueOfLockupMargin() != null)
                assertThat("ls.expodetail.cutvaloflkupmarg.lt").innerText().isEqualToIgnoringWhitespace(actionAndExposure.getLeftCurrentValueOfLockupMargin().getRealValue());
            if(actionAndExposure.getRightCurrentValueOfLockupMargin() != null)
                assertThat("ls.expodetail.cutvaloflkupmarg.rt").innerText().isEqualToIgnoringWhitespace(actionAndExposure.getRightCurrentValueOfLockupMargin().getRealValue());
            if(actionAndExposure.getLeftLockupMarginCall() != null)
                assertThat("ls.expodetail.lkupmargcall.lt").innerText().isEqualToIgnoringWhitespace(actionAndExposure.getLeftLockupMarginCall().getRealValue());
            if(actionAndExposure.getRightLockupMarginCall() != null)
                assertThat("ls.expodetail.lkupmargcall.rt").innerText().isEqualToIgnoringWhitespace(actionAndExposure.getRightLockupMarginCall().getRealValue());
        }
    }

    private void assertLetterAndPartyInformation(MarginLetter marginLetter) throws Exception{
        if(marginLetter.getLetterSetupDetails() != null){
            LetterSetupDetails letterSetupDetails = marginLetter.getLetterSetupDetails();
            BranchInformation branchInformation;
            if(letterSetupDetails.getPrincipalDetails() != null){
                branchInformation = letterSetupDetails.getPrincipalDetails();
                if(branchInformation.getShortName() != null)
                    assertThat("letterSummary.prin.name").innerText().isEqualToIgnoringWhitespace("Principal Name: " + branchInformation.getShortName().getRealValue());
                if(branchInformation.getContactName() != null)
                    assertThat("letterSummary.prin.contname").innerText().isEqualToIgnoringWhitespace("Principal Contact Name: " + branchInformation.getContactName().getRealValue());
                if(branchInformation.getContactInfo() != null)
                    assertThat("letterSummary.prin.address").innerText().isEqualToIgnoringWhitespace("Principal Contact Address: " + branchInformation.getContactInfo().getRealValue());
                if(branchInformation.getContactEmail() != null)
                    assertThat("letterSummary.prin.contemail").innerText().isEqualToIgnoringWhitespace("Principal Contact Email: " + branchInformation.getContactEmail().getRealValue());
                if(branchInformation.getAdditionalSignatory() != null)
                    assertThat("letterSummary.prin.additional.signatory").innerText().isEqualToIgnoringWhitespace("Principal Additional Signatory: " + branchInformation.getAdditionalSignatory().getRealValue());
                if(branchInformation.getCcEmailContact() != null)
                    assertThat("letterSummary.prin.ccemail.cont").innerText().isEqualToIgnoringWhitespace("Principal CC E-mail - " + branchInformation.getCcEmailContact().getRealValue());
            }
            if(letterSetupDetails.getCounterpartyDetails() != null){
                branchInformation = letterSetupDetails.getCounterpartyDetails();
                if(branchInformation.getShortName() != null)
                    assertThat("letterSummary.cpty.name").innerText().isEqualToIgnoringWhitespace("Counterparty Name: " + branchInformation.getShortName().getRealValue());
                if(branchInformation.getContactName() != null)
                    assertThat("letterSummary.cpty.contname").innerText().isEqualToIgnoringWhitespace("Counterparty Contact Name: " + branchInformation.getContactName().getRealValue());
                if(branchInformation.getContactInfo() != null)
                    assertThat("letterSummary.cpty.address").innerText().isEqualToIgnoringWhitespace("Counterparty Contact Address: " + branchInformation.getContactInfo().getRealValue());
                if(branchInformation.getContactEmail() != null)
                    assertThat("letterSummary.cpty.contemail").innerText().isEqualToIgnoringWhitespace("Counterparty Contact Email: " + branchInformation.getContactEmail().getRealValue());
                if(branchInformation.getCcEmailContact() != null)
                    assertThat("letterSummary.cpty.ccemail.cont").innerText().isEqualToIgnoringWhitespace("Counterparty CC Email - " + branchInformation.getCcEmailContact().getRealValue());
            }
            if(letterSetupDetails.getAgreementDescription() != null)
                assertThat("letterSummary.agr.desc").innerText().isEqualToIgnoringWhitespace("Agreement Description: " + letterSetupDetails.getAgreementDescription().getRealValue());
        }
    }

    private void assertLetterAndPartyInformation(LetterAndPartyInformation letterAndPartyInformation) throws Exception{
        if(letterAndPartyInformation != null){
            BranchInformation branchInformation;
            if(letterAndPartyInformation.getPrincipalInformation() != null){
                branchInformation = letterAndPartyInformation.getPrincipalInformation();
                if(branchInformation.getShortName() != null)
                    assertThat("letterSummary.prin.name").innerText().isEqualToIgnoringWhitespace("Principal Name: " + branchInformation.getShortName().getRealValue());
                if(branchInformation.getContactName() != null)
                    assertThat("letterSummary.prin.contname").innerText().isEqualToIgnoringWhitespace("Principal Contact Name: " + branchInformation.getContactName().getRealValue());
                if(branchInformation.getContactInfo() != null)
                    assertThat("letterSummary.prin.address").innerText().isEqualToIgnoringWhitespace("Principal Contact Address: " + branchInformation.getContactInfo().getRealValue());
                if(branchInformation.getContactEmail() != null)
                    assertThat("letterSummary.prin.contemail").innerText().isEqualToIgnoringWhitespace("Principal Contact Email: " + branchInformation.getContactEmail().getRealValue());
                if(branchInformation.getAdditionalSignatory() != null)
                    assertThat("letterSummary.prin.additional.signatory").innerText().isEqualToIgnoringWhitespace("Principal Additional Signatory: " + branchInformation.getAdditionalSignatory().getRealValue());
                if(branchInformation.getCcEmailContact() != null)
                    assertThat("letterSummary.prin.ccemail.cont").innerText().isEqualToIgnoringWhitespace("Principal CC E-mail - " + branchInformation.getCcEmailContact().getRealValue());
            }
            if(letterAndPartyInformation.getCounterpartyInformation() != null){
                branchInformation = letterAndPartyInformation.getCounterpartyInformation();
                if(branchInformation.getShortName() != null)
                    assertThat("letterSummary.cpty.name").innerText().isEqualToIgnoringWhitespace("Counterparty Name: " + branchInformation.getShortName().getRealValue());
                if(branchInformation.getContactName() != null)
                    assertThat("letterSummary.cpty.contname").innerText().isEqualToIgnoringWhitespace("Counterparty Contact Name: " + branchInformation.getContactName().getRealValue());
                if(branchInformation.getContactInfo() != null)
                    assertThat("letterSummary.cpty.address").innerText().isEqualToIgnoringWhitespace("Counterparty Contact Address: " + branchInformation.getContactInfo().getRealValue());
                if(branchInformation.getContactEmail() != null)
                    assertThat("letterSummary.cpty.contemail").innerText().isEqualToIgnoringWhitespace("Counterparty Contact Email: " + branchInformation.getContactEmail().getRealValue());
                if(branchInformation.getCcEmailContact() != null)
                    assertThat("letterSummary.cpty.ccemail.cont").innerText().isEqualToIgnoringWhitespace("Counterparty CC E-mail - " + branchInformation.getCcEmailContact().getRealValue());
            }
            if(letterAndPartyInformation.getAgreementDescription() != null)
                assertThat("letterSummary.agr.desc").innerText().isEqualToIgnoringWhitespace("Agreement Description: " + letterAndPartyInformation.getAgreementDescription().getRealValue());
        }
    }
}
