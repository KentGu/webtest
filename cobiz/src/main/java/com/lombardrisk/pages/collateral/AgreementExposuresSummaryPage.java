package com.lombardrisk.pages.collateral;

import com.lombardrisk.pojo.AgreementExposureSummary;
import com.lombardrisk.pojo.IaByPortfolioIaType;
import com.lombardrisk.pojo.ProductList;
import com.lombardrisk.pojo.TotalAmount;
import com.lombardrisk.util.Biz;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import java.lang.reflect.Method;
import java.text.DecimalFormat;

/**
 * Created by Kenny Wang on 2/3/2016.
 */
public class AgreementExposuresSummaryPage extends PageBase {
    public AgreementExposuresSummaryPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    /**
     * view statement
     */
    public void viewStatement() throws Exception {
        element("td.view.statement").click();
    }

    public void enterTradeSummary(AgreementExposureSummary trade) throws Exception {
        if (trade.getProductList() != null && !trade.getProductList().isEmpty() && trade.getProductList().get(0).getProductType() != null)
            element("td.product", trade.getProductList().get(0).getProductType().getRealValue()).clickByJavaScript();
    }
    
    public void enterAgreementStatementPage() throws Exception{
    	element("").click();
    }

    public void assertAgreementExposureSummary(AgreementExposureSummary summary) throws Exception {
        DecimalFormat format = new DecimalFormat();
        format.applyPattern("###,###,###,##0.00");
        if (summary.getProductList() != null && summary.getProductList().size() > 0)
            for (ProductList list : summary.getProductList())
                if (list.getProductType() != null) {
                    assertThat("td.exposure.summary.product.type.column", "/a", "contains(text(),'"+list.getProductType().getRealValue()+"')").displayed().isTrue();
                    assertProductList(list, "/a", "contains(text(),'"+list.getProductType().getRealValue()+"')");
                }
        if (summary.getAgreementIa() != null)
            assertProductList(summary.getAgreementIa(), "", "contains(text(),'Agreement IA')");
        if (summary.getPortfolioTotals() != null)
            assertProductList(summary.getPortfolioTotals(), "/b", "contains(text(),'Portfolio Totals')");
        if (summary.getNetDealLevelIa()!=null)
            assertProductList(summary.getNetDealLevelIa(),"/b","contains(text(),'Net') and contains(text(),'Deal') and contains(text(),'Level') and contains(text(),'IA') and string-length(text())=string-length('Net Deal Level IA')");

        if (summary.getIaByPortfolioIaType()!=null && summary.getIaByPortfolioIaType().size() > 0){
            for (IaByPortfolioIaType iaByPortfolioIaType : summary.getIaByPortfolioIaType()){
                assertIaByPortfolioIaType(iaByPortfolioIaType);
            }
        }
        if (summary.getNetPortfolioIa()!=null){
            assertNetPortfolioIa(summary);
        }

        Method[] methods=summary.getClass().getDeclaredMethods();
        for (Method method : methods){
            if (method.getName().startsWith("get") && method.invoke(summary)!=null && method.getReturnType()== TotalAmount.class) {
                TotalAmount result = (TotalAmount) method.invoke(summary);
                String functionName = Biz.getColumnName(method.getName().substring(3), "(?<!^)(?=[A-Z])");
                if (functionName.contains("Lockup"))
                    functionName = functionName.replace("Lockup", "Lock-up");
                if (functionName.contains("T S A")){
//                    functionName = functionName.replace("T S A", "(TSA)");
                    functionName = "Cashflow";
                }
                if (result.getCurrency() != null)
                    assertThat("td.exposure.summary.total.amount.ccy", functionName).innerText().isEqualToIgnoringWhitespace(functionName + " (" + result.getCurrency().getRealValue() + ")");
                if (result.getAmount() != null)
                    assertThat("td.exposure.summary.total.amount", functionName).innerText().isEqualToIgnoringWhitespace(
                            format.format(Float.parseFloat(result.getAmount().getRealValue()))
                    );

            }
        }
    }

