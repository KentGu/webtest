package com.lombardrisk.pages.collateral;


import com.lombardrisk.pages.AbstractRuleSetupPage;
import com.lombardrisk.pojo.*;
import com.lombardrisk.util.Biz;
import com.lombardrisk.util.PageHelper;
import org.yiwan.webcore.web.IWebDriverWrapper;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Kenny Wang on 2/3/2016.
 */
public class AgreementCollateralSetupPage extends AbstractRuleSetupPage {
    public AgreementCollateralSetupPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    public void saveAndExit() throws Exception {
        element("ap.save.and.exit").click();
    }

    public void setupAgreementCollateral(Agreement agmt) throws Exception {
        setupCollateralRule(agmt);

        setCollateralOther(agmt);

        setupApplicableAgencies(agmt);

//        setupCollateralEligibleForReceipt(agmt);

//        setupCollateralAssetClass(agmt);
        setupCollateralAssetClassNew(agmt);

        waitThat("ap.tab.fixedTrigger").attributeValueOf("style").toBe("");
    }

    public void editAgreementCollateral(Agreement oriAgmt, Agreement agmt) throws Exception {
        setupCollateralRule(agmt);

        setCollateralOther(agmt);

        setupApplicableAgencies(agmt);

//        setupCollateralEligibleForReceipt(agmt);
        editAgreementAssetClassNew(oriAgmt,agmt);
//        editAgreementAssetClass(oriAgmt,agmt);

        waitThat().document().toBeReady();
        waitThat().jQuery().toBeInactive();
        waitThat("ap.tab.fixedTrigger").attributeValueOf("style").toBe("");
    }

    public void setupCollateralRule(Agreement agmt) throws Exception {
        setupCollateralRulesInterest(agmt);
        setupCollateralRulesVmImInterest(agmt);
        setupCollateralRulesDfInterest(agmt);
        setupOtherColRule(agmt);
    }
    public void setupOtherColRule(Agreement agmt) throws Exception{
        if (agmt.getApplicableBalanceInterestCalculation() != null) {
            element("ap.applicable.balance.interest.calc").selectByVisibleText(agmt.getApplicableBalanceInterestCalculation().value());
        }
        if (agmt.getInterestSettlementDate() != null) {
            element("ap.interest.settlement.date").selectByVisibleText(agmt.getInterestSettlementDate().value());
        }
        if (agmt.isAllowNegativeEffectiveInterestRate() != null) {
            if (agmt.isAllowNegativeEffectiveInterestRate()) {
                element("ap.interest.allow.negative.effective.rate").check(agmt.isAllowNegativeEffectiveInterestRate());
            }
        }
    }

    public void setupCollateralRulesInterest(Agreement agmt) throws Exception {
        //net agreement interest
        if ((agmt.getNettingIAandMtM() != null && agmt.getNettingIAandMtM().value().equals(NettingIAandMtMType.NET.value())) || agmt.getNettingIAandMtM() == null) {
            if (agmt.isInterest() != null) {
                if (agmt.isInterest()) {
                    element("ap.interest.calc.enable").check(agmt.isInterest());
                }
            }
            if (agmt.getInterestCalculationsMethod() != null) {
                element("ap.interest.calc.method").selectByVisibleText(agmt.getInterestCalculationsMethod().value());
            }
            if (agmt.getCalculationRule() != null) {
                element("ap.calc.rule").selectByVisibleText(agmt.getCalculationRule().value());
            }
            if (agmt.getPayMethod() != null) {
                element("ap.pay.method").selectByVisibleText(agmt.getPayMethod().value());
            }
            if (agmt.getReceiveMethod() != null) {
                element("ap.rec.method").selectByVisibleText(agmt.getReceiveMethod().value());
            }
            //set both net and not net parameters

        }

    }

    public void setupCollateralRulesVmImInterest(Agreement agmt) throws Exception {
        //not net vm interest
//        if (agmt.getNettingIAandMtM() != null && !agmt.getNettingIAandMtM().value().equals(NettingIAandMtMType.NET.value())) {
            if (agmt.isVmInterest() != null) {
                element("ap.interest.calc.enable").check(agmt.isVmInterest());

            }

            if (agmt.getVminterestCalculationsMethod() != null) {
                element("ap.interest.calc.method").selectByVisibleText(agmt.getVminterestCalculationsMethod().value());
            }
            if (agmt.getVmCalculationRule() != null) {
                element("ap.calc.rule").selectByVisibleText(agmt.getVmCalculationRule().value());
            }
            if (agmt.getVmPayMethod() != null) {
                element("ap.pay.method").selectByVisibleText(agmt.getVmPayMethod().value());
            }
            if (agmt.getVmReceiveMethod() != null) {
                element("ap.rec.method").selectByVisibleText(agmt.getVmReceiveMethod().value());
            }

            // im interest
            if (agmt.isImInterest() != null) {
                element("ap.interest.calc.enable.im").check(agmt.isImInterest());
            }
            if (agmt.getImInterestCalculationsMethod() != null) {
                element("ap.interest.calc.method.im").selectByVisibleText(agmt.getImInterestCalculationsMethod().value());
            }
            if (agmt.getImCalculationRule() != null) {
                element("ap.calc.rule.im").selectByVisibleText(agmt.getImCalculationRule().value());
            }
            if (agmt.getImPayMethod() != null) {
                element("ap.pay.method.im").selectByVisibleText(agmt.getImPayMethod().value());
            }
            if (agmt.getImReceiveMethod() != null) {
                element("ap.rec.method.im").selectByVisibleText(agmt.getImReceiveMethod().value());
            }
            //set both net and not net parameters

//        }
    }

    public void setupCollateralRulesDfInterest(Agreement agmt) throws Exception {
        //not net vm interest
        if ((agmt.getNettingIAandMtM() != null && !agmt.getNettingIAandMtM().value().equals(NettingIAandMtMType.NET.value())) ||
                (agmt.getBusinessLine() != null && agmt.getBusinessLine().value().equals("ETD"))) {
            if (agmt.isDfInterest() != null) {
                element("ap.interest.calc.enable.df").check(agmt.isDfInterest());

            }

            if (agmt.getDfInterestCalculationsMethod() != null) {
                element("ap.interest.calc.method.df").selectByVisibleText(agmt.getDfInterestCalculationsMethod().value());
            }
            if (agmt.getDfCalculationRule() != null) {
                element("ap.calc.rule.df").selectByVisibleText(agmt.getDfCalculationRule().value());
            }
            if (agmt.getDfPayMethod() != null) {
                element("ap.pay.method.df").selectByVisibleText(agmt.getDfPayMethod().value());
            }
            if (agmt.getDfReceiveMethod() != null) {
                element("ap.rec.method.df").selectByVisibleText(agmt.getDfReceiveMethod().value());
            }

        }
    }

