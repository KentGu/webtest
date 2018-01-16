package com.lombardrisk.pages.collateral;


import com.lombardrisk.pojo.Agreement;
import com.lombardrisk.pojo.StringType;
import com.lombardrisk.util.PageHelper;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;


/**
 * Created by Kenny Wang on 2/3/2016.
 */
public class AgreementDocumentationSetupPage extends PageBase {
    public AgreementDocumentationSetupPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void setupAgreementDocumentation(Agreement agmt) throws Exception {
        
    	setupPartyAndBranchInformation(agmt);
    	setupDocumentation(agmt);
        setupCalculationRule(agmt);
    	setupRehypothecationRights(agmt);
        setupLettersAndCustodianInformation(agmt);
        enterNextStage();
    }

    public void setupAgreementDocumentationAndSave(Agreement agmt) throws Exception {

        setupPartyAndBranchInformation(agmt);
        setupDocumentation(agmt);
        setupCalculationRule(agmt);
        setupRehypothecationRights(agmt);
        setupLettersAndCustodianInformation(agmt);
        saveAndExit();
    }

    public void saveAndExit() throws Exception {
        element("ap.save.and.exit").click();
        waitThat().document().toBeReady();
        waitThat().jQuery().toBeInactive();
    }
    
    public void setupPartyAndBranchInformation(Agreement agmt) throws Exception{
    	//setup pricipal party branch information -yan lu
        if (agmt.getPrincipalInformation() != null) {
            if (agmt.getPrincipalInformation().getPrimaryContact() != null) {
                element("ap.pricipal.primary.contact").selectByVisibleText(agmt.getPrincipalInformation().getPrimaryContact().getRealValue());
            }
            if (agmt.getPrincipalInformation().getInterestContact() != null) {
                element("ap.pricipal.interest.contact").selectByVisibleText(agmt.getPrincipalInformation().getInterestContact().getRealValue());
            }
            if (agmt.getPrincipalInformation().getCcEmailContact() != null) {
                element("ap.pricipal.cc.email").deselectAll();
                for (int i = 0; i < agmt.getPrincipalInformation().getCcEmailContact().size(); i++) {
                    element("ap.pricipal.cc.email").selectByVisibleText(agmt.getPrincipalInformation().getCcEmailContact().get(i).getRealValue());
                }
            }
            if (agmt.getPrincipalInformation().getInterestCcEmailContact() != null) {
                element("ap.pricipal.interest.cc.email").deselectAll();
                for (int i = 0; i < agmt.getPrincipalInformation().getInterestCcEmailContact().size(); i++) {
                    element("ap.pricipal.interest.cc.email").selectByVisibleText(agmt.getPrincipalInformation().getInterestCcEmailContact().get(i).getRealValue());
                }
            }
            if (agmt.getPrincipalInformation().isAllPartyBranches() != null) {
                element("ap.all.pricipal.branches").check(agmt.getPrincipalInformation().isAllPartyBranches());
            }
            if (agmt.getPrincipalInformation().getPartyBranch()!=null && agmt.getPrincipalInformation().getPartyBranch().size()>0){
                for (StringType brach : agmt.getPrincipalInformation().getPartyBranch())
                    element("ap.pricipal.branches",brach.getRealValue()).check(true);
            }
            if (agmt.getPrincipalInformation().getPartyNav() != null) {
                element("ap.pricipal.nav").input(agmt.getPrincipalInformation().getPartyNav().getRealValue());
            }
            if (agmt.getPrincipalInformation().getPartyNavCurrency() != null) {
                element("ap.pricipal.nav.currency").selectByVisibleText(agmt.getPrincipalInformation().getPartyNavCurrency().getRealValue());
            }
            if (agmt.getPrincipalInformation().isValuationAgent() != null) {
                element("ap.pricipal.valuation.agent").check(agmt.getPrincipalInformation().isValuationAgent());
            }
        }


        //setup counterparty party branch information
        if (agmt.getCounterpartyInformation() != null) {
            if (agmt.getCounterpartyInformation().getPrimaryContact() != null) {
                element("ap.counterparty.primary.contact").selectByVisibleText(agmt.getCounterpartyInformation().getPrimaryContact().getRealValue());
            }
            if (agmt.getCounterpartyInformation().getInterestContact() != null) {
                element("ap.counterparty.interest.contact").selectByVisibleText(agmt.getCounterpartyInformation().getInterestContact().getRealValue());
            }
            if (agmt.getCounterpartyInformation().getCcEmailContact() != null) {
                element("ap.counterparty.cc.email").deselectAll();
                for (int i = 0; i < agmt.getCounterpartyInformation().getCcEmailContact().size(); i++) {
                    element("ap.counterparty.cc.email").selectByVisibleText(agmt.getCounterpartyInformation().getCcEmailContact().get(i).getRealValue());
                }
            }
            if (agmt.getCounterpartyInformation().getInterestCcEmailContact() != null) {
                element("ap.counterparty.interest.cc.email").deselectAll();
                for (int i = 0; i < agmt.getCounterpartyInformation().getInterestCcEmailContact().size(); i++) {
                    element("ap.counterparty.interest.cc.email").selectByVisibleText(agmt.getCounterpartyInformation().getInterestCcEmailContact().get(i).getRealValue());
                }
            }
            if (agmt.getCounterpartyInformation().isAllPartyBranches() != null) {
                element("ap.all.counterparty.branches").check(agmt.getCounterpartyInformation().isAllPartyBranches());

            }
            if (agmt.getCounterpartyInformation().getPartyBranch()!=null && agmt.getCounterpartyInformation().getPartyBranch().size()>0){
                for (StringType brach : agmt.getCounterpartyInformation().getPartyBranch())
                    element("ap.counterparty.branches",brach.getRealValue()).check(true);
            }
            if (agmt.getCounterpartyInformation().getPartyNav() != null) {
                element("ap.counterparty.nav").input(agmt.getCounterpartyInformation().getPartyNav().getRealValue());
            }
            if (agmt.getCounterpartyInformation().getPartyNavCurrency() != null) {
                element("ap.counterparty.nav.currency").selectByVisibleText(agmt.getCounterpartyInformation().getPartyNavCurrency().getRealValue());
            }
            if (agmt.getCounterpartyInformation().isValuationAgent() != null) {
                element("ap.counterparty.valuation.agent").check(agmt.getCounterpartyInformation().isValuationAgent());
            }
        }
    }
    
    
    public void setupDocumentation(Agreement agmt) throws Exception{
    	 //setup documentation
        if (agmt.getMasterDocumentation() != null) {
            element("ap.master.documentation").selectByVisibleText(agmt.getMasterDocumentation());
        }
        if (agmt.getCreationDate() != null) {
            element("ap.creation.date").input(agmt.getCreationDate().getRealValue());
        }
        if (agmt.getCreditSupportDocumentation() != null) {
            element("ap.credit.support.documentation").selectByVisibleText(agmt.getCreditSupportDocumentation());
        }
        if (agmt.getSignoffDate() != null) {
            element("ap.signoff.date").input(agmt.getSignoffDate().getRealValue());
        }
//        if (agmt.getReciprocity() != null) {
//            element("reciprocity").selectByVisibleText(agmt.getReciprocity().value());
//        }

        if (agmt.getSignoffBy() != null) {
            element("ap.signoff.by").input(agmt.getSignoffBy().getRealValue());
        }
        if (agmt.getAnnex() != null ) {
        	if (agmt.getAnnex().size() > 0){
        		for (StringType annex : agmt.getAnnex())
                    element("ap.annex").selectByVisibleText(annex.getRealValue());
        	}
        }
        if (agmt.getAgreementDate() != null)
            element("ap.date").input(agmt.getAgreementDate().getRealValue());
        if (agmt.getBaseCurrency() != null) {
            element("ap.base.currency").selectByVisibleText(agmt.getBaseCurrency().getRealValue());
        }
        if (agmt.getType() != null) {
            element("ap.type").selectByVisibleText(agmt.getType().getRealValue());
        }
        if (agmt.getReviewDate() != null) {
            element("ap.review.date").input(agmt.getReviewDate().getRealValue());
        }
    }
    