    public void assertIaByPortfolioIaType(IaByPortfolioIaType iaByPortfolioIaType) throws Exception{
        DecimalFormat format = new DecimalFormat();
        format.applyPattern("###,###,###,##0.00");

        if ((iaByPortfolioIaType.getVariation() != null && iaByPortfolioIaType.getVariation().equalsIgnoreCase("hasCategory"))
            && (iaByPortfolioIaType.getName() != null)
            && (iaByPortfolioIaType.getIaCategory() != null)){
            assertThat("td.exposure.summary.principal.ia.system",iaByPortfolioIaType.getIaCategory().getRealValue()).innerText().isEqualToIgnoringCase(iaByPortfolioIaType.getName());
        }

        if (iaByPortfolioIaType.getPrincipalIaAmount() != null){
            if (iaByPortfolioIaType.getIaCategory() != null){
                try {
                    validateThat("td.exposure.summary.principal.ia.amount.categoryLevel",iaByPortfolioIaType.getIaCategory().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                            format.format(Double.parseDouble(iaByPortfolioIaType.getPrincipalIaAmount().getRealValue())));
                } catch (NumberFormatException nfe) {
                    validateThat("td.exposure.summary.principal.ia.amount.categoryLevel",iaByPortfolioIaType.getIaCategory().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                            iaByPortfolioIaType.getPrincipalIaAmount().getRealValue());
                }
            }
            if (iaByPortfolioIaType.getIaSystem() != null){
                if (iaByPortfolioIaType.getVariation() != null && iaByPortfolioIaType.getVariation().equalsIgnoreCase("hasCategory")){
                    try {
                        validateThat("td.exposure.summary.principal.ia.amount.systemLevel.withCategory",iaByPortfolioIaType.getIaSystem().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                                format.format(Double.parseDouble(iaByPortfolioIaType.getPrincipalIaAmount().getRealValue())));
                    } catch (NumberFormatException nfe) {
                        validateThat("td.exposure.summary.principal.ia.amount.systemLevel.withCategory",iaByPortfolioIaType.getIaSystem().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                                iaByPortfolioIaType.getPrincipalIaAmount().getRealValue());
                    }
                }else {
                    try {
                        validateThat("td.exposure.summary.principal.ia.amount.systemLevel.withoutCategory",iaByPortfolioIaType.getIaSystem().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                                format.format(Double.parseDouble(iaByPortfolioIaType.getPrincipalIaAmount().getRealValue())));
                    } catch (NumberFormatException nfe) {
                        validateThat("td.exposure.summary.principal.ia.amount.systemLevel.withoutCategory",iaByPortfolioIaType.getIaSystem().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                                iaByPortfolioIaType.getPrincipalIaAmount().getRealValue());
                    }
                }
            }
        }

        if (iaByPortfolioIaType.getCounterpartyIaAmount() != null){
            if (iaByPortfolioIaType.getIaCategory() != null){
                try {
                    validateThat("td.exposure.summary.counterparty.ia.amount.categoryLevel",iaByPortfolioIaType.getIaCategory().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                            format.format(Double.parseDouble(iaByPortfolioIaType.getCounterpartyIaAmount().getRealValue())));
                } catch (NumberFormatException nfe) {
                    validateThat("td.exposure.summary.counterparty.ia.amount.categoryLevel",iaByPortfolioIaType.getIaCategory().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                            iaByPortfolioIaType.getCounterpartyIaAmount().getRealValue());
                }
            }
            if (iaByPortfolioIaType.getIaSystem() != null){
                if (iaByPortfolioIaType.getVariation() != null && iaByPortfolioIaType.getVariation().equalsIgnoreCase("hasCategory")){
                    try {
                        validateThat("td.exposure.summary.counterparty.ia.amount.systemLevel.withCategory",iaByPortfolioIaType.getIaSystem().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                                format.format(Double.parseDouble(iaByPortfolioIaType.getCounterpartyIaAmount().getRealValue())));
                    } catch (NumberFormatException nfe) {
                        validateThat("td.exposure.summary.counterparty.ia.amount.systemLevel.withCategory",iaByPortfolioIaType.getIaSystem().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                                iaByPortfolioIaType.getCounterpartyIaAmount().getRealValue());
                    }
                }else {
                    try {
                        validateThat("td.exposure.summary.counterparty.ia.amount.systemLevel.withoutCategory",iaByPortfolioIaType.getIaSystem().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                                format.format(Double.parseDouble(iaByPortfolioIaType.getCounterpartyIaAmount().getRealValue())));
                    } catch (NumberFormatException nfe) {
                        validateThat("td.exposure.summary.counterparty.ia.amount.systemLevel.withoutCategory",iaByPortfolioIaType.getIaSystem().getRealValue()).innerText().isEqualToIgnoringWhitespace(
                                iaByPortfolioIaType.getCounterpartyIaAmount().getRealValue());
                    }
                }
            }
        }
    }

