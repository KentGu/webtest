package com.lombardrisk.pages.collateral;

import com.lombardrisk.pojo.*;
import org.yiwan.webcore.web.IWebDriverWrapper;
import org.yiwan.webcore.web.PageBase;

import java.lang.reflect.Method;

/**
 * Created by Kenny Wang on 2/3/2016.
 */
public class LetterInstrumentSearchPage extends PageBase {
    public LetterInstrumentSearchPage(IWebDriverWrapper webDriverWrapper) {
        super(webDriverWrapper);
    }
    
    public void clearInstrumentSearch() throws Exception{
    	element("lis.clear").click();
    }
    
    public void searchInstrument() throws Exception{
    	element("lis.search").click();
    }
    
    
    public void setInstrumentSearch(InstrumentSelectionSearch instrumentSelectionSearch) throws Exception{
        if(instrumentSelectionSearch != null){
            if(instrumentSelectionSearch.getPrincipal() != null && !instrumentSelectionSearch.getPrincipal().isEmpty()){
                for(OrganisationSimpleSearch organisationSimpleSearch : instrumentSelectionSearch.getPrincipal()){
                    SimpleSearch simpleSearch = null;
                    if(organisationSimpleSearch.getShortName() != null){
                        simpleSearch = organisationSimpleSearch.getShortName();
                        element("lis.principal.searchType").selectByVisibleText("short name");
                    }else if(organisationSimpleSearch.getLongName() != null){
                        simpleSearch = organisationSimpleSearch.getLongName();
                        element("lis.principal.searchType").selectByVisibleText("long name");
                    }else if(organisationSimpleSearch.getCode() != null){
                        simpleSearch = organisationSimpleSearch.getCode();
                        element("lis.principal.searchType").selectByVisibleText("code");
                    }
                    if(simpleSearch != null) {
                        if (simpleSearch.getFilter() != null)
                            element("lis.principal.searchFilter").input(simpleSearch.getFilter().getRealValue());
                        if (simpleSearch.getLinkText() != null)
                            element("lis.principal.searchLinktext", simpleSearch.getLinkText().getRealValue()).click();
                    }
                }
            }
            if(instrumentSelectionSearch.getCounterparty() != null && !instrumentSelectionSearch.getCounterparty().isEmpty()){
                for(OrganisationSimpleSearch organisationSimpleSearch : instrumentSelectionSearch.getCounterparty()){
                    SimpleSearch simpleSearch = null;
                    if(organisationSimpleSearch.getShortName() != null){
                        simpleSearch = organisationSimpleSearch.getShortName();
                        element("lis.counterparty.searchType").selectByVisibleText("short name");
                    }else if(organisationSimpleSearch.getLongName() != null){
                        simpleSearch = organisationSimpleSearch.getLongName();
                        element("lis.counterparty.searchType").selectByVisibleText("long name");
                    }else if(organisationSimpleSearch.getCode() != null){
                        simpleSearch = organisationSimpleSearch.getCode();
                        element("lis.counterparty.searchType").selectByVisibleText("code");
                    }
                    if(simpleSearch != null) {
                        if (simpleSearch.getFilter() != null)
                            element("lis.counterparty.searchFilter").input(simpleSearch.getFilter().getRealValue());
                        if (simpleSearch.getLinkText() != null)
                            element("lis.counterparty.searchLinktext", simpleSearch.getLinkText().getRealValue()).click();
                    }
                }
            }
            if(instrumentSelectionSearch.getAgreementDescription() != null){
                SimpleSearch simpleSearch = instrumentSelectionSearch.getAgreementDescription();
                if(simpleSearch.getType() != null)
                    element("lis.agreementDesc.searchType").selectByVisibleText(simpleSearch.getType().value());
                if(simpleSearch.getFilter() != null)
                    element("lis.agreementDesc.searchFilter").input(simpleSearch.getFilter().getRealValue());
            }
            if(instrumentSelectionSearch.getDeliveryPriority() != null)
                element("lis.deliveryPriority").selectByVisibleText(instrumentSelectionSearch.getDeliveryPriority().getRealValue());
            if(instrumentSelectionSearch.getRecallPriority() != null)
                element("lis.recallPriority").selectByVisibleText(instrumentSelectionSearch.getRecallPriority().getRealValue());
            if(instrumentSelectionSearch.getAssetClass() != null)
                element("lis.assetClass").selectByVisibleText(instrumentSelectionSearch.getAssetClass().getRealValue());
            if(instrumentSelectionSearch.getAssetType() != null)
                element("lis.assetType").selectByVisibleText(instrumentSelectionSearch.getAssetType().getRealValue());
            if(instrumentSelectionSearch.getPosition() != null)
                element("lis.position").selectByVisibleText(instrumentSelectionSearch.getPosition().getRealValue());
            if(instrumentSelectionSearch.getBook() != null)
                element("lis.book").selectByVisibleText(instrumentSelectionSearch.getBook().getRealValue());
            if(instrumentSelectionSearch.getInstrumentId() != null){
                SimpleSearch simpleSearch = instrumentSelectionSearch.getInstrumentId();
                if(simpleSearch.getType() != null)
                    element("lis.instruId.searchType").selectByVisibleText(simpleSearch.getType().value());
                if(simpleSearch.getFilter() != null)
                    element("lis.instruId.searchFilter").input(simpleSearch.getFilter().getRealValue());
            }
            if(instrumentSelectionSearch.getDescription() != null){
                SimpleSearch simpleSearch = instrumentSelectionSearch.getDescription();
                if(simpleSearch.getType() != null)
                    element("lis.description.searchType").selectByVisibleText(simpleSearch.getType().value());
                if(simpleSearch.getFilter() != null)
                    element("lis.description.searchFilter").input(simpleSearch.getFilter().getRealValue());
            }
            if(instrumentSelectionSearch.getMaturityDate() != null)
                element("lis.maturityDate").input(instrumentSelectionSearch.getMaturityDate().getRealValue());
            if(instrumentSelectionSearch.isIncludeInventoryManager() != null)
                element("lis.include.inventory.manager").check(instrumentSelectionSearch.isIncludeInventoryManager());
            if(instrumentSelectionSearch.getInventoryDeliveryGroup() != null)
                element("lis.inventory.delivery.group").selectByVisibleText(instrumentSelectionSearch.getInventoryDeliveryGroup().getRealValue());
            if(instrumentSelectionSearch.getAssetOwner() != null && !instrumentSelectionSearch.getAssetOwner().isEmpty()){
                for(AssetOwnerSearch assetOwnerSearch : instrumentSelectionSearch.getAssetOwner()){
                    if(assetOwnerSearch.getRule() != null)
                        element("lis.assetOwner.rule").selectByVisibleText(assetOwnerSearch.getRule().value());
                    if(assetOwnerSearch.getType() != null)
                        element("lis.assetOwner.searchType").selectByVisibleText(assetOwnerSearch.getType().getRealValue());
                    if(assetOwnerSearch.getFilter() != null)
                        element("lis.assetOwner.searchFilter").input(assetOwnerSearch.getFilter().getRealValue());
                    if(assetOwnerSearch.getLinktext() != null)
                        element("lis.assetOwner.searchLinktext", assetOwnerSearch.getLinktext().getRealValue()).click();
                }
            }
        }
    }
    