    public void setCollateralOther(Agreement agmt) throws Exception {
        if (agmt.getConcentrationLimitTrigger() != null)
            element("ap.cl.rule.trigger").input(agmt.getConcentrationLimitTrigger().getRealValue());
        if (agmt.getConcentrationLimitBreachAdjustment() != null){
            if(agmt.getConcentrationLimitBreachAdjustment() == ConcentrationLimitBreachAdjustmentType.BLANK) {
                element("ap.cl.rule.adj").selectByValue("0");
            }else{
                element("ap.cl.rule.adj").selectByVisibleText(agmt.getConcentrationLimitBreachAdjustment().value());
            }
        }
        if (agmt.getRatingMethod() != null) {
            if (agmt.getRatingMethod().isUseIssueRating() != null) {
                if (agmt.getRatingMethod().isUseIssueRating()) {
                    element("ap.issue").check(agmt.getRatingMethod().isUseIssueRating());
                }
            }
            if (agmt.getRatingMethod().isUseIssuerRating() != null) {
                if (agmt.getRatingMethod().isUseIssuerRating()) {
                    element("ap.issuer").check(agmt.getRatingMethod().isUseIssuerRating());
                }
            }
        }
        if (agmt.getEligibilityRulesTemplate() != null) {
            element("ap.eligibility.rule.template").selectByVisibleText(agmt.getEligibilityRulesTemplate().getRealValue());
        }
        if (agmt.getGracePeriod()!=null){
            element("ap.grace.period").selectByVisibleText(agmt.getGracePeriod().getRealValue());
        }
//        if (agmt.isApplyOrganisationThreshold()!=null){
//            element("").check(agmt.isApplyOrganisationThreshold());
//        }

    }

    public void setupApplicableAgencies(Agreement agmt) throws Exception {
        if (agmt.isApplicableAgencies() != null) {
            element("ap.applicable.agencies").check(agmt.isApplicableAgencies());
        }

        if (agmt.getCollateralApplicableAgency() != null) {
            if (agmt.getCollateralApplicableAgency().getReferenceRatingAgencies() != null) {
                for (int i = 0; i < agmt.getCollateralApplicableAgency().getReferenceRatingAgencies().size(); i++) {
                    element("ap.reference.rating.agencies").selectByVisibleText(agmt.getCollateralApplicableAgency().getReferenceRatingAgencies().get(i).getRealValue());
                }
            }
            if (agmt.getCollateralApplicableAgency().getDebtClassification() != null) {
                element("ap.debt.classfication").selectByVisibleText(agmt.getCollateralApplicableAgency().getDebtClassification().getRealValue());
            }
            if (agmt.getCollateralApplicableAgency().getSelectionOfAgencyUseValue() != null) {
                element("ap.selection.agency.use").input(agmt.getCollateralApplicableAgency().getSelectionOfAgencyUseValue().getRealValue());
            }
            if (agmt.getCollateralApplicableAgency().getSelectionOfAgencyUseType() != null) {
                element("ap.selection.agency").selectByVisibleText(agmt.getCollateralApplicableAgency().getSelectionOfAgencyUseType().value());
            }
            if (agmt.getCollateralApplicableAgency().getApplicationOfRatings() != null) {
                element("ap.application.rating").selectByVisibleText(agmt.getCollateralApplicableAgency().getApplicationOfRatings().value());
            }
        }
    }

    public void setupCollateralEligibleForReceipt(Agreement agmt) throws Exception {
        if (agmt.getApplicableParty() != null) {
            if (agmt.getApplicableParty().equals(ApplicablePartyType.APPLICABLE_TO_BOTH_PARTIES)) {
                element("ap.applicable.party.to.both").click();
            }
            if (agmt.getApplicableParty().equals(ApplicablePartyType.APPLICABLE_TO_PRINCIPAL_ONLY)) {
                element("ap.applicable.party.to.p").click();
            }
        }
        if (agmt.getApplicableType() != null) {
            if (agmt.getApplicableType().equals(ApplicableType.VM_IM)) {
                element("ap.applicable.type.to.both").click();
            }
            if (agmt.getApplicableType().equals(ApplicableType.VM_ONLY)) {
                element("ap.applicable.type.to.vm").click();
            }
        }

    }