    public void setupRehypothecationRights(Agreement agmt) throws Exception{
    	//if agreement is net tick rehypothecation rights
    	if (agmt.isRehypothecationRights() != null){
            element("ap.rehypothecation.rights").check(agmt.isRehypothecationRights());
    	}
    	
    	//tick vm rehypothecation rights
        if (agmt.isVmRehypothecationRights() != null) {
            element("ap.vm.rehypothecation.rights").check(agmt.isVmRehypothecationRights());
        }
        //tick im rehypothecation rights
        if (agmt.isImRehypothecationRights() != null) {
            element("ap.im.rehypothecation.rights").check(agmt.isImRehypothecationRights());
        }
        if (agmt.getVmRehypothecation() != null) {
            element("ap.vm.rehypothecation.type").selectByVisibleText(agmt.getVmRehypothecation().value());
        }
        if (agmt.getImRehypothecation() != null) {
            element("ap.im.rehypothecation.type").selectByVisibleText(agmt.getImRehypothecation().value());
        }
        if (agmt.getCreditContingentReuseRights() != null) {
            element("ap.credit.contingent.reuse.rights").input(agmt.getCreditContingentReuseRights().getRealValue());
        }
        if (agmt.getSignoffNotes() != null) {
            element("ap.signoff.notes").input(agmt.getSignoffNotes().getRealValue());
        }
    }
    
