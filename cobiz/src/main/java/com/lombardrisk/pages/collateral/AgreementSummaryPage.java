package com.lombardrisk.pages.collateral;

import com.lombardrisk.pojo.*;
import com.lombardrisk.util.Biz;
import com.lombardrisk.util.PageHelper;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Kenny Wang on 2/3/2016.
 */
public class AgreementSummaryPage extends PageBase {
    public AgreementSummaryPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }


    /**
     * enter agreement party summary page
     */
    public void enterAgreementPartySummary() throws Exception {
        if (element("as.party.tab").isDisplayed())
            element("as.party.tab").clickByJavaScript();
        else
            logger.warn("Party/Counterparty tab doesn't exist");
    }


    /**
     * switch to agreement documentation summary tab
     */
    public void enterAgreementDocumentationSummary() throws Exception {
        element("as.docu.tab").click();
    }


    public void enterAgreementFixedTriggerSummary() throws Exception {
        element("as.fixtrigger.tab").click();
    }

    public void enterAgreementCollateralSummary() throws Exception {
        element("as.collateral.tab").clickByJavaScript();
    }

    public void enterAgreementSummary() throws Exception {
        element("as.smry.tab").click();
    }

    public void checkAgreementEditbuttonIsDisabled(AgreementSummary agmt) throws Exception {
        validateThat("as.edit").present().isEqualTo(false);
    }

    /**
     * click edit button
     */
    public void editAgreementSummary() throws Exception {
        if (element("as.edit").isDisplayed())
            element("as.edit").clickByJavaScript();
        else if (element("as.messaging.edit").isDisplayed()) {
            element("as.messaging.edit").fireEvent(Biz.FIRE_EVENT_ONCLICK);
        } else {
            logger.warn("Edit button doesn't exist");
        }
    }

    public void forceEditAgreementSummary() throws Exception {
        boolean flag = false;
        for (IWebDriverWrapper.IWebElementWrapper e : element("as.edit").getAllMatchedElements()) {
            if (e.isDisplayed()) {
                e.clickByJavaScript();
                flag = true;
                break;
            }
        }
        if (!flag)
            logger.warn("Edit button doesn't exist");
    }

    /**
     * save to linkage set
     */
    public void savetoLinkageSet(AgreementSummary agmt) throws Exception {
        if (agmt.isSelectAll() != null)
            element("as.select.all").check(agmt.isSelectAll());
        if (agmt.getPartyAndCounterpartyDetails() != null)
            tickPartyAndCounterpartyDetails(agmt.getPartyAndCounterpartyDetails());
        if (agmt.getDocumentationDetails() != null)
            tickDocumentationDetails(agmt.getDocumentationDetails());
        if (agmt.getValuationFrequencyAndCallScheduleDetails() != null)
            tickValuationFrequencyAndCallScheduleDetails(agmt.getValuationFrequencyAndCallScheduleDetails());
        if (agmt.getCollateralisedPortfolioDetails() != null)
            tickCollateralisedPortfolioDetails(agmt.getCollateralisedPortfolioDetails());
        if (agmt.getCollateralRules() != null)
            tickCollateralRules(agmt.getCollateralRules());
        if (agmt.getPrincipalAndCounterpartyEligibleForReceipt() != null && !agmt.getPrincipalAndCounterpartyEligibleForReceipt().isEmpty())
            tickPrincipalAndCounterpartyEligibleForReceipt(agmt.getPrincipalAndCounterpartyEligibleForReceipt());

        //Principal and Counterparty Haircut Adjustment Rules

        if (agmt.getPrincipalAndCounterpartyEligibilityRules() != null && !agmt.getPrincipalAndCounterpartyEligibilityRules().isEmpty())
            tickPrincipalAndCounterpartyEligibleRules(agmt.getPrincipalAndCounterpartyEligibilityRules());

        //Auto Booking Rule

        if (agmt.getUserDefinedFieldsDetails() != null)
            tickUserDefinedFieldsDetails(agmt.getUserDefinedFieldsDetails());
        if (agmt.getSettlementInformationConfiguration() != null)
            tickSettlementInformationConfiguration(agmt.getSettlementInformationConfiguration());
        if (agmt.getReportingDetails() != null)
            tickReportingDetails(agmt.getReportingDetails());

        if (agmt.getSaveToLinkageSet() != null) {
            for (StringType linkage : agmt.getSaveToLinkageSet())
                element("as.linkage.set").selectByVisibleText(linkage.getRealValue());
        }
        // click save to linkage set button
        element("as.linkage.set.save").click();
    }

    private void tickPartyAndCounterpartyDetails(PartyAndCounterpartyDetails partyAndCounterpartyDetails) throws Exception {
        if (partyAndCounterpartyDetails.isCheck() != null)
            element("as.select.parcoudetail").check(partyAndCounterpartyDetails.isCheck());
        if (partyAndCounterpartyDetails.getCustodian() != null && partyAndCounterpartyDetails.getCustodian().isCheck() != null)
            element("as.select.custodian").check(partyAndCounterpartyDetails.getCustodian().isCheck());
        if (partyAndCounterpartyDetails.getRegion() != null && partyAndCounterpartyDetails.getRegion().isCheck() != null)
            element("as.select.region").check(partyAndCounterpartyDetails.getRegion().isCheck());
        if (partyAndCounterpartyDetails.getGroup() != null && partyAndCounterpartyDetails.getGroup().isCheck() != null)
            element("as.select.group").check(partyAndCounterpartyDetails.getGroup().isCheck());
        if (partyAndCounterpartyDetails.getRegulatory() != null && partyAndCounterpartyDetails.getRegulatory().isCheck() != null)
            element("as.select.regulatory").check(partyAndCounterpartyDetails.getRegulatory().isCheck());
        if (partyAndCounterpartyDetails.getAgreementDescription() != null && partyAndCounterpartyDetails.getAgreementDescription().isCheck() != null)
            element("as.select.arg.desc").check(partyAndCounterpartyDetails.getAgreementDescription().isCheck());
        if (partyAndCounterpartyDetails.getCategory() != null && partyAndCounterpartyDetails.getCategory().isCheck() != null)
            element("as.select.category").check(partyAndCounterpartyDetails.getCategory().isCheck());
        if (partyAndCounterpartyDetails.getCollateralManager() != null && partyAndCounterpartyDetails.getCollateralManager().isCheck() != null)
            element("as.select.colman").check(partyAndCounterpartyDetails.getCollateralManager().isCheck());
        if (partyAndCounterpartyDetails.getNotes() != null && partyAndCounterpartyDetails.getNotes().isCheck() != null)
            element("as.select.notes").check(partyAndCounterpartyDetails.getNotes().isCheck());
        if (partyAndCounterpartyDetails.getRank() != null && partyAndCounterpartyDetails.getRank().isCheck() != null)
            element("as.select.rank").check(partyAndCounterpartyDetails.getRank().isCheck());
        if (partyAndCounterpartyDetails.getAgreedJurisdiction() != null &&
                partyAndCounterpartyDetails.getAgreedJurisdiction().isCheck() != null) {
            element("as.select.agreedJurisdiction").check(partyAndCounterpartyDetails.getAgreedJurisdiction().isCheck());
        }
        if (partyAndCounterpartyDetails.getConfigurableRule() != null &&
                partyAndCounterpartyDetails.getConfigurableRule().isCheck() != null) {
            element("as.select.configurableRule").check(partyAndCounterpartyDetails.getConfigurableRule().isCheck());
        }
//        partyAndCounterpartyDetails.getPrincipal();
//        partyAndCounterpartyDetails.getCounterparty();
//        partyAndCounterpartyDetails.getCounterpartyOrgRegion();
//        partyAndCounterpartyDetails.getBusinessLine();
//        partyAndCounterpartyDetails.getMultiModel();
//        partyAndCounterpartyDetails.getUmbrellaAgreement();
//        partyAndCounterpartyDetails.getStatus();
//        partyAndCounterpartyDetails.getAgreementName();
//        partyAndCounterpartyDetails.getExternalId();
//        partyAndCounterpartyDetails.getOwner();
//        partyAndCounterpartyDetails.getLastmodifiedBy();
//        partyAndCounterpartyDetails.getLinkageSet();
    }

    private void tickDocumentationDetails(DocumentationDetails documentationDetails) throws Exception {
        if (documentationDetails.isCheck() != null)
            element("as.select.docdetail").check(documentationDetails.isCheck());
        if (documentationDetails.getPrincipalBranches() != null && documentationDetails.getPrincipalBranches().isCheck() != null)
            element("as.select.pribran").check(documentationDetails.getPrincipalBranches().isCheck());
        if (documentationDetails.getPrincipalContact() != null && documentationDetails.getPrincipalContact().isCheck() != null)
            element("as.select.pricont").check(documentationDetails.getPrincipalContact().isCheck());
        if (documentationDetails.getPrincipalCCEmail() != null && documentationDetails.getPrincipalCCEmail().isCheck() != null) {
            element("as.select.pricontcc").check(documentationDetails.getPrincipalCCEmail().isCheck());
            if (documentationDetails.getPrincipalCCEmail().getContact() != null && !documentationDetails.getPrincipalCCEmail().getContact().isEmpty()) {
                for (StringType stringType : documentationDetails.getPrincipalCCEmail().getContact()) {
                    if (stringType.isCheck() != null)
                        element("as.select.pricontcc.emails", stringType.getRealValue()).check(stringType.isCheck());
                }
            }
        }
        if (documentationDetails.getPrincipalInterestcontact() != null && documentationDetails.getPrincipalInterestcontact().isCheck() != null)
            element("as.select.priinterestcont").check(documentationDetails.getPrincipalInterestcontact().isCheck());
        if (documentationDetails.getPrincipalInterestCCEmail() != null && documentationDetails.getPrincipalInterestCCEmail().isCheck() != null) {
            element("as.select.priinterestcontcc").check(documentationDetails.getPrincipalInterestCCEmail().isCheck());
            if (documentationDetails.getPrincipalInterestCCEmail().getContact() != null && !documentationDetails.getPrincipalInterestCCEmail().getContact().isEmpty()) {
                for (StringType stringType : documentationDetails.getPrincipalInterestCCEmail().getContact()) {
                    if (stringType.isCheck() != null)
                        element("as.select.priinterestcontcc.emails", stringType.getRealValue()).check(
                                stringType.isCheck());
                }
            }
        }
        if (documentationDetails.getCounterpartyBranches() != null && documentationDetails.getCounterpartyBranches().isCheck() != null)
            element("as.select.coubran").check(documentationDetails.getCounterpartyBranches().isCheck());
        if (documentationDetails.getCounterpartyContact() != null && documentationDetails.getCounterpartyContact().isCheck() != null)
            element("as.select.coucont").check(documentationDetails.getCounterpartyContact().isCheck());
        if (documentationDetails.getCounterpartyCCEmail() != null && documentationDetails.getCounterpartyCCEmail().isCheck() != null) {
            element("as.select.coucontcc").check(documentationDetails.getCounterpartyCCEmail().isCheck());
            if (documentationDetails.getCounterpartyCCEmail().getContact() != null && !documentationDetails.getCounterpartyCCEmail().getContact().isEmpty()) {
                for (StringType stringType : documentationDetails.getCounterpartyCCEmail().getContact()) {
                    if (stringType.isCheck() != null)
                        element("as.select.coucontcc.emails", stringType.getRealValue()).check(stringType.isCheck());
                }
            }
        }
        if (documentationDetails.getCounterpartyInterestcontact() != null && documentationDetails.getCounterpartyInterestcontact().isCheck() != null)
            element("as.select.couinterestcont").check(documentationDetails.getCounterpartyInterestcontact().isCheck());
        if (documentationDetails.getCounterpartyInterestCCEmail() != null && documentationDetails.getCounterpartyInterestCCEmail().isCheck() != null) {
            element("as.select.couinterestcontcc").check(
                    documentationDetails.getCounterpartyInterestCCEmail().isCheck());
            if (documentationDetails.getCounterpartyInterestCCEmail().getContact() != null && !documentationDetails.getCounterpartyInterestCCEmail().getContact().isEmpty()) {
                for (StringType stringType : documentationDetails.getCounterpartyInterestCCEmail().getContact()) {
                    if (stringType.isCheck() != null)
                        element("as.select.couinterestcontcc.emails", stringType.getRealValue()).check(
                                stringType.isCheck());
                }
            }
        }
        if (documentationDetails.getSendConfirmationLetterTo() != null && documentationDetails.getSendConfirmationLetterTo().isCheck() != null)
            element("as.select.sendconfirmletterto").check(
                    documentationDetails.getSendConfirmationLetterTo().isCheck());
        if (documentationDetails.getPrincipalNAV() != null && documentationDetails.getPrincipalNAV().isCheck() != null)
            element("as.select.prinav").check(documentationDetails.getPrincipalNAV().isCheck());
        if (documentationDetails.getPrincipalValuationAgent() != null && documentationDetails.getPrincipalValuationAgent().isCheck() != null)
            element("as.select.privalagent").check(documentationDetails.getPrincipalNAV().isCheck());
        if (documentationDetails.getCounterpartyNAV() != null && documentationDetails.getCounterpartyNAV().isCheck() != null)
            element("as.select.counav").check(documentationDetails.getCounterpartyNAV().isCheck());
        if (documentationDetails.getCounterpartyValuationAgent() != null && documentationDetails.getCounterpartyValuationAgent().isCheck() != null)
            element("as.select.couvalagent").check(documentationDetails.getCounterpartyValuationAgent().isCheck());
        if (documentationDetails.getMasterDocumentation() != null && documentationDetails.getMasterDocumentation().isCheck() != null)
            element("as.select.masterdoc").check(documentationDetails.getMasterDocumentation().isCheck());
        if (documentationDetails.getCreditSupportDocumentation() != null && documentationDetails.getCreditSupportDocumentation().isCheck() != null)
            element("as.select.creditsupdoc").check(documentationDetails.getCreditSupportDocumentation().isCheck());
        if (documentationDetails.getReciprocity() != null && documentationDetails.getReciprocity().isCheck() != null)
            element("as.select.reciprocity").check(documentationDetails.getReciprocity().isCheck());
        if (documentationDetails.getBaseCurrency() != null && documentationDetails.getBaseCurrency().isCheck() != null)
            element("as.select.baseccy").check(documentationDetails.getBaseCurrency().isCheck());
        if (documentationDetails.getPrincipalTransferCurrency() != null && documentationDetails.getPrincipalTransferCurrency().isCheck() != null)
            element("as.select.pritransccy").check(documentationDetails.getPrincipalTransferCurrency().isCheck());
        if (documentationDetails.getCounterpartyTransferCurrency() != null && documentationDetails.getCounterpartyTransferCurrency().isCheck() != null)
            element("as.select.coutransccy").check(documentationDetails.getCounterpartyTransferCurrency().isCheck());
        if (documentationDetails.getType() != null && documentationDetails.getType().isCheck() != null)
            element("as.select.type").check(documentationDetails.getType().isCheck());
        if (documentationDetails.getCreditContingentRights() != null && documentationDetails.getCreditContingentRights().isCheck() != null)
            element("as.select.creconright").check(documentationDetails.getCreditContingentRights().isCheck());
        if (documentationDetails.getNettingIAandMtM() != null && documentationDetails.getNettingIAandMtM().isCheck() != null)
            element("as.select.netiamtm").check(documentationDetails.getNettingIAandMtM().isCheck());
        if (documentationDetails.getSegregation() != null && documentationDetails.getSegregation().isCheck() != null)
            element("as.select.segregation").check(documentationDetails.getSegregation().isCheck());
        if (documentationDetails.getFlush() != null && documentationDetails.getFlush().isCheck() != null)
            element("as.select.flush").check(documentationDetails.getFlush().isCheck());
        if (documentationDetails.getGrossCalc() != null && documentationDetails.getGrossCalc().isCheck() != null)
            element("as.select.groscal").check(documentationDetails.getGrossCalc().isCheck());
        if (documentationDetails.getInterestCalc() != null && documentationDetails.getInterestCalc().isCheck() != null)
            element("as.select.interestcal").check(documentationDetails.getInterestCalc().isCheck());
        if (documentationDetails.getFxRates() != null && documentationDetails.getFxRates().isCheck() != null)
            element("as.select.fxrates").check(documentationDetails.getFxRates().isCheck());
        if (documentationDetails.getPriceSource() != null && documentationDetails.getPriceSource().isCheck() != null)
            element("as.select.pricesour").check(documentationDetails.getPriceSource().isCheck());

//        documentationDetails.getCreationDate();
//        documentationDetails.getSignoffDate();
//        documentationDetails.getSignoffBy();
//        documentationDetails.getSignoffNotes();
//        documentationDetails.getAgreementDate();
//        documentationDetails.getReviewDate();
    }

    private void tickValuationFrequencyAndCallScheduleDetails(ValuationFrequencyAndCallScheduleDetails valuationFrequencyAndCallScheduleDetails) throws Exception {
        if (valuationFrequencyAndCallScheduleDetails.isCheck() != null)
            element("as.select.valfrecalschdetail").check(valuationFrequencyAndCallScheduleDetails.isCheck());
        if (valuationFrequencyAndCallScheduleDetails.getLegalReviewFrequency() != null && valuationFrequencyAndCallScheduleDetails.getLegalReviewFrequency().isCheck() != null)
            element("as.select.legreviewfre").check(
                    valuationFrequencyAndCallScheduleDetails.getLegalReviewFrequency().isCheck());
        if (valuationFrequencyAndCallScheduleDetails.getNotificationTime() != null && valuationFrequencyAndCallScheduleDetails.getNotificationTime().isCheck() != null)
            element("as.select.notitime").check(
                    valuationFrequencyAndCallScheduleDetails.getNotificationTime().isCheck());
        if (valuationFrequencyAndCallScheduleDetails.getResolutionTime() != null && valuationFrequencyAndCallScheduleDetails.getResolutionTime().isCheck() != null)
            element("as.select.resoltime").check(
                    valuationFrequencyAndCallScheduleDetails.getResolutionTime().isCheck());
        if (valuationFrequencyAndCallScheduleDetails.getTimeZone() != null && valuationFrequencyAndCallScheduleDetails.getTimeZone().isCheck() != null)
            element("as.select.timezone").check(valuationFrequencyAndCallScheduleDetails.getTimeZone().isCheck());
        if (valuationFrequencyAndCallScheduleDetails.getHolidayCentres() != null && valuationFrequencyAndCallScheduleDetails.getHolidayCentres().isCheck() != null)
            element("as.select.holicren").check(valuationFrequencyAndCallScheduleDetails.getHolidayCentres().isCheck());
        if (valuationFrequencyAndCallScheduleDetails.getClientReleaseRequiredSelfService() != null && valuationFrequencyAndCallScheduleDetails.isCheck() != null)
            element("as.select.clientreleasereqselfser").check(valuationFrequencyAndCallScheduleDetails.isCheck());
//        valuationFrequencyAndCallScheduleDetails.getImLegalReviewFrequency();
//        valuationFrequencyAndCallScheduleDetails.getImNotificationTime();
//        valuationFrequencyAndCallScheduleDetails.getImResolutionTime();
//        valuationFrequencyAndCallScheduleDetails.getVmLegalReviewFrequency();
//        valuationFrequencyAndCallScheduleDetails.getVmNotificationTime();
//        valuationFrequencyAndCallScheduleDetails.getVmResolutionTime();
    }

    private void tickCollateralisedPortfolioDetails(CollateralisedPortfolioDetails collateralisedPortfolioDetails) throws Exception {
        if (collateralisedPortfolioDetails.isCheck() != null)
            element("as.select.colprodetail").check(collateralisedPortfolioDetails.isCheck());
        if (collateralisedPortfolioDetails.getDefaultTradeValuationRule() != null && collateralisedPortfolioDetails.getDefaultTradeValuationRule().isCheck() != null)
            element("as.select.deftradevalrule").check(
                    collateralisedPortfolioDetails.getDefaultTradeValuationRule().isCheck());
        if (collateralisedPortfolioDetails.getTradeMappingRules() != null && collateralisedPortfolioDetails.getTradeMappingRules().isCheck() != null)
            element("as.select.trademaprule").check(collateralisedPortfolioDetails.getTradeMappingRules().isCheck());
        if (collateralisedPortfolioDetails.getAgreementIA() != null && collateralisedPortfolioDetails.getAgreementIA().isCheck() != null)
            element("as.select.argia").check(collateralisedPortfolioDetails.getAgreementIA().isCheck());
        if (collateralisedPortfolioDetails.getProduct() != null && !collateralisedPortfolioDetails.getProduct().isEmpty()) {
            for (Product product : collateralisedPortfolioDetails.getProduct()) {
                if (product.isCheck() != null)
                    element("as.select.product", product.getProductName().getRealValue()).check(product.isCheck());
            }
        }
//        collateralisedPortfolioDetails.getExposureProfile();
//        collateralisedPortfolioDetails.getTradeCalculationRule();
    }

    private void tickCollateralRules(CollateralRules collateralRules) throws Exception {
        if (collateralRules.isCheck() != null)
            element("as.select.colrules").check(collateralRules.isCheck());
        if (collateralRules.getEnableInterestCalculation() != null && collateralRules.getEnableInterestCalculation().isCheck() != null)
            element("as.select.enableinterestcal").check(collateralRules.getEnableInterestCalculation().isCheck());
        if (collateralRules.getInterestCalculationsMethod() != null && collateralRules.getInterestCalculationsMethod().isCheck() != null)
            element("as.select.interestcalmethod").check(collateralRules.getInterestCalculationsMethod().isCheck());
        if (collateralRules.getCalculationRule() != null && collateralRules.getCalculationRule().isCheck() != null)
            element("as.select.calrule").check(collateralRules.getCalculationRule().isCheck());
        if (collateralRules.getPayMethod() != null && collateralRules.getPayMethod().isCheck() != null)
            element("as.select.paymethod").check(collateralRules.getPayMethod().isCheck());
        if (collateralRules.getReceiveMethod() != null && collateralRules.getReceiveMethod().isCheck() != null)
            element("as.select.receivemethod").check(collateralRules.getReceiveMethod().isCheck());
        if (collateralRules.getInterestSettlementDate() != null && collateralRules.getInterestSettlementDate().isCheck() != null)
            element("as.select.interestsetdate").check(collateralRules.getInterestSettlementDate().isCheck());
        if (collateralRules.getAllowNegativeEffectiveRate() != null && collateralRules.getAllowNegativeEffectiveRate().isCheck() != null)
            element("as.select.allownegeffrate").check(collateralRules.getAllowNegativeEffectiveRate().isCheck());
        if (collateralRules.getConcentrationLimitTrigger() != null && collateralRules.getConcentrationLimitTrigger().isCheck() != null)
            element("as.select.conlimittrigger").check(collateralRules.getConcentrationLimitTrigger().isCheck());
        if (collateralRules.getConcentrationLimitBreachAdjustment() != null && collateralRules.getConcentrationLimitBreachAdjustment().isCheck() != null)
            element("as.select.conlimitbreadj").check(
                    collateralRules.getConcentrationLimitBreachAdjustment().isCheck());
        if (collateralRules.getRatingMethod() != null && collateralRules.getRatingMethod().isCheck() != null)
            element("as.select.ratingmethod").check(collateralRules.getRatingMethod().isCheck());
        if (collateralRules.getEligibilityRuleTemplate() != null && collateralRules.getEligibilityRuleTemplate().isCheck() != null)
            element("as.select.elirultem").check(collateralRules.getEligibilityRuleTemplate().isCheck());
        if (collateralRules.getGracePeriod() != null && collateralRules.getGracePeriod().isCheck() != null)
            element("as.select.graceperiod").check(collateralRules.getGracePeriod().isCheck());
        if (collateralRules.getEnableApplicableAgencies() != null && collateralRules.getEnableApplicableAgencies().isCheck() != null)
            element("as.select.enbleappagency").check(collateralRules.getEnableApplicableAgencies().isCheck());
        if (collateralRules.getExposureCushionRule() != null && collateralRules.getExposureCushionRule().isCheck() != null)
            element("as.select.expocushrule").check(collateralRules.getExposureCushionRule().isCheck());

//        collateralRules.getEnableVMInterestCalculation();
//        collateralRules.getEnableIMInterestCalculation();
//        collateralRules.getVminterestCalculationsMethod();
//        collateralRules.getVmCalculationRule();
//        collateralRules.getVmPayMethod();
//        collateralRules.getVmReceiveMethod();
//        collateralRules.getImInterestCalculationsMethod();
//        collateralRules.getImCalculationRule();
//        collateralRules.getImPayMethod();
//        collateralRules.getImReceiveMethod();
    }

    private void tickPrincipalAndCounterpartyEligibleForReceipt(List<PartyEligibleForReceipt> list) throws Exception {
        for (PartyEligibleForReceipt partyEligibleForReceipt : list) {
            if (partyEligibleForReceipt.isCheck() != null)
                element("as.select.partyeliforrecp", partyEligibleForReceipt.getAssetType().getRealValue()).check(
                        partyEligibleForReceipt.isCheck());
            if (partyEligibleForReceipt.getRules() != null && partyEligibleForReceipt.getRules().isCheck() != null)
                element("as.select.partyeliforrecprule", partyEligibleForReceipt.getAssetType().getRealValue()).check(
                        partyEligibleForReceipt.getRules().isCheck());
//            partyEligibleForReceipt.getAssetClass();
//            partyEligibleForReceipt.getAssetType();
//            partyEligibleForReceipt.getNotes();
//            partyEligibleForReceipt.getSettlementPeriod();
//            partyEligibleForReceipt.getDeliveryPriority();
//            partyEligibleForReceipt.getRecallPriority();
//            partyEligibleForReceipt.getContractualReuseRights();
//            partyEligibleForReceipt.getInternalPolicyPermitsReuse();
//            partyEligibleForReceipt.getValuationPercentage();
//            partyEligibleForReceipt.getValuationBasis();
//            partyEligibleForReceipt.getRate();
//            partyEligibleForReceipt.getBasisPointSpread();
//            partyEligibleForReceipt.getReinvestRate();
//            partyEligibleForReceipt.getWithholdingTaxRate();
//            partyEligibleForReceipt.getWithholdingApplied();
//            partyEligibleForReceipt.getInterestAppliedParty();
//            partyEligibleForReceipt.getConcLimitRuleSet();
//            partyEligibleForReceipt.getAutoBooking();
        }
    }

    private void tickPrincipalAndCounterpartyEligibleRules(List<PartyEligibilityRule> list) throws Exception {
        for (PartyEligibilityRule partyEligibilityRule : list) {
            if (partyEligibilityRule.isCheck() != null)
                element("as.select.partyelirule", partyEligibilityRule.getAssetType().getRealValue()).check(
                        partyEligibilityRule.isCheck());
        }
    }

    private void tickUserDefinedFieldsDetails(UserDefinedFieldsDetails userDefinedFieldsDetails) throws Exception {
        if (userDefinedFieldsDetails.isCheck() != null)
            element("as.select.userdeffiedetail").check(userDefinedFieldsDetails.isCheck());
        if (userDefinedFieldsDetails.getUserDefinedFieldsDetail() != null && !userDefinedFieldsDetails.getUserDefinedFieldsDetail().isEmpty()) {
            for (Field field : userDefinedFieldsDetails.getUserDefinedFieldsDetail()) {
                if (field.isCheck() != null)
                    element("as.select.userdeffie", field.getFieldName().getRealValue()).check(field.isCheck());
            }
        }
    }

    private void tickSettlementInformationConfiguration(SettlementInformationConfiguration settlementInformationConfiguration) throws Exception {
        if (settlementInformationConfiguration.isCheck() != null)
            element("as.select.setinfoconfig").check(settlementInformationConfiguration.isCheck());
        if (settlementInformationConfiguration.getSettlement() != null && settlementInformationConfiguration.getSettlement().isCheck() != null)
            element("as.select.settlementinfo.settlement").check(
                    settlementInformationConfiguration.getSettlement().isCheck());
        if (settlementInformationConfiguration.getSettlementBucket() != null && !settlementInformationConfiguration.getSettlementBucket().isEmpty()) {
            for (SettlementBucketInformation settlementBucketInformation : settlementInformationConfiguration.getSettlementBucket()) {
                if (settlementBucketInformation.isCheck() != null) {
                    StringBuffer xpath = new StringBuffer("");
                    if (settlementBucketInformation.getBookingType() != null) {
                        xpath.append("[//td[normalize-space(text()[2])='").append(
                                settlementBucketInformation.getBookingType().value()).append("']]");
                    }
                    if (settlementBucketInformation.getDirection() != null) {
                        xpath.append("[td='").append(settlementBucketInformation.getDirection().value()).append("']");
                    }
                    if (settlementBucketInformation.getBucket() != null) {
                        xpath.append("[td='").append(settlementBucketInformation.getBucket().getRealValue()).append(
                                "']");
                    }
                    element("as.select.settlementinfo.bucket", xpath.toString()).check(
                            settlementBucketInformation.isCheck());
                }
            }
        }
    }

    private void tickReportingDetails(ReportingDetails reportingDetails) throws Exception {
        if (reportingDetails.isCheck() != null)
            element("as.select.reportdetails").check(reportingDetails.isCheck());
        if (reportingDetails.getNotificationLetterType() != null && reportingDetails.getNotificationLetterType().isCheck() != null)
            element("as.select.notiletttype").check(reportingDetails.getNotificationLetterType().isCheck());
        if (reportingDetails.getLetterTemplateType() != null && reportingDetails.getLetterTemplateType().isCheck() != null)
            element("as.select.lettemptype").check(reportingDetails.getLetterTemplateType().isCheck());
        if (reportingDetails.getExposureStatementTemplateType() != null && reportingDetails.getExposureStatementTemplateType().isCheck() != null)
            element("as.select.expostatetemptype").check(reportingDetails.getExposureStatementTemplateType().isCheck());
        if (reportingDetails.getLetterDistributionMedium() != null && reportingDetails.getLetterDistributionMedium().get(
                0).isCheck() != null)
            element("as.select.letdistrimedi").check(reportingDetails.getLetterDistributionMedium().get(0).isCheck());
//        if(reportingDetails.getPrincipalDeliveryGroup() != null && reportingDetails.getPrincipalDeliveryGroup().isCheck() != null)
//            element("as.select.pridelivgroup").check(reportingDetails.getPrincipalDeliveryGroup().isCheck());
        if (reportingDetails.getInventoryDeliveryGroup() != null && reportingDetails.getInventoryDeliveryGroup().isCheck() != null)
            element("as.select.invendelivgroup").check(reportingDetails.getInventoryDeliveryGroup().isCheck());
        if (reportingDetails.getEnableStp() != null && reportingDetails.getEnableStp().isCheck() != null)
            element("as.select.enablestp").check(reportingDetails.getEnableStp().isCheck());
        if (reportingDetails.getStpIntraDayEvents() != null && reportingDetails.getStpIntraDayEvents().isCheck() != null)
            element("as.select.stpintradayeve").check(reportingDetails.getStpIntraDayEvents().isCheck());
        if (reportingDetails.getPayFourEyeConditions() != null && reportingDetails.getPayFourEyeConditions().isCheck() != null)
            element("as.select.payfoureyecondi").check(reportingDetails.getPayFourEyeConditions().isCheck());
        if (reportingDetails.getReceiveFourEyeConditions() != null && reportingDetails.getReceiveFourEyeConditions().isCheck() != null)
            element("as.select.recfoureyecondi").check(reportingDetails.getPayFourEyeConditions().isCheck());
//        reportingDetails.getDistributionTask();
//        reportingDetails.getLetterAttachment();
    }

    /**
     * duplicate agreement
     *
     * @param agmt
     */
    public void duplicateAgreement(Agreement agmt) throws Exception {
        enterAgreementPartySummary();
        editAgreementSummary();
        if (agmt.getDuplicateCount() != null)
            element("ap.duplicate.count").input(agmt.getDuplicateCount().getRealValue());
        if (agmt.getLinkageSet() != null) {
            for (StringType linkage : agmt.getLinkageSet())
                element("ap.linkage.set").selectByVisibleText(linkage.getRealValue());
        }
        saveAndExit();
    }

    /**
     * click save & exit
     */
    public void saveAndExit() throws Exception {
        element("ap.save.and.exit").click();
    }

    /**
     * view statement
     */
    public void viewStatement() throws Exception {
//        statement might be get locked by receiving 2 same showAgreementStatement request by the click method
//        it's ok with clickByJavaScript method
//        element("as.view.statement").click();
        waitThat().document().toBeReady();
        waitThat().jQuery().toBeInactive();
        PageHelper.d33640Workaround();
        element("as.view.statement").clickByJavaScript();
    }

    public void switchToPartyAndCounterpartyTab() throws Exception {
        element("as.partycounterpary.tab").click();
    }

    public void switchToDocumentationTab() throws Exception {
        element("as.documentation.tab").click();
    }

    public void switchToCallSceduleTab() throws Exception {
        element("as.callschedule.tab").click();
    }

    public void switchToProductTab() throws Exception {
        element("as.products.tab").click();
    }

    public void switchToCollateralTab() throws Exception {
        element("as.collateral.tab").click();
    }

    public void switchToFixedTriggerTab() throws Exception {
        element("as.fixed.trigger.tab").click();
    }

    public void switchToRuleTriggerTab() throws Exception {
        element("as.rule.trigger.tab").click();
    }

    public void switchToAdditionalFieldsTab() throws Exception {
        element("as.additional.fields.tab").click();
    }

    public void switchToSettlementTab() throws Exception {
        element("as.settlement.tab").click();
    }

    public void switchToReportingControlTab() throws Exception {
        element("as.reporting.control.tab").click();
    }

    public void switchToAgreementSummaryTab() throws Exception {
        element("as.agreement.summary.tab").clickByJavaScript();
    }


    private void assertAgreementPartyAndCounterpartySummary(AgreementSummary agreementSummary) throws Exception {
        if (agreementSummary.getPartyAndCounterpartyDetails().getPrincipal() != null) {
            validateThat("as.partycounterpary.principal").innerText().isEqualToIgnoringWhitespace(
                    "Principal - " + agreementSummary.getPartyAndCounterpartyDetails().getPrincipal().getRealValue());
        }
        if (agreementSummary.getPartyAndCounterpartyDetails().getCounterparty() != null) {
            validateThat("as.partycounterpary.counterparty").innerText().isEqualToIgnoringWhitespace(
                    "Counterparty - " + agreementSummary.getPartyAndCounterpartyDetails().getCounterparty().getRealValue());
        }
        if (agreementSummary.getPartyAndCounterpartyDetails().getCustodian() != null) {
            validateThat("as.partycounterpary.custodian").innerText().isEqualToIgnoringWhitespace(
                    "Custodian - " + agreementSummary.getPartyAndCounterpartyDetails().getCustodian().getRealValue());
        }
        if (agreementSummary.getPartyAndCounterpartyDetails().getCounterpartyOrgRegion() != null) {
            validateThat("as.partycounterpary.counterpaty.orgRegion").innerText().isEqualToIgnoringWhitespace(
                    "Counterparty OrgRegion - " + agreementSummary.getPartyAndCounterpartyDetails().getCounterpartyOrgRegion().getRealValue());
        }
        if (agreementSummary.getPartyAndCounterpartyDetails().getRegion() != null) {
            validateThat("as.partycounterpary.region").innerText().isEqualToIgnoringWhitespace(
                    "Region - " + agreementSummary.getPartyAndCounterpartyDetails().getRegion().getRealValue());
        }
        if (agreementSummary.getPartyAndCounterpartyDetails().getGroup() != null) {
            validateThat("as.partycounterpary.group").innerText().isEqualToIgnoringWhitespace(
                    "Group - " + agreementSummary.getPartyAndCounterpartyDetails().getGroup().getRealValue());
        }
        if (agreementSummary.getPartyAndCounterpartyDetails().getBusinessLine() != null) {
            validateThat("as.partycounterpary.business.line").innerText().isEqualToIgnoringWhitespace(
                    "Business Line - " + agreementSummary.getPartyAndCounterpartyDetails().getBusinessLine().getRealValue());
        }
        if (agreementSummary.getPartyAndCounterpartyDetails().getCcp() != null) {
            validateThat("as.partycounterpary.ccp").innerText().isEqualToIgnoringWhitespace(
                    "CCP - " + agreementSummary.getPartyAndCounterpartyDetails().getCcp().getRealValue());
        }
        if (agreementSummary.getPartyAndCounterpartyDetails().getConfigurableRule() != null) {
            validateThat("as.partycounterpary.configurableRule").innerText().isEqualToIgnoringWhitespace(
                    "Configurable Rule - " +
                            agreementSummary.getPartyAndCounterpartyDetails().getConfigurableRule().getRealValue());
        }
        if (agreementSummary.getPartyAndCounterpartyDetails().getFamilyAgreement() != null) {
            validateThat("as.partycounterpary.familyAgreement").innerText().isEqualToIgnoringWhitespace(
                    "Family Agreement - " +
                            agreementSummary.getPartyAndCounterpartyDetails().getFamilyAgreement().getRealValue());
        }
        if (agreementSummary.getPartyAndCounterpartyDetails().getRegulatory() != null) {
            validateThat("as.partycounterpary.regulatory").innerText().isEqualToIgnoringWhitespace(
                    "Regulatory - " + agreementSummary.getPartyAndCounterpartyDetails().getRegulatory().getRealValue());
        }
        if (agreementSummary.getPartyAndCounterpartyDetails().getAgreedJurisdiction() != null) {
            validateThat("as.partycounterpary.jurisdiction").innerText().isEqualToIgnoringWhitespace(
                    "Jurisdiction - " + agreementSummary.getPartyAndCounterpartyDetails().getAgreedJurisdiction().getRealValue());
        }
        if (agreementSummary.getPartyAndCounterpartyDetails().getOtherJurisdiction() != null &&
                agreementSummary.getPartyAndCounterpartyDetails().getOtherJurisdiction().size() > 0) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < agreementSummary.getPartyAndCounterpartyDetails().getOtherJurisdiction().size(); i++) {
                sb.append(agreementSummary.getPartyAndCounterpartyDetails().getOtherJurisdiction().get(
                        i).getRealValue()).append(",");
            }
            String sbValue = sb.toString().substring(0, sb.toString().length() - 1);
            validateThat("as.partycounterpary.regulatory").innerText().contains(sbValue);
        }
        if (agreementSummary.getPartyAndCounterpartyDetails().getMultiModel() != null) {
            validateThat("as.partycounterpary.multi.model").innerText().isEqualToIgnoringWhitespace(
                    "Multi-Model - " + agreementSummary.getPartyAndCounterpartyDetails().getMultiModel().getRealValue());
        }
        if (agreementSummary.getPartyAndCounterpartyDetails().getUmbrellaAgreement() != null) {
            validateThat("as.partycounterpary.umbrella.agreement").innerText().isEqualToIgnoringWhitespace(
                    "Umbrella Agreement - " + agreementSummary.getPartyAndCounterpartyDetails().getUmbrellaAgreement().getRealValue());
        }
        if (agreementSummary.getPartyAndCounterpartyDetails().getUmbrellaRule() != null) {
            validateThat("as.partycounterpary.umbrella.rule").innerText().isEqualToIgnoringWhitespace(
                    "Umbrella Rule - " + agreementSummary.getPartyAndCounterpartyDetails().getUmbrellaRule().getRealValue());
        }
        if (agreementSummary.getPartyAndCounterpartyDetails().getIndividualOrders() != null)
            validateThat("as.partycounterpary.individual.orders").innerText().isEqualToIgnoringCase(
                    "Individual Orders - " + agreementSummary.getPartyAndCounterpartyDetails().getIndividualOrders().getRealValue()
            );
        if (agreementSummary.getPartyAndCounterpartyDetails().getAgreementDescription() != null) {
            validateThat("as.partycounterpary.agreement.desc").innerText().isEqualToIgnoringWhitespace(
                    "Agreement Description - " + agreementSummary.getPartyAndCounterpartyDetails().getAgreementDescription().getRealValue());
        }
        if (agreementSummary.getPartyAndCounterpartyDetails().getStatus() != null) {
            validateThat("as.partycounterpary.status").innerText().isEqualToIgnoringWhitespace(
                    "Status - " + agreementSummary.getPartyAndCounterpartyDetails().getStatus().getRealValue());
        }
        if (agreementSummary.getPartyAndCounterpartyDetails().getCategory() != null) {
            validateThat("as.partycounterpary.category").innerText().isEqualToIgnoringWhitespace(
                    "Category - " + agreementSummary.getPartyAndCounterpartyDetails().getCategory().getRealValue());
        }
        if (agreementSummary.getPartyAndCounterpartyDetails().getCollateralManager() != null) {
            validateThat("as.partycounterpary.collateral.manager").innerText().isEqualToIgnoringWhitespace(
                    "Collateral Manager - " + agreementSummary.getPartyAndCounterpartyDetails().getCollateralManager().getRealValue());
        }
        if (agreementSummary.getPartyAndCounterpartyDetails().getAgreementName() != null) {
            validateThat("as.partycounterpary.agreement.name").innerText().isEqualToIgnoringWhitespace(
                    "Agreement Name - " + agreementSummary.getPartyAndCounterpartyDetails().getAgreementName().getRealValue());
        }
        if (agreementSummary.getPartyAndCounterpartyDetails().getExternalId() != null) {
            validateThat("as.partycounterpary.external.id").innerText().isEqualToIgnoringWhitespace(
                    "External Id - " + agreementSummary.getPartyAndCounterpartyDetails().getExternalId().getRealValue());
        }
        if (agreementSummary.getPartyAndCounterpartyDetails().getNotes() != null) {
            validateThat("as.partycounterpary.notes").innerText().isEqualToIgnoringWhitespace(
                    "Notes - " + agreementSummary.getPartyAndCounterpartyDetails().getNotes().getRealValue());
        }
        if (agreementSummary.getPartyAndCounterpartyDetails().getRank() != null) {
            validateThat("as.partycounterpary.rank").innerText().isEqualToIgnoringWhitespace(
                    "Rank - " + agreementSummary.getPartyAndCounterpartyDetails().getRank().getRealValue());
        }
        if (agreementSummary.getPartyAndCounterpartyDetails().getOwner() != null) {
            validateThat("as.partycounterpary.owner").innerText().isEqualToIgnoringWhitespace(
                    "Owner - " + agreementSummary.getPartyAndCounterpartyDetails().getOwner().getRealValue());
        }
        if (agreementSummary.getPartyAndCounterpartyDetails().getLastmodifiedBy() != null) {
            validateThat("as.partycounterpary.last.modified.by").innerText().isEqualToIgnoringWhitespace(
                    "Last modified By - " + agreementSummary.getPartyAndCounterpartyDetails().getLastmodifiedBy().getRealValue());
        }
        if (agreementSummary.getPartyAndCounterpartyDetails().getLinkageSet() != null && agreementSummary.getPartyAndCounterpartyDetails().getLinkageSet().size() > 0) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < agreementSummary.getPartyAndCounterpartyDetails().getLinkageSet().size(); i++) {
                sb.append(
                        agreementSummary.getPartyAndCounterpartyDetails().getLinkageSet().get(i).getRealValue()).append(
                        ", ");
            }
            String sbValue = sb.toString().trim();
            String realValue = sbValue.substring(0, sbValue.length() - 1);
            validateThat("as.partycounterpary.linkage.set").innerText().isEqualToIgnoringWhitespace(
                    "Linkage Set - " + realValue);

        }
    }

    public void assertAgreementDocumentationDetails(AgreementSummary agreementSummary) throws Exception {
        DecimalFormat format = new DecimalFormat();
        if (agreementSummary.getDocumentationDetails().getPrincipalBranches() != null) {
            StringBuffer sb = new StringBuffer();
            sb.append("Principal Branches");
            if (agreementSummary.getDocumentationDetails().getPrincipalBranches().getBranchName() != null && agreementSummary.getDocumentationDetails().getPrincipalBranches().getBranchName().size() > 0) {
                for (StringType branchName : agreementSummary.getDocumentationDetails().getPrincipalBranches().getBranchName()) {
                    sb.append("\n" + "    ").append(branchName.getRealValue());
                }
                //validateThat("as.documentation.principal.branches").allInnerTexts().contains(sb.toString());
                validateThat("as.documentation.principal.branches").innerText().isEqualToIgnoringWhitespace(
                        sb.toString());
            }
        }
        if (agreementSummary.getDocumentationDetails().getPrincipalContact() != null) {
            validateThat("as.documentation.principal.contact").innerText().isEqualToIgnoringWhitespace(
                    "Principal contact - " + agreementSummary.getDocumentationDetails().getPrincipalContact().getRealValue());
        }
        if (agreementSummary.getDocumentationDetails().getPrincipalCCEmail() != null) {
            if (agreementSummary.getDocumentationDetails().getPrincipalCCEmail().getContact() != null && agreementSummary.getDocumentationDetails().getPrincipalCCEmail().getContact().size() > 0) {
                StringBuffer sb = new StringBuffer();
                sb.append("Principal CC E-mail");
                for (StringType contact : agreementSummary.getDocumentationDetails().getPrincipalCCEmail().getContact()) {
                    sb.append("\n" + "    ").append(contact.getRealValue());
                }
                validateThat("as.documentation.principal.cc.email").innerText().isEqualToIgnoringWhitespace(
                        sb.toString());
            }
        }
        if (agreementSummary.getDocumentationDetails().getPrincipalInterestcontact() != null) {
            validateThat("as.documentation.principal.interest.contact").innerText().isEqualToIgnoringWhitespace(
                    "Principal Interest contact - " + agreementSummary.getDocumentationDetails().getPrincipalInterestcontact().getRealValue());
        }
        if (agreementSummary.getDocumentationDetails().getPrincipalInterestCCEmail() != null) {
            if (agreementSummary.getDocumentationDetails().getPrincipalInterestCCEmail().getContact() != null && agreementSummary.getDocumentationDetails().getPrincipalInterestCCEmail().getContact().size() > 0) {
                StringBuffer sb = new StringBuffer();
                sb.append("Principal Interest CC E-mail");
                for (StringType contact : agreementSummary.getDocumentationDetails().getPrincipalInterestCCEmail().getContact()) {
                    sb.append("\n" + "    ").append(contact.getRealValue());
                }
                validateThat("as.documentation.principal.interest.cc.email").innerText().isEqualToIgnoringWhitespace(
                        sb.toString());
            }
        }
        //counterparty info
        if (agreementSummary.getDocumentationDetails().getCounterpartyBranches() != null) {
            if (agreementSummary.getDocumentationDetails().getCounterpartyBranches().getBranchName() != null && agreementSummary.getDocumentationDetails().getCounterpartyBranches().getBranchName().size() > 0) {
                StringBuffer sb = new StringBuffer();
                sb.append("Counterparty Branches");
                for (StringType branchName : agreementSummary.getDocumentationDetails().getCounterpartyBranches().getBranchName()) {
                    sb.append("\n" + "    ").append(branchName.getRealValue());
                }
                //validateThat("as.documentation.counterparty.branches").allInnerTexts().contains("Counterparty Branches"+"\n"+"    "+branchName.getRealValue());
                validateThat("as.documentation.counterparty.branches").innerText().isEqualToIgnoringWhitespace(
                        sb.toString());

            }
        }
        if (agreementSummary.getDocumentationDetails().getCounterpartyContact() != null) {
            validateThat("as.documentation.counterparty.contact").innerText().isEqualToIgnoringWhitespace(
                    "Counterparty contact - " + agreementSummary.getDocumentationDetails().getCounterpartyContact().getRealValue());
        }
        if (agreementSummary.getDocumentationDetails().getCounterpartyCCEmail() != null) {
            if (agreementSummary.getDocumentationDetails().getCounterpartyCCEmail().getContact() != null && agreementSummary.getDocumentationDetails().getCounterpartyCCEmail().getContact().size() > 0) {
                StringBuffer sb = new StringBuffer();
                sb.append("Counterparty CC E-mail");
                for (StringType contact : agreementSummary.getDocumentationDetails().getCounterpartyCCEmail().getContact()) {
                    sb.append("\n" + "    ").append(contact.getRealValue());
                }
                validateThat("as.documentation.counterparty.cc.email").innerText().isEqualToIgnoringWhitespace(
                        sb.toString());
            }
        }
        if (agreementSummary.getDocumentationDetails().getCounterpartyInterestcontact() != null) {
            validateThat("as.documentation.counterparty.interest.contact").innerText().isEqualToIgnoringWhitespace(
                    "Counterparty Interest contact - " + agreementSummary.getDocumentationDetails().getCounterpartyInterestcontact().getRealValue());
        }
        if (agreementSummary.getDocumentationDetails().getCounterpartyInterestCCEmail() != null) {
            if (agreementSummary.getDocumentationDetails().getCounterpartyInterestCCEmail().getContact() != null && agreementSummary.getDocumentationDetails().getCounterpartyInterestCCEmail().getContact().size() > 0) {
                StringBuffer sb = new StringBuffer();
                sb.append("Counterparty Interest CC E-mail");
                for (StringType contact : agreementSummary.getDocumentationDetails().getCounterpartyInterestCCEmail().getContact()) {
                    sb.append("\n" + "    ").append(contact.getRealValue());
                }
                validateThat("as.documentation.counterparty.interest.cc.email").innerText().isEqualToIgnoringWhitespace(
                        sb.toString());
            }
        }
        if (agreementSummary.getDocumentationDetails().getSendConfirmationLetterTo() != null) {
            if (agreementSummary.getDocumentationDetails().getSendConfirmationLetterTo().getCounterpary() != null) {
                validateThat("as.documentation.send.confirmation.letter.to").innerText().contains(
                        "Counterparty(" + agreementSummary.getDocumentationDetails().getSendConfirmationLetterTo().getCounterpary().value() + ")");
            }
            if (agreementSummary.getDocumentationDetails().getSendConfirmationLetterTo().getCustodian() != null) {
                validateThat("as.documentation.send.confirmation.letter.to").innerText().contains(
                        "Custodian(" + agreementSummary.getDocumentationDetails().getSendConfirmationLetterTo().getCustodian().value() + ")");
            }
            if (agreementSummary.getDocumentationDetails().getSendConfirmationLetterTo().getCustodianContact() != null) {
                validateThat("as.documentation.send.confirmation.letter.to").innerText().contains(
                        "Custodian contact - " + agreementSummary.getDocumentationDetails().getSendConfirmationLetterTo().getCustodianContact().getRealValue());
            }
            if (agreementSummary.getDocumentationDetails().getSendConfirmationLetterTo().getCustodianCCEmail() != null) {
                if (agreementSummary.getDocumentationDetails().getSendConfirmationLetterTo().getCustodianCCEmail().getContact() != null && agreementSummary.getDocumentationDetails().getSendConfirmationLetterTo().getCustodianCCEmail().getContact().size() > 0) {
                    StringBuffer value = new StringBuffer();
                    for (StringType contact : agreementSummary.getDocumentationDetails().getSendConfirmationLetterTo().getCustodianCCEmail().getContact()) {
                        value.append(contact.getRealValue()).append(",");
                    }
                    value.deleteCharAt(value.length() - 1);
                    validateThat("as.documentation.send.confirmation.letter.to").innerText().contains(
                            "Custodian CC Email - " + value.toString());
                }
            }
        }
        if (agreementSummary.getDocumentationDetails().getPrincipalNAV() != null) {
            if (agreementSummary.getDocumentationDetails().getPrincipalNAV().getAmount() != null) {
                format.applyPattern("###,###,##0.00");
                validateThat("as.documentation.principal.nav").innerText().contains("Principal NAV - "
                        + format.format(Float.parseFloat(
                        agreementSummary.getDocumentationDetails().getPrincipalNAV().getAmount().getRealValue()))
                );
            }
            if (agreementSummary.getDocumentationDetails().getPrincipalNAV().getCurrency() != null) {
                validateThat("as.documentation.principal.nav").innerText().contains(
                        agreementSummary.getDocumentationDetails().getPrincipalNAV().getCurrency().getRealValue());
            }
        }
        if (agreementSummary.getDocumentationDetails().getPrincipalValuationAgent() != null) {
            validateThat("as.documentation.valuation.agent.principal").innerText().isEqualToIgnoringWhitespace(
                    "Valuation Agent (Principal) - " + agreementSummary.getDocumentationDetails().getPrincipalValuationAgent().getRealValue());
        }
        if (agreementSummary.getDocumentationDetails().getCounterpartyNAV() != null) {
            if (agreementSummary.getDocumentationDetails().getCounterpartyNAV().getAmount() != null) {
                format.applyPattern("###,###,##0.00");
                validateThat("as.documentation.counterparty.nav").innerText().contains("Counterparty NAV - "
                        + format.format(Float.parseFloat(
                        agreementSummary.getDocumentationDetails().getCounterpartyNAV().getAmount().getRealValue()))
                );
            }
            if (agreementSummary.getDocumentationDetails().getCounterpartyNAV().getCurrency() != null) {
                validateThat("as.documentation.counterparty.nav").innerText().contains(
                        agreementSummary.getDocumentationDetails().getCounterpartyNAV().getCurrency().getRealValue());
            }
        }
        if (agreementSummary.getDocumentationDetails().getCounterpartyValuationAgent() != null) {
            validateThat("as.documentation.valuation.agent.counterparty").innerText().isEqualToIgnoringWhitespace(
                    "Valuation Agent (Cpty) - " + agreementSummary.getDocumentationDetails().getCounterpartyValuationAgent().getRealValue());
        }
        if (agreementSummary.getDocumentationDetails().getMasterDocumentation() != null) {
            validateThat("as.documentation.master.documentation").innerText().isEqualToIgnoringWhitespace(
                    "Master Documentation - " + agreementSummary.getDocumentationDetails().getMasterDocumentation().getRealValue());
        }
        if (agreementSummary.getDocumentationDetails().getCreditSupportDocumentation() != null) {
            validateThat("as.documentation.credit.support.documentation").innerText().isEqualToIgnoringWhitespace(
                    "Credit Support Documentation - " + agreementSummary.getDocumentationDetails().getCreditSupportDocumentation().getRealValue());
        }
        if (agreementSummary.getDocumentationDetails().getAnnexes() != null) {
            validateThat("").innerText().isEqualToIgnoringWhitespace(
                    "Annexes - " + agreementSummary.getDocumentationDetails().getAnnexes().getRealValue());
        }
        if (agreementSummary.getDocumentationDetails().getCreationDate() != null) {
            validateThat("as.documentation.creation.date").innerText().startsWith(
                    "Creation Date - " + agreementSummary.getDocumentationDetails().getCreationDate().getRealValue());
        }
        if (agreementSummary.getDocumentationDetails().getSignoffDate() != null) {
            validateThat("as.documentation.signoff.date").innerText().isEqualToIgnoringWhitespace(
                    "Signoff Date - " + agreementSummary.getDocumentationDetails().getSignoffDate().getRealValue());
        }
        if (agreementSummary.getDocumentationDetails().getSignoffBy() != null) {
            validateThat("as.documentation.signoff.by").innerText().isEqualToIgnoringWhitespace(
                    "Signoff By - " + agreementSummary.getDocumentationDetails().getSignoffBy().getRealValue());
        }
        if (agreementSummary.getDocumentationDetails().getSignoffNotes() != null) {
            validateThat("as.documentation.signoff.notes").innerText().isEqualToIgnoringWhitespace(
                    "Signoff Notes - " + agreementSummary.getDocumentationDetails().getSignoffNotes().getRealValue());
        }
        if (agreementSummary.getDocumentationDetails().getAgreementDate() != null) {
            validateThat("as.documentation.agreement.date").innerText().isEqualToIgnoringWhitespace(
                    "Agreement Date - " + agreementSummary.getDocumentationDetails().getAgreementDate().getRealValue());
        }
        if (agreementSummary.getDocumentationDetails().getReviewDate() != null) {
            validateThat("as.documentation.review.date").innerText().startsWith(
                    "Review Date - " + agreementSummary.getDocumentationDetails().getReviewDate().getRealValue());
        }
        if (agreementSummary.getDocumentationDetails().getReciprocity() != null) {
            if (agreementSummary.getDocumentationDetails().getReciprocity().getReciprocity() != null) {
                validateThat("as.documentation.reciprocity").innerText().contains(
                        "Reciprocity - " + agreementSummary.getDocumentationDetails().getReciprocity().getReciprocity().getValue());
            }
            if (agreementSummary.getDocumentationDetails().getReciprocity().getRehypothecationRights() != null) {
                validateThat("as.documentation.reciprocity").innerText().contains(
                        "Rehypothecation Rights - " + agreementSummary.getDocumentationDetails().getReciprocity().getRehypothecationRights().value().toUpperCase());
            }
            if (agreementSummary.getDocumentationDetails().getReciprocity().getRehypothecation() != null) {
                validateThat("as.documentation.reciprocity").innerText().contains(
                        agreementSummary.getDocumentationDetails().getReciprocity().getRehypothecation().getRealValue());
            }
        }
        if (agreementSummary.getDocumentationDetails().getBaseCurrency() != null) {
            validateThat("as.documentation.base.currecy").innerText().isEqualToIgnoringWhitespace(
                    "Base Currency - " + agreementSummary.getDocumentationDetails().getBaseCurrency().getRealValue());
        }
        if (agreementSummary.getDocumentationDetails().getPrincipalTransferCurrency() != null) {
            validateThat("as.documentation.principal.transfer.currecy").innerText().isEqualToIgnoringWhitespace(
                    "Principal Transfer Currency - " + agreementSummary.getDocumentationDetails().getPrincipalTransferCurrency().getRealValue());
        }
        if (agreementSummary.getDocumentationDetails().getCounterpartyTransferCurrency() != null) {
            validateThat("as.documentation.counterparty.transfer.currecy").innerText().isEqualToIgnoringWhitespace(
                    "Counterparty Transfer Currency - " + agreementSummary.getDocumentationDetails().getCounterpartyTransferCurrency().getRealValue());
        }
        if (agreementSummary.getDocumentationDetails().getVmPrincipalTransferCurrency() != null) {
            validateThat("as.documentation.principal.transfer.currecy").innerText().isEqualToIgnoringWhitespace(
                    "Principal Transfer Currency (VM) - " + agreementSummary.getDocumentationDetails().getVmPrincipalTransferCurrency().getRealValue());
        }
        if (agreementSummary.getDocumentationDetails().getVmCounterpartyTransferCurrency() != null) {
            validateThat("as.documentation.counterparty.transfer.currecy").innerText().isEqualToIgnoringWhitespace(
                    "Counterparty Transfer Currency (VM) - " + agreementSummary.getDocumentationDetails().getVmCounterpartyTransferCurrency().getRealValue());
        }
        if (agreementSummary.getDocumentationDetails().getPrincipalTerminationCurrency() != null) {
            if (agreementSummary.getDocumentationDetails().getPrincipalTerminationCurrency().isApply() != null && !agreementSummary.getDocumentationDetails().getPrincipalTerminationCurrency().isApply()) {
                validateThat("as.documentation.principal.termination.currecy").displayed().isFalse();
            } else {
                validateThat("as.documentation.principal.termination.currecy").innerText()
                        .isEqualToIgnoringWhitespace("Principal Termination Currency (IM) - "
                                + agreementSummary.getDocumentationDetails().getPrincipalTerminationCurrency().getRealValue());
            }
        }
        if (agreementSummary.getDocumentationDetails().getCounterpartyTerminationCurrency() != null) {
            if (agreementSummary.getDocumentationDetails().getCounterpartyTerminationCurrency().isApply() != null && !agreementSummary.getDocumentationDetails().getCounterpartyTerminationCurrency().isApply()) {
                validateThat("as.documentation.counterparty.termination.currecy").displayed().isFalse();
            } else {
                validateThat("as.documentation.counterparty.termination.currecy").innerText()
                        .isEqualToIgnoringWhitespace("Counterparty Termination Currency (IM) - "
                                + agreementSummary.getDocumentationDetails().getCounterpartyTerminationCurrency().getRealValue());
            }
        }
        if (agreementSummary.getDocumentationDetails().getType() != null) {
            validateThat("as.documentation.type").innerText().isEqualToIgnoringWhitespace(
                    "Type - " + agreementSummary.getDocumentationDetails().getType().getRealValue());
        }
        if (agreementSummary.getDocumentationDetails().getCreditContingentRights() != null) {
            validateThat("as.documentation.credit.contingent.reuse.rights").innerText().isEqualToIgnoringWhitespace(
                    "Credit Contingent Re-use Rights - " + agreementSummary.getDocumentationDetails().getCreditContingentRights().getRealValue());
        }
        if (agreementSummary.getDocumentationDetails().getNettingIAandMtM() != null) {
            validateThat("as.documentation.netting.ia.and.mtm").innerText().isEqualToIgnoringWhitespace(
                    "Netting IA and MtM - " + agreementSummary.getDocumentationDetails().getNettingIAandMtM().getRealValue());
        }
        if (agreementSummary.getDocumentationDetails().getSegregation() != null) {
            validateThat("as.documentation.segregation.section").allInnerTexts().contains(
                    "Segregation - " + agreementSummary.getDocumentationDetails().getSegregation().getRealValue());
            if (agreementSummary.getDocumentationDetails().getPrincipalVMSeg() != null)
                validateThat("as.documentation.segregation.section").allInnerTexts().contains(
                        "Principal VM Seg - " + agreementSummary.getDocumentationDetails().getPrincipalVMSeg().getRealValue());
            if (agreementSummary.getDocumentationDetails().getPrinVMRqv() != null)
                validateThat("as.documentation.segregation.section").allInnerTexts().contains(
                        "Prin VM RQV - " + agreementSummary.getDocumentationDetails().getPrinVMRqv().getRealValue());
            if (agreementSummary.getDocumentationDetails().getPrincipalIMSeg() != null)
                validateThat("as.documentation.segregation.section").allInnerTexts().contains(
                        "Principal IM Seg - " + agreementSummary.getDocumentationDetails().getPrincipalIMSeg().getRealValue());
            if (agreementSummary.getDocumentationDetails().getPrinIMRqv() != null)
                validateThat("as.documentation.segregation.section").allInnerTexts().contains(
                        "Prin IM RQV - " + agreementSummary.getDocumentationDetails().getPrinIMRqv().getRealValue());
            if (agreementSummary.getDocumentationDetails().getCounterpartyVMSeg() != null)
                validateThat("as.documentation.segregation.section").allInnerTexts().contains(
                        "Counterparty VM Seg - " + agreementSummary.getDocumentationDetails().getCounterpartyVMSeg().getRealValue());
            if (agreementSummary.getDocumentationDetails().getCptyVMRqv() != null)
                validateThat("as.documentation.segregation.section").allInnerTexts().contains(
                        "Cpty VM RQV - " + agreementSummary.getDocumentationDetails().getCptyVMRqv().getRealValue());
            if (agreementSummary.getDocumentationDetails().getCounterpartyIMSeg() != null)
                validateThat("as.documentation.segregation.section").allInnerTexts().contains(
                        "Counterparty IM Seg - " + agreementSummary.getDocumentationDetails().getCounterpartyIMSeg().getRealValue());
            if (agreementSummary.getDocumentationDetails().getCptyIMRqv() != null)
                validateThat("as.documentation.segregation.section").allInnerTexts().contains(
                        "Cpty IM RQV - " + agreementSummary.getDocumentationDetails().getCptyIMRqv().getRealValue());
        }

        if (agreementSummary.getDocumentationDetails().getNetVMIMEventsInSameDirection() != null)
            validateThat("as.documentation.event.same.direction").innerText().isEqualToIgnoringWhitespace(
                    "Net VM/IM Events in same direction - " + agreementSummary.getDocumentationDetails().getNetVMIMEventsInSameDirection().getRealValue());

        if (agreementSummary.getDocumentationDetails().getNetVMIMInterest() != null)
            validateThat("as.documentation.net.vm.im.interest").innerText().isEqualToIgnoringWhitespace(
                    "Net VM/IM Interest - " + agreementSummary.getDocumentationDetails().getNetVMIMInterest().getRealValue());

        if (agreementSummary.getDocumentationDetails().getFlush() != null) {
            validateThat("as.documentation.flush").innerText().isEqualToIgnoringWhitespace(
                    "Flush - " + agreementSummary.getDocumentationDetails().getFlush().getRealValue());
        }
        if (agreementSummary.getDocumentationDetails().getTsa() != null) {
            validateThat("as.documentation.tsa").innerText().contains(
                    "Cashflow - " + agreementSummary.getDocumentationDetails().getTsa().getRealValue());
        }
        if (agreementSummary.getDocumentationDetails().getTsaRule() != null) {
            validateThat("as.documentation.tsa").innerText().contains(
                    "Cashflow Rule - " + agreementSummary.getDocumentationDetails().getTsaRule().getRealValue());
        }
        if (agreementSummary.getDocumentationDetails().getEventAggregation() != null) {
            validateThat("as.documentation.event.aggregation").innerText().isEqualToIgnoringWhitespace(
                    "Event Aggregation - " + agreementSummary.getDocumentationDetails().getEventAggregation().getRealValue());
        }
        if (agreementSummary.getDocumentationDetails().getGrossCalc() != null) {
            validateThat("as.documentation.gross.calc").innerText().isEqualToIgnoringWhitespace(
                    "Gross Calc(ITM vs OTM) - " + agreementSummary.getDocumentationDetails().getGrossCalc().getRealValue());

        }
        if (agreementSummary.getDocumentationDetails().getInterestCalc() != null) {
            validateThat("as.documentation.interest.calc").innerText().isEqualToIgnoringWhitespace(
                    "Interest Calc - " + agreementSummary.getDocumentationDetails().getInterestCalc().getRealValue());
        }
        if (agreementSummary.getDocumentationDetails().getInterestLetter() != null) {
            validateThat("as.documentation.interest.letter").innerText().isEqualToIgnoringWhitespace(
                    "Interest Letter - " + agreementSummary.getDocumentationDetails().getInterestLetter().getRealValue());
        }
        if (agreementSummary.getDocumentationDetails().getFxRates() != null) {
            validateThat("as.documentation.fx.rates").innerText().isEqualToIgnoringWhitespace(
                    "FX Rates - " + agreementSummary.getDocumentationDetails().getFxRates().getRealValue());
        }
        if (agreementSummary.getDocumentationDetails().getCalcMtm() != null) {
            validateThat("as.documentation.calc.mtm").innerText().isEqualToIgnoringWhitespace(
                    "Calc MTM - " + agreementSummary.getDocumentationDetails().getCalcMtm().getRealValue());
        }
        if (agreementSummary.getDocumentationDetails().getPriceSource() != null) {
            validateThat("as.documentation.price.source").innerText().isEqualToIgnoringWhitespace(
                    "Price Source - " + agreementSummary.getDocumentationDetails().getPriceSource().getRealValue());
        }
    }


    public void agreementSummaryAssertion(AgreementSummary agreementSummary) throws Exception {
        //party and counterparty tab
        if (agreementSummary.getPartyAndCounterpartyDetails() != null) {
            assertAgreementPartyAndCounterpartySummary(agreementSummary);
        }
        //documentation tab
        if (agreementSummary.getDocumentationDetails() != null) {
            assertAgreementDocumentationDetails(agreementSummary);
        }
        //call schedule tab
        if (agreementSummary.getValuationFrequencyAndCallScheduleDetails() != null) {
            assertAgreementCallScheduleTab(agreementSummary.getValuationFrequencyAndCallScheduleDetails());
        }
        //products tab
        if (agreementSummary.getCollateralisedPortfolioDetails() != null) {
            assertAgreementProductsTab(agreementSummary.getCollateralisedPortfolioDetails());
        }
        //collateral tab
        if (agreementSummary.getCollateralRules() != null || agreementSummary.getPrincipalAndCounterpartyEligibleForReceipt() != null
                || agreementSummary.getPrincipalAndCounterpartyEligibilityRules() != null
                || agreementSummary.getPrincipalAndCounterpartyHaircutAdjustmentRules() != null) {
            if (agreementSummary.getCollateralRules() != null) {
                assertAgreeementCollateralOfCollateralRules(agreementSummary.getCollateralRules());
            }
            if (agreementSummary.getPrincipalAndCounterpartyEligibleForReceipt() != null && agreementSummary.getPrincipalAndCounterpartyEligibleForReceipt().size() > 0) {
                assertAgreementPrincipalAndCounterpartyCollateralEligibleForReceipts(
                        agreementSummary.getPrincipalAndCounterpartyEligibleForReceipt());
            }
            if (agreementSummary.getPrincipalAndCounterpartyEligibilityRules() != null && agreementSummary.getPrincipalAndCounterpartyEligibilityRules().size() > 0) {
                assertAgreeementPrincipalAndCounterpartyEligibilityRules(
                        agreementSummary.getPrincipalAndCounterpartyEligibilityRules());
            }
            if (agreementSummary.getPrincipalAndCounterpartyHaircutAdjustmentRules() != null && !agreementSummary.getPrincipalAndCounterpartyHaircutAdjustmentRules().isEmpty()) {
                assertAgreementPrincipalAndCounterpartyHairutAdjustmentRules(
                        agreementSummary.getPrincipalAndCounterpartyHaircutAdjustmentRules());
            }
        }
        //fixed trigger tab
        if (agreementSummary.getCollateralThresholdAndMinimumTransferDetails() != null) {
            if (agreementSummary.getCollateralThresholdAndMinimumTransferDetails().isApplyOrganisationThreshold() != null)
                validateThat("as.fixed.trigger.apply.organisation.threshold").innerText().isEqualToIgnoringWhitespace(
                        "Apply Organisation Threshold - "
                                + agreementSummary.getCollateralThresholdAndMinimumTransferDetails().isApplyOrganisationThreshold().toString());
            if (agreementSummary.getCollateralThresholdAndMinimumTransferDetails().getRounding() != null)
                assertAgreementFixedTriggerRounding(
                        agreementSummary.getCollateralThresholdAndMinimumTransferDetails().getRounding());
            if (agreementSummary.getCollateralThresholdAndMinimumTransferDetails().getImRounding() != null)
                assertAgreementFixedTriggerIMRounding(
                        agreementSummary.getCollateralThresholdAndMinimumTransferDetails().getImRounding());
            if (agreementSummary.getCollateralThresholdAndMinimumTransferDetails().getPrincipalFixedValue() != null)
                assertAgreementFixedTriggerPrincipalFixedValue(
                        agreementSummary.getCollateralThresholdAndMinimumTransferDetails().getPrincipalFixedValue());
            if (agreementSummary.getCollateralThresholdAndMinimumTransferDetails().getImPrincipalFixedValue() != null)
                assertAgreementFixedTriggerIMPrincipalFixedValue(
                        agreementSummary.getCollateralThresholdAndMinimumTransferDetails().getImPrincipalFixedValue());
            if (agreementSummary.getCollateralThresholdAndMinimumTransferDetails().getCounterpartyFixedValue() != null)
                assertAgreementFixedTriggerCounterpartyFixedValue(
                        agreementSummary.getCollateralThresholdAndMinimumTransferDetails().getCounterpartyFixedValue());
            if (agreementSummary.getCollateralThresholdAndMinimumTransferDetails().getImCounterpartyFixedValue() != null)
                assertAgreementFixedTriggerIMCounterpartyFixedValue(
                        agreementSummary.getCollateralThresholdAndMinimumTransferDetails().getImCounterpartyFixedValue());
            if (agreementSummary.getCollateralThresholdAndMinimumTransferDetails().isFinra4210Adjustment() != null)
                validateThat("as.fixed.trigger.finra.4210").innerText().isEqualToIgnoringCase(
                        "FINRA 4210 Adjustment - " + agreementSummary.getCollateralThresholdAndMinimumTransferDetails().isFinra4210Adjustment());
        }
        //rule trigger
        if (agreementSummary.getPrincipalRatingBasedContingentValue() != null || agreementSummary.getCounterpartyRatingBasedContingentValue() != null ||
                agreementSummary.getPrincipalTimeBasedContingentValue() != null || agreementSummary.getCounterpartyRatingBasedContingentValue() != null ||
                agreementSummary.getPrincipalNavBasedContingentValue() != null || agreementSummary.getCounterpartyNavBasedContingentValue() != null) {
            if (agreementSummary.getPrincipalRatingBasedContingentValue() != null) {
                assertAgreementPrincipalRatingBasedContingentDetails(
                        agreementSummary.getPrincipalRatingBasedContingentValue());
            }
            if (agreementSummary.getCounterpartyRatingBasedContingentValue() != null) {
                assertAgreementCounterpartyRatingBasedContingentDetails(
                        agreementSummary.getCounterpartyRatingBasedContingentValue());
            }
            if (agreementSummary.getPrincipalTimeBasedContingentValue() != null) {
                assertAgreementPincipalTimeBasedContingentDetail(
                        agreementSummary.getPrincipalTimeBasedContingentValue());
            }
            if (agreementSummary.getCounterpartyTimeBasedContingentValue() != null) {
                assertAgreementCounterpartyTimeBasedContingentDetail(
                        agreementSummary.getCounterpartyTimeBasedContingentValue());
            }
            if (agreementSummary.getPrincipalNavBasedContingentValue() != null) {
                assertAgreementPrincipalNavBasedContingentDetail(
                        agreementSummary.getPrincipalNavBasedContingentValue());
            }
            if (agreementSummary.getCounterpartyNavBasedContingentValue() != null) {
                assertAgreementCounterpartyNavBasedContingentDetail(
                        agreementSummary.getCounterpartyNavBasedContingentValue());
            }
        }
        //additional fields tab
        if (agreementSummary.getUserDefinedFieldsDetails() != null) {
            assertAgreementAdditionalFieldsDetails(agreementSummary);
        }
        //settlement tab
        if (agreementSummary.getSettlementInformationConfiguration() != null) {
            assertAgreementSettlementDetails(agreementSummary);
        }
        //reporting control tab
        if (agreementSummary.getReportingDetails() != null) {
            assertAgreementReportingControlDetail(agreementSummary);
        }
        //messaging tab
        if (agreementSummary.getMessagingDetails() != null)
            assertAgreementMessgingDetail(agreementSummary.getMessagingDetails());
    }

    private void assertAgreementMessgingDetail(MessagingDetails messagingDetails) throws Exception {
        DecimalFormat format = new DecimalFormat();
        if (messagingDetails.getInterfaceSystem() != null)
            validateThat("as.messaging.interface.system").innerText().isEqualToIgnoringCase(
                    "Interface System : " + messagingDetails.getInterfaceSystem().getRealValue());
        if (messagingDetails.getMessageID() != null) {
            MessagingDetails.MessageID messageID = messagingDetails.getMessageID();
            if (messageID.getIDType() != null)
                validateThat("as.messaging.message.id.type").innerText().contains(messageID.getIDType().getRealValue());
            if (messageID.getID() != null)
                validateThat("as.messaging.message.id.type").innerText().contains(messageID.getID().getRealValue());
        }
        if (messagingDetails.getMessageResponseToleranceAmount() != null) {
            format.applyPattern("###,###,###,##0.00");
            validateThat("as.messaging.message.response.tolerance.amt").innerText().isEqualToIgnoringCase(
                    "Message Response Tolerance Amount : " + format.format(
                            Double.parseDouble(messagingDetails.getMessageResponseToleranceAmount().getRealValue())));
        }
        if (messagingDetails.getToleranceAmountCurrency() != null)
            validateThat("as.messaging.message.tolerance.amount.currency").innerText().isEqualToIgnoringCase(
                    "Tolerance Amount Currency : " + messagingDetails.getToleranceAmountCurrency().getRealValue());
    }

    private void assertAgreementCounterpartyTimeBasedContingentDetail(TimeBasedContingentValue timeBasedContingentValue) throws Exception {
        if (timeBasedContingentValue.getTimeBasedContingentDetail() != null &&
                timeBasedContingentValue.getTimeBasedContingentDetail().size() > 0) {
            for (int i = 0; i < timeBasedContingentValue.getTimeBasedContingentDetail().size(); i++) {
                if (timeBasedContingentValue.getTimeBasedContingentDetail().get(i).getEffectiveDate() != null) {
                    validateThat("as.rule.trigger.counterparty.time.details.effective.date",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            timeBasedContingentValue.getTimeBasedContingentDetail().get(
                                    i).getEffectiveDate().getRealValue());
                }
                if (timeBasedContingentValue.getTimeBasedContingentDetail().get(i).getThreshold() != null) {
                    validateThat("as.rule.trigger.counterparty.time.details.threshold",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            timeBasedContingentValue.getTimeBasedContingentDetail().get(
                                    i).getThreshold().getRealValue());
                }
                if (timeBasedContingentValue.getTimeBasedContingentDetail().get(i).getThresholdType() != null) {
                    validateThat("as.rule.trigger.counterparty.time.details.threshold.type",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            timeBasedContingentValue.getTimeBasedContingentDetail().get(
                                    i).getThresholdType().getRealValue());
                }
                if (timeBasedContingentValue.getTimeBasedContingentDetail().get(i).getThresholdCurrency() != null) {
                    validateThat("as.rule.trigger.counterparty.time.details.threshold.currency",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            timeBasedContingentValue.getTimeBasedContingentDetail().get(
                                    i).getThresholdCurrency().getRealValue());
                }
                if (timeBasedContingentValue.getTimeBasedContingentDetail().get(i).getMinimumTransfer() != null) {
                    validateThat("as.rule.trigger.counterparty.time.details.minimum.transfer",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            timeBasedContingentValue.getTimeBasedContingentDetail().get(
                                    i).getMinimumTransfer().getRealValue());
                }
                if (timeBasedContingentValue.getTimeBasedContingentDetail().get(i).getMinimumTransferType() != null) {
                    validateThat("as.rule.trigger.counterparty.time.details.minimum.transfer.type",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            timeBasedContingentValue.getTimeBasedContingentDetail().get(
                                    i).getMinimumTransferType().getRealValue());
                }
                if (timeBasedContingentValue.getTimeBasedContingentDetail().get(i).getRoundingAmounts() != null) {
                    validateThat("as.rule.trigger.counterparty.time.details.rounding.amounts",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            timeBasedContingentValue.getTimeBasedContingentDetail().get(
                                    i).getRoundingAmounts().getRealValue());
                }
                if (timeBasedContingentValue.getTimeBasedContingentDetail().get(i).getTimeBasedIa() != null) {
                    validateThat("as.rule.trigger.counterparty.time.details.time.based.ia",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            timeBasedContingentValue.getTimeBasedContingentDetail().get(
                                    i).getTimeBasedIa().getRealValue());
                }
                if (timeBasedContingentValue.getTimeBasedContingentDetail().get(i).getTimeBasedIaCurrency() != null) {
                    validateThat("as.rule.trigger.counterparty.time.details.time.based.ia.currency",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            timeBasedContingentValue.getTimeBasedContingentDetail().get(
                                    i).getTimeBasedIaCurrency().getRealValue());
                }
            }
        }

    }

    private void assertAgreementPincipalTimeBasedContingentDetail(TimeBasedContingentValue timeBasedContingentValue) throws Exception {
        if (timeBasedContingentValue.getTimeBasedContingentDetail() != null &&
                timeBasedContingentValue.getTimeBasedContingentDetail().size() > 0) {
            for (int i = 0; i < timeBasedContingentValue.getTimeBasedContingentDetail().size(); i++) {
                if (timeBasedContingentValue.getTimeBasedContingentDetail().get(i).getEffectiveDate() != null) {
                    validateThat("as.rule.trigger.principal.time.details.effective.date",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            timeBasedContingentValue.getTimeBasedContingentDetail().get(
                                    i).getEffectiveDate().getRealValue());
                }
                if (timeBasedContingentValue.getTimeBasedContingentDetail().get(i).getThreshold() != null) {
                    validateThat("as.rule.trigger.principal.time.details.threshold",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            timeBasedContingentValue.getTimeBasedContingentDetail().get(
                                    i).getThreshold().getRealValue());
                }
                if (timeBasedContingentValue.getTimeBasedContingentDetail().get(i).getThresholdType() != null) {
                    validateThat("as.rule.trigger.principal.time.details.threshold.type",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            timeBasedContingentValue.getTimeBasedContingentDetail().get(
                                    i).getThresholdType().getRealValue());
                }
                if (timeBasedContingentValue.getTimeBasedContingentDetail().get(i).getThresholdCurrency() != null) {
                    validateThat("as.rule.trigger.principal.time.details.threshold.currency",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            timeBasedContingentValue.getTimeBasedContingentDetail().get(
                                    i).getThresholdCurrency().getRealValue());
                }
                if (timeBasedContingentValue.getTimeBasedContingentDetail().get(i).getMinimumTransfer() != null) {
                    validateThat("as.rule.trigger.principal.time.details.minimum.transfer",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            timeBasedContingentValue.getTimeBasedContingentDetail().get(
                                    i).getMinimumTransfer().getRealValue());
                }
                if (timeBasedContingentValue.getTimeBasedContingentDetail().get(i).getMinimumTransferType() != null) {
                    validateThat("as.rule.trigger.principal.time.details.minimum.transfer.type",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            timeBasedContingentValue.getTimeBasedContingentDetail().get(
                                    i).getMinimumTransferType().getRealValue());
                }
                if (timeBasedContingentValue.getTimeBasedContingentDetail().get(i).getRoundingAmounts() != null) {
                    validateThat("as.rule.trigger.principal.time.details.rounding.amounts",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            timeBasedContingentValue.getTimeBasedContingentDetail().get(
                                    i).getRoundingAmounts().getRealValue());
                }
                if (timeBasedContingentValue.getTimeBasedContingentDetail().get(i).getTimeBasedIa() != null) {
                    validateThat("as.rule.trigger.principal.time.details.time.based.ia",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            timeBasedContingentValue.getTimeBasedContingentDetail().get(
                                    i).getTimeBasedIa().getRealValue());
                }
                if (timeBasedContingentValue.getTimeBasedContingentDetail().get(i).getTimeBasedIaCurrency() != null) {
                    validateThat("as.rule.trigger.principal.time.details.time.based.ia.currency",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            timeBasedContingentValue.getTimeBasedContingentDetail().get(
                                    i).getTimeBasedIaCurrency().getRealValue());
                }
            }
        }
    }

    private void assertAgreementCounterpartyNavBasedContingentDetail(NavBasedContingentValue navBasedContingentValue) throws Exception {
        if (navBasedContingentValue.getNavBasedContingentValueDetail() != null &&
                navBasedContingentValue.getNavBasedContingentValueDetail().size() > 0) {
            for (int i = 0; i < navBasedContingentValue.getNavBasedContingentValueDetail().size(); i++) {
                if (navBasedContingentValue.getNavBasedContingentValueDetail().get(i).getNavAmt() != null) {
                    validateThat("as.rule.trigger.counterparty.nav.details.nav.amount",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            navBasedContingentValue.getNavBasedContingentValueDetail().get(
                                    i).getNavAmt().getRealValue());
                }
                if (navBasedContingentValue.getNavBasedContingentValueDetail().get(i).getThreshold() != null) {
                    validateThat("as.rule.trigger.counterparty.nav.details.threshold",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            navBasedContingentValue.getNavBasedContingentValueDetail().get(
                                    i).getThreshold().getRealValue());
                }
                if (navBasedContingentValue.getNavBasedContingentValueDetail().get(i).getThresholdType() != null) {
                    validateThat("as.rule.trigger.counterparty.nav.details.threshold.type",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            navBasedContingentValue.getNavBasedContingentValueDetail().get(
                                    i).getThresholdType().getRealValue());
                }
                if (navBasedContingentValue.getNavBasedContingentValueDetail().get(i).getThresholdCurrency() != null) {
                    validateThat("as.rule.trigger.counterparty.nav.details.threshold.currency",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            navBasedContingentValue.getNavBasedContingentValueDetail().get(
                                    i).getThresholdCurrency().getRealValue());
                }
                if (navBasedContingentValue.getNavBasedContingentValueDetail().get(i).getMinimumTransfer() != null) {
                    validateThat("as.rule.trigger.counterparty.nav.details.minimum.transfer",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            navBasedContingentValue.getNavBasedContingentValueDetail().get(
                                    i).getMinimumTransfer().getRealValue());
                }
                if (navBasedContingentValue.getNavBasedContingentValueDetail().get(
                        i).getMinimumTransferType() != null) {
                    validateThat("as.rule.trigger.counterparty.nav.details.minimum.transfer.type",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            navBasedContingentValue.getNavBasedContingentValueDetail().get(
                                    i).getMinimumTransferType().getRealValue());
                }
                if (navBasedContingentValue.getNavBasedContingentValueDetail().get(i).getRoundingAmounts() != null) {
                    validateThat("as.rule.trigger.counterparty.nav.details.rounding.amounts",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            navBasedContingentValue.getNavBasedContingentValueDetail().get(
                                    i).getRoundingAmounts().getRealValue());
                }
                if (navBasedContingentValue.getNavBasedContingentValueDetail().get(i).getNavBasedIa() != null) {
                    validateThat("as.rule.trigger.counterparty.nav.details.nav.based.ia",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            navBasedContingentValue.getNavBasedContingentValueDetail().get(
                                    i).getNavBasedIa().getRealValue());
                }
                if (navBasedContingentValue.getNavBasedContingentValueDetail().get(i).getNavBasedIaCurrency() != null) {
                    validateThat("as.rule.trigger.counterparty.nav.details.nav.based.ia.currency",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            navBasedContingentValue.getNavBasedContingentValueDetail().get(
                                    i).getNavBasedIaCurrency().getRealValue());
                }
            }
        }

    }

    private void assertAgreementPrincipalNavBasedContingentDetail(NavBasedContingentValue navBasedContingentValue) throws Exception {
        if (navBasedContingentValue.getNavBasedContingentValueDetail() != null &&
                navBasedContingentValue.getNavBasedContingentValueDetail().size() > 0) {
            for (int i = 0; i < navBasedContingentValue.getNavBasedContingentValueDetail().size(); i++) {
                if (navBasedContingentValue.getNavBasedContingentValueDetail().get(i).getNavAmt() != null) {
                    validateThat("as.rule.trigger.principal.nav.details.nav.amount",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            navBasedContingentValue.getNavBasedContingentValueDetail().get(
                                    i).getNavAmt().getRealValue());
                }
                if (navBasedContingentValue.getNavBasedContingentValueDetail().get(i).getThreshold() != null) {
                    validateThat("as.rule.trigger.principal.nav.details.threshold",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            navBasedContingentValue.getNavBasedContingentValueDetail().get(
                                    i).getThreshold().getRealValue());
                }
                if (navBasedContingentValue.getNavBasedContingentValueDetail().get(i).getThresholdType() != null) {
                    validateThat("as.rule.trigger.principal.nav.details.threshold.type",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            navBasedContingentValue.getNavBasedContingentValueDetail().get(
                                    i).getThresholdType().getRealValue());
                }
                if (navBasedContingentValue.getNavBasedContingentValueDetail().get(i).getThresholdCurrency() != null) {
                    validateThat("as.rule.trigger.principal.nav.details.threshold.currency",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            navBasedContingentValue.getNavBasedContingentValueDetail().get(
                                    i).getThresholdCurrency().getRealValue());
                }
                if (navBasedContingentValue.getNavBasedContingentValueDetail().get(i).getMinimumTransfer() != null) {
                    validateThat("as.rule.trigger.principal.nav.details.minimum.transfer",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            navBasedContingentValue.getNavBasedContingentValueDetail().get(
                                    i).getMinimumTransfer().getRealValue());
                }
                if (navBasedContingentValue.getNavBasedContingentValueDetail().get(
                        i).getMinimumTransferType() != null) {
                    validateThat("as.rule.trigger.principal.nav.details.minimum.transfer.type",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            navBasedContingentValue.getNavBasedContingentValueDetail().get(
                                    i).getMinimumTransferType().getRealValue());
                }
                if (navBasedContingentValue.getNavBasedContingentValueDetail().get(i).getRoundingAmounts() != null) {
                    validateThat("as.rule.trigger.principal.nav.details.rounding.amounts",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            navBasedContingentValue.getNavBasedContingentValueDetail().get(
                                    i).getRoundingAmounts().getRealValue());
                }
                if (navBasedContingentValue.getNavBasedContingentValueDetail().get(i).getNavBasedIa() != null) {
                    validateThat("as.rule.trigger.principal.nav.details.nav.based.ia",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            navBasedContingentValue.getNavBasedContingentValueDetail().get(
                                    i).getNavBasedIa().getRealValue());
                }
                if (navBasedContingentValue.getNavBasedContingentValueDetail().get(i).getNavBasedIaCurrency() != null) {
                    validateThat("as.rule.trigger.principal.nav.details.nav.based.ia.currency",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            navBasedContingentValue.getNavBasedContingentValueDetail().get(
                                    i).getNavBasedIaCurrency().getRealValue());
                }
            }
        }
    }

    private void assertAgreementCounterpartyRatingBasedContingentDetails(RatingContingentValue ratingContingentValue) throws Exception {
        if (ratingContingentValue.getUsePrincipalRating() != null) {
            validateThat("as.rule.trigger.use.counterparty.rating").innerText().contains(
                    "Use Counterparty Rating -" + ratingContingentValue.getUsePrincipalRating().getRealValue());
        }
        if (ratingContingentValue.getUseCreditSupportProviderRating() != null) {
            validateThat("as.rule.trigger.counterparty.use.credit.support").innerText().contains(
                    "Use Credit Support Provider Rating -" + ratingContingentValue.getUseCreditSupportProviderRating().getRealValue());
        }
        if (ratingContingentValue.getReferenceRatingAgencies() != null &&
                ratingContingentValue.getReferenceRatingAgencies().size() > 0) {
            //StringBuffer sb = new StringBuffer();
            for (int i = 0; i < ratingContingentValue.getReferenceRatingAgencies().size(); i++) {
                //sb.append(ratingContingentValue.getReferenceRatingAgencies().get(i).getRealValue()+" ");
                validateThat("as.rule.trigger.counterparty.reference.rating.agencies").innerText().contains(
                        " " + ratingContingentValue.getReferenceRatingAgencies().get(i).getRealValue());
            }
            //validateThat("as.rule.trigger.counterparty.reference.rating.agencies").innerText().isEqualToIgnoringWhitespace("Reference Rating Agencies - "+sb.toString().trim());
        }
        if (ratingContingentValue.getDebtClassfication() != null &&
                ratingContingentValue.getDebtClassfication().size() > 0) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < ratingContingentValue.getDebtClassfication().size(); i++) {
                sb.append(ratingContingentValue.getDebtClassfication().get(i).getRealValue()).append(" ");
            }
            validateThat("as.rule.trigger.counterparty.debt.classification").innerText().isEqualToIgnoringWhitespace(
                    "Debt Classification -" + sb.toString().trim());
        }
        if (ratingContingentValue.getApplicationOfRatings() != null) {
            validateThat("as.rule.trigger.counterparty.application.of.ratings").innerText().isEqualToIgnoringWhitespace(
                    "Application Of Ratings -" + ratingContingentValue.getApplicationOfRatings().getRealValue());
        }
        if (ratingContingentValue.getIfNotRatedByAnyAgency() != null) {
            validateThat("as.rule.trigger.counterparty.if.not.rated").innerText().isEqualToIgnoringWhitespace(
                    "If Not Rated By Any Agency, Assume NR" + ratingContingentValue.getIfNotRatedByAnyAgency().getRealValue());
        }
        if (ratingContingentValue.getRatingContingentValueDetail() != null && ratingContingentValue.getRatingContingentValueDetail().size() > 0) {
            DecimalFormat format = new DecimalFormat();
            format.applyPattern("###,###,##0.00");
            for (int i = 0; i < ratingContingentValue.getRatingContingentValueDetail().size(); i++) {
                if (ratingContingentValue.getRatingContingentValueDetail().get(i).getRatingLevel() != null) {
                    validateThat("as.rule.trigger.counterparty.rating.details.rating.level",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            ratingContingentValue.getRatingContingentValueDetail().get(
                                    i).getRatingLevel().getRealValue());
                }
                if (ratingContingentValue.getRatingContingentValueDetail().get(i).getThreshold() != null) {
                    validateThat("as.rule.trigger.counterparty.rating.details.threshold",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            format.format(Float.parseFloat(ratingContingentValue.getRatingContingentValueDetail().get(
                                    i).getThreshold().getRealValue())));
                }
                if (ratingContingentValue.getRatingContingentValueDetail().get(i).getThresholdType() != null) {
                    validateThat("as.rule.trigger.counterparty.rating.details.threshold.type",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            ratingContingentValue.getRatingContingentValueDetail().get(
                                    i).getThresholdType().getRealValue());
                }
                if (ratingContingentValue.getRatingContingentValueDetail().get(i).getMinimumTransfer() != null) {
                    validateThat("as.rule.trigger.counterparty.rating.details.minimum.transfer",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            format.format(Float.parseFloat(ratingContingentValue.getRatingContingentValueDetail().get(
                                    i).getMinimumTransfer().getRealValue())));
                }
                if (ratingContingentValue.getRatingContingentValueDetail().get(i).getMinimumTransferType() != null) {
                    validateThat("as.rule.trigger.counterparty.rating.details.minimum.transfer.type",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            ratingContingentValue.getRatingContingentValueDetail().get(
                                    i).getMinimumTransferType().getRealValue());
                }
                if (ratingContingentValue.getRatingContingentValueDetail().get(i).getRoundingAmounts() != null) {
                    validateThat("as.rule.trigger.counterparty.rating.details.rounding.amounts",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            format.format(Float.parseFloat(ratingContingentValue.getRatingContingentValueDetail().get(
                                    i).getRoundingAmounts().getRealValue())));
                }
                if (ratingContingentValue.getRatingContingentValueDetail().get(i).getRatingBasedIa() != null) {
                    validateThat("as.rule.trigger.counterparty.rating.details.rating.based.ia",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            ratingContingentValue.getRatingContingentValueDetail().get(
                                    i).getRatingBasedIa().getRealValue());
                }
                if (ratingContingentValue.getRatingContingentValueDetail().get(i).getRatingBasedIaCurrency() != null) {
                    validateThat("as.rule.trigger.counterparty.rating.details.rating.based.ia.type",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            ratingContingentValue.getRatingContingentValueDetail().get(
                                    i).getRatingBasedIaCurrency().getRealValue());
                }
            }
        }
    }

    private void assertAgreementPrincipalRatingBasedContingentDetails(RatingContingentValue ratingContingentValue) throws Exception {
        if (ratingContingentValue.getUsePrincipalRating() != null) {
            validateThat("as.rule.trigger.use.principal.rating").innerText().contains(
                    "Use Principle Rating -" + ratingContingentValue.getUsePrincipalRating().getRealValue());
        }
        if (ratingContingentValue.getUseCreditSupportProviderRating() != null) {
            validateThat("as.rule.trigger.principal.use.credit.support").innerText().contains(
                    "Use Credit Support Provider Rating -" + ratingContingentValue.getUseCreditSupportProviderRating().getRealValue());
        }
        if (ratingContingentValue.getReferenceRatingAgencies() != null &&
                ratingContingentValue.getReferenceRatingAgencies().size() > 0) {
            //StringBuffer sb = new StringBuffer();
            for (int i = 0; i < ratingContingentValue.getReferenceRatingAgencies().size(); i++) {
                //sb.append(ratingContingentValue.getReferenceRatingAgencies().get(i).getRealValue()+" ");
                validateThat("as.rule.trigger.principal.reference.rating.agencies").innerText().contains(
                        " " + ratingContingentValue.getReferenceRatingAgencies().get(i).getRealValue());
            }
            //validateThat("as.rule.trigger.principal.reference.rating.agencies").innerText().isEqualToIgnoringWhitespace("Reference Rating Agencies - "+sb.toString().trim());
        }
        if (ratingContingentValue.getDebtClassfication() != null &&
                ratingContingentValue.getDebtClassfication().size() > 0) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < ratingContingentValue.getDebtClassfication().size(); i++) {
                sb.append(ratingContingentValue.getDebtClassfication().get(i).getRealValue()).append(" ");
            }
            validateThat("as.rule.trigger.principal.debt.classification").innerText().isEqualToIgnoringWhitespace(
                    "Debt Classification -" + sb.toString().trim());
        }
        if (ratingContingentValue.getApplicationOfRatings() != null) {
            validateThat("as.rule.trigger.principal.application.of.ratings").innerText().isEqualToIgnoringWhitespace(
                    "Application Of Ratings -" + ratingContingentValue.getApplicationOfRatings().getRealValue());
        }
        if (ratingContingentValue.getIfNotRatedByAnyAgency() != null) {
            validateThat("as.rule.trigger.principal.if.not.rated").innerText().isEqualToIgnoringWhitespace(
                    "If Not Rated By Any Agency, Assume NR" + ratingContingentValue.getIfNotRatedByAnyAgency().getRealValue());
        }
        if (ratingContingentValue.getRatingContingentValueDetail() != null && ratingContingentValue.getRatingContingentValueDetail().size() > 0) {
            DecimalFormat format = new DecimalFormat();
            format.applyPattern("###,###,##0.00");
            for (int i = 0; i < ratingContingentValue.getRatingContingentValueDetail().size(); i++) {
                if (ratingContingentValue.getRatingContingentValueDetail().get(i).getRatingLevel() != null) {
                    validateThat("as.rule.trigger.principal.rating.details.rating.level",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            ratingContingentValue.getRatingContingentValueDetail().get(
                                    i).getRatingLevel().getRealValue());
                }
                if (ratingContingentValue.getRatingContingentValueDetail().get(i).getThreshold() != null) {
                    validateThat("as.rule.trigger.principal.rating.details.threshold",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            format.format(Float.parseFloat(ratingContingentValue.getRatingContingentValueDetail().get(
                                    i).getThreshold().getRealValue())));
                }
                if (ratingContingentValue.getRatingContingentValueDetail().get(i).getThresholdType() != null) {
                    validateThat("as.rule.trigger.principal.rating.details.threshold.type",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            ratingContingentValue.getRatingContingentValueDetail().get(
                                    i).getThresholdType().getRealValue());
                }
                if (ratingContingentValue.getRatingContingentValueDetail().get(i).getMinimumTransfer() != null) {
                    validateThat("as.rule.trigger.principal.rating.details.minimum.transfer",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            format.format(Float.parseFloat(ratingContingentValue.getRatingContingentValueDetail().get(
                                    i).getMinimumTransfer().getRealValue())));
                }
                if (ratingContingentValue.getRatingContingentValueDetail().get(i).getMinimumTransferType() != null) {
                    validateThat("as.rule.trigger.principal.rating.details.minimum.transfer.type",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            ratingContingentValue.getRatingContingentValueDetail().get(
                                    i).getMinimumTransferType().getRealValue());
                }
                if (ratingContingentValue.getRatingContingentValueDetail().get(i).getRoundingAmounts() != null) {
                    validateThat("as.rule.trigger.principal.rating.details.rounding.amounts",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            format.format(Float.parseFloat(ratingContingentValue.getRatingContingentValueDetail().get(
                                    i).getRoundingAmounts().getRealValue())));
                }
                if (ratingContingentValue.getRatingContingentValueDetail().get(i).getRatingBasedIa() != null) {
                    validateThat("as.rule.trigger.principal.rating.details.rating.based.ia",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            ratingContingentValue.getRatingContingentValueDetail().get(
                                    i).getRatingBasedIa().getRealValue());
                }
                if (ratingContingentValue.getRatingContingentValueDetail().get(i).getRatingBasedIaCurrency() != null) {
                    validateThat("as.rule.trigger.principal.rating.details.rating.based.ia.type",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            ratingContingentValue.getRatingContingentValueDetail().get(
                                    i).getRatingBasedIaCurrency().getRealValue());
                }
            }
        }
    }

    private void assertAgreementAdditionalFieldsDetails(AgreementSummary agreementSummary) throws Exception {
        if (agreementSummary.getUserDefinedFieldsDetails().getUserDefinedFieldsDetail() != null &&
                agreementSummary.getUserDefinedFieldsDetails().getUserDefinedFieldsDetail().size() > 0) {
            for (Field field : agreementSummary.getUserDefinedFieldsDetails().getUserDefinedFieldsDetail()) {
                if (field.getFieldName() != null) {
                    validateThat("as.additional.fields.value",
                            field.getFieldName().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                            field.getFieldName().getRealValue() + ":" + field.getValue().getRealValue());
                }
            }
        }
    }

    private void assertAgreementSettlementDetails(AgreementSummary agreementSummary) throws Exception {
        if (agreementSummary.getSettlementInformationConfiguration().getSettlement() != null) {
            validateThat("as.select.settlementinfo.settlement.type").innerText().isEqualToIgnoringWhitespace(
                    "Settlement - " + agreementSummary.getSettlementInformationConfiguration().getSettlement().getRealValue());
        }
        if (agreementSummary.getSettlementInformationConfiguration().getTsaLevel() != null) {
            validateThat("as.select.settlementinfo.tsa.level").innerText().isEqualToIgnoringWhitespace(
                    "Cashflow Level - " + agreementSummary.getSettlementInformationConfiguration().getTsaLevel().getRealValue());
        }
        if (agreementSummary.getSettlementInformationConfiguration().getSettlementBucket() != null &&
                agreementSummary.getSettlementInformationConfiguration().getSettlementBucket().size() > 0) {
            for (int i = 0; i < agreementSummary.getSettlementInformationConfiguration().getSettlementBucket().size(); i++) {
                if (agreementSummary.getSettlementInformationConfiguration().getSettlementBucket().get(
                        i).getBookingType() != null) {
                    validateThat("as.settlement.booking.type",
                            String.valueOf(i + 1)).innerText().isEqualToIgnoringWhitespace(
                            agreementSummary.getSettlementInformationConfiguration().getSettlementBucket().get(
                                    i).getBookingType().value());
                }
                if (agreementSummary.getSettlementInformationConfiguration().getSettlementBucket().get(
                        i).getDirection() != null) {
                    validateThat("as.settlement.direction",
                            String.valueOf(i + 1)).innerText().isEqualToIgnoringWhitespace(
                            agreementSummary.getSettlementInformationConfiguration().getSettlementBucket().get(
                                    i).getDirection().value());
                }
                if (agreementSummary.getSettlementInformationConfiguration().getSettlementBucket().get(
                        i).getBucket() != null) {
                    validateThat("as.settlement.bucket", String.valueOf(i + 1)).innerText().isEqualToIgnoringWhitespace(
                            agreementSummary.getSettlementInformationConfiguration().getSettlementBucket().get(
                                    i).getBucket().getRealValue());
                }
            }
        }
    }

    private void assertAgreementReportingControlDetail(AgreementSummary agreementSummary) throws Exception {
        if (agreementSummary.getReportingDetails().getDistributionTask() != null &&
                agreementSummary.getReportingDetails().getDistributionTask().size() > 0) {
            for (int i = 0; i < agreementSummary.getReportingDetails().getDistributionTask().size(); i++) {
                //report distribution name
                if (agreementSummary.getReportingDetails().getDistributionTask().get(
                        i).getDistributionTaskName() != null) {
                    validateThat("as.reporting.control.report.distribution.name",
                            String.valueOf(i + 1)).innerText().isEqualToIgnoringWhitespace(
                            agreementSummary.getReportingDetails().getDistributionTask().get(
                                    i).getDistributionTaskName().getRealValue());
                }
                //distribution include report
                if (agreementSummary.getReportingDetails().getDistributionTask().get(i).getReport() != null) {
                    StringBuffer sb = new StringBuffer();
                    if (agreementSummary.getReportingDetails().getDistributionTask().get(
                            i).getReport().getExposures() != null) {
                        sb.append(agreementSummary.getReportingDetails().getDistributionTask().get(
                                i).getReport().getExposures().getRealValue()).append(",");
                    }
                    if (agreementSummary.getReportingDetails().getDistributionTask().get(
                            i).getReport().getReconciliationTradesOutput() != null) {
                        sb.append(agreementSummary.getReportingDetails().getDistributionTask().get(
                                i).getReport().getReconciliationTradesOutput().getRealValue()).append(",");
                    }
                    if (agreementSummary.getReportingDetails().getDistributionTask().get(
                            i).getReport().getAssetHoldingsandValuation() != null) {
                        sb.append(agreementSummary.getReportingDetails().getDistributionTask().get(
                                i).getReport().getAssetHoldingsandValuation().getRealValue()).append(",");
                    }
                    if (agreementSummary.getReportingDetails().getDistributionTask().get(
                            i).getReport().getDailyExposure() != null) {
                        sb.append(agreementSummary.getReportingDetails().getDistributionTask().get(
                                i).getReport().getDailyExposure().getRealValue()).append(",");
                    }
                    String realValue = sb.toString().substring(0, sb.toString().length() - 1);
                    validateThat("as.reporting.control.distribution.include.report",
                            String.valueOf(i + 1)).innerText().isEqualToIgnoringWhitespace(realValue);
                }
                //distribution include template
                if (agreementSummary.getReportingDetails().getDistributionTask().get(i).getAttachedTemplate() != null) {
                    if (agreementSummary.getReportingDetails().getDistributionTask().get(
                            i).getAttachedTemplate().getLinkText() != null) {
                        validateThat("as.reporting.control.distribution.include.template",
                                String.valueOf(i + 1)).innerText().isEqualToIgnoringWhitespace(
                                agreementSummary.getReportingDetails().getDistributionTask().get(
                                        i).getAttachedTemplate().getLinkText().getRealValue());

                    }
                }
                //frequency name
                if (agreementSummary.getReportingDetails().getDistributionTask().get(i).getFrequency() != null) {
                    validateThat("as.reporting.control.frequency.name",
                            String.valueOf(i + 1)).innerText().isEqualToIgnoringWhitespace(
                            agreementSummary.getReportingDetails().getDistributionTask().get(i).getFrequency().value());
                }
                //business adjust type
                if (agreementSummary.getReportingDetails().getDistributionTask().get(
                        i).getBusinessAdjustType() != null) {
                    validateThat("as.reporting.control.business.adjust.type",
                            String.valueOf(i + 1)).innerText().isEqualToIgnoringWhitespace(
                            agreementSummary.getReportingDetails().getDistributionTask().get(
                                    i).getBusinessAdjustType().value());
                }
                //frequency start date
                if (agreementSummary.getReportingDetails().getDistributionTask().get(i).getFrequencyStart() != null) {
                    validateThat("as.reporting.control.frequency.start.date",
                            String.valueOf(i + 1)).innerText().isEqualToIgnoringWhitespace(
                            agreementSummary.getReportingDetails().getDistributionTask().get(
                                    i).getFrequencyStart().getRealValue());
                }
                //medium
                if (agreementSummary.getReportingDetails().getDistributionTask().get(i).getMedium() != null &&
                        agreementSummary.getReportingDetails().getDistributionTask().get(i).getMedium().size() > 0) {
                    StringBuffer sb = new StringBuffer();
                    for (int j = 0; j < agreementSummary.getReportingDetails().getDistributionTask().get(
                            i).getMedium().size(); j++) {
                        sb.append(agreementSummary.getReportingDetails().getDistributionTask().get(i).getMedium().get(
                                j).value()).append(",");
                    }
                    String realValue = sb.toString().substring(0, sb.toString().length() - 1);
                    validateThat("as.reporting.control.medium",
                            String.valueOf(i + 1)).innerText().isEqualToIgnoringWhitespace(realValue);
                }
                //principal contact
                if (agreementSummary.getReportingDetails().getDistributionTask().get(i).getPrincipalContact() != null) {
                    if (agreementSummary.getReportingDetails().getDistributionTask().get(
                            i).getPrincipalContact().getPrimaryContact() != null) {
                        validateThat("as.reporting.control.principal.contact",
                                String.valueOf(i + 1)).innerText().isEqualToIgnoringWhitespace(
                                agreementSummary.getReportingDetails().getDistributionTask().get(
                                        i).getPrincipalContact().getPrimaryContact().getRealValue());
                    }
                }
                //principal cc email contacts
                if (agreementSummary.getReportingDetails().getDistributionTask().get(i).getPrincipalContact() != null) {
                    if (agreementSummary.getReportingDetails().getDistributionTask().get(
                            i).getPrincipalContact().getCcEmailContact() != null &&
                            agreementSummary.getReportingDetails().getDistributionTask().get(
                                    i).getPrincipalContact().getCcEmailContact().size() > 0) {
                        StringBuffer sb = new StringBuffer();
                        for (int j = 0; j < agreementSummary.getReportingDetails().getDistributionTask().get(
                                i).getPrincipalContact().getCcEmailContact().size(); j++) {
                            sb.append(agreementSummary.getReportingDetails().getDistributionTask().get(
                                    i).getPrincipalContact().getCcEmailContact().get(j).getRealValue()).append(",");
                        }
                        String realValue = sb.toString().substring(0, sb.toString().length() - 1);
                        validateThat("as.reporting.control.principal.cc.email.contacts",
                                String.valueOf(i + 1)).innerText().isEqualToIgnoringWhitespace(realValue);
                    }
                }
                //counterparty contact
                if (agreementSummary.getReportingDetails().getDistributionTask().get(
                        i).getCounterpartyContact() != null) {
                    if (agreementSummary.getReportingDetails().getDistributionTask().get(
                            i).getCounterpartyContact().getPrimaryContact() != null) {
                        validateThat("as.reporting.control.counterparty.contact",
                                String.valueOf(i + 1)).innerText().isEqualToIgnoringWhitespace(
                                agreementSummary.getReportingDetails().getDistributionTask().get(
                                        i).getCounterpartyContact().getPrimaryContact().getRealValue());
                    }
                }
                //counterparty cc email contacts
                if (agreementSummary.getReportingDetails().getDistributionTask().get(
                        i).getCounterpartyContact() != null) {
                    if (agreementSummary.getReportingDetails().getDistributionTask().get(
                            i).getCounterpartyContact().getCcEmailContact() != null &&
                            agreementSummary.getReportingDetails().getDistributionTask().get(
                                    i).getCounterpartyContact().getCcEmailContact().size() > 0) {
                        StringBuffer sb = new StringBuffer();
                        for (int j = 0; j < agreementSummary.getReportingDetails().getDistributionTask().get(
                                i).getCounterpartyContact().getCcEmailContact().size(); j++) {
                            sb.append(agreementSummary.getReportingDetails().getDistributionTask().get(
                                    i).getCounterpartyContact().getCcEmailContact().get(j).getRealValue()).append(",");
                        }
                        String realValue = sb.toString().substring(0, sb.toString().length() - 1);
                        validateThat("as.reporting.control.counterparty.cc.email.contacts",
                                String.valueOf(i + 1)).innerText().isEqualToIgnoringWhitespace(realValue);
                    }
                }
            }
        }
        if (agreementSummary.getReportingDetails().getLetterAttachment() != null &&
                agreementSummary.getReportingDetails().getLetterAttachment().size() > 0) {
            for (int i = 0; i < agreementSummary.getReportingDetails().getLetterAttachment().size(); i++) {
                if (agreementSummary.getReportingDetails().getLetterAttachment().get(
                        i).getLetterAttachmentReportType() != null) {
                    validateThat("as.reporting.control.letter.attachment.attached.name",
                            String.valueOf(i + 1)).innerText().isEqualToIgnoringWhitespace(
                            agreementSummary.getReportingDetails().getLetterAttachment().get(
                                    i).getLetterAttachmentReportType().value() + " Include Report");
                }
                if (agreementSummary.getReportingDetails().getLetterAttachment().get(
                        i).getLetterAttachmentDetail() != null &&
                        agreementSummary.getReportingDetails().getLetterAttachment().get(
                                i).getLetterAttachmentDetail().size() > 0) {
                    for (int j = 0; j < agreementSummary.getReportingDetails().getLetterAttachment().get(
                            i).getLetterAttachmentDetail().size(); j++) {

                        if (agreementSummary.getReportingDetails().getLetterAttachment().get(
                                i).getLetterAttachmentDetail().get(j).getIncludeReport() != null) {
                            validateThat("as.reporting.control.letter.attachment.attached.report",
                                    String.valueOf(i + 1)).innerText().isEqualToIgnoringWhitespace(
                                    agreementSummary.getReportingDetails().getLetterAttachment().get(
                                            i).getLetterAttachmentDetail().get(j).getIncludeReport().getRealValue());
                        }
                        if (agreementSummary.getReportingDetails().getLetterAttachment().get(
                                i).getLetterAttachmentDetail().get(j).getAttachedTemplate() != null) {
                            if (agreementSummary.getReportingDetails().getLetterAttachment().get(
                                    i).getLetterAttachmentDetail().get(j).getAttachedTemplate().getLinkText() != null) {
                                validateThat("as.reporting.control.letter.attachment.attached.template",
                                        String.valueOf(i + 1)).innerText().isEqualToIgnoringWhitespace(
                                        agreementSummary.getReportingDetails().getLetterAttachment().get(
                                                i).getLetterAttachmentDetail().get(
                                                j).getAttachedTemplate().getLinkText().getRealValue());
                            }
                        }
                        if (agreementSummary.getReportingDetails().getLetterAttachment().get(
                                i).getLetterAttachmentDetail().get(j).getAttachedFormat() != null &&
                                agreementSummary.getReportingDetails().getLetterAttachment().get(
                                        i).getLetterAttachmentDetail().get(j).getAttachedFormat().size() > 0) {
                            StringBuffer sb = new StringBuffer();
                            for (AttachedFormatType attachedFormatType : agreementSummary.getReportingDetails().getLetterAttachment().get(
                                    i).getLetterAttachmentDetail().get(j).getAttachedFormat()) {
                                sb.append(attachedFormatType.value()).append(",");
                            }
                            String sbValue = sb.toString();
                            String realValue = sbValue.substring(0, sbValue.length() - 1);
                            validateThat("as.reporting.control.letter.attachment.attached.format",
                                    String.valueOf(i + 1)).innerText().isEqualToIgnoringWhitespace(realValue);
                        }
                    }
                }
            }
        }
        if (agreementSummary.getReportingDetails().getNotificationLetterType() != null) {
            validateThat("as.reporting.control.notification.letter.type").innerText().isEqualToIgnoringWhitespace(
                    agreementSummary.getReportingDetails().getNotificationLetterType().getRealValue());
        }

        if (agreementSummary.getReportingDetails().getLetterTemplateType() != null) {
            validateThat("as.reporting.control.letter.template.type").innerText().isEqualToIgnoringWhitespace(
                    agreementSummary.getReportingDetails().getLetterTemplateType().getRealValue());
        }
        if (agreementSummary.getReportingDetails().getExposureStatementTemplateType() != null) {
            validateThat(
                    "as.reporting.control.exposure.statement.template.type").innerText().isEqualToIgnoringWhitespace(
                    agreementSummary.getReportingDetails().getExposureStatementTemplateType().getRealValue());
        }
        if (agreementSummary.getReportingDetails().getLetterDistributionMedium() != null &&
                agreementSummary.getReportingDetails().getLetterDistributionMedium().size() > 0) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < agreementSummary.getReportingDetails().getLetterDistributionMedium().size(); i++) {
                sb.append(agreementSummary.getReportingDetails().getLetterDistributionMedium().get(
                        i).getRealValue()).append(",");
            }
            String sbValue = sb.toString().substring(0, sb.toString().length() - 1);
            validateThat("as.reporting.control.letter.distribution.medium").innerText().isEqualToIgnoringWhitespace(
                    sbValue);
        }
        if (agreementSummary.getReportingDetails().getMessageWorkflow() != null) {
            validateThat("as.reporting.control.message.workflow").innerText().isEqualToIgnoringCase(
                    agreementSummary.getReportingDetails().getMessageWorkflow().getRealValue());
        }
        if (agreementSummary.getReportingDetails().getPrincipalDeliveryGroup() != null &&
                agreementSummary.getReportingDetails().getPrincipalDeliveryGroup().size() > 0) {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < agreementSummary.getReportingDetails().getPrincipalDeliveryGroup().size(); i++) {
                sb.append(agreementSummary.getReportingDetails().getPrincipalDeliveryGroup().get(
                        i).getRealValue()).append(", ");
            }
            String sbValue = sb.toString().substring(0, sb.toString().length() - 2);
            validateThat("as.reporting.control.principal.delivery.group").innerText().isEqualToIgnoringWhitespace(
                    sbValue);
        }
        if (agreementSummary.getReportingDetails().getInventoryDeliveryGroup() != null) {
            validateThat("as.reporting.control.inventory.delivery.group").innerText().isEqualToIgnoringWhitespace(
                    agreementSummary.getReportingDetails().getInventoryDeliveryGroup().getRealValue());
        }
        if (agreementSummary.getReportingDetails().getEnableStp() != null) {
            validateThat("as.reporting.control.enable.stp").innerText().isEqualToIgnoringWhitespace(
                    "Enable STP - " + agreementSummary.getReportingDetails().getEnableStp().getRealValue() + ":");
        }
        if (agreementSummary.getReportingDetails().getStpIntraDayEvents() != null) {
            validateThat("as.reporting.control.stp.insta.day.events").innerText().isEqualToIgnoringWhitespace(
                    "STP Intra-day Events - " + agreementSummary.getReportingDetails().getStpIntraDayEvents().getRealValue());
        }
        if (agreementSummary.getReportingDetails().getPayFourEyeConditions() != null) {
            if (agreementSummary.getReportingDetails().getPayFourEyeConditions().getConditionRule() != null) {
                validateThat("as.reporting.control.pay.condition.rule").innerText().isEqualToIgnoringWhitespace(
                        agreementSummary.getReportingDetails().getPayFourEyeConditions().getConditionRule().value().replace(
                                " Value", ""));
            }
            if (agreementSummary.getReportingDetails().getPayFourEyeConditions().getFixedValue() != null) {
                if (agreementSummary.getReportingDetails().getPayFourEyeConditions().getFixedValue().getAmount() != null) {
                    validateThat("as.reporting.control.pay.fixed.value").innerText().contains(
                            agreementSummary.getReportingDetails().getPayFourEyeConditions().getFixedValue().getAmount().getRealValue());
                }
                if (agreementSummary.getReportingDetails().getPayFourEyeConditions().getFixedValue().getCurrency() != null) {
                    validateThat("as.reporting.control.pay.fixed.value").innerText().contains(
                            agreementSummary.getReportingDetails().getPayFourEyeConditions().getFixedValue().getCurrency().getRealValue());
                }
            }
            if (agreementSummary.getReportingDetails().getPayFourEyeConditions().getPercentageOftotalExposure() != null) {
                validateThat("as.reporting.control.pay.total.exposure").innerText().isEqualToIgnoringWhitespace(
                        agreementSummary.getReportingDetails().getPayFourEyeConditions().getPercentageOftotalExposure().getRealValue());
            }
        }
        if (agreementSummary.getReportingDetails().getReceiveFourEyeConditions() != null) {
            if (agreementSummary.getReportingDetails().getReceiveFourEyeConditions().getConditionRule() != null) {
                validateThat("as.reporting.control.receive.condition.rule").innerText().isEqualToIgnoringWhitespace(
                        agreementSummary.getReportingDetails().getReceiveFourEyeConditions().getConditionRule().value().replace(
                                " Value", ""));
            }
            if (agreementSummary.getReportingDetails().getReceiveFourEyeConditions().getFixedValue() != null) {
                if (agreementSummary.getReportingDetails().getReceiveFourEyeConditions().getFixedValue().getAmount() != null) {
                    validateThat("as.reporting.control.receive.fixed.value").innerText().contains(
                            agreementSummary.getReportingDetails().getReceiveFourEyeConditions().getFixedValue().getAmount().getRealValue());
                }
                if (agreementSummary.getReportingDetails().getReceiveFourEyeConditions().getFixedValue().getCurrency() != null) {
                    validateThat("as.reporting.control.receive.fixed.value").innerText().contains(
                            agreementSummary.getReportingDetails().getReceiveFourEyeConditions().getFixedValue().getCurrency().getRealValue());
                }
            }
            if (agreementSummary.getReportingDetails().getReceiveFourEyeConditions().getPercentageOftotalExposure() != null) {
                validateThat("as.reporting.control.receive.total.exposure").innerText().isEqualToIgnoringWhitespace(
                        agreementSummary.getReportingDetails().getReceiveFourEyeConditions().getPercentageOftotalExposure().getRealValue());
            }
        }
    }

    private void assertAgreementFixedTriggerCounterpartyFixedValue(FixedValue fixedValue) throws Exception {
        if (fixedValue.getThreshold() != null) {
            validateThat("as.fixed.trigger.counterparty.fixed.value.threshold").innerText().isEqualToIgnoringWhitespace(
                    "Threshold - " + fixedValue.getThreshold().getRealValue());
        }
        if (fixedValue.getMininumTransfer() != null) {
            validateThat(
                    "as.fixed.trigger.counterparty.fixed.value.minimum.transfer").innerText().isEqualToIgnoringWhitespace(
                    "Minimum Transfer - " + fixedValue.getMininumTransfer().getRealValue());
        }
        if (fixedValue.getRoundingAmount() != null) {
            validateThat(
                    "as.fixed.trigger.counterparty.fixed.value.rounding.amount").innerText().isEqualToIgnoringWhitespace(
                    "Rounding Amount - " + fixedValue.getRoundingAmount().getRealValue());
        }
        if (fixedValue.isMtaInternalOnly() != null)
            validateThat("as.fixed.trigger.fixed.value.mta.internal.only",
                    "Counterparty Fixed values").innerText().isEqualToIgnoringCase(
                    "MTA Internal Only - " + fixedValue.isMtaInternalOnly());
        if (fixedValue.isRoundingInternalOnly() != null)
            validateThat("as.fixed.trigger.fixed.value.rounding.internal.only",
                    "Counterparty Fixed values").innerText().isEqualToIgnoringCase(
                    "Rounding Internal Only - " + fixedValue.isRoundingInternalOnly());
    }

    private void assertAgreementFixedTriggerIMCounterpartyFixedValue(FixedValue fixedValue) throws Exception {
        if (fixedValue.getThreshold() != null) {
            validateThat(
                    "as.fixed.trigger.im.counterparty.fixed.value.threshold").innerText().isEqualToIgnoringWhitespace(
                    "Threshold - " + fixedValue.getThreshold().getRealValue());
        }
        if (fixedValue.getMininumTransfer() != null) {
            validateThat(
                    "as.fixed.trigger.im.counterparty.fixed.value.minimum.transfer").innerText().isEqualToIgnoringWhitespace(
                    "Minimum Transfer - " + fixedValue.getMininumTransfer().getRealValue());
        }
        if (fixedValue.getRoundingAmount() != null) {
            validateThat(
                    "as.fixed.trigger.im.counterparty.fixed.value.rounding.amount").innerText().isEqualToIgnoringWhitespace(
                    "Rounding Amount - " + fixedValue.getRoundingAmount().getRealValue());
        }
    }

    private void assertAgreementFixedTriggerPrincipalFixedValue(FixedValue fixedValue) throws Exception {
        if (fixedValue.getThreshold() != null) {
            validateThat("as.fixed.trigger.principal.fixed.value.threshold").innerText().isEqualToIgnoringWhitespace(
                    "Threshold - " + fixedValue.getThreshold().getRealValue());
        }
        if (fixedValue.getMininumTransfer() != null) {
            validateThat(
                    "as.fixed.trigger.principal.fixed.value.minimum.transfer").innerText().isEqualToIgnoringWhitespace(
                    "Minimum Transfer - " + fixedValue.getMininumTransfer().getRealValue());
        }
        if (fixedValue.getRoundingAmount() != null) {
            validateThat(
                    "as.fixed.trigger.principal.fixed.value.rounding.amount").innerText().isEqualToIgnoringWhitespace(
                    "Rounding Amount - " + fixedValue.getRoundingAmount().getRealValue());
        }
        if (fixedValue.isMtaInternalOnly() != null)
            validateThat("as.fixed.trigger.fixed.value.mta.internal.only",
                    "Principal Fixed values").innerText().isEqualToIgnoringCase(
                    "MTA Internal Only - " + fixedValue.isMtaInternalOnly());
        if (fixedValue.isRoundingInternalOnly() != null)
            validateThat("as.fixed.trigger.fixed.value.rounding.internal.only",
                    "Principal Fixed values").innerText().isEqualToIgnoringCase(
                    "Rounding Internal Only - " + fixedValue.isRoundingInternalOnly());
    }

    private void assertAgreementFixedTriggerIMPrincipalFixedValue(FixedValue fixedValue) throws Exception {
        if (fixedValue.getThreshold() != null) {
            validateThat("as.fixed.trigger.im.principal.fixed.value.threshold").innerText().isEqualToIgnoringWhitespace(
                    "Threshold - " + fixedValue.getThreshold().getRealValue());
        }
        if (fixedValue.getMininumTransfer() != null) {
            validateThat(
                    "as.fixed.trigger.im.principal.fixed.value.minimum.transfer").innerText().isEqualToIgnoringWhitespace(
                    "Minimum Transfer - " + fixedValue.getMininumTransfer().getRealValue());
        }
        if (fixedValue.getRoundingAmount() != null) {
            validateThat(
                    "as.fixed.trigger.im.principal.fixed.value.rounding.amount").innerText().isEqualToIgnoringWhitespace(
                    "Rounding Amount - " + fixedValue.getRoundingAmount().getRealValue());
        }
    }

    private void assertAgreementFixedTriggerRounding(RoundingDatail roundingDatail) throws Exception {
        if (roundingDatail.getDelivery() != null) {
            validateThat("as.fixed.trigger.rounding.delivery").innerText().isEqualToIgnoringWhitespace(
                    "Delivery - " + roundingDatail.getDelivery().getRealValue());
        }
        if (roundingDatail.getRecall() != null) {
            validateThat("as.fixed.trigger.rounding.recall").innerText().isEqualToIgnoringWhitespace(
                    "Recall - " + roundingDatail.getRecall().getRealValue());
        }
        if (roundingDatail.getExposureStd() != null) {
            validateThat("as.fixed.trigger.rounding.exposure.std").innerText().isEqualToIgnoringWhitespace(
                    "Exposure STD - " + roundingDatail.getExposureStd().getRealValue());
        }
        if (roundingDatail.getAgreementRequirementStd() != null) {
            validateThat("as.fixed.trigger.rounding.agreement.requirement.std").innerText().isEqualToIgnoringCase(
                    "Agreement Requirement  STD - " + roundingDatail.getAgreementRequirementStd().getRealValue());
        }
        if (roundingDatail.isApplySTDtoNoCall() != null) {
            validateThat("as.fixed.trigger.rounding.apply.std.to.no.call").innerText().isEqualToIgnoringWhitespace(
                    "Apply STD to No Call - " + roundingDatail.isApplySTDtoNoCall().toString());
        }
    }

    private void assertAgreementFixedTriggerIMRounding(RoundingDatail roundingDatail) throws Exception {
        if (roundingDatail.getDelivery() != null) {
            validateThat("as.fixed.trigger.im.rounding.delivery").innerText().isEqualToIgnoringWhitespace(
                    "Delivery - " + roundingDatail.getDelivery().getRealValue());
        }
        if (roundingDatail.getRecall() != null) {
            validateThat("as.fixed.trigger.im.rounding.recall").innerText().isEqualToIgnoringWhitespace(
                    "Recall - " + roundingDatail.getRecall().getRealValue());
        }
        if (roundingDatail.getExposureStd() != null) {
            validateThat("as.fixed.trigger.im.rounding.exposure.std").innerText().isEqualToIgnoringWhitespace(
                    "Exposure STD - " + roundingDatail.getExposureStd().getRealValue());
        }
        if (roundingDatail.isApplySTDtoNoCall() != null) {
            validateThat("as.fixed.trigger.im.rounding.apply.std.to.no.call").innerText().isEqualToIgnoringWhitespace(
                    "Apply STD to No Call - " + roundingDatail.isApplySTDtoNoCall().toString());
        }
    }

    private void assertAgreeementPrincipalAndCounterpartyEligibilityRules(List<PartyEligibilityRule> partyEligibilityRuleList) throws Exception {
        if (partyEligibilityRuleList != null && partyEligibilityRuleList.size() > 0) {
            for (int i = 0; i < partyEligibilityRuleList.size(); i++) {
                if (partyEligibilityRuleList.get(i).getAssetClass() != null) {
                    validateThat("as.collateral.pc.eligibility.rules.asset.class",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            partyEligibilityRuleList.get(i).getAssetClass().getRealValue());
                }
                if (partyEligibilityRuleList.get(i).getAssetType() != null) {
                    validateThat("as.collateral.pc.eligibility.rules.asset.type",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            partyEligibilityRuleList.get(i).getAssetType().getRealValue());
                }
                if (partyEligibilityRuleList.get(i).getApplicableParty() != null)
                    validateThat("as.collateral.pc.eligibility.rules.applicable.party",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringCase(
                            partyEligibilityRuleList.get(i).getApplicableParty().getRealValue()
                    );
                if (partyEligibilityRuleList.get(i).getMinimumEligibility() != null && partyEligibilityRuleList.get(
                        i).getMinimumEligibility().size() > 0) {
                    StringBuffer sb = new StringBuffer();
                    for (StringType stringType : partyEligibilityRuleList.get(i).getMinimumEligibility()) {
                        sb.append(stringType.getRealValue()).append(",");
                    }
                    String sbValue = sb.toString();
                    validateThat("as.collateral.pc.eligibility.rules.minimum.eligibility",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            sbValue.substring(0, sbValue.length() - 1));
                }
                if (partyEligibilityRuleList.get(i).getMinimumEligibilityCqs() != null) {
                    validateThat("as.collateral.pc.eligibility.rules.minimum.eligibility.cqs",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            partyEligibilityRuleList.get(i).getMinimumEligibilityCqs().getRealValue());
                }
                if (partyEligibilityRuleList.get(i).getRatingMethod() != null) {
                    validateThat("as.collateral.pc.eligibility.rules.rating.method",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            partyEligibilityRuleList.get(i).getRatingMethod().getRealValue());
                }
                if (partyEligibilityRuleList.get(i).getEligibleCurrencies() != null && partyEligibilityRuleList.get(
                        i).getEligibleCurrencies().size() > 0) {
                    StringBuffer sb = new StringBuffer();
                    for (StringType stringType : partyEligibilityRuleList.get(i).getEligibleCurrencies()) {
                        sb.append(stringType.getRealValue()).append(",");
                    }
                    String sbValue = sb.toString();
                    validateThat("as.collateral.pc.eligibility.rules.eligible.currencies",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            sbValue.substring(0, sbValue.length() - 1));
                }

                if (partyEligibilityRuleList.get(i).getEligibleCountries() != null && partyEligibilityRuleList.get(
                        i).getEligibleCountries().size() > 0) {
                    StringBuffer sb = new StringBuffer();
                    for (StringType stringType : partyEligibilityRuleList.get(i).getEligibleCountries()) {
                        sb.append(stringType.getRealValue()).append(",");
                    }
                    String sbValue = sb.toString();
                    validateThat("as.collateral.pc.eligibility.rules.eligible.countries",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            sbValue.substring(0, sbValue.length() - 1));
                }
                if (partyEligibilityRuleList.get(i).getIssueDateAfter() != null) {
                    validateThat("as.collateral.pc.eligibility.rules.issue.date.after",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            partyEligibilityRuleList.get(i).getIssueDateAfter().getRealValue());
                }
                if (partyEligibilityRuleList.get(i).getMaturityLessThan() != null) {
                    validateThat("as.collateral.pc.eligibility.rules.maturity.less.than",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            partyEligibilityRuleList.get(i).getMaturityLessThan().getRealValue());
                }
                if (partyEligibilityRuleList.get(i).getEligibleIssuer() != null && partyEligibilityRuleList.get(
                        i).getEligibleIssuer().size() > 0) {
                    StringBuffer sb = new StringBuffer();
                    for (StringType stringType : partyEligibilityRuleList.get(i).getEligibleIssuer()) {
                        sb.append(stringType.getRealValue()).append(",");
                    }
                    String sbValue = sb.toString();
                    validateThat("as.collateral.pc.eligibility.rules.eligible.issuer",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            sbValue.substring(0, sbValue.length() - 1));
                }
                if (partyEligibilityRuleList.get(i).getExcludeInstrument() != null && partyEligibilityRuleList.get(
                        i).getExcludeInstrument().size() > 0) {
                    StringBuffer sb = new StringBuffer();
                    for (StringType stringType : partyEligibilityRuleList.get(i).getExcludeInstrument()) {
                        sb.append(stringType.getRealValue()).append(",");
                    }
                    String sbValue = sb.toString();
                    validateThat("as.collateral.pc.eligibility.rules.exclude.instrument",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            sbValue.substring(0, sbValue.length() - 1));
                }
                if (partyEligibilityRuleList.get(i).getRoleCorrelation() != null && partyEligibilityRuleList.get(
                        i).getRoleCorrelation().size() > 0) {
                    StringBuffer sb = new StringBuffer();
                    for (StringType stringType : partyEligibilityRuleList.get(i).getRoleCorrelation()) {
                        sb.append(stringType.getRealValue()).append(",");
                    }
                    String sbValue = sb.toString();
                    validateThat("as.collateral.pc.eligibility.rules.role.correlation",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            sbValue.substring(0, sbValue.length() - 1));
                }
                if (partyEligibilityRuleList.get(i).getUltimateParentCorrelation() != null) {
                    validateThat("as.collateral.pc.eligibility.rules.ultimate.parent.correlation",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            partyEligibilityRuleList.get(i).getUltimateParentCorrelation().getRealValue());
                }
                if (partyEligibilityRuleList.get(i).getCloseLinkCorrelation() != null) {
                    validateThat("as.collateral.pc.eligibility.rules.close.link.correlation",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            partyEligibilityRuleList.get(i).getCloseLinkCorrelation().getRealValue());
                }
                if (partyEligibilityRuleList.get(i).getIndustryCorrelation() != null) {
                    validateThat("as.collateral.pc.eligibility.rules.industry.colleration",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            partyEligibilityRuleList.get(i).getIndustryCorrelation().getRealValue());
                }
                if (partyEligibilityRuleList.get(i).getUdfRules() != null) {
                    validateThat("as.collateral.pc.eligibility.rules.udf.rules",
                            String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
                            partyEligibilityRuleList.get(i).getUdfRules().getRealValue());
                }
            }
        }
    }

    private void assertAgreementPrincipalAndCounterpartyHairutAdjustmentRules(List<PartyHaircutRule> partyHaircutRuleList) throws Exception {
        if (partyHaircutRuleList != null && !partyHaircutRuleList.isEmpty()) {
            for (PartyHaircutRule partyHaircutRule : partyHaircutRuleList) {
                validateThat("as.collateral.pc.collateral.haircut.adjustment.rules.row",
                        getHaircutAdjustmentRule(partyHaircutRule)).displayed().isTrue();
            }
        }
    }

    private String getHaircutAdjustmentRule(PartyHaircutRule partyHaircutRule) throws Exception {
        DecimalFormat format = new DecimalFormat();
        format.applyPattern("0.00");
        StringBuffer xpath = new StringBuffer();
        Method[] methods = partyHaircutRule.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("get")
                    && method.getReturnType() == StringType.class
                    && method.invoke(partyHaircutRule) != null) {
                StringType stringType = (StringType) method.invoke(partyHaircutRule);
                switch (method.getName()) {
                    case "getAssetClass":
                        xpath.append("[td[contains(text()[2],'").append(stringType.getRealValue()).append("')]]");
//                    xpath.append("[td[contains(text()[2],'" + stringType.getRealValue() + "') and string-length(normalize-space(text()[2]))=string-length('" + stringType.getRealValue() + "')+1]]");
                        break;
                    case "getAssetType":
                        xpath.append("[td[contains(.,'").append(stringType.getRealValue()).append("')]]");
//                    xpath.append("[td[contains(.,'" + stringType.getRealValue() + "') and string-length(normalize-space(.))=string-length('" + stringType.getRealValue() + "')+1]]");
                        break;
                    case "getValuePercentage":
                        xpath.append("[td/table/tbody/tr/td[contains(.,'").append(
                                format.format(Float.parseFloat(stringType.getRealValue()))).append("')]]");
//                    xpath.append("[td/table/tbody/tr/td[contains(.,'" + format.format(Float.parseFloat(stringType.getRealValue())) + "') and string-length(normalize-space(.))=string-length('" + format.format(Float.parseFloat(stringType.getRealValue())) + "')+1]]");
                        break;
                    default:
                        xpath.append("[td/table/tbody/tr/td[contains(normalize-space(.)," + "normalize-space('").append(
                                stringType.getRealValue()).append("'))]]");
//                    xpath.append("[td/table/tbody/tr/td[contains(.,'" + stringType.getRealValue() + "') and string-length(normalize-space(.))=string-length('" + stringType.getRealValue() + "')+1]]");
                        break;
                }
            }
        }
        return xpath.toString();
    }

    private void assertAgreementPrincipalAndCounterpartyCollateralEligibleForReceipts(List<PartyEligibleForReceipt> partyEligibleForReceipt) throws Exception {
        if (partyEligibleForReceipt != null && partyEligibleForReceipt.size() > 0) {
//            DecimalFormat format = new DecimalFormat();
            for (PartyEligibleForReceipt pefr : partyEligibleForReceipt) {
                validateThat("as.collateral.pc.collateral.eligible.for.receipt.row",
                        getpartyEligibleForReceiptRow(pefr)).displayed().isTrue();
            }

//            for (int i = 0; i < partyEligibleForReceipt.size(); i++) {
//                if (partyEligibleForReceipt.get(i).getAssetClass() != null) {
//                    validateThat("as.collateral.pc.collateral.eligible.for.receipt.asset.class", String.valueOf(i * 2 + 2)).innerText().isEqualToIgnoringWhitespace(
//                            partyEligibleForReceipt.get(i).getAssetClass().getRealValue());
//                }
//                if (partyEligibleForReceipt.get(i).getAssetType() != null) {
//                    validateThat("as.collateral.pc.collateral.eligible.for.receipt.asset.type", String.valueOf(i * 2 + 2)).innerText().isEqualToIgnoringWhitespace(
//                            partyEligibleForReceipt.get(i).getAssetType().getRealValue());
//                }
//                if (partyEligibleForReceipt.get(i).getTemplateVsAgreementEligibilityRules() != null) {
//                    validateThat("as.collateral.pc.collateral.eligible.for.receipt.templateVsAgreementEligibilityRules", String.valueOf(i * 2 + 2)).innerText().isEqualToIgnoringWhitespace(partyEligibleForReceipt.get(i).getTemplateVsAgreementEligibilityRules().getRealValue());
//                }
//                if (partyEligibleForReceipt.get(i).getNotes() != null) {
//                    validateThat("as.collateral.pc.collateral.eligible.for.receipt.notes", String.valueOf(i * 2 + 2)).innerText().isEqualToIgnoringWhitespace(partyEligibleForReceipt.get(i).getNotes().getRealValue());
//                }
//                if (partyEligibleForReceipt.get(i).getSettlementPeriod() != null) {
//                    validateThat("as.collateral.pc.collateral.eligible.for.receipt.settlementPeriod", String.valueOf(i * 2 + 2)).innerText().isEqualToIgnoringWhitespace(partyEligibleForReceipt.get(i).getSettlementPeriod().getRealValue());
//                }
//                if (partyEligibleForReceipt.get(i).getDeliveryPriority() != null) {
//                    validateThat("as.collateral.pc.collateral.eligible.for.receipt.deliveryPriority", String.valueOf(i * 2 + 2)).innerText().isEqualToIgnoringWhitespace(partyEligibleForReceipt.get(i).getDeliveryPriority().getRealValue());
//                }
//                if (partyEligibleForReceipt.get(i).getRecallPriority() != null) {
//                    validateThat("as.collateral.pc.collateral.eligible.for.receipt.recallPriority", String.valueOf(i * 2 + 2)).innerText().isEqualToIgnoringWhitespace(partyEligibleForReceipt.get(i).getRecallPriority().getRealValue());
//                }
//                if (partyEligibleForReceipt.get(i).getContractualReuseRights() != null) {
//                    validateThat("as.collateral.pc.collateral.eligible.for.receipt.contractualReuseRights", String.valueOf(i * 2 + 2)).innerText().isEqualToIgnoringWhitespace(partyEligibleForReceipt.get(i).getContractualReuseRights().getRealValue());
//                }
//                if (partyEligibleForReceipt.get(i).getInternalPolicyPermitsReuse() != null) {
//                    validateThat("as.collateral.pc.collateral.eligible.for.receipt.internalPolicyPermitsReuse", String.valueOf(i * 2 + 2)).innerText().isEqualToIgnoringWhitespace(partyEligibleForReceipt.get(i).getInternalPolicyPermitsReuse().getRealValue());
//                }
//                if (partyEligibleForReceipt.get(i).getRules() != null) {
//                    validateThat("as.collateral.pc.collateral.eligible.for.receipt.rules", String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(partyEligibleForReceipt.get(i).getRules().getRealValue());
//                }
//                if (partyEligibleForReceipt.get(i).getValuationPercentage() != null) {
//                    format.applyPattern("###,###,###,##0.00");
//                    validateThat("as.collateral.pc.collateral.eligible.for.receipt.valuation", String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(
//                            format.format(Double.parseDouble(partyEligibleForReceipt.get(i).getValuationPercentage().getRealValue()))
//                    );
//                }
//                if (partyEligibleForReceipt.get(i).getValuationBasis() != null) {
//                    validateThat("as.collateral.pc.collateral.eligible.for.receipt.valuationBasis", String.valueOf(i + 2)).innerText().isEqualToIgnoringWhitespace(partyEligibleForReceipt.get(i).getValuationBasis().getRealValue());
//                }
//                if (partyEligibleForReceipt.get(i).getRate() != null) {
//                    validateThat("as.collateral.pc.collateral.eligible.for.receipt.rate", String.valueOf(i * 2 + 2)).innerText().isEqualToIgnoringWhitespace(partyEligibleForReceipt.get(i).getRate().getRealValue());
//                }
//                if (partyEligibleForReceipt.get(i).getBasisPointSpread() != null) {
//                    validateThat("as.collateral.pc.collateral.eligible.for.receipt.basisPointSpread", String.valueOf(i * 2 + 2)).innerText().isEqualToIgnoringWhitespace(partyEligibleForReceipt.get(i).getBasisPointSpread().getRealValue());
//                }
//                if (partyEligibleForReceipt.get(i).getReinvestRate() != null) {
//                    validateThat("as.collateral.pc.collateral.eligible.for.receipt.reinvestRate", String.valueOf(i * 2 + 2)).innerText().isEqualToIgnoringWhitespace(partyEligibleForReceipt.get(i).getReinvestRate().getRealValue());
//                }
//                if (partyEligibleForReceipt.get(i).getWithholdingTaxRate() != null) {
//                    validateThat("as.collateral.pc.collateral.eligible.for.receipt.withholdingTaxRate", String.valueOf(i * 2 + 2)).innerText().isEqualToIgnoringWhitespace(partyEligibleForReceipt.get(i).getWithholdingTaxRate().getRealValue());
//                }
//                if (partyEligibleForReceipt.get(i).getWithholdingApplied() != null) {
//                    validateThat("as.collateral.pc.collateral.eligible.for.receipt.withholdingApplied", String.valueOf(i * 2 + 2)).innerText().isEqualToIgnoringWhitespace(partyEligibleForReceipt.get(i).getWithholdingApplied().getRealValue());
//                }
//                if (partyEligibleForReceipt.get(i).getInterestAppliedParty() != null) {
//                    validateThat("as.collateral.pc.collateral.eligible.for.receipt.interestAppliedParty", String.valueOf(i * 2 + 2)).innerText().isEqualToIgnoringWhitespace(partyEligibleForReceipt.get(i).getInterestAppliedParty().getRealValue());
//                }
//                if (partyEligibleForReceipt.get(i).getConcLimitRuleSet() != null) {
//                    validateThat("as.collateral.pc.collateral.eligible.for.receipt.concLimitRuleSet", String.valueOf(i * 2 + 2)).innerText().isEqualToIgnoringWhitespace(partyEligibleForReceipt.get(i).getConcLimitRuleSet().getRealValue());
//                }
//                if (partyEligibleForReceipt.get(i).getAutoBooking() != null) {
//                    validateThat("as.collateral.pc.collateral.eligible.for.receipt.autoBooking", String.valueOf(i * 2 + 2)).innerText().isEqualToIgnoringWhitespace(partyEligibleForReceipt.get(i).getAutoBooking().getRealValue());
//                }
//            }
        }
    }

    private String getpartyEligibleForReceiptRow(PartyEligibleForReceipt partyEligibleForReceipt) throws Exception {
        StringBuffer xpath = new StringBuffer();
        DecimalFormat format = new DecimalFormat();
        format.applyPattern("###,###,###,##0.00");
        Method[] methods = partyEligibleForReceipt.getClass().getMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("get")
                    && method.invoke(partyEligibleForReceipt) != null
                    && method.getReturnType() == StringType.class) {
                if (method.getName().equals("getAssetClass") || method.getName().equals("getRules")) {
                    xpath.append("[td[contains(normalize-space(.),'").append(
                            ((StringType) method.invoke(partyEligibleForReceipt)).getRealValue()).append("')]]");
                } else {
                    String value = ((StringType) method.invoke(partyEligibleForReceipt)).getRealValue();
                    if (method.getName().equals("getValuationPercentage"))
                        value = format.format(Double.parseDouble(value));
                    xpath.append("[td[normalize-space(.)='").append(value).append("']]");
                }
            }
        }
        return xpath.toString();
    }

    private void assertAgreeementCollateralOfCollateralRules(CollateralRules collateralRules) throws Exception {
        DecimalFormat format = new DecimalFormat();
        if (collateralRules.getEnableInterestCalculation() != null) {
            validateThat("as.collateral.enable.interest.calculation").innerText().isEqualToIgnoringWhitespace(
                    "Enable Interest Calculation - " + collateralRules.getEnableInterestCalculation().getRealValue());
        }
        if (collateralRules.getInterestCalculationsMethod() != null) {
            validateThat("as.collateral.interest.calculation.method").innerText().isEqualToIgnoringWhitespace(
                    "Interest Calculations Method - " + collateralRules.getInterestCalculationsMethod().getRealValue());
        }
        if (collateralRules.getCalculationRule() != null) {
            validateThat("as.collateral.calculation.rule").innerText().isEqualToIgnoringWhitespace(
                    "Calculation Rule - " + collateralRules.getCalculationRule().getRealValue());
        }
        if (collateralRules.getPayMethod() != null) {
            validateThat("as.collateral.pay.method").innerText().isEqualToIgnoringWhitespace(
                    "Pay Method - " + collateralRules.getPayMethod().getRealValue());
        }
        if (collateralRules.getReceiveMethod() != null) {
            validateThat("as.collateral.receive.method").innerText().isEqualToIgnoringWhitespace(
                    "Receive Method - " + collateralRules.getReceiveMethod().getRealValue());
        }
        if (collateralRules.getEnableVMInterestCalculation() != null) {
            validateThat("as.collateral.enable.vm.interest.calculation").innerText().isEqualToIgnoringWhitespace(
                    "Enable VM Interest Calculation - " + collateralRules.getEnableVMInterestCalculation().getRealValue());
        }
        if (collateralRules.getEnableIMInterestCalculation() != null) {
            validateThat("as.collateral.enable.im.interest.calculation").innerText().isEqualToIgnoringWhitespace(
                    "Enable IM Interest Calculation - " + collateralRules.getEnableIMInterestCalculation().getRealValue());
        }
        if (collateralRules.getVminterestCalculationsMethod() != null) {
            validateThat("as.collateral.vm.interest.calculation.method").innerText().isEqualToIgnoringWhitespace(
                    "VM Interest Calculations Method - " + collateralRules.getVminterestCalculationsMethod().getRealValue());
        }
        if (collateralRules.getVmCalculationRule() != null) {
            validateThat("as.collateral.vm.calculation.rule").innerText().isEqualToIgnoringWhitespace(
                    "VM Calculation Rule - " + collateralRules.getVmCalculationRule().getRealValue());
        }
        if (collateralRules.getVmPayMethod() != null) {
            validateThat("as.collateral.vm.pay.method").innerText().isEqualToIgnoringWhitespace(
                    "VM Pay Method - " + collateralRules.getVmPayMethod().getRealValue());
        }
        if (collateralRules.getVmReceiveMethod() != null) {
            validateThat("as.collateral.vm.receive.method").innerText().isEqualToIgnoringWhitespace(
                    "VM Receive Method - " + collateralRules.getVmReceiveMethod().getRealValue());
        }
        //im
        if (collateralRules.getImInterestCalculationsMethod() != null) {
            validateThat("as.collateral.im.interest.calculation.method").innerText().isEqualToIgnoringWhitespace(
                    "IM Interest Calculations Method - " + collateralRules.getImInterestCalculationsMethod().getRealValue());
        }
        if (collateralRules.getImCalculationRule() != null) {
            validateThat("as.collateral.im.calculation.rule").innerText().isEqualToIgnoringWhitespace(
                    "IM Calculation Rule - " + collateralRules.getImCalculationRule().getRealValue());
        }
        if (collateralRules.getImPayMethod() != null) {
            validateThat("as.collateral.im.pay.method").innerText().isEqualToIgnoringWhitespace(
                    "IM Pay Method - " + collateralRules.getImPayMethod().getRealValue());
        }
        if (collateralRules.getImReceiveMethod() != null) {
            validateThat("as.collateral.im.receive.method").innerText().isEqualToIgnoringWhitespace(
                    "IM Receive Method - " + collateralRules.getImReceiveMethod().getRealValue());
        }

        if (collateralRules.getInterestSettlementDate() != null) {
            validateThat("as.collateral.interest.settlement.date").innerText().isEqualToIgnoringWhitespace(
                    "Interest Settlement Date - " + collateralRules.getInterestSettlementDate().getRealValue());
        }
        if (collateralRules.getAllowNegativeEffectiveRate() != null) {
            validateThat("as.collateral.allow.negative.effective.rate").innerText().isEqualToIgnoringWhitespace(
                    "Allow Negative Effective Rate - " + collateralRules.getAllowNegativeEffectiveRate().getRealValue());
        }
        if (collateralRules.getConcentrationLimitTrigger() != null) {
            format.applyPattern("###,###,###,##0.00");
            validateThat("as.collateral.concentration.limit.trigger").innerText().isEqualToIgnoringWhitespace(
                    "Concentration Limit Trigger - " +
                            format.format(
                                    Double.parseDouble(collateralRules.getConcentrationLimitTrigger().getRealValue()))
            );
        }
        if (collateralRules.getConcentrationLimitBreachAdjustment() != null) {
            if (collateralRules.getConcentrationLimitBreachAdjustment().isApply() != null && !collateralRules.getConcentrationLimitBreachAdjustment().isApply()) {
                validateThat("as.collateral.concentration.limit.breach.adjustment").displayed().isFalse();
            } else {
                validateThat(
                        "as.collateral.concentration.limit.breach.adjustment").innerText().isEqualToIgnoringWhitespace(
                        "Concentration Limit Breach Adjustment - " + collateralRules.getConcentrationLimitBreachAdjustment().getRealValue());
            }
        }
        if (collateralRules.getRatingMethod() != null) {
            validateThat("as.collateral.rating.method").innerText().isEqualToIgnoringWhitespace(
                    "Rating Method - " + collateralRules.getRatingMethod().getRealValue());
        }
        if (collateralRules.getEligibilityRuleTemplate() != null) {
            validateThat("as.collateral.eligibility.rule.template").innerText().isEqualToIgnoringWhitespace(
                    "Eligibility Rule Template - " + collateralRules.getEligibilityRuleTemplate().getRealValue());
        }
        if (collateralRules.getGracePeriod() != null) {
            validateThat("as.collateral.grace.period").innerText().isEqualToIgnoringWhitespace(
                    "Grace Period - " + collateralRules.getGracePeriod().getRealValue());
        }
        if (collateralRules.getEnableApplicableAgencies() != null) {
            validateThat("as.collateral.enable.applicable.agencies").innerText().isEqualToIgnoringWhitespace(
                    "Enable Applicable Agencies - " + collateralRules.getEnableApplicableAgencies().getRealValue());
        }
        if (collateralRules.getReferenceRatingAgencies() != null && collateralRules.getReferenceRatingAgencies().size() > 0) {
            StringBuffer sb = new StringBuffer();
            for (StringType agency : collateralRules.getReferenceRatingAgencies()) {
                sb.append(agency.getRealValue()).append(",");
            }
            String sbValue = sb.toString();
            validateThat("as.collateral.reference.rating.agencies").innerText().isEqualToIgnoringWhitespace(
                    "Reference Rating Agencies - " + sbValue.substring(0, sbValue.length() - 1));
        }
        if (collateralRules.getDebtClassification() != null) {
            validateThat("as.collateral.debt.classification").innerText().isEqualToIgnoringWhitespace(
                    "Debt Classification - " + collateralRules.getDebtClassification().getRealValue());
        }
        if (collateralRules.getSelectionOfAgency() != null) {
            validateThat("as.collateral.selection.of.agency").innerText().isEqualToIgnoringWhitespace(
                    "Selection of Agency - " + collateralRules.getSelectionOfAgency().getRealValue());
        }
        if (collateralRules.getApplicationOfRating() != null) {
            validateThat("as.collateral.application.of.ratings").innerText().isEqualToIgnoringWhitespace(
                    "Application of Ratings - " + collateralRules.getApplicationOfRating().getRealValue());
        }
        if (collateralRules.getExposureCushionRule() != null) {
            validateThat("as.collateral.exposure.cushion.rule").innerText().isEqualToIgnoringWhitespace(
                    "Exposure Cushion Rule - " + collateralRules.getExposureCushionRule().getRealValue());
        }
        if (collateralRules.getEcPercentage() != null) {
            validateThat("as.collateral.ec.percentage").innerText().isEqualToIgnoringWhitespace(
                    "EC Percentage - " + collateralRules.getEcPercentage().getRealValue());
        }
        if (collateralRules.getEcFixedValue() != null) {
            validateThat("as.collateral.ec.fixed.value").innerText().isEqualToIgnoringWhitespace(
                    "EC Fixed Value - " + collateralRules.getEcFixedValue().getRealValue());
        }
        if (collateralRules.getEcCalculationMethod() != null) {
            validateThat("as.collateral.ec.calculation.method").innerText().isEqualToIgnoringWhitespace(
                    "EC Calculation Method - " + collateralRules.getEcCalculationMethod().getRealValue());
        }
        if (collateralRules.getEcAssetClassApplicability() != null && collateralRules.getEcAssetClassApplicability().size() > 0) {
            StringBuffer sb = new StringBuffer();
            for (StringType applability : collateralRules.getEcAssetClassApplicability()) {
                sb.append(applability.getRealValue()).append(",");
            }
            String sbValue = sb.toString();
            validateThat("as.collateral.ec.asset.class.applicability").innerText().isEqualToIgnoringWhitespace(
                    "EC Asset Class Applicability - " + sbValue.substring(0, sbValue.length() - 1));
        }
        if (collateralRules.getEcDirection() != null && collateralRules.getEcDirection().size() > 0) {
            StringBuffer sb = new StringBuffer();
            for (StringType stringType : collateralRules.getEcDirection()) {
                sb.append(stringType.getRealValue()).append(",");
            }
            String sbValue = sb.toString();
            validateThat("as.collateral.ec.direction").innerText().isEqualToIgnoringWhitespace(
                    "EC Direction - " + sbValue.substring(0, sbValue.length() - 1));
        }
    }

    private void assertAgreementProductsTab(CollateralisedPortfolioDetails details) throws Exception {
        if (details.getDefaultTradeValuationRule() != null) {
            validateThat("as.products.default.trade.valuation.rule").innerText().isEqualToIgnoringWhitespace(
                    "Default Trade Valuation Rule - " + details.getDefaultTradeValuationRule().getRealValue());
        }
        if (details.getTradeMappingRules() != null) {
            if (details.getTradeMappingRules().getTradeMappingRuleDetail() != null && details.getTradeMappingRules().getTradeMappingRuleDetail().size() > 0) {
                StringBuffer sb = new StringBuffer();
                for (TradeMappingRuleDetail tradeMappingRuleDetail : details.getTradeMappingRules().getTradeMappingRuleDetail()) {
                    if (tradeMappingRuleDetail.getField() != null) {
                        sb.append("(").append(tradeMappingRuleDetail.getField().getRealValue()).append(" ");
                    }
                    if (tradeMappingRuleDetail.getRule() != null) {
                        sb.append(tradeMappingRuleDetail.getRule().getRealValue()).append(" ");
                    }
                    if (tradeMappingRuleDetail.getValue() != null) {
                        sb.append(tradeMappingRuleDetail.getValue().getRealValue()).append(")");
                    }
                    if (tradeMappingRuleDetail.getOperate() != null) {
                        sb.append(" ").append(tradeMappingRuleDetail.getOperate().getRealValue()).append(" ");
                    }
                }
                String sbValue = sb.toString().trim();
                validateThat("as.products.default.trade.mapping.rule").innerText().isEqualToIgnoringWhitespace(
                        "Trade Mapping Rule - " + sbValue);
            }

        }

        if (details.getSharesPerCreationUnit() != null) {
            validateThat("as.products.shares.per.creation.unit").innerText().isEqualToIgnoringWhitespace(
                    "Shares per Creation Unit - " + details.getSharesPerCreationUnit().getRealValue());
        }

        if (details.getProduct() != null && details.getProduct().size() > 0) {
            for (int i = 0; i < details.getProduct().size(); i++) {
                if (details.getProduct().get(i).getProductName() != null) {
                    validateThat("as.products.productName", details.getProduct().get(
                            i).getProductName().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                            details.getProduct().get(i).getProductName().getRealValue());
                    if (details.getProduct().get(i).getExposurePercentageMTM() != null) {
                        validateThat("as.products.exposure.percentage.mtm", details.getProduct().get(
                                i).getProductName().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                                details.getProduct().get(i).getExposurePercentageMTM().getRealValue());
                    }
                    if (details.getProduct().get(i).getSettlementPeriod() != null) {
                        validateThat("as.products.settlement.period", details.getProduct().get(
                                i).getProductName().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                                details.getProduct().get(i).getSettlementPeriod());
                    }
                    if (details.getProduct().get(i).getUpfrontCalculationMethodology() != null) {
                        validateThat("as.products.upfont.calculation.methodology", details.getProduct().get(
                                i).getProductName().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                                details.getProduct().get(i).getUpfrontCalculationMethodology().value());
                    }
                    if (details.getProduct().get(i).getExternalIADetail() != null) {
                        if (details.getProduct().get(
                                i).getExternalIADetail().isPrincipalUpfronts() != null && details.getProduct().get(
                                i).getExternalIADetail().isPrincipalUpfronts()) {
                            validateThat("as.products.upfont.party", details.getProduct().get(
                                    i).getProductName().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                                    "Princ");
                        } else if (details.getProduct().get(
                                i).getExternalIADetail().isCptyUpfronts() != null && details.getProduct().get(
                                i).getExternalIADetail().isCptyUpfronts()) {
                            validateThat("as.products.upfont.party", details.getProduct().get(
                                    i).getProductName().getRealValue()).innerText().isEqualToIgnoringWhitespace("Cpty");
                        }
                        if (details.getProduct().get(i).getExternalIADetail().getFixedPercentage() != null) {
                            validateThat("as.products.fixed.percentage", details.getProduct().get(
                                    i).getProductName().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                                    details.getProduct().get(
                                            i).getExternalIADetail().getFixedPercentage().getRealValue());
                        }
                        if (details.getProduct().get(i).getExternalIADetail().getFixedValue() != null) {
                            validateThat("as.products.fixed.amount", details.getProduct().get(
                                    i).getProductName().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                                    details.getProduct().get(i).getExternalIADetail().getFixedValue().getRealValue());
                        }
                    }
                }
            }
        }

    }

    private void assertAgreementCallScheduleTab(ValuationFrequencyAndCallScheduleDetails details) throws Exception {
        if (details.getLegalReviewFrequency() != null) {
            if (details.getLegalReviewFrequency().getLegalReviewFrequency() != null) {
                validateThat("as.callschedule.legal.review.frequecy").innerText().contains(
                        "Legal Review Frequency - " + details.getLegalReviewFrequency().getLegalReviewFrequency().getRealValue().replace(
                                ".", ":"));
            }
            if (details.getLegalReviewFrequency().getRollForward() != null) {
                validateThat("as.callschedule.legal.review.frequecy").innerText().contains(
                        "Roll Forward:  " + details.getLegalReviewFrequency().getRollForward().getRealValue());
            }
            if (details.getLegalReviewFrequency().getRollBackward() != null) {
                validateThat("as.callschedule.legal.review.frequecy").innerText().contains(
                        "Roll Backward:  " + details.getLegalReviewFrequency().getRollBackward().getRealValue());
            }
            if (details.getLegalReviewFrequency().getFrequencyStart() != null) {
                validateThat("as.callschedule.legal.review.frequecy").innerText().contains(
                        "Frequency Start:" + details.getLegalReviewFrequency().getFrequencyStart().getRealValue());
            }
        }
        if (details.getNotificationTime() != null) {
            validateThat("as.callschedule.notification.time").innerText().isEqualToIgnoringWhitespace(
                    "Notification Time - " + details.getNotificationTime().getRealValue().replace(".", ":"));
        }
        if (details.getResolutionTime() != null) {
            if (details.getResolutionTime().getResolutionTime() != null) {
                validateThat("as.callschedule.resolution.time").innerText().contains("" +
                        "Resolution Time - " + details.getResolutionTime().getResolutionTime().getRealValue().replace(
                        ".", ":"));
            }
            if (details.getResolutionTime().getResolutionTimeType() != null) {
                validateThat("as.callschedule.resolution.time").innerText().contains(
                        details.getResolutionTime().getResolutionTime().getRealValue().replace(".", ":"));
            }
        }

        //vm im sepereated
        if (details.getVmLegalReviewFrequency() != null) {
            if (details.getVmLegalReviewFrequency().getLegalReviewFrequency() != null) {
                validateThat("as.callschedule.vm.legal.review.frequecy").innerText().contains(
                        "VM Legal Review Frequency - " + details.getVmLegalReviewFrequency().getLegalReviewFrequency().getRealValue().replace(
                                ".", ":"));
            }
            if (details.getVmLegalReviewFrequency().getRollForward() != null) {
                validateThat("as.callschedule.vm.legal.review.frequecy").innerText().contains(
                        "Roll Forward:  " + details.getVmLegalReviewFrequency().getRollForward().getRealValue());
            }
            if (details.getVmLegalReviewFrequency().getRollBackward() != null) {
                validateThat("as.callschedule.vm.legal.review.frequecy").innerText().contains(
                        "Roll Backward:  " + details.getVmLegalReviewFrequency().getRollBackward().getRealValue());
            }
            if (details.getVmLegalReviewFrequency().getFrequencyStart() != null) {
                validateThat("as.callschedule.vm.legal.review.frequecy").innerText().contains(
                        "Frequency Start:" + details.getVmLegalReviewFrequency().getFrequencyStart().getRealValue());
            }
        }

        if (details.getVmNotificationTime() != null) {
            validateThat("as.callschedule.vm.notification.time").innerText().isEqualToIgnoringWhitespace(
                    "VM Notification Time - " + details.getVmNotificationTime().getRealValue().replace(".", ":"));
        }
        if (details.getVmResolutionTime() != null) {
            if (details.getVmResolutionTime().getResolutionTime() != null) {
                validateThat("as.callschedule.vm.resolution.time").innerText().contains(
                        "VM Resolution Time - " + details.getVmResolutionTime().getResolutionTime().getRealValue().replace(
                                ".", ":"));
            }
            if (details.getVmResolutionTime().getResolutionTimeType() != null) {
                validateThat("as.callschedule.vm.resolution.time").innerText().contains(
                        details.getVmResolutionTime().getResolutionTimeType().getRealValue().replace(".", ":"));
            }
        }

        if (details.getImLegalReviewFrequency() != null) {
            if (details.getImLegalReviewFrequency().getLegalReviewFrequency() != null) {
                validateThat("as.callschedule.im.legal.review.frequecy").innerText().contains(
                        "IM Legal Review Frequency - " + details.getImLegalReviewFrequency().getLegalReviewFrequency().getRealValue().replace(
                                ".", ":"));
            }
            if (details.getImLegalReviewFrequency().getRollForward() != null) {
                validateThat("as.callschedule.im.legal.review.frequecy").innerText().contains(
                        "Roll Forward:  " + details.getImLegalReviewFrequency().getRollForward().getRealValue());
            }
            if (details.getImLegalReviewFrequency().getRollBackward() != null) {
                validateThat("as.callschedule.im.legal.review.frequecy").innerText().contains(
                        "Roll Backward:  " + details.getImLegalReviewFrequency().getRollBackward().getRealValue());
            }
            if (details.getImLegalReviewFrequency().getFrequencyStart() != null) {
                validateThat("as.callschedule.im.legal.review.frequecy").innerText().contains(
                        "Frequency Start:" + details.getImLegalReviewFrequency().getFrequencyStart().getRealValue());
            }
        }
        if (details.getImNotificationTime() != null) {
            validateThat("as.callschedule.im.notification.time").innerText().isEqualToIgnoringWhitespace(
                    "IM Notification Time - " + details.getImNotificationTime().getRealValue().replace(".", ":"));
        }
        if (details.getImResolutionTime() != null) {
            if (details.getImResolutionTime().getResolutionTime() != null) {
                validateThat("as.callschedule.im.resolution.time").innerText().contains(
                        "IM Resolution Time - " + details.getImResolutionTime().getResolutionTime().getRealValue().replace(
                                ".", ":"));
            }
            if (details.getImResolutionTime().getResolutionTimeType() != null) {
                validateThat("as.callschedule.im.resolution.time").innerText().contains(
                        details.getImResolutionTime().getResolutionTimeType().getRealValue().replace(".", ":"));
            }
        }
        if (details.getTimeZone() != null) {
            validateThat("as.callschedule.time.zome").innerText().isEqualToIgnoringWhitespace(
                    "Time Zone - " + details.getTimeZone().getRealValue());
        }
        if (details.getHolidayCentres() != null) {
            if (details.getHolidayCentres().getHolidayCentre() != null && details.getHolidayCentres().getHolidayCentre().size() > 0) {
                StringBuffer sb = new StringBuffer();
                for (StringType centre : details.getHolidayCentres().getHolidayCentre()) {
                    sb.append(centre.getRealValue()).append(",");
                }
                String sbValue = sb.toString();
                validateThat("as.callschedule.holiday.centre").innerText().isEqualToIgnoringWhitespace(
                        "Holiday Centre(s) - " + sbValue.substring(0, sbValue.length() - 1));
            }
        }
        if (details.getClientReleaseRequiredSelfService() != null) {
            validateThat("as.callschedule.client.release.required").innerText().isEqualToIgnoringWhitespace(
                    "Client Release Required (Self Service) - " + details.getClientReleaseRequiredSelfService().getRealValue());
        }


    }

    public void enterAgreementcallScheduleSummary() throws Exception {
        element("as.callschedule.tab").click();
    }

    public void enterAgreementProductSummary() throws Exception {
        element("as.products.tab").click();
    }

    public void enterAgreementRuleTriggerSummary() throws Exception {
        element("as.rule.trigger.tab").click();
    }

    public void enterAgreementAdditionalFieldsSummary() throws Exception {
        element("as.additional.fields.tab").click();
    }

    public void enterAgreementSettlementSummary() throws Exception {
        element("as.settlement.tab").click();
    }

    public void enterAgreementReportingControlSummary() throws Exception {
        element("as.reporting.control.tab").click();
    }

    public void enterAgreementMessagingSummary() throws Exception {
        element("as.messaging.tab").click();
    }

    public void assertAgreeementCollateralSummary(AgreementSummary agmt) throws Exception {
        if (agmt.getPrincipalAndCounterpartyEligibleForReceipt() != null && agmt.getPrincipalAndCounterpartyEligibleForReceipt().size() > 0) {
            List<PartyEligibleForReceipt> PartyEligibleForReceiptList = agmt.getPrincipalAndCounterpartyEligibleForReceipt();
            for (PartyEligibleForReceipt pefr : PartyEligibleForReceiptList) {
                if (pefr.getAssetType() != null) {
                    if (pefr.getNotes() != null && !pefr.getNotes().isApply()) {
                        validateThat("ap.asset.type.first.note", pefr.getAssetType().getRealValue()).attributeValueOf(
                                "readonly");
                    }
                }
                if (pefr.getAssetType() != null) {
                    if (pefr.getSettlementPeriod() != null && !pefr.getSettlementPeriod().isApply()) {
                        validateThat("ap.asset.type.settlement.date.updated",
                                pefr.getAssetType().getRealValue()).enabled().isEqualTo(
                                !pefr.getSettlementPeriod().isApply());
                    }
                }
            }
        }
    }

    public void assertAgreementPartySummary(AgreementSummary agmt) throws Exception {

        if (agmt.getPartyAndCounterpartyDetails() != null) {
            PartyAndCounterpartyDetails details = agmt.getPartyAndCounterpartyDetails();

            if ((details.getLinkageSet() != null) && (!details.getLinkageSet().get(0).isApply())) {
                validateThat("ap.linkage.set").enabled().isEqualTo(details.getLinkageSet().get(0).isApply());
            }
        }
    }

    public void callScheduleErrorpage(AgreementSummary agmt) throws Exception {
        if (agmt.getValuationFrequencyAndCallScheduleDetails() != null) {
            validateThat("ap.callschedule.errormessage").displayed().withFailMessage(
                    "Error: You don't have the required privilege to perform this operation");
        }
    }

    public void assertcallScheduleErrorMessage(Agreement agmt) throws Exception {
        if (agmt.getErrorMessage() != null) {
            validateThat("ap.callschedule.errormessage").innerText().contains(agmt.getErrorMessage().getRealValue());
        }
    }


}