    public void setupCollateralAssetClass(Agreement agmt) throws Exception {
        //PCVI
        if (agmt.getCollateralAssetClass() != null && !agmt.getCollateralAssetClass().isEmpty()) {
            for (CollateralAssetClass assetClass : agmt.getCollateralAssetClass()) {
                if (assetClass.getCollateralAssetType() != null
                        && !assetClass.getCollateralAssetType().isEmpty()) {
                    for (CollateralAssetType assetType : assetClass.getCollateralAssetType()) {
                        setupAgreementAssetType(assetClass.getCollateralAssetClassName().getRealValue(), assetType);
                    }
                }
                //after setup assetType,setup asset class
                setupAgreementAssetClass(assetClass);
            }
        }

        //PC  VM IM seperated
        //PC VM
        if ((agmt.getVmCollateralAssetClass() != null && !agmt.getVmCollateralAssetClass().isEmpty())
                || (agmt.getImCollateralAssetClass() != null && !agmt.getImCollateralAssetClass().isEmpty())) {
            if (agmt.getVmCollateralAssetClass() != null
                    && !agmt.getVmCollateralAssetClass().isEmpty()) {
                for (CollateralAssetClass assetClass : agmt.getVmCollateralAssetClass()) {
                    if (assetClass.getCollateralAssetType() != null
                            && !assetClass.getCollateralAssetType().isEmpty()) {
                        for (CollateralAssetType assetType : assetClass.getCollateralAssetType()) {
                            setupAgreementAssetType(assetClass.getCollateralAssetClassName().getRealValue(), assetType);
                        }
                    }
                    //after setup assetType,setup asset class
                    setupAgreementAssetClass(assetClass);
                }
            }
            enterNextStage();
            // PC IM
            if (agmt.getImCollateralAssetClass() != null && !agmt.getImCollateralAssetClass().isEmpty()) {
                for (CollateralAssetClass assetClass : agmt.getImCollateralAssetClass()) {
                    if (assetClass.getCollateralAssetType() != null
                            && !assetClass.getCollateralAssetType().isEmpty()) {
                        for (CollateralAssetType assetType : assetClass.getCollateralAssetType()) {
                            setupAgreementAssetType(assetClass.getCollateralAssetClassName().getRealValue(), assetType);
                        }
                    }
                    //after setup assetType,setup asset class
                    setupAgreementAssetClass(assetClass);
                }
            }
        }

        // PC seperated    VMIM
        if ((agmt.getPrincipalCollateralAssetClass() != null && !agmt.getPrincipalCollateralAssetClass().isEmpty())
                || (agmt.getCounterpartyCollateralAssetClass() != null && !agmt.getCounterpartyCollateralAssetClass().isEmpty())) {
            //P VMIM
            if (agmt.getPrincipalCollateralAssetClass() != null
                    && !agmt.getPrincipalCollateralAssetClass().isEmpty()) {
                for (CollateralAssetClass assetClass : agmt.getPrincipalCollateralAssetClass()) {
                    if (assetClass.getCollateralAssetType() != null
                            && !assetClass.getCollateralAssetType().isEmpty()) {
                        for (CollateralAssetType assetType : assetClass.getCollateralAssetType()) {
                            setupAgreementAssetType(assetClass.getCollateralAssetClassName().getRealValue(), assetType);
                        }
                    }
                    //after setup assetType,setup asset class
                    setupAgreementAssetClass(assetClass);
                }
            }
            enterNextStage();
            //C VMIM
            if (agmt.getCounterpartyCollateralAssetClass() != null
                    && !agmt.getCounterpartyCollateralAssetClass().isEmpty()) {
                for (CollateralAssetClass assetClass : agmt.getCounterpartyCollateralAssetClass()) {
                    if (assetClass.getCollateralAssetType() != null && !assetClass.getCollateralAssetType().isEmpty()) {
                        for (CollateralAssetType assetType : assetClass.getCollateralAssetType()) {
                            setupAgreementAssetType(assetClass.getCollateralAssetClassName().getRealValue(), assetType);
                        }
                    }
                    //after setup assetType,setup asset class
                    setupAgreementAssetClass(assetClass);
                }
            }
        }


        //PVM PIM CVM CIM
        if ((agmt.getPrincipalVMCollateralAssetClass() != null && !agmt.getPrincipalVMCollateralAssetClass().isEmpty())
                || (agmt.getPrincipalIMCollateralAssetClass() != null && !agmt.getPrincipalIMCollateralAssetClass().isEmpty())
                || (agmt.getCounterpartyVMCollateralAssetClass() != null && !agmt.getCounterpartyVMCollateralAssetClass().isEmpty())
                || (agmt.getCounterpartyIMCollateralAssetClass() != null && !agmt.getCounterpartyIMCollateralAssetClass().isEmpty())) {
            //P VM
            if (agmt.getPrincipalVMCollateralAssetClass() != null
                    && !agmt.getPrincipalVMCollateralAssetClass().isEmpty()) {
                for (CollateralAssetClass assetClass : agmt.getPrincipalVMCollateralAssetClass()) {
                    if (assetClass.getCollateralAssetType() != null && !assetClass.getCollateralAssetType().isEmpty()) {
                        for (CollateralAssetType assetType : assetClass.getCollateralAssetType()) {
                            setupAgreementAssetType(assetClass.getCollateralAssetClassName().getRealValue(), assetType);
                        }
                    }
                    //after setup assetType,setup asset class
                    setupAgreementAssetClass(assetClass);
                }
            }
            enterNextStage();
            //P IM
            if (agmt.getPrincipalIMCollateralAssetClass() != null
                    && !agmt.getPrincipalIMCollateralAssetClass().isEmpty()) {
                for (CollateralAssetClass assetClass : agmt.getPrincipalIMCollateralAssetClass()) {
                    if (assetClass.getCollateralAssetType() != null && !assetClass.getCollateralAssetType().isEmpty()) {
                        for (CollateralAssetType assetType : assetClass.getCollateralAssetType()) {
                            setupAgreementAssetType(assetClass.getCollateralAssetClassName().getRealValue(), assetType);
                        }
                    }
                    //after setup assetType,setup asset class
                    setupAgreementAssetClass(assetClass);
                }
            }
            enterNextStage();
            //C VM
            if (agmt.getCounterpartyVMCollateralAssetClass() != null
                    && !agmt.getCounterpartyVMCollateralAssetClass().isEmpty()) {
                for (CollateralAssetClass assetClass : agmt.getCounterpartyVMCollateralAssetClass()) {
                    if (assetClass.getCollateralAssetType() != null && !assetClass.getCollateralAssetType().isEmpty()) {
                        for (CollateralAssetType assetType : assetClass.getCollateralAssetType()) {
                            setupAgreementAssetType(assetClass.getCollateralAssetClassName().getRealValue(), assetType);
                        }
                    }
                    //after setup assetType,setup asset class
                    setupAgreementAssetClass(assetClass);
                }
            }
            enterNextStage();
            //C IM
            if (agmt.getCounterpartyIMCollateralAssetClass() != null
                    && !agmt.getCounterpartyIMCollateralAssetClass().isEmpty()) {
                for (CollateralAssetClass assetClass : agmt.getCounterpartyIMCollateralAssetClass()) {
                    if (assetClass.getCollateralAssetType() != null && !assetClass.getCollateralAssetType().isEmpty()) {
                        for (CollateralAssetType assetType : assetClass.getCollateralAssetType()) {
                            setupAgreementAssetType(assetClass.getCollateralAssetClassName().getRealValue(), assetType);
                        }
                    }
                    //after setup assetType,setup asset class
                    setupAgreementAssetClass(assetClass);
                }
            }
        }

        enterNextStage();
    }