    public void selectInstrumentResult(InstrumentSelectionSearchResult instrumentSelectionSearchResult) throws Exception{
        element("lis.selectResult", getInstrumentResultRow(instrumentSelectionSearchResult)).click();
    }

    private String getInstrumentResultRow(InstrumentSelectionSearchResult instrumentSelectionSearchResult) throws Exception{
        StringBuffer xpath = new StringBuffer();
        Method[] methods = instrumentSelectionSearchResult.getClass().getDeclaredMethods();
        for(Method method : methods){
            if(method.getName().startsWith("get")
                    && method.invoke(instrumentSelectionSearchResult) != null
                    && method.getReturnType() == StringType.class){
                StringType stringType = (StringType) method.invoke(instrumentSelectionSearchResult);
                switch (method.getName()) {
                    case "getAssetClass":
//                    xpath.append("[contains(td/text()[2],'" + stringType.getRealValue() + "')][string-length(td/text()[2])=string-length('" + stringType.getRealValue() + "')+25]");
                        xpath.append("[contains(td/text()[2],'").append(stringType.getRealValue()).append("')][string-length(td/text()[2])=string-length('").append(stringType.getRealValue()).append("')+25]");
                        break;
                    case "getInstrumentId":
//                    xpath.append("[td/a='" + stringType.getRealValue() + "']");
                        xpath.append("[td/a='").append(stringType.getRealValue()).append("']");
                        break;
                    case "getMaturityDate":
//                    xpath.append("[td='" + stringType.getRealValue() + "']");
                        xpath.append("[td='").append(stringType.getRealValue()).append("']");
                        break;
                    default:
//                    xpath.append("[td[contains(.,'" + stringType.getRealValue() + "')]][td[string-length(.)=string-length('" + stringType.getRealValue() + "')+1]]");
                        xpath.append("[td[contains(.,'").append(stringType.getRealValue()).append("')]][td[string-length(.)=string-length('").append(stringType.getRealValue()).append("')+1]]");
                        break;
                }
            }
        }
        return xpath.toString();
    }
}