    public void assertNetPortfolioIa(AgreementExposureSummary summary) throws Exception{
        DecimalFormat format = new DecimalFormat();
        format.applyPattern("###,###,###,##0.00");

        if (summary.getNetPortfolioIa().getPrincipalIaAmount() != null){
                try {
                    validateThat("td.exposure.summary.NetPortfolioIa.principalIa").innerText().isEqualToIgnoringWhitespace(
                            format.format(Double.parseDouble(summary.getNetPortfolioIa().getPrincipalIaAmount().getRealValue())));
                } catch (NumberFormatException nfe) {
                    validateThat("td.exposure.summary.NetPortfolioIa.principalIa").innerText().isEqualToIgnoringWhitespace(
                            summary.getNetPortfolioIa().getPrincipalIaAmount().getRealValue());
                }
         }

        if (summary.getNetPortfolioIa().getCounterpartyIaAmount() != null){
            try {
                validateThat("td.exposure.summary.NetPortfolioIa.counterpartyIa").innerText().isEqualToIgnoringWhitespace(
                        format.format(Double.parseDouble(summary.getNetPortfolioIa().getCounterpartyIaAmount().getRealValue())));
            } catch (NumberFormatException nfe) {
                validateThat("td.exposure.summary.NetPortfolioIa.counterpartyIa").innerText().isEqualToIgnoringWhitespace(
                        summary.getNetPortfolioIa().getCounterpartyIaAmount().getRealValue());
            }
        }
    }

    public void assertProductList(ProductList list, String sub1, String sub2) throws Exception{
        DecimalFormat format = new DecimalFormat();
        format.applyPattern("###,###,###,##0.00");

        if (list.getPrincipalIa() != null)
            try {
                assertThat("td.exposure.summary.principal.ia.column",sub1, sub2).innerText().isEqualTo(
                        format.format(Float.parseFloat(list.getPrincipalIa().getRealValue())));
            } catch (NumberFormatException nfe){
                assertThat("td.exposure.summary.principal.ia.column",sub1, sub2).innerText().isEqualTo(
                        list.getPrincipalIa().getRealValue());
            }
        if (list.getCounterpartyIa() != null)
            try {
                assertThat("td.exposure.summary.counterparty.ia.column",sub1, sub2).innerText().isEqualTo(
                        format.format(Float.parseFloat(list.getCounterpartyIa().getRealValue())));
            } catch (NumberFormatException nfe){
                assertThat("td.exposure.summary.counterparty.ia.column",sub1, sub2).innerText().isEqualTo(
                        list.getCounterpartyIa().getRealValue());

            }
            assertThat("td.exposure.summary.counterparty.ia.column",sub1, sub2).innerText().isEqualTo(
                    format.format(Float.parseFloat(list.getCounterpartyIa().getRealValue()))
            );
        if (list.getPrincipalLm() != null)
            assertThat("td.exposure.summary.principal.lm.column",sub1, sub2).innerText().isEqualTo(
                    format.format(Float.parseFloat(list.getPrincipalLm().getRealValue()))
            );
        if (list.getCounterpartyLm() != null)
            assertThat("td.exposure.summary.counterparty.lm.column",sub1, sub2).innerText().isEqualTo(
                    format.format(Float.parseFloat(list.getCounterpartyLm().getRealValue()))
            );
        if (list.getAlternativeAmounts() != null)
            assertThat("td.exposure.summary.alternative.amounts.column",sub1, sub2).innerText().isEqualTo(
                    format.format(Float.parseFloat(list.getAlternativeAmounts().getRealValue()))
            );
        if (list.getNettedExposureAmounts() != null)
            assertThat("td.exposure.summary.netted.exposure.amounts.column",sub1, sub2).innerText().isEqualTo(
                    format.format(Float.parseFloat(list.getNettedExposureAmounts().getRealValue()))
            );
        if (list.getCollateralAmounts() != null)
            assertThat("td.exposure.summary.collateral.amounts.column",sub1, sub2).innerText().isEqualTo(
                    format.format(Float.parseFloat(list.getCollateralAmounts().getRealValue()))
            );
        if (list.getSysCalcSecurityLeg()!=null)
            assertThat("td.exposure.summary.sys.calc.security.leg.column",sub1,sub2).innerText().isEqualTo(
                    format.format(Float.parseFloat(list.getSysCalcSecurityLeg().getRealValue()))
            );
        if (list.getSysCalcCashLeg()!=null)
            assertThat("td.exposure.summary.sys.calc.cash.leg.column",sub1,sub2).innerText().isEqualTo(
                    format.format(Float.parseFloat(list.getSysCalcCashLeg().getRealValue()))
            );
        if (list.getNonSysCalcValue()!=null)
            assertThat("td.exposure.summary.non.sys.calc.value.column",sub1,sub2).innerText().isEqualTo(
                    format.format(Float.parseFloat(list.getNonSysCalcValue().getRealValue()))
            );
        if (list.getCashComponent()!=null)
            assertThat("td.exposure.summary.cash.component.column",sub1,sub2).innerText().isEqualTo(list.getCashComponent().getRealValue());
        if (list.getFee()!=null)
            assertThat("td.exposure.summary.fee.column",sub1,sub2).innerText().isEqualTo(
                    format.format(Float.parseFloat(list.getFee().getRealValue()))
            );
    }
}