    private List<CollateralAssetClass> convertToCollateralClass(Agreement agmt) throws Exception{
        List<CollateralAssetClass> collateralAssetClassList = new ArrayList<>();
        //PCVI
        if (agmt.getCollateralAssetClass() != null && !agmt.getCollateralAssetClass().isEmpty()) {
            for (CollateralAssetClass assetClass : agmt.getCollateralAssetClass())
                collateralAssetClassList.add(assetClass);
        }

        //PC  VM IM seperated
        //PC VM
        else if ((agmt.getVmCollateralAssetClass() != null && !agmt.getVmCollateralAssetClass().isEmpty())
                || (agmt.getImCollateralAssetClass() != null && !agmt.getImCollateralAssetClass().isEmpty())) {

            if (agmt.getVmCollateralAssetClass() != null
                    && !agmt.getVmCollateralAssetClass().isEmpty()) {
                for (CollateralAssetClass assetClass : agmt.getVmCollateralAssetClass()){
                    for (CollateralAssetType assetType:assetClass.getCollateralAssetType()){
                        assetType.getApplicableType().add(ApplicableToType.VM);
                        assetType.getApplicableParty().add(ApplicablePartyToType.P);
                        assetType.getApplicableParty().add(ApplicablePartyToType.C);
                    }
                    for (ConcentrationLimitRule clRule : assetClass.getConcentrationLimitRule()){
                        clRule.getPrincipalOrCpty().add(ApplicablePartyToType.C);
                        clRule.getPrincipalOrCpty().add(ApplicablePartyToType.P);
                        clRule.getBookingType().add(ApplicableToType.VM);
                    }
                    collateralAssetClassList.add(assetClass);
                }
            }
            // PC IM
            if (agmt.getImCollateralAssetClass() != null && !agmt.getImCollateralAssetClass().isEmpty()) {
                int flag=0;
                for (CollateralAssetClass assetClass : agmt.getImCollateralAssetClass()) {
                    for (CollateralAssetType assetType : assetClass.getCollateralAssetType()) {
                        assetType.getApplicableType().add(ApplicableToType.IM);
                        assetType.getApplicableParty().add(ApplicablePartyToType.P);
                        assetType.getApplicableParty().add(ApplicablePartyToType.C);
                    }
                    for (ConcentrationLimitRule clRule : assetClass.getConcentrationLimitRule()){
                        clRule.getPrincipalOrCpty().add(ApplicablePartyToType.C);
                        clRule.getPrincipalOrCpty().add(ApplicablePartyToType.P);
                        clRule.getBookingType().add(ApplicableToType.IM);
                    }
                    if (!collateralAssetClassList.isEmpty()) {

                        for (CollateralAssetClass baseClass : collateralAssetClassList) {
                            if (assetClass.getCollateralAssetClassName().getRealValue().equals(
                                    baseClass.getCollateralAssetClassName().getRealValue())) {
                                for (CollateralAssetType assetType : assetClass.getCollateralAssetType())
                                    baseClass.getCollateralAssetType().add(assetType);
                                for (ConcentrationLimitRule cl :assetClass.getConcentrationLimitRule())
                                    baseClass.getConcentrationLimitRule().add(cl);
                                for (AutoBookingRule ar :assetClass.getAutoBookingRule())
                                    baseClass.getAutoBookingRule().add(ar);
                            } else
                                flag++;
                        }
                        if (flag != 0 && flag == collateralAssetClassList.size())
                            collateralAssetClassList.add(assetClass);
                    } else
                        collateralAssetClassList.add(assetClass);
                }
            }

        }

        // PC seperated    VMIM
        else if ((agmt.getPrincipalCollateralAssetClass() != null && !agmt.getPrincipalCollateralAssetClass().isEmpty())
                || (agmt.getCounterpartyCollateralAssetClass() != null && !agmt.getCounterpartyCollateralAssetClass().isEmpty())) {
            //P VMIM
            if (agmt.getPrincipalCollateralAssetClass() != null
                    && !agmt.getPrincipalCollateralAssetClass().isEmpty()) {
                for (CollateralAssetClass assetClass : agmt.getPrincipalCollateralAssetClass()){
                    for (CollateralAssetType assetType:assetClass.getCollateralAssetType()){
                        assetType.getApplicableType().add(ApplicableToType.IM);
                        assetType.getApplicableType().add(ApplicableToType.VM);
                        assetType.getApplicableParty().add(ApplicablePartyToType.P);
                    }
                    for (ConcentrationLimitRule clRule : assetClass.getConcentrationLimitRule()){
                        clRule.getPrincipalOrCpty().add(ApplicablePartyToType.P);
                        clRule.getBookingType().add(ApplicableToType.VM);
                        clRule.getBookingType().add(ApplicableToType.IM);
                    }
                    collateralAssetClassList.add(assetClass);
                }
            }
            //C VMIM
            if (agmt.getCounterpartyCollateralAssetClass() != null
                    && !agmt.getCounterpartyCollateralAssetClass().isEmpty()) {
                int flag=0;
                for (CollateralAssetClass assetClass : agmt.getCounterpartyCollateralAssetClass()) {
                    for (CollateralAssetType assetType : assetClass.getCollateralAssetType()) {
                        assetType.getApplicableType().add(ApplicableToType.IM);
                        assetType.getApplicableType().add(ApplicableToType.VM);
                        assetType.getApplicableParty().add(ApplicablePartyToType.C);
                    }
                    for (ConcentrationLimitRule clRule : assetClass.getConcentrationLimitRule()){
                        clRule.getPrincipalOrCpty().add(ApplicablePartyToType.C);
                        clRule.getBookingType().add(ApplicableToType.VM);
                        clRule.getBookingType().add(ApplicableToType.IM);
                    }
                    if (!collateralAssetClassList.isEmpty()) {

                        for (CollateralAssetClass baseClass : collateralAssetClassList) {
                            if (assetClass.getCollateralAssetClassName().getRealValue().equals(
                                    baseClass.getCollateralAssetClassName().getRealValue())) {
                                for (CollateralAssetType assetType : assetClass.getCollateralAssetType())
                                    baseClass.getCollateralAssetType().add(assetType);
                                for (ConcentrationLimitRule cl :assetClass.getConcentrationLimitRule())
                                    baseClass.getConcentrationLimitRule().add(cl);
                                for (AutoBookingRule ar :assetClass.getAutoBookingRule())
                                    baseClass.getAutoBookingRule().add(ar);
                            } else
                                flag++;
                        }
                        if (flag != 0 && flag == collateralAssetClassList.size())
                            collateralAssetClassList.add(assetClass);
                    } else
                        collateralAssetClassList.add(assetClass);
                }
            }
        }


        //PVM PIM CVM CIM
        else if ((agmt.getPrincipalVMCollateralAssetClass() != null && !agmt.getPrincipalVMCollateralAssetClass().isEmpty())
                || (agmt.getPrincipalIMCollateralAssetClass() != null && !agmt.getPrincipalIMCollateralAssetClass().isEmpty())
                || (agmt.getCounterpartyVMCollateralAssetClass() != null && !agmt.getCounterpartyVMCollateralAssetClass().isEmpty())
                || (agmt.getCounterpartyIMCollateralAssetClass() != null && !agmt.getCounterpartyIMCollateralAssetClass().isEmpty())) {
            //P VM
            if (agmt.getPrincipalVMCollateralAssetClass() != null
                    && !agmt.getPrincipalVMCollateralAssetClass().isEmpty()) {
                for (CollateralAssetClass assetClass : agmt.getPrincipalVMCollateralAssetClass()){
                    for (CollateralAssetType assetType:assetClass.getCollateralAssetType()){
                        assetType.getApplicableType().add(ApplicableToType.VM);
                        assetType.getApplicableParty().add(ApplicablePartyToType.P);
                    }
                    for (ConcentrationLimitRule clRule : assetClass.getConcentrationLimitRule()){
                        clRule.getPrincipalOrCpty().add(ApplicablePartyToType.P);
                        clRule.getBookingType().add(ApplicableToType.VM);
                    }
                    collateralAssetClassList.add(assetClass);
                }
            }
            //P IM
            if (agmt.getPrincipalIMCollateralAssetClass() != null
                    && !agmt.getPrincipalIMCollateralAssetClass().isEmpty()) {
                int flag=0;
                for (CollateralAssetClass assetClass : agmt.getPrincipalIMCollateralAssetClass()){
                    for (CollateralAssetType assetType:assetClass.getCollateralAssetType()){
                        assetType.getApplicableType().add(ApplicableToType.IM);
                        assetType.getApplicableParty().add(ApplicablePartyToType.P);
                    }
                    for (ConcentrationLimitRule clRule : assetClass.getConcentrationLimitRule()){
                        clRule.getPrincipalOrCpty().add(ApplicablePartyToType.P);
                        clRule.getBookingType().add(ApplicableToType.IM);
                    }
                    if (!collateralAssetClassList.isEmpty()) {
                        for (CollateralAssetClass baseClass : collateralAssetClassList) {
                            if (assetClass.getCollateralAssetClassName().getRealValue().equals(
                                    baseClass.getCollateralAssetClassName().getRealValue())) {
                                for (CollateralAssetType assetType : assetClass.getCollateralAssetType())
                                    baseClass.getCollateralAssetType().add(assetType);
                                for (ConcentrationLimitRule cl :assetClass.getConcentrationLimitRule())
                                    baseClass.getConcentrationLimitRule().add(cl);
                                for (AutoBookingRule ar :assetClass.getAutoBookingRule())
                                    baseClass.getAutoBookingRule().add(ar);
                                break;
                            } else
                                flag ++;
                        }
                        if (flag!=0 && flag==collateralAssetClassList.size())
                            collateralAssetClassList.add(assetClass);
                    } else
                        collateralAssetClassList.add(assetClass);
                }
            }
            //C VM
            if (agmt.getCounterpartyVMCollateralAssetClass() != null
                    && !agmt.getCounterpartyVMCollateralAssetClass().isEmpty()) {
                int flag=0;
                for (CollateralAssetClass assetClass : agmt.getCounterpartyVMCollateralAssetClass()){
                    for (CollateralAssetType assetType:assetClass.getCollateralAssetType()){
                        assetType.getApplicableType().add(ApplicableToType.VM);
                        assetType.getApplicableParty().add(ApplicablePartyToType.C);
                    }
                    for (ConcentrationLimitRule clRule : assetClass.getConcentrationLimitRule()){
                        clRule.getPrincipalOrCpty().add(ApplicablePartyToType.C);
                        clRule.getBookingType().add(ApplicableToType.VM);
                    }
                    if (!collateralAssetClassList.isEmpty()) {
                        for (CollateralAssetClass baseClass : collateralAssetClassList) {
                            if (assetClass.getCollateralAssetClassName().getRealValue().equals(
                                    baseClass.getCollateralAssetClassName().getRealValue())) {
                                for (CollateralAssetType assetType : assetClass.getCollateralAssetType())
                                    baseClass.getCollateralAssetType().add(assetType);
                                for (ConcentrationLimitRule cl :assetClass.getConcentrationLimitRule())
                                    baseClass.getConcentrationLimitRule().add(cl);
                                for (AutoBookingRule ar :assetClass.getAutoBookingRule())
                                    baseClass.getAutoBookingRule().add(ar);
                                break;
                            } else
                                flag++;
                        }
                        if (flag!=0 && flag==collateralAssetClassList.size())
                            collateralAssetClassList.add(assetClass);

                    } else
                        collateralAssetClassList.add(assetClass);
                }
            }
            //C IM
            if (agmt.getCounterpartyIMCollateralAssetClass() != null
                    && !agmt.getCounterpartyIMCollateralAssetClass().isEmpty()) {
                int flag=0;
                for (CollateralAssetClass assetClass : agmt.getCounterpartyIMCollateralAssetClass()){
                    for (CollateralAssetType assetType:assetClass.getCollateralAssetType()){
                        assetType.getApplicableType().add(ApplicableToType.IM);
                        assetType.getApplicableParty().add(ApplicablePartyToType.C);
                    }
                    for (ConcentrationLimitRule clRule : assetClass.getConcentrationLimitRule()){
                        clRule.getPrincipalOrCpty().add(ApplicablePartyToType.C);
                        clRule.getBookingType().add(ApplicableToType.IM);
                    }
                    if (!collateralAssetClassList.isEmpty()) {
                        for (CollateralAssetClass baseClass : collateralAssetClassList) {
                            if (assetClass.getCollateralAssetClassName().getRealValue().equals(
                                    baseClass.getCollateralAssetClassName().getRealValue())) {
                                for (CollateralAssetType assetType : assetClass.getCollateralAssetType())
                                    baseClass.getCollateralAssetType().add(assetType);
                                for (ConcentrationLimitRule cl :assetClass.getConcentrationLimitRule())
                                    baseClass.getConcentrationLimitRule().add(cl);
                                for (AutoBookingRule ar :assetClass.getAutoBookingRule())
                                    baseClass.getAutoBookingRule().add(ar);
                                break;
                            } else
                                flag++;
                        }
                        if (flag!=0 && flag==collateralAssetClassList.size())
                            collateralAssetClassList.add(assetClass);
                    } else
                        collateralAssetClassList.add(assetClass);
                }
            }

        }
        return collateralAssetClassList;
    }

