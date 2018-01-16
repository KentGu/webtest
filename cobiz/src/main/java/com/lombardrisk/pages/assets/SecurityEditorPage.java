package com.lombardrisk.pages.assets;

import com.lombardrisk.pojo.*;
import com.lombardrisk.util.Biz;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public final class SecurityEditorPage extends PageBase {

    public SecurityEditorPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    public void cancelInstrument() throws Exception {
        element("ss.instr.cancel").clickByJavaScript();
    }

    public void saveInstrument() throws Exception{
    	element("ss.instr.save").clickByJavaScript();
    }

    /**
     * click approve button - Securities Editor page
     *
     * @author kent gu
     */
    public void approveInstrunment() throws Exception {
        element("ss.instr.approve").click();
    }

    /**
     * input instrument data on security editor page
     *
     * @param instr
     */
    public void inputInstrument(Instrument instr) throws Exception {
        if (instr.getAssetType() != null)
            element("ss.instr.asset.type").selectByVisibleText(instr.getAssetType().getRealValue());

        if (instr.getInstrumentId() != null && !instr.getInstrumentId().isEmpty()) {
            for (int i = 0; i < instr.getInstrumentId().size(); i++) {
                if (i > 0)
                    element("ss.instr.id.add").click();
                if (instr.getInstrumentId().get(i).getType() != null)
                    element("ss.instr.id.type", String.valueOf(i)).selectByVisibleText(instr.getInstrumentId().get(i).getType().value());
                if (instr.getInstrumentId().get(i).getValue() != null)
                    element("ss.instr.id.value", String.valueOf(i)).input(instr.getInstrumentId().get(i).getValue().getRealValue());
                if (instr.getInstrumentId().get(i).isPrimary() != null)
                    element("ss.instr.id.primary", String.valueOf(i + 1)).check(instr.getInstrumentId().get(i).isPrimary());
            }
        }

        if (instr.getAssetClassification() != null) {
            for (AssetClassification assetClassification : instr.getAssetClassification()) {
                if (assetClassification.getAssetClassificationName() != null) {
                    if (assetClassification.getAssetcategory() != null) {
                        element("ss.instr.category", assetClassification.getAssetClassificationName().getRealValue()).input(assetClassification.getAssetcategory().getRealValue());
                    }
                }
            }

        }

        if (instr.getMarket() != null)
            element("ss.instr.market").input(instr.getMarket().getRealValue());
        if (instr.getDescription() != null)
            element("ss.instr.desc").input(instr.getDescription().getRealValue());
        if (instr.getIssuer() != null) {
            if (instr.getIssuer().getType() != null)
                element("ss.instr.issuer.type").selectByVisibleText(instr.getIssuer().getType().value());
            if (instr.getIssuer().getFilter() != null) {
//                element("ss.instr.issuer.filter").setAttribute("value", instr.getIssuer().getFilter().getRealValue());
                element("ss.instr.issuer.filter").input(instr.getIssuer().getFilter().getRealValue());
            }
            if (instr.getIssuer().getLinkText() != null) {
                element("ss.instr.issuer.linktext", instr.getIssuer().getLinkText().getRealValue(), instr.getIssuer().getLinkText().getRealValue()).click();
            }
        }
        if (instr.getMaturityDate() != null) {
            element("ss.instr.maturity.date").input(instr.getMaturityDate().getRealValue()).fireEvent(
                    Biz.FIRE_EVENT_ONCHANGE);
            waitThat().jQuery().toBeInactive();
            waitThat().document().toBeReady();
        }
        if (instr.getCurrencyOfDenomination() != null)
            element("ss.instr.ccy").selectByVisibleText(instr.getCurrencyOfDenomination().getRealValue());
        if (instr.getCountryOfIssue() != null)
            element("ss.instr.countryofissue").selectByVisibleText(instr.getCountryOfIssue().getRealValue());
        if (instr.getCouponRate() != null) {
            element("ss.instr.coupon.rate").input(instr.getCouponRate().getRealValue()).fireEvent(
                    Biz.FIRE_EVENT_ONCHANGE);
            waitThat().jQuery().toBeInactive();
            waitThat().document().toBeReady();
        }
        if (instr.getCouponFrequency() != null)
            element("ss.instr.coupon.frequency").selectByVisibleText(instr.getCouponFrequency().value());
        if (instr.getAccrualBasis() != null) {
            element("ss.instr.accrual.basis").selectByVisibleText(instr.getAccrualBasis()).fireEvent(
                    Biz.FIRE_EVENT_ONCHANGE);
            waitThat().jQuery().toBeInactive();
            waitThat().document().toBeReady();
        }
        if (instr.getFactor() != null) {
            element("ss.instr.factor").input(instr.getFactor().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
            waitThat().jQuery().toBeInactive();
            waitThat().document().toBeReady();
        }
        if (instr.getPriceFactor() != null) {
//            if (element("ss.instr.pricefactor").isDisplayed())
            element("ss.instr.pricefactor").input(instr.getPriceFactor().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
//            else if (element("ss.instr.pricefactor.equity").isDisplayed())
//                element("ss.instr.pricefactor.equity").input(instr.getPriceFactor().getRealValue());
            waitThat().jQuery().toBeInactive();
            waitThat().document().toBeReady();
        }

        if (instr.getIssueRating() != null && instr.getIssueRating().size() > 0) {
            for (OrganisationRating rate : instr.getIssueRating()) {
                element("ss.instr.issue.rating", rate.getAgency().getRealValue()).selectByVisibleText(
                        rate.getRating().getRealValue());
            }
        }
        if (instr.isManuallyOverrideNextCouponDate() != null)
            element("ss.instr.override.next.date").check(true);
        if (instr.getNextCouponDate() != null) {
            element("ss.instr.next.coupon.date").input(instr.getNextCouponDate().getRealValue()).fireEvent(
                    Biz.FIRE_EVENT_ONCHANGE);
            waitThat().jQuery().toBeInactive();
            waitThat().document().toBeReady();
        }
        if (instr.isManuallyOverrideLastCouponDate() != null)
            element("ss.instr.override.last.date").check(true);
        if (instr.getLastCouponDate() != null) {
            element("ss.instr.last.coupon.date").input(instr.getLastCouponDate().getRealValue()).fireEvent(
                    Biz.FIRE_EVENT_ONCHANGE);
            waitThat().jQuery().toBeInactive();
            waitThat().document().toBeReady();
        }
        if (instr.getRecordDate() != null) {
            element("ss.instr.record.date").input(instr.getRecordDate().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
            waitThat().jQuery().toBeInactive();
            waitThat().document().toBeReady();
        }
        if (instr.getIssueDate() != null)
            element("ss.instr.issue.date").input(instr.getIssueDate().getRealValue());
        if(instr.isLock() !=null)
            element("ss.instr.lock").check(true);
        if (instr.getCouponAccrual() != null) {
            element("ss.instr.coupon.accrual").input(instr.getCouponAccrual().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
            waitThat().jQuery().toBeInactive();
            waitThat().document().toBeReady();
        }
        if (instr.getAdtv() != null)
            element("ss.instr.adtv").input(instr.getAdtv().getRealValue());
        if (instr.getFinra4210MinHaircut() != null)
            element("ss.instr.finra4210MinHaircut").input(instr.getFinra4210MinHaircut().getRealValue());
        if (instr.getProbabilityofDefault() != null)
            element("ss.instr.probability.of.default").input(instr.getProbabilityofDefault().getRealValue());
        if (instr.getAdditionalInfo1() != null)
            element("ss.instr.additionalinfo1").input(instr.getAdditionalInfo1().getRealValue());
        if (instr.getAdditionalInfo2() != null)
            element("ss.instr.additionalinfo2").input(instr.getAdditionalInfo2().getRealValue());
        if (instr.getAdditionalInfo3() != null)
            element("ss.instr.additionalinfo3").input(instr.getAdditionalInfo3().getRealValue());
        if (instr.getAdditionalInfo4() != null)
            element("ss.instr.additionalinfo4").input(instr.getAdditionalInfo4().getRealValue());
        if (instr.getMinimumTradableAmount() != null)
            element("ss.instr.mininum.tradeable.amount").input(instr.getMinimumTradableAmount().getRealValue());
        if (instr.getMultipleTradableAmount() != null)
            element("ss.instr.multiple.tradeable.amount").input(instr.getMultipleTradableAmount().getRealValue());
        if (instr.getNotation() != null)
            element("ss.instr.notation").input(instr.getNotation().getRealValue());
        if (instr.getTradedInterest() != null)
            element("ss.instr.trade.interest").input(instr.getTradedInterest().getRealValue());
        if (instr.getOutstandingIssuance() != null)
            element("ss.instr.outstanding.issuance").input(instr.getOutstandingIssuance().getRealValue());
        if (instr.getAdditionalField() != null && instr.getAdditionalField().size() > 0) {
            element("ss.instr.showudf").click();
            for (Field field : instr.getAdditionalField()) {
                if (field.getFieldName() != null && field.getValue() != null)
                    element("ss.instr.udf.edit", field.getFieldName().getRealValue(), field.getFieldName().getRealValue()).input(field.getValue().getRealValue());
            }
        }

        if (instr.getPriceSource() != null && !instr.getPriceSource().isEmpty()) {
            for (int i = 0; i < instr.getPriceSource().size(); i++) {
                String ps_index = element("ss.instr.ps", instr.getPriceSource().get(i).getPriceSourceName().getRealValue()).getAttribute("name");
                ps_index = ps_index.split("\\.")[0];
                // show price source
                element("ss.instr.ps.show", instr.getPriceSource().get(i).getPriceSourceName().getRealValue()).clickSmartly();
                if (instr.getPriceSource().get(i).getDirtyPrice() != null) {
                    element("ss.instr.ps.dirty.price", ps_index).input(
                            instr.getPriceSource().get(i).getDirtyPrice().getRealValue()).fireEvent(
                            Biz.FIRE_EVENT_ONCHANGE);
                    waitThat().jQuery().toBeInactive();
                    waitThat().document().toBeReady();
                }
                if (instr.getPriceSource().get(i).getLastPriceChange() != null)
                    element("ss.instr.ps.valuation.date", ps_index).input(instr.getPriceSource().get(i).getLastPriceChange().getRealValue());
                if (instr.getPriceSource().get(i).getCleanPrice() != null) {
                    element("ss.instr.ps.clean.price", ps_index).input(
                            instr.getPriceSource().get(i).getCleanPrice().getRealValue()).fireEvent(
                            Biz.FIRE_EVENT_ONCHANGE);
                    waitThat().jQuery().toBeInactive();
                    waitThat().document().toBeReady();
                }
                if (instr.getPriceSource().get(i).getPriceDate() != null) {
                    element("ss.instr.ps.price.date", ps_index).input(
                            instr.getPriceSource().get(i).getPriceDate().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
                    waitThat().jQuery().toBeInactive();
                    waitThat().document().toBeReady();
                }
                if (instr.getPriceSource().get(i).getPriceLevel() != null)
                    element("ss.instr.ps.price.level", ps_index).selectByVisibleText(instr.getPriceSource().get(i).getPriceLevel().value());
            }
        }
        waitThat().jQuery().toBeInactive();
        waitThat().document().toBeReady();
    }

    /**
     * assert Instrument value on Security Editor page
     *
     * @param instr
     */
    public void assertInstrument(Instrument instr) throws Exception {
        DecimalFormat format = new DecimalFormat();
        Date serverDate = getServerTime();

        if (instr.getAssetClass() != null) {
            validateThat("ss.instr.assertion.asset.class").innerText().isEqualToIgnoringWhitespace(instr.getAssetClass().getRealValue());
        }
        if (instr.getAssetType() != null) {
            validateThat("ss.instr.assertion.asset.type").selectedText().isEqualToIgnoringWhitespace(instr.getAssetType().getRealValue());
        }
        if (instr.getAssetClassification() != null && !instr.getAssetClassification().isEmpty()) {
            for(AssetClassification assetClassification : instr.getAssetClassification()) {
                if (assetClassification.getAssetClassificationName() != null && assetClassification.getAssetcategory() != null)
                    validateThat("ss.instr.asset.category", assetClassification.getAssetClassificationName().getRealValue()).attributeValueOf("value").isEqualToIgnoringWhitespace(assetClassification.getAssetcategory().getRealValue());
            }
        }
        if (instr.getInstrumentId() != null && !instr.getInstrumentId().isEmpty()){
            for (InstrumentId instrumentId : instr.getInstrumentId()){
                if (instrumentId.getType() != null && instrumentId.getValue() != null) {
                    validateThat("ss.instr.assertion.instrument.row", instrumentId.getType().value(), instrumentId.getValue().getRealValue()).displayed().isTrue();
                }else{
                    if(instrumentId.getType() != null){
                        validateThat("ss.instr.assertion.instrument.id.type", instrumentId.getType().value()).displayed().isTrue();
                    }else if(instrumentId.getValue() != null){
                        validateThat("ss.instr.assertion.instrument.id.value", instrumentId.getValue().getRealValue()).displayed().isTrue();
                    }
                }
            }
        }
        if (instr.getMarket() != null){
            validateThat("ss.instr.assertion.market").attributeValueOf("value").isEqualToIgnoringWhitespace(instr.getMarket().getRealValue());
        }
        if (instr.getDescription() != null){
            validateThat("ss.instr.assertion.description").attributeValueOf("value").isEqualToIgnoringWhitespace(instr.getDescription().getRealValue());
        }
        if (instr.getIssuer() != null){
            if (instr.getIssuer().getType() != null){
                validateThat("ss.instr.assertion.issuer.type").selectedText().isEqualToIgnoringWhitespace(instr.getIssuer().getType().value());
            }
            if (instr.getIssuer().getLinkText() != null){
                validateThat("ss.instr.assertion.issuer.linktext").attributeValueOf("value").isEqualToIgnoringWhitespace(instr.getIssuer().getLinkText().getRealValue());
            }
        }
        if (instr.getMaturityDate() != null){
            String date = instr.getMaturityDate().getRealValue();
            if (date.matches("^[Tt]([+|-]\\d+)?$")){
                date = Biz.convertDate(serverDate, date, "M/d/yyyy");
            }
            validateThat("ss.instr.assertion.maturity.date").attributeValueOf("value").isEqualToIgnoringWhitespace(date);
        }
        if (instr.getCountryOfIssuer() != null){
            validateThat("ss.instr.assertion.country.of.issuer").selectedText().isEqualToIgnoringWhitespace(instr.getCountryOfIssuer().getRealValue());
        }
        if (instr.getCurrencyOfDenomination() != null){
            validateThat("ss.instr.assertion.currency.of.denomination").selectedText().isEqualToIgnoringWhitespace(instr.getCurrencyOfDenomination().getRealValue());
        }
        if (instr.getIssuerClassification() != null){
            validateThat("ss.instr.assertion.issuer.classification").innerText().isEqualToIgnoringWhitespace(instr.getIssuerClassification().getRealValue());
        }
        if (instr.getCountryOfIssue() != null){
            validateThat("ss.instr.assertion.country.of.issue").selectedText().isEqualToIgnoringWhitespace(instr.getCountryOfIssue().getRealValue());
        }

        if (instr.getPriceSource() != null && instr.getPriceSource().size() > 0) {
            for (int i = 0; i < instr.getPriceSource().size(); i++) {
                String ps_index = element("ss.instr.ps", instr.getPriceSource().get(i).getPriceSourceName().getRealValue()).getAttribute("name");
                ps_index = ps_index.split("\\.")[0];
                // show price source
                element("ss.instr.ps.show", instr.getPriceSource().get(i).getPriceSourceName().getRealValue()).clickSmartly();
                if (instr.getPriceSource().get(i).getPriceAge() != null)
                    validateThat("ss.instr.ps.price.age", ps_index).innerText().isEqualTo(instr.getPriceSource().get(i).getPriceAge().getRealValue());
                if (instr.getPriceSource().get(i).getDirtyPrice() != null) {
                    format.applyPattern("###,###,###,##0.0000000");
                    try {
                        validateThat("ss.instr.ps.dirty.price", ps_index).attributeValueOf("value").isEqualTo(
                                format.format(Double.parseDouble(instr.getPriceSource().get(i).getDirtyPrice().getRealValue()))
                        );
                    }catch(NumberFormatException nfe){
                        validateThat("ss.instr.ps.dirty.price", ps_index).attributeValueOf("value").isEqualToIgnoringWhitespace(instr.getPriceSource().get(i).getDirtyPrice().getRealValue());
                    }
                }
                if (instr.getPriceSource().get(i).getCleanPrice() != null) {
                    format.applyPattern("###,###,###,##0.0000000");
                    validateThat("ss.instr.ps.clean.price", ps_index).attributeValueOf("value").isEqualTo(
                            format.format(Double.parseDouble(instr.getPriceSource().get(i).getCleanPrice().getRealValue())));
                }
                if (instr.getPriceSource().get(i).getLastPriceChange() != null){
                    String date = instr.getPriceSource().get(i).getLastPriceChange().getRealValue();
                    if (date.matches("^[Tt]([+|-]\\d+)?$")){
                        date = Biz.convertDate(serverDate, date, "M/d/yyyy");
                    }
                    validateThat("ss.instr.ps.valuation.date",ps_index).attributeValueOf("value").isEqualToIgnoringWhitespace(date);
                }
                if (instr.getPriceSource().get(i).getPriceDate() != null){
                    String date = instr.getPriceSource().get(i).getPriceDate().getRealValue();
                    if (date.matches("^[Tt]([+|-]\\d+)?$")){
                        date = Biz.convertDate(serverDate, date, "M/d/yyyy");
                    }
                    validateThat("ss.instr.ps.price.date",ps_index).attributeValueOf("value").isEqualToIgnoringWhitespace(date);
                }
                if (instr.getPriceSource().get(i).getPriceLevel() != null){
                    validateThat("ss.instr.ps.price.level",ps_index).selectedText().isEqualToIgnoringWhitespace(instr.getPriceSource().get(i).getPriceLevel().value());
                }

                if (instr.getCouponDayCount() != null)
                    validateThat("ss.instr.coupon.day.count").innerText().isEqualTo(instr.getCouponDayCount().getRealValue());
            }
        }
        if (instr.getCouponRate() != null){
            format.applyPattern("0.000");
            validateThat("ss.instr.assertion.coupon.rate").attributeValueOf("value").isEqualToIgnoringWhitespace(
                    format.format(Float.parseFloat(instr.getCouponRate().getRealValue())));
        }
        if (instr.getCouponFrequency() != null){
            validateThat("ss.instr.assertion.coupon.frequency").selectedText().isEqualToIgnoringWhitespace(instr.getCouponFrequency().value());
        }
        if (instr.getAccrualBasis() != null){
            validateThat("ss.instr.assertion.accrual.basis").selectedText().isEqualToIgnoringWhitespace(instr.getAccrualBasis());
        }
        if (instr.getNextCouponDate() != null){
            String date = instr.getNextCouponDate().getRealValue();
            if (date.matches("^[Tt]([+|-]\\d+)?$")){
                date = Biz.convertDate(serverDate, date, "M/d/yyyy");
            }
            validateThat("ss.instr.assertion.next.coupon.date").attributeValueOf("value").isEqualToIgnoringWhitespace(date);
        }
        if (instr.isManuallyOverrideNextCouponDate() != null && instr.isManuallyOverrideNextCouponDate()){
            validateThat("ss.instr.assertion.override.next.date").selected();
        }
        if (instr.getCouponAccrual() != null){
            format.applyPattern("###,###,###,##0.0000000");
            validateThat("ss.instr.assertion.coupon.accrual").attributeValueOf("value").isEqualToIgnoringWhitespace(
                    format.format(Double.parseDouble(instr.getCouponAccrual().getRealValue())));
        }
        if (instr.getLastCouponDate() != null){
            String date = instr.getLastCouponDate().getRealValue();
            if (date.matches("^[Tt]([+|-]\\d+)?$")){
                date = Biz.convertDate(serverDate, date, "M/d/yyyy");
            }
            validateThat("ss.instr.assertion.last.coupon.date").attributeValueOf("value").isEqualToIgnoringWhitespace(date);
        }
        if (instr.isManuallyOverrideLastCouponDate() != null && instr.isManuallyOverrideLastCouponDate()){
            validateThat("ss.instr.assertion.override.last.date").selected();
        }
        if (instr.getFactor() != null){
            format.applyPattern("0.0000000");
            validateThat("ss.instr.assertion.factor").attributeValueOf("value").isEqualToIgnoringWhitespace(
                    format.format(Float.parseFloat(instr.getFactor().getRealValue())));
        }
        if (instr.getCouponDayCount() != null){
            validateThat("ss.instr.assertion.coupon.day.count").innerText().isEqualToIgnoringWhitespace(instr.getCouponDayCount().getRealValue());
        }
        if (instr.getPriceFactor() != null){
            format.applyPattern("0.0000000");
            validateThat("ss.instr.assertion.price.factor").attributeValueOf("value").isEqualToIgnoringWhitespace(
                    format.format(Float.parseFloat(instr.getPriceFactor().getRealValue())));
        }
        if (instr.getRecordDate() != null){
            String date = instr.getRecordDate().getRealValue();
            if (date.matches("^[Tt]([+|-]\\d+)?$")){
                date = Biz.convertDate(serverDate, date, "M/d/yyyy");
            }
            validateThat("ss.instr.assertion.record.date").attributeValueOf("value").isEqualToIgnoringWhitespace(date);
        }
        if (instr.getAdtv() != null){
            format.applyPattern("#,###,##0.00");
            validateThat("ss.instr.adtv").attributeValueOf("value").isEqualToIgnoringWhitespace(
                    format.format(Float.parseFloat(instr.getAdtv().getRealValue())));
        }
        if (instr.getIssueDate() != null){
            String date = instr.getIssueDate().getRealValue();
            if (date.matches("^[Tt]([+|-]\\d+)?$")){
                date = Biz.convertDate(serverDate, date, "M/d/yyyy");
            }
            validateThat("ss.instr.assertion.issue.date").attributeValueOf("value").isEqualToIgnoringWhitespace(date);
        }
        if (instr.getExDivDate() != null){
            String date = instr.getExDivDate().getRealValue();
            if (date.matches("^[Tt]([+|-]\\d+)?$")){
                date = Biz.convertDate(serverDate, date, "M/d/yyyy");
            }
            validateThat("ss.instr.assertion.ex.div.date").attributeValueOf("value").isEqualToIgnoringWhitespace(date);
        }
        if (instr.getFinra4210MinHaircut() != null){
            format.applyPattern("0.00");
            validateThat("ss.instr.finra4210MinHaircut").attributeValueOf("value").isEqualToIgnoringWhitespace(
                    format.format(Float.parseFloat(instr.getFinra4210MinHaircut().getRealValue())));
        }
        if (instr.getIssuerRating() != null && instr.getIssuerRating().size() > 0){
            for (int i=0;i<instr.getIssuerRating().size();i++){
                if (instr.getIssuerRating().get(i).getAgency() != null){
                    validateThat("ss.instr.assertion.issuer.ratings").innerText().containsIgnoringCase(
                            instr.getIssuerRating().get(i).getAgency().getRealValue());
                }
                if (instr.getIssuerRating().get(i).getRating() != null){
                    validateThat("ss.instr.assertion.issuer.ratings").innerText().containsIgnoringCase(
                            instr.getIssuerRating().get(i).getRating().getRealValue());
                }
            }
        }
        if (instr.getDebtClassification() != null){
            validateThat("ss.instr.assertion.debt.classification").innerText().isEqualToIgnoringWhitespace(instr.getDebtClassification().getRealValue());
        }
        if (instr.getIssueRating() != null && instr.getIssueRating().size() > 0){
            for (int i=0;i<instr.getIssueRating().size();i++){
                if (instr.getIssueRating().get(i).getAgency() != null && instr.getIssueRating().get(i).getAgency().getRealValue().equals("SandP")){
                    if (instr.getIssueRating().get(i).getRating() != null){
                        validateThat("ss.instr.assertion.issue.ratings.sandp").selectedText().isEqualToIgnoringWhitespace(
                                instr.getIssueRating().get(i).getRating().getRealValue());
                    }
                }else if (instr.getIssueRating().get(i).getAgency() != null && instr.getIssueRating().get(i).getAgency().getRealValue().equals("MOODYS")){
                    if (instr.getIssueRating().get(i).getRating() != null){
                        validateThat("ss.instr.assertion.issue.ratings.moodys").selectedText().isEqualToIgnoringWhitespace(
                                instr.getIssueRating().get(i).getRating().getRealValue());
                    }
                }else if (instr.getIssueRating().get(i).getAgency() != null && instr.getIssueRating().get(i).getAgency().getRealValue().equals("Fitch")){
                    if (instr.getIssueRating().get(i).getRating() != null){
                        validateThat("ss.instr.assertion.issue.ratings.fitch").selectedText().isEqualToIgnoringWhitespace(
                                instr.getIssueRating().get(i).getRating().getRealValue());
                    }
                }
            }
        }
        if (instr.getProbabilityofDefault() != null){
            format.applyPattern("0.0");
            validateThat("ss.instr.assertion.probability.of.default").attributeValueOf("value").isEqualToIgnoringWhitespace(
                    format.format(Float.parseFloat(instr.getProbabilityofDefault().getRealValue())));
        }
        if (instr.getCqs() != null && instr.getCqs().size() > 0){
            for (int i=0;i<instr.getCqs().size();i++){
                if (instr.getCqs().get(i).getAgency() != null){
                    validateThat("ss.instr.assertion.cqs").innerText().containsIgnoringCase(
                            instr.getCqs().get(i).getAgency().getRealValue());
                }
                if (instr.getCqs().get(i).getRating() != null){
                    validateThat("ss.instr.assertion.cqs").innerText().containsIgnoringCase(
                            instr.getCqs().get(i).getRating().getRealValue());
                }
            }
        }
        if (instr.getAdditionalInfo1() != null){
            validateThat("ss.instr.assertion.additional.info1").attributeValueOf("value").isEqualToIgnoringWhitespace(instr.getAdditionalInfo1().getRealValue());
        }
        if (instr.getAdditionalInfo2() != null){
            validateThat("ss.instr.assertion.additional.info2").attributeValueOf("value").isEqualToIgnoringWhitespace(instr.getAdditionalInfo2().getRealValue());
        }
        if (instr.getAdditionalInfo3() != null){
            validateThat("ss.instr.assertion.additional.info3").attributeValueOf("value").isEqualToIgnoringWhitespace(instr.getAdditionalInfo3().getRealValue());
        }
        if (instr.getAdditionalInfo4() != null){
            validateThat("ss.instr.assertion.additional.info4").attributeValueOf("value").isEqualToIgnoringWhitespace(instr.getAdditionalInfo4().getRealValue());
        }
        if (instr.getMinimumTradableAmount() != null){
            format.applyPattern("###,###,##0.00");
            validateThat("ss.instr.assertion.minimum.trade.amount").attributeValueOf("value").isEqualToIgnoringWhitespace(
                    format.format(Float.parseFloat(instr.getMinimumTradableAmount().getRealValue())));
        }
        if (instr.getMultipleTradableAmount() != null){
            format.applyPattern("###,###,##0.00");
            validateThat("ss.instr.assertion.multiple.tradable.amount").attributeValueOf("value").isEqualToIgnoringWhitespace(
                    format.format(Float.parseFloat(instr.getMultipleTradableAmount().getRealValue())));
        }
        if (instr.getNotation() != null){
            validateThat("ss.instr.assertion.notation").attributeValueOf("value").isEqualToIgnoringWhitespace(instr.getNotation().getRealValue());
        }
        if (instr.getTradedInterest() != null){
            format.applyPattern("0.00");
            validateThat("ss.instr.assertion.trade.interest").attributeValueOf("value").isEqualToIgnoringWhitespace(
                    format.format(Float.parseFloat(instr.getTradedInterest().getRealValue())));
        }
        if (instr.getOutstandingIssuance() != null){
            format.applyPattern("0.00");
            validateThat("ss.instr.assertion.outstanding.issuance").attributeValueOf("value").isEqualToIgnoringWhitespace(
                    format.format(Float.parseFloat(instr.getOutstandingIssuance().getRealValue())));
        }
        if (instr.isLock() != null && instr.isLock()){
            validateThat("ss.instr.assertion.lock").selected();
        }
        if (instr.getSecurityStatus() != null){
            validateThat("ss.instr.assertion.security.status").innerText().isEqualToIgnoringWhitespace(instr.getSecurityStatus().getRealValue());
        }
        if (instr.getValidationError() != null && instr.getValidationError().size()>0) {
            List<ValidationErrorType> validationErrors = instr.getValidationError();
            for (ValidationErrorType validationError : validationErrors) {
                if (validationError.getContext() != null) {
                    assertThat("ss.instr.assertion.validationError").innerText().contains(validationError.getContext().getRealValue());
                }
            }
        }

    }

    private Date getServerTime() throws Exception {
        Date date;
        String time;
        if (element("hm.server.time", "scrollingMessage").isDisplayed()) {
            time = element("hm.server.time", "scrollingMessage").getInnerText();
        } else if (element("hm.server.time", "noNewScrollingMessage").isDisplayed()) {
            time = element("hm.server.time", "noNewScrollingMessage").getInnerText();
        } else {
            time = element("hm.server.time", "nonScrollingMessage").getInnerText();
        }
        if (time == null || time.equals("")) {
            date = new Date();
        } else {
            try {
                date = new Date(time);
            } catch (IllegalArgumentException iae) {
                SimpleDateFormat s = new SimpleDateFormat("EEEE, d MMMM yyyy");
                date = s.parse(time);
            }
        }
        return date;
    }
}
