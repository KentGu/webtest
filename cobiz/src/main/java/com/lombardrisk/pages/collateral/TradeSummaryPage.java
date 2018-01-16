package com.lombardrisk.pages.collateral;

import com.lombardrisk.pages.AbstractCollinePage;
import com.lombardrisk.pojo.*;
import com.lombardrisk.util.Biz;
import com.lombardrisk.util.PageHelper;
import org.yiwan.webcore.web.IWebDriverWrapper;

import java.lang.reflect.Method;
import java.text.DecimalFormat;

/**
 * Created by Kenny Wang on 2/3/2016.
 */
public class TradeSummaryPage extends AbstractCollinePage {
    public TradeSummaryPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    public void addTrade() throws Exception {
        waitThat().document().toBeReady();
        waitThat().jQuery().toBeInactive();
        PageHelper.d33640Workaround();
        element("td.add").clickByJavaScript();
    }

    public void backToExporuresSummaryPage() throws Exception {
        element("").click();
    }

    public void editTrade(TradeDetail td) {
    }

    public void deleteTrade(TradeDetail tradeDetail) throws Exception {
        String xpath = getTradeAttributes(tradeDetail);
        xpath += "//img[@alt='Remove']";
        alert().disable();
        element("td.edit", xpath).clickByJavaScript();
        alert().enable();
//        PageHelper.handleAlters(getWebDriverWrapper(), tradeDetail.getAlertHandling());
    }

    public void enterTrade(TradeDetail id) throws Exception {
        String xpath = getTradeAttributes(id);
        xpath += "//img[contains(@src,'edit.gif')]";
        element("td.edit", xpath).click();

    }

    private String getTradeAttributes(TradeDetail result) {
        DecimalFormat format = new DecimalFormat();
        format.applyPattern("###,###,###,##0.00");
        StringBuffer attributes = new StringBuffer();
        attributes.append("//tr");
        if (result.getTradeInformation() != null) {
            if (result.getTradeInformation().getTradeIdentifierPartyA() != null) {
//                attributes.append("[td[contains(text(),'" + result.getTradeInformation().getTradeIdentifierPartyA().getRealValue() + "')]]");
                attributes.append("[td[contains(text(),'").append(result.getTradeInformation().getTradeIdentifierPartyA().getRealValue()).append("')]]");
            }
            if (result.getTradeInformation().getTradeIdentifierPartyB() != null) {
//                attributes.append("[td[contains(text(),'" + result.getTradeInformation().getTradeIdentifierPartyB().getRealValue() + "')]]");
                attributes.append("[td[contains(text(),'").append(result.getTradeInformation().getTradeIdentifierPartyB().getRealValue()).append("')]]");
            }
//            if (result.getTradeInformation().getTradeDate() != null)
//                attributes.append("[td[contains(text(),'" + result.getTradeInformation().getTradeDate().getRealValue() + "')]]");
//            if (result.getTradeInformation().getMaturityDate() != null)
//                attributes.append("[td[contains(text(),'" + result.getTradeInformation().getMaturityDate().getRealValue() + "')]]");
            if (result.getTradeInformation().getExchangedNotional1Amount() != null) {
//                attributes.append("[td[contains(text(),'" +
//                        format.format(Float.parseFloat(result.getTradeInformation().getExchangedNotional1Amount().getRealValue()))
//                        + "')]]");
                attributes.append("[td[contains(text(),'").append(format.format(Float.parseFloat(result.getTradeInformation().getExchangedNotional1Amount().getRealValue()))).append("')]]");
            }
            if (result.getTradeInformation().getExchangedNotional1Currency() != null) {
//                attributes.append("[td[contains(text(),'" + result.getTradeInformation().getExchangedNotional1Currency().getRealValue() + "')]]");
                attributes.append("[td[contains(text(),'").append(result.getTradeInformation().getExchangedNotional1Currency().getRealValue()).append("')]]");
            }
        }
//        if (result.getValuationInformation()!=null){
//            if (result.getValuationInformation().getPrincipalValuationAmount()!=null){
//                if (result.getValuationInformation().getPrincipalValuationAmount().getAmount()!=null)
//                    attributes.append("[td[contains(text(),'"+result.getValuationInformation().getPrincipalValuationAmount().getAmount().getRealValue()+"')]]");
//                if (result.getValuationInformation().getPrincipalValuationAmount().getCurrency()!=null)
//                    attributes.append("[td[contains(text(),'"+result.getValuationInformation().getPrincipalValuationAmount().getCurrency().getRealValue()+"')]]");
//            }
//            if (result.getValuationInformation().getCounterPartyValuationAmount()!=null){
//                if (result.getValuationInformation().getCounterPartyValuationAmount().getAmount()!=null)
//                    attributes.append("[td[contains(text(),'"+result.getValuationInformation().getCounterPartyValuationAmount().getAmount().getRealValue()+"')]]");
//                if (result.getValuationInformation().getPrincipalValuationAmount().getCurrency()!=null)
//                    attributes.append("[td[contains(text(),'"+result.getValuationInformation().getCounterPartyValuationAmount().getCurrency().getRealValue()+"')]]");
//            }
//            if (result.getValuationInformation().getIndependentValuationAmount()!=null){
//                if (result.getValuationInformation().getIndependentValuationAmount().getAmount()!=null)
//                    attributes.append("[td[contains(text(),'"+result.getValuationInformation().getIndependentValuationAmount().getAmount().getRealValue()+"')]]");
//                if (result.getValuationInformation().getPrincipalValuationAmount().getCurrency()!=null)
//                    attributes.append("[td[contains(text(),'"+result.getValuationInformation().getIndependentValuationAmount().getCurrency().getRealValue()+"')]]");
//            }
//        }
//        attributes.append("//img[contains(@src,'edit.gif')]");
        return attributes.toString();
    }