    public void setupCollateralAssetClassNew(Agreement agmt) throws Exception {
        List<CollateralAssetClass> collateralAssetClassList = convertToCollateralClass(agmt);
        setupAssetInfo(collateralAssetClassList);
        enterNextStage();
    }

    @Override
    public void setupEligibilityRulesTemplateAssetClass(CollateralAssetClass assetClass) throws Exception {
        // setup asset class concentration limit rules
        super.setupEligibilityRulesTemplateAssetClass(assetClass);
        if (assetClass.getAutoBookingRule() != null && !assetClass.getAutoBookingRule().isEmpty()) {
//            element("ap.edit.rules", assetClass.getCollateralAssetClassName().getRealValue()).click();
            openAssetClassRule(assetClass);
            switchToAutoBookingRuleTab();
            setupAutoBookingRule(assetClass.getAutoBookingRule());
            saveAutoBookingRule();
            exitAutoBookingRule();
//            switchTo().defaultContent();
        }
    }

    /**
     * setup agreement asset class on agreement collateral tab
     *
     * @param assetClass
     */
    public void setupAgreementAssetClass(CollateralAssetClass assetClass) throws Exception {
        // TODO setup asset class here
        // setup asset class concentration limit rules
        if (assetClass.getConcentrationLimitRule() != null && !assetClass.getConcentrationLimitRule().isEmpty()) {
//            element("ap.edit.rules", assetClass.getCollateralAssetClassName().getRealValue()).click();
            openAssetClassRule(assetClass);
            switchToConcentrationLimitRulesTab();
            setupConcentrationLimitRules(assetClass.getConcentrationLimitRule());
            saveConcentrationLimitRules();
            exitConcentrationLimitRules();
//            switchTo().defaultContent();
        }

        if (assetClass.getAutoBookingRule() != null && !assetClass.getAutoBookingRule().isEmpty()) {
//            element("ap.edit.rules", assetClass.getCollateralAssetClassName().getRealValue()).click();
            openAssetClassRule(assetClass);
            switchToAutoBookingRuleTab();
            setupAutoBookingRule(assetClass.getAutoBookingRule());
            saveAutoBookingRule();
            exitAutoBookingRule();
//            switchTo().defaultContent();
        }

    }

    public void editAgreementAssetClassRule(CollateralAssetClass assetClass, List<CollateralAssetClass> oriList) throws Exception{
        CollateralAssetClass oriCollateralAssetClass = (CollateralAssetClass) Biz.matchedObjectWithName(assetClass, oriList);
        if (assetClass.getConcentrationLimitRule() != null && !assetClass.getConcentrationLimitRule().isEmpty()) {
            openAssetClassRule(assetClass);
            switchToConcentrationLimitRulesTab();
            editConcentrationLimitRules(oriCollateralAssetClass.getConcentrationLimitRule(), assetClass.getConcentrationLimitRule());
//            setupConcentrationLimitRules(assetClass.getConcentrationLimitRule());
            saveConcentrationLimitRules();
            exitConcentrationLimitRules();
//            switchTo().defaultContent();
        }

        if (assetClass.getAutoBookingRule() != null && !assetClass.getAutoBookingRule().isEmpty()) {
            openAssetClassRule(assetClass);
            switchToAutoBookingRuleTab();
            setupAutoBookingRule(assetClass.getAutoBookingRule());
            saveAutoBookingRule();
            exitAutoBookingRule();
//            switchTo().defaultContent();
        }

    }

