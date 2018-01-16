package com.lombardrisk.pages.collateral;

import com.lombardrisk.pojo.*;
import com.lombardrisk.util.Biz;
import com.lombardrisk.util.PageHelper;
import org.yiwan.webcore.util.PropHelper;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kenny Wang on 2/3/2016.
 */
public class TradeEditorPage extends PageBase {
    public TradeEditorPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    /**
     * add trade from agreement exposure summary page, using
     * editSummaryExposureInfo first for adding trade from statement page
     *
     * @param trade
     * @author Yan Lu
     */
    public void addTrade(TradeDetail trade) throws Exception {
        setupTrade(trade);
        save();
        PageHelper.handleAlters(trade.getAlertHandling());
    }

    /**
     * add trade
     *
     * @param tradeDetail
     * @throws Exception
     */
    @SuppressWarnings("DanglingJavadoc")
    public void setupTrade(TradeDetail tradeDetail) throws Exception {
        setTradeInformation(tradeDetail);
        setValuationInformation(tradeDetail);

        if (tradeDetail.getCcpTradeInformation() != null) {
            inputCcpTradeInformation(tradeDetail.getCcpTradeInformation());
        }

        setCollateralInformation(tradeDetail);
        setSecurityInformation(tradeDetail);
        setCashInformation(tradeDetail);
        setAdjustmentInformation(tradeDetail);
        setExposureInformation(tradeDetail);
        setTradeMiscellaneousFields(tradeDetail);


//        TradeInformation tradeInformation = tradeDetail.getTradeInformation();
//        if (tradeInformation != null) {
//            inputTradeInformation(tradeInformation);
//        }

//        TradeValuationInformation tradeValuationInformation = tradeDetail.getValuationInformation();
//        if (tradeValuationInformation != null) {
//            inputTradeValuationInformation(tradeValuationInformation);
//        }

//        TradeCollateralInformation tradeCollateralInformation = tradeDetail.getCollateralInformation();
//        if (tradeCollateralInformation != null) {
//            inputTradeCollateralInformation(tradeCollateralInformation);
//        }

//        TradeSecurityInformation tradeSecurityInformation = tradeDetail.getSecurityInformation();
//        if (tradeSecurityInformation != null) {
//            inputTradeSecurityInformation(tradeSecurityInformation);
//        }
//
//        TradeCashInformation tradeCashInformation = tradeDetail.getCashInformation();
//        if (tradeCashInformation != null) {
//            inputTradeCashInformation(tradeCashInformation);
//        }
//
//        TradeAdjustmentInformation tradeAdjustmentInformation = tradeDetail.getAdjustmentInformation();
//        if (tradeAdjustmentInformation != null) {
//            inputTradeAdjustmentInformation(tradeAdjustmentInformation);
//        }
//
//        TradeValuationInformation tradeExposureInformation = tradeDetail.getExposureInformation();
//        if (tradeExposureInformation != null) {
//            inputTradeValuationInformation(tradeExposureInformation);
//        }
//
//        TradeMiscellaneousFields tradeMiscellaneousFields = tradeDetail.getTradeMiscellaneousFields();
//        if (tradeMiscellaneousFields != null) {
//            inputTradeMiscellaneousFields(tradeMiscellaneousFields);
//        }
    }

