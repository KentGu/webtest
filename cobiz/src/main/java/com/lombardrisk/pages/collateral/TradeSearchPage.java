package com.lombardrisk.pages.collateral;

import com.lombardrisk.pojo.*;
import com.lombardrisk.util.Biz;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import java.lang.reflect.Method;

/**
 * Created by mengli huang on 2/08/2016.
 */
public class TradeSearchPage extends PageBase {
    public TradeSearchPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }

    public void searchTrade(TradeSearch tradeSearch) throws Exception{
        setTradeSearchCondition(tradeSearch);
        clickSearchButton();
    }

    public void setTradeSearchCondition(TradeSearch tradeSearch) throws Exception{
        if (tradeSearch.getProductType()!=null)
            element("ts.product.type").selectByVisibleText(tradeSearch.getProductType().getRealValue());
        if (tradeSearch.getValuationInformation()!=null)
            element("ts.valuation.information").selectByVisibleText(tradeSearch.getValuationInformation().value());
        if (tradeSearch.getExchangeNotional1From()!=null)
            element("ts.exchange.notional1.from").input(tradeSearch.getExchangeNotional1From().getRealValue());
        if (tradeSearch.getExchangeNotional1To()!=null)
            element("ts.exchange.notional1.to").input(tradeSearch.getExchangeNotional1To().getRealValue());
        if (tradeSearch.getExchangeNotional1Ccy()!=null)
            element("ts.exchange.notional1.ccy").selectByVisibleText(tradeSearch.getExchangeNotional1Ccy().getRealValue());
        if (tradeSearch.getExchangeNotional2From()!=null)
            element("ts.exchange.notional2.from").input(tradeSearch.getExchangeNotional2From().getRealValue());
        if (tradeSearch.getExchangeNotional2To()!=null)
            element("ts.exchange.notional2.to").input(tradeSearch.getExchangeNotional2To().getRealValue());
        if (tradeSearch.getExchangeNotional2Ccy()!=null)
            element("ts.exchange.notional2.ccy").selectByVisibleText(tradeSearch.getExchangeNotional2Ccy().getRealValue());
        if (tradeSearch.getSecurityNotionalFrom()!=null)
            element("ts.security.notional.from").input(tradeSearch.getSecurityNotionalFrom().getRealValue());
        if (tradeSearch.getSecurityNotionalTo()!=null)
            element("ts.security.notional.to").input(tradeSearch.getSecurityNotionalTo().getRealValue());
        if (tradeSearch.getSecurityNotionalCcy()!=null)
            element("ts.security.notional.ccy").selectByVisibleText(tradeSearch.getSecurityNotionalCcy().getRealValue());
        if (tradeSearch.getOpenCashFrom()!=null)
            element("ts.open.cash.from").input(tradeSearch.getOpenCashFrom().getRealValue());
        if (tradeSearch.getOpenCashTo()!=null)
            element("ts.open.cash.to").input(tradeSearch.getOpenCashTo().getRealValue());
        if (tradeSearch.getCashCcy()!=null)
            element("ts.open.cash.ccy").selectByVisibleText(tradeSearch.getCashCcy().getRealValue());
        if (tradeSearch.getFeeFrom()!=null)
            element("ts.fee.from").input(tradeSearch.getFeeFrom().getRealValue());
        if (tradeSearch.getFeeTo()!=null)
            element("ts.fee.to").input(tradeSearch.getFeeTo().getRealValue());
        if (tradeSearch.getFeeCcy()!=null)
            element("ts.fee.ccy").selectByVisibleText(tradeSearch.getFeeCcy().getRealValue());
        if (tradeSearch.getCategory()!=null && tradeSearch.getCategory().size()>0)
            for (ProductCategoryType category : tradeSearch.getCategory())
                element("ts.category").selectByVisibleText(category.value());
        if (tradeSearch.getTradeIdentifierPartyA()!=null) {
            if (tradeSearch.getTradeIdentifierPartyA().getType() != null)
                element("ts.trade.identifier.partyA.type").selectByVisibleText(tradeSearch.getTradeIdentifierPartyA().getType().value());
            if (tradeSearch.getTradeIdentifierPartyA().getFilter()!=null)
                element("ts.trade.identifier.partyA").input(tradeSearch.getTradeIdentifierPartyA().getFilter().getRealValue());
        }
        if (tradeSearch.getTradeIdentifier2PartyA()!=null){
            if (tradeSearch.getTradeIdentifier2PartyA().getType()!=null)
                element("ts.trade.identifier2.partyA.type").selectByVisibleText(tradeSearch.getTradeIdentifier2PartyA().getType().value());
            if (tradeSearch.getTradeIdentifier2PartyA().getFilter()!=null)
                element("ts.trade.identifier2.partyA").input(tradeSearch.getTradeIdentifier2PartyA().getFilter().getRealValue());
        }
        if (tradeSearch.getTradeIdentifierPartyB()!=null){
            if (tradeSearch.getTradeIdentifierPartyB().getType()!=null)
                element("ts.trade.identifier.partyB.type").selectByVisibleText(tradeSearch.getTradeIdentifierPartyB().getType().value());
            if (tradeSearch.getTradeIdentifierPartyB().getFilter()!=null)
                element("ts.trade.identifier.partyB").input(tradeSearch.getTradeIdentifierPartyB().getFilter().getRealValue());
        }
        if (tradeSearch.getTradeDateFrom()!=null)
            element("ts.trade.date.from").input(tradeSearch.getTradeDateFrom().getRealValue());
        if (tradeSearch.getTradeDateTo()!=null)
            element("ts.trade.date.to").input(tradeSearch.getTradeDateTo().getRealValue());
        if (tradeSearch.getEffectiveDateFrom()!=null)
            element("ts.effective.date.from").input(tradeSearch.getEffectiveDateFrom().getRealValue());
        if (tradeSearch.getEffectiveDateTo()!=null)
            element("ts.effective.date.to").input(tradeSearch.getEffectiveDateTo().getRealValue());
        if (tradeSearch.getMaturityDateFrom()!=null)
            element("ts.maturity.date.from").input(tradeSearch.getMaturityDateFrom().getRealValue());
        if (tradeSearch.getMaturityDateTo()!=null)
            element("ts.maturity.date.to").input(tradeSearch.getMaturityDateTo().getRealValue());
        if (tradeSearch.getStartDateFrom()!=null)
            element("ts.start.date.from").input(tradeSearch.getStartDateFrom().getRealValue());
        if (tradeSearch.getStartDateTo()!=null)
            element("ts.start.date.to").input(tradeSearch.getStartDateTo().getRealValue());
        if (tradeSearch.getEndDateFrom()!=null)
            element("ts.end.date.from").input(tradeSearch.getEndDateFrom().getRealValue());
        if (tradeSearch.getEndDateTo()!=null)
            element("ts.end.date.to").input(tradeSearch.getEndDateTo().getRealValue());
        if (tradeSearch.getSourceSystem()!=null)
            element("ts.source.system").selectByVisibleText(tradeSearch.getSourceSystem().getRealValue());
        if (tradeSearch.getValuationQualityFrom()!=null)
            element("ts.valuation.quality.from").input(tradeSearch.getValuationQualityFrom().getRealValue());
        if (tradeSearch.getValuationQualityTo()!=null)
            element("ts.valuation.quality.to").input(tradeSearch.getValuationQualityTo().getRealValue());
        if (tradeSearch.getAdvancedSearch() != null){
            element("ah.advance").click();
            setAdvancedSearch(tradeSearch);
        }
    }

    public void setAdvancedSearch(TradeSearch search) throws Exception{
        if (search.getAdvancedSearch().getCcp() != null && search.getAdvancedSearch().getCcp().size() > 0){
            for (int i = 0; i < search.getAdvancedSearch().getCcp().size(); i++) {
                if (search.getAdvancedSearch().getCcp().get(i).getFilter() != null){
                    element("am.agreementSearch.ccpFilter").input(search.getAdvancedSearch().getCcp().get(i).getFilter().getRealValue());
                }
                if (search.getAdvancedSearch().getCcp().get(i).getLinkText() != null){
                    element("am.agreementSearch.ccpFilter",search.getAdvancedSearch().getCcp().get(i).getLinkText().getRealValue()).click();
                }
            }
        }
        if (search.getAdvancedSearch().getLinkageSet() != null && search.getAdvancedSearch().getLinkageSet().size() > 0){
            for (int i = 0; i < search.getAdvancedSearch().getLinkageSet().size(); i++) {
                if (search.getAdvancedSearch().getLinkageSet().get(i).getFilter() != null){
                    element("am.agreementSearch.linkageSet.filter").input(search.getAdvancedSearch().getLinkageSet().get(i).getFilter().getRealValue());
                }
                if (search.getAdvancedSearch().getLinkageSet().get(i).getLinkText() != null){
                    element("am.agreementSearch.linkageSet.filter",search.getAdvancedSearch().getLinkageSet().get(i).getLinkText().getRealValue()).click();
                }
            }
        }
        if (search.getAdvancedSearch().getStatementSet() != null && search.getAdvancedSearch().getStatementSet().size() > 0){
            for (int i = 0; i < search.getAdvancedSearch().getStatementSet().size(); i++) {
                if (search.getAdvancedSearch().getStatementSet().get(i).getFilter() != null){
                    element("am.agreementSearch.statementSetFilter").input(search.getAdvancedSearch().getStatementSet().get(i).getFilter().getRealValue());
                }
                if (search.getAdvancedSearch().getStatementSet().get(i).getLinkText() != null){
                    element("am.agreementSearch.statementSetFilter",search.getAdvancedSearch().getStatementSet().get(i).getLinkText().getRealValue()).click();
                }
            }
        }
        if (search.getAdvancedSearch().getBusinessLine() != null && search.getAdvancedSearch().getBusinessLine().size() > 0){
            for (int i = 0; i < search.getAdvancedSearch().getBusinessLine().size(); i++) {
                element("am.agreementSearch.businessLine").selectByVisibleText(search.getAdvancedSearch().getBusinessLine().get(i).getRealValue());
            }
        }
    }

    public void clickSearchButton() throws Exception{
        element("ts.search").click();
    }

    public void assertTradeSearchResultDetailByInvoke(TradeSearchResult detail) throws Exception{
        StringBuffer attributes = new StringBuffer();
        Method[] methods = detail.getClass().getDeclaredMethods();
        for (Method method : methods){
            if (method.getName().startsWith("get") && method.invoke(detail)!=null && method.getReturnType()==StringType.class) {
                String functionName = Biz.getColumnName(method.getName().substring(3),"(?<!^)(?=[A-Z,0-9])");
                StringType value = (StringType) method.invoke(detail);
                if (!method.getName().equals("getProductType")) {
                    attributes.append("[td[count(//th[normalize-space(a)='").append(functionName).append("']/preceding-sibling::th)+1]='").append(value.getRealValue()).append("']");
                } else
                    attributes.append("[td[count(//th[normalize-space(a)='").append(functionName).append("']/preceding-sibling::th)+1][contains(text(),'").append(value.getRealValue()).append("')][string-length(text())=string-length('").append(value.getRealValue()).append("')+1]]");
            }
        }
        assertThat("ts.result.row.match",attributes.toString()).displayed().isTrue();
    }

    @Deprecated
    public void assertTradeSearchResultDetail(TradeSearchResult tradeSearchResultDetail) throws Exception{
        StringBuffer attributes = new StringBuffer();
        if (tradeSearchResultDetail.getPartyABranchName()!=null)
            attributes.append("[td[count(//th[normalize-space(a)='Party A Branch Name']/preceding-sibling::th)+1]='").append(tradeSearchResultDetail.getPartyABranchName().getRealValue()).append("']");
        if (tradeSearchResultDetail.getPartyBBranchName()!=null)
            attributes.append("[td[count(//th[normalize-space(a)='Party B Branch Name']/preceding-sibling::th)+1]='").append(tradeSearchResultDetail.getPartyBBranchName().getRealValue()).append("']");
        if (tradeSearchResultDetail.getDescription()!=null)
            attributes.append("[td[count(//th[normalize-space(a)='Description']/preceding-sibling::th)+1]='").append(tradeSearchResultDetail.getDescription().getRealValue()).append("']");
        if (tradeSearchResultDetail.getAgreementId()!=null)
            attributes.append("[td[count(//th[normalize-space(a)='Agreement Id']/preceding-sibling::th)+1]='").append(tradeSearchResultDetail.getAgreementId().getRealValue()).append("']");
        if (tradeSearchResultDetail.getProductType()!=null)
            attributes.append("[td[count(//th[normalize-space(a)='Product Type']/preceding-sibling::th)+1][contains(text(),'").append(tradeSearchResultDetail.getProductType().getRealValue()).append("')][string-length(text())=string-length('").append(tradeSearchResultDetail.getProductType().getRealValue()).append("')+1]]");
        if (tradeSearchResultDetail.getProductCategory()!=null)
            attributes.append("[td[count(//th[normalize-space(a)='Product Category']/preceding-sibling::th)+1]='").append(tradeSearchResultDetail.getProductCategory().getRealValue()).append("']");
        if (tradeSearchResultDetail.getTradeIdentifierPartyA()!=null)
            attributes.append("[td[count(//th[normalize-space(a)='Trade Identifier Party A']/preceding-sibling::th)+1]='").append(tradeSearchResultDetail.getTradeIdentifierPartyA().getRealValue()).append("']");
        if (tradeSearchResultDetail.getSourceSystem()!=null)
            attributes.append("[td[count(//th[normalize-space(a)='Source System']/preceding-sibling::th)+1]='").append(tradeSearchResultDetail.getSourceSystem().getRealValue()).append("']");
        if (tradeSearchResultDetail.getTradeIdentifierPartyB()!=null)
            attributes.append("[td[count(//th[normalize-space(a)='Trade Identifier Party B']/preceding-sibling::th)+1]='").append(tradeSearchResultDetail.getTradeIdentifierPartyB().getRealValue()).append("']");
        if (tradeSearchResultDetail.getBusinessLine()!=null)
            attributes.append("[td[count(//th[normalize-space(a)='Bussiness']/preceding-sibling::th)+1]='").append(tradeSearchResultDetail.getBusinessLine().getRealValue()).append("']");
        if (tradeSearchResultDetail.getTradeIdentifier2PartyA()!=null)
            attributes.append("[td[count(//th[normalize-space(a)='Trade Identifier 2 Party A']/preceding-sibling::th)+1]='").append(tradeSearchResultDetail.getTradeIdentifier2PartyA().getRealValue()).append("']");

        assertThat("ts.result.row.match",attributes.toString()).displayed().isTrue();
    }

    public void checkAllResult(TradeSearch tradeSearch) throws Exception{
        if (element("ts.result.row").isDisplayed()) {
            checkAllResultInCurrenetPage(tradeSearch);
            while (element("ts.next").isDisplayed()) {
                element("ts.next").click();
                checkAllResultInCurrenetPage(tradeSearch);
            }
        }
    }

    public void checkAllResultInCurrenetPage(TradeSearch tradeSearch) throws Exception{
        if (tradeSearch.getProductType() != null) {
            assertThat("ts.column.product.type").allInnerTexts().containsOnly(tradeSearch.getProductType().getRealValue());
        }
        if (tradeSearch.getCategory()!=null && tradeSearch.getCategory().size()>0){
            int expectedRowNum = 0;
            for (ProductCategoryType cate : tradeSearch.getCategory()) {
                if (element("ts.column.category", cate.value()).isDisplayed())
                    expectedRowNum += element("ts.column.category", cate.value()).getAllMatchedElements().size();
            }
            int actualRowNum = element("ts.result.row").getAllMatchedElements().size();
            org.assertj.core.api.Assertions.assertThat(actualRowNum).as("the product category do not conform the expected value").isEqualTo(expectedRowNum);
        }
        if (tradeSearch.getSourceSystem()!=null){
            assertThat("ts.column.source.system").allInnerTexts().containsOnly(tradeSearch.getSourceSystem().getRealValue());
        }
        if (tradeSearch.getAdvancedSearch()!=null && tradeSearch.getAdvancedSearch().getBusinessLine()!=null &&tradeSearch.getAdvancedSearch().getBusinessLine().size()>0) {
            int expectedRowNum = 0;
            for (StringType business : tradeSearch.getAdvancedSearch().getBusinessLine()) {
                if (element("ts.column.business.line", business.getRealValue()).isDisplayed())
                    expectedRowNum += element("ts.column.business.line", business.getRealValue()).getAllMatchedElements().size();
            }
            int actualRowNum = element("ts.result.row").getAllMatchedElements().size();
            org.assertj.core.api.Assertions.assertThat(actualRowNum).as("the business line do not conform the expected value").isEqualTo(expectedRowNum);
        }
        if (tradeSearch.getTradeIdentifierPartyA()!=null && tradeSearch.getTradeIdentifierPartyA().getFilter()!=null){
            if (tradeSearch.getTradeIdentifierPartyA().getType()!=null && !tradeSearch.getTradeIdentifierPartyA().getType().value().equals("Starting With")){
                if (tradeSearch.getTradeIdentifierPartyA().getType().value().equals("Containing")){
//                    for (int i=1; i<=element("ts.result.row").getAllMatchedElements().size(); i++){
//                        assertThat("ts.column.trade.identifier.party.a.row",Integer.toString(i)).innerText().contains(tradeSearch.getTradeIdentifierPartyA().getFilter().getRealValue());
//                    }
                    assertThat("ts.column.trade.identifier.party.a.notcontains",tradeSearch.getTradeIdentifierPartyA().getFilter().getRealValue()).displayed().isFalse();
                }
                if (tradeSearch.getTradeIdentifierPartyA().getType().value().equals("Exactly matching"))
                    assertThat("ts.column.trade.identifier.party.a").allInnerTexts().containsOnly(tradeSearch.getTradeIdentifierPartyA().getFilter().getRealValue());
            }else {
                assertThat("ts.column.trade.identifier.party.a.notstartwith",tradeSearch.getTradeIdentifierPartyA().getFilter().getRealValue()).displayed().isFalse();
            }

        }
        if (tradeSearch.getTradeIdentifier2PartyA()!=null && tradeSearch.getTradeIdentifier2PartyA().getFilter()!=null){
            if (tradeSearch.getTradeIdentifier2PartyA().getType()!=null && !tradeSearch.getTradeIdentifier2PartyA().getType().value().equals("Starting With")){
                if (tradeSearch.getTradeIdentifier2PartyA().getType().value().equals("Containing")){
                    assertThat("ts.column.trade.identifier.party.a.notcontains",tradeSearch.getTradeIdentifier2PartyA().getFilter().getRealValue()).displayed().isFalse();
                }
                if (tradeSearch.getTradeIdentifier2PartyA().getType().value().equals("Exactly matching"))
                    assertThat("ts.column.trade.identifier.party.a").allInnerTexts().containsOnly(tradeSearch.getTradeIdentifier2PartyA().getFilter().getRealValue());
            }else {
                assertThat("ts.column.trade.identifier.party.a.notstartwith",tradeSearch.getTradeIdentifier2PartyA().getFilter().getRealValue()).displayed().isFalse();
            }
        }
        if (tradeSearch.getTradeIdentifierPartyB()!=null && tradeSearch.getTradeIdentifierPartyB().getFilter()!=null){
            if (tradeSearch.getTradeIdentifierPartyB().getType()!=null && !tradeSearch.getTradeIdentifierPartyB().getType().value().equals("Starting With")){
                if (tradeSearch.getTradeIdentifierPartyB().getType().value().equals("Containing")){
                    assertThat("ts.column.trade.identifier.party.a.notcontains",tradeSearch.getTradeIdentifierPartyB().getFilter().getRealValue()).displayed().isFalse();
                }
                if (tradeSearch.getTradeIdentifierPartyB().getType().value().equals("Exactly matching"))
                    assertThat("ts.column.trade.identifier.party.a").allInnerTexts().containsOnly(tradeSearch.getTradeIdentifierPartyB().getFilter().getRealValue());
            }else {
                assertThat("ts.column.trade.identifier.party.a.notstartwith",tradeSearch.getTradeIdentifierPartyB().getFilter().getRealValue()).displayed().isFalse();
            }
        }
    }


}