    /**
     * setup agreement asset type on agreement collateral tab
     *
     * @param assetClassName
     * @param assetType
     */
    public void setupAgreementAssetType(String assetClassName, CollateralAssetType assetType) throws Exception {
        // expand the asset class
        waitThat().jQuery().toBeInactive();
        waitThat().document().toBeReady();
        logger.info(assetClassName + " expand button displayed - " + element("ap.asset.class.expand", assetClassName).isDisplayed());
        if (element("ap.asset.class.expand", assetClassName).isDisplayed()){
            element("ap.asset.class.expand", assetClassName).clickCircularly();
        }
        // tick on the asset type
        if(!element("ap.asset.type", assetType.getCollateralAssetTypeName().getRealValue()).isChecked()) {
            if (element("ap.asset.type", assetType.getCollateralAssetTypeName().getRealValue()).tick(true)) {
                waitThat("ap.asset.class.expand", assetClassName).toBeVisible();
            }
        }

        if (assetType.getInterestRule() != null || assetType.getWhtRule() != null || (assetType.getConcentrationLimitRule() != null && assetType.getConcentrationLimitRule().size() > 0)
                ||assetType.getTemplateVsAgreementEligibilityRules() != null || assetType.getSettlementDate() != null || assetType.getDeliveryPriority() != null || assetType.getRecallPriority() != null
                || assetType.getAssetNote1() != null || assetType.getAssetNote2() != null || assetType.getAssetNote3() != null ){
            if(element("ap.asset.class.expand", assetClassName).isDisplayed())
                element("ap.asset.class.expand", assetClassName).clickCircularly();
        }
        //setup asset type parameters
        if (assetType.getTemplateVsAgreementEligibilityRules() != null){
            element("ap.asset.type.template.vs.agreement.eligibility.rule",assetType.getCollateralAssetTypeName().getRealValue()).selectByVisibleText(assetType.getTemplateVsAgreementEligibilityRules().getRealValue());
        }
        if (assetType.getSettlementDate() != null) {
            element("ap.asset.type.settlement.date", assetType.getCollateralAssetTypeName().getRealValue()).selectByVisibleText(assetType.getSettlementDate().getRealValue());
        }
        if (assetType.getDeliveryPriority() != null) {
            element("ap.asset.type.delivery.priority", assetType.getCollateralAssetTypeName().getRealValue()).selectByVisibleText(assetType.getDeliveryPriority().value());
        }
        if (assetType.getRecallPriority() != null) {
            element("ap.asset.type.recall.priority", assetType.getCollateralAssetTypeName().getRealValue()).selectByVisibleText(assetType.getRecallPriority().value());
        }
        if (assetType.getAssetNote1() != null)
            element("ap.asset.type.first.note",assetType.getCollateralAssetTypeName().getRealValue()).input(assetType.getAssetNote1().getRealValue());

        if (assetType.getAssetNote2() != null || assetType.getAssetNote3() != null) {
            element("ap.asset.type.expand.notes", assetType.getCollateralAssetTypeName().getRealValue()).click();
            if (assetType.getAssetNote2().getRealValue() != null) {
                element("ap.asset.type.second.note", assetType.getCollateralAssetTypeName().getRealValue()).input(assetType.getAssetNote2().getRealValue());
            }
            if (assetType.getAssetNote3().getRealValue() != null) {
                element("ap.asset.type.third.note", assetType.getCollateralAssetTypeName().getRealValue()).input(assetType.getAssetNote3().getRealValue());
            }
        }



        // setup asset type haircut rules, interest rules, wht rules,
        // eligibility rules, concentration limit rules
        if (assetType.getInterestRule() != null
                || assetType.getWhtRule() != null
                || assetType.getEligibilityRule() != null
                || (assetType.getConcentrationLimitRule() != null && assetType.getConcentrationLimitRule().size() > 0)) {

            openAssetTypeRule(assetClassName, assetType);

            // setup asset type haircut rules - yan lu
            if (assetType.getHaircutRule() != null) {
                switchToHaircutTab();
                setupHaircutRule(assetType.getHaircutRule());
                saveHaircutRule();
            }
            // setup asset type eligibility rules
            if (assetType.getEligibilityRule() != null) {
                switchToEligibilityRulesTab();
                setupEligibilityRules(assetType.getEligibilityRule());
                saveEligibilityRules();
            }
            // setup asset type interest Rules and wht rules
            if (assetType.getInterestRule() != null || assetType.getWhtRule() != null) {
                switchToInterestRuleAndWhtRuleTab();
                if (assetType.getInterestRule() != null)
                    setupInterestRule(assetType.getInterestRule());
                if (assetType.getWhtRule() != null)
                    setupWhtRule(assetType.getWhtRule());
                saveInterestRuleAndWhtRule();
            }

            // setup asset type concentration limit rules
            if (assetType.getConcentrationLimitRule() != null && assetType.getConcentrationLimitRule().size() > 0) {
                switchToConcentrationLimitRulesTab();
                setupConcentrationLimitRules(assetType.getConcentrationLimitRule());
                saveConcentrationLimitRules();
            }

            clickSmartly(locator("ap.haircut.exit"), locator("ap.interest.exit"), locator("ap.el.exit"),
                    locator("ap.cl.exit"));
//            switchTo().defaultContent();
        }
    }

    public void editAgreementAssetTypeRule(String assetClassName, CollateralAssetType assetType, List<CollateralAssetType> oriList) throws Exception{
        // setup asset type haircut rules, interest rules, wht rules,
        // eligibility rules, concentration limit rules
        CollateralAssetType oriCollateralAssetType = (CollateralAssetType) Biz.matchedObjectWithName(assetType, oriList);
        if (assetType.getInterestRule() != null
                || assetType.getWhtRule() != null
                || assetType.getEligibilityRule() != null
                || assetType.getHaircutRule() != null
                || (assetType.getConcentrationLimitRule() != null && assetType.getConcentrationLimitRule().size() > 0)) {
            openAssetTypeRule(assetClassName, assetType);

            // setup asset type haircut rules - yan lu
            if (assetType.getHaircutRule() != null) {
                switchToHaircutTab();
                editHaircutRule(oriCollateralAssetType.getHaircutRule(), assetType.getHaircutRule());
                saveHaircutRule();
            }
            // setup asset type eligibility rules
            if (assetType.getEligibilityRule() != null) {
                switchToEligibilityRulesTab();
                editEligibilityRules(oriCollateralAssetType.getEligibilityRule(), assetType.getEligibilityRule());
                saveEligibilityRules();
            }
            // setup asset type interest Rules and wht rules
            if (assetType.getInterestRule() != null || assetType.getWhtRule() != null) {
                switchToInterestRuleAndWhtRuleTab();
                if (assetType.getInterestRule() != null)
                    editInterestRule(oriCollateralAssetType.getInterestRule(), assetType.getInterestRule());
                if (assetType.getWhtRule() != null)
                    editWhtRule(oriCollateralAssetType.getWhtRule(), assetType.getWhtRule());
                saveInterestRuleAndWhtRule();
            }

            // setup asset type concentration limit rules
            if (assetType.getConcentrationLimitRule() != null && assetType.getConcentrationLimitRule().size() > 0) {
                switchToConcentrationLimitRulesTab();
                editConcentrationLimitRules(oriCollateralAssetType.getConcentrationLimitRule(), assetType.getConcentrationLimitRule());
                saveConcentrationLimitRules();
            }

            clickSmartly(locator("ap.haircut.exit"), locator("ap.interest.exit"), locator("ap.el.exit"),
                    locator("ap.cl.exit"));
//            switchTo().defaultContent();
        }
    }

