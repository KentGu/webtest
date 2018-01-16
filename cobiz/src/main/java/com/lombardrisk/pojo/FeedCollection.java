package com.lombardrisk.pojo;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Unified Collection.
 * Created by Lawrence Xu on 5/24/2017.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"extIA", "NAV", "rating", "settlementInstruction", "portfolioTSA", "agreementUDF", "agreement", "asset",
        "assetBooking", "inventoryManager", "fxrate", "interestrate", "interestAmount", "MtM", "organisation", "holiday", "refservicedata",
        "weekend", "eligibilityTemplate", "trade", "repoSettlement", "counterpartyAmount"})
@XmlRootElement(name = "collection")
public class FeedCollection {

    protected List<FeedExternalIA> extIA;

    protected List<FeedNAV> NAV;

    protected List<FeedAgreementRating> rating;

    protected List<FeedSettlementInstruction> settlementInstruction;

    protected List<FeedPortfolioTSA> portfolioTSA;

    protected List<FeedAgreementUDF> agreementUDF;

    protected List<FeedAgreement> agreement;

    /* for asset rating/pricing, security template */
    protected List asset;

    protected List<FeedAssetBooking> assetBooking;

    protected List<FeedInventoryManager> inventoryManager;

    protected List<FeedFxRate> fxrate;

    protected List<FeedInterestRate> interestrate;

    protected List<FeedInterestAmount> interestAmount;

    protected List<FeedMtM> MtM;

    /* for org and org contact */
    protected List organisation;

    protected List<FeedHolidayCentre> holiday;

    protected List<FeedStaticData> refservicedata;

    protected List<FeedWeekend> weekend;

    protected List<FeedEligibilityRulesTemplate> eligibilityTemplate;

    /* for trade, etf trade and repo trade */
    protected List trade;

    protected List<FeedRepoSettlement> repoSettlement;

    protected List<FeedCounterpartyAmount> counterpartyAmount;

    @SuppressWarnings("unused")
    public List<FeedExternalIA> getExtIA() {
        return extIA;
    }

    public void setExtIA(List<FeedExternalIA> extIA) {
        this.extIA = extIA;
    }

    @SuppressWarnings("unused")
    public List<FeedNAV> getNAV() {
        return NAV;
    }

    public void setNAV(List<FeedNAV> NAV) {
        for (FeedNAV feedNav : NAV){
            if (feedNav.getId() != null)
                feedNav.setId(null);
        }
        this.NAV = NAV;
    }

    @SuppressWarnings("unused")
    public List<FeedAgreementRating> getRating() {
        return rating;
    }

    public void setRating(List<FeedAgreementRating> rating) {
        this.rating = rating;
    }

    @SuppressWarnings("unused")
    public List<FeedSettlementInstruction> getSettlementInstruction() {
        return settlementInstruction;
    }

    public void setSettlementInstruction(List<FeedSettlementInstruction> settlementInstruction) {
        this.settlementInstruction = settlementInstruction;
    }

    @SuppressWarnings("unused")
    public List<FeedPortfolioTSA> getPortfolioTSA() {
        return portfolioTSA;
    }

    public void setPortfolioTSA(List<FeedPortfolioTSA> portfolioTSA) {
        for (FeedPortfolioTSA feedPortfolioTSA : portfolioTSA){
            if (feedPortfolioTSA.getId() != null)
                feedPortfolioTSA.setId(null);
        }
        this.portfolioTSA = portfolioTSA;
    }

    @SuppressWarnings("unused")
    public List<FeedAgreementUDF> getAgreementUDF() {
        return agreementUDF;
    }

    public void setAgreementUDF(List<FeedAgreementUDF> agreementUDF) {
        this.agreementUDF = agreementUDF;
    }

    @SuppressWarnings("unused")
    public List<FeedAgreement> getAgreement() {
        return agreement;
    }

    public void setAgreement(List<FeedAgreement> agreement) {
        this.agreement = agreement;
    }

    @SuppressWarnings("unused")
    public List getAsset() {
        return asset;
    }

    public void setAsset(List asset) {
        this.asset = asset;
    }

    @SuppressWarnings("unused")
    public List<FeedAssetBooking> getAssetBooking() {
        return assetBooking;
    }

    public void setAssetBooking(List<FeedAssetBooking> assetBooking) {
        this.assetBooking = assetBooking;
    }

    @SuppressWarnings("unused")
    public List<FeedInventoryManager> getInventoryManager() {
        return inventoryManager;
    }

    public void setInventoryManager(List<FeedInventoryManager> inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    @SuppressWarnings("unused")
    public List<FeedFxRate> getFxrate() {
        return fxrate;
    }

    public void setFxrate(List<FeedFxRate> fxrate) {
        this.fxrate = fxrate;
    }

    @SuppressWarnings("unused")
    public List<FeedInterestRate> getInterestrate() {
        return interestrate;
    }

    public void setInterestrate(List<FeedInterestRate> interestrate) {
        this.interestrate = interestrate;
    }

    @SuppressWarnings("unused")
    public List<FeedInterestAmount> getInterestAmount() {
        return interestAmount;
    }

    public void setInterestAmount(List<FeedInterestAmount> interestAmount) {
        this.interestAmount = interestAmount;
    }

    @SuppressWarnings("unused")
    public List<FeedMtM> getMtM() {
        return MtM;
    }

    public void setMtM(List<FeedMtM> MtM) {
        this.MtM = MtM;
    }

    @SuppressWarnings("unused")
    public List getOrganisation() {
        return organisation;
    }

    public void setOrganisation(List organisation) {
        this.organisation = organisation;
    }

    @SuppressWarnings("unused")
    public List<FeedHolidayCentre> getHoliday() {
        return holiday;
    }

    public void setHoliday(List<FeedHolidayCentre> holiday) {
        this.holiday = holiday;
    }

    @SuppressWarnings("unused")
    public List<FeedStaticData> getRefservicedata() {
        return refservicedata;
    }

    public void setRefservicedata(List<FeedStaticData> refservicedata) {
        this.refservicedata = refservicedata;
    }

    @SuppressWarnings("unused")
    public List<FeedWeekend> getWeekend() {
        return weekend;
    }

    public void setWeekend(List<FeedWeekend> weekend) {
        this.weekend = weekend;
    }

    @SuppressWarnings("unused")
    public List<FeedEligibilityRulesTemplate> getEligibilityTemplate() {
        return eligibilityTemplate;
    }

    public void setEligibilityTemplate(List<FeedEligibilityRulesTemplate> eligibilityTemplate) {
        for(FeedEligibilityRulesTemplate feedEligibilityRulesTemplate : eligibilityTemplate){
            if(feedEligibilityRulesTemplate.getId() != null)
                feedEligibilityRulesTemplate.setId(null);
        }
        this.eligibilityTemplate = eligibilityTemplate;
    }

    @SuppressWarnings("unused")
    public List getTrade() {
        return trade;
    }

    public void setTrade(List trade) {
        this.trade = trade;
    }

    @SuppressWarnings("unused")
    public List<FeedRepoSettlement> getRepoSettlement() {
        return repoSettlement;
    }

    public void setRepoSettlement(List<FeedRepoSettlement> repoSettlement) {
        this.repoSettlement = repoSettlement;
    }

    @SuppressWarnings("unused")
    public List<FeedCounterpartyAmount> getCounterpartyAmount() {
        return counterpartyAmount;
    }

    public void setCounterpartyAmount(List<FeedCounterpartyAmount> counterpartyAmount) {
        this.counterpartyAmount = counterpartyAmount;
    }
}