    public void editAgreementExposureAdjustment(AgreementExposureAdjustment agreementExposureAdjustment) throws Exception {
        if (agreementExposureAdjustment.getAgreementExposureAdjustment() != null) {
            if (agreementExposureAdjustment.getAgreementExposureAdjustment().getAmount() != null) {
                element("td.agreement.exposure.adjustment.amount").input(agreementExposureAdjustment.getAgreementExposureAdjustment().getAmount().getRealValue());
            }
            if (agreementExposureAdjustment.getAgreementExposureAdjustment().getCurrency() != null) {
                element("td.agreement.exposure.adjustment.currency").selectByVisibleText(agreementExposureAdjustment.getAgreementExposureAdjustment().getCurrency().getRealValue());
            }
        }
        if (agreementExposureAdjustment.getMaturityDate() != null) {
            element("td.maturity.date").input(agreementExposureAdjustment.getMaturityDate().getRealValue());
        }
        element("td.agreement.exposure.adjustment.save").click();
    }

    public void editAgreementIaValue(AgreementIaValue agreementIaValue) throws Exception {
        if (agreementIaValue.getReportingCcy() != null) {
            element("td.agreement.ia.reporting.ccy").selectByVisibleText(agreementIaValue.getReportingCcy().getRealValue());
        }
        element("td.agreement.ia.show").click();
        if (agreementIaValue.getPrincipalCalculatedAmt() != null) {
            element("td.agreement.ia.principal.calculated.amt").input(agreementIaValue.getPrincipalCalculatedAmt().getRealValue());
        }
        if (agreementIaValue.getCounterpartyCalculatedAmt() != null) {
            element("td.agreement.ia.counterparty.calculated.amt").input(agreementIaValue.getCounterpartyCalculatedAmt().getRealValue());
        }
        if (agreementIaValue.isOverrideSystemCalculatedIa() != null) {
            element("td.agreement.ia.override.system.calculated.ia").check(agreementIaValue.isOverrideSystemCalculatedIa());
        }
        if (agreementIaValue.getAgreedIa() != null) {
            element("td.agreement.ia").input(agreementIaValue.getAgreedIa().getRealValue());
        }
        if (agreementIaValue.isPrincipalCalculatedAmtApply() != null && agreementIaValue.isPrincipalCalculatedAmtApply()) {
            element("td.agreement.ia.principal.apply").click();
        }
        if (agreementIaValue.isCounterpartyCalculatedAmtApply() != null && agreementIaValue.isCounterpartyCalculatedAmtApply()) {
            element("td.agreement.ia.counterparty.apply").click();
        }
        element("td.agreement.ia.save").click();
    }