    public void assertAgreementAssetTypeRule(String assetClassName, CollateralAssetType assetType) throws Exception{
        openAssetTypeRule(assetClassName,assetType);
        if (assetType.getConcentrationLimitRule() != null && assetType.getConcentrationLimitRule().size() > 0) {
            switchToConcentrationLimitRulesTab();
            assertConcentrationLimitRules(assetType.getConcentrationLimitRule());
            exitConcentrationLimitRules();
//            switchTo().defaultContent();
        }
    }

    public void editAgreementAssetClassNew(Agreement oriAgreement, Agreement newAgreement) throws Exception{
        List<CollateralAssetClass> collateralAssetClassListOri = convertToCollateralClass(oriAgreement);
        List<CollateralAssetClass> collateralAssetClassListNew = convertToCollateralClass(newAgreement);
        modifyAssetInfo(collateralAssetClassListOri,collateralAssetClassListNew);
        enterNextStage();
    }


    public void editAgreementAssetClass(Agreement oriAgreement, Agreement newAgreement) throws Exception{
        //PCVI
        if (newAgreement.getCollateralAssetClass() != null && !newAgreement.getCollateralAssetClass().isEmpty()) {
            for (CollateralAssetClass assetClass : newAgreement.getCollateralAssetClass()) {
                CollateralAssetClass oriCollateralAssetClass = (CollateralAssetClass) Biz.matchedObjectWithName(assetClass, oriAgreement.getCollateralAssetClass());
                if (assetClass.getCollateralAssetType() != null
                        && !assetClass.getCollateralAssetType().isEmpty()) {
                    for (CollateralAssetType assetType : assetClass.getCollateralAssetType()) {
                        editAgreementAssetTypeRule(
                                assetClass.getCollateralAssetClassName().getRealValue(), assetType, oriCollateralAssetClass.getCollateralAssetType()
                        );
                    }
                }
                //after setup assetType,setup asset class
                editAgreementAssetClassRule(assetClass, oriAgreement.getCollateralAssetClass());
            }
        }

        //PC  VM IM seperated
        //PC VM
        if ((newAgreement.getVmCollateralAssetClass() != null && !newAgreement.getVmCollateralAssetClass().isEmpty())
                || (newAgreement.getImCollateralAssetClass() != null && !newAgreement.getImCollateralAssetClass().isEmpty())) {
            if (newAgreement.getVmCollateralAssetClass() != null
                    && !newAgreement.getVmCollateralAssetClass().isEmpty()) {
                for (CollateralAssetClass assetClass : newAgreement.getVmCollateralAssetClass()) {
                    CollateralAssetClass oriCollateralAssetClass = (CollateralAssetClass) Biz.matchedObjectWithName(assetClass, oriAgreement.getVmCollateralAssetClass());
                    if (assetClass.getCollateralAssetType() != null
                            && !assetClass.getCollateralAssetType().isEmpty()) {
                        for (CollateralAssetType assetType : assetClass.getCollateralAssetType()) {
                            editAgreementAssetTypeRule(
                                    assetClass.getCollateralAssetClassName().getRealValue(), assetType, oriCollateralAssetClass.getCollateralAssetType()
                            );
                        }
                    }
                    //after setup assetType,setup asset class
                    editAgreementAssetClassRule(assetClass, oriAgreement.getVmCollateralAssetClass());
                }
            }
            enterNextStage();
            // PC IM
            if (newAgreement.getImCollateralAssetClass() != null && !newAgreement.getImCollateralAssetClass().isEmpty()) {
                for (CollateralAssetClass assetClass : newAgreement.getImCollateralAssetClass()) {
                    CollateralAssetClass oriCollateralAssetClass = (CollateralAssetClass) Biz.matchedObjectWithName(assetClass, oriAgreement.getImCollateralAssetClass());
                    if (assetClass.getCollateralAssetType() != null
                            && !assetClass.getCollateralAssetType().isEmpty()) {
                        for (CollateralAssetType assetType : assetClass.getCollateralAssetType()) {
                            editAgreementAssetTypeRule(
                                    assetClass.getCollateralAssetClassName().getRealValue(), assetType, oriCollateralAssetClass.getCollateralAssetType()
                            );
                        }
                    }
                    //after setup assetType,setup asset class
                    editAgreementAssetClassRule(assetClass, oriAgreement.getImCollateralAssetClass());
                }
            }
        }

        // PC seperated    VMIM
        if ((newAgreement.getPrincipalCollateralAssetClass() != null && !newAgreement.getPrincipalCollateralAssetClass().isEmpty())
                || (newAgreement.getCounterpartyCollateralAssetClass() != null && !newAgreement.getCounterpartyCollateralAssetClass().isEmpty())) {
            //P VMIM
            if (newAgreement.getPrincipalCollateralAssetClass() != null
                    && !newAgreement.getPrincipalCollateralAssetClass().isEmpty()) {
                for (CollateralAssetClass assetClass : newAgreement.getPrincipalCollateralAssetClass()) {
                    CollateralAssetClass oriCollateralAssetClass = (CollateralAssetClass) Biz.matchedObjectWithName(assetClass, oriAgreement.getPrincipalCollateralAssetClass());
                    if (assetClass.getCollateralAssetType() != null
                            && !assetClass.getCollateralAssetType().isEmpty()) {
                        for (CollateralAssetType assetType : assetClass.getCollateralAssetType()) {
                            editAgreementAssetTypeRule(
                                    assetClass.getCollateralAssetClassName().getRealValue(), assetType, oriCollateralAssetClass.getCollateralAssetType()
                            );
                        }
                    }
                    //after setup assetType,setup asset class
                    editAgreementAssetClassRule(assetClass, oriAgreement.getPrincipalCollateralAssetClass());
                }
            }
            enterNextStage();
            //C VMIM
            if (newAgreement.getCounterpartyCollateralAssetClass() != null
                    && !newAgreement.getCounterpartyCollateralAssetClass().isEmpty()) {
                for (CollateralAssetClass assetClass : newAgreement.getCounterpartyCollateralAssetClass()) {
                    CollateralAssetClass oriCollateralAssetClass = (CollateralAssetClass) Biz.matchedObjectWithName(assetClass, oriAgreement.getCounterpartyCollateralAssetClass());
                    if (assetClass.getCollateralAssetType() != null && !assetClass.getCollateralAssetType().isEmpty()) {
                        for (CollateralAssetType assetType : assetClass.getCollateralAssetType()) {
                            editAgreementAssetTypeRule(
                                    assetClass.getCollateralAssetClassName().getRealValue(), assetType, oriCollateralAssetClass.getCollateralAssetType()
                            );
                        }
                    }
                    //after setup assetType,setup asset class
                    editAgreementAssetClassRule(assetClass, oriAgreement.getCounterpartyCollateralAssetClass());
                }
            }
        }


        //PVM PIM CVM CIM
        if ((newAgreement.getPrincipalVMCollateralAssetClass() != null && !newAgreement.getPrincipalVMCollateralAssetClass().isEmpty())
                || (newAgreement.getPrincipalIMCollateralAssetClass() != null && !newAgreement.getPrincipalIMCollateralAssetClass().isEmpty())
                || (newAgreement.getCounterpartyVMCollateralAssetClass() != null && !newAgreement.getCounterpartyVMCollateralAssetClass().isEmpty())
                || (newAgreement.getCounterpartyIMCollateralAssetClass() != null && !newAgreement.getCounterpartyIMCollateralAssetClass().isEmpty())) {
            //P VM
            if (newAgreement.getPrincipalVMCollateralAssetClass() != null
                    && !newAgreement.getPrincipalVMCollateralAssetClass().isEmpty()) {
                for (CollateralAssetClass assetClass : newAgreement.getPrincipalVMCollateralAssetClass()) {
                    CollateralAssetClass oriCollateralAssetClass = (CollateralAssetClass) Biz.matchedObjectWithName(assetClass, oriAgreement.getPrincipalVMCollateralAssetClass());
                    if (assetClass.getCollateralAssetType() != null && !assetClass.getCollateralAssetType().isEmpty()) {
                        for (CollateralAssetType assetType : assetClass.getCollateralAssetType()) {
                            editAgreementAssetTypeRule(
                                    assetClass.getCollateralAssetClassName().getRealValue(), assetType, oriCollateralAssetClass.getCollateralAssetType()
                            );
                        }
                    }
                    //after setup assetType,setup asset class
                    editAgreementAssetClassRule(assetClass, oriAgreement.getPrincipalVMCollateralAssetClass());
                }
            }
            enterNextStage();
            //P IM
            if (newAgreement.getPrincipalIMCollateralAssetClass() != null
                    && !newAgreement.getPrincipalIMCollateralAssetClass().isEmpty()) {
                for (CollateralAssetClass assetClass : newAgreement.getPrincipalIMCollateralAssetClass()) {
                    CollateralAssetClass oriCollateralAssetClass = (CollateralAssetClass) Biz.matchedObjectWithName(assetClass, oriAgreement.getPrincipalIMCollateralAssetClass());
                    if (assetClass.getCollateralAssetType() != null && !assetClass.getCollateralAssetType().isEmpty()) {
                        for (CollateralAssetType assetType : assetClass.getCollateralAssetType()) {
                            editAgreementAssetTypeRule(
                                    assetClass.getCollateralAssetClassName().getRealValue(), assetType, oriCollateralAssetClass.getCollateralAssetType()
                            );
                        }
                    }
                    //after setup assetType,setup asset class
                    editAgreementAssetClassRule(assetClass, oriAgreement.getPrincipalIMCollateralAssetClass());
                }
            }
            enterNextStage();
            //C VM
            if (newAgreement.getCounterpartyVMCollateralAssetClass() != null
                    && !newAgreement.getCounterpartyVMCollateralAssetClass().isEmpty()) {
                for (CollateralAssetClass assetClass : newAgreement.getCounterpartyVMCollateralAssetClass()) {
                    CollateralAssetClass oriCollateralAssetClass = (CollateralAssetClass) Biz.matchedObjectWithName(assetClass, oriAgreement.getCounterpartyVMCollateralAssetClass());
                    if (assetClass.getCollateralAssetType() != null && !assetClass.getCollateralAssetType().isEmpty()) {
                        for (CollateralAssetType assetType : assetClass.getCollateralAssetType()) {
                            editAgreementAssetTypeRule(
                                    assetClass.getCollateralAssetClassName().getRealValue(), assetType, oriCollateralAssetClass.getCollateralAssetType()
                            );
                        }
                    }
                    //after setup assetType,setup asset class
                    editAgreementAssetClassRule(assetClass, oriAgreement.getCounterpartyVMCollateralAssetClass());
                }
            }
            enterNextStage();
            //C IM
            if (newAgreement.getCounterpartyIMCollateralAssetClass() != null
                    && !newAgreement.getCounterpartyIMCollateralAssetClass().isEmpty()) {
                for (CollateralAssetClass assetClass : newAgreement.getCounterpartyIMCollateralAssetClass()) {
                    CollateralAssetClass oriCollateralAssetClass = (CollateralAssetClass) Biz.matchedObjectWithName(assetClass, oriAgreement.getCounterpartyIMCollateralAssetClass());
                    if (assetClass.getCollateralAssetType() != null && !assetClass.getCollateralAssetType().isEmpty()) {
                        for (CollateralAssetType assetType : assetClass.getCollateralAssetType()) {
                            editAgreementAssetTypeRule(
                                    assetClass.getCollateralAssetClassName().getRealValue(), assetType, oriCollateralAssetClass.getCollateralAssetType()
                            );
                        }
                    }
                    //after setup assetType,setup asset class
                    editAgreementAssetClassRule(assetClass, oriAgreement.getCounterpartyIMCollateralAssetClass());
                }
            }
        }
        enterNextStage();
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

    public void enterLastStage() throws Exception {
        waitThat().document().toBeReady();
        waitThat().jQuery().toBeInactive();
        element("ap.last").clickByJavaScript();
        //Biz.d33640Workaround(this);
    }

    public void openAssetClassRule(CollateralAssetClass collateralAssetClass) throws Exception{
        element("ap.edit.rules", collateralAssetClass.getCollateralAssetClassName().getRealValue()).clickByJavaScript();
//        element("ap.frame.asset.rule").switchTo();
//        waitThat("hm.frame.body").innerText().notToBe("false");
//        waitThat().timeout(PropHelper.TIMEOUT_NAVIGATION_INTERVAL);
    }

    public void openAssetTypeRule(String assetClassName, CollateralAssetType collateralAssetType) throws Exception{
        element("ap.asset.class.expand", assetClassName).clickSmartly();
        element("ap.edit.type.rules", collateralAssetType.getCollateralAssetTypeName().getRealValue()).clickByJavaScript();
//        element("ap.frame.asset.rule").switchTo();
//        waitThat("hm.frame.body").innerText().notToBe("false");
//        waitThat().timeout(PropHelper.TIMEOUT_NAVIGATION_INTERVAL);
    }

    @Override
    public void setupEligibilityRulesTemplateAssetType(String rowXpath, CollateralAssetType assetType) throws Exception {
        super.setupEligibilityRulesTemplateAssetType(rowXpath,assetType);
        if ((assetType.getApplicableParty() == null||assetType.getApplicableParty().isEmpty()) && element("er.applicable.party", rowXpath).isDisplayed()) {
            element("er.applicable.party", rowXpath).selectAll();
        }

        if ((assetType.getApplicableType() == null||assetType.getApplicableType().isEmpty()) && element("er.applicable.type", rowXpath).isDisplayed()){
            element("er.applicable.type", rowXpath).selectAll();
        }
    }

}