    public void setupCalculationRule(Agreement agmt) throws Exception{
    	 //setup calculation rule
        if (agmt.getNettingIAandMtM() != null)
            element("ap.net").selectByVisibleText(agmt.getNettingIAandMtM().value());

        if (agmt.isSegregation() != null) {
            element("ap.segregation").check(agmt.isSegregation());
        }
        if (agmt.isFlush() != null){
            element("ap.flush").check(agmt.isFlush());
        }
        if (agmt.isAllowAdj() != null){
			element("ap.allowAdj").check(agmt.isAllowAdj());
        }
        if (agmt.isTsa() != null){
            element("ap.tsa").check(agmt.isTsa());
        }else if (agmt.isCashflow() != null && agmt.isCashflow()){
            element("ap.tsa").check(agmt.isCashflow());
        }

        if (agmt.getTsaRule() != null){
            element("ap.tsa.rule").selectByVisibleText(agmt.getTsaRule().getRealValue());
        }else if (agmt.getCashflowRule() != null){
            element("ap.tsa.rule").selectByVisibleText(agmt.getCashflowRule().getRealValue());
        }

        if (agmt.getValuationDate() != null){
            element("ap.valuation.date").selectByVisibleText(agmt.getValuationDate().getRealValue());
        }
        if (agmt.getDefaultFund()!=null){
            element("ap.default.fund").selectByVisibleText(agmt.getDefaultFund());
        }
            
        if (agmt.getFlushType() != null)
            element("ap.flush.type").selectByVisibleText(agmt.getFlushType().value());
        
        if (agmt.getPrincipalSeg() != null) {
            element("ap.principal.seg").selectByValue(agmt.getPrincipalSeg().value());
        }
        if (agmt.getCounterpartySeg() != null) {
            element("ap.counterparty.seg").selectByValue(agmt.getCounterpartySeg().value());
        }
        if (agmt.getPrincipalVMSeg() != null) {
            element("ap.principal.vm.seg").selectByVisibleText(agmt.getPrincipalVMSeg().value());
        }
        if (agmt.getCounterpartyVMSeg() != null) {
            element("ap.counterparty.vm.seg").selectByVisibleText(agmt.getCounterpartyVMSeg().value());
        }
        if (agmt.getPrincipalIMSeg() != null) {
            element("ap.principal.im.seg").selectByVisibleText(agmt.getPrincipalIMSeg().value());
        }
        if (agmt.getCounterpartyIMSeg() != null) {
            element("ap.counterparty.im.seg").selectByVisibleText(agmt.getCounterpartyIMSeg().value());
        }
        if (agmt.getGrossCalc() != null) {
            element("ap.gross.calc").selectByVisibleText(agmt.getGrossCalc().value());
        }
        if (agmt.getVmGrossCalc() != null) {
            element("ap.vmgross.calc").selectByVisibleText(agmt.getVmGrossCalc().value());
        }
        if (agmt.getImGrossCalc() != null) {
            element("ap.imgross.calc").selectByVisibleText(agmt.getImGrossCalc().value());
        }
        if (agmt.isNetVMIMEventsInSameDirection() != null) {
            element("ap.net.events").check(agmt.isNetVMIMEventsInSameDirection());
        }
        if (agmt.isNetVMIMInterest() != null) {
            element("ap.net.interest").check(agmt.isNetVMIMInterest());
        }
        if (agmt.getInterestCalc() != null){
        	element("ap.interest.calc").selectByVisibleText(agmt.getInterestCalc().value());
        }
        if (agmt.getFxRate() != null){
        	element("ap.fx").selectByVisibleText(agmt.getFxRate().getRealValue());
        }
        if (agmt.getPriceSource() != null){
        	element("ap.ps").selectByVisibleText(agmt.getPriceSource().getRealValue());
        }
        if (agmt.getMarginType() != null)
            element("ap.marginType").selectByVisibleText(agmt.getMarginType());
        if (agmt.getStatementCalc() != null)
            element("ap.statementCalc").selectByVisibleText(agmt.getStatementCalc());
        if (agmt.getUserDefinedStatementCalc() != null)
            element("ap.statementCalc").selectByVisibleText(agmt.getUserDefinedStatementCalc().getRealValue());
        if (agmt.getClientType() != null)
            element("ap.clientType").selectByVisibleText(agmt.getClientType());
        if (agmt.getPrincipalTransferCurrency()!=null)
            element("ap.principal.transfer.currency").selectByVisibleText(agmt.getPrincipalTransferCurrency().getRealValue());
        if (agmt.getPrincipalTerminationCurrency()!=null)
            element("ap.principal.termination.currency").selectByVisibleText(agmt.getPrincipalTerminationCurrency().getRealValue());
        if (agmt.getCounterpartyTransferCurrency()!=null)
            element("ap.counterparty.transfer.currency").selectByVisibleText(agmt.getCounterpartyTransferCurrency().getRealValue());
        if (agmt.getCounterpartyTerminationCurrency()!=null)
            element("ap.counterparty.termination.currency").selectByVisibleText(agmt.getCounterpartyTerminationCurrency().getRealValue());
        if (agmt.getInterestLetter()!=null)
            element("ap.interest.letter").selectByVisibleText(agmt.getInterestLetter().getRealValue());
        if (agmt.getEventAggregation()!=null)
            element("ap.event.aggregation").selectByVisibleText(agmt.getEventAggregation().getRealValue());
    }
    
    public void setupLettersAndCustodianInformation(Agreement agmt) throws Exception{
        //letter and custodian information
        if (agmt.isSendConfirmationLetterToCounterparty() != null) {
            element("ap.send.letter.to.counterparty").check(agmt.isSendConfirmationLetterToCounterparty());
        }
        if (agmt.isSendConfirmationLetterToCustodian() != null) {
            element("ap.send.letter.to.custodian").check(agmt.isSendConfirmationLetterToCustodian());
        }
        
        if (agmt.getCustodianInformation() != null) {
            element("ap.custodian.primary.contact").selectByVisibleText(agmt.getCustodianInformation().getPrimaryContact().getRealValue());
        }
        if (agmt.getCustodianInformation() != null) {
            for (int i = 0; i < agmt.getCustodianInformation().getCcEmailContact().size(); i++) {
                element("ap.custodian.ccemail.contact").selectByVisibleText(agmt.getCustodianInformation().getCcEmailContact().get(i).getRealValue());
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