    public void assertTradeSummary(TradeSummary summary) throws Exception {
        if (element("td.trade.summary.viewSummary").isDisplayed()) {
            if (summary.getTradeDetailList() != null && summary.getTradeDetailList().size() > 0) {
                for (TradeDetail tradeDetail : summary.getTradeDetailList()) {
                    if (tradeDetail.getTradeInformation() != null) {
                        if (tradeDetail.getTradeInformation().getTradeIdentifierPartyA() != null) {
                            assertTradeDetailByType(tradeDetail.getTradeInformation().getTradeIdentifierPartyA().getRealValue(), tradeDetail.getTradeInformation());
                            if (tradeDetail.getValuationInformation() != null)
                                assertTradeDetailByType(tradeDetail.getTradeInformation().getTradeIdentifierPartyA().getRealValue(), tradeDetail.getValuationInformation());
                        }
                    }
                }
            }
        } else if (element("td.trade.summary.viewAllDetails").isDisplayed()) {
            assertTradeSummaryByType(summary);
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void assertTradeDetailByType(String ref, Object object) throws Exception {
        Method[] methods = object.getClass().getDeclaredMethods();
        for (Method method : methods) {
            StringBuffer attributes = new StringBuffer();
            if (method.getName().startsWith("get") && method.invoke(object) != null) {
                String functionName = Biz.getColumnName(method.getName().substring(3), "(?<!^)(?=[A-Z])");
                if (functionName.contains("Sell") || functionName.contains("Call"))
                    functionName += "?";
                if (functionName.contains("Or"))
                    functionName = functionName.replace("Or", "or");
                if (functionName.contains("Identifier2"))
                    functionName = functionName.replace("Identifier2", "Identifier 2");
                if (functionName.contains("Identifier") && functionName.contains("Party A"))
                    functionName += " ";
                String[] columnCells = functionName.trim().split(" ");
                for (String cell : columnCells) {
//                    attributes.append("[contains(text(),'" + cell + "')]");
                    attributes.append("[contains(text(),'").append(cell).append("')]");
                }
//                attributes.append("[string-length(text())=string-length('" + functionName + "')]");
                attributes.append("[string-length(text())=string-length('").append(functionName).append("')]");
                if (method.getReturnType() == StringType.class) {
                    StringType result = (StringType) method.invoke(object);
                    assertThat("td.trade.summary.ref.column", ref, attributes.toString()).innerText().isEqualToIgnoringWhitespace(result.getRealValue());
                } else if (method.getReturnType() == BuyOrSellType.class) {
                    BuyOrSellType result = (BuyOrSellType) method.invoke(object);
                    assertThat("td.trade.summary.ref.column", ref, attributes.toString()).innerText().isEqualToIgnoringWhitespace(result.value());
                } else if (method.getReturnType() == Money.class) {
                    Money result = (Money) method.invoke(object);
                    if (result.getAmount() != null)
                        assertThat("td.trade.summary.ref.column", ref, attributes.toString()).innerText().isEqualToIgnoringWhitespace(result.getAmount().getRealValue());
                    if (result.getCurrency() != null) {
                        String newAttribute = attributes.toString().replace("Amount", "Currency");
                        assertThat("td.trade.summary.ref.column", ref, newAttribute).innerText().isEqualToIgnoringWhitespace(result.getCurrency().getRealValue());
                    }
                }
            }
        }
    }

    public void assertTradeSummaryByType(TradeSummary summary) throws Exception {
        DecimalFormat format = new DecimalFormat();
        format.applyPattern("###,###,###,##0.00");
        if (summary.getTradeDetailList() != null && summary.getTradeDetailList().size() > 0) {
            for (TradeDetail detail : summary.getTradeDetailList()) {
                if (detail.getTradeInformation() != null) {
                    if (detail.getTradeInformation().getTradeIdentifierPartyA() != null) {
                        String ref = detail.getTradeInformation().getTradeIdentifierPartyA().getRealValue();
                        assertThat("td.trade.summary.row", ref).displayed().isTrue();
                        if (detail.getTradeInformation().getProductType() != null)
                            assertThat("td.trade.summary.product.type", ref).innerText().isEqualToIgnoringWhitespace(detail.getTradeInformation().getProductType().getRealValue());
//                        if (detail.getTradeInformation().getTradeIdentifierPartyA() != null)
//                            assertThat("", ref).innerText().isEqualToIgnoringCase(detail.getTradeInformation().getTradeIdentifierPartyA().getRealValue());
                        if (detail.getTradeInformation().getTradeIdentifierPartyB() != null)
                            assertThat("td.trade.summary.trade.identifier.party.b", ref).innerText().isEqualToIgnoringWhitespace(detail.getTradeInformation().getTradeIdentifierPartyB().getRealValue());
                        if (detail.getTradeInformation().getTradeDate() != null)
                            assertThat("td.trade.summary.trade.date", ref).innerText().isEqualToIgnoringWhitespace(detail.getTradeInformation().getTradeDate().getRealValue());
                        if (detail.getTradeInformation().getMaturityDate() != null)
                            assertThat("td.trade.summary.maturity.date", ref).innerText().isEqualToIgnoringWhitespace(detail.getTradeInformation().getMaturityDate().getRealValue());
                        if (detail.getTradeInformation().getExchangedNotional1Amount() != null)
                            assertThat("td.trade.summary.exchanged notional1.amount", ref).innerText().isEqualToIgnoringWhitespace(
                                    format.format(Double.parseDouble(detail.getTradeInformation().getExchangedNotional1Amount().getRealValue()))
                            );
                        if (detail.getTradeInformation().getExchangedNotional1Currency() != null)
                            assertThat("td.trade.summary.exchanged notional1.currency", ref).innerText().isEqualToIgnoringWhitespace(detail.getTradeInformation().getExchangedNotional1Currency().getRealValue());
                        if (detail.getValuationBaseCurrencyAmount() != null) {
                            if (detail.getValuationBaseCurrencyAmount().getCurrency() != null)
                                assertThat("td.trade.summary.valuetion.base.currency").innerText().isEqualToIgnoringWhitespace("Valuation Base Currency Amount (" + detail.getValuationBaseCurrencyAmount().getCurrency().getRealValue() + ")");
                            if (detail.getValuationBaseCurrencyAmount().getAmount() != null)
                                assertThat("td.trade.summary.valuetion.base.currency.amount", ref).innerText().isEqualToIgnoringWhitespace(
                                        format.format(Double.parseDouble(detail.getValuationBaseCurrencyAmount().getAmount().getRealValue()))
                                );
                        }
                        if (detail.getValuationInformation() != null) {
                            if (detail.getValuationInformation().getValuationNativeCurrencyAmount() != null) {
                                if (detail.getValuationInformation().getValuationNativeCurrencyAmount().getAmount() != null)
                                    assertThat("td.trade.summary.valuation.native.amount", ref).innerText().isEqualToIgnoringWhitespace(
                                            format.format(Double.parseDouble(detail.getValuationInformation().getValuationNativeCurrencyAmount().getAmount().getRealValue()))
                                );
                                if (detail.getValuationInformation().getValuationNativeCurrencyAmount().getCurrency() != null)
                                    assertThat("td.trade.summary.valuation.native.currency", ref).innerText().isEqualToIgnoringWhitespace(detail.getValuationInformation().getValuationNativeCurrencyAmount().getCurrency().getRealValue());
                            }
                            if (detail.getValuationInformation().getValutionAmountType() != null) {
                                String actualValue = detail.getValuationInformation().getValutionAmountType().value().substring(0, 1).toUpperCase();
                                assertThat("td.trade.summary.valuation.method.applied", ref).innerText().isEqualToIgnoringWhitespace(actualValue);
                            }
                        }
                        if (detail.getTradeInformation().getStartDate() != null)
                            assertThat("td.trade.summary.start.date", ref).innerText().isEqualToIgnoringWhitespace(detail.getTradeInformation().getStartDate().getRealValue());
                        if (detail.getTradeInformation().getEndDate() != null)
                            assertThat("td.trade.summary.end.date", ref).innerText().isEqualToIgnoringWhitespace(detail.getTradeInformation().getEndDate().getRealValue());
                        if (detail.getSecurityInformation() != null) {
                            if (detail.getSecurityInformation().getInstrumentId() != null && detail.getSecurityInformation().getInstrumentId().getLinkText() != null)
                                assertThat("td.trade.summary.instrument.id", ref).innerText().isEqualToIgnoringWhitespace(detail.getSecurityInformation().getInstrumentId().getLinkText().getRealValue());
                            if (detail.getSecurityInformation().getNotional() != null)
                                assertThat("td.trade.summary.notional", ref).innerText().isEqualToIgnoringWhitespace(
                                        format.format(Double.parseDouble(detail.getSecurityInformation().getNotional().getRealValue()))
                                );
                            if (detail.getSecurityInformation().getCleanPrice() != null)
                                assertThat("td.trade.summary.clean.price", ref).innerText().isEqualToIgnoringWhitespace(
                                        format.format(Double.parseDouble(detail.getSecurityInformation().getCleanPrice().getRealValue()))
                                );
                            if (detail.getSecurityInformation().getAllInPrice() != null)
                                assertThat("td.trade.summary.all.in.price", ref).innerText().isEqualToIgnoringWhitespace(
                                        format.format(Double.parseDouble(detail.getSecurityInformation().getAllInPrice().getRealValue()))
                                );
                            if (detail.getSecurityInformation().getNetValueSecurityCurrency() != null) {
                                if (detail.getSecurityInformation().getNetValueSecurityCurrency().getCurrency() != null)
                                    assertThat("td.trade.summary.security.leg.net.value.currency").innerText().isEqualToIgnoringWhitespace("Security Leg\nNet Value\n(" + detail.getSecurityInformation().getNetValueSecurityCurrency().getCurrency().getRealValue() + ")");
                                if (detail.getSecurityInformation().getNetValueSecurityCurrency().getAmount() != null)
                                    assertThat("td.trade.summary.security.leg.net.value", ref).innerText().isEqualToIgnoringWhitespace(
                                            format.format(Double.parseDouble(detail.getSecurityInformation().getNetValueSecurityCurrency().getAmount().getRealValue()))
                                    );
                            }
                        }
                        if (detail.getCashInformation() != null) {
                            if (detail.getCashInformation().getCashAssetType() != null && detail.getCashInformation().getCashAssetType().getLinkText() != null)
                                assertThat("td.trade.summary.cash.asset.type", ref).innerText().isEqualToIgnoringWhitespace(detail.getCashInformation().getCashAssetType().getLinkText().getRealValue());
                            if (detail.getCashInformation().getOpenCash() != null)
                                assertThat("td.trade.summary.open.cash", ref).innerText().isEqualToIgnoringWhitespace(
                                        format.format(Double.parseDouble(detail.getCashInformation().getOpenCash().getRealValue()))
                                );
                            if (detail.getCashInformation().getRepoRate() != null)
                                assertThat("td.trade.summary.repo.rate", ref).innerText().isEqualToIgnoringWhitespace(
                                        format.format(Double.parseDouble(detail.getCashInformation().getRepoRate().getRealValue()))
                                );
                            if (detail.getCashInformation().getNetValueCashCurrency() != null) {
                                if (detail.getCashInformation().getNetValueCashCurrency().getCurrency() != null)
                                    assertThat("td.trade.summary.cash.leg.net.value.currency").innerText().isEqualToIgnoringWhitespace("Cash Leg\nNet Value\n(" + detail.getCashInformation().getNetValueCashCurrency().getCurrency().getRealValue() + ")");
                                if (detail.getCashInformation().getNetValueCashCurrency().getAmount() != null)
                                    assertThat("td.trade.summary.cash.leg.net.value", ref).innerText().isEqualToIgnoringWhitespace(
                                            format.format(Double.parseDouble(detail.getCashInformation().getNetValueCashCurrency().getAmount().getRealValue()))
                                    );
                            }
                        }
                        if (detail.getExposureInformation() != null) {
                            if (detail.getExposureInformation().isOverridden())
                                assertThat("td.trade.summary.manual.Overridden", ref).innerText().isEqualToIgnoringWhitespace("Yes");
                            else
                                assertThat("td.trade.summary.manual.Overridden", ref).innerText().isEqualToIgnoringWhitespace("No");
                        }
                    }
                }
            }
        }
    }

}