    public Date getServerTime() throws Exception {
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

    /**
     * setup trade information block values including ETF trade and repo trade
     *
     * @param tradeDetail
     * @throws Exception
     * @author yan lu
     */
    public void setTradeInformation(TradeDetail tradeDetail) throws Exception {

        if (tradeDetail.getTradeInformation() != null) {
            if (tradeDetail.getTradeInformation().getUnderlying() != null) {
                element("td.tradeInfo.underlying").input(tradeDetail.getTradeInformation().getUnderlying().getRealValue());
            }
            if (tradeDetail.getTradeInformation().getStrategy() != null) {
                element("td.tradeInfo.strategy").input(tradeDetail.getTradeInformation().getStrategy().getRealValue());
            }
            if (tradeDetail.getTradeInformation().getTradeIdentifierPartyA() != null) {
                element("td.tradeInfo.id.party.a").input(tradeDetail.getTradeInformation().getTradeIdentifierPartyA().getRealValue());
            }
            if (tradeDetail.getTradeInformation().getTradeDate() != null) {
                element("td.tradeInfo.tradeDate").input(tradeDetail.getTradeInformation().getTradeDate().getRealValue());
//				if (tradeDetail.getTradeInformation().getTradeDate().getRealValue().toLowerCase().contains("t")) {
//					tradeDetail.getTradeInformation().setTradeDate(new StringType(Biz.convertDate(getServerTime(), tradeDetail.getTradeInformation().getTradeDate().getRealValue())));
//				}
            }
            if (tradeDetail.getTradeInformation().getTradeIdentifier2PartyA() != null) {
                element("td.tradeInfo.id2.party.a").input(tradeDetail.getTradeInformation().getTradeIdentifier2PartyA().getRealValue());
            }
            if (tradeDetail.getTradeInformation().getMaturityDate() != null) {
                element("td.tradeInfo.maturityDate").input(tradeDetail.getTradeInformation().getMaturityDate().getRealValue());
//				if (tradeDetail.getTradeInformation().getMaturityDate().getRealValue().toLowerCase().contains("t"))
//					tradeDetail.getTradeInformation().setMaturityDate(new StringType(Biz.convertDate(getServerTime(), tradeDetail.getTradeInformation().getMaturityDate().getRealValue())));
            }

            //ETF Trade Or Repo Trade information
            if (tradeDetail.getTradeInformation().getStartDate() != null) {
                element("td.tradeInfo.startDate").input(tradeDetail.getTradeInformation().getStartDate().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
                waitThat().jQuery().toBeInactive();
                waitThat().document().toBeReady();
//				if (tradeDetail.getTradeInformation().getStartDate().getRealValue().toLowerCase().contains("t"))
//					tradeDetail.getTradeInformation().setStartDate(new StringType(Biz.convertDate(getServerTime(), tradeDetail.getTradeInformation().getStartDate().getRealValue())));

            }//

            if (tradeDetail.getTradeInformation().getPartyABranchName() != null) {
                element("td.tradeInfo.party.a.branch.name").input(tradeDetail.getTradeInformation().getPartyABranchName().getRealValue());
            }
            if (tradeDetail.getTradeInformation().getEffectiveDate() != null) {
                element("td.tradeInfo.effectiveDate").input(tradeDetail.getTradeInformation().getEffectiveDate().getRealValue());
//				if (tradeDetail.getTradeInformation().getEffectiveDate().getRealValue().toLowerCase().contains("t"))
//					tradeDetail.getTradeInformation().setEffectiveDate(new StringType(Biz.convertDate(getServerTime(), tradeDetail.getTradeInformation().getEffectiveDate().getRealValue())));
            }
            //ETF Trade information Or Repo Trade information
            if (tradeDetail.getTradeInformation().getEndDate() != null) {
                element("td.tradeInfo.endDate").input(tradeDetail.getTradeInformation().getEndDate().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
                waitThat().jQuery().toBeInactive();
                waitThat().document().toBeReady();
//				if (tradeDetail.getTradeInformation().getEndDate().getRealValue().toLowerCase().contains("t"))
//					tradeDetail.getTradeInformation().setEndDate(new StringType(Biz.convertDate(getServerTime(), tradeDetail.getTradeInformation().getEndDate().getRealValue())));

            }//
            if (tradeDetail.getTradeInformation().getTradeIdentifierPartyB() != null) {
                element("td.tradeInfo.tradeIdPartyB").input(tradeDetail.getTradeInformation().getTradeIdentifierPartyB().getRealValue());
            }
            if (tradeDetail.getTradeInformation().getStrikePrice() != null) {
                element("td.tradeInfo.strikePrice").input(tradeDetail.getTradeInformation().getStrikePrice().getRealValue());
            }
            if (tradeDetail.getTradeInformation().getPartyBBranchName() != null) {
                element("td.tradeInfo.party.b.branch.name").input(tradeDetail.getTradeInformation().getPartyBBranchName().getRealValue());
            }
            if (tradeDetail.getTradeInformation().getExchangedNotional1Amount() != null) {
                element("td.tradeInfo.exchangedNotional1Amount").setValue("").type(tradeDetail.getTradeInformation().getExchangedNotional1Amount().getRealValue());
            }

            //ETF Trade information
            if (tradeDetail.getTradeInformation().getOrderType() != null) {
                element("td.tradeInfo.orderType").selectByVisibleText(tradeDetail.getTradeInformation().getOrderType().getRealValue());
            }//

            if (tradeDetail.getTradeInformation().getExchangedNotional1Currency() != null) {
                element("td.tradeInfo.exchangedNotional1Currency").selectByVisibleText(tradeDetail.getTradeInformation().getExchangedNotional1Currency().getRealValue());
            }

            //ETF Trade information
            if (tradeDetail.getTradeInformation().getOrderNumber() != null) {
                element("td.tradeInfo.orderNumber").input(tradeDetail.getTradeInformation().getOrderNumber().getRealValue());
            }//

            if (tradeDetail.getTradeInformation().getExchangedNotional2Amount() != null) {
                element("td.tradeInfo.exchangedNotional2Amount").setValue("").type(tradeDetail.getTradeInformation().getExchangedNotional2Amount().getRealValue());
            }
            //ETF  Trade information
            if (tradeDetail.getTradeInformation().getInstructionReleaseStatus() != null) {
                element("td.tradeInfo.instructionReleaseStatus").selectByVisibleText(tradeDetail.getTradeInformation().getInstructionReleaseStatus().getRealValue());
            }//

            if (tradeDetail.getTradeInformation().getExchangedNotional2Currency() != null) {
                element("td.tradeInfo.exchangedNotional2Currency").selectByVisibleText(tradeDetail.getTradeInformation().getExchangedNotional2Currency().getRealValue());
            }
            if (tradeDetail.getTradeInformation().getBuyOrSell() != null) {
                element("td.tradeInfo.buyOrSell").selectByVisibleText(tradeDetail.getTradeInformation().getBuyOrSell().value());
            }
            if (tradeDetail.getTradeInformation().getModel() != null) {
                element("td.tradeInfo.model").selectByVisibleText(tradeDetail.getTradeInformation().getModel().getRealValue());
            }
            //ETF  Trade information
            if (tradeDetail.getTradeInformation().getCounterpartyOrderNumber() != null) {
                element("td.tradeInfo.cptyOrderNumber").input(tradeDetail.getTradeInformation().getCounterpartyOrderNumber().getRealValue());
            }//

        }
    }

    public void setValuationInformation(TradeDetail tradeDetail) throws Exception {

        if (tradeDetail.getValuationInformation() != null) {
            if (tradeDetail.getValuationInformation().getPrincipalValuationAmount() != null) {
                //click principal valuation amount radio group
                element("td.valuationInfo.principalValuationRule").click();
                if (tradeDetail.getValuationInformation().getPrincipalValuationAmount().getAmount() != null) {
                    element("td.valuationInfo.principalValuationAmount").setValue("")
                            .type(tradeDetail.getValuationInformation().getPrincipalValuationAmount().getAmount().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
                    waitThat().jQuery().toBeInactive();
                    waitThat().document().toBeReady();
                }
                if (tradeDetail.getValuationInformation().getPrincipalValuationAmount().getCurrency() != null) {
                    element("td.valuationInfo.principalValuationCurrency")
                            .selectByVisibleText(tradeDetail.getValuationInformation().getPrincipalValuationAmount().getCurrency().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
                    waitThat().jQuery().toBeInactive();
                    waitThat().document().toBeReady();
                }
            }
            if (tradeDetail.getValuationInformation().getValuationNativeCurrencyAmount() != null) {
                if (tradeDetail.getValuationInformation().getValuationNativeCurrencyAmount().getAmount() != null) {
                    element("td.valuationInfo.nativeAmount").setValue("").type(tradeDetail.getValuationInformation().getValuationNativeCurrencyAmount().getAmount().getRealValue());
                }
                if (tradeDetail.getValuationInformation().getValuationNativeCurrencyAmount().getCurrency() != null) {
                    element("td.valuationInfo.nativeCurrency").selectByVisibleText(tradeDetail.getValuationInformation().getValuationNativeCurrencyAmount().getCurrency().getRealValue());
                }
            }
            if (tradeDetail.getValuationInformation().getCounterPartyValuationAmount() != null) {
                //click counterparty valuation amount radio group
                element("td.valuationInfo.counterpartyValuationRule").click();
                if (tradeDetail.getValuationInformation().getCounterPartyValuationAmount().getAmount() != null) {
                    element("td.valuationInfo.counterpartyValuationAmount").setValue("")
                            .type(tradeDetail.getValuationInformation().getCounterPartyValuationAmount().getAmount().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
                    waitThat().jQuery().toBeInactive();
                    waitThat().document().toBeReady();
                }
                if (tradeDetail.getValuationInformation().getCounterPartyValuationAmount().getCurrency() != null) {
                    element("td.valuationInfo.counterpartyValuationCurrency")
                            .selectByVisibleText(tradeDetail.getValuationInformation().getCounterPartyValuationAmount().getCurrency().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
                    waitThat().jQuery().toBeInactive();
                    waitThat().document().toBeReady();
                }
            }
            if (tradeDetail.getValuationInformation().getAlternativeValuationAmount() != null) {
                if (tradeDetail.getValuationInformation().getAlternativeValuationAmount().getAmount() != null) {
                    element("td.valuationInfo.alternativeAmount").input(tradeDetail.getValuationInformation().getAlternativeValuationAmount().getAmount().getRealValue());
                }
                if (tradeDetail.getValuationInformation().getAlternativeValuationAmount().getCurrency() != null) {
                    element("td.valuationInfo.alternativeCurrency").selectByVisibleText(tradeDetail.getValuationInformation().getAlternativeValuationAmount().getCurrency().getRealValue());
                }
            }

            if (tradeDetail.getValuationInformation().getIndependentValuationAmount() != null) {
                //click independent valuation amount radio group
                element("td.valuationInfo.independentValuationRule").click();
                if (tradeDetail.getValuationInformation().getIndependentValuationAmount().getAmount() != null) {
                    element("td.valuationInfo.independentValuationAmount").setValue("")
                            .type(tradeDetail.getValuationInformation().getIndependentValuationAmount().getAmount().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
                    waitThat().jQuery().toBeInactive();
                    waitThat().document().toBeReady();
                }
                if (tradeDetail.getValuationInformation().getIndependentValuationAmount().getCurrency() != null) {
                    element("td.valuationInfo.independentyValuationCurrency")
                            .selectByVisibleText(tradeDetail.getValuationInformation().getIndependentValuationAmount().getCurrency().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
                    waitThat().jQuery().toBeInactive();
                    waitThat().document().toBeReady();
                }
            }

            if (tradeDetail.getValuationInformation().getSourceSystem() != null) {
                element("td.valuationInfo.sourceSystem").selectByVisibleText(tradeDetail.getValuationInformation().getSourceSystem().getRealValue());
            }
            if (tradeDetail.getValuationInformation().isOverridden() != null) {
                if (tradeDetail.getValuationInformation().isOverridden()) {
                    element("td.valuationInfo.manuallyOverridenValuation").check(tradeDetail.getValuationInformation().isOverridden());
                }
            }
            if (tradeDetail.getValuationInformation().isLockedTradeValues() != null) {
                if (tradeDetail.getValuationInformation().isLockedTradeValues()) {
                    element("td.valuationInfo.lockedTradeValues").check(tradeDetail.getValuationInformation().isLockedTradeValues());
                }
            }
            if (tradeDetail.getValuationInformation().isAllowFeedOverrrideLockedValues() != null) {
                if (tradeDetail.getValuationInformation().isAllowFeedOverrrideLockedValues()) {
                    element("td.valuationInfo.allowFeedOverrideLockedValues").check(tradeDetail.getValuationInformation().isAllowFeedOverrrideLockedValues());
                }
            }
            if (tradeDetail.getValuationInformation().getValuationDate() != null) {
                element("td.valuationInfo.valuationDate").input(tradeDetail.getValuationInformation().getValuationDate().getRealValue());
            }
            if (tradeDetail.getValuationInformation().isExcludeValueFromCalculations() != null) {
                if (tradeDetail.getValuationInformation().isExcludeValueFromCalculations()) {
                    element("td.valuationInfo.excludeValueFromCalculations").check(tradeDetail.getValuationInformation().isExcludeValueFromCalculations());
                }
            }
        }
    }

    public void setCollateralInformation(TradeDetail tradeDetail) throws Exception {

        if (tradeDetail.getCollateralInformation() != null) {
            if (tradeDetail.getCollateralInformation().isDealLevelUpfront() != null) {
                if (tradeDetail.getCollateralInformation().isDealLevelUpfront()) {
                    element("td.collateralInfo.dealLevelUpfront").check(tradeDetail.getCollateralInformation().isDealLevelUpfront());
                }
            }
            if (tradeDetail.getCollateralInformation().getDealLevelType() != null) {
                element("td.collateralInfo.dealLevelType").selectByVisibleText(tradeDetail.getCollateralInformation().getDealLevelType().value());
            }
            if (tradeDetail.getCollateralInformation().getDealLevelAmount() != null) {
                String value = Biz.convertAmount(tradeDetail.getCollateralInformation().getDealLevelAmount().getRealValue());
                Double dealLevelAmt = Double.parseDouble(value.replace(",", ""));
                if (dealLevelAmt > 0){
                    element("td.collateralInfo.cpty.dealLevelAmount").setValue("").type(tradeDetail.getCollateralInformation().getDealLevelAmount().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
                }else{
                    element("td.collateralInfo.prin.dealLevelAmount").setValue("").type(tradeDetail.getCollateralInformation().getDealLevelAmount().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
                }
//                element("td.collateralInfo.dealLevelAmount").setValue("").type(tradeDetail.getCollateralInformation().getDealLevelAmount().getRealValue());
            }
            if (tradeDetail.getCollateralInformation().getPrinDealLevelAmount() != null)
                element("td.collateralInfo.prin.dealLevelAmount").setValue("").type(tradeDetail.getCollateralInformation().getPrinDealLevelAmount().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
            if (tradeDetail.getCollateralInformation().getCptyDealLevelAmount() != null)
                element("td.collateralInfo.cpty.dealLevelAmount").setValue("").type(tradeDetail.getCollateralInformation().getCptyDealLevelAmount().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
            if (tradeDetail.getCollateralInformation().getCollateralComment() != null) {
                element("td.collateralInfo.collateralComment").input(tradeDetail.getCollateralInformation().getCollateralComment().getRealValue());
            }
            if (tradeDetail.getCollateralInformation().getCurrency() != null) {
                element("td.collateralInfo.currency").selectByVisibleText(tradeDetail.getCollateralInformation().getCurrency().getRealValue());
            }
        }
    }

    public void setTradeMiscellaneousFields(TradeDetail tradeDetail) throws Exception {
        if (tradeDetail.getTradeMiscellaneousFields() != null && tradeDetail.getTradeMiscellaneousFields().size() > 0) {
            element("td.miscellaneous.expand").click();
            for (Field miscField : tradeDetail.getTradeMiscellaneousFields()) {
                if (miscField.getFieldName() != null && miscField.getValue() != null) {
                    element("td.miscellaneous", miscField.getFieldName().getRealValue()).input(miscField.getValue().getRealValue());
                }
            }
        }
    }

    public void setSecurityInformation(TradeDetail tradeDetail) throws Exception {
        if (tradeDetail.getSecurityInformation() != null) {
            if (tradeDetail.getSecurityInformation().getInstrumentId() != null) {
                if (tradeDetail.getSecurityInformation().getInstrumentId().getFilter() != null) {
                    element("td.securityInfo.insruId.filter").input(tradeDetail.getSecurityInformation().getInstrumentId().getFilter().getRealValue());
                }
                if (tradeDetail.getSecurityInformation().getInstrumentId().getLinkText() != null) {
                    element("td.securityInfo.insruId.linktext", tradeDetail.getSecurityInformation().getInstrumentId().getLinkText().getRealValue()).click();
                    waitThat().timeout(PropHelper.TIMEOUT_NAVIGATION_INTERVAL);
                    waitThat().jQuery().toBeInactive();
                    waitThat().document().toBeReady();
                }
            }
            if (tradeDetail.getSecurityInformation().getNotional() != null) {
                element("td.securityInfo.notional").setValue("")
                        .type(tradeDetail.getSecurityInformation().getNotional().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
                waitThat().jQuery().toBeInactive();
                waitThat().document().toBeReady();
            }
            if (tradeDetail.getSecurityInformation().getCouponAccrual() != null) {
                element("td.securityInfo.couponAccrual")
                        .input(tradeDetail.getSecurityInformation().getCouponAccrual().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
                waitThat().jQuery().toBeInactive();
                waitThat().document().toBeReady();
            }
            if (tradeDetail.getSecurityInformation().getHaircut() != null) {
                element("td.securityInfo.haircut")
                        .input(tradeDetail.getSecurityInformation().getHaircut().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
                waitThat().jQuery().toBeInactive();
                waitThat().document().toBeReady();
            }
            if (tradeDetail.getSecurityInformation().getTradePrice() != null) {
                element("td.securityInfo.tradePrice").input(tradeDetail.getSecurityInformation().getTradePrice().getRealValue());
            }
            if (tradeDetail.getSecurityInformation().getCleanPrice() != null) {
                element("td.securityInfo.cleanPrice").setValue("")
                        .type(tradeDetail.getSecurityInformation().getCleanPrice().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
                waitThat().jQuery().toBeInactive();
                waitThat().document().toBeReady();
            }
            if (tradeDetail.getSecurityInformation().getPriceSource() != null) {
                element("td.securityInfo.priceSource")
                        .selectByVisibleText(tradeDetail.getSecurityInformation().getPriceSource().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
                waitThat().jQuery().toBeInactive();
                waitThat().document().toBeReady();
            }
            if (tradeDetail.getSecurityInformation().getSettlementStatus() != null) {
                element("td.securityInfo.settlementStatus").selectByVisibleText(tradeDetail.getSecurityInformation().getSettlementStatus().getRealValue());
            }
            //this two methods blow is for repo trade
            if (tradeDetail.getSecurityInformation().getSettlementStatusStartLeg() != null) {
                element("td.securityInfo.settlementStatusStartLeg")
                        .selectByVisibleText(tradeDetail.getSecurityInformation().getSettlementStatusStartLeg().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
                waitThat().jQuery().toBeInactive();
                waitThat().document().toBeReady();
            }
            if (tradeDetail.getSecurityInformation().getSettlementStatusCloseLeg() != null) {
                element("td.securityInfo.settlementStatusCloseLeg")
                        .selectByVisibleText(tradeDetail.getSecurityInformation().getSettlementStatusCloseLeg().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
                waitThat().jQuery().toBeInactive();
                waitThat().document().toBeReady();
            }
            if (tradeDetail.getSecurityInformation().isTradeFinra4210Exempt() != null) {
                element("td.securityInfo.finra4210Exempt").check(tradeDetail.getSecurityInformation().isTradeFinra4210Exempt());
            }
        }
    }

    public void setCashInformation(TradeDetail tradeDetail) throws Exception {
        if (tradeDetail.getCashInformation() != null) {

            //the following methods before isAllowFeedOverrideLockedValues is for ETF trade
            if (tradeDetail.getCashInformation().getCashComponent() != null) {
                if (tradeDetail.getCashInformation().getCashComponent().getAmount() != null) {
                    element("td.cashInfo.cashComponent").input(tradeDetail.getCashInformation().getCashComponent().getAmount().getRealValue());
                }
                if (tradeDetail.getCashInformation().getCashComponent().getCurrency() != null) {
                    element("td.cashInfo.cashComponentCcy").selectByVisibleText(tradeDetail.getCashInformation().getCashComponent().getCurrency().getRealValue());
                }
            }

            if (tradeDetail.getCashInformation().getFee() != null) {
                if (tradeDetail.getCashInformation().getFee().getAmount() != null) {
                    element("td.cashInfo.fee").setValue(tradeDetail.getCashInformation().getFee().getAmount().getRealValue());
                    element("td.cashInfo.fee").fireEvent(Biz.FIRE_EVENT_ONCHANGE);
//                    element("td.cashInfo.fee").input("");
//                    element("td.cashInfo.fee").type(tradeDetail.getCashInformation().getFee().getAmount().getRealValue());
                }
                if (tradeDetail.getCashInformation().getFee().getCurrency() != null) {
                    element("td.cashInfo.feeCcy").selectByVisibleText(tradeDetail.getCashInformation().getFee().getCurrency().getRealValue());
                }
            }
            if (tradeDetail.getCashInformation().isLockedTradeValues() != null) {
                if (tradeDetail.getCashInformation().isLockedTradeValues()) {
                    element("td.cashInfo.lockedTradeValues").check(tradeDetail.getCashInformation().isLockedTradeValues());
                }
            }
            if (tradeDetail.getCashInformation().isAllowFeedOverrideLockedValues() != null) {
                if (tradeDetail.getCashInformation().isAllowFeedOverrideLockedValues()) {
                    element("td.cashInfo.allowFeedOverrideLockedValues").check(tradeDetail.getCashInformation().isAllowFeedOverrideLockedValues());
                }
            }
            //the following methods is suite for REPO trade
            if (tradeDetail.getCashInformation().getCashAssetType() != null) {
                if (tradeDetail.getCashInformation().getCashAssetType().getFilter() != null) {
                    element("td.cashInfo.cashAssetType.filter").input(tradeDetail.getCashInformation().getCashAssetType().getFilter().getRealValue());
                }
                if (tradeDetail.getCashInformation().getCashAssetType().getLinkText() != null) {
                    element("td.cashInfo.cashAssetType.linktext", tradeDetail.getCashInformation().getCashAssetType().getLinkText().getRealValue()).click();
                    waitThat().timeout(PropHelper.TIMEOUT_NAVIGATION_INTERVAL);
                    waitThat().jQuery().toBeInactive();
                    waitThat().document().toBeReady();
                }

            }
            if (tradeDetail.getCashInformation().getOpenCash() != null) {
                element("td.cashInfo.openCash").setValue("")
                        .type(tradeDetail.getCashInformation().getOpenCash().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
                waitThat().jQuery().toBeInactive();
                waitThat().document().toBeReady();
            }
            if (tradeDetail.getCashInformation().getRepoInterestAccrued() != null) {
                element("td.cashInfo.repoInterestAccrued")
                        .input(tradeDetail.getCashInformation().getRepoInterestAccrued().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
                waitThat().jQuery().toBeInactive();
                waitThat().document().toBeReady();
            }
            if (tradeDetail.getCashInformation().getRepoRate() != null) {
                element("td.cashInfo.repoRate")
                        .input(tradeDetail.getCashInformation().getRepoRate().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
                waitThat().jQuery().toBeInactive();
                waitThat().document().toBeReady();
            }
            if (tradeDetail.getCashInformation().getCloseCash() != null) {
                element("td.cashInfo.closeCash").setValue("").type(tradeDetail.getCashInformation().getCloseCash().getRealValue());
            }
            if (tradeDetail.getCashInformation().getAccrualBasis() != null) {
                element("td.cashInfo.accrualBasis")
                        .selectByVisibleText(tradeDetail.getCashInformation().getAccrualBasis().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
                waitThat().jQuery().toBeInactive();
                waitThat().document().toBeReady();
            }
            if (tradeDetail.getCashInformation().getHaircut() != null) {
                element("td.cashInfo.haircut").input(tradeDetail.getCashInformation().getHaircut().getRealValue())
                        .fireEvent(Biz.FIRE_EVENT_ONCHANGE);
                waitThat().jQuery().toBeInactive();
                waitThat().document().toBeReady();
            }
            if (tradeDetail.getCashInformation().getSettlementStatusStartLeg() != null) {
                element("td.cashInfo.settlementStatusStartLeg")
                        .selectByVisibleText(tradeDetail.getCashInformation().getSettlementStatusStartLeg().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
                waitThat().jQuery().toBeInactive();
                waitThat().document().toBeReady();
            }
            if (tradeDetail.getCashInformation().getSettlementStatusCloseLeg() != null) {
                element("td.cashInfo.settlementStatusCloseLeg")
                        .selectByVisibleText(tradeDetail.getCashInformation().getSettlementStatusCloseLeg().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
                waitThat().jQuery().toBeInactive();
                waitThat().document().toBeReady();
            }
        }
    }

    public void setExposureInformation(TradeDetail tradeDetail) throws Exception {

        if (tradeDetail.getExposureInformation() != null) {
            if (tradeDetail.getExposureInformation().getPrincipalValuationAmount() != null) {
//                element("td.exposureInfo.principalValuationRule").click();
                if (tradeDetail.getExposureInformation().getPrincipalValuationAmount().getAmount() != null) {
                    element("td.exposureInfo.principalValuationAmount").setValue("")
                            .type(tradeDetail.getExposureInformation().getPrincipalValuationAmount().getAmount().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
                    waitThat().jQuery().toBeInactive();
                    waitThat().document().toBeReady();
                }
                if (tradeDetail.getExposureInformation().getPrincipalValuationAmount().getCurrency() != null) {
                    element("td.exposureInfo.principalValuationCurrency")
                            .selectByVisibleText(tradeDetail.getExposureInformation().getPrincipalValuationAmount().getCurrency().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
                    waitThat().jQuery().toBeInactive();
                    waitThat().document().toBeReady();
                }
            }
            if (tradeDetail.getExposureInformation().getCounterPartyValuationAmount() != null) {
//                element("td.exposureInfo.counterpartyValuationRule").click();
                if (tradeDetail.getExposureInformation().getCounterPartyValuationAmount().getAmount() != null) {
                    element("td.exposureInfo.counterpartyValuationAmount").setValue("")
                            .type(tradeDetail.getExposureInformation().getCounterPartyValuationAmount().getAmount().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
                    waitThat().jQuery().toBeInactive();
                    waitThat().document().toBeReady();
                }
                if (tradeDetail.getExposureInformation().getCounterPartyValuationAmount().getCurrency() != null) {
                    element("td.exposureInfo.counterpartyValuationCurrency")
                            .selectByVisibleText(tradeDetail.getExposureInformation().getCounterPartyValuationAmount().getCurrency().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
                    waitThat().jQuery().toBeInactive();
                    waitThat().document().toBeReady();
                }
            }
            if (tradeDetail.getExposureInformation().getSourceSystem() != null) {
                element("td.exposureInfo.sourceSystem").selectByVisibleText(tradeDetail.getExposureInformation().getSourceSystem().getRealValue());
            }

            if (tradeDetail.getExposureInformation().getIndependentValuationAmount() != null) {
//                element("td.exposureInfo.independentValuationRule").click();
                if (tradeDetail.getExposureInformation().getIndependentValuationAmount().getAmount() != null) {
                    element("td.exposureInfo.independentValuationAmount").setValue("")
                            .type(tradeDetail.getExposureInformation().getIndependentValuationAmount().getAmount().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
                    waitThat().jQuery().toBeInactive();
                    waitThat().document().toBeReady();
                }
                if (tradeDetail.getExposureInformation().getIndependentValuationAmount().getCurrency() != null) {
                    element("td.exposureInfo.independentyValuationCurrency")
                            .selectByVisibleText(tradeDetail.getExposureInformation().getIndependentValuationAmount().getCurrency().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
                    waitThat().jQuery().toBeInactive();
                    waitThat().document().toBeReady();
                }
            }
            if (tradeDetail.getExposureInformation().getValutionAmountType() != null) {
                waitThat().document().toBeReady();
                waitThat().jQuery().toBeInactive();
                if (tradeDetail.getExposureInformation().getValutionAmountType().equals(ValutionAmountType.PRINCIPAL_VALUATION_AMOUNT))
                    element("td.exposureInfo.principalValuationRule").click();
                if (tradeDetail.getExposureInformation().getValutionAmountType().equals(ValutionAmountType.COUNTERPARTY_VALUATION_AMOUNT))
                    //click counterparty valuation amount radio group
                    element("td.exposureInfo.counterpartyValuationRule").click();
                if (tradeDetail.getExposureInformation().getValutionAmountType().equals(ValutionAmountType.INDEPENDENT_VALUATION_AMOUNT))
                    //click independent valuation amount radio group
                    element("td.exposureInfo.independentValuationRule").click();
            }

            if (tradeDetail.getExposureInformation().isExcludeValueFromCalculations() != null) {
                if (tradeDetail.getExposureInformation().isExcludeValueFromCalculations()) {
                    element("td.exposureInfo.excludeValueFromCalulations").check(tradeDetail.getExposureInformation().isExcludeValueFromCalculations());
                }
            }
            if (tradeDetail.getExposureInformation().getValuationNativeCurrencyAmount() != null) {
                if (tradeDetail.getExposureInformation().getValuationNativeCurrencyAmount().getAmount() != null) {
                    element("td.exposureInfo.nativeAmount").setValue("").type(tradeDetail.getExposureInformation().getValuationNativeCurrencyAmount().getAmount().getRealValue());
                }
                if (tradeDetail.getExposureInformation().getValuationNativeCurrencyAmount().getCurrency() != null) {
                    element("td.exposureInfo.nativeCurrency").setValue("").type(tradeDetail.getExposureInformation().getValuationNativeCurrencyAmount().getCurrency().getRealValue());
                }
            }
            if (tradeDetail.getExposureInformation().getCollateralComment() != null) {
                element("td.exposureInfo.collateralComment").input(tradeDetail.getExposureInformation().getCollateralComment().getRealValue());
            }
            if (tradeDetail.getExposureInformation().isOverridden() != null) {
                if (tradeDetail.getExposureInformation().isOverridden()) {
                    element("td.exposureInfo.overridenBy").check(tradeDetail.getExposureInformation().isOverridden());
                }
            }
            if (tradeDetail.getExposureInformation().getValuationDate() != null) {
                element("td.exposureInfo.valuationDate").input(tradeDetail.getExposureInformation().getValuationDate().getRealValue()).fireEvent(Biz.FIRE_EVENT_ONCHANGE);
                waitThat().jQuery().toBeInactive();
                waitThat().document().toBeReady();
            }
        }
    }

    public void setAdjustmentInformation(TradeDetail tradeDetail) throws Exception {

        if (tradeDetail.getAdjustmentInformation() != null) {
            //Held Over Coupon Adjustment ()
            if (tradeDetail.getAdjustmentInformation().getHeldOverCouponAdjustmentSecurityCurrency() != null) {
                if (tradeDetail.getAdjustmentInformation().getHeldOverCouponAdjustmentSecurityCurrency().getAmount() != null) {
                    element("td.adjustInfo.adjustmentSecCcy").input(tradeDetail.getAdjustmentInformation().getHeldOverCouponAdjustmentSecurityCurrency().getAmount().getRealValue());
                }
            }

            //Held Over Coupon Adjustment (USD)
            if (tradeDetail.getAdjustmentInformation().getHeldOverCouponAdjustmentAgreementCurrency() != null) {
                if (tradeDetail.getAdjustmentInformation().getHeldOverCouponAdjustmentAgreementCurrency().getAmount() != null) {
                    element("td.adjustInfo.adjustmentAgrCcy").input(tradeDetail.getAdjustmentInformation().getHeldOverCouponAdjustmentAgreementCurrency().getAmount().getRealValue());
                }
            }

            //Coupon Reinvestment ()
            if (tradeDetail.getAdjustmentInformation().getCouponReinvestmentSecurityCurrency() != null) {
                if (tradeDetail.getAdjustmentInformation().getCouponReinvestmentSecurityCurrency().getAmount() != null) {
                    element("td.adjustInfo.couponReinvestmentSecCcy").input(tradeDetail.getAdjustmentInformation().getCouponReinvestmentSecurityCurrency().getAmount().getRealValue());
                }
            }
            //Coupon Reinvestment (USD)
            if (tradeDetail.getAdjustmentInformation().getCouponReinvestmentAgreementCurrency() != null) {
                if (tradeDetail.getAdjustmentInformation().getCouponReinvestmentAgreementCurrency().getAmount() != null) {
                    element("td.adjustInfo.couponReinvestmentAgrCcy").input(tradeDetail.getAdjustmentInformation().getCouponReinvestmentAgreementCurrency().getAmount().getRealValue());
                }
            }

        }
    }


    public void inputTradeInformation(TradeInformation tradeInformation) throws Exception {
        if (tradeInformation.getTradeDate() != null) {
            element("td.date").input(tradeInformation.getTradeDate().getRealValue());
        }
        if (tradeInformation.getEffectiveDate() != null) {
            element("td.effective.date").input(tradeInformation.getEffectiveDate().getRealValue());
        }
        if (tradeInformation.getMaturityDate() != null) {
            element("td.maturity.date").input(tradeInformation.getMaturityDate().getRealValue());
        }
        if (tradeInformation.getStartDate() != null) {
            element("td.effective.date").input(tradeInformation.getStartDate().getRealValue());
        }
        if (tradeInformation.getEndDate() != null) {
            element("td.maturity.date").input(tradeInformation.getEndDate().getRealValue());
        }
        if (tradeInformation.getTradeIdentifierPartyA() != null) {
            element("td.id.party.a").input(tradeInformation.getTradeIdentifierPartyA().getRealValue());
        }
        if (tradeInformation.getPartyABranchName() != null) {
            element("td.party.a.branch.name").input(tradeInformation.getPartyABranchName().getRealValue());
        }
        if (tradeInformation.getPartyBBranchName() != null) {
            element("td.party.b.branch.name").input(tradeInformation.getPartyBBranchName().getRealValue());
        }
        if (tradeInformation.getOrderNumber() != null) {
            element("td.order.no").input(tradeInformation.getOrderNumber().getRealValue());
        }
    }

    public void inputTradeValuationInformation(TradeValuationInformation tradeValuationInformation) throws Exception {
        if (tradeValuationInformation.getPrincipalValuationAmount() != null) {
            if (tradeValuationInformation.getPrincipalValuationAmount().getAmount() != null) {
                element("td.prin.val.amt").input(tradeValuationInformation.getPrincipalValuationAmount().getAmount().getRealValue());
            }
        }
        if (tradeValuationInformation.getCounterPartyValuationAmount() != null) {
            if (tradeValuationInformation.getCounterPartyValuationAmount().getAmount() != null) {
                element("td.cpty.valuation.amount").setValue("").type(tradeValuationInformation.getCounterPartyValuationAmount().getAmount().getRealValue());
            }
        }
        if (tradeValuationInformation.getIndependentValuationAmount() != null) {
            if (tradeValuationInformation.getIndependentValuationAmount().getAmount() != null) {
                element("td.independent.valuation.amount").setValue("").type(tradeValuationInformation.getIndependentValuationAmount().getAmount().getRealValue());
            }
        }
        if (tradeValuationInformation.getValuationDate() != null) {
            element("td.valuation.date").input(tradeValuationInformation.getValuationDate().getRealValue());
        }
    }

    public void inputCcpTradeInformation(CcpTradeInformation ccpTradeInformation) throws Exception {
        if (ccpTradeInformation.getPaiRate() != null) {
            element("td.pai.rate").input(ccpTradeInformation.getPaiRate().getRealValue());
        }
        if (ccpTradeInformation.getFixedCoupon() != null) {
            if (ccpTradeInformation.getFixedCoupon().getAmount() != null)
                element("td.fixed.coupon").input(ccpTradeInformation.getFixedCoupon().getAmount().getRealValue());
            if (ccpTradeInformation.getFixedCoupon().getCurrency() != null)
                element("td.fixed.coupon.ccy").selectByVisibleText(ccpTradeInformation.getFixedCoupon().getCurrency().getRealValue());
        }
        if (ccpTradeInformation.getFixedRate() != null) {
            element("td.fixed.rate").input(ccpTradeInformation.getFixedRate().getRealValue());
        }
        if (ccpTradeInformation.getFloatingCoupon() != null) {
            if (ccpTradeInformation.getFloatingCoupon().getAmount() != null)
                element("td.floating.coupon").input(ccpTradeInformation.getFloatingCoupon().getAmount().getRealValue());
            if (ccpTradeInformation.getFloatingCoupon().getCurrency() != null)
                element("td.floating.coupon.ccy").selectByVisibleText(ccpTradeInformation.getFloatingCoupon().getCurrency().getRealValue());
        }
        if (ccpTradeInformation.getFloatingIndex() != null)
            element("td.floating.index").input(ccpTradeInformation.getFloatingIndex().getRealValue());
        if (ccpTradeInformation.getClearedDate() != null)
            element("td.cleared.date").input(ccpTradeInformation.getClearedDate().getRealValue());
        if (ccpTradeInformation.getAccruedCoupon() != null) {
            if (ccpTradeInformation.getAccruedCoupon().getAmount() != null) {
                element("td.accrued.coupon").input(ccpTradeInformation.getAccruedCoupon().getAmount().getRealValue());
            }
            if (ccpTradeInformation.getAccruedCoupon().getCurrency() != null)
                element("td.accrued.coupon.ccy").selectByVisibleText(ccpTradeInformation.getAccruedCoupon().getCurrency().getRealValue());
        }
        if (ccpTradeInformation.getOriginalPartyB() != null)
            element("td.original.party.b").input(ccpTradeInformation.getOriginalPartyB().getRealValue());
        if (ccpTradeInformation.getTsaCurrency() != null) {
            element("td.tsa.ccy").selectByVisibleText(ccpTradeInformation.getTsaCurrency().getRealValue());
        }else if (ccpTradeInformation.getCashflowCurrency()!=null)
			element("td.tsa.ccy").selectByVisibleText(ccpTradeInformation.getCashflowCurrency().getRealValue());
        if (ccpTradeInformation.getResetToParAmount() != null) {
            element("td.reset.to.par.amount").setValue("").type(ccpTradeInformation.getResetToParAmount().getRealValue());
        }
        if (ccpTradeInformation.getInitialCoupon() != null) {
            element("td.initial.coupon").input(ccpTradeInformation.getInitialCoupon().getRealValue());
        }
        if (ccpTradeInformation.getCreditEventCashAmount() != null) {
            element("td.credit.event.cash.amount").setValue("").type(ccpTradeInformation.getCreditEventCashAmount().getRealValue());
        }
        if (ccpTradeInformation.getSuccessionEventCashAmount() != null) {
            element("td.succession.event.cash.amount").setValue("").type(ccpTradeInformation.getSuccessionEventCashAmount().getRealValue());
        }
        if (ccpTradeInformation.getTransferValue() != null) {
            element("td.transfer.value").input(ccpTradeInformation.getTransferValue().getRealValue());
        }
        if (ccpTradeInformation.getBankedCoupon() != null) {
            element("td.banked.coupon").input(ccpTradeInformation.getBankedCoupon().getRealValue());
        }
        if (ccpTradeInformation.getPai() != null) {
            element("td.pai").input(ccpTradeInformation.getPai().getRealValue());
        }
        if (ccpTradeInformation.getImInterest() != null) {
            element("td.im.interest").input(ccpTradeInformation.getImInterest().getRealValue());
        }
        if (ccpTradeInformation.getNdfCashflow() != null) {
            element("td.ndf.cash.flow").input(ccpTradeInformation.getNdfCashflow().getRealValue());
        }
        if (ccpTradeInformation.getUpfrontFee() != null)
            element("td.upfront.fee").input(ccpTradeInformation.getUpfrontFee().getRealValue());
        if (ccpTradeInformation.getUpfrontFeeSettlementDate() != null)
            element("td.upfront.fee.settlement.date").input(ccpTradeInformation.getUpfrontFeeSettlementDate().getRealValue());

        if (ccpTradeInformation.getTsaMisc1() != null) {
            element("td.tsa.misc1").input(ccpTradeInformation.getTsaMisc1().getRealValue());
        }else if (ccpTradeInformation.getCashflowMisc1()!=null)
			element("td.tsa.misc1").input(ccpTradeInformation.getCashflowMisc1().getRealValue());
        if (ccpTradeInformation.getTsaMisc2() != null) {
            element("td.tsa.misc2").input(ccpTradeInformation.getTsaMisc2().getRealValue());
        }else if (ccpTradeInformation.getCashflowMisc2()!=null)
			element("td.tsa.misc2").input(ccpTradeInformation.getCashflowMisc2().getRealValue());
        if (ccpTradeInformation.getTsaMisc3() != null) {
            element("td.tsa.misc3").input(ccpTradeInformation.getTsaMisc3().getRealValue());
        }else if (ccpTradeInformation.getCashflowMisc3()!=null)
			element("td.tsa.misc3").input(ccpTradeInformation.getCashflowMisc3().getRealValue());
    }

    public void inputTradeCollateralInformation(TradeCollateralInformation tradeCollateralInformation) throws Exception {
        if (tradeCollateralInformation.isDealLevelUpfront() != null)
            element("td.deal.level.upfront").check(tradeCollateralInformation.isDealLevelUpfront());
        if (tradeCollateralInformation.getDealLevelType() != null)
            element("td.deal.level.type").selectByVisibleText(tradeCollateralInformation.getDealLevelType().value());
        if (tradeCollateralInformation.getDealLevelAmount() != null)
            element("td.deal.level.amount").setAttribute("value", tradeCollateralInformation.getDealLevelAmount().getRealValue());
    }

    public void inputTradeSecurityInformation(TradeSecurityInformation tradeSecurityInformation) throws Exception {
        if (tradeSecurityInformation.getInstrumentId() != null) {
            if (tradeSecurityInformation.getInstrumentId().getFilter() != null) {
                element("td.instru.id").input(tradeSecurityInformation.getInstrumentId().getFilter().getRealValue());
            }
            if (tradeSecurityInformation.getInstrumentId().getLinkText() != null) {
                element("td.instru.id.link", tradeSecurityInformation.getInstrumentId().getLinkText().getRealValue()).click();
            }
        }
        if (tradeSecurityInformation.getPriceSource() != null) {
            element("td.price.source").selectByVisibleText(tradeSecurityInformation.getPriceSource().getRealValue());
        }
        if (tradeSecurityInformation.getSettlementStatusStartLeg() != null) {
            element("td.sec.settlement.status.start.leg").selectByVisibleText(tradeSecurityInformation.getSettlementStatusStartLeg().getRealValue());
        }
        if (tradeSecurityInformation.getSettlementStatusCloseLeg() != null) {
            element("td.sec.settlement.status.end.leg").selectByVisibleText(tradeSecurityInformation.getSettlementStatusCloseLeg().getRealValue());
        }
        if (tradeSecurityInformation.getNotional() != null) {
            element("td.notional").input(tradeSecurityInformation.getNotional().getRealValue());
        }
        if (tradeSecurityInformation.getTradePrice() != null) {
            element("td.trade.price").input(tradeSecurityInformation.getTradePrice().getRealValue());
        }
        if (tradeSecurityInformation.getCleanPrice() != null) {
            element("td.clean.price").input(tradeSecurityInformation.getCleanPrice().getRealValue());
        }
    }

    public void inputTradeCashInformation(TradeCashInformation tradeCashInformation) throws Exception {
//        if (tradeCashInformation.getCashAssetType() != null) {
//            element("td.cash.asset.type").input(tradeCashInformation.getCashAssetType().getRealValue());
//        }
        if (tradeCashInformation.getOpenCash() != null) {
            element("td.open.cash").input(tradeCashInformation.getOpenCash().getRealValue());
        }
        // fireEvent(OPEN_CASH, "onchange");
        // input(OPEN_CASH, trade.getOpenCash());
        if (tradeCashInformation.getRepoRate() != null) {
            element("td.repo.rate").input(tradeCashInformation.getRepoRate().getRealValue());
        }
        // input(REPO_RATE, trade.getRepoRate());
        if (tradeCashInformation.getCloseCash() != null) {
            element("td.close.cash").input(tradeCashInformation.getCloseCash().getRealValue());
        }
        if (tradeCashInformation.getRepoInterestAccrued() != null) {
            element("td.repo.interest.accured").input(tradeCashInformation.getRepoInterestAccrued().getRealValue());
        }
    }

    public void inputTradeAdjustmentInformation(TradeAdjustmentInformation tradeAdjustmentInformation) throws Exception {
        if (tradeAdjustmentInformation.getCouponReinvestmentSecurityCurrency() != null) {
            if (tradeAdjustmentInformation.getCouponReinvestmentSecurityCurrency().getAmount() != null) {
                element("td.held.over.coupon.adjustment").input(tradeAdjustmentInformation.getCouponReinvestmentSecurityCurrency().getAmount().getRealValue());
            }
        }

        if (tradeAdjustmentInformation.getCouponReinvestmentSecurityCurrency() != null) {
            if (tradeAdjustmentInformation.getCouponReinvestmentSecurityCurrency().getAmount() != null) {
                element("td.coupon.reinvestment").input(tradeAdjustmentInformation.getCouponReinvestmentSecurityCurrency().getAmount().getRealValue());
            }
        }
    }

    public void inputTradeMiscellaneousFields(TradeMiscellaneousFields tradeMiscellaneousFields) {

    }

    public void save() throws Exception {
        element("td.save").click();
    }


    /**
     * assert trade detail on the Collateral - Agreement Exposures - Trade Editor
     *
     * @param tradeDetail
     */
    public void assertTradeDetail(TradeDetail tradeDetail) throws Exception {
        TradeSecurityInformation tradeSecurityInformation = tradeDetail.getSecurityInformation();
        if (tradeSecurityInformation != null) {
            assertTradeSecurityInformation(tradeSecurityInformation);
        }
        TradeCashInformation tradeCashInformation = tradeDetail.getCashInformation();
        if (tradeCashInformation != null) {
            asssetTradeCashInformation(tradeCashInformation);
        }
        TradeValuationInformation tradeValuationInformation = tradeDetail.getExposureInformation();
        if (tradeValuationInformation != null) {
            assertTradeValuationInformation(tradeValuationInformation);
        }
        TradeValuationInformation tradeExposureInformation = tradeDetail.getExposureInformation();
        if (tradeExposureInformation != null) {
            assertTradeValuationInformation(tradeExposureInformation);
        }
    }

    private void assertTradeValuationInformation(TradeValuationInformation tradeValuationInformation) throws Exception {
        DecimalFormat format = new DecimalFormat();
        if (tradeValuationInformation.getSecurityLeg() != null) {
            if (tradeValuationInformation.getSecurityLeg().getAmount() != null) {
                assertThat("td.sec.leg").innerText().isEqualTo(tradeValuationInformation.getSecurityLeg().getAmount().getRealValue());
            }
        }
        if (tradeValuationInformation.getCashLeg() != null) {
            if (tradeValuationInformation.getCashLeg().getAmount() != null) {
                assertThat("td.cash.leg").innerText().isEqualTo(tradeValuationInformation.getCashLeg().getAmount());
            }
        }
        if (tradeValuationInformation.getPrincipalValuationAmount() != null) {
            if (tradeValuationInformation.getPrincipalValuationAmount().getAmount() != null) {
                format.applyPattern("###,###,###,##0.00");
                assertThat("td.prin.valuation.amount").attributeValueOf("value").isEqualTo(
                        format.format(Float.parseFloat(tradeValuationInformation.getPrincipalValuationAmount().getAmount().getRealValue()))
                );
            }
        }
    }

    private void asssetTradeCashInformation(TradeCashInformation tradeCashInformation) throws Exception {
        if (tradeCashInformation.getOpenCash() != null) {
            // Open Cash * [(Repo Rate/100)/Year count basis] * Cash Accrual Day Count
            assertThat("td.cash.gross.value.denom.ccy").innerText().isEqualTo(tradeCashInformation.getOpenCash().getRealValue());
        }
    }

    public void assertTradeSecurityInformation(TradeSecurityInformation tradeSecurityInformation) throws Exception {
        if (tradeSecurityInformation.getDirtyPrice() != null) {
            assertThat("td.dirty.price").innerText().isEqualTo(tradeSecurityInformation.getDirtyPrice().getRealValue());
        }
        if (tradeSecurityInformation.getAllInPrice() != null) {
            assertThat("td.all.in.price").innerText().isEqualTo(tradeSecurityInformation.getAllInPrice().getRealValue());
        }
        if (tradeSecurityInformation.getGrossValueSecurityCurrency() != null) {
            if (tradeSecurityInformation.getGrossValueSecurityCurrency().getAmount() != null) {
                assertThat("td.sec.gross.value.denom.ccy").innerText().isEqualTo(tradeSecurityInformation.getGrossValueSecurityCurrency().getAmount());
            }
        }
        if (tradeSecurityInformation.getGrossValueAgreementCurrency() != null) {
            if (tradeSecurityInformation.getGrossValueAgreementCurrency().getAmount() != null) {
                assertThat("td.sec.gross.value.agr.ccy").innerText().isEqualTo(tradeSecurityInformation.getGrossValueAgreementCurrency().getAmount());
            }
        }
        if (tradeSecurityInformation.getNetValueSecurityCurrency() != null) {
            if (tradeSecurityInformation.getNetValueSecurityCurrency().getAmount() != null) {
                assertThat("td.sec.net.value.denom.ccy").innerText().isEqualTo(tradeSecurityInformation.getNetValueSecurityCurrency().getAmount());
            }
        }
        if (tradeSecurityInformation.getNetValueAgreementCurrency() != null) {
            if (tradeSecurityInformation.getNetValueAgreementCurrency().getAmount() != null) {
                assertThat("td.sec.net.value.agr.ccy").innerText().isEqualTo(tradeSecurityInformation.getNetValueAgreementCurrency().getAmount());
            }
        }
    }

    public void assertTradeDetailByInvoke(TradeDetail tradeDetail) throws Exception {
        if (tradeDetail.getTradeInformation() != null)
            assertTradeDetailByType(tradeDetail.getTradeInformation());
        if (tradeDetail.getValuationInformation() != null)
            assertTradeDetailByType(tradeDetail.getValuationInformation());
        if (tradeDetail.getSecurityInformation() != null)
            assertTradeDetailByType(tradeDetail.getSecurityInformation());
        if (tradeDetail.getCashInformation() != null)
            assertTradeDetailByType(tradeDetail.getCashInformation());
        if (tradeDetail.getAdjustmentInformation() != null) {
            element("td.arrow.icon").click();
            assertTradeDetailByType(tradeDetail.getAdjustmentInformation());
        }
        if (tradeDetail.getCollateralInformation() != null)
            assertTradeDetailByType(tradeDetail.getCollateralInformation());
        if (tradeDetail.getTradeMiscellaneousFields() != null) {
            element("td.arrow.icon").click();
            assertTradeDetailByType(tradeDetail.getTradeMiscellaneousFields());
        }
    }

    public void assertTradeDetailByType(Object object) throws Exception {
        DecimalFormat format = new DecimalFormat();
        Method[] methods = object.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("get")
                    && method.getParameterTypes().length == 0
                    && method.invoke(object) != null) {
                String functionName = Biz.getColumnName(method.getName().substring(3), "(?<!^)(?=[A-Z])");
                if (functionName.contains("Sell") || functionName.contains("Call"))
                    functionName += "?";
                if (functionName.contains("Or"))
                    functionName = functionName.replace("Or", "or");
                if (functionName.contains("Identifier2"))
                    functionName = functionName.replace("Identifier2", "Identifier 2");
                if (functionName.contains("All In Price"))
                    functionName = "All-in Price";
                if (functionName.startsWith("Misc")) {
                    functionName = "Misc " + functionName.substring("Misc".length());
                    if (element("td.miscellaneous.expand").isDisplayed())
                        element("td.miscellaneous.expand").clickByJavaScript();
                }
                if (functionName.contains("Finra4210"))
                    functionName=functionName.replace("Finra4210","FINRA 4210");

                if (method.getDeclaringClass() == TradeSecurityInformation.class && functionName.contains("Haircut") && !functionName.contains("FINRA 4210")) {
                    StringType result = (StringType) method.invoke(object);
                    format.applyPattern("###,###,###,##0.00");
                    assertThat("td.security.information.haircut").attributeValueOf("value").isEqualToIgnoringWhitespace(
                            format.format(Double.parseDouble(result.getRealValue()))
                    );
                } else if (method.getDeclaringClass() == TradeSecurityInformation.class && functionName.contains("Accrual Basis")) {
                    StringType result = (StringType) method.invoke(object);
                    assertThat("td.security.information.accrual.basis").innerText().isEqualToIgnoringWhitespace(result.getRealValue());
                } else if (method.getDeclaringClass() == TradeSecurityInformation.class && functionName.contains("Settlement Status Start Leg")) {
                    StringType result = (StringType) method.invoke(object);
                    assertThat("td.security.information.settlement.status.start.leg").allSelectedTexts().containsOnly(result.getRealValue());
                } else if (method.getDeclaringClass() == TradeSecurityInformation.class && functionName.contains("Settlement Status Close Leg")) {
                    StringType result = (StringType) method.invoke(object);
                    assertThat("td.security.information.settlement.status.close.leg").allSelectedTexts().containsOnly(result.getRealValue());
                } else if (method.getDeclaringClass() == TradeSecurityInformation.class && functionName.contains("Gross Value Agreement Currency")) {
                    int c = (int) element("td.trade.detail.td.type", functionName).getCellColumn();
                    Money result = (Money) method.invoke(object);
                    if (result.getAmount() != null) {
                        assertThat("td.security.information.gross.value.agreement.ccy").innerText().isEqualToIgnoringWhitespace(result.getAmount().getRealValue());
                    }
                    if (result.getCurrency() != null) {
                        assertThat("td.trade.detail.td.type", functionName).innerText().isEqualToIgnoringWhitespace(functionName + " (" + result.getCurrency().getRealValue() + ")");
                    }
                } else if (method.getDeclaringClass() == TradeSecurityInformation.class && functionName.contains("Net Value Agreement Currency")) {
                    int c = (int) element("td.trade.detail.td.type", functionName).getCellColumn();
                    Money result = (Money) method.invoke(object);
                    if (result.getAmount() != null) {
                        assertThat("td.security.information.net.value.agreement.ccy").innerText().isEqualToIgnoringWhitespace(result.getAmount().getRealValue());
                    }
                    if (result.getCurrency() != null) {
                        assertThat("td.trade.detail.td.type", functionName).innerText().isEqualToIgnoringWhitespace(functionName + " (" + result.getCurrency().getRealValue() + ")");
                    }
                } else if (method.getDeclaringClass() == TradeCashInformation.class && functionName.contains("Haircut")) {
                    StringType result = (StringType) method.invoke(object);
                    format.applyPattern("###,###,###,##0.00");
                    assertThat("td.cash.information.haircut").attributeValueOf("value").isEqualToIgnoringWhitespace(
                            format.format(Double.parseDouble(result.getRealValue()))
                    );
                } else if (method.getDeclaringClass() == TradeCashInformation.class && functionName.contains("Accrual Basis")) {
                    StringType result = (StringType) method.invoke(object);
                    assertThat("td.cash.information.accrual.basis").allSelectedTexts().containsOnly(result.getRealValue());
                } else if (method.getDeclaringClass() == TradeCashInformation.class && functionName.contains("Settlement Status Start Leg")) {
                    StringType result = (StringType) method.invoke(object);
                    assertThat("td.cash.information.settlement.status.start.leg").allSelectedTexts().containsOnly(result.getRealValue());
                } else if (method.getDeclaringClass() == TradeCashInformation.class && functionName.contains("Settlement Status Close Leg")) {
                    StringType result = (StringType) method.invoke(object);
                    assertThat("td.cash.information.settlement.status.close.leg").allSelectedTexts().containsOnly(result.getRealValue());
                } else if (method.getDeclaringClass() == TradeCashInformation.class && functionName.contains("Gross Value Agreement Currency")) {
                    int c = (int) element("td.trade.detail.td.type", functionName).getCellColumn();
                    Money result = (Money) method.invoke(object);
                    if (result.getAmount() != null) {
                        assertThat("td.cash.information.gross.value.agreement.ccy").innerText().isEqualToIgnoringWhitespace(result.getAmount().getRealValue());
                    }
                    if (result.getCurrency() != null) {
                        assertThat("td.trade.detail.td.type", functionName).innerText().isEqualToIgnoringWhitespace(functionName + " (" + result.getCurrency().getRealValue() + ")");
                    }
                } else if (method.getDeclaringClass() == TradeCashInformation.class && functionName.contains("Net Value Agreement Currency")) {
                    int c = (int) element("td.trade.detail.td.type", functionName).getCellColumn();
                    Money result = (Money) method.invoke(object);
                    if (result.getAmount() != null) {
                        assertThat("td.cash.information.net.value.agreement.ccy").innerText().isEqualToIgnoringWhitespace(result.getAmount().getRealValue());
                    }
                    if (result.getCurrency() != null) {
                        assertThat("td.trade.detail.td.type", functionName).innerText().isEqualToIgnoringWhitespace(functionName + " (" + result.getCurrency().getRealValue() + ")");
                    }
                } else {
                    if (method.getReturnType() == StringType.class) {
                        int c;
                        try {
                            c = (int) element("td.trade.detail.td.type", functionName).getCellColumn();
                        } catch (Exception e) {
                            if (functionName.contains("Identifier 2"))
                                functionName = functionName.replace("Identifier 2", "Identifier2");
                            c = (int) element("td.trade.detail.td.type", functionName).getCellColumn();
                        }
                        StringType result = (StringType) method.invoke(object);
                        if (element("td.trade.detail.td.value", functionName, String.valueOf(c + 1), "/input").isDisplayed()) {
                            StringBuffer value = new StringBuffer();
                            if (functionName.contains("Amount")
                                    || functionName.contains("Notional")
                                    || functionName.contains("Haircut")
                                    || functionName.contains("Open Cash")
                                    || functionName.contains("Close Cash")
                                    || functionName.contains("Held Over Counpon Adjustment")
                                    || functionName.contains("Coupon Reinvestment")
                                    || (functionName.matches("^Misc [2-3][0-9]$") && !functionName.equals("Misc 20"))) {
                                format.applyPattern("###,###,###,##0.00");
                                value.append(format.format(Double.parseDouble(result.getRealValue())));
                            } else if (functionName.contains("Strike Price")
                                    || functionName.contains("Repo Rate")) {
                                format.applyPattern("###,###,###,##0.000");
                                value.append(format.format(Double.parseDouble(result.getRealValue())));
                            } else if (functionName.contains("Coupon Accrual")
                                    || functionName.contains("Trade Price")
                                    || functionName.contains("Clean Price")
                                    || functionName.contains("Repo Interest Accrued")) {
                                format.applyPattern("###,###,###,##0.0000000");
                                value.append(format.format(Double.parseDouble(result.getRealValue())));
                            } else {
                                value.append(result.getRealValue());
                            }
                            assertThat("td.trade.detail.td.value", functionName, String.valueOf(c + 1), "/input").attributeValueOf("value").isEqualToIgnoringWhitespace(value.toString());
                        } else if (element("td.trade.detail.td.value", functionName, String.valueOf(c + 1), "/select").isDisplayed())
                            assertThat("td.trade.detail.td.value", functionName, String.valueOf(c + 1), "/select").allSelectedTexts().containsOnly(result.getRealValue());
                        else
                            assertThat("td.trade.detail.td.value", functionName, String.valueOf(c + 1), "").innerText().isEqualToIgnoringWhitespace(result.getRealValue());
                    } else if (method.getReturnType() == BuyOrSellType.class) {
                        int c = (int) element("td.trade.detail.td.type", functionName).getCellColumn();
                        BuyOrSellType result = (BuyOrSellType) method.invoke(object);
                        assertThat("td.trade.detail.td.value", functionName, String.valueOf(c + 1), "/select").allSelectedTexts().containsOnly(result.value());
                    } else if (method.getReturnType() == Money.class) {
                        int c = (int) element("td.trade.detail.td.type", functionName).getCellColumn();
                        Money result = (Money) method.invoke(object);
                        if (result.getAmount() != null) {
                            format.applyPattern("###,###,###,##0.00");
                            if (element("td.trade.detail.td.value", functionName, String.valueOf(c + 1), "/input").isDisplayed()) {
                                assertThat("td.trade.detail.td.value", functionName, String.valueOf(c + 1), "/input").attributeValueOf("value").isEqualToIgnoringWhitespace(
                                        format.format(Double.parseDouble(result.getAmount().getRealValue()))
                                );
                            } else {
                                assertThat("td.trade.detail.td.value", functionName, String.valueOf(c + 1), "").innerText().isEqualToIgnoringWhitespace(
                                        format.format(Double.parseDouble(result.getAmount().getRealValue()))
                                );
                            }
                        }
                        if (result.getCurrency() != null) {
                            if (element("td.trade.detail.td.value", functionName, String.valueOf(c + 1), "/select").isDisplayed())
                                assertThat("td.trade.detail.td.value", functionName, String.valueOf(c + 1), "/select").allSelectedTexts().containsOnly(result.getCurrency().getRealValue());
                            else
                                assertThat("td.trade.detail.td.type", functionName).innerText().isEqualToIgnoringWhitespace(functionName + " (" + result.getCurrency().getRealValue() + ")");
                        }
                    } else if (method.getReturnType() == Boolean.class) {
                        int c = (int) element("td.trade.detail.td.type", functionName).getCellColumn();
                        Boolean isTrue = (Boolean) method.invoke(object);
                        assertThat("td.trade.detail.td.value", functionName, String.valueOf(c), "/input").enabled().isEqualTo(isTrue);
                    } else if (method.getReturnType() == DealLevelType.class) {
                        int c = (int) element("td.trade.detail.td.type", functionName).getCellColumn();
                        DealLevelType result = (DealLevelType) method.invoke(object);
                        assertThat("td.trade.detail.td.value", functionName, String.valueOf(c + 1), "/select").allSelectedTexts().containsOnly(result.value());
                    } else if (method.getReturnType() == ValutionAmountType.class) {
                        ValutionAmountType result = (ValutionAmountType) method.invoke(object);
                        int c = (int) element("td.trade.detail.td.type", result.value()).getCellColumn();
                        assertThat("td.trade.detail.td.value", result.value(), String.valueOf(c), "/input").attributeValueOf("checked").isEqualTo("true");
                    }
                }
            }
        }
    }
}